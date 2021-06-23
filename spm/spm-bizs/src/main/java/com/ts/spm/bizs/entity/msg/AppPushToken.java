package com.ts.spm.bizs.entity.msg;

import java.util.List;

public class AppPushToken {
	private List<AppPushUserMap> tokens;  //对应APP的华为推送token
	private String station;
	private String point;

	
	public List<AppPushUserMap> getTokens() {
		return tokens;
	}
	public void setTokens(List<AppPushUserMap> tokens) {
		this.tokens = tokens;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public AppPushToken() {
		super();
	}
	
	
	
}
