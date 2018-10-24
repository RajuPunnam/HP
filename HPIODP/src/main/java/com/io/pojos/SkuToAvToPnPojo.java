package com.io.pojos;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class SkuToAvToPnPojo {
	@Id
	private String id;
	@Field("Family")
	private String family;
    @Field("SKU")
	private String SKU;
    @Field(value="SKU_Price")
    private double skuPrice;
    @Field("AV")
	private String AV;
    @Field(value="AV_Price")
    private double avPrice;
    @Field("PN")
	private String PN;
    @Field(value="PN_Price")
    private double PnPrice;
    @Field("Local/Imported")
	private String loc_Imp;
    @Field("MPC")
	private String MPC;
    @Field("PN_FcstQty")
	private double PFQ;
    @Field("FcstGD")
	private String FGD;
    @Field("FcstDate")
	private String FD;
    @Field("AV_FcstQty")
	private double avFQ;
    @Field("SKU_FcstQty")
	private double skuFQ;
    @Field("week")
	private int week;
    @Field("Type")
    private String type;
    @Field("Commodity")
    private String commodity;
    @Field("Supplier")
    private String supplier;
    @Field("FcstDate_Obj")
    private Date FcstDate;
    @Field("FcstGD_Obj")
	private Date FctGD;
    
    
	public Date getFcstDate() {
		return FcstDate;
	}
	public void setFcstDate(Date fcstDate) {
		FcstDate = fcstDate;
	}
	public Date getFctGD() {
		return FctGD;
	}
	public void setFctGD(Date fctGD) {
		FctGD = fctGD;
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
	public String getCommodity() {
		if(commodity!=null)return commodity.trim();
		else
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	public String getFamily() {
		if(family!=null)return family.trim();
		else
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getSKU() {
		if(SKU!=null)return SKU.trim();
		else
		return SKU;
	}
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	public String getAV() {
		if(AV!=null)return AV.trim();
		else
		return AV;
	}
	public void setAV(String aV) {
		AV = aV;
	}
	
	public String getPN() {
		if(PN!=null)return PN.trim();
		else
		return PN;
	}
	public void setPN(String pN) {
		PN = pN;
	}
	
	public String getLoc_Imp() {
		if(loc_Imp!=null)return loc_Imp.trim();
		else
		return loc_Imp;
	}
	public void setLoc_Imp(String loc_Imp) {
		this.loc_Imp = loc_Imp;
	}
	public String getMPC() {
		if(MPC!=null)return MPC.trim();
		else
		return MPC;
	}
	public void setMPC(String mPC) {
		MPC = mPC;
	}
	public double getPFQ() {
		return PFQ;
	}
	public void setPFQ(double pFQ) {
		PFQ = pFQ;
	}
	public String getFGD() {
		if(FGD!=null)return FGD.trim();
		else
		return FGD;
	}
	public void setFGD(String fGD) {
		FGD = fGD;
	}
	public String getFD() {
		if(FD!=null)return FD.trim();
		else
		return FD;
	}
	public void setFD(String fD) {
		FD = fD;
	}
	public double getAvFQ() {
		return avFQ;
	}
	public void setAvFQ(double avFQ) {
		this.avFQ = avFQ;
	}
	public double getSkuFQ() {
		return skuFQ;
	}
	public void setSkuFQ(double skuFQ) {
		this.skuFQ = skuFQ;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public String getType() {
		if(type!=null)return type.trim();
		else
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getId() {
		if(id!=null)return id.trim();
		else
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getSkuPrice() {
		return skuPrice;
	}
	public void setSkuPrice(double skuPrice) {
		this.skuPrice = skuPrice;
	}
	public double getAvPrice() {
		return avPrice;
	}
	public void setAvPrice(double avPrice) {
		this.avPrice = avPrice;
	}
	public double getPnPrice() {
		return PnPrice;
	}
	public void setPnPrice(double pnPrice) {
		PnPrice = pnPrice;
	}
	@Override
	public String toString() {
		return "SkuToAvToPnPojo [id=" + id + ", family=" + family + ", SKU=" + SKU + ", skuPrice=" + skuPrice + ", AV="
				+ AV + ", avPrice=" + avPrice + ", PN=" + PN + ", PnPrice=" + PnPrice + ", loc_Imp=" + loc_Imp
				+ ", MPC=" + MPC + ", PFQ=" + PFQ + ", FGD=" + FGD + ", FD=" + FD + ", avFQ=" + avFQ + ", skuFQ="
				+ skuFQ + ", week=" + week + ", type=" + type + ", commodity=" + commodity + ", supplier=" + supplier
				+ ", FcstDate=" + FcstDate + ", FctGD=" + FctGD + "]";
	}
    
	public SkuToAvToPnPojo() {
	}
	
	public SkuToAvToPnPojo(SkuToAvToPnPojo skuToAvToPnPojo) {
		this.AV = skuToAvToPnPojo.AV;
		this.avFQ = skuToAvToPnPojo.avFQ;
		this.avPrice = skuToAvToPnPojo.avPrice;
		this.commodity = skuToAvToPnPojo.commodity;
		this.family = skuToAvToPnPojo.family;
		this.FcstDate = skuToAvToPnPojo.FcstDate;
		this.FctGD = skuToAvToPnPojo.FctGD;
		this.FD = skuToAvToPnPojo.FD;
		this.FGD = skuToAvToPnPojo.FGD;
		this.loc_Imp = skuToAvToPnPojo.loc_Imp;
		this.MPC = skuToAvToPnPojo.MPC;
		this.PFQ = skuToAvToPnPojo.PFQ;
		this.PN = skuToAvToPnPojo.PN;
		this.PnPrice = skuToAvToPnPojo.PnPrice;
		this.SKU = skuToAvToPnPojo.SKU;
		this.skuPrice = skuToAvToPnPojo.skuPrice;
		this.supplier = skuToAvToPnPojo.supplier;
		this.type = skuToAvToPnPojo.type;
		this.week = skuToAvToPnPojo.week;
		this.skuFQ = skuToAvToPnPojo.skuFQ;
	}
    
	
}
