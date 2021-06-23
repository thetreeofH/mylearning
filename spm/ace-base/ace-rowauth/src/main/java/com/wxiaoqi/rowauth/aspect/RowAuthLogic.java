package com.wxiaoqi.rowauth.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 数据权限逻辑标记  注解
 * methods.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RowAuthLogic {
    /**
     * 可选填 ，不填标识属性 默认回使用方法名命名
     * @return
     */
    String authField() default  "";


}
