package org.xiaow.behappy.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xiaow.behappy.order.entity.OrderAddrEntity;
import org.xiaow.behappy.order.mapper.OrderAddrMapper;

/**
 *
 * @author xiaowu
 */
@Slf4j
@Service
public class OrderAddrService extends ServiceImpl<OrderAddrMapper, OrderAddrEntity> implements IService<OrderAddrEntity> {
}
