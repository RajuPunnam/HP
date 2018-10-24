package com.mongo.mysql.pojos;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="Combined_PriceMaster_Corrected")
public class PriceMaster {
	@Field("Item") 
	private String item;
	@Field("Date") 
	private String date;
	@Field("Type") 
	private String type;
	@Field("Price(US$)") 
	private Double dollarPrice;
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getDollarPrice() {
		return dollarPrice;
	}
	public void setDollarPrice(Double dollarPrice) {
		this.dollarPrice = dollarPrice;
	}

}
