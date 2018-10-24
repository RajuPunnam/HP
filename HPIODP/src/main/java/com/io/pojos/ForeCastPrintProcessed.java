package com.io.pojos;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="fcst_print_processed")
public class ForeCastPrintProcessed {
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	@Column(name="SKU")
	private String sku;
	@Column(name="FcstDate")
	private String fcstDate;
	@Column(name="FcstGD")
	private Date fcstGD;
	@Column(name="week")
	private String week;
	@Column(name="Type")
	private String type;
	@Column(name="Fcst_Qty")
	private double qty;
	@Column(name="Category")
	private String category;
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getFcstDate() {
		return fcstDate;
	}
	public void setFcstDate(String fcstDate) {
		this.fcstDate = fcstDate;
	}
	
	public Date getFcstGD() {
		return fcstGD;
	}
	public void setFcstGD(Date fcstGD) {
		this.fcstGD = fcstGD;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "ForeCastPrintProcessed [id1=" + id1 + ", sku=" + sku
				+ ", fcstDate=" + fcstDate + ", fcstGD=" + fcstGD + ", week="
				+ week + ", type=" + type + ", qty=" + qty + ", category="
				+ category + "]";
	}
	
}
