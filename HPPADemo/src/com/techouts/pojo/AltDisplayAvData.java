
package com.techouts.pojo;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="ALT_DISPLAY_AV_DATA")
public class AltDisplayAvData {
	@Field("AV")
	String av;
	
	@Field("AV_DESCRIPTION")
	String avdesc;
	
	@Field("COMMODITY")
	String commodity;
	
	@Field("COMMODITY_ATTRIBUTE")
	String commodityAttr;
	
	@Field("PRIORITY")
	String priority;

	public String getAv() {
		return av;
	}

	public void setAv(String av) {
		this.av = av;
	}

	public String getAvdesc() {
		return avdesc;
	}

	public void setAvdesc(String avdesc) {
		this.avdesc = avdesc;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getCommodityAttr() {
		return commodityAttr;
	}

	public void setCommodityAttr(String commodityAttr) {
		this.commodityAttr = commodityAttr;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
}
