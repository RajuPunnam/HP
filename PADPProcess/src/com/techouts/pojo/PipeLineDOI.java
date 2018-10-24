package com.techouts.pojo;

public class PipeLineDOI {
	private String _id;
	private String sku;
	private String part;
	private double qty;
	private double qty15;
	private double qty30;
	private double qty45;
	private double qty60;
	private double qty75;
	private double qty90;
	
	public PipeLineDOI(String sku,String part,double[] qtys) {
		super();
		this.sku = sku;
		this.part = part;
		this.qty = qtys[6];
		this.qty15 = qtys[0];
		this.qty30 = qtys[1];
		this.qty45 = qtys[2];
		this.qty60 = qtys[3];
		this.qty75 = qtys[4];
		this.qty90 = qtys[5];
	}
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
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
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public double getQty15() {
		return qty15;
	}
	public void setQty15(double qty15) {
		this.qty15 = qty15;
	}
	public double getQty30() {
		return qty30;
	}
	public void setQty30(double qty30) {
		this.qty30 = qty30;
	}
	public double getQty45() {
		return qty45;
	}
	public void setQty45(double qty45) {
		this.qty45 = qty45;
	}
	public double getQty60() {
		return qty60;
	}
	public void setQty60(double qty60) {
		this.qty60 = qty60;
	}
	public double getQty75() {
		return qty75;
	}
	public void setQty75(double qty75) {
		this.qty75 = qty75;
	}
	public double getQty90() {
		return qty90;
	}
	public void setQty90(double qty90) {
		this.qty90 = qty90;
	}	
}
