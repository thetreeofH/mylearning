/*
 *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>

 *  AG-Enterprise 企业版源码
 *  郑重声明:
 *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  老A将追究授予人和传播人的法律责任!

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.ts.spm.bizs.entity.admin;

import com.ts.spm.bizs.util.AdminCommonConstant;
import com.github.wxiaoqi.security.common.audit.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "SPM_SYS.base_menu")
@AceAudit
public class Menu {
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    private String code;

    private String title;

    @Column(name = "parent_id")
    private String parentId = AdminCommonConstant.ROOT;

    private String href;

    private String icon;

    @Column(name = "type")
    private String type;

    private String description;

    private Integer orderNum;

    @CrtTime
    @Column(name = "crt_time")
    private Date crtTime;
    @CrtUserId
    @Column(name = "crt_user")
    private String crtUser;
    @CrtUserName
    @Column(name = "crt_name")
    private String crtName;

    @Column(name = "crt_host")
    private String crtHost;
    @ModifiedTime
    @Column(name = "upd_time")
    private Date updTime;
    @ModifiedUserId
    @Column(name = "upd_user")
    private String updUser;
    @ModifiedUserName
    @Column(name = "upd_name")
    private String updName;

    @Column(name = "upd_host")
    private String updHost;

    private String attr1;

    private String attr2;

    private String attr3;

    private String attr4;

    private String attr5;

    private String attr6;

    private String attr7;

    private String attr8;

    private String path;
    @Column(name = "tenant_id")
    private String tenantId;

  @Column(name = "is_wailian")
  private String isWailian;

  @Column(name="FUN_TYPE")
  private String funType;

    /**
     *
     * @return FUN_TYPE
     */
    public String getFunType() {
        return funType;
    }

    /**
     *
     * @param funType
     */
    public void setFunType(String funType) {
        this.funType = funType;
    }

    public String getIsWailian() {
    return isWailian;
  }

  public void setIsWailian(String isWailian) {
    this.isWailian = isWailian;
  }

  public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }


    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return parent_id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return href
     */
    public String getHref() {
        return href;
    }

    /**
     * @param href
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return crt_time
     */
    public Date getCrtTime() {
        return crtTime;
    }

    /**
     * @param crtTime
     */
    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    /**
     * @return crt_user
     */
    public String getCrtUser() {
        return crtUser;
    }

    /**
     * @param crtUser
     */
    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    /**
     * @return crt_name
     */
    public String getCrtName() {
        return crtName;
    }

    /**
     * @param crtName
     */
    public void setCrtName(String crtName) {
        this.crtName = crtName;
    }

    /**
     * @return crt_host
     */
    public String getCrtHost() {
        return crtHost;
    }

    /**
     * @param crtHost
     */
    public void setCrtHost(String crtHost) {
        this.crtHost = crtHost;
    }

    /**
     * @return upd_time
     */
    public Date getUpdTime() {
        return updTime;
    }

    /**
     * @param updTime
     */
    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    /**
     * @return upd_user
     */
    public String getUpdUser() {
        return updUser;
    }

    /**
     * @param updUser
     */
    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    /**
     * @return upd_name
     */
    public String getUpdName() {
        return updName;
    }

    /**
     * @param updName
     */
    public void setUpdName(String updName) {
        this.updName = updName;
    }

    /**
     * @return upd_host
     */
    public String getUpdHost() {
        return updHost;
    }

    /**
     * @param updHost
     */
    public void setUpdHost(String updHost) {
        this.updHost = updHost;
    }

    /**
     * @return attr1
     */
    public String getAttr1() {
        return attr1;
    }

    /**
     * @param attr1
     */
    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    /**
     * @return attr2
     */
    public String getAttr2() {
        return attr2;
    }

    /**
     * @param attr2
     */
    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    /**
     * @return attr3
     */
    public String getAttr3() {
        return attr3;
    }

    /**
     * @param attr3
     */
    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    /**
     * @return attr4
     */
    public String getAttr4() {
        return attr4;
    }

    /**
     * @param attr4
     */
    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    /**
     * @return attr5
     */
    public String getAttr5() {
        return attr5;
    }

    /**
     * @param attr5
     */
    public void setAttr5(String attr5) {
        this.attr5 = attr5;
    }

    /**
     * @return attr6
     */
    public String getAttr6() {
        return attr6;
    }

    /**
     * @param attr6
     */
    public void setAttr6(String attr6) {
        this.attr6 = attr6;
    }

    /**
     * @return attr7
     */
    public String getAttr7() {
        return attr7;
    }

    /**
     * @param attr7
     */
    public void setAttr7(String attr7) {
        this.attr7 = attr7;
    }

    /**
     * @return attr8
     */
    public String getAttr8() {
        return attr8;
    }

    /**
     * @param attr8
     */
    public void setAttr8(String attr8) {
        this.attr8 = attr8;
    }

    /**
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
