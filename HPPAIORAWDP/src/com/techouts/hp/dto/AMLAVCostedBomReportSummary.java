package com.techouts.hp.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="BOM_AMLCOSTEDBOM_AVSUMMARY")
public class AMLAVCostedBomReportSummary 
{
@Field("SR NO")
private int srNo;
@Field("Date")
private String date;
@Field("CM")
private String CM;
@Field("AV PN")
private String AV_PN;
@Field("Level")
private String Level;
@Field("Part Number")
private String Part_Number;
@Field("Position")
private String Position;
@Field("Type")
private String Type;
@Field("Qty Per")
private double QtyPer;
@Field("Supplier")
private String Supplier;
@Field("Loc/Imp")
private String Loc_Imp;
@Field("Mfg Lead Time")
private double Mfg_Lead_Time;
@Field("Transp Lead Time")
private double Transp_Lead_Time;
@Field("Total Lead Time")
private double Total_Lead_Time;
@Field("Currency")
private String Currency;
@Field("Price")
private double Price;
@Field("Families")
private String Families;
@Field("SubFamilies")
private String SubFamilies;
@Field("Part Description")
private String Part_Description;
@Field("MPC")
private String MPC;
@Field("Commodity")
private String Commodity;
public int getSrNo() {
	return srNo;
}
public void setSrNo(int srNo) {
	this.srNo = srNo;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getCM() {
	return CM;
}
public void setCM(String cM) {
	CM = cM;
}
public String getAV_PN() {
	return AV_PN;
}
public void setAV_PN(String aV_PN) {
	AV_PN = aV_PN;
}
public String getLevel() {
	return Level;
}
public void setLevel(String level) {
	Level = level;
}
public String getPart_Number() {
	return Part_Number;
}
public void setPart_Number(String part_Number) {
	Part_Number = part_Number;
}
public String getPosition() {
	return Position;
}
public void setPosition(String position) {
	Position = position;
}
public String getType() {
	return Type;
}
public void setType(String type) {
	Type = type;
}
public double getQtyPer() {
	return QtyPer;
}
public void setQtyPer(double qtyPer) {
	QtyPer = qtyPer;
}
public String getSupplier() {
	return Supplier;
}
public void setSupplier(String supplier) {
	Supplier = supplier;
}
public String getLoc_Imp() {
	return Loc_Imp;
}
public void setLoc_Imp(String loc_Imp) {
	Loc_Imp = loc_Imp;
}
public double getMfg_Lead_Time() {
	return Mfg_Lead_Time;
}
public void setMfg_Lead_Time(double mfg_Lead_Time) {
	Mfg_Lead_Time = mfg_Lead_Time;
}
public double getTransp_Lead_Time() {
	return Transp_Lead_Time;
}
public void setTransp_Lead_Time(double transp_Lead_Time) {
	Transp_Lead_Time = transp_Lead_Time;
}
public double getTotal_Lead_Time() {
	return Total_Lead_Time;
}
public void setTotal_Lead_Time(double total_Lead_Time) {
	Total_Lead_Time = total_Lead_Time;
}
public String getCurrency() {
	return Currency;
}
public void setCurrency(String currency) {
	Currency = currency;
}
public double getPrice() {
	return Price;
}
public void setPrice(double price) {
	Price = price;
}
public String getFamilies() {
	return Families;
}
public void setFamilies(String families) {
	Families = families;
}
public String getSubFamilies() {
	return SubFamilies;
}
public void setSubFamilies(String subFamilies) {
	SubFamilies = subFamilies;
}
public String getPart_Description() {
	return Part_Description;
}
public void setPart_Description(String part_Description) {
	Part_Description = part_Description;
}
public String getMPC() {
	return MPC;
}
public void setMPC(String mPC) {
	MPC = mPC;
}
public String getCommodity() {
	return Commodity;
}
public void setCommodity(String commodity) {
	Commodity = commodity;
}


}
