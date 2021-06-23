package com.github.wxiaoqi.security.common.util;

import java.util.Base64;

public class JDKBase64Util {
	 
    /**
     * BASE64解密
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return Base64.getDecoder().decode(key);
    }
 
    /**
     * BASE64加密
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return Base64.getEncoder().encodeToString(key);
    }
}