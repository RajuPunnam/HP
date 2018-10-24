package com.io.pojos;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="fcst_print_les_hw")
public class ForeCast_Les_HW {
	
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	@Column(name="Location")
	private String location;              
	@Column(name="SKU")
	private String 	planningProduct;    
	@Column(name="Country")
	private String country;             
	@Column(name="Category")
	private String category;             
	@Column(name="PL")
	private String pl;                    
	@Column(name="Family")
	private String family;                
	@Column(name="PPB")
	private String ppb;                   
	@Column(name="FcstGD")
	private Date fcstGD;
	@Column(name="FcstDate")
	private String FcstDate;
	@Column(name="WEEK")
	private String forecastWeek;
	@Column(name="FcstQty")
	private double FcstQty;               
	@Column(name="Type")
    private String type;   
	
	@Column(name ="Category1")
	private String category1;
	
	
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPlanningProduct() {
		return planningProduct;
	}
	public void setPlanningProduct(String planningProduct) {
		this.planningProduct = planningProduct;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCategory1() {
		return category1;
	}
	public void setCategory1(String category1) {
		this.category1 = category1;
	}
	public String getPl() {
		return pl;
	}
	public void setPl(String pl) {
		this.pl = pl;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getPpb() {
		return ppb;
	}
	public void setPpb(String ppb) {
		this.ppb = ppb;
	}
	public Date getFcstGD() {
		return fcstGD;
	}
	public void setFcstGD(Date fcstGD) {
		this.fcstGD = fcstGD;
	}
	public String getFcstDate() {
		return FcstDate;
	}
	public void setFcstDate(String fcstDate) {
		FcstDate = fcstDate;
	}
	public String getForecastWeek() {
		return forecastWeek;
	}
	public void setForecastWeek(String forecastWeek) {
		this.forecastWeek = forecastWeek;
	}
	public double getFcstQty() {
		return FcstQty;
	}
	public void setFcstQty(double fcstQty) {
		FcstQty = fcstQty;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "ForeCast_Les_HW [id1=" + id1 + ", location=" + location
				+ ", planningProduct=" + planningProduct + ", country="
				+ country + ", category1=" + category1 + ", pl=" + pl
				+ ", family=" + family + ", ppb=" + ppb + ", fcstGD=" + fcstGD
				+ ", FcstDate=" + FcstDate + ", forecastWeek=" + forecastWeek
				+ ", FcstQty=" + FcstQty + ", type=" + type + ", category="
				+ category + "]";
	}
	
	
	

}

