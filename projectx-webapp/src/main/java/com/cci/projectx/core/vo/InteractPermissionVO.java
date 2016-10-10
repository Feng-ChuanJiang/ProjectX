package com.cci.projectx.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@MapClass("com.cci.projectx.core.model.InteractModel")
public class InteractPermissionVO {

    private Long id;
    private String content;
    private Long userId;
    private Date createTime;
    private Integer privacyPermission;
    private Integer tag;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String addressDescribe;
    private String picture;
    private List<Long> circleIds;
    private List<Long> friendIds;
    private List<Long> invites;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setPrivacyPermission(Integer privacyPermission) {
        this.privacyPermission = privacyPermission;
    }

    public Integer getPrivacyPermission() {
        return this.privacyPermission;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Integer getTag() {
        return this.tag;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLongitude() {
        return this.longitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLatitude() {
        return this.latitude;
    }

    public void setAddressDescribe(String addressDescribe) {
        this.addressDescribe = addressDescribe;
    }

    public String getAddressDescribe() {
        return this.addressDescribe;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return this.picture;
    }

    public List<Long> getCircleIds() {
        return circleIds;
    }

    public void setCircleIds(List<Long> circleIds) {
        this.circleIds = circleIds;
    }

    public List<Long> getFriendIds() {
        return friendIds;
    }

    public void setFriendIds(List<Long> friendIds) {
        this.friendIds = friendIds;
    }

    public List<Long> getInvites() {
        return invites;
    }

    public void setInvites(List<Long> invites) {
        this.invites = invites;
    }
}