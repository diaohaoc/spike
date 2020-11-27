package com.dh.spike.service;

import com.dh.spike.domain.OrderInfo;
import com.dh.spike.domain.SpikeUser;
import com.dh.spike.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Create by DiaoHao on 2020/10/23 18:48
 */
@Service
public class SpikeService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo spike(SpikeUser user, GoodsVo goods) {
        //减库存、下订单、写入秒杀订单
        goodsService.reduceStock(goods);
        return orderService.createOrder(user, goods);
    }

}
