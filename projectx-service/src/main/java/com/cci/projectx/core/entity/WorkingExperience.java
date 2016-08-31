package com.cci.projectx.core.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;



@Document(indexName = "user-profile",type = "user-working-experience")
public class WorkingExperience {
    @Id
    private Long id;

    private Long userId;

    private String company;

    private String companyForShort;

    private String logo;

    private String department;

    private String departmentForShort;

    private String position;

    private String positionForShort;

    private String title;

    private String titleForShort;

    private Date startTime;

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyForShort() {
        return companyForShort;
    }

    public void setCompanyForShort(String companyForShort) {
        this.companyForShort = companyForShort;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartmentForShort() {
        return departmentForShort;
    }

    public void setDepartmentForShort(String departmentForShort) {
        this.departmentForShort = departmentForShort;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPositionForShort() {
        return positionForShort;
    }

    public void setPositionForShort(String positionForShort) {
        this.positionForShort = positionForShort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleForShort() {
        return titleForShort;
    }

    public void setTitleForShort(String titleForShort) {
        this.titleForShort = titleForShort;
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