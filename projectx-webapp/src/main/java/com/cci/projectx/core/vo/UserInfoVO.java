package com.cci.projectx.core.vo;

import com.cci.projectx.core.model.EducationModel;
import com.cci.projectx.core.model.WorkingExperienceModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by haiquanli on 16/7/28.
 */
public class UserInfoVO {
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
    private Date birthday;
    private Integer birthdayType;
    private Date createTime;
    private List<WorkingExperienceModel> workingExperiences;
    private List<EducationModel> educations;
    private String sessionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLndustry() {
        return lndustry;
    }

    public void setLndustry(String lndustry) {
        this.lndustry = lndustry;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Long getPraise() {
        return praise;
    }

    public void setPraise(Long praise) {
        this.praise = praise;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
