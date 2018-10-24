package com.io.pojos;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="fcst_print_les_supplies")
public class ForeCast_LES_Supplies {
	
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	@Column(name="SKU")
	private String APOProduct;	
	@Column(name="Model")
	private String	model;	
	@Column(name="Plt")
	private double plt;	
	@Column(name="`Size Code`")
	private double sizeCode;	
	@Column(name ="`T/S`")
	private String t_s;	
	@Column(name="Family")
	private String family;
	@Column(name="FcstQty")
	private double qty;
	@Column(name="FcstGD")
	private Date fileDate;
	@Column(name="FcstDate")
	private String forecastDate;
	@Column(name="WeeK")
	private String forecastWeek;
	
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
	public String getAPOProduct() {
		return APOProduct;
	}
	public void setAPOProduct(String aPOProduct) {
		APOProduct = aPOProduct;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	public double getPlt() {
		return plt;
	}
	public void setPlt(double plt) {
		this.plt = plt;
	}
	public double getSizeCode() {
		return sizeCode;
	}
	public void setSizeCode(double sizeCode) {
		this.sizeCode = sizeCode;
	}
	public String getT_s() {
		return t_s;
	}
	public void setT_s(String t_s) {
		this.t_s = t_s;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public Date getFileDate() {
		return fileDate;
	}
	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}
	public String getForecastDate() {
		return forecastDate;
	}
	public void setForecastDate(String forecastDate) {
		this.forecastDate = forecastDate;
	}
	public String getForecastWeek() {
		return forecastWeek;
	}
	public void setForecastWeek(String forecastWeek) {
		this.forecastWeek = forecastWeek;
	}
	@Override
	public String toString() {
		return "ForeCast_LES_Supplies [id1=" + id1 + ", APOProduct="
				+ APOProduct + ", model=" + model + ", plt=" + plt
				+ ", sizeCode=" + sizeCode + ", t_s=" + t_s + ", family="
				+ family + ", qty=" + qty + ", fileDate=" + fileDate
				+ ", forecastDate=" + forecastDate + ", forecastWeek="
				+ forecastWeek + ", fileType=" + fileType + ", category="
				+ category + "]";
	}
	
	

}
