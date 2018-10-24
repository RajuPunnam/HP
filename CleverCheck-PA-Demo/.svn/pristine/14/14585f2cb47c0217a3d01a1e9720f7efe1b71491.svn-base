package com.techouts.pojo;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "PN_LT")
@TypeAlias("PNLT")
public class PNLT {
	@Field("Part Number")
private String partId;
	@Field("Lead Time")
private double leadtime;

public String getPartId() {
	return partId;
}
public void setPartId(String partId) {
	this.partId = partId;
}
public double getLeadtime() {
	return leadtime;
}
public void setLeadtime(double leadtime) {
	this.leadtime = leadtime;
}
}
