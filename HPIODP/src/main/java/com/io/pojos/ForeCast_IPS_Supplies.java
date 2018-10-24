package com.io.pojos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="fcst_print_ips_supplies")
public class ForeCast_IPS_Supplies {

	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	@Column(name="SKU")
	private String apo_product;
	@Column(name="Initial")
	private String initial;
	@Column(name="FcstGD")
	private Date fcstGD;
	@Column(name="FcstDate")
	private String fcstDate;
	@Column(name="WEEK")
	private String forecastWeek;
	@Column(name="FcstQty")
	private double fcstQty;
	@Column(name="Type")
	private String fileType;
	
	@Column(name ="Category")
	private String category;
	
	
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public String getApo_product() {
		return apo_product;
	}
	public void setApo_product(String apo_product) {
		this.apo_product = apo_product;
	}
	public String getInitial() {
		return initial;
	}
	public void setInitial(String initial) {
		this.initial = initial;
	}
	public Date getFcstGD() {
		return fcstGD;
	}
	public void setFcstGD(Date fcstGD) {
		this.fcstGD = fcstGD;
	}
	public String getFcstDate() {
		return fcstDate;
	}
	public void setFcstDate(String fcstDate) {
		this.fcstDate = fcstDate;
	}
	public String getForecastWeek() {
		return forecastWeek;
	}
	public void setForecastWeek(String forecastWeek) {
		this.forecastWeek = forecastWeek;
	}
	public double getFcstQty() {
		return fcstQty;
	}
	public void setFcstQty(double fcstQty) {
		this.fcstQty = fcstQty;
	}
	@Override
	public String toString() {
		return "ForeCast_IPS_Supplies [id1=" + id1 + ", apo_product="
				+ apo_product + ", initial=" + initial + ", fcstGD=" + fcstGD
				+ ", fcstDate=" + fcstDate + ", forecastWeek=" + forecastWeek
				+ ", fcstQty=" + fcstQty + ", fileType=" + fileType
				+ ", category=" + category + "]";
	}
	
	
	
	
	
}
