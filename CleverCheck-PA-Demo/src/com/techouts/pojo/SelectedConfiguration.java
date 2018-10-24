package com.techouts.pojo;

public class SelectedConfiguration {

	private String familyKey;
	//private Integer promise;
	
	private Integer fgiAvailQuantity = 0;
	private Integer matAvailQuantity = 0;
	private Integer fgiMatQuantity = 0;
	private Integer orderTime = 0;
	
	
	private String preConfiguredSku = "";
	private String selectedSKUID = "";
	//private Integer quantity;
	
	
	
	public String getFamilyKey() {
		return familyKey;
	}
	public void setFamilyKey(String familyKey) {
		this.familyKey = familyKey;
	}
	public Integer getOrderTime() {
	    return orderTime;
	}
	public void setOrderTime(Integer orderTime) {
	    this.orderTime = orderTime;
	}
	public Integer getMatAvailQuantity() {
		return matAvailQuantity;
	}
	public void setMatAvailQuantity(Integer matAvailQuantity) {
		this.matAvailQuantity = matAvailQuantity;
	}
	public String getPreConfiguredSku() {
		return preConfiguredSku;
	}
	public void setPreConfiguredSku(String preConfiguredSku) {
		this.preConfiguredSku = preConfiguredSku;
	}
	public Integer getFgiAvailQuantity() {
		return fgiAvailQuantity;
	}
	public void setFgiAvailQuantity(Integer fgiAvailQuantity) {
		this.fgiAvailQuantity = fgiAvailQuantity;
	}
	public Integer getFgiMatQuantity() {
		//return fgiAvailQuantity + fgiMatQuantity;
		return fgiMatQuantity;
	}
	public void setFgiMatQuantity(Integer fgiMatQuantity) {
		this.fgiMatQuantity = fgiMatQuantity;
	}
	public String getSelectedSKUID() {
	    return selectedSKUID;
	}
	public void setSelectedSKUID(String selectedSKUID) {
	    this.selectedSKUID = selectedSKUID;
	}
	
	
	
}
