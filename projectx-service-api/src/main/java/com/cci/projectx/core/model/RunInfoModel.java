package com.cci.projectx.core.model;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;

import java.math.BigDecimal;

@MapClass("com.cci.projectx.core.entity.RunInfo")
public class RunInfoModel {
	
	private Long id;
	private Long userId;
	private BigDecimal longitude;
	private BigDecimal latitude;
	private BigDecimal longitude1;
	private BigDecimal latitude1;
	private BigDecimal longitude2;
	private BigDecimal latitude2;
	private BigDecimal longitude3;
	private BigDecimal latitude3;
	private BigDecimal longitude4;
	private BigDecimal latitude4;
		
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
		
	public void setLongitude1(BigDecimal longitude1){
		this.longitude1 = longitude1;
	}
	
	public BigDecimal getLongitude1(){
		return this.longitude1;
	}
		
	public void setLatitude1(BigDecimal latitude1){
		this.latitude1 = latitude1;
	}
	
	public BigDecimal getLatitude1(){
		return this.latitude1;
	}
		
	public void setLongitude2(BigDecimal longitude2){
		this.longitude2 = longitude2;
	}
	
	public BigDecimal getLongitude2(){
		return this.longitude2;
	}
		
	public void setLatitude2(BigDecimal latitude2){
		this.latitude2 = latitude2;
	}
	
	public BigDecimal getLatitude2(){
		return this.latitude2;
	}
		
	public void setLongitude3(BigDecimal longitude3){
		this.longitude3 = longitude3;
	}
	
	public BigDecimal getLongitude3(){
		return this.longitude3;
	}
		
	public void setLatitude3(BigDecimal latitude3){
		this.latitude3 = latitude3;
	}
	
	public BigDecimal getLatitude3(){
		return this.latitude3;
	}
		
	public void setLongitude4(BigDecimal longitude4){
		this.longitude4 = longitude4;
	}
	
	public BigDecimal getLongitude4(){
		return this.longitude4;
	}
		
	public void setLatitude4(BigDecimal latitude4){
		this.latitude4 = latitude4;
	}
	
	public BigDecimal getLatitude4(){
		return this.latitude4;
	}
		
		
}