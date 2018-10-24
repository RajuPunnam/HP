package com.techouts.pojo;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Cart")
@TypeAlias("Cart")
public class Cart {

	@Id
	private String id;
	@Field("cartId")
	private String cartId;
	@Field("familyKey")
	private String familyKey;
	@Field("sku")
	private String sku;
	@Field("fgi")
	private int fgi;
	@Field("quantity")
	private Integer quantity;
	@Field("salesManId")
	private String salesManId;
	@Field("blockTime")
	private Long blockTime;
	@Field("expireTime")
	private Long expireTime;


	
	// Setters and Getters
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
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getSalesManId() {
		return salesManId;
	}
	public void setSalesManId(String salesManId) {
		this.salesManId = salesManId;
	}
	public String getBlockTime() {
		Date date = new Date(blockTime);
		Format format = new SimpleDateFormat("dd-MMM-yyyy HH:mm a");
		return format.format(date);
		// return time;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public int getFgi() {
	    return fgi;
	}
	public void setFgi(int fgi) {
	    this.fgi = fgi;
	}
	
}
