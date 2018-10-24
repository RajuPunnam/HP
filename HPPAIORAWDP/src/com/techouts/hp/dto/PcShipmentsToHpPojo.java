package com.techouts.hp.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "INVOICES")
public class PcShipmentsToHpPojo {
	@Field("File Date")
	private String fileDate;
	@Field("Date")
	private String date;
	@Field("SKU")
	private String SKU;
	@Field("PLs")
	private String PLs;
	@Field("Po")
	private double Po;
	@Field("Qty")
	private double quantity;
	@Field("NFiscal")
	private double NFiscal;
	@Field("Emb")
	private String Embarque;
	@Field("HPM1")
	private String hpm1;
	@Field("Family")
	private String family;
	@Field("Customer")
	private String customer;
	@Field("Pallet")
	private double Pallet;
	@Field("Shipped")
	private double Shipped;
	@Field("WO")
	private int wo;
	@Field("WO Qty")
	private double woQty;

	public String getFileDate() {
		return fileDate;
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

	public double getNFiscal() {
		return NFiscal;
	}

	public void setNFiscal(double nFiscal) {
		NFiscal = nFiscal;
	}

	public String getEmbarque() {
		return Embarque;
	}

	public void setEmbarque(String embarque) {
		Embarque = embarque;
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

	public int getWo() {
		return wo;
	}

	public void setWo(int wo) {
		this.wo = wo;
	}

	public double getWoQty() {
		return woQty;
	}

	public void setWoQty(double woQty) {
		this.woQty = woQty;
	}

	@Override
	public String toString() {
		return "ShipmentsToHpPojo [fileDate=" + fileDate + ", date=" + date
				+ ", SKU=" + SKU + ", PLs=" + PLs + ", Po=" + Po
				+ ", quantity=" + quantity + ", NFiscal=" + NFiscal
				+ ", Embarque=" + Embarque + ", hpm1=" + hpm1 + ", family="
				+ family + ", customer=" + customer + ", Pallet=" + Pallet
				+ ", Shipped=" + Shipped + "]";
	}
}
