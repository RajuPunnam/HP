package com.io.pojos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

//@Document(collection="MASTERAV_PN_BOM_V11")
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
	@Field("Supplier")
	private String supplier;
	
	public String getId() {
		if(id!=null)return id.trim();
		else
		return id;
	}
	public String getSupplier() {
		if(supplier!=null)
			return supplier.trim();
		else
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
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
		if(bomDate!=null)return bomDate.trim();
		else
		return bomDate;
	}
	public void setBomDate(String bomDate) {
		this.bomDate = bomDate;
	}
	public int getLevel() {
		try {
			return Integer.parseInt(level);

		} catch (Exception e) {
			return 0;
		}
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getPartNumber() {
		if(partNumber!=null)return partNumber.trim();
		else
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getAv() {
		if(av!=null)return av.trim();
		else
		return av;
	}
	public void setAv(String av) {
		this.av = av;
	}
	public String getPartDescription() {
		if(partDescription!=null)return partDescription.trim();
		else
		return partDescription;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public String getCommodity() {
		if(commodity!=null)return commodity.trim();
		else
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	public String getCommodityGroup() {
		if(commodityGroup!=null)
			return commodityGroup.trim();
		else
		return commodityGroup;
	}
	public void setCommodityGroup(String commodityGroup) {
		this.commodityGroup = commodityGroup;
	}
	public String getLocalImport() {
		if(localImport!=null)return localImport.trim();
		else
		return localImport;
	}
	public void setLocalImport(String localImport) {
		this.localImport = localImport;
	}

	public double getQtyPer() {
		try {
			return Double.parseDouble(qtyPer);
		} catch (Exception e) {
			return 0.0;
		}
	}
	public void setQtyPer(String qtyPer) {
		this.qtyPer = qtyPer;
	}
	public String getPosition() {
		if(position!=null)
			return position.trim();
		else
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getType() {
		if(type!=null)
			return type.trim();
		else
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMpc() {
		if(mpc!=null)
			return mpc.trim();
		else
		return mpc;
	}
	public void setMpc(String mpc) {
		this.mpc = mpc;
	}
	public String getEffDate() {
		if(effDate!=null)return effDate.trim();
		else
		return effDate;
	}
	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}
	public String getEndDate() {
		if(endDate!=null)return endDate.trim();
		else
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getLastLevel() {
		if(lastLevel!=null)return lastLevel.trim();
		else
		return lastLevel;
	}
	public void setLastLevel(String lastLevel) {
		this.lastLevel = lastLevel;
	}
	@Override
	public String toString() {
		return "MasterBomPojo [id=" + id + ", srNo=" + srNo + ", bomDate="
				+ bomDate + ", level=" + level + ", partNumber=" + partNumber
				+ ", av=" + av + ", partDescription=" + partDescription
				+ ", commodity=" + commodity + ", commodityGroup="
				+ commodityGroup + ", localImport=" + localImport + ", qtyPer="
				+ qtyPer + ", position=" + position + ", type=" + type
				+ ", mpc=" + mpc + ", effDate=" + effDate + ", endDate="
				+ endDate + ", lastLevel=" + lastLevel + ", supplier="
				+ supplier + "]";
	}
	
	
	
	
}
