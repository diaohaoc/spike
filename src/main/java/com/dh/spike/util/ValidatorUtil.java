package com.dh.spike.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by DiaoHao on 2020/10/20 20:55
 */
public class ValidatorUtil {
    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    /**
     * 检测手机号的格式是否匹配
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        Matcher m = mobile_pattern.matcher(str);
        return m.matches();
    }



}
