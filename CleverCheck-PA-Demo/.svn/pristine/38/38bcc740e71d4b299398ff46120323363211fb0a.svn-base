package com.techouts.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="MASTERSKUAVBOM_PA")
@TypeAlias("PASkuAvBom")
public class PASkuAvBom {
	@Id
	private String id;
	@Field("bu")
	private String businessUnit;
	@Field("pl")
	private String pl;
	@Field("family")
	private String family;
	@Field("sku")
	private String skuId;
	@Field("av")
	private String avId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	public String getPl() {
		return pl;
	}
	public void setPl(String pl) {
		this.pl = pl;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getAvId() {
		return avId;
	}
	public void setAvId(String avId) {
		this.avId = avId;
	}


}
