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

@Document(collection = "INVOICES")
@Entity
@Table(name="invoices_raw_tbl")
@Access(value=AccessType.FIELD)
public class ShipmentsToHpPojo {
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	@Field("File Date")
	@Column(name="FileDate")
	private String fileDate;
	@Field("Date")
	@Column(name="Date")
	private String date;
	@Field("SKU")
	@Column(name="SKU")
	private String SKU;
	@Field("PLs")
	@Column(name="PLs")
	private String PLs;
	@Field("Po")
	@Column(name="Po")
	private double Po;
	@Field("Qty")
	@Column(name="Qty")
	private double quantity;
	@Field("NFiscal")
	@Column(name="NFiscal")
	private String NFiscal;
	@Field("HPM1")
	@Column(name="HPM1")
	private String hpm1;
	@Field("Family")
	@Column(name="Family")
	private String family;
	@Field("Customer")
	@Column(name="Customer")
	private String customer;
	@Field("Pallet")
	@Column(name="Pallet")
	private double Pallet;
	@Field("Shipped")
	@Column(name="Shipped")
	private double Shipped;
	
	public String getFileDate() {
		return DateUtil.getRequiredDateFornate(fileDate);
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	public String getPLs() {
		return PLs;
	}
	public void setPLs(String pLs) {
		PLs = pLs;
	}
	public double getPo() {
		return Po;
	}
	public void setPo(double po) {
		Po = po;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public String getNFiscal() {
		return NFiscal;
	}
	public void setNFiscal(String nFiscal) {
		NFiscal = nFiscal;
	}
	
	public String getHpm1() {
		return hpm1;
	}
	public void setHpm1(String hpm1) {
		this.hpm1 = hpm1;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public double getPallet() {
		return Pallet;
	}
	public void setPallet(double pallet) {
		Pallet = pallet;
	}
	public double getShipped() {
		return Shipped;
	}
	public void setShipped(double shipped) {
		Shipped = shipped;
	}
	
	@Override
	public String toString() {
		return "ShipmentsToHpPojo [id1=" + id1 + ", fileDate=" + fileDate
				+ ", date=" + date + ", SKU=" + SKU + ", PLs=" + PLs + ", Po="
				+ Po + ", quantity=" + quantity + ", NFiscal=" + NFiscal
				+ ", hpm1=" + hpm1 + ", family=" + family + ", customer="
				+ customer + ", Pallet=" + Pallet + ", Shipped=" + Shipped
				+ "]";
	}
	
	
}
