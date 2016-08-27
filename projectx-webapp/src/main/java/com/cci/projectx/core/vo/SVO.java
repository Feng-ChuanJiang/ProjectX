package com.cci.projectx.core.vo;

import com.wlw.pylon.core.beans.mapping.annotation.MapClass;
import java.util.Date;

@MapClass("com.cci.projectx.core.model.SModel")
public class SVO{
	
	private Long id;
	private Long userId;
	private Date creatTime;
	private Double u;
	private Double v;
	private Double m;
	private Double n;
	private Double a;
	private Double b;
	private Double c;
	private Double d;
		
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
		
	public void setCreatTime(Date creatTime){
		this.creatTime = creatTime;
	}
	
	public Date getCreatTime(){
		return this.creatTime;
	}
		
	public void setU(Double u){
		this.u = u;
	}
	
	public Double getU(){
		return this.u;
	}
		
	public void setV(Double v){
		this.v = v;
	}
	
	public Double getV(){
		return this.v;
	}
		
	public void setM(Double m){
		this.m = m;
	}
	
	public Double getM(){
		return this.m;
	}
		
	public void setN(Double n){
		this.n = n;
	}
	
	public Double getN(){
		return this.n;
	}
		
	public void setA(Double a){
		this.a = a;
	}
	
	public Double getA(){
		return this.a;
	}
		
	public void setB(Double b){
		this.b = b;
	}
	
	public Double getB(){
		return this.b;
	}
		
	public void setC(Double c){
		this.c = c;
	}
	
	public Double getC(){
		return this.c;
	}
		
	public void setD(Double d){
		this.d = d;
	}
	
	public Double getD(){
		return this.d;
	}
		
		
}