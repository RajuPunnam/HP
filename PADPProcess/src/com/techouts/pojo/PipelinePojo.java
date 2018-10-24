package com.techouts.pojo;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="PipeLineAvAvbail")
public class PipelinePojo 
{
	private String _id;
	@Field("sku")
	private String sku;
	@Field("avId")
	private String avId;
	@Field("avbail")
	private double avbail;
	@Field("avbail15")
	private double avbail15;
	@Field("avbail30")
	private double avbail30;
	@Field("avbail45")
	private double avbail45;
	@Field("avbail60")
	private double avbail60;
	@Field("avbail75")
	private double avbail75;
	@Field("avbail90")
	private double avbail90;
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getAvId() {
		return avId;
	}
	public void setAvId(String avId) {
		this.avId = avId;
	}
	public double getAvbail() {
		return avbail;
	}
	public void setAvbail(double avbail) {
		this.avbail = avbail;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public double getAvbail15() {
		return avbail15;
	}
	public void setAvbail15(double avbail15) {
		this.avbail15 = avbail15;
	}
	public double getAvbail30() {
		return avbail30;
	}
	public void setAvbail30(double avbail30) {
		this.avbail30 = avbail30;
	}
	public double getAvbail45() {
		return avbail45;
	}
	public void setAvbail45(double avbail45) {
		this.avbail45 = avbail45;
	}
	public double getAvbail60() {
		return avbail60;
	}
	public void setAvbail60(double avbail60) {
		this.avbail60 = avbail60;
	}
	public double getAvbail75() {
		return avbail75;
	}
	public void setAvbail75(double avbail75) {
		this.avbail75 = avbail75;
	}
	public double getAvbail90() {
		return avbail90;
	}
	public void setAvbail90(double avbail90) {
		this.avbail90 = avbail90;
	}
	
	
	
}
