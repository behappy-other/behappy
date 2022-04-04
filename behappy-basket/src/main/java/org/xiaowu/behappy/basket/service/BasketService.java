package org.xiaowu.behappy.basket.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.basket.dto.ModifyShopCartDto;
import org.xiaowu.behappy.basket.entity.BasketEntity;
import org.xiaowu.behappy.basket.mapper.BasketMapper;
import org.xiaowu.behappy.basket.vo.ShopCartAmountVo;
import org.xiaowu.behappy.basket.vo.ShopCartItemVo;
import org.xiaowu.behappy.common.core.exception.BeHappyException;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.common.core.util.ResponseConvert;
import org.xiaowu.behappy.common.redis.util.CacheManagerUtil;
import org.xiaowu.behappy.product.feign.ProdFeign;
import org.xiaowu.behappy.product.feign.SkuFeign;
import org.xiaowu.behappy.product.vo.ShopProdVo;
import org.xiaowu.behappy.product.vo.ShopSkuVo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.xiaowu.behappy.basket.enums.BizCode.FAILED_ADD_CART;
import static org.xiaowu.behappy.common.redis.constant.CacheConstant.BASKET_CART_CACHE;
import static org.xiaowu.behappy.product.enums.BizCode.GOODS_FROM_THE_SHELVES;

/**
 * @author xiaowu
 * @date 12:03
 * @apiNote
 */
@Slf4j
@Service
@AllArgsConstructor
public class BasketService extends ServiceImpl<BasketMapper, BasketEntity> implements IService<BasketEntity> {

    private final ProdFeign prodFeign;

    private final SkuFeign skuFeign;

    private final ThreadPoolExecutor executor;

    private final ResponseConvert responseConvert;

    /**
     * 获取购物车
     */
    @SneakyThrows
    public List<ShopCartItemVo> getCartOps(Long userId) {
        log.debug("\n用户 [" + userId + "] 正在操作购物车");
        List<ShopCartItemVo> shopCartItemVos = CacheManagerUtil.getCache(BASKET_CART_CACHE, String.valueOf(userId));
        if (CollUtil.isNotEmpty(shopCartItemVos)) {
            return shopCartItemVos;
        }
        shopCartItemVos = new ArrayList<>();
        // 异步编排
        // 1. 查询购物车信息
        CompletableFuture<List<BasketEntity>> basketFuture = CompletableFuture.supplyAsync(() -> {
            LambdaQueryWrapper<BasketEntity> queryWrapper = Wrappers.<BasketEntity>lambdaQuery().
                    eq(BasketEntity::getUserId, userId);
            List<BasketEntity> basketEntities = baseMapper.selectList(queryWrapper);
            return basketEntities;
        }, executor);
        // 2. 查询product
        CompletableFuture<List<ShopProdVo>> shopProdVosFuture = basketFuture.thenApplyAsync(basketEntities -> {
            List<Long> productIds = basketEntities.stream().map(BasketEntity::getProdId).collect(Collectors.toList());
            Response<List<ShopProdVo>> prodResponse = prodFeign.shopProdVoList(productIds);
            List<ShopProdVo> shopProdVos = responseConvert.convert(prodResponse, new TypeReference<List<ShopProdVo>>() {
            });
            return shopProdVos;
        }, executor);
        // 3. 查询sku
        CompletableFuture<List<ShopSkuVo>> shopSkuVosFuture = basketFuture.thenApplyAsync(basketEntities -> {
            List<Long> skuIds = basketEntities.stream().map(BasketEntity::getSkuId).collect(Collectors.toList());
            Response<List<ShopSkuVo>> skuResponse = skuFeign.shopSkuVoList(skuIds);
            List<ShopSkuVo> shopSkuVos = responseConvert.convert(skuResponse, new TypeReference<List<ShopSkuVo>>() {
            });
            return shopSkuVos;
        }, executor);
        // 4. 等待异步完成,组装信息
        CompletableFuture.allOf(shopProdVosFuture, shopSkuVosFuture).get();

        List<BasketEntity> basketEntities = basketFuture.get();
        List<ShopSkuVo> shopSkuVos = shopSkuVosFuture.get();
        List<ShopProdVo> shopProdVos = shopProdVosFuture.get();
        Map<Long, List<ShopSkuVo>> skuVoMap = shopSkuVos.stream().collect(Collectors.groupingBy(ShopSkuVo::getSkuId));
        Map<Long, List<ShopProdVo>> prodVoMap = shopProdVos.stream().collect(Collectors.groupingBy(ShopProdVo::getProdId));
        for (BasketEntity basketEntity : basketEntities) {
            ShopCartItemVo shopCartItemVo = new ShopCartItemVo();
            Optional.ofNullable(skuVoMap.get(basketEntity.getSkuId())).ifPresent(skuVos -> {
                ShopSkuVo shopSkuVo = skuVos.get(0);
                shopCartItemVo.setSkuName(shopSkuVo.getSkuName());
                shopCartItemVo.setProdName(shopSkuVo.getProdName());
                shopCartItemVo.setPrice(shopSkuVo.getPrice());
                shopCartItemVo.setPic(shopSkuVo.getPic());
                shopCartItemVo.setSkuId(shopSkuVo.getSkuId());
                shopCartItemVo.setProperties(shopSkuVo.getProperties());
            });
            Optional.ofNullable(prodVoMap.get(basketEntity.getProdId())).ifPresent(prodVos -> {
                ShopProdVo shopProdVo = prodVos.get(0);
                shopCartItemVo.setBrief(shopProdVo.getBrief());
                if (StrUtil.isEmpty(shopCartItemVo.getPic())) {
                    shopCartItemVo.setPic(shopProdVo.getPic());
                }
                shopCartItemVo.setProdId(shopProdVo.getProdId());
            });
            shopCartItemVo.setBasketId(basketEntity.getBasketId());
            shopCartItemVo.setProdCount(basketEntity.getBasketCount());
            // 商品总金额
            shopCartItemVo.setProductTotalAmount(NumberUtil.mul(shopCartItemVo.getProdCount(), shopCartItemVo.getPrice()));
            shopCartItemVos.add(shopCartItemVo);
        }
        shopCartItemVos.sort(Comparator.comparing(ShopCartItemVo::getBasketId).reversed());
        CacheManagerUtil.putCache(BASKET_CART_CACHE, userId.toString(), shopCartItemVos);
        return shopCartItemVos;
    }

