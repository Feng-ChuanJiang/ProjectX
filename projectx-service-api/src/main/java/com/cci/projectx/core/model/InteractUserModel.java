package com.cci.projectx.core.model;

import java.util.List;

/**
 * Created by 33303 on 2016/10/10.
 */
public class InteractUserModel {

    private Long id;
    private String name;
    private String photos;
    private String gender;
    private Integer age;
    private List<WorkingExperienceModel> workingExperiences;
    private List<EducationModel> educations;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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
}
