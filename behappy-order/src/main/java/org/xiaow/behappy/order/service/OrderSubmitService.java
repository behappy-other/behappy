package org.xiaow.behappy.order.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xiaow.behappy.order.config.MqProperties;
import org.xiaow.behappy.order.entity.OrderAddrEntity;
import org.xiaow.behappy.order.entity.OrderEntity;
import org.xiaow.behappy.order.entity.OrderItemEntity;
import org.xiaowu.behappy.basket.feign.CartFeign;
import org.xiaowu.behappy.common.core.exception.BeHappyException;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.common.core.util.ResponseConvert;
import org.xiaowu.behappy.order.dto.SubmitOrderDto;
import org.xiaowu.behappy.order.enums.OrderStatus;
import org.xiaowu.behappy.order.vo.ConfirmOrderVo;
import org.xiaowu.behappy.order.vo.OrderItemVo;
import org.xiaowu.behappy.order.vo.UserAddrVo;
import org.xiaowu.behappy.product.feign.ProdFeign;
import org.xiaowu.behappy.product.feign.SkuFeign;
import org.xiaowu.behappy.product.vo.ShopProdVo;
import org.xiaowu.behappy.product.vo.ShopSkuVo;
import org.xiaowu.behappy.ware.dto.WareSkuLockDto;
import org.xiaowu.behappy.ware.dto.WareSkuLockItemDto;
import org.xiaowu.behappy.ware.feign.WareSkuFeign;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import static org.xiaowu.behappy.order.enums.BizCode.GOODS_CAN_NOT_IDENTIFY;
import static org.xiaowu.behappy.thirdparty.enums.BizCode.INSUFFICIENT_INVENTORY;

/**
 *
 * @author xiaowu
 */
@Service
@AllArgsConstructor
public class OrderSubmitService {

    private final OrderAddrService orderAddrService;

    private final Snowflake snowflake;

    private final ProdFeign prodFeign;

    private final SkuFeign skuFeign;

    private final ThreadPoolExecutor executor;

    private final ResponseConvert responseConvert;

    private final CartFeign cartFeign;

    private final OrderService orderService;

    private final OrderItemService orderItemService;

    private final WareSkuFeign wareSkuFeign;

    private final RabbitTemplate rabbitTemplate;

