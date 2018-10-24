package com.mongo.mysql.pojos;
import java.io.Serializable;

import javax.persistence.AccessType;
import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.inventory.utill.DateUtil;
@Document(collection="OPEN_ORDER_CLEAN")
@Entity
@Table(name="open_orders_clean_tbl")
@Access(value=AccessType.FIELD)
public class Clean implements Serializable{
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	@Column(name="FILE_DATE")
	@Field("File Date")
	private String fileDate;
	@Field("PO Received Date")
	@Column(name="PO_RECEIVED_DATE")
	private String poRecDate;
	@Field("PO")
	@Column(name="PO")
	private String po;
	@Field("SKU")
	@Column(name="SKU")
	private String sku;
	@Field("Customer")
	@Column(name="CUSTOMER")
	private String customer;
	@Field("PL")
	@Column(name="PL")
	private String pl;
	@Field("Family")
	@Column(name="FAMILY")
	private String family;
	@Field("ETS")
	@Column(name="ETS")
	private String ets;
	@Field("Total")
	@Column(name="TOTAL")
	private String total;
	@Field("Adjusted Date")
	@Column(name="ADJUSTED_DATE")
	private String adjustedDate;
	
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public String getFileDate() {
		return DateUtil.getRequiredDateFornate(fileDate);
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public String getPoRecDate() {
		return poRecDate;
	}
	public void setPoRecDate(String poRecDate) {
		this.poRecDate = poRecDate;
	}
	public String getPo() {
		return po;
	}
	public void setPo(String po) {
		this.po = po;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
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
	public String getEts() {
		return ets;
	}
	public void setEts(String ets) {
		this.ets = ets;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getAdjustedDate() {
		return adjustedDate;
	}
	public void setAdjustedDate(String adjustedDate) {
		this.adjustedDate = adjustedDate;
	}
	@Override
	public String toString() {
		return "Clean [id=" + id1 + ", fileDate=" + fileDate + ", poRecDate="
				+ poRecDate + ", po=" + po + ", sku=" + sku + ", customer="
				+ customer + ", pl=" + pl + ", family=" + family + ", ets="
				+ ets + ", total=" + total + ", adjustedDate=" + adjustedDate
				+ "]";
	}
	
	
}
