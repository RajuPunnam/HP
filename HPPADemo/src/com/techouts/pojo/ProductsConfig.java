package com.techouts.pojo;

import java.util.ArrayList;
import java.util.List;

public class ProductsConfig implements Cloneable {

	private String keyFeature;
	private List<FamilyInfoFinal> configuration;
	private String selectedConfiguration;
	
	//private List<AvailParts> availParts;
	//private String selectedAvailPart;
	
	private String description;
	private String cpuDesc;
	private Integer availability;
	private Integer leadTime;
	private double cost;
	private String comment;
	private String miscComment;
	private String local_Import;
	
	private int isEmpty;
	private int isInactive;
	
	
	
	public ProductsConfig() {
	    configuration = new ArrayList<FamilyInfoFinal>();
	    selectedConfiguration = "";
	}
	public ProductsConfig(String keyFeature){
		this.keyFeature = keyFeature;
		configuration = new ArrayList<FamilyInfoFinal>();
		selectedConfiguration = "";
	}
	
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}
	
	public String getLocal_Import() {
		return local_Import;
	}
	public void setLocal_Import(String local_Import) {
		this.local_Import = local_Import;
	}
	
	public String getKeyFeature() {
		return keyFeature;
	}
	public void setKeyFeature(String keyFeature) {
		this.keyFeature = keyFeature;
	}
	
	
	
	public List<FamilyInfoFinal> getConfiguration() {
	    return configuration;
	}
	public void setConfiguration(List<FamilyInfoFinal> configuration) {
	    this.configuration = configuration;
	}
	public String getSelectedConfiguration() {
	    return selectedConfiguration;
	}
	public void setSelectedConfiguration(String selectedConfiguration) {
	    this.selectedConfiguration = selectedConfiguration;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getAvailability() {
		return availability;
	}
	public void setAvailability(Integer availability) {
		this.availability = availability;
	}
	
	
	public Integer getLeadTime() {
	    return leadTime;
	}
	public void setLeadTime(Integer leadTime) {
	    this.leadTime = leadTime;
	}
	
	public double getCost() {
	    return cost;
	}
	public void setCost(double cost) {
	    this.cost = cost;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getMiscComment() {
	    return miscComment;
	}
	public void setMiscComment(String miscComment) {
	    this.miscComment = miscComment;
	}
	public int getIsEmpty() {
	    return isEmpty;
	}
	public void setIsEmpty(int isEmpty) {
	    this.isEmpty = isEmpty;
	}
	public String getCpuDesc() {
	    return cpuDesc;
	}
	public void setCpuDesc(String cpuDesc) {
	    this.cpuDesc = cpuDesc;
	}
	public int getIsInactive() {
	    return isInactive;
	}
	public void setIsInactive(int isInactive) {
	    this.isInactive = isInactive;
	}
	@Override
	public String toString() {
		return "ProductsConfig [keyFeature=" + keyFeature + ", configuration="
				+ configuration + ", selectedConfiguration="
				+ selectedConfiguration + ", description=" + description
				+ ", cpuDesc=" + cpuDesc + ", availability=" + availability
				+ ", leadTime=" + leadTime + ", cost=" + cost + ", comment="
				+ comment + ", miscComment=" + miscComment + ", local_Import="
				+ local_Import + ", isEmpty=" + isEmpty + ", isInactive="
				+ isInactive + "]";
	}
	
	 
	
}