    /**
     * 先删库,再更新缓存
     * @param userId
     * @param basketIds
     */
    public void deleteShopCartItemsByBasketIds(Long userId, List<Long> basketIds) {
        LambdaQueryWrapper<BasketEntity> queryWrapper = Wrappers.<BasketEntity>lambdaQuery().
                eq(BasketEntity::getUserId, userId).
                in(BasketEntity::getBasketId, basketIds);
        baseMapper.delete(queryWrapper);
        CacheManagerUtil.evictCache(BASKET_CART_CACHE, String.valueOf(userId));
    }

    /**
     * 先删库,再更新缓存
     * @param basketEntity
     */
    public void updateShopCartItem(BasketEntity basketEntity) {
        baseMapper.updateById(basketEntity);
        CacheManagerUtil.evictCache(BASKET_CART_CACHE, basketEntity.getUserId().toString());
    }

    public void addShopCartItem(ModifyShopCartDto modifyShopCartDto, Long userId) {
        BasketEntity basketEntity = new BasketEntity();
        basketEntity.setBasketCount(modifyShopCartDto.getCount());
        basketEntity.setBasketDate(LocalDateTime.now());
        basketEntity.setProdId(modifyShopCartDto.getProdId());
        basketEntity.setUserId(userId);
        basketEntity.setSkuId(modifyShopCartDto.getSkuId());
        baseMapper.insert(basketEntity);
        CacheManagerUtil.evictCache(BASKET_CART_CACHE, String.valueOf(userId));
    }

