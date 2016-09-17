package com.cci.projectx.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

import java.util.Date;

@MapClass("com.cci.projectx.core.entity.Audio")
public class AudioModel{
	
	private Long id;
	private String name;
	private String path;
	private Date createTime;
	private String contentType;
	private String extension;
		
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
		
	public void setPath(String path){
		this.path = path;
	}
	
	public String getPath(){
		return this.path;
	}
		
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return this.createTime;
	}
		
	public void setContentType(String contentType){
		this.contentType = contentType;
	}
	
	public String getContentType(){
		return this.contentType;
	}
		
	public void setExtension(String extension){
		this.extension = extension;
	}
	
	public String getExtension(){
		return this.extension;
	}
		
		
}