package com.cci.projectx.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

import java.util.Date;
import java.util.List;

@MapClass("com.cci.projectx.core.model.DiscussModel")
public class DiscussMyModel {
	
	private Long id;
	private Long userId;
	private String userName;
	private String mobilePhone;
	private String userPhoto;
	private String title;
	private Date createTime;
	private String content;
	private String remark;
	private int joinType;
	private String noSee;
	private String noBack;
    private List<DiscussMyModel> users;
	private List<UserModel> inviteUsers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<DiscussMyModel> getUsers() {
		return users;
	}

	public void setUsers(List<DiscussMyModel> users) {
		this.users = users;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

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

	public int getJoinType() {
		return joinType;
	}

	public void setJoinType(int joinType) {
		this.joinType = joinType;
	}

	public List<UserModel> getInviteUsers() {
		return inviteUsers;
	}

	public void setInviteUsers(List<UserModel> inviteUsers) {
		this.inviteUsers = inviteUsers;
	}
}