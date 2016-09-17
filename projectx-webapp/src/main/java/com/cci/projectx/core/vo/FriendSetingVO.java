package com.cci.projectx.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.cci.projectx.core.model.FriendSetingModel")
public class FriendSetingVO{
	
	private Long id;
	private Long userId;
	private Long friendId;
	private Integer hisInteract;
	private Integer myInteract;
	private Integer blacklist;
		
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
		
	public void setFriendId(Long friendId){
		this.friendId = friendId;
	}
	
	public Long getFriendId(){
		return this.friendId;
	}
		
	public void setHisInteract(Integer hisInteract){
		this.hisInteract = hisInteract;
	}
	
	public Integer getHisInteract(){
		return this.hisInteract;
	}
		
	public void setMyInteract(Integer myInteract){
		this.myInteract = myInteract;
	}
	
	public Integer getMyInteract(){
		return this.myInteract;
	}
		
	public void setBlacklist(Integer blacklist){
		this.blacklist = blacklist;
	}
	
	public Integer getBlacklist(){
		return this.blacklist;
	}
		
		
}