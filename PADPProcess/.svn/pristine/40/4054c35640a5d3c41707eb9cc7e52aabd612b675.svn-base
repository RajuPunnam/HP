package com.techouts.pojo;

import org.springframework.data.mongodb.core.mapping.Field;

public class FlexBom {
	
@Field("SR_NO")
private int srno;
@Field("bu")
private String bu;
@Field("family")
private String family;
@Field("sku")
private String sku;
@Field("AV")
private String av;
@Field("PART_NUMBER")
private String partNumber;
@Field("BOM_LEVEL")
private String level;
@Field("QTY_PER")
private double qtyPer;
@Field("FinalQty")
private double finalQty;
private double allocatedQty;
private double qtyPerAv;
@Field("BOM_TYPE")
private String bomTypep;
@Field("MPC")
private String mpc;
@Field("EFF_DATE")
private String  startDate;
@Field("END_DATE")
private String endDate;
@Field("INCLUDE_ON_SCREEN")
private String INCLUDE_ON_SCREEN;
@Field("SCREEN_COMMODITY_GROUP_DESC")
private String SCREEN_COMMODITY_GROUP_DESC;
@Field("AVBL_SCREEN_COMMODITY")
private String AVBL_SCREEN_COMMODITY;
@Field("GROUP_SECONDARY")
private String GROUP_SECONDARY;
@Field("GROUP_SECONDARY_DESC")
private String GROUP_SECONDARY_DESC;

public String getGROUP_SECONDARY() {
	return GROUP_SECONDARY;
}
public void setGROUP_SECONDARY(String gROUP_SECONDARY) {
	GROUP_SECONDARY = gROUP_SECONDARY;
}
public String getGROUP_SECONDARY_DESC() {
	return GROUP_SECONDARY_DESC;
}
public void setGROUP_SECONDARY_DESC(String gROUP_SECONDARY_DESC) {
	GROUP_SECONDARY_DESC = gROUP_SECONDARY_DESC;
}
public String getSCREEN_COMMODITY_GROUP_DESC() {
	return SCREEN_COMMODITY_GROUP_DESC;
}
public void setSCREEN_COMMODITY_GROUP_DESC(String sCREEN_COMMODITY_GROUP_DESC) {
	SCREEN_COMMODITY_GROUP_DESC = sCREEN_COMMODITY_GROUP_DESC;
}
public String getAVBL_SCREEN_COMMODITY() {
	return AVBL_SCREEN_COMMODITY;
}
public void setAVBL_SCREEN_COMMODITY(String aVBL_SCREEN_COMMODITY) {
	AVBL_SCREEN_COMMODITY = aVBL_SCREEN_COMMODITY;
}
public String getINCLUDE_ON_SCREEN() {
	return INCLUDE_ON_SCREEN;
}
public void setINCLUDE_ON_SCREEN(String iNCLUDE_ON_SCREEN) {
	INCLUDE_ON_SCREEN = iNCLUDE_ON_SCREEN;
}
public String getBu() {
	return bu;
}
public void setBu(String bu) {
	this.bu = bu;
}
public String getFamily() {
	return family;
}
public void setFamily(String family) {
	this.family = family;
}
public String getSku() {
	return sku;
}
public void setSku(String sku) {
	this.sku = sku;
}
public int getSrno() {
	return srno;
}
public void setSrno(int srno) {
	this.srno = srno;
}
public String getAv() {
	return av;
}
public void setAv(String av) {
	this.av = av;
}
public String getPartNumber() {
	return partNumber;
}
public void setPartNumber(String partNumber) {
	this.partNumber = partNumber;
}
public int getLevel() {
	if(level.equals("0.1")){
		return Integer.parseInt(level.replace("0.",""));
	}
	else{
		if(level.contains("."))
		return Integer.parseInt(level.replace(".", ""));
		else
			return Integer.parseInt(level);	
	}
}
public void setLevel(String level) {
	this.level = level;
}
public double getQtyPer() {
	return qtyPer;
}
public void setQtyPer(double qtyPer) {
	this.qtyPer = qtyPer;
}
public double getFinalQty() {
	return finalQty;
}
public void setFinalQty(double finalQty) {
	this.finalQty = finalQty;
}

public String getBomTypep() {
	return bomTypep;
}
public void setBomTypep(String bomTypep) {
	this.bomTypep = bomTypep;
}
public String getMpc() {
	return mpc;
}
public void setMpc(String mpc) {
	this.mpc = mpc;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}
public double getAllocatedQty() {
	return allocatedQty;
}
public void setAllocatedQty(double allocatedQty) {
	this.allocatedQty = allocatedQty;
}


public double getQtyPerAv() {
	return qtyPerAv;
}
public void setQtyPerAv(double qtyPerAv) {
	this.qtyPerAv = qtyPerAv;
}
@Override
public String toString() {
	return "FlexBom [srno=" + srno + ", bu=" + bu + ", family=" + family
			+ ", sku=" + sku + ", av=" + av + ", partNumber=" + partNumber
			+ ", level=" + level + ", qtyPer=" + qtyPer + ", finalQty="
			+ finalQty + ", allocatedQty=" + allocatedQty + ", qtyPerAv="
			+ qtyPerAv + ", bomTypep=" + bomTypep + ", mpc=" + mpc
			+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
}

}