    @SneakyThrows
    public void modifyCartItem(ModifyShopCartDto modifyShopCartDto, Long userId) {
        List<ShopCartItemVo> cartOps = this.getCartOps(userId);
        // 1. 检查商品状态
        checkProdAnSkuStatus(modifyShopCartDto);
        // 2.判断是添加还是更改
        Optional<ShopCartItemVo> cartItemVoOptional = cartOps.stream().
                filter(shopCartItemVo -> shopCartItemVo.getSkuId().
                        equals(modifyShopCartDto.getSkuId())).
                findFirst();
        cartItemVoOptional.ifPresentOrElse(shopCartItemVo -> {
            // 更新
            BasketEntity basketEntity = new BasketEntity();
            basketEntity.setUserId(userId);
            basketEntity.setBasketCount(modifyShopCartDto.getCount() + shopCartItemVo.getProdCount());
            basketEntity.setBasketId(shopCartItemVo.getBasketId());
            // 防止购物车变成负数
            if (basketEntity.getBasketCount() <= 0) {
                deleteShopCartItemsByBasketIds(userId, Collections.singletonList(basketEntity.getBasketId()));
                throw new BeHappyException(FAILED_ADD_CART.getCode(), FAILED_ADD_CART.getMsg());
            }
            updateShopCartItem(basketEntity);
        }, () -> {
            // 新增
            addShopCartItem(modifyShopCartDto, userId);
        });
    }

    private void checkProdAnSkuStatus(ModifyShopCartDto modifyShopCartDto) throws InterruptedException, ExecutionException {
        // 1. 查询product
        CompletableFuture<ShopProdVo> shopProdVosFuture = CompletableFuture.supplyAsync(() -> {
            Response<List<ShopProdVo>> prodResponse = prodFeign.shopProdVoList(Collections.singletonList(modifyShopCartDto.getProdId()));
            List<ShopProdVo> shopProdVos = responseConvert.convert(prodResponse, new TypeReference<List<ShopProdVo>>() {
            });
            if (CollUtil.isNotEmpty(shopProdVos)) {
                return shopProdVos.get(0);
            }
            return new ShopProdVo();
        }, executor);
        // 2. 查询sku
        CompletableFuture<ShopSkuVo> shopSkuVosFuture = CompletableFuture.supplyAsync(() -> {
            Response<List<ShopSkuVo>> skuResponse = skuFeign.shopSkuVoList(Collections.singletonList(modifyShopCartDto.getSkuId()));
            List<ShopSkuVo> shopSkuVos = responseConvert.convert(skuResponse, new TypeReference<List<ShopSkuVo>>() {
            });
            if (CollUtil.isNotEmpty(shopSkuVos)) {
                return shopSkuVos.get(0);
            }
            return new ShopSkuVo();
        }, executor);
        // 3. 等待异步完成,组装信息
        CompletableFuture.allOf(shopProdVosFuture, shopSkuVosFuture).get();

        // 当商品状态不正常时，不能添加到购物车
        if (shopProdVosFuture.get().getStatus() != 1 || shopSkuVosFuture.get().getStatus() != 1) {
            throw new BeHappyException(GOODS_FROM_THE_SHELVES.getCode(), GOODS_FROM_THE_SHELVES.getMsg());
        }
    }

    public Integer prodCount(Long userId) {
        List<ShopCartItemVo> cartOps = getCartOps(userId);
        if (CollUtil.isEmpty(cartOps)) {
            return 0;
        }
        return cartOps.stream().
                mapToInt(ShopCartItemVo::getProdCount).
                reduce(0, Integer::sum);
    }

    public ShopCartAmountVo prodTotalPay(Long userId, List<Long> basketIds) {
        // 1. 获取购物车item
        List<ShopCartItemVo> cartOps = getCartOps(userId);
        // 2. 过滤得到chosenCartOps
        List<ShopCartItemVo> chosenCartOps = cartOps.
                stream().
                filter(shopCartItemVo -> {
                    for (Long basketId : basketIds) {
                        if (Objects.equals(basketId, shopCartItemVo.getBasketId())) {
                            return true;
                        }
                    }
                    return false;
                }).
                collect(Collectors.toList());
        // 3. 计算金额及数量
        AtomicReference<BigDecimal> finalPrice = new AtomicReference<>(new BigDecimal("0"));
        chosenCartOps.stream().
                map(ShopCartItemVo::getProductTotalAmount).
                reduce(BigDecimal::add).
                ifPresentOrElse(price -> {
                    finalPrice.set(price);
                }, () -> {
                    finalPrice.set(new BigDecimal("0"));
                });
        ShopCartAmountVo shopCartAmountVo = new ShopCartAmountVo();
        shopCartAmountVo.setFinalMoney(finalPrice.get());
        return shopCartAmountVo;
    }
}