    private final MqProperties mqProperties;

    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public Long submit(Long userId, ConfirmOrderVo confirmOrderVo, SubmitOrderDto submitOrderDto) {
        // 1. 保存订单地址
        UserAddrVo userAddrVo = confirmOrderVo.getUserAddrVo();
        OrderAddrEntity orderAddrEntity = BeanUtil.copyProperties(userAddrVo, OrderAddrEntity.class);
        orderAddrEntity.setUserId(userId);
        orderAddrService.save(orderAddrEntity);

        // 2. 生成订单
        List<Long> prodIds = confirmOrderVo.getOrderItemVos().stream().map(OrderItemVo::getProdId).collect(Collectors.toList());
        List<Long> skuIds = confirmOrderVo.getOrderItemVos().stream().map(OrderItemVo::getSkuId).collect(Collectors.toList());
        CompletableFuture<Void> prodCompletableFuture = CompletableFuture.runAsync(() -> {
            Response<List<ShopProdVo>> prodResponse = prodFeign.shopProdVoList(prodIds);
            int size = responseConvert.convert(prodResponse, new TypeReference<List<ShopProdVo>>() {
            }).size();
            if (prodIds.size() != size) {
                throw new BeHappyException(GOODS_CAN_NOT_IDENTIFY.getCode(), GOODS_CAN_NOT_IDENTIFY.getMsg());
            }
        }, executor);
        CompletableFuture<Void> skuCompletableFuture = CompletableFuture.runAsync(() -> {
            Response<List<ShopSkuVo>> skuResponse = skuFeign.shopSkuVoList(skuIds);
            int size = responseConvert.convert(skuResponse, new TypeReference<List<ShopSkuVo>>() {
            }).size();
            if (skuIds.size() != size) {
                throw new BeHappyException(GOODS_CAN_NOT_IDENTIFY.getCode(), GOODS_CAN_NOT_IDENTIFY.getMsg());
            }
        }, executor);
        CompletableFuture.allOf(skuCompletableFuture, prodCompletableFuture).get();
        long orderNum = snowflake.nextId();
        // 订单商品名称
        StringBuilder orderProdName = new StringBuilder(100);
        LocalDateTime now = LocalDateTime.now();
        List<OrderItemEntity> orderItemEntities = new ArrayList<>();
        // 待删除的购物车信息
        List<Long> basketIds = new ArrayList<>();
        List<WareSkuLockItemDto> wareSkuLockItemDtos = new LinkedList<>();
        for (OrderItemVo confirmOrderItemVo : confirmOrderVo.getOrderItemVos()) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrderNumber(orderNum);
            orderItemEntity.setProdId(confirmOrderItemVo.getProdId());
            orderItemEntity.setSkuId(confirmOrderItemVo.getSkuId());
            orderItemEntity.setSkuName(confirmOrderItemVo.getSkuName());
            orderItemEntity.setProdName(confirmOrderItemVo.getProdName());
            orderItemEntity.setUserId(userId);
            orderItemEntity.setPic(confirmOrderItemVo.getPic());
            orderItemEntity.setBasketDate(now);
            orderItemEntity.setPrice(confirmOrderItemVo.getPrice());
            orderItemEntity.setProdCount(confirmOrderItemVo.getProdCount());
            orderItemEntity.setRecTime(now);
            // 商品总金额
            orderItemEntity.setProductTotalAmount(confirmOrderVo.getTotal());
            orderProdName.append(confirmOrderItemVo.getProdName()).append(",");
            // 待保存的订单子项
            orderItemEntities.add(orderItemEntity);

            // 需要删除的购物车
            if (ObjectUtil.notEqual(confirmOrderItemVo.getBasketId(), null)) {
                basketIds.add(confirmOrderItemVo.getBasketId());
            }
            // 将要所库存的dto子项
            WareSkuLockItemDto wareSkuLockItemDto = new WareSkuLockItemDto();
            wareSkuLockItemDto.setSkuId(confirmOrderItemVo.getSkuId());
            wareSkuLockItemDto.setNum(confirmOrderItemVo.getProdCount());
            wareSkuLockItemDto.setSkuName(confirmOrderItemVo.getSkuName());
            wareSkuLockItemDtos.add(wareSkuLockItemDto);
        }
        String prodName = StrUtil.sub(orderProdName, 0, 100);
        Long orderAddrId = orderAddrEntity.getAddrOrderId();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setProdName(prodName);
        orderEntity.setUserId(userId);
        orderEntity.setOrderNumber(orderNum);
        orderEntity.setTotal(confirmOrderVo.getTotal());
        orderEntity.setRemarks(submitOrderDto.getRemarks());
        orderEntity.setStatus(OrderStatus.UNPAY.value());
        orderEntity.setAddrOrderId(orderAddrId);
        // 商品总数
        orderEntity.setProductNums(confirmOrderVo.getTotalCount());

        // 删除用户购物车
        if (CollUtil.isNotEmpty(basketIds)) {
            cartFeign.deleteCartItem(basketIds, userId);
        }
        // 插入订单
        orderService.save(orderEntity);
        orderItemService.saveBatch(orderItemEntities);
        WareSkuLockDto wareSkuLockDto = new WareSkuLockDto();
        wareSkuLockDto.setOrderSn(orderNum);
        wareSkuLockDto.setLocks(wareSkuLockItemDtos);
        // 远程锁库存
        Response response = wareSkuFeign.orderLockStock(wareSkuLockDto);

        if (response.getCode() != 0) {
            // 库存不足
            throw new BeHappyException(INSUFFICIENT_INVENTORY.getCode(), INSUFFICIENT_INVENTORY.getMsg());
        }
        // 库存足够 锁定成功 给MQ发送消息(等待支付成功,如果未支付,则需要close掉订单)
        rabbitTemplate.convertAndSend(mqProperties.getEventExchange(), mqProperties.getCreateOrderRoutingKey(), orderNum);
        // 返回订单号
        return orderNum;
    }
}
