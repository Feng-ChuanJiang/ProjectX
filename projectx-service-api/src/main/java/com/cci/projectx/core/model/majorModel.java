package com.cci.projectx.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.cci.projectx.core.entity.major")
public class MajorModel {
	
	private Long id;
	private String name;
	private String remark;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
		
	public void setRemark(String remark){
		this.remark = remark;
	}
	
	public String getRemark(){
		return this.remark;
	}
		
		
}