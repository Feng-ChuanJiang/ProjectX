package com.cci.projectx.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.cci.projectx.core.model.CircleModel")
public class CircleVO {
	
	private Long id;
	private String groupName;
	private String logo;
		
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
		
	public void setLogo(String logo){
		this.logo = logo;
	}
	
	public String getLogo(){
		return this.logo;
	}
		
		
}