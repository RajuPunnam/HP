package com.techouts.pojo;

import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "All_Skus_Availability")
@TypeAlias("OverAllSkusAvailability")
public class OverAllSkusAvailability {
	@Field("_id")
	private String _id;
	@Field("SKU")
	private String skuId;
	@Field("Family")
	private String family;
	@Field("BU")
	private String bu;
	@Field("AVBL")
	private double Availability;
	@Field("PipeLine_AVBL")
	private double pipeLineAvailability;
	@Field("New_Order_Delivery")
	private int newOrderDeliveryWeeks;
	@Field("Pipe_Line_Weeks")
	private int pipeLineWeeks;
	@Field("Configuration")
	private List<String> configuration;
	@Field("Shortage")
	private List<String> shortage;
	@Field("pAvail15Days")
	private double pAvail15Days;
	@Field("pAvail30Days")
	private double pAvail30Days;
	@Field("pAvail45Days")
	private double pAvail45Days;	
	@Field("pAvail60Days")
	private double pAvail60Days;	
	@Field("pAvail75Days")
	private double pAvail75Days;
	@Field("pAvail90Days")
	private double pAvail90Days;
	@Field("skuDesc")
	private String skuDesc;
	@Field("doiDate")
	private String doiDate;
	
	public String getDoiDate() {
		return doiDate;
	}

	public void setDoiDate(String doiDate) {
		this.doiDate = doiDate;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public double getAvailability() {
		return Availability;
	}

	public void setAvailability(double availability) {
		Availability = availability;
	}

	public double getPipeLineAvailability() {
		return pipeLineAvailability;
	}

	public void setPipeLineAvailability(double pipeLineAvailability) {
		this.pipeLineAvailability = pipeLineAvailability;
	}

	public int getNewOrderDeliveryWeeks() {
		return newOrderDeliveryWeeks;
	}

	public void setNewOrderDeliveryWeeks(int newOrderDeliveryWeeks) {
		this.newOrderDeliveryWeeks = newOrderDeliveryWeeks;
	}

	public int getPipeLineWeeks() {
		return pipeLineWeeks;
	}

	public void setPipeLineWeeks(int pipeLineWeeks) {
		this.pipeLineWeeks = pipeLineWeeks;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public List<String> getConfiguration() {
		return configuration;
	}

	public void setConfiguration(List<String> configuration) {
		this.configuration = configuration;
	}

	public List<String> getShortage() {
		return shortage;
	}

	public void setShortage(List<String> shortage) {
		this.shortage = shortage;
	}

	public double getpAvail15Days() {
		return pAvail15Days;
	}

	public void setpAvail15Days(double pAvail15Days) {
		this.pAvail15Days = pAvail15Days;
	}

	public double getpAvail30Days() {
		return pAvail30Days;
	}

	public void setpAvail30Days(double pAvail30Days) {
		this.pAvail30Days = pAvail30Days;
	}

	public double getpAvail45Days() {
		return pAvail45Days;
	}

	public void setpAvail45Days(double pAvail45Days) {
		this.pAvail45Days = pAvail45Days;
	}

	public double getpAvail60Days() {
		return pAvail60Days;
	}

	public void setpAvail60Days(double pAvail60Days) {
		this.pAvail60Days = pAvail60Days;
	}

	public double getpAvail75Days() {
		return pAvail75Days;
	}

	public void setpAvail75Days(double pAvail75Days) {
		this.pAvail75Days = pAvail75Days;
	}

	public double getpAvail90Days() {
		return pAvail90Days;
	}

	public void setpAvail90Days(double pAvail90Days) {
		this.pAvail90Days = pAvail90Days;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getSkuDesc() {
		return skuDesc;
	}

	public void setSkuDesc(String skuDesc) {
		this.skuDesc = skuDesc;
	}

	@Override
	public String toString() {
		return "OverAllSkusAvailability [_id=" + _id + ", skuId=" + skuId
				+ ", family=" + family + ", bu=" + bu + ", Availability="
				+ Availability + ", pipeLineAvailability="
				+ pipeLineAvailability + ", newOrderDeliveryWeeks="
				+ newOrderDeliveryWeeks + ", pipeLineWeeks=" + pipeLineWeeks
				+ ", configuration=" + configuration + ", shortage=" + shortage
				+ ", pAvail15Days=" + pAvail15Days + ", pAvail30Days="
				+ pAvail30Days + ", pAvail45Days=" + pAvail45Days
				+ ", pAvail60Days=" + pAvail60Days + ", pAvail75Days="
				+ pAvail75Days + ", pAvail90Days=" + pAvail90Days
				+ ", skuDesc=" + skuDesc + ", doiDate=" + doiDate + "]";
	}
	
}
