package org.xiaow.behappy.order.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.xiaow.behappy.order.config.MqProperties;
import org.xiaow.behappy.order.entity.OrderEntity;
import org.xiaow.behappy.order.mapper.OrderMapper;
import org.xiaowu.behappy.basket.feign.CartFeign;
import org.xiaowu.behappy.basket.vo.ShopCartItemVo;
import org.xiaowu.behappy.common.core.exception.BeHappyException;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.common.core.util.ResponseConvert;
import org.xiaowu.behappy.common.redis.util.CacheManagerUtil;
import org.xiaowu.behappy.member.feign.UserAddrFeign;
import org.xiaowu.behappy.member.vo.UserAddressDetailVo;
import org.xiaowu.behappy.order.dto.*;
import org.xiaowu.behappy.order.enums.OrderStatus;
import org.xiaowu.behappy.order.vo.ConfirmOrderVo;
import org.xiaowu.behappy.order.vo.OrderItemVo;
import org.xiaowu.behappy.order.vo.UserAddrVo;
import org.xiaowu.behappy.product.feign.ProdFeign;
import org.xiaowu.behappy.product.feign.SkuFeign;
import org.xiaowu.behappy.product.vo.ShopProdVo;
import org.xiaowu.behappy.product.vo.ShopSkuVo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import static org.xiaowu.behappy.common.redis.constant.CacheConstant.CONFIRM_ORDER_CACHE;
import static org.xiaowu.behappy.order.enums.BizCode.*;

/**
 *
 * @author xiaowu
 */
@Slf4j
@Service
@AllArgsConstructor
public class OrderService extends ServiceImpl<OrderMapper, OrderEntity> implements IService<OrderEntity> {

    private final UserAddrFeign userAddrFeign;

    private final CartFeign cartFeign;

    private final ProdFeign prodFeign;

    private final SkuFeign skuFeign;

    private final ResponseConvert responseConvert;

    private final ThreadPoolExecutor executor;

    private final OrderSubmitService orderSubmitService;

    private final RabbitTemplate rabbitTemplate;

    private final MqProperties mqProperties;

