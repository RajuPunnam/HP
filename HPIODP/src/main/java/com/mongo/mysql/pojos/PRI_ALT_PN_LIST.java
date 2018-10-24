package com.mongo.mysql.pojos;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection="PRI_ALT_PN_LIST")
@Entity
@Table(name="pri_alt_list_tbl")
@Access(value=AccessType.FIELD)
public class PRI_ALT_PN_LIST {
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	  @Field("PRI")
	  @Column(name="PRI")
	  private String pri;
	  @Field("ALT")
	  @Column(name="ALT")
	  private String alt;
	  @Field("PN_SOURCE")
	  @Column(name="PN_SOURCE")
	  private String pnSource;
	  @Field("PRI_DESC")
	  @Column(name="PRI_DESC")
	  private String priDesc;
	  @Field("ALT_DESC")
	  @Column(name="ALT_DESC")
	  private String altDesc;
	  @Field("PRI_COMMODITY")
	  @Column(name="PRI_COMMODITY")
	  private String priCommodity;
	  @Field("ALT_COMMODITY")
	  @Column(name="ALT_COMMODITY")
	  private String altCommodity;
	  
	  
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public String getPri() {
		return pri;
	}
	public void setPri(String pri) {
		this.pri = pri;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public String getPnSource() {
		return pnSource;
	}
	public void setPnSource(String pnSource) {
		this.pnSource = pnSource;
	}
	public String getPriDesc() {
		return priDesc;
	}
	public void setPriDesc(String priDesc) {
		this.priDesc = priDesc;
	}
	public String getAltDesc() {
		return altDesc;
	}
	public void setAltDesc(String altDesc) {
		this.altDesc = altDesc;
	}
	public String getPriCommodity() {
		return priCommodity;
	}
	public void setPriCommodity(String priCommodity) {
		this.priCommodity = priCommodity;
	}
	public String getAltCommodity() {
		return altCommodity;
	}
	public void setAltCommodity(String altCommodity) {
		this.altCommodity = altCommodity;
	}
	  
	  
}
