package com.dh.spike.redis;

/**
 * Create by DiaoHao on 2020/10/19 11:56
 */
public class GoodsKey extends BasePrefix{

    private GoodsKey(int exppireSeconds, String prefix) {
        super(exppireSeconds, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60,"gd");


}
