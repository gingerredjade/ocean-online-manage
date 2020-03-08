package com.ocean.model.po;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *角色信息表
 */
@Entity
@Table(name = "role_info")
public  class RoleInfoEntity implements Serializable {

    private static final long seriaVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    private String roleName;

    @Column
    private String roleSign;

    @Column
    private String description;


    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Column
    private Integer confidenceLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleSign() {
        return roleSign;
    }

    public void setRoleSign(String roleSign) {
        this.roleSign = roleSign;
    }

    public String getDescribe() {
        return description;
    }

    public void setDescribe(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getConfidenceLevel() {
        return confidenceLevel;
    }

    public void setConfidenceLevel(Integer confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }

    @Override
    public String toString() {
        return "RoleInfoEntity{" +
            "id=" + id +
            ", roleName='" + roleName + '\'' +
            ", roleSign='" + roleSign + '\'' +
            ", describe='" + description + '\'' +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            '}';
    }
}
