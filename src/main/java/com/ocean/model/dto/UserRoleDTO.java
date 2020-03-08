package com.ocean.model.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserRoleDTO {

    @Id
    private Integer userId;
    private String userAccount;
    private String userName;
    private Integer userAge;
    private String userSex;
    private String roleName;
    private String roleSign;
    private Integer confidenceLevel;


    public UserRoleDTO() {
    }

    public UserRoleDTO(Integer userId, String userAccount, String userName, Integer userAge, String userSex, String roleName, String roleSign, Integer confidenceLevel) {
        this.userId = userId;
        this.userAccount = userAccount;
        this.userName = userName;
        this.userAge = userAge;
        this.userSex = userSex;
        this.roleName = roleName;
        this.roleSign = roleSign;
        this.confidenceLevel = confidenceLevel;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
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


    public Integer getConfidenceLevel() {
        return confidenceLevel;
    }

    public void setConfidenceLevel(Integer confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }

    @Override
    public String toString() {
        return "UserRoleDTO{" +
                "userId=" + userId +
                ", userAccount='" + userAccount + '\'' +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                ", userSex='" + userSex + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleSign='" + roleSign + '\'' +
                '}';
    }
}
