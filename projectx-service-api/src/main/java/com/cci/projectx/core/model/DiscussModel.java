package com.cci.projectx.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

import java.util.Date;
import java.util.List;

@MapClass("com.cci.projectx.core.entity.Discuss")
public class DiscussModel {

    private Long id;
    private Long userId;
    private String title;
    private Integer type;
    private Integer permissionType;
    private Date createTime;
    private String content;
    private String remark;
    private String noSee;
    private String noBack;
    private List<Long> permissionUserIds;
    private List<Long> inviteUserIds;

    public String getNoSee() {
        return noSee;
    }

    public void setNoSee(String noSee) {
        this.noSee = noSee;
    }

    public String getNoBack() {
        return noBack;
    }

    public void setNoBack(String noBack) {
        this.noBack = noBack;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return this.type;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    public Integer getPermissionType() {
        return this.permissionType;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public List<Long> getPermissionUserIds() {
        return permissionUserIds;
    }

    public void setPermissionUserIds(List<Long> permissionUserIds) {
        this.permissionUserIds = permissionUserIds;
    }

    public List<Long> getInviteUserIds() {
        return inviteUserIds;
    }

    public void setInviteUserIds(List<Long> inviteUserIds) {
        this.inviteUserIds = inviteUserIds;
    }
}