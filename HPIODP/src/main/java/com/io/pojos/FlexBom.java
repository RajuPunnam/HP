package com.io.pojos;

import org.springframework.data.mongodb.core.mapping.Field;

public class FlexBom {

	@Field("SR_NO")
	private int srno;
	@Field("AV")
	private String av;
	@Field("PART_NUMBER")
	private String partNumber;
	@Field("BOM_LEVEL")
	private String level;
	@Field("QTY_PER")
	private double qtyPer;
	@Field("FinalQty")
	private int finalQty;
	private double allocatedQty;
	private double qtyPerAv;
	@Field("BOM_TYPE")
	private String bomTypep;
	@Field("MPC")
	private String mpc;
	@Field("COMMODITY")
	private String Commodity;

	public int getSrno() {
		return srno;
	}

	public void setSrno(int srno) {
		this.srno = srno;
	}

	public String getAv() {
		if(av!=null)return av.trim();
		else
		return av;
	}

	public void setAv(String av) {
		this.av = av;
	}

	public String getPartNumber() {
		if(partNumber!=null)return partNumber.trim();
		else
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public int getLevel() {
		if (level.equals("0.1")) {
			return Integer.parseInt(level.replace("0.", ""));
		} else {
			if (level.contains("."))
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

	public int getFinalQty() {
		return finalQty;
	}

	public void setFinalQty(int finalQty) {
		this.finalQty = finalQty;
	}

	public String getBomTypep() {
		if(bomTypep!=null)
			return bomTypep.trim();
		else
		return bomTypep;
	}

	public void setBomTypep(String bomTypep) {
		this.bomTypep = bomTypep;
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

	public String getCommodity() {
		if(Commodity!=null)
			return Commodity.trim();
		else
		return Commodity;
	}

	public void setCommodity(String commodity) {
		Commodity = commodity;
	}

}
