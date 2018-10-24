package com.io.pojos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class SkuBom {

	@Id
	private String id;
	@Field("sku")
	private String sku;
	@Field("av")
	private String avs;
	@Field("bu")
	private String bu;	
	@Field("family")
	private String family;
	
	
	public String getFamily() {
		if(family!=null)return family.trim();
		else
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	
	public String getId() {
		if(id!=null)return id.trim();
		else
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSku() {
		if(sku!=null)return sku.trim();
		else
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getAvs() {
		if(avs!=null)return avs.trim();
		else
		return avs;
	}
	public void setAvs(String avs) {
		this.avs = avs;
	}
	public String getBu() {
		if(bu!=null)return bu.trim();
		else
		return bu;
	}
	public void setBu(String bu) {
		this.bu = bu;
	}
	
	


}
