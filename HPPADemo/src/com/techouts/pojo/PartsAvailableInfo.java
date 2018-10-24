package com.techouts.pojo;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "PartsAvailableInfo")
@TypeAlias("PartsAvailableInfo")
public class PartsAvailableInfo implements Serializable {

	@Id
	private String id;
	@Field("partId")
	private String partId;
	// @Field("onHand")
	private int onHand;

	@Field("reserved")
	private int reserved;
	@Field("allocated")
	private int allocated;
	@Field("acutalQuantity")
	private int acutalQuantity;

	// Setters and Getters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public int getOnHand() {
		return onHand;
	}

	public void setOnHand(int onHand) {
		this.onHand = onHand;
	}

	public int getReserved() {
		return reserved;
	}

	public void setReserved(int reserved) {
		this.reserved = reserved;
	}

	public int getAllocated() {
		return allocated;
	}

	public void setAllocated(int allocated) {
		this.allocated = allocated;
	}

	public int getAcutalQuantity() {
		return acutalQuantity;
	}

	public void setAcutalQuantity(int acutalQuantity) {
		this.acutalQuantity = acutalQuantity;
	}

	@Override
	public String toString() {
	    return "PartsAvailableInfo [id=" + id + ", partId=" + partId
		    + ", onHand=" + onHand + ", reserved=" + reserved
		    + ", allocated=" + allocated + ", acutalQuantity="
		    + acutalQuantity + "]";
	}
	
	
	

}
