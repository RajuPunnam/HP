package com.techouts.pojo;

import java.util.List;


public class SkuDetailsInfo implements Comparable<SkuDetailsInfo>{

	private String skuId;
	
	private int count;
	
	private int skuAvailability;
	
	private int pipeLineQuantity;
	
	private int newOrderDeliveryWeeks;
	
	private int pipeLineWeeks;
	
	private String family;
	
	private String businessUnit;
	
	private String businessGroup;
	
	private List<String> configuration;
	
	private List<String> shortage;

	private String imageType;
	
	private boolean showstar;
	
	private String simIndex;
	
	private int pavail15Days;

	private int pavail30Days;
 
	private int pavail45Days;
	
	private int pavail60Days;
	
	private int pavail75Days;
	
	private int pavail90Days;
	
	private int cartqty;
	
	private String description;
	
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCartqty() {
		return cartqty;
	}

	public void setCartqty(int cartqty) {
		this.cartqty = cartqty;
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

	private String isShortListed;
	
	public String getIsShortListed() {
		return isShortListed;
	}

	public void setIsShortListed(String isShortListed) {
		this.isShortListed = isShortListed;
	}

	@Override
	public String toString() {
		return "SkuDetailsInfo [skuId=" + skuId + ", count=" + count + ", skuAvailability=" + skuAvailability
				+ ", pipeLineQuantity=" + pipeLineQuantity + ", newOrderDeliveryWeeks=" + newOrderDeliveryWeeks
				+ ", pipeLineWeeks=" + pipeLineWeeks + ", family=" + family + ", businessUnit=" + businessUnit
				+ ", businessGroup=" + businessGroup + ", configuration=" + configuration + ", shortage=" + shortage
				+ ", imageType=" + imageType + ", showstar=" + showstar + ", simIndex=" + simIndex + ", pavail15Days="
				+ pavail15Days + ", pavail30Days=" + pavail30Days + ", pavail45Days=" + pavail45Days + ", pavail60Days="
				+ pavail60Days + ", pavail75Days=" + pavail75Days + ", pavail90Days=" + pavail90Days + ", cartqty="
				+ cartqty + ", description=" + description + ", isShortListed=" + isShortListed + "]";
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

	public String getSimIndex() {
		return simIndex;
	}

	public void setSimIndex(String simIndex) {
		this.simIndex = simIndex;
	}

	public boolean isShowstar() {
		return showstar;
	}

	public void setShowstar(boolean showstar) {
		this.showstar = showstar;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	

	public int getSkuAvailability() {
		return skuAvailability;
	}

	public void setSkuAvailability(int skuAvailability) {
		this.skuAvailability = skuAvailability;
	}

	public int getPipeLineQuantity() {
		return pipeLineQuantity;
	}

	public void setPipeLineQuantity(int pipeLineQuantity) {
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

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getBusinessGroup() {
		return businessGroup;
	}

	public void setBusinessGroup(String businessGroup) {
		this.businessGroup = businessGroup;
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

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public int compareTo(SkuDetailsInfo o) {
		// TODO Auto-generated method stub
		return o.skuAvailability - this.skuAvailability;
	}
}
