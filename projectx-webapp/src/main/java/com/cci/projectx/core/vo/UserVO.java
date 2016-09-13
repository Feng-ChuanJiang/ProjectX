package com.cci.projectx.core.vo;

import com.cci.projectx.core.model.EducationModel;
import com.cci.projectx.core.model.WorkingExperienceModel;
import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@MapClass("com.cci.projectx.core.model.UserModel")
public class UserVO{

	private Long id;
	private String name;
	private String photos;
	private String mobilePhone;
	private String gender;
	private String constellation;
	private Integer age;
	private String lndustry;
	private Long groupId;
	private String labels;
	private BigDecimal longitude;
	private BigDecimal latitude;
	private Long praise;
	private Date createTime;
	private List<WorkingExperienceModel> workingExperiences;
	private List<EducationModel> educations;

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

	public void setPhotos(String photos){
		this.photos = photos;
	}

	public String getPhotos(){
		return this.photos;
	}

	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}

	public String getMobilePhone(){
		return this.mobilePhone;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return this.gender;
	}

	public void setConstellation(String constellation){
		this.constellation = constellation;
	}

	public String getConstellation(){
		return this.constellation;
	}

	public void setAge(Integer age){
		this.age = age;
	}

	public Integer getAge(){
		return this.age;
	}

	public void setLndustry(String lndustry){
		this.lndustry = lndustry;
	}

	public String getLndustry(){
		return this.lndustry;
	}

	public void setGroupId(Long groupId){
		this.groupId = groupId;
	}

	public Long getGroupId(){
		return this.groupId;
	}

	public void setLabels(String labels){
		this.labels = labels;
	}

	public String getLabels(){
		return this.labels;
	}

	public void setLongitude(BigDecimal longitude){
		this.longitude = longitude;
	}

	public BigDecimal getLongitude(){
		return this.longitude;
	}

	public void setLatitude(BigDecimal latitude){
		this.latitude = latitude;
	}

	public BigDecimal getLatitude(){
		return this.latitude;
	}

	public List<WorkingExperienceModel> getWorkingExperiences() {
		return workingExperiences;
	}

	public void setWorkingExperiences(List<WorkingExperienceModel> workingExperiences) {
		this.workingExperiences = workingExperiences;
	}

	public List<EducationModel> getEducations() {
		return educations;
	}

	public void setEducations(List<EducationModel> educations) {
		this.educations = educations;
	}

	public Long getPraise() {
		return praise;
	}

	public void setPraise(Long praise) {
		this.praise = praise;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}