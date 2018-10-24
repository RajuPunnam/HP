/**
 * 
 */
package com.techouts.hp.supplies.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author TO-OWDG-02
 *
 */
@Entity
@Table(name = "doi-print_raw_tbl")
public class SuppliesDOI implements Serializable {
	@Id
	@GenericGenerator(name = "kaugen", strategy = "increment")
	@GeneratedValue(generator = "kaugen")
	@Column(name = "ID", unique = true, nullable = false)
	private int ID;
	@Column(name = "`BONE PILE`")
	private int BONE_PILE;
	@Column(name = "BU")
	private String baseunit;
	@Column(name = "category")
	private String category;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "FileDate", columnDefinition = "DATE")
	private Date fileDate;
	@Column(name = "ITEM")
	private String item;
	@Column(name = "`MAKE_BUY`")
	private String makebuy;
	@Column(name = "`MRB/ RTV`")
	private int MRB_RTV;
	@Column(name = "`NETTABLE_INVENTORY`")
	private int nettableInventory;
	@Column(name = "OO")
	private String OO;
	@Column(name = "SKU")
	private String sku;
	@Column(name = "`Total Demand`")
	private int totalDemand;
	@Column(name = "`TOTAL_STK`")
	private int TOTAL_STK;
	@Column(name = "Transit")
	private int transit;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getBONE_PILE() {
		return BONE_PILE;
	}

	public void setBONE_PILE(int bONE_PILE) {
		BONE_PILE = bONE_PILE;
	}

	public String getBaseunit() {
		return baseunit;
	}

	public void setBaseunit(String baseunit) {
		this.baseunit = baseunit;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getFileDate() {
		return fileDate;
	}

	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getMakebuy() {
		return makebuy;
	}

	public void setMakebuy(String makebuy) {
		this.makebuy = makebuy;
	}

	public int getMRB_RTV() {
		return MRB_RTV;
	}

	public void setMRB_RTV(int mRB_RTV) {
		MRB_RTV = mRB_RTV;
	}

	public int getNettableInventory() {
		return nettableInventory;
	}

	public void setNettableInventory(int nettableInventory) {
		this.nettableInventory = nettableInventory;
	}

	public String getOO() {
		return OO;
	}

	public void setOO(String oO) {
		OO = oO;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getTotalDemand() {
		return totalDemand;
	}

	public void setTotalDemand(int totalDemand) {
		this.totalDemand = totalDemand;
	}

	public int getTOTAL_STK() {
		return TOTAL_STK;
	}

	public void setTOTAL_STK(int tOTAL_STK) {
		TOTAL_STK = tOTAL_STK;
	}

	public int getTransit() {
		return transit;
	}

	public void setTransit(int transit) {
		this.transit = transit;
	}

}
