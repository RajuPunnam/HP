package com.techouts.pojo;

public class SkuPartInventory {
private String sku;
private String part;
private double milage;
public String getSku() {
	return sku;
}
public void setSku(String sku) {
	this.sku = sku;
}
public String getPart() {
	return part;
}
public void setPart(String part) {
	this.part = part;
}
public double getMilage() {
	return milage;
}
public void setMilage(double milage) {
	this.milage = milage;
}

@Override
public String toString() {
	return "SkuPartInventory [sku=" + sku + ", part=" + part + ", milage="
			+ milage + "]";
}



}
