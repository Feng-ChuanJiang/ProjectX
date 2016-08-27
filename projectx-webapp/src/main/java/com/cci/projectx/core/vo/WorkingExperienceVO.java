package com.cci.projectx.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;

@MapClass("com.cci.projectx.core.model.WorkingExperienceModel")
public class WorkingExperienceVO{
	
	private Long id;
	private Long userId;
	private Long companyId;
	private String companyForShort;
	private String department;
	private String departmentForShort;
	private String title;
	private String titleForShort;
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
		
	public void setCompanyId(Long companyId){
		this.companyId = companyId;
	}
	
	public Long getCompanyId(){
		return this.companyId;
	}
		
	public void setCompanyForShort(String companyForShort){
		this.companyForShort = companyForShort;
	}
	
	public String getCompanyForShort(){
		return this.companyForShort;
	}
		
	public void setDepartment(String department){
		this.department = department;
	}
	
	public String getDepartment(){
		return this.department;
	}
		
	public void setDepartmentForShort(String departmentForShort){
		this.departmentForShort = departmentForShort;
	}
	
	public String getDepartmentForShort(){
		return this.departmentForShort;
	}
		
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return this.title;
	}
		
	public void setTitleForShort(String titleForShort){
		this.titleForShort = titleForShort;
	}
	
	public String getTitleForShort(){
		return this.titleForShort;
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