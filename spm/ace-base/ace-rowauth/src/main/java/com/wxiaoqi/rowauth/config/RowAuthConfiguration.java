package com.wxiaoqi.rowauth.config;

import com.wxiaoqi.rowauth.aspect.RowAuthMarkAspect;
import com.wxiaoqi.rowauth.logic.DefaultPermissionLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RowAuthConfiguration {



    @Bean
    public RowAuthMarkAspect rowAuthMarkAspect(){
      return   new RowAuthMarkAspect();
    }
    @Bean
    public DefaultPermissionLogic defaultPermissionLogic(){
        return new DefaultPermissionLogic();
    }
}
