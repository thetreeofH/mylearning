package com.ts.spm.bizs.rest.jzpacm;

import java.io.IOException;
import java.util.List;
import com.ts.spm.bizs.biz.jzpacm.HomeManBiz;
import com.ts.spm.bizs.config.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/**
 * @author 作者biguopeng:
 * @version 创建时间：2020年9月7日 说明:首页大屏接口
 */
@Component
public class JzpAcmHomeController {

	@Autowired
    private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	HomeManBiz HomeManBiz;
	
	//@Scheduled(fixedDelay = 10000)
	public void selectCount() throws IOException {
		List<Object> list = HomeManBiz.selectCount();
		String string = redisTemplate.opsForValue().get("123");
		WebSocketUtil.sendMessage(list.toString(), "123");
	}

}
