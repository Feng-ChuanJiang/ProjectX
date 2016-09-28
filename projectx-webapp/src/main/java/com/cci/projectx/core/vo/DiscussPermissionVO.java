package com.cci.projectx.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.cci.projectx.core.model.DiscussPermissionModel")
public class DiscussPermissionVO{
	
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