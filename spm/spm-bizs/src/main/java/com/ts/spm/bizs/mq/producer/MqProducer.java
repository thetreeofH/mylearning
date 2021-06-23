package com.ts.spm.bizs.mq.producer;/**
 * Created by fengzheng on 2020/7/10.
 */


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName MqProducer
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/7/10 20:19
 * @Version 1.0
 **/
@Component
public class MqProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;



    //exchange交换器名称
    @Value("${mq.config.new.mission.exchange}")
    private String exchange;

     /*
   1.对下面这个方法的三个参数的说明
       1.交换器的名称
       2.路由键
       3.消息
   */

    public void  send(String msg) {
        this.amqpTemplate.convertAndSend(this.exchange, "collect.handle.mission.add", "producer...add========"+msg);
        this.amqpTemplate.convertAndSend(this.exchange, "collect.handle.mission.update", "producer....update========"+msg);
        this.amqpTemplate.convertAndSend(this.exchange, "collect.handle.mission.put", "producer...put========"+msg);
        this.amqpTemplate.convertAndSend(this.exchange, "collect.handle.mission.delete", "producer...delete========"+msg);
    }

}
