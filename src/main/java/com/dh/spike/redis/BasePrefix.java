package com.dh.spike.redis;

/**
 * Create by DiaoHao on 2020/10/19 11:52
 */
public abstract class BasePrefix implements KeyPrefix{

    //key的过期时间
    private int exppireSeconds;
    //前缀
    private String prefix;

    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    public BasePrefix(int exppireSeconds, String prefix) {
        this.exppireSeconds = exppireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {//默认0代表永不过期
        return exppireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
