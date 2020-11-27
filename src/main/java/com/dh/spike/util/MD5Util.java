package com.dh.spike.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * 密码加密
 * Create by DiaoHao on 2020/10/19 15:54
 */
public class MD5Util {

    private static final String salt = "1a2b3c4d";

    public static String md5(String pass) {
        return DigestUtils.md5Hex(pass);
    }

    public static String inputPassToFormPass(String inputPass) {
        String password = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(password);
    }

    /**
     * 存入到数据库中的密码
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String password = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(password);
    }

    public static String inputPassToDBPass(String input, String DBsalt) {
        String formPass = inputPassToFormPass(input);
        String dbPass = formPassToDBPass(formPass, DBsalt);
        return dbPass;
    }

//    public static void main(String[] args) {
//        System.out.println(inputPassToDBPass("123456", "1a2b3c4d"));
//    }
}
