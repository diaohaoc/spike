package com.dh.spike.util;

import java.util.UUID;

/**
 * Create by DiaoHao on 2020/10/23 13:47
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
