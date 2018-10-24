package com.techouts.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "OrdersPipeLine")
public class OpenOrderPipeLine {
	@Id
	private String id;
	@Field("Date")
	private String date;
	@Field("partId")
	private String partNumber;
	@Field("Qty")
	private double qty;
	@Field("Qty_Days15")
	private double qty_days15;

	@Field("Qty_Days30")
	private double qty_days30;

	@Field("Qty_Days45")
	private double qty_days45;

	@Field("Qty_Days60")
	private double qty_days60;

	@Field("Qty_Days75")
	private double qty_days75;

	@Field("Qty_Days90")
	private double qty_days90;

	public double getQty_days60() {
		return qty_days60;
	}

	public void setQty_days60(double qty_days60) {
		this.qty_days60 = qty_days60;
	}

	public double getQty_days75() {
		return qty_days75;
	}

	public void setQty_days75(double qty_days75) {
		this.qty_days75 = qty_days75;
	}

	public double getQty_days90() {
		return qty_days90;
	}

	public void setQty_days90(double qty_days90) {
		this.qty_days90 = qty_days90;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPartNumber() {

		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public double getQty_days15() {
		return qty_days15;
	}

	public void setQty_days15(double qty_days15) {
		this.qty_days15 = qty_days15;
	}

	public double getQty_days30() {
		return qty_days30;
	}

	public void setQty_days30(double qty_days30) {
		this.qty_days30 = qty_days30;
	}

	public double getQty_days45() {
		return qty_days45;
	}

	public void setQty_days45(double qty_days45) {
		this.qty_days45 = qty_days45;
	}

}
