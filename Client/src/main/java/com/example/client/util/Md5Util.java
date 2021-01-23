package com.example.client.util;

import com.example.client.constant.CommonConst;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * MD5加密
 */
public class Md5Util {


    /**
     * shiro的md5加密
     *
     * @param pwd  密码
     * @param salt 盐
     * @param i    加密次数
     * @return 加密后字符串
     */
    public static String toMd5(String pwd, String salt, int i) {
        Md5Hash toMd5 = new Md5Hash(pwd, salt, i);
        return toMd5.toString();
    }



    public static void main(String args[]) {
        System.out.println(toMd5("123456", CommonConst.PASSWORD_SALT, 10));
    }
}
