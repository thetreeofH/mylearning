package com.wxiaoqi.rowauth.logic;

import com.wxiaoqi.rowauth.aspect.RowAuthLogic;

public abstract class PermissionLogicBase<T> {

    /**
     * 判断当前用户对该行是否有编辑权限
     * @param row 数据行对象
     * @return
     * @throws IllegalAccessException
     */
    @RowAuthLogic
    public   boolean authEdit(T row) throws Exception {
        //判断当前用户对该行是否有编辑权限（结合业务场景）
        return authEditLogic(row);
    }
    public abstract boolean authEditLogic(T row) throws  Exception;


    /**
     * 判断当前用户对该行是否有删除权限
     * @param row 数据行对象
     * @return
     * @throws IllegalAccessException
     */
    @RowAuthLogic
    public    boolean authDel(T row) throws Exception {
        //判断当前用户对该行是否有编辑权限（结合业务场景）
        return authDelLogic(row);
    }
    public abstract boolean authDelLogic(T row) throws  Exception;
}
