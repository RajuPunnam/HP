package com.mongo.mysql.pojos;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.inventory.utill.DoubleUtill;

@Entity
@Table(name="demand_30days_tbl")
@Access(value=AccessType.FIELD)
public class Demand_30Days {
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	@Column(name="`Part`")
	private String item;
	@Column(name="`Demand_30_Days`")
	private double demand30Days;
	@Column(name="`FileDate`")
	private Date fileDate;
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public double getDemand30Days() {
		return demand30Days;
	}
	public void setDemand30Days(Object obj) {
		this.demand30Days = DoubleUtill.getValue(obj);
	}
	public Date getFileDate() {
		return fileDate;
	}
	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	} 
	
	
	
}
