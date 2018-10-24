package com.inventory.finalpojos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="MASTERAV_PN_BOM_V11")
public class MasterBomPojo {

	@Id
	private String id;
	@Field("SR NO")
	private int srNo;
	@Field("BOM Date")
	private String bomDate;
	@Field("level")
	private String level;
	@Field("part number")
	private String partNumber;
	@Field("AV")
	private String av;
	@Field("part description")
	private String partDescription;
	@Field("Commodity")
	private String commodity;
	@Field("Commodity Group")
	private String commodityGroup;
	@Field("Local/Import")
	private String localImport;
	@Field("qty per")
	private String qtyPer;
	@Field("Position")
	private String position;
	@Field("type")
	private String type;
	@Field("MPC")
	private String mpc;
	@Field("eff date")
	private String effDate;
	@Field("end date")
	private String endDate;
	@Field("Last Level")
	private String lastLevel;
	@Field("PCBA")
	private String pcba;
	@Field("Supplier")
	private String supplier;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
		if(level !=null || level != "")
		return Integer.parseInt(level);
		else
			return 0;
	}
	public void setLevel(String level) {
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
	public String getPartDescription() {
		return partDescription;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
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
	public String getLocalImport() {
		return localImport;
	}
	public void setLocalImport(String localImport) {
		this.localImport = localImport;
	}
	public double getQtyPer() {
		if(qtyPer != "" || qtyPer != null)
		return Double.parseDouble(qtyPer);
		else
			return 0.0;
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
	public String getPcba() {
		return pcba;
	}
	public void setPcba(String pcba) {
		this.pcba = pcba;
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
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	@Override
	public String toString() {
		return "MasterBomPojo [id=" + id + ", srNo=" + srNo + ", bomDate=" + bomDate + ", level=" + level
				+ ", partNumber=" + partNumber + ", av=" + av + ", partDescription=" + partDescription + ", commodity="
				+ commodity + ", commodityGroup=" + commodityGroup + ", localImport=" + localImport + ", qtyPer="
				+ qtyPer + ", position=" + position + ", type=" + type + ", mpc=" + mpc + ", effDate=" + effDate
				+ ", endDate=" + endDate + ", lastLevel=" + lastLevel + "]";
	}
	
	
	
	
}
