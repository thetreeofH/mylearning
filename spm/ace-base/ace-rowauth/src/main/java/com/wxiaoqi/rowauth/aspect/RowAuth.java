package com.wxiaoqi.rowauth.aspect;

import com.wxiaoqi.rowauth.logic.DefaultPermissionLogic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 处理数据集行间加权标记 注解
 * methods.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RowAuth {
    Class<? extends Object> logicClass() default DefaultPermissionLogic.class ;


}
