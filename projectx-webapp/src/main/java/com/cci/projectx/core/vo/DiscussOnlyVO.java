package com.cci.projectx.core.vo;

import com.cci.projectx.core.model.UserModel;
import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

import java.util.Date;
import java.util.List;

@MapClass("com.cci.projectx.core.model.DiscussModel")
public class DiscussOnlyVO {
	
	private Long id;
	private Long userId;
	private String userName;
	private String userPhoto;
	private String title;
	private Date createTime;
	private String content;
	private String remark;
    private List<UserModel> users;

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

	public List<UserModel> getUsers() {
		return users;
	}

	public void setUsers(List<UserModel> users) {
		this.users = users;
	}
}