package com.techouts.hp.dto;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "DOI_1.1")
@TypeAlias("DOICollection")
public class DOICollection {
	@Field("PartID")
	private String partId;
	@Field("MAKE_BUY")
	private String makeBuy;
	@Field("DESCRIPTION")
	private String description;
	@Field("Business_Unit")
	private String businessUnit;
	@Field("Type")
	private String type;
	@Field("Quantity")
	private int quantity;
	@Field("SKU")
	private String sku;
	@Field("Date")
	private String date;

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getMakeBuy() {
		return makeBuy;
	}

	public void setMakeBuy(String makeBuy) {
		this.makeBuy = makeBuy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@Override
	public String toString() {
		return "DOICollection [partId=" + partId + ", makeBuy=" + makeBuy
				+ ", description=" + description + ", businessUnit="
				+ businessUnit + ", type=" + type + ", quantity=" + quantity
				+ ", sku=" + sku + "]";
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
