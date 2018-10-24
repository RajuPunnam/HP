package com.techouts.dto;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

public class All_Skus_Availability {
	@Field("SKU")
	private String skuId;
	@Field("AVBL")
	private double skuAvailability;
	@Field("PipeLine_AVBL")
	private double pipeLineQuantity;
	@Field("New_Order_Delivery")
	private int newOrderDeliveryWeeks;
	@Field("Pipe_Line_Weeks")
	private int pipeLineWeeks;
	@Field("Configuration")
	private List<String> configurations;
	@Field("Shortage")
	private List<String> shortages;
	@Field("BU")
	private String bu;
	@Field("Family")
	private String family;
	@Field("pAvail15Days")
	private int pavail15Days;
	@Field("pAvail30Days")
	private int pavail30Days;
	@Field("pAvail45Days")
	private int pavail45Days;
	@Field("pAvail60Days")
	private int pavail60Days;
	@Field("pAvail75Days")
	private int pavail75Days;
	@Field("pAvail90Days")
	private int pavail90Days;
	@Field("skuDesc")
	private String description;
	
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPavail90Days() {
		return pavail90Days;
	}

	public void setPavail90Days(int pavail90Days) {
		this.pavail90Days = pavail90Days;
	}

	public int getPavail60Days() {
		return pavail60Days;
	}

	public void setPavail60Days(int pavail60Days) {
		this.pavail60Days = pavail60Days;
	}

	public int getPavail75Days() {
		return pavail75Days;
	}

	public void setPavail75Days(int pavail75Days) {
		this.pavail75Days = pavail75Days;
	}

	public int getPavail15Days() {
		return pavail15Days;
	}

	public void setPavail15Days(int pavail15Days) {
		this.pavail15Days = pavail15Days;
	}

	public int getPavail30Days() {
		return pavail30Days;
	}

	public void setPavail30Days(int pavail30Days) {
		this.pavail30Days = pavail30Days;
	}

	public int getPavail45Days() {
		return pavail45Days;
	}

	public void setPavail45Days(int pavail45Days) {
		this.pavail45Days = pavail45Days;
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

	public List<String> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(List<String> configurations) {
		this.configurations = configurations;
	}

	public List<String> getShortages() {
		return shortages;
	}

	public void setShortages(List<String> shortages) {
		this.shortages = shortages;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public double getSkuAvailability() {
		return skuAvailability;
	}

	public void setSkuAvailability(double skuAvailability) {
		this.skuAvailability = skuAvailability;
	}

	public double getPipeLineQuantity() {
		return pipeLineQuantity;
	}

	public void setPipeLineQuantity(double pipeLineQuantity) {
		this.pipeLineQuantity = pipeLineQuantity;
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

	@Override
	public String toString() {
		return "All_Skus_Availability [skuId=" + skuId + ", skuAvailability=" + skuAvailability + ", pipeLineQuantity="
				+ pipeLineQuantity + ", newOrderDeliveryWeeks=" + newOrderDeliveryWeeks + ", pipeLineWeeks="
				+ pipeLineWeeks + ", configurations=" + configurations + ", shortages=" + shortages + ", bu=" + bu
				+ ", family=" + family + ", pavail15Days=" + pavail15Days + ", pavail30Days=" + pavail30Days
				+ ", pavail45Days=" + pavail45Days + ", pavail60Days=" + pavail60Days + ", pavail75Days=" + pavail75Days
				+ ", pavail90Days=" + pavail90Days + ", description=" + description + "]";
	}

}
