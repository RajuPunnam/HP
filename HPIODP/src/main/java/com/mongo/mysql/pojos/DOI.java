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

@Document(collection = "DOI")
@Entity
@Table(name="doi_raw_tbl")
@Access(value=AccessType.FIELD)
public class DOI {
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
    @Field("Business Unit")
	@Column(name="`Business Unit`")
	private String baseunit;
	@Field("ITEM")
	@Column(name="ITEM")
	private String item;
	@Field("DESCRIPTION")
	@Column(name="`DESCRIPTION`")
	private String description;
	@Field("MAKE_BUY")
	@Column(name="MAKE_BUY")
	private String makebuy;
	@Field("SKU")
	@Column(name="SKU")
	private String sku;
	@Field("NETTABLE_INVENTORY")
	@Column(name="NETTABLE_INVENTORY")
	private double netQty;
	@Field("Total Demand to order")
	@Column(name="`Total Demand to order`")
	private double demandQty;
	@Field("Date")
	@Column(name="Date")
	private String date;
	@Field("NON_NETTABLE_INVENTORY")
	@Column(name="NON_NETTABLE_INVENTORY")
	private double NON_NETTABLE_INVENTORY;
	@Field("BONE PILE")
	@Column(name="`BONE PILE`")
	private String BONE_PILE;
	@Field("TOTAL_STK")
	@Column(name="TOTAL_STK")
	private double TOTAL_STK;
	@Field("Transit")
	@Column(name="Transit")
	private double Transit;
	@Field("OO")
	@Column(name="OO")
	private String OO;
	@Field("NET OH")
	@Column(name="`NET OH`")
	private String NET_OH;

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

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public double getNetQty() {
		return netQty;
	}

	public void setNetQty(int netQty) {
		this.netQty = netQty;
	}

	public double getDemandQty() {
		return demandQty;
	}

	public void setDemandQty(int demandQty) {
		this.demandQty = demandQty;
	}

	public String getDate() {
		return com.inventory.utill.DateUtil.getRequiredDateFornate(date);
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getNON_NETTABLE_INVENTORY() {
		return NON_NETTABLE_INVENTORY;
	}

	public void setNON_NETTABLE_INVENTORY(int nON_NETTABLE_INVENTORY) {
		NON_NETTABLE_INVENTORY = nON_NETTABLE_INVENTORY;
	}

	

	public double getTOTAL_STK() {
		return TOTAL_STK;
	}

	public void setTOTAL_STK(int tOTAL_STK) {
		TOTAL_STK = tOTAL_STK;
	}

	public double getTransit() {
		return Transit;
	}

	public void setTransit(int transit) {
		Transit = transit;
	}

	public String getOO() {
		return OO;
	}

	public void setOO(String oO) {
		OO = oO;
	}

	public String getNET_OH() {
		return NET_OH;
	}

	public void setNET_OH(String nET_OH) {
		NET_OH = nET_OH;
	}

	public String getBONE_PILE() {
		return BONE_PILE;
	}

	public void setBONE_PILE(String bONE_PILE) {
		BONE_PILE = bONE_PILE;
	}

	@Override
	public String toString() {
		return "DOI [ baseunit=" + baseunit + ", item="
				+ item + ", description=" + description + ", makebuy="
				+ makebuy + ", sku=" + sku + ", netQty=" + netQty
				+ ", demandQty=" + demandQty + ", date=" + date
				+ ", NON_NETTABLE_INVENTORY=" + NON_NETTABLE_INVENTORY
				+ ", BONE_PILE=" + BONE_PILE + ", TOTAL_STK=" + TOTAL_STK
				+ ", Transit=" + Transit + ", OO=" + OO + ", NET_OH=" + NET_OH
				+ "]";
	}
}
