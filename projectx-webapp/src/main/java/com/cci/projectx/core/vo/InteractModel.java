package com.cci.projectx.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@MapClass("com.cci.projectx.core.entity.Interact")
public class InteractModel{
	
	private Long id;
	private String content;
	private Long userId;
	private Long groupId;
	private Date createTime;
	private Integer privacyPermission;
	private Integer tag;
	private BigDecimal longitude;
	private BigDecimal latitude;
	private String addressDescribe;
	private String picture;
	private List<CommentModel> comments;
	private List<CommentModel> praises;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getPrivacyPermission() {
		return privacyPermission;
	}

	public void setPrivacyPermission(Integer privacyPermission) {
		this.privacyPermission = privacyPermission;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getAddressDescribe() {
		return addressDescribe;
	}

	public void setAddressDescribe(String addressDescribe) {
		this.addressDescribe = addressDescribe;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public List<CommentModel> getComments() {
		return comments;
	}

	public void setComments(List<CommentModel> comments) {
		this.comments = comments;
	}

	public List<CommentModel> getPraises() {
		return praises;
	}

	public void setPraises(List<CommentModel> praises) {
		this.praises = praises;
	}
}