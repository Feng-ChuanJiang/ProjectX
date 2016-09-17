package com.cci.projectx.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.cci.projectx.core.entity.InteractPermission")
public class InteractPermissionModel{
	
	private Long id;
	private Long interactId;
	private Long userId;
	private Long friendId;
		
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
		
	public void setFriendId(Long friendId){
		this.friendId = friendId;
	}
	
	public Long getFriendId(){
		return this.friendId;
	}
		
		
}