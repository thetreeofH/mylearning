package com.ts.spm.bizs.service;

import com.ts.spm.bizs.util.LicencePara;
import com.ts.spm.bizs.util.LicenceUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Author luoyu
 * @Date 2020/9/1 11:29
 * @Version 1.0
 */
@Service
public class LicenceService {
    @Value("${licencePath}")
    private String licencePath;

    //获取licence文件内容
    public LicencePara getLicenceResult(){
        LicencePara lic = new LicencePara();
        String path=licencePath + File.separator+"licence.txt";
        boolean result= false;
        try {
            result = LicenceUtil.isLicValid(path, lic);
        } catch (Exception e) {
            System.out.println("0000000000000000000000000000");
            e.printStackTrace();
        }
        System.out.println(result);
        return lic;
    }

    //获取licence编码
    public String getValidFlag(String valid){
        byte[] text = valid.getBytes(StandardCharsets.UTF_8);;
        byte[] key = LicenceUtil.strKey.getBytes();

        byte[] byteCipher= new byte[0]; //原始二进制
        try {
            byteCipher = LicenceUtil.encryptECB(text, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String base64Cipher = Base64.getEncoder().encodeToString(byteCipher); //base64位输出
        return base64Cipher;
    }
}
