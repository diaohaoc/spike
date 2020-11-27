package com.dh.spike.redis;

/**
 * Create by DiaoHao on 2020/10/19 11:56
 */
public class UserKey extends BasePrefix{

    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");




}
