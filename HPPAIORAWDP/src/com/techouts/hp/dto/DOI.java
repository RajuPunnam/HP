package com.techouts.hp.dto;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "DOI")
public class DOI {

	private String type;

	@Field("Business Unit")
	private String baseunit;

	@Field("ITEM")
	private String item;

	@Field("DESCRIPTION")
	private String description;

	@Field("MAKE_BUY")
	private String makebuy;

	@Field("SKU")
	private String sku;

	@Field("NETTABLE_INVENTORY")
	private int netQty;

	@Field("Total Demand to order")
	private int demandQty;

	@Field("Date")
	private String date;

	@Field("NON_NETTABLE_INVENTORY")
	private int NON_NETTABLE_INVENTORY;

	@Field("BONE PILE")
	private int BONE_PILE;
	@Field("TOTAL_STK")
	private int TOTAL_STK;

	@Field("Transit")
	private int Transit;

	@Field("OO")
	private String OO;

	@Field("NET OH")
	private String NET_OH;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public int getNetQty() {
		return netQty;
	}

	public void setNetQty(int netQty) {
		this.netQty = netQty;
	}

	public int getDemandQty() {
		return demandQty;
	}

	public void setDemandQty(int demandQty) {
		this.demandQty = demandQty;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNON_NETTABLE_INVENTORY() {
		return NON_NETTABLE_INVENTORY;
	}

	public void setNON_NETTABLE_INVENTORY(int nON_NETTABLE_INVENTORY) {
		NON_NETTABLE_INVENTORY = nON_NETTABLE_INVENTORY;
	}

	

	public int getTOTAL_STK() {
		return TOTAL_STK;
	}

	public void setTOTAL_STK(int tOTAL_STK) {
		TOTAL_STK = tOTAL_STK;
	}

	public int getTransit() {
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

	public int getBONE_PILE() {
		return BONE_PILE;
	}

	public void setBONE_PILE(int bONE_PILE) {
		BONE_PILE = bONE_PILE;
	}

	@Override
	public String toString() {
		return "DOI [type=" + type + ", baseunit=" + baseunit + ", item="
				+ item + ", description=" + description + ", makebuy="
				+ makebuy + ", sku=" + sku + ", netQty=" + netQty
				+ ", demandQty=" + demandQty + ", date=" + date
				+ ", NON_NETTABLE_INVENTORY=" + NON_NETTABLE_INVENTORY
				+ ", BONE_PILE=" + BONE_PILE + ", TOTAL_STK=" + TOTAL_STK
				+ ", Transit=" + Transit + ", OO=" + OO + ", NET_OH=" + NET_OH
				+ "]";
	}
}
