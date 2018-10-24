package com.inventory.finalpojos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection = "INVOICES")
public class InvoicesBaseData {
	@Id
	private String id;
	@Field("SKU")
	private String sku;
	@Field("Qty")
	private int skuQuantity;
	@Field("PLs")
	private String pl;
	@Field("Po")
	private long po;
	@Field("NFiscal")
	private String nFiscal;
	@Field("EMB")
	private String emb;
	@Field("Date")
	private String invoiceDate;
	@Field("File Date")
	private String fileDate;
	
	private double totalpartsQty;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public int getSkuQuantity() {
		return skuQuantity;
	}
	public void setSkuQuantity(int skuQuantity) {
		this.skuQuantity = skuQuantity;
	}
	public String getPl() {
		return pl;
	}
	public void setPl(String pl) {
		this.pl = pl;
	}
	public long getPo() {
		return po;
	}
	public void setPo(long po) {
		this.po = po;
	}
	public String getnFiscal() {
		return nFiscal;
	}
	public void setnFiscal(String nFiscal) {
		this.nFiscal = nFiscal;
	}
	public String getEmb() {
		return emb;
	}
	public void setEmb(String emb) {
		this.emb = emb;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public double getTotalpartsPerSku() {
		return totalpartsQty;
	}
	public void setTotalpartsPerSku(double totalpartsPerSku) {
		this.totalpartsQty = totalpartsPerSku;
	}
	
}
