package org.xiaow.behappy.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xiaow.behappy.order.entity.OrderItemEntity;
import org.xiaow.behappy.order.mapper.OrderItemMapper;

/**
 *
 * @author xiaowu
 */
@Service
public class OrderItemService extends ServiceImpl<OrderItemMapper, OrderItemEntity> implements IService<OrderItemEntity> {
}
