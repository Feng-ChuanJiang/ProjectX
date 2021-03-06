package com.cci.projectx.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@MapClass("com.cci.projectx.core.entity.User")
public class UserContactsModel {

	private Long id;
	private String name;
	private String password;
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
	private Date birthday;
	private Integer birthdayType;
	private Date createTime;
	private List<UserModel> friends;
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
		
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return this.password;
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
		if(getBirthday()==null){
			return 0;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year=c.get(Calendar.YEAR);
		c.setTime(getBirthday());
		int birthday=c.get(Calendar.YEAR);
		return year-birthday;
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

	public BigDecimal getLatitude() {
		return latitude;
	}

	public List<WorkingExperienceModel> getWorkingExperiences() {
		if(workingExperiences==null){
			workingExperiences=new ArrayList<>();
		}
		return workingExperiences;
	}

	public void setWorkingExperiences(List<WorkingExperienceModel> workingExperiences) {
		this.workingExperiences = workingExperiences;
	}

	public List<EducationModel> getEducations() {
		if(educations==null){
			educations=new ArrayList<>();
		}
		return educations;
	}

	public void setEducations(List<EducationModel> educations) {
		this.educations = educations;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getBirthdayType() {
		return birthdayType;
	}

	public void setBirthdayType(Integer birthdayType) {
		this.birthdayType = birthdayType;
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

	public List<UserModel> getFriends() {
		return friends;
	}

	public void setFriends(List<UserModel> friends) {
		this.friends = friends;
	}
}