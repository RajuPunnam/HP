package com.techouts.pojo;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "ConfirmedOrder")
@TypeAlias("ConfirmedOrder")
public class ConfirmedOrder implements Serializable {

	@Id
	private String id;
	@Field("orderId")
	private String orderId;
	
	@Field("salesManId")
	private String salesManId;
	@Field("customer")
	private String customer;
	@Field("orderDate")
	private long orderDate;
	@Field("deliveryDate")
	private long deliveryDate;

	@Field("familyKey")
	private String familyKey;
	@Field("sku")
	private String sku;
	@Field("fgi")
	private int fgi;
	@Field("quantity")
	private Integer quantity;
	@Field("blockTime")
	private Long blockTime;
	@Field("expireTime")
	private Long expireTime;
	
	
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
	public String getSalesManId() {
	    return salesManId;
	}
	public void setSalesManId(String salesManId) {
	    this.salesManId = salesManId;
	}
	public String getCustomer() {
	    return customer;
	}
	public void setCustomer(String customer) {
	    this.customer = customer;
	}
	public String getOrderDate() {
	    
	    Date date = new Date(orderDate);
	    Format format = new SimpleDateFormat("dd-MMM-yyyy HH:mm a");
	    return format.format(date);
	    
	    //return orderDate;
	}
	public void setOrderDate(long orderDate) {
	    this.orderDate = orderDate;
	}
	public long getDeliveryDate() {
	    return deliveryDate;
	}
	public void setDeliveryDate(long deliveryDate) {
	    this.deliveryDate = deliveryDate;
	}
	public String getFamilyKey() {
	    return familyKey;
	}
	public void setFamilyKey(String familyKey) {
	    this.familyKey = familyKey;
	}
	public String getSku() {
	    return sku;
	}
	public void setSku(String sku) {
	    this.sku = sku;
	}
	public int getFgi() {
	    return fgi;
	}
	public void setFgi(int fgi) {
	    this.fgi = fgi;
	}
	public Integer getQuantity() {
	    return quantity;
	}
	public void setQuantity(Integer quantity) {
	    this.quantity = quantity;
	}
	public Long getBlockTime() {
	    return blockTime;
	}
	public void setBlockTime(Long blockTime) {
	    this.blockTime = blockTime;
	}
	public Long getExpireTime() {
	    return expireTime;
	}
	public void setExpireTime(Long expireTime) {
	    this.expireTime = expireTime;
	}
	
	
	

}
