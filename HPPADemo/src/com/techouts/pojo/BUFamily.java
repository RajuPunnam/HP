package com.techouts.pojo;


public class BUFamily 
{

	private String bu;
	private int totalCount;
	private String sku;
	private int qty;
	private int pipelineQty;
	public String getBu() {
		return bu.replace("\"", "");
	}
	public void setBu(String bu) {
		this.bu = bu;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public int getPipelineQty() {
		return pipelineQty;
	}
	public void setPipelineQty(int pipelineQty) {
		this.pipelineQty = pipelineQty;
	}

}
