package com.cci.projectx.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

import java.util.Date;
import java.util.List;

@MapClass("com.cci.projectx.core.entity.Comment")
public class CommentModel {
	
	private Long id;
	private Long userId;
	private Long friendId;
	private Long interactId;
	private Long commentId;
	private Date creatTime;
	private String comment;
	private Integer audio;
	private Integer type;
	private CommentUserModel user;
	private CommentUserModel friend;
	private List<CommentModel> commentModels;
		
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
		
	public void setInteractId(Long interactId){
		this.interactId = interactId;
	}
	
	public Long getInteractId(){
		return this.interactId;
	}

	public CommentUserModel getUser() {
		return user;
	}

	public void setUser(CommentUserModel user) {
		this.user = user;
	}

	public CommentUserModel getFriend() {
		return friend;
	}

	public void setFriend(CommentUserModel friend) {
		this.friend = friend;
	}

	public void setCommentId(Long commentId){
		this.commentId = commentId;
	}
	
	public Long getCommentId(){
		return this.commentId;
	}
		
	public void setCreatTime(Date creatTime){
		this.creatTime = creatTime;
	}
	
	public Date getCreatTime(){
		return this.creatTime;
	}
		
	public void setComment(String comment){
		this.comment = comment;
	}
	
	public String getComment(){
		return this.comment;
	}
		
	public void setAudio(Integer audio){
		this.audio = audio;
	}
	
	public Integer getAudio(){
		return this.audio;
	}
		
	public void setType(Integer type){
		this.type = type;
	}
	
	public Integer getType(){
		return this.type;
	}

	public List<CommentModel> getCommentModels() {
		return commentModels;
	}

	public void setCommentModels(List<CommentModel> commentModels) {
		this.commentModels = commentModels;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}
}