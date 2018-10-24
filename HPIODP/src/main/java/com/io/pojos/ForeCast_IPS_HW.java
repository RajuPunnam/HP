package com.io.pojos;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="fcst_print_ips_hw")
public class ForeCast_IPS_HW {
	
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	@Column(name="SKU")
	private String partNumber;
	@Column(name="Family")
	private String family;
	@Column(name="Country")
	private String country;
	@Column(name="Status")
	private String status;
	@Column(name="FcstGD")
	private Date file_Date;
	@Column(name="FcstDate")
	private String forecast_Date;
	@Column(name="FcstQty")
	private double qty;
	@Column(name="WeeK")
	private String foreCastWeek;
	@Column(name="`CRP/NRP`")
	private String crp_hrp;
	@Column(name ="Type")
	private String fileType;
	@Column(name ="Category")
	private String category;
	
	
	
	
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
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getCrp_hrp() {
		return crp_hrp;
	}
	public void setCrp_hrp(String crp_hrp) {
		this.crp_hrp = crp_hrp;
	}
	public String getForeCastWeek() {
		return foreCastWeek;
	}
	public void setForeCastWeek(String foreCastWeek) {
		this.foreCastWeek = foreCastWeek;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public Date getFile_Date() {
		return file_Date;
	}
	public void setFile_Date(Date file_Date) {
		this.file_Date = file_Date;
	}
	public String getForecast_Date() {
		return forecast_Date;
	}
	public void setForecast_Date(String forecast_Date) {
		this.forecast_Date = forecast_Date;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	@Override
	public String toString() {
		return "ForeCast_IPS_HW [id1=" + id1 + ", partNumber=" + partNumber
				+ ", family=" + family + ", country=" + country + ", status="
				+ status + ", file_Date=" + file_Date + ", forecast_Date="
				+ forecast_Date + ", qty=" + qty + ", foreCastWeek="
				+ foreCastWeek + ", crp_hrp=" + crp_hrp + ", fileType="
				+ fileType + ", category=" + category + "]";
	}
	
	


}