    @SneakyThrows
    private List<ShopCartItemVo> getShopCartItemsByOrderItems(ConfimOrderDto confimOrderDto, Long userId) {
        // 既没有勾选购买项也没有购物车
        if (confimOrderDto.getOrderItemDto() == null && CollectionUtil.isEmpty(confimOrderDto.getBasketIds())) {
            throw new BeHappyException(NOT_CHOOSE_GOODS.getCode(), NOT_CHOOSE_GOODS.getMsg());
        }
        // 立即购买的情况
        if (CollectionUtil.isEmpty(confimOrderDto.getBasketIds()) && confimOrderDto.getOrderItemDto() != null) {
            CompletableFuture<List<ShopSkuVo>> skuCompletableFuture = CompletableFuture.supplyAsync(() -> {
                Response<List<ShopSkuVo>> skuResponse = skuFeign.shopSkuVoList(Collections.singletonList(confimOrderDto.getOrderItemDto().getSkuId()));
                List<ShopSkuVo> skuVoList = responseConvert.convert(skuResponse, new TypeReference<List<ShopSkuVo>>() {
                });
                return skuVoList;
            }, executor);
            CompletableFuture<List<ShopProdVo>> prodCompletableFuture = CompletableFuture.supplyAsync(() -> {
                Response<List<ShopProdVo>> prodResponse = prodFeign.shopProdVoList(Collections.singletonList(confimOrderDto.getOrderItemDto().getProdId()));
                List<ShopProdVo> shopProdVos = responseConvert.convert(prodResponse, new TypeReference<List<ShopProdVo>>() {
                });
                return shopProdVos;
            }, executor);
            CompletableFuture.allOf(skuCompletableFuture, prodCompletableFuture).get();
            List<ShopSkuVo> shopSkuVos = skuCompletableFuture.get();
            List<ShopProdVo> shopProdVos = prodCompletableFuture.get();
            // 判断状态
            if (CollUtil.isEmpty(shopProdVos) || CollUtil.isEmpty(shopSkuVos)) {
                throw new BeHappyException(GOODS_CAN_NOT_IDENTIFY.getCode(), GOODS_CAN_NOT_IDENTIFY.getMsg());
            }
            // 拿到购物车的所有item
            ShopCartItemVo cartItemVo = new ShopCartItemVo();
            cartItemVo.setBasketId(-1L);
            cartItemVo.setSkuId(confimOrderDto.getOrderItemDto().getSkuId());
            cartItemVo.setProdCount(confimOrderDto.getOrderItemDto().getProdCount());
            cartItemVo.setProdId(confimOrderDto.getOrderItemDto().getProdId());
            cartItemVo.setSkuName(shopSkuVos.get(0).getSkuName());
            cartItemVo.setPic(StrUtil.isBlank(shopSkuVos.get(0).getPic()) ? shopProdVos.get(0).getPic() : shopSkuVos.get(0).getPic());
            cartItemVo.setProdName(shopSkuVos.get(0).getProdName());
            cartItemVo.setProductTotalAmount(NumberUtil.mul(shopSkuVos.get(0).getPrice(), confimOrderDto.getOrderItemDto().getProdCount()));
            cartItemVo.setPrice(shopSkuVos.get(0).getPrice());
            return Collections.singletonList(cartItemVo);
        }
        // 购物车购买的情况
        Response<List<ShopCartItemVo>> cartResponse = cartFeign.userCartItems(userId);
        List<ShopCartItemVo> cartItemVos = responseConvert.convert(cartResponse, new TypeReference<List<ShopCartItemVo>>() {
        });
        // 返回购物车中选中的
        List<ShopCartItemVo> filterCartItems = cartItemVos.stream().
                filter(shopCartItemVo -> confimOrderDto.getBasketIds().contains(shopCartItemVo.getBasketId())).
                collect(Collectors.toList());
        return filterCartItems;
    }

    /**
     * 确认订单
     * @param confimOrderDto
     * @param userId
     * @return
     */
    public ConfirmOrderVo confirmOrder(ConfimOrderDto confimOrderDto, Long userId) {
        //1. 订单的地址信息
        Response<UserAddressDetailVo> userAddrResponse = userAddrFeign.getUserAddrByUserIdAndAddrId(userId, confimOrderDto.getAddrId());
        UserAddressDetailVo addressDetailVo = responseConvert.convert(userAddrResponse, new TypeReference<UserAddressDetailVo>() {
        });
        //2. 判断是立即购买还是购物车进入
        List<ShopCartItemVo> shopCartItemsByOrderItems = getShopCartItemsByOrderItems(confimOrderDto, userId);
        List<OrderItemVo> orderItemVos = BeanUtil.copyToList(shopCartItemsByOrderItems, OrderItemVo.class, CopyOptions.create());
        UserAddrVo userAddrVo = BeanUtil.copyProperties(addressDetailVo, UserAddrVo.class);
        //3. 计算总价,总数等
        BigDecimal total = shopCartItemsByOrderItems.
                stream().
                map(ShopCartItemVo::getProductTotalAmount).
                reduce(BigDecimal::add).get();
        int totalCount = shopCartItemsByOrderItems.
                stream().
                mapToInt(ShopCartItemVo::getProdCount).
                sum();
        ConfirmOrderVo confirmOrderVo = new ConfirmOrderVo();
        confirmOrderVo.setTotal(total);
        confirmOrderVo.setTotalCount(totalCount);
        confirmOrderVo.setUserAddrVo(userAddrVo);
        confirmOrderVo.setOrderItemVos(orderItemVos);
        CacheManagerUtil.putCache(CONFIRM_ORDER_CACHE, userId.toString(), confimOrderDto);
        return confirmOrderVo;
    }

