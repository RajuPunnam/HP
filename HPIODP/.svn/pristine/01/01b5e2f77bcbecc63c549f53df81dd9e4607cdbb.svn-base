package com.inventory.finalpojos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection = "INVOICES_AV_PN")
public class InvPNConversion {
	
	@Id
	private String id;	
	@Field("SKU")
	private String sku;
	@Field("PLs")
	private String pl;
	@Field("Po")
	private long po;
	@Field("File Date")
	private String fileDate;
	@Field("Date")
	private String poDate;	
	@Field("SKUQTY")
	private int skuQty;	
	@Field("partId")
	private String partId;	
	@Field("AdjustedDate")
	private String adjustedDate;
	@Field("AV")
	private String av;
	@Field("Description")
	private String partDescription;
	private double av_Price;
	private double part_Price;
	private double sku_Price;	
	private double qtyPer;
	@Field("AV_Qty")
	private int avQty;
	@Field("PN_Qty")
	private double partQty;
	public boolean isNotMatched;
	@Field("Supplier")
	private String  supplier;
	@Field("TotalPrice")
	private double totalPrice;
	
	
	public InvPNConversion() {
	}
    
    public InvPNConversion(InvPNConversion invoiceAVPN){
    	this.adjustedDate = invoiceAVPN.adjustedDate;
    	this.fileDate = invoiceAVPN.fileDate;
    	this.pl = invoiceAVPN.pl;
    	this.po = invoiceAVPN.po;
    	this.poDate = invoiceAVPN.poDate;
    	this.sku = invoiceAVPN.sku;
    	this.skuQty = invoiceAVPN.skuQty;
    	this.sku_Price = invoiceAVPN.sku_Price;
    }
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
	public String getPl() {
		return pl;
	}
	public void setPl(String pl) {
		this.pl = pl;
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
	public int getSkuQty() {
		return skuQty;
	}
	public void setSkuQty(int skuQty) {
		this.skuQty = skuQty;
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
	public double getAv_Price() {
		return av_Price;
	}
	public void setAv_Price(double av_Price) {
		this.av_Price = av_Price;
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
	public double getSku_Price() {
		return sku_Price;
	}
	public void setSku_Price(double sku_Price) {
		this.sku_Price = sku_Price;
	}
	public String getAv() {
		return av;
	}
	public int getAvQty() {
		return avQty;
	}
	public void setAvQty(int avQty) {
		this.avQty = avQty;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
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
	public void setAv(String av) {
		this.av = av;
	}
	public double getQtyPer() {
		return qtyPer;
	}	
	public void setQtyPer(double qtyPer) {
		this.qtyPer = qtyPer;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}	

	

}
