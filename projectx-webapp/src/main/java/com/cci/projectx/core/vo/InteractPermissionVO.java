package com.cci.projectx.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.cci.projectx.core.model.InteractPermissionModel")
public class InteractPermissionVO{
	
	private Long id;
	private Long interactId;
	private Long userId;
	private Integer tag;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setInteractId(Long interactId){
		this.interactId = interactId;
	}
	
	public Long getInteractId(){
		return this.interactId;
	}
		
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	public Long getUserId(){
		return this.userId;
	}
		
	public void setTag(Integer tag){
		this.tag = tag;
	}
	
	public Integer getTag(){
		return this.tag;
	}
		
		
}