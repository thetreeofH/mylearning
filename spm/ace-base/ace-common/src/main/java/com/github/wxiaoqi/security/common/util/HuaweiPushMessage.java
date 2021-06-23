/**
 * 
 */
package com.github.wxiaoqi.security.common.util;

/**
 * 
 * @author huxuehu
 * @date 2016-3-4
 * @version 1.0
 * Copyright telesound.com
 * 
 */
public class HuaweiPushMessage {

	private String title;//消息标题
	private String description;//消息描述
	
	public HuaweiPushMessage(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
