package com.cci.projectx.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;
import java.math.BigDecimal;

@MapClass("com.cci.projectx.core.model.InteractModel")
public class InteractVO{
	
	private Long id;
	private String content;
	private Long userId;
	private Date createTime;
	private Integer tag;
	private BigDecimal longitude;
	private BigDecimal latitude;
	private String addressDescribe;
	private String picture;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return this.content;
	}
		
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	public Long getUserId(){
		return this.userId;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return this.createTime;
	}
		
	public void setTag(Integer tag){
		this.tag = tag;
	}
	
	public Integer getTag(){
		return this.tag;
	}
		
	public void setLongitude(BigDecimal longitude){
		this.longitude = longitude;
	}
	
	public BigDecimal getLongitude(){
		return this.longitude;
	}
		
	public void setLatitude(BigDecimal latitude){
		this.latitude = latitude;
	}
	
	public BigDecimal getLatitude(){
		return this.latitude;
	}
		
	public void setAddressDescribe(String addressDescribe){
		this.addressDescribe = addressDescribe;
	}
	
	public String getAddressDescribe(){
		return this.addressDescribe;
	}
		
	public void setPicture(String picture){
		this.picture = picture;
	}
	
	public String getPicture(){
		return this.picture;
	}
		
		
}