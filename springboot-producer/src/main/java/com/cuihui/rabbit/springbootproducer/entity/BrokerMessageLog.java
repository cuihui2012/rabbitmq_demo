package com.cuihui.rabbit.springbootproducer.entity;

import java.io.Serializable;
import java.util.Date;

public class BrokerMessageLog implements Serializable {
    private static final long serialVersionUID = -1036681821461460479L;

    private String messageId;
    private String message;
    private Integer tryCount = 0;
    private String status;
    private Date nextRetry;
    private Date createTime;
    private Date updateTime;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getNextRetry() {
        return nextRetry;
    }

    public void setNextRetry(Date nextRetry) {
        this.nextRetry = nextRetry;
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

    public BrokerMessageLog() {
    }

    public BrokerMessageLog(String messageId, String message, Integer tryCount, String status, Date nextRetry, Date createTime, Date updateTime) {
        this.messageId = messageId;
        this.message = message;
        this.tryCount = tryCount;
        this.status = status;
        this.nextRetry = nextRetry;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "BrokerMessageLog{" +
                "messageId='" + messageId + '\'' +
                ", message='" + message + '\'' +
                ", tryCount=" + tryCount +
                ", status='" + status + '\'' +
                ", nextRetry=" + nextRetry +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}