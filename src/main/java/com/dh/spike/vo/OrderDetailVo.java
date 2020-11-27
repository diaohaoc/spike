package com.dh.spike.vo;

import com.dh.spike.domain.OrderInfo;

/**
 * Create by DiaoHao on 2020/11/11 20:25
 */
public class OrderDetailVo {
    GoodsVo goods;
    OrderInfo order;

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }
}
