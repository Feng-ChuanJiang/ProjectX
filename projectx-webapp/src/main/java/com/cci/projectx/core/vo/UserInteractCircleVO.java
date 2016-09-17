package com.cci.projectx.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.cci.projectx.core.model.UserInteractCircleModel")
public class UserInteractCircleVO{
	
	private Long id;
	private Long userId;
	private Long circleId;
	private Long interactId;
		
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
		
	public void setCircleId(Long circleId){
		this.circleId = circleId;
	}
	
	public Long getCircleId(){
		return this.circleId;
	}
		
	public void setInteractId(Long interactId){
		this.interactId = interactId;
	}
	
	public Long getInteractId(){
		return this.interactId;
	}
		
		
}