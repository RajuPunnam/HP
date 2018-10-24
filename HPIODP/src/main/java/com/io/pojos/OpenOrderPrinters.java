package com.io.pojos;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="openorders_print")
public class OpenOrderPrinters {

	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	@Column(name="Buyer")
	private String	buyer;	
	@Column(name="PN")
	private String pn;
	@Column(name="Description")
	private String desc;
	@Column(name="PODATE")
	private Date po_DATE;
	@Column(name="`PO/L`")
	private String PO_L;
	@Column(name="ST")
	private String ST;
	@Column(name="DtEmb")
	private Date dt_Emb;
	@Column(name="DtConfEmb")
	private Date Ddt_Conf_Emb;
	@Column(name="PickUp")
	private Date Pick_Up;
	@Column(name="DtEntr")
	private Date dt_Entr;
	@Column(name="QT")
	private double qty;
	@Column(name="Fornecedor")
	private String fornecedor;
	@Column(name="InvoiceManual")
	private String invoice_Manual;
	
	@Column(name="FileDate")
	private Date fileDate;
	@Column(name="category")
	private String category;
	
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	
	
	public Date getFileDate() {
		return fileDate;
	}
	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	
	public Date getPo_DATE() {
		return po_DATE;
	}
	public String getPn() {
		return pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public void setPo_DATE(Date po_DATE) {
		this.po_DATE = po_DATE;
	}
	public String getPO_L() {
		return PO_L;
	}
	public void setPO_L(String pO_L) {
		PO_L = pO_L;
	}
	public String getST() {
		return ST;
	}
	public void setST(String sT) {
		ST = sT;
	}
	public Date getDt_Emb() {
		return dt_Emb;
	}
	public void setDt_Emb(Date dt_Emb) {
		this.dt_Emb = dt_Emb;
	}
	public Date getDdt_Conf_Emb() {
		return Ddt_Conf_Emb;
	}
	public void setDdt_Conf_Emb(Date ddt_Conf_Emb) {
		Ddt_Conf_Emb = ddt_Conf_Emb;
	}
	public Date getPick_Up() {
		return Pick_Up;
	}
	public void setPick_Up(Date pick_Up) {
		Pick_Up = pick_Up;
	}
	public Date getDt_Entr() {
		return dt_Entr;
	}
	public void setDt_Entr(Date dt_Entr) {
		this.dt_Entr = dt_Entr;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public String getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
	public String getInvoice_Manual() {
		return invoice_Manual;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setInvoice_Manual(String invoice_Manual) {
		this.invoice_Manual = invoice_Manual;
	}
	@Override
	public String toString() {
		return "OpenOrderPrinters [id1=" + id1 + ", buyer=" + buyer + ", pn="
				+ pn + ", desc=" + desc + ", po_DATE=" + po_DATE + ", PO_L="
				+ PO_L + ", ST=" + ST + ", dt_Emb=" + dt_Emb
				+ ", Ddt_Conf_Emb=" + Ddt_Conf_Emb + ", Pick_Up=" + Pick_Up
				+ ", dt_Entr=" + dt_Entr + ", qty=" + qty + ", fornecedor="
				+ fornecedor + ", invoice_Manual=" + invoice_Manual
				+ ", fileDate=" + fileDate + "]";
	}
	
}
