package com.ocean.model.po;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *日志表
 */
@Entity
@Table(name = "system_log")
public  class SystemLogEntity implements Serializable {

    private static final long seriaVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    private String operationAccount;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Column
    private String operationContent;

    @Column
    private String operationType;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperationAccount() {
        return operationAccount;
    }

    public void setOperationAccount(String operationAccount) {
        this.operationAccount = operationAccount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return "SystemLogEntity{" +
            "id=" + id +
            ", operationAccount='" + operationAccount + '\'' +
            ", createTime=" + createTime +
            ", operationContent='" + operationContent + '\'' +
            ", operationType='" + operationType + '\'' +
            '}';
    }
}
