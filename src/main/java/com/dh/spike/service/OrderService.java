package com.dh.spike.service;

import com.dh.spike.dao.OrderDao;
import com.dh.spike.domain.OrderInfo;
import com.dh.spike.domain.SpikeOrder;
import com.dh.spike.domain.SpikeUser;
import com.dh.spike.redis.OrderKey;
import com.dh.spike.redis.RedisService;
import com.dh.spike.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by DiaoHao on 2020/10/23 18:48
 */
@Service
public class OrderService {

    @Resource
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

   public SpikeOrder getSpikeOrderByUserIdGoodsId(long userId, long goodsId) {
        //return orderDao.getSpikeOrderByUserIdGoodsId(userId, goodsId);
       return redisService.get(OrderKey.getSpikeOrderByUidGid, "" + userId + "_" + goodsId, SpikeOrder.class);
   }

   public OrderInfo getOrderById(long orderId) {
       return orderDao.getOrderById(orderId);
   }

    /**
     * 下订单
     * @param user
     * @param goods
     * @return
     */
    @Transactional
    public OrderInfo createOrder(SpikeUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getSpikePrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setUserId(user.getId());
        orderInfo.setStatus(0);
        long orderId = orderDao.insert(orderInfo);
        SpikeOrder spikeOrder = new SpikeOrder();
        spikeOrder.setGoodsId(goods.getId());
        spikeOrder.setOrderId(orderId);
        spikeOrder.setUserId(user.getId());
        orderDao.insertSpikeOrder(spikeOrder);
        redisService.set(OrderKey.getSpikeOrderByUidGid, "" + user.getId() + "_" + goods.getId(), spikeOrder);
        return orderInfo;
    }

}
