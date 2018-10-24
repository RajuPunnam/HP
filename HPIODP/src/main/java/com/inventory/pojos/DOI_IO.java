package com.inventory.pojos;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="DOI_IO")
@TypeAlias("DOI_IO")
public class DOI_IO {
	@Id
	private Object id;
	@Field("Date")
    private String date;		
	@Field("PN")
	private String partNumber;
	@Field("MAKE_BUY")
	private String makebuy;	
	@Field("DESCRIPTION")
	private String description;
	@Field("Business_Unit")
	private String baseunit;
	@Field("SKU")
	private Object sku;	
	@Field("NETTABLE_INVENTORY")
	private double netQty;	
	@Field("TOTAL_STK")
	private double totalStock;
	@Field("Total_Demand_to_Order")
	private double totalDemand;
	@Field("Transit")
	private double transit;
	private boolean isDead; 
	private boolean isNotMatched;   
    @Field("Part_in_PCBABOM")
    private String pCBABOM ;
    @Field("Base Item")
    private String baseItem;
    private double price;
    @Field("Age")
    private String group;
    private String lastOrderDate;
    private int diffenceDays;    
    private double totalPrice;
    @Field("Supplier")
    private String supplier; 
	public String getBaseunit() {
		return baseunit;
	}
	public void setBaseunit(String baseunit) {
		this.baseunit = baseunit;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
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
	public double getNetQty() {
		return netQty;
	}
	public void setNetQty(double netQty) {
		this.netQty = netQty;
	}
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public double getTotalStock() {
		return totalStock;
	}
	public void setTotalStock(double totalStock) {
		this.totalStock = totalStock;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public boolean isNotMatched() {
		return isNotMatched;
	}
	public void setNotMatched(boolean isNotMatched) {
		this.isNotMatched = isNotMatched;
	}	
	public String getpCBABOM() {
		return pCBABOM;
	}
	public void setpCBABOM(String pCBABOM) {
		this.pCBABOM = pCBABOM;
	}
	public String getBaseItem() {
		return baseItem;
	}
	public void setBaseItem(String baseItem) {
		this.baseItem = baseItem;
	}

	public double getTotalDemand() {
		return totalDemand;
	}
	public void setTotalDemand(double totalDemand) {
		this.totalDemand = totalDemand;
	}
	public double getTransit() {
		return transit;
	}
	public void setTransit(double transit) {
		this.transit = transit;
	}
	public boolean isDead() {
		return isDead;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getDiffenceDays() {
		return diffenceDays;
	}
	public void setDiffenceDays(int diffenceDays) {
		this.diffenceDays = diffenceDays;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getLastOrderDate() {
		return lastOrderDate;
	}
	public void setLastOrderDate(String lastOrderDate) {
		this.lastOrderDate = lastOrderDate;
	}
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	@Override
	public String toString() {
		return "DOI_IO [baseunit=" + baseunit + ", partNumber=" + partNumber
				+ ", description=" + description + ", makebuy=" + makebuy
				+ ", sku=" + sku + ", netQty=" + netQty + ", totalStock="
				+ totalStock + ", date=" + date + "]";
	}	
	
}
