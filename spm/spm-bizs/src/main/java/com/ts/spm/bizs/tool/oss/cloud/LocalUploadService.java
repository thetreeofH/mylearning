package com.ts.spm.bizs.tool.oss.cloud;


import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.ts.spm.bizs.config.CloudStorageConfig;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LocalUploadService extends CloudStorageService {
    public LocalUploadService(CloudStorageConfig config){
        this.config = config;

        //初始化
        init();
    }
    private void init(){
    }

    @Override
    public String upload(byte[] data, String path) {
        try {
            String imgurl=config.getLocalAddress()+path;
            File f = new File(imgurl);
            if(!f.exists()){
                f.getParentFile().mkdirs();//创建父级文件路径
                f.createNewFile();//创建文件
            }
            FileOutputStream out = new FileOutputStream(f);
            out.write(data);
            out.close();
        } catch (IOException e) {
            throw new BusinessException("上传文件失败，请检查配置信息");
        }
        return path;
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            throw new BusinessException("上传文件失败");
        }
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getLocalPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getLocalPrefix(), suffix));
    }
}
