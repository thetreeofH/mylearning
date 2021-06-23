package com.ts.spm.bizs;

import com.github.wxiaoqi.merge.EnableAceMerge;
import com.github.wxiaoqi.security.auth.client.EnableAceAuthClient;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
@EnableTransactionManagement
// 开启熔断监控
@EnableFeignClients({"com.github.wxiaoqi.security.auth.client.feign"})
@MapperScan("com.ts.spm.bizs.mapper")
@EnableAceAuthClient
@EnableSwagger2Doc
@EnableAceMerge
public class SpmBizsBootstrap {



    public static void main(String[] args) {
        new SpringApplicationBuilder(SpmBizsBootstrap.class).web(true).run(args);
    }
}
