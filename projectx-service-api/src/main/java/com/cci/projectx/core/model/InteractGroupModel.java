package com.cci.projectx.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.cci.projectx.core.entity.InteractGroup")
public class InteractGroupModel {
	
	private Long id;
	private String groupName;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}
	
	public String getGroupName(){
		return this.groupName;
	}
		
		
}