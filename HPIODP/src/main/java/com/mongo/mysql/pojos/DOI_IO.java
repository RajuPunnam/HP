package com.mongo.mysql.pojos;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.inventory.utill.DateUtil;

@Document(collection="DOI_IO")
@TypeAlias("DOI_IO")
@Entity
@Table(name="doi_processed_tbl")
@Access(value=AccessType.FIELD)
public class DOI_IO {
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	@Field("Date")
	@Column(name="Date")
    private String date;		
	@Field("PN")
	@Column(name="PN")
	private String partNumber;
	@Field("MAKE_BUY")
	@Column(name="MAKE_BUY")
	private String makebuy;	
	@Field("DESCRIPTION")
	@Column(name="DESCRIPTION")
	private String description;
	@Field("Business_Unit")
	@Column(name="Business_Unit")
	private String baseunit;
	@Field("SKU")
	@Column(name="SKU")
	private String sku;	
	@Field("NETTABLE_INVENTORY")
	@Column(name="NETTABLE_INVENTORY")
	private double netQty;	
	@Field("TOTAL_STK")
	@Column(name="TOTAL_STK")
	private double totalStock;
	@Field("Total_Demand_to_Order")
	@Column(name="Total_Demand_to_Order")
	private double totalDemand;
	@Field("Transit")
	@Column(name="Transit")
	private double transit;
	@Column(name="isDead")
	private boolean isDead; 
	@Column(name="isNotMatched")
	private boolean isNotMatched;   
    @Field("Part_in_PCBABOM")
    @Column(name="Part_in_PCBABOM")
    private String pCBABOM ;
    @Column(name="price")
    private double price;
    @Field("Age")
    @Column(name="Age")
    private String group;
    @Column(name="lastOrderDate")
    private String lastOrderDate;
    @Column(name="diffenceDays")
    private int diffenceDays;
    @Column(name="totalPrice")
    private double totalPrice;
    @Field("Supplier")
    @Column(name="Supplier")
    private String supplier;
    @Field("AdjustedDate")
    @Column(name="AdjustedDate")
    private String adjustedDate;
    
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
	public void setSku(String sku) {
		this.sku = sku;
	}
	public double getNetQty() {
		return netQty;
	}
	public void setNetQty(double netQty) {
		this.netQty = netQty;
	}
	public int getId() {
		return id1;
	}
	public void setId(int id) {
		this.id1 = id;
	}
	public double getTotalStock() {
		return totalStock;
	}
	public void setTotalStock(double totalStock) {
		this.totalStock = totalStock;
	}
	public String getDate() {
		return DateUtil.getRequiredDateFornate(date);
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
