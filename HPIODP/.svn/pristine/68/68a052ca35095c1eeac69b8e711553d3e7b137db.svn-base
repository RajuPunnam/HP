package com.inventory.pojos;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "DOI")
public class DOIBase {


	@Field( "Business Unit")
	private String baseunit;
	
	@Field("ITEM")
	private String item;
	
	@Field("DESCRIPTION")              
	private String description;
	
	@Field("MAKE_BUY")
	private String makebuy;
	
	@Field("SKU")
	private Object sku;
	
	@Field("NETTABLE_INVENTORY")
	private int netQty;
	
	@Field("Total Demand to order")
	private int total_Demand;
	
	@Field("Transit")
	private int transit;
	
	@Field("TOTAL_STK")
	private int totalStock;
	@Field("Date")
    private String date;
	@Field("AdjustedMondayDate")
	private String adjustedMondayDate;	
	private double dollarPrice;
	
	public String getBaseunit() {
		return baseunit;
	}
	public void setBaseunit(String baseunit) {
		this.baseunit = baseunit;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMakebuy() {
		return makebuy;
	}
	public void setMakebuy(String makebuy) {
		this.makebuy = makebuy;
	}
	public Object getSku() {
		return sku;
	}
	public void setSku(Object sku) {
		this.sku = sku;
	}
	public int getNetQty() {
		return netQty;
	}
	public void setNetQty(int netQty) {
		this.netQty = netQty;
	}

	public int getTotalStock() {
		return totalStock;
	}
	public void setTotalStock(int totalStock) {
		this.totalStock = totalStock;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTransit() {
		return transit;
	}
	public void setTransit(int transit) {
		this.transit = transit;
	}
	public String getAdjustedMondayDate() {
		return adjustedMondayDate;
	}
	public void setAdjustedMondayDate(String adjustedMondayDate) {
		this.adjustedMondayDate = adjustedMondayDate;
	}
	public int getTotal_Demand() {
		return total_Demand;
	}
	public void setTotal_Demand(int total_Demand) {
		this.total_Demand = total_Demand;
	}
	public double getDollarPrice() {
		return dollarPrice;
	}
	public void setDollarPrice(double dollarPrice) {
		this.dollarPrice = dollarPrice;
	}
	
}
