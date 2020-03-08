package com.ocean.model.vo;

/**
 * 消息相关实体
 *
 * @author wm
 */
public class MessageVO {
    //消息id
    private String id;
    //用户账号
    private String userAccount;
    //消息标题
    private String title;
    //消息内容
    private String message;
    //是否已读
    private String isRead;
    //创建时间
    private String createTime;

    public MessageVO() {
    }

    public MessageVO(String id, String userAccount, String title, String message, String isRead, String createTime) {
        this.id = id;
        this.userAccount = userAccount;
        this.title = title;
        this.message = message;
        this.isRead = isRead;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "MessageVO{" +
                "id='" + id + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", isRead='" + isRead + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
