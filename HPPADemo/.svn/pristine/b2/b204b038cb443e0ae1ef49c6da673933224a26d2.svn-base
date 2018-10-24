package com.techouts.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection = "PADASHBOARDSKUAVBL")
@TypeAlias("PADashboardSkuAvbl")
public class PADashboardSkuAvbl {
	@Id
	private String id;
	@Field("BU")
	private String bu;
	@Field("FAMILY")
	private String family;
	@Field("SKU")
	private String sku;
	@Field("MAXAVAILABILITY")
	private String skuMaxAvailable;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBu() {
		return bu;
	}
	public void setBu(String bu) {
		this.bu = bu;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getSkuMaxAvailable() {
		return skuMaxAvailable;
	}
	public void setSkuMaxAvailable(String skuMaxAvailable) {
		this.skuMaxAvailable = skuMaxAvailable;
	}
	@Override
	public String toString() {
		return "PADashboardSkuAvbl [id=" + id + ", bu=" + bu + ", family="
				+ family + ", sku=" + sku + ", skuMaxAvailable="
				+ skuMaxAvailable + "]";
	}	
	
}
