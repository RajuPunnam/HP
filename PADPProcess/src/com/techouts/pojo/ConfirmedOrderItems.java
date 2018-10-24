package com.techouts.pojo;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "ConfirmedOrderItems")
@TypeAlias("ConfirmedOrderItems")
public class ConfirmedOrderItems implements Serializable {

	@Id
	private String id;
	@Field("orderId")
	private String orderId;

	@Field("familyKey")
	private String familyKey;
	@Field("avId")
	private String avId;
	@Field("quantity")
	private Integer quantity;
	
	
	//Setters and Getters
	public String getId() {
	    return id;
	}
	public void setId(String id) {
	    this.id = id;
	}
	public String getOrderId() {
	    return orderId;
	}
	public void setOrderId(String orderId) {
	    this.orderId = orderId;
	}
	public String getFamilyKey() {
	    return familyKey;
	}
	public void setFamilyKey(String familyKey) {
	    this.familyKey = familyKey;
	}
	public String getAvId() {
	    return avId;
	}
	public void setAvId(String avId) {
	    this.avId = avId;
	}
	public Integer getQuantity() {
	    return quantity;
	}
	public void setQuantity(Integer quantity) {
	    this.quantity = quantity;
	}
	
	
	

}
