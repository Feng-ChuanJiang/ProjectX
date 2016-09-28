package com.cci.projectx.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.cci.projectx.core.entity.DiscussPermission")
public class DiscussPermissionModel{
	
	private Long id;
	private Long userId;
	private Long discussId;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	public Long getUserId(){
		return this.userId;
	}
		
	public void setDiscussId(Long discussId){
		this.discussId = discussId;
	}
	
	public Long getDiscussId(){
		return this.discussId;
	}
		
		
}