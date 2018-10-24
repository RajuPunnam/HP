package com.techouts.hp.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="BOM_FULL_REPORT_PERIOD_NEW")
public class BomFullReportPeriod
{
@Field("SR NO")
private int SR_NO;
@Field("Date")
private String date;
@Field("PL")
private String  PL;
@Field("family")
private String family;
@Field("sub family")
private String sub_family;
@Field("product number")
private String product_number;
@Field("prod description")
private String prod_description;
@Field("level")
private String level;
@Field("part number")
private String part_number;
@Field("part description")
private String part_description;
@Field("commodity")
private String commodity;
@Field("qty per")
private double qty_per;
@Field("position")
private double position;
@Field("MPC")
private String MPC;
@Field("RoHs")
private String RoHs;
@Field("PICA Code")
private String PICA_Code;
@Field("eff date")
private String eff_date;
@Field("end date")
private String end_date;
@Field("type")
private String type;
public int getSR_NO() {
	return SR_NO;
}
public void setSR_NO(int sR_NO) {
	SR_NO = sR_NO;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getPL() {
	return PL;
}
public void setPL(String pL) {
	PL = pL;
}
public String getFamily() {
	return family;
}
public void setFamily(String family) {
	this.family = family;
}
public String getSub_family() {
	return sub_family;
}
public void setSub_family(String sub_family) {
	this.sub_family = sub_family;
}
public String getProduct_number() {
	return product_number;
}
public void setProduct_number(String product_number) {
	this.product_number = product_number;
}
public String getProd_description() {
	return prod_description;
}
public void setProd_description(String prod_description) {
	this.prod_description = prod_description;
}
public String getLevel() {
	return level;
}
public void setLevel(String level) {
	this.level = level;
}
public String getPart_number() {
	return part_number;
}
public void setPart_number(String part_number) {
	this.part_number = part_number;
}
public String getPart_description() {
	return part_description;
}
public void setPart_description(String part_description) {
	this.part_description = part_description;
}
public String getCommodity() {
	return commodity;
}
public void setCommodity(String commodity) {
	this.commodity = commodity;
}
public double getQty_per() {
	return qty_per;
}
public void setQty_per(double qty_per) {
	this.qty_per = qty_per;
}
public String getMPC() {
	return MPC;
}
public double getPosition() {
	return position;
}
public String getPICA_Code() {
	return PICA_Code;
}
public void setPICA_Code(String pICA_Code) {
	PICA_Code = pICA_Code;
}
public void setPosition(double position) {
	this.position = position;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public void setMPC(String mPC) {
	MPC = mPC;
}
public String getRoHs() {
	return RoHs;
}
public void setRoHs(String roHs) {
	RoHs = roHs;
}
public String getEff_date() {
	return eff_date;
}
@Override
public String toString() {
	return "BomFullReportPeriod [SR_NO=" + SR_NO + ", date=" + date + ", PL="
			+ PL + ", family=" + family + ", sub_family=" + sub_family
			+ ", product_number=" + product_number + ", prod_description="
			+ prod_description + ", level=" + level + ", part_number="
			+ part_number + ", part_description=" + part_description
			+ ", commodity=" + commodity + ", qty_per=" + qty_per + ", MPC="
			+ MPC + ", RoHs=" + RoHs + ", eff_date=" + eff_date + ", end_date="
			+ end_date + "]";
}
public void setEff_date(String eff_date) {
	this.eff_date = eff_date;
}
public String getEnd_date() {
	return end_date;
}
public void setEnd_date(String end_date) {
	this.end_date = end_date;
}
}
