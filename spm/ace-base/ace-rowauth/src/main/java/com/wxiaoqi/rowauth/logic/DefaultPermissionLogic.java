package com.wxiaoqi.rowauth.logic;

import com.github.ag.core.context.BaseContextHandler;
import com.wxiaoqi.rowauth.util.ObjectUtils;

import java.util.LinkedHashMap;

/**
 * 分页查询 默认约定权限逻辑控制
 */
public class DefaultPermissionLogic extends PermissionLogicBase<Object> {


    /**
     *  判断当前用户对该行是否有编辑权限（谁创建谁可编辑）
     * @param row
     * @return
     * @throws IllegalAccessException
     */
    @Override
    public boolean authEditLogic(Object row) throws Exception {
        String userid = BaseContextHandler.getUserID();
        //通过结构转换模糊差异处理
        return valiAuth(row, userid);
    }




    /**
     * 判断当前用户对该行是否有删除权限
     * @param row
     * @return
     * @throws Exception
     */
    @Override
    public boolean authDelLogic(Object row) throws Exception {
        String userId = BaseContextHandler.getUserID();
        //由于核查申请实体数据结构高度统一，这里通过结构转换模糊差异处理
        return valiAuth(row,userId);
    }

    /**
     * 鉴权判定
     * @param row
     * @param userid
     * @return
     * @throws IllegalAccessException
     */
    private boolean valiAuth(Object row, String userid) throws IllegalAccessException {
        LinkedHashMap<String, Object> map = ObjectUtils.objectToLinkedHashMap(row);
        if(map.containsKey("createUserId")){
            Object createUserId = map.get("createUserId");
            //必须是【登陆用户与记录创建人一致】
            if (createUserId.equals(userid)) {
                return true;
            }
        }else{
            throw  new RuntimeException(DefaultPermissionLogic.class + " define requirements， data rows must have an createUserId attribute  ");
        }
        return false;
    }
}