    public Long submitOrder(SubmitOrderDto submitOrderDto, Long userId) {
        ConfirmOrderVo confirmOrderVo = CacheManagerUtil.getCache(CONFIRM_ORDER_CACHE, userId.toString());
        // 30分钟未提交,已过期
        if (ObjectUtil.isNull(confirmOrderVo)) {
            throw new BeHappyException(ORDER_HAS_BEEN_OVERDUE.getCode(), ORDER_HAS_BEEN_OVERDUE.getMsg());
        }
        List<OrderItemVo> orderItemVos = confirmOrderVo.getOrderItemVos();
        orderItemVos.forEach(orderItemVo -> orderItemVo.setRemarks(submitOrderDto.getRemarks()));
        Long orderNum = orderSubmitService.submit(userId, confirmOrderVo, submitOrderDto);
        // 清除缓存
        CacheManagerUtil.evictCache(CONFIRM_ORDER_CACHE, userId.toString());
        // 返回订单号
        return orderNum;
    }

    /**
     * 查询订单状态
     * @param orderNum
     * @return
     */
    public Integer getOrderStatus(Long orderNum) {
        LambdaQueryWrapper<OrderEntity> queryWrapper = Wrappers.<OrderEntity>lambdaQuery().
                select(OrderEntity::getStatus).
                eq(OrderEntity::getOrderNumber, orderNum);
        OrderEntity orderEntity = baseMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNotNull(orderEntity)) {
            return orderEntity.getStatus();
        }
        return null;
    }

    public void closeOrder(Long orderNum) {
        log.info("OrderService - closeOrder: {}", orderNum);

        // 查询订单状态
        LambdaQueryWrapper<OrderEntity> queryWrapper = Wrappers.<OrderEntity>lambdaQuery().
                eq(OrderEntity::getOrderNumber, orderNum);
        OrderEntity orderEntity = baseMapper.selectOne(queryWrapper);
        if (orderEntity.getStatus().equals(OrderStatus.UNPAY.value())) {
            // 更新为关闭状态
            orderEntity.setStatus(OrderStatus.CLOSE.value());
            baseMapper.updateById(orderEntity);
        }
        // 发送队列消息, 解锁库存
        rabbitTemplate.convertAndSend(mqProperties.getEventExchange(),
                mqProperties.getReleaseOtherRoutingKey(), orderNum);
    }

    public PayDto pay(OrderPayDto orderPayDto) {
        PayDto payDto = new PayDto();
        String orderNumber = orderPayDto.getOrderNumber();
        LambdaQueryWrapper<OrderEntity> queryWrapper = Wrappers.<OrderEntity>lambdaQuery().
                eq(OrderEntity::getOrderNumber, orderNumber);
        OrderEntity orderEntity = getOne(queryWrapper);
        // 向上取值2位
        BigDecimal total = orderEntity.getTotal().setScale(2, RoundingMode.UP);
        payDto.setTotalAmount(total.toString());
        payDto.setOutTradeNo(orderNumber);
        payDto.setSubject(orderEntity.getProdName());
        payDto.setBody(orderEntity.getRemarks());
        return payDto;
    }

    public String handlePayResult(PayAsyncDto payAsyncDto) {
        // todo 1.保存交易流水
        // 2.修改订单状态信息
        if (payAsyncDto.getTrade_status().equals("TRADE_SUCCESS") || payAsyncDto.getTrade_status().equals("TRADE_FINISHED")) {
            // 支付成功
            String orderSn = payAsyncDto.getOut_trade_no();
            LambdaUpdateWrapper<OrderEntity> updateWrapper = Wrappers.<OrderEntity>lambdaUpdate().
                    set(OrderEntity::getStatus, OrderStatus.PADYED.value()).
                    set(OrderEntity::getPayTime, LocalDateTime.now()).
                    set(OrderEntity::getPayType, 2).
                    set(OrderEntity::getPayType, 2).
                    eq(OrderEntity::getOrderNumber, orderSn);
            this.baseMapper.update(null, updateWrapper);
        }
        return "success";
    }
}
