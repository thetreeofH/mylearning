package com.ts.spm.bizs.entity.admin;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "SPM_SYS.BASE_UNICODE")
public class BaseUnicode {
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    @Column(name = "PREFIX")
    private String prefix;

    @Column(name = "SN")
    private Integer sn;

    /**
     * @return ID
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
     * @return PREFIX
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return SN
     */
    public Integer getSn() {
        return sn;
    }

    /**
     * @param sn
     */
    public void setSn(Integer sn) {
        this.sn = sn;
    }
}