package com.techouts.pojo;

public class Family{
	  private String skuid;
	  private String count;
	public String getSkuid() {
		return skuid;
	}
	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "Family [skuid=" + skuid + ", count=" + count + "]";
	}
	  
	}