package com.cci.projectx.core.vo;

import java.util.Date;

/**
 * Created by haiquanli on 16/7/28.
 */
public class UserInfoVO {
    private Long id;
    private String account;
    private Date createTime;
    private String sessionId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
