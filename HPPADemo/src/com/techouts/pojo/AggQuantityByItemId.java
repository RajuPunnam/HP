package com.techouts.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="AggQuantityByItemId")
@TypeAlias("AggQuantityByItemId")
public class AggQuantityByItemId {

	@Id
	private String item;
	private double totalDemandToOrder;
	
	/*@Field("Total_Demand_to_order")
	private double totalDemand;
	*/
	
	//Setters and Getters
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	
	public double getTotalDemandToOrder() {
		return totalDemandToOrder;
	}
	public void setTotalDemandToOrder(double totalDemandToOrder) {
		this.totalDemandToOrder = totalDemandToOrder;
	}
	
	
	
}
