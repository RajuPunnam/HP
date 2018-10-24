package com.io.pojos;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection="OPEN_ORDERS")
public class POOrderDB implements Serializable {

	@Id
	private String id;
	@Field("File Date")
	private String fileDate;
	@Field("PO Received Date")
	private String poRecDate;
	@Field("PO")
	private String po;
	@Field("SKU")
	private String sku;
	@Field("Customer")
	private String customer;
	@Field("PL")
	private String pl;
	@Field("Family")
	private String family;
	@Field("ETS")
	private String ets;
	@Field("Total")
	private double total;
	@Field("Adjusted Date")
	private String adjustedDate;
	@Field("SO")
    private String SO;
	@Field("Delta")
    private double delta;
    
    public String getSO() {
    	if(SO!=null)return SO.trim();
    	else
		return SO;
	}

	public void setSO(String sO) {
		SO = sO;
	}
	
	public String getAdjustedDate() {
		if(adjustedDate!=null)return adjustedDate.trim();
		else
		return adjustedDate;
	}

	public void setAdjustedDate(String adjustedDate) {
		this.adjustedDate = adjustedDate;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	// Setters and Getters
	public String getId() {
		if(id!=null)return id.trim();
		else
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPoRecDate() {
		if(poRecDate!=null)return poRecDate.trim();
		else
		return poRecDate;
	}

	public void setPoRecDate(String poRecDate) {
		this.poRecDate = poRecDate;
	}

	public String getPo() {
		if(po!=null)return po.trim();
		else
		return po;
	}

	public void setPo(String po) {
		this.po = po;
	}

	public String getSku() {
		if(sku!=null)return sku.trim();
		else
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getCustomer() {
		if(customer!=null)return customer.trim();
		else
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getPl() {
		if(pl!=null)return pl.trim();
		else
		return pl;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

	public String getFamily() {
		if(family!=null)return family.trim();
		else
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	/*public String getSkuCons() {
		return skuCons;
	}

	public void setSkuCons(String skuCons) {
		this.skuCons = skuCons;
	}*/

	public String getEts() {
		if(ets!=null)return ets.trim();
		else
		return ets;
	}

	public void setEts(String ets) {
		this.ets = ets;
	}

	public String getFileDate() {
		if(fileDate!=null)return fileDate.trim();
		else
		return fileDate;
	}

	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	
	
	
	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	@Override
	public String toString() {
		return "POOrderDB [id=" + id + ", fileDate=" + fileDate + ", poRecDate=" + poRecDate + ", po=" + po + ", sku="
				+ sku + ", customer=" + customer + ", pl=" + pl + ", family=" + family + ", ets=" + ets + ", total="
				+ total + ", adjustedDate=" + adjustedDate + "]";
	}

}
