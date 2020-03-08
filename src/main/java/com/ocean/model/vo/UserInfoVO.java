package com.ocean.model.vo;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 用户信息实体
 */
@Entity
public class UserInfoVO {

    @Id
    private Integer id;
    private String account;
    private String userName;
    private Integer userAge;
    private String userSex;
    private Date createTime;
    private String roleName;
    private String roleSign;

    public UserInfoVO(Integer id, String account, String userName, Integer userAge, String userSex, Date createTime, String roleName, String roleSign) {
        this.id = id;
        this.account = account;
        this.userName = userName;
        this.userAge = userAge;
        this.userSex = userSex;
        this.createTime = createTime;
        this.roleName = roleName;
        this.roleSign = roleSign;
    }

    public UserInfoVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    @Override
    public String toString() {
        return "UserInfoVO{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                ", userSex='" + userSex + '\'' +
                ", createTime=" + createTime +
                ", roleName='" + roleName + '\'' +
                ", roleSign='" + roleSign + '\'' +
                '}';
    }
}
