package com.dh.spike.redis;

/**
 * Create by DiaoHao on 2020/10/19 11:56
 */
public class OrderKey extends BasePrefix {
    public OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getSpikeOrderByUidGid = new OrderKey("soug");
}
