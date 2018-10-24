package com.techouts.pojo;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "BUFamilyAvailability")
@TypeAlias("BUFamilyAvailability")
public class BUFamilyAvailability {
	@Field("BU")
	private String bu;
	@Field("BUAvail")
	private double buAvail;
	@Field("Family")
	private String family;
	@Field("FamilyAvail")
	private double familyAvail;

	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public double getBuAvail() {
		return buAvail;
	}

	public void setBuAvail(double buAvail) {
		this.buAvail = buAvail;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public double getFamilyAvail() {
		return familyAvail;
	}

	public void setFamilyAvail(double familyAvail) {
		this.familyAvail = familyAvail;
	}

	@Override
	public String toString() {
		return "BUFamilyAvailability [bu=" + bu + ", buAvail=" + buAvail + ", family=" + family + ", familyAvail="
				+ familyAvail + "]";
	}
	
}
