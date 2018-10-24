package com.io.pojos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="fcst_print_status")

public class ForeCastStatus {

	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	@Column(name="FileName")
	private String fileName;
	@Column(name="UploadDate")
	private String uploadfileDate;
	@Column(name="FileDate")
	private Date maxDate;
	@Column(name="Count")
	private int totalRecords;
	
	
	
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public String getUploadfileDate() {
		return uploadfileDate;
	}
	public void setUploadfileDate(String uploadfileDate) {
		this.uploadfileDate = uploadfileDate;
	}
	public Date getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}
	@Override
	public String toString() {
		return "ForeCastStatus [fileName=" + fileName + ", uploadfileDate="
				+ uploadfileDate + ", maxDate=" + maxDate + "]";
	}
	
	
	
	
}

