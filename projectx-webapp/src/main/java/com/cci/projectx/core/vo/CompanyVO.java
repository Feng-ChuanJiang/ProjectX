package com.cci.projectx.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

@MapClass("com.cci.projectx.core.model.CompanyModel")
public class CompanyVO{
	
	private Long id;
	private String companyName;
	private String companyLogo;
		
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
		
	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}
	
	public String getCompanyName(){
		return this.companyName;
	}
		
	public void setCompanyLogo(String companyLogo){
		this.companyLogo = companyLogo;
	}
	
	public String getCompanyLogo(){
		return this.companyLogo;
	}
		
		
}