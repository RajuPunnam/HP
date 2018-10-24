package com.techouts.pojo;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "NEWOPNORDERS_LATEST")
public class OpenOrders {
	@Field("item")
	private String partNumber;
	@Field("ETD")
	private String etdDate;
	@Field("Confirmed_delivery")
	private String conformDliveryDate;
	@Field("Qty")
	private double qty;
	
	
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getEtdDate() {
		return etdDate;
	}
	public void setEtdDate(String etdDate) {
		this.etdDate = etdDate;
	}
	public String getConformDliveryDate() {
		return conformDliveryDate;
	}
	public void setConformDliveryDate(String conformDliveryDate) {
		this.conformDliveryDate = conformDliveryDate;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
}
