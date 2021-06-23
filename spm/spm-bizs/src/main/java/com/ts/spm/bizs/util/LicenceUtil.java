package com.ts.spm.bizs.util;


import net.sf.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Author luoyu
 * @Date 2020/8/31 14:33
 * @Version 1.0
 */
public class LicenceUtil {

    public static final String KEY_ALGORITHM = "AES";
    public static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
    public static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
    public static String strKey = "TelesoundSx@8833"; //密码
    public static String StrIv  = "Sx-1234567ABCDEF"; //向量
    public static LicencePara lic = new LicencePara();
    public static Boolean init = false;

    //初始化key
    public static byte[] initKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        kg.init(128);
        return kg.generateKey().getEncoded();
    }

    //编码-AES/ECB/PKCS5Padding
    public static byte[] encryptECB(byte[] data, byte[] key) throws Exception {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, KEY_ALGORITHM));
            result = cipher.doFinal(data);
        }catch (Exception e){
            return null;
        }
        return result;
    }

    //解码-AES/ECB/PKCS5Padding
    public static byte[] decryptECB(byte[] data, byte[] key) throws Exception {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, KEY_ALGORITHM));
            result =  cipher.doFinal(data);
        }catch (Exception e){
            return null;
        }
        return result;
    }

    //编码-AES/CBC/PKCS5Padding
    public static byte[] encryptCBC(byte[] data, byte[] key, byte[] iv) throws Exception {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, KEY_ALGORITHM), new IvParameterSpec(iv));
            result = cipher.doFinal(data);
        }catch (Exception e){
            return null;
        }
        return result;
    }

    //解码-AES/CBC/PKCS5Padding
    public static byte[] decryptCBC(byte[] data, byte[] key, byte[] iv) throws Exception {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, KEY_ALGORITHM), new IvParameterSpec(iv));
            result = cipher.doFinal(data);
        }catch (Exception e){
            return null;
        }
        return result;
    }


    //-------------------Base64各种情况----------------------------------
    /**
     * Base64可以将ASCII字符串或者是二进制编码成只包含A—Z，a—z，0—9，+，/ 
     * 这64个字符（ 26个大写字母，26个小写字母，10个数字，1个+，一个 / 刚好64个字符）。
     * 这64个字符用6个bit位就可以全部表示出来，一个字节有8个bit 位，那么还剩下两个bit位
     * ，这两个bit位用0来补充。其实，一个Base64字符仍然是8个bit位，但是有效部分只有右边的6个 bit，左边两个永远是0。
     * @param bytes
     * @return
     */

    //编码-base64
    public static String base64Encode1(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);  //
    }

    //解码-base64
    public static String base64Decode1(byte[] bytes) throws UnsupportedEncodingException {
        String src = new String(bytes);
        return base64Decode4(src);  	//必须转换为string才可以，如果直接调用bytes会报错 Illegal base64 character a
    }

    //编码-base64
    public static byte[] base64Encode2(byte[] bytes){
        return Base64.getEncoder().encode(bytes);
    }

    //解码-base64
    public static byte[] base64Decode2(byte[] bytes){
        return Base64.getDecoder().decode(bytes) ;
    }

    //编码-base64
    public static byte[] base64Encode3(String src){
        return Base64.getEncoder().encode(src.getBytes());
    }

    //解码-base64
    public static byte[] base64Decode3(String src){
        src = src.replace("\r\n", "").replace("\n", "").replace("\r", ""); //去除里面的换行符号,否则会出现错误
        return Base64.getDecoder().decode(src);
    }


    //编码-base64
    public static String base64Encode4(String src){
        return Base64.getEncoder().encode(src.getBytes()).toString();
    }

    //编码-base64
    public static String base64Decode4(String src) throws UnsupportedEncodingException{
        try {
            byte[] decBytes = base64Decode3(src);
            return new String(decBytes,  "UTF-8");
        }catch(UnsupportedEncodingException e){
            return null;
        }
    }



    /**
     * 将byte[]转为各种进制的字符串
     * @param bytes byte[]
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);	// 这里的1代表正数 ，这里有问题，因为第一个为0输出错误
    }

    /**
     * 16进制的字符串表示转成字节数组
     *
     * @param hexString 16进制格式的字符串
     * @return 转换后的字节数组
     **/
    public static byte[] toByteArray(String hexString) {
        if (hexString.isEmpty())
            throw new IllegalArgumentException("this hexString must not be empty");

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }



    /**
     *
     * @param licPath  licence所在的路径，全名
     * @param lic 如果有错误，则返回相应的值
     * @return 错误与否
     * @throws Exception
     */
    public static boolean isLicValid(String licPath, LicencePara lic) throws Exception{
        File file = new File(licPath);
        InputStream in = null;
        String sb = new String();
        byte[] temBytes = new byte[1024];

        if (file.isFile() && file.exists()){
            System.out.println("文件打开成功");
            //读取文件完毕
            int byteRead = 0;
            in = new FileInputStream(file);
            System.out.println("当前字节输入流中的字节数为：" + in.available());
            while ((byteRead = in.read(temBytes)) != -1) {
                String str = new String(temBytes, 0, byteRead);
                sb += str;
            }

            System.out.println("读取的licence内容为");
            System.out.println(sb);

            byte[] ase = base64Decode3(sb);
            if (ase == null || ase.length <= 0){
                lic.setMsg("Step1 err");
                return false;
            }

            byte[] jsonArray = decryptCBC(ase, strKey.getBytes(), StrIv.getBytes()); 	 //解码
            if (jsonArray == null){
                lic.setMsg("Step2 err");
                return false;
            }

            String jsonStr = base64Decode1(jsonArray);
            if (jsonStr == null || jsonStr.length() <= 0){
                lic.setMsg("Step3 err");
                return false;
            }

            System.out.println("====================");
            System.out.println(jsonStr);

            JSONObject jsonobject = JSONObject.fromObject(jsonStr);
            if (jsonobject == null){
                lic.setMsg("Step4 err");
                return false;
            }
            String salt = jsonobject.getString("salt");
            String line = jsonobject.getString("lineCnt");
            if (line != null && StringUtil.isNumber(line)){
                lic.setLineCnt(Integer.parseInt(line));
            }else{
                lic.setMsg("Step5 err");
                return false;
            }
            String StationCnt = jsonobject.getString("StationCnt");
            if (StationCnt != null && StringUtil.isNumber(StationCnt)){
                lic.setStationCnt(Integer.parseInt(StationCnt));
            }else{
                lic.setMsg("Step6 err");
                return false;
            }
            String PointCnt = jsonobject.getString("PointCnt");
            if (PointCnt != null && StringUtil.isNumber(PointCnt)){
                lic.setPointCnt(Integer.parseInt(PointCnt));
            }else{
                lic.setMsg("Step6 err");
                return false;
            }
            String UserCnt = jsonobject.getString("UserCnt");
            if (UserCnt != null && StringUtil.isNumber(UserCnt)){
                lic.setUserCnt(Integer.parseInt(UserCnt));
            }else{
                lic.setMsg("Step7 err");
                return false;
            }

        }else{
            lic.setMsg("Lic不存在");
            return false;
        }
        lic.setMsg("解码成功");
        return true;
    }


    public static void testECB(String[] args) throws Exception {
        byte[] text = "U2FsdGVkX1/H2oesxEh/+GOLyeA/0wjwD8g7zDv4OyFRLwJ22bBMzqZ7p9ryrgZF23ZoGA1B1za0LKPqwGkkKENTtluMJjPgv3JM8E0Xs/qrTf5gKcZHq6pjLsR9wYquMmA1Hi9J8N18yPCsOwBvaR7AQagMrTUKcTZ7kTD/E6s=".getBytes(StandardCharsets.UTF_8);;
        byte[] key = "TelesoundSx@8833".getBytes();

        byte[] byteCipher= encryptECB(text, key); //原始二进制
        String base64Cipher = Base64.getEncoder().encodeToString(byteCipher); //base64位输出
        System.out.println("Base64加密");
        System.out.println(base64Cipher);

        byte[] byteCipher2 = Base64.getDecoder().decode(base64Cipher);  //
        byte[] bytePlain = decryptECB(byteCipher2, key); //byte
        String planeText = new String(bytePlain, StandardCharsets.UTF_8);
        System.out.println(planeText);
    }

    public static void testCBC(String[] args) throws Exception {
        String valid="U000204_ceshi";
        byte[] text = valid.getBytes(StandardCharsets.UTF_8);;
        byte[] key = "TelesoundSx@8833".getBytes();
        byte[] iv = "TelesoundSx@8833".getBytes();

        byte[] byteCipher= encryptCBC(text, key, iv); //原始二进制
        String base64Cipher = Base64.getEncoder().encodeToString(byteCipher); //base64位输出
        System.out.println("Base64加密");
        System.out.println(base64Cipher);

        byte[] byteCipher2 = Base64.getDecoder().decode(base64Cipher);  //
        byte[] bytePlain = decryptCBC(byteCipher2, key, iv); //byte
        String planeText = new String(bytePlain, StandardCharsets.UTF_8);
        System.out.println(planeText);
    }


    public static void main(String[] args) throws Exception {
//    	String filePath = "N:/02_workspace/vc/Licence/Release/licence.txt";
//    	init(filePath);
//    	System.out.println(lic.getMsg());

        String valid="U000208_ceshi";
        byte[] text = valid.getBytes(StandardCharsets.UTF_8);;
        byte[] key = "TelesoundSx@8833".getBytes();
        byte[] iv = "TelesoundSx@8833".getBytes();

        byte[] byteCipher= encryptECB(text, key); //原始二进制
        String base64Cipher = Base64.getEncoder().encodeToString(byteCipher); //base64位输出
        System.out.println("Base64加密");
        System.out.println(base64Cipher);
    }

    public static void init(String filePath) throws Exception {
        boolean result = isLicValid(filePath, lic);
        System.out.println(lic.getMsg());
    }
}
