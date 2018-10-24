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

@Document(collection="MASTERSKUAVBOM_PA")
@Entity
@Table(name="masterskuavbom_pa")
@Access(value=AccessType.FIELD)
public class MASTERSKUAVBOM_PA {
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	  private int id1;
	  @Field ("sku")
	  @Column(name="sku")
	  private String sku;
	  @Field ("av" )
	  @Column(name="av")
	  private String av;
      @Field ("family")
      @Column(name="family")
      private String family;
	  @Field ("pl")
	  @Column(name="pl")
	  private String pl;
	  @Field ("bomName")
	  @Column(name="bomName")
	  private String bomName;
	  @Field ("bu")
	  @Column(name="bu")
	  private String bu;
	  
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
	public String getAv() {
		return av;
	}
	public void setAv(String av) {
		this.av = av;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getPl() {
		return pl;
	}
	public void setPl(String pl) {
		this.pl = pl;
	}
	public String getBomName() {
		return bomName;
	}
	public void setBomName(String bomName) {
		this.bomName = bomName;
	}
	public String getBu() {
		return bu;
	}
	public void setBu(String bu) {
		this.bu = bu;
	}
	  
	  
	  
}
