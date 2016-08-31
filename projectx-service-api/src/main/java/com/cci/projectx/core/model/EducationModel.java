package com.cci.projectx.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

import java.util.Date;

@MapClass("com.cci.projectx.core.entity.Education")
public class EducationModel{
	
	private Long id;
	private Long userId;
	private String university;
	private String universityForShor;
	private String logo;
	private String degree;
	private String majorx;
	private String majorxForShort;
	private String majory;
	private String majoryForShort;
	private Date startTime;
	private Date endTime;
	private Integer sort;
		
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
		
	public void setUniversity(String university){
		this.university = university;
	}
	
	public String getUniversity(){
		return this.university;
	}
		
	public void setUniversityForShor(String universityForShor){
		this.universityForShor = universityForShor;
	}
	
	public String getUniversityForShor(){
		return this.universityForShor;
	}
		
	public void setLogo(String logo){
		this.logo = logo;
	}
	
	public String getLogo(){
		return this.logo;
	}
		
	public void setDegree(String degree){
		this.degree = degree;
	}
	
	public String getDegree(){
		return this.degree;
	}
		
	public void setMajorx(String majorx){
		this.majorx = majorx;
	}
	
	public String getMajorx(){
		return this.majorx;
	}
		
	public void setMajorxForShort(String majorxForShort){
		this.majorxForShort = majorxForShort;
	}
	
	public String getMajorxForShort(){
		return this.majorxForShort;
	}
		
	public void setMajory(String majory){
		this.majory = majory;
	}
	
	public String getMajory(){
		return this.majory;
	}
		
	public void setMajoryForShort(String majoryForShort){
		this.majoryForShort = majoryForShort;
	}
	
	public String getMajoryForShort(){
		return this.majoryForShort;
	}
		
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	
	public Date getStartTime(){
		return this.startTime;
	}
		
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	
	public Date getEndTime(){
		return this.endTime;
	}
		
	public void setSort(Integer sort){
		this.sort = sort;
	}
	
	public Integer getSort(){
		return this.sort;
	}
		
		
}