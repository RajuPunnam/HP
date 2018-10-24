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

@Document(collection="MASTERAV_PN_BOM_V11")
@Entity
@Table(name="masterav_pn_bom_v11_tbl")
@Access(value=AccessType.FIELD)
public class MASTERAV_PN_BOM_V11 {
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	@Column(name="`SR NO`")
	@Field("SR NO")
	private int srNo;
	@Field("BOM Date")
	@Column(name="`BOM Date`") 
	private String bomDate;
	@Field("level")
	@Column(name="level") 
	private int level;
	@Field("part number")
	@Column(name="`part number`") 
	private String partNumber;
	@Field("AV")
	@Column(name="AV") 
	private String av;
	@Field("part description")
	@Column(name="`part description`") 
	private String partDesc;
	@Field("Commodity")
	@Column(name="`Commodity`") 
	private String commodity;
	@Field("Commodity Group")
	@Column(name="`Commodity Group`") 
	private String commodityGroup;
	@Field("qty per")
	@Column(name="`qty per`") 
	private String qtyPer;
	@Field("Position")
	@Column(name="`Position`")
	private String position;
	@Field("type")
	@Column(name="`type`")
	private String type;
	@Field("MPC")
	@Column(name="`MPC`") 
	private String mpc;
	@Field("eff date")
	@Column(name="`eff date`")
	private String effDate;
	@Field("end date")
	@Column(name="`end date`") 
	private String endDate;
	@Field("Last Level")
	@Column(name="`Last Level`")
	private String lastLevel;
	@Field("FileName")
	@Column(name="`FileName`")
	private String fileName;
	@Field("PCBA")
	@Column(name="`PCBA`") 
	private String pcba;
	@Field("Supplier")
	@Column(name="`Supplier`")
	private String supplier;
	
	
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	public String getBomDate() {
		return bomDate;
	}
	public void setBomDate(String bomDate) {
		this.bomDate = bomDate;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getAv() {
		return av;
	}
	public void setAv(String av) {
		this.av = av;
	}
	public String getPartDesc() {
		return partDesc;
	}
	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}
	public String getCommodity() {
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	public String getCommodityGroup() {
		return commodityGroup;
	}
	public void setCommodityGroup(String commodityGroup) {
		this.commodityGroup = commodityGroup;
	}
	public String getQtyPer() {
		return qtyPer;
	}
	public void setQtyPer(String qtyPer) {
		this.qtyPer = qtyPer;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMpc() {
		return mpc;
	}
	public void setMpc(String mpc) {
		this.mpc = mpc;
	}
	public String getEffDate() {
		return effDate;
	}
	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getLastLevel() {
		return lastLevel;
	}
	public void setLastLevel(String lastLevel) {
		this.lastLevel = lastLevel;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPcba() {
		return pcba;
	}
	public void setPcba(String pcba) {
		this.pcba = pcba;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	
}
