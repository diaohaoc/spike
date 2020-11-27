package com.dh.spike.redis;

/**
 * Create by DiaoHao on 2020/10/19 11:56
 */
public class SpikeUserKey extends BasePrefix{

    private static final int TOKEN_EXPIRE = 3600*24*2;
    private SpikeUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static SpikeUserKey token = new SpikeUserKey(TOKEN_EXPIRE, "tk");
    public static SpikeUserKey getById = new SpikeUserKey(0, "id");
}
