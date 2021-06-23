package com.ts.spm.bizs.vo.admin;

import com.github.wxiaoqi.security.common.vo.TreeNodeVO;

import java.util.Date;
import java.util.List;

/**
 * @ClassName OperatorTree
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/9/7 15:28
 * @Version 1.0
 **/
public class OperatorTree extends TreeNodeVO<OperatorTree> {
    String label;
    String code;

    String id;
    String name;
    String parentId;
    String type;
    String status;
    Date crtTime;
    Integer orderNum;

    boolean disabled;

    List<UserVo> userVoList;

    public OperatorTree(){

    }

    public OperatorTree(String id, String name, String parentId, String code, String type, String status, Date crtTime, Integer orderNum, boolean disabled) {
        super();
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public OperatorTree(String id, String name, String parentId, String code, String type, String status, Date crtTime, Integer orderNum, boolean disabled, List<UserVo> userVoList) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.code = code;
        this.type = type;
        this.status = status;
        this.crtTime = crtTime;
        this.orderNum = orderNum;
        this.disabled=disabled;
        this.userVoList = userVoList;
    }

    public OperatorTree(String id, String name, String parentId, String code, String type) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.code = code;
        this.type=type;
    }
    public OperatorTree(String id, String name, String parentId, String code) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.code = code;
    }

    public OperatorTree(Object id, Object parentId, String label, String code) {
        this.label = label;
        this.code = code;
        this.setId(id);
        this.setParentId(parentId);
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<UserVo> getUserVoList() {
        return userVoList;
    }

    public void setUserVoList(List<UserVo> userVoList) {
        this.userVoList = userVoList;
    }
}
