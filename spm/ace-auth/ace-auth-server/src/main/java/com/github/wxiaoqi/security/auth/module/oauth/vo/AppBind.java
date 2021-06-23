package com.github.wxiaoqi.security.auth.module.oauth.vo;

import lombok.Data;

import javax.persistence.*;
@Data
public class AppBind {
    private String id;
    private String deviceId;
    private String userId;
}
