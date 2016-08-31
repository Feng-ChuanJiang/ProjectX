package com.cci.projectx.core.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Document(indexName = "user-profile",type = "user-education")
public class Education {
    @Id
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
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date endTime;

    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getUniversityForShor() {
        return universityForShor;
    }

    public void setUniversityForShor(String universityForShor) {
        this.universityForShor = universityForShor;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getMajorx() {
        return majorx;
    }

    public void setMajorx(String majorx) {
        this.majorx = majorx;
    }

    public String getMajorxForShort() {
        return majorxForShort;
    }

    public void setMajorxForShort(String majorxForShort) {
        this.majorxForShort = majorxForShort;
    }

    public String getMajory() {
        return majory;
    }

    public void setMajory(String majory) {
        this.majory = majory;
    }

    public String getMajoryForShort() {
        return majoryForShort;
    }

    public void setMajoryForShort(String majoryForShort) {
        this.majoryForShort = majoryForShort;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}