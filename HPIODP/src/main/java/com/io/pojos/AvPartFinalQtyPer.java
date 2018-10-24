package com.io.pojos;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection="AVPartFinalLevel")
public class AvPartFinalQtyPer {
	
	@Field("AV")
	private String av;
	@Field("PART_NUMBER")
	private String partNumber;
	@Field("FinalQty")
	private double finalQty;
	@Field("MPC")
	private String MPC;
	@Field("COMMODITY")
	private String Commodity;
	
	public String getMPC() {
		if(MPC!=null)
		return	MPC.trim();
		else
		return MPC;
	}
	public void setMPC(String mPC) {
		MPC = mPC;
	}
	public String getAv() {
		if(av!=null)
			return av.trim();
		else
		return av;
	}
	public void setAv(String av) {
		this.av = av;
	}
	public String getPartNumber() {
		if(partNumber!=null)
			return partNumber.trim();
		else
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public double getFinalQty() {
		return finalQty;
	}
	public void setFinalQty(double finalQty) {
		this.finalQty = finalQty;
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
