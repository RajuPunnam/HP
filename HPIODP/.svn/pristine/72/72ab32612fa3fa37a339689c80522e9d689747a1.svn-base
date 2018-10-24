package com.mongo.mysql.pojos;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.inventory.utill.DateUtil;
@Document(collection = "INVOICE_AV_PN")
@Entity
@Table(name="invoices_sku_av_pn")
@Access(value=AccessType.FIELD)
public class InvPNConversion {
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="Id",unique = true, nullable = false)
	private int id1;	
	@Field("SKU")
	@Column(name="SKU")
	private String sku;
	@Field("PLs")
	@Column(name="PLs")
	private String pl;
	@Field("Po")
	@Column(name="Po")
	private long po;
	@Field("File Date")
	@Column(name="FILE_DATE")
	private String fileDate;
	@Field("Date")
	@Column(name="Date")
	private String poDate;	
	@Field("SKUQTY")
	@Column(name="SKUQTY")
	private int skuQty;	
	@Field("partId")
	@Column(name="PartId")
	private String partId;	
	@Field("AdjustedDate")
	@Column(name="AdjustedDate")
	private String adjustedDate;
	@Field("AV")
	@Column(name="AV")
	private String av;
	@Field("Description")
	@Column(name="Description")
	private String partDescription;
	@Column(name="av_price")
	private double av_Price;
	@Column(name="part_Price")
	private double part_Price;
	@Column(name="sku_Price")
	private double sku_Price;	
	@Column(name="QtyPer")
	private double qtyPer;
	@Field("AV_Qty")
	@Column(name="AV_Qty")
	private int avQty;
	@Field("PN_Qty")
	@Column(name="PN_Qty")
	private double partQty;
	@Column(name="isNotMatched")
	public boolean isNotMatched;
	@Field("Supplier")
	@Column(name="Supplier")
	private String  supplier;
	@Field("TotalPrice")
	@Column(name="TotalPrice")
	private double totalPrice;
	
	public int getId() {
		return id1;
	}
	public void setId(int id) {
		this.id1 = id;
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
		return DateUtil.getRequiredDateFornate(fileDate);
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
