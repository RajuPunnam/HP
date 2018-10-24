package com.inventory.finalpojos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection = "INVOICES_PN")
public class InvPNConversionFinal {
	@Id
	private String id;	
	@Field("SKU")
	private String sku;	
	@Field("Po")
	private long po;
	@Field("File Date")
	private String fileDate;
	@Field("Date")
	private String poDate;	
	@Field("partId")
	private String partId;	
	@Field("AdjustedDate")
	private String adjustedDate;	
	@Field("Description")
	private String partDescription;	
	private double part_Price;	
	@Field("PN_Qty")
	private double partQty;
	public boolean isNotMatched;

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public long getPo() {
		return po;
	}
	public void setPo(long po) {
		this.po = po;
	}
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public String getPoDate() {
		return poDate;
	}
	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}
	
	public String getPartId() {
		return partId;
	}
	public void setPartId(String partId) {
		this.partId = partId;
	}
	
	public String getAdjustedDate() {
		return adjustedDate;
	}
	public void setAdjustedDate(String adjustedDate) {
		this.adjustedDate = adjustedDate;
	}
	
	public double getPart_Price() {
		return part_Price;
	}
	public void setPart_Price(double part_Price) {
		this.part_Price = part_Price;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	
	
	public double getPartQty() {
		return partQty;
	}
	public void setPartQty(double partQty) {
		this.partQty = partQty;
	}
	public boolean isNotMatched() {
		return isNotMatched;
	}
	public void setNotMatched(boolean isNotMatched) {
		this.isNotMatched = isNotMatched;
	}	

}
