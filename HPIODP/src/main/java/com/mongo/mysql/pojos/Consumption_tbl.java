package com.mongo.mysql.pojos;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.inventory.utill.DoubleUtill;

@Document(collection="PPB_PC")
@TypeAlias("PPB_PC")
@Entity
@Table(name="consumption_tbl")
@Access(value=AccessType.FIELD)
public class Consumption_tbl {
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	@Field("Item code")
	@Column(name="Itemcode")
	private String itemCode;
	@Field("Description")
	@Column(name="`Description`")
	private String description;
	@Field("Alm")
	@Column(name="Alm")
	private String alm;
	@Field("DataTrans")
	@Column(name="DataTrans")
	private String date;
	@Field("hr Trans")
	@Column(name="TransHr")
	private String transHr;
	@Field("Hr + 4 hrs")
	@Column(name="Hr_4hrs")
	private String hr_4Hrs;
	@Field("Transacton type")
	@Column(name="`Transacton type`")
	private String transactionType;
	@Field("IP")
	@Column(name="IP")
	private String ip;
	@Field("SKU")
	@Column(name="SKU")
	private String sku;
	@Field("Total OP")
	@Column(name="Totalop")
	private String totalOp;
	@Field("Low Qty / Receive")
	@Column(name="`QtyLow/Receive`")
	private String qtyLow;
	@Field("OP balance")
	@Column(name="Opbalance")
	private String opBalance;
	@Field("Est after Trans")
	@Column(name="EstafterTrans")
	private String estafterTrans;
	@Field("Retrab")
	@Column(name="Retrab")
	private String retrab;
	@Field("Login code")
	@Column(name="Logincode")
	private String loginCode;
	
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getAlm() {
		/*System.out.println("getAlm");
		if(alm!=null && !alm.isEmpty()){
			alm.trim();
			   if(alm.contains(","))
			    return Double.parseDouble(alm.replace(",", ""));
			   else
			    return Double.parseDouble(alm);
			   }else{
			    return 0;
			   }*/
		return DoubleUtill.getValue(opBalance);
	}
	public void setAlm(String alm) {
		this.alm = alm;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTransHr() {
		return transHr;
	}
	public void setTransHr(String transHr) {
		this.transHr = transHr;
	}
	public String getHr_4Hrs() {
		return hr_4Hrs;
	}
	public void setHr_4Hrs(String hr_4Hrs) {
		this.hr_4Hrs = hr_4Hrs;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public double getIp() {
		/*System.out.println("getIp");
		if(ip!=null && !ip.isEmpty()){
			ip.trim();
			   if(ip.contains(","))
			    return Double.parseDouble(ip.replace(",", ""));
			   else
			    return Double.parseDouble(ip);
			   }else{
			    return 0;
			   }*/
		return DoubleUtill.getValue(ip);
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public double getTotalOp() {
		/*if(totalOp!=null && !totalOp.isEmpty()){
			totalOp.trim();
			   if(totalOp.contains(","))
			    return Double.parseDouble(totalOp.replace(",", ""));
			   else
			    return Double.parseDouble(totalOp);
			   }else{
			    return 0;
			   }*/
		return DoubleUtill.getValue(totalOp);
	}
	public void setTotalOp(String totalOp) {
		this.totalOp = totalOp;
	}
	public double getQtyLow() {
		/*if(qtyLow!=null && !qtyLow.isEmpty()){
			qtyLow.trim();
			   if(qtyLow.contains(","))
			    return Double.parseDouble(qtyLow.replace(",", ""));
			   else
			    return Double.parseDouble(qtyLow);
			   }else{
			    return 0;
			   }*/
		return DoubleUtill.getValue(qtyLow);
	}
	public void setQtyLow(String qtyLow) {
		this.qtyLow = qtyLow;
	}
	public double getOpBalance() {
		/*System.out.println("getOpBalance");
		if(opBalance!=null && !opBalance.isEmpty()){
			opBalance.trim();
			   if(opBalance.contains(","))
			    return Double.parseDouble(opBalance.replace(",", ""));
			   else
			    return Double.parseDouble(opBalance);
			   }else{
			    return 0;
			   }*/
		return DoubleUtill.getValue(opBalance);
	}
	public void setOpBalance(String opBalance) {
		this.opBalance = opBalance;
	}
	public double getEstafterTrans() {
		/*System.out.println("getEstafterTrans");
		if(estafterTrans!=null && !estafterTrans.isEmpty()){
			estafterTrans.trim();
			   if(estafterTrans.contains(","))
			    return Double.parseDouble(estafterTrans.replace(",", ""));
			   else
			    return Double.parseDouble(estafterTrans);
			   }else{
			    return 0;
			   }*/
		return DoubleUtill.getValue(estafterTrans);
	}
	public void setEstafterTrans(String estafterTrans) {
		this.estafterTrans = estafterTrans;
	}
	public String getRetrab() {
		return retrab;
	}
	public void setRetrab(String retrab) {
		this.retrab = retrab;
	}
	public String getLoginCode() {
		return loginCode;
	}
	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}
	
	
	@Override
	public String toString() {
		System.out.println("toString");
		return "Consumption_tbl [id1=" + id1 + ", itemCode=" + itemCode
				+ ", description=" + description + ", alm=" + alm + ", date="
				+ date + ", transHr=" + transHr + ", hr_4Hrs=" + hr_4Hrs
				+ ", transactionType=" + transactionType + ", ip=" + ip
				+ ", sku=" + sku + ", totalOp=" + totalOp + ", qtyLow="
				+ qtyLow + ", opBalance=" + opBalance + ", estafterTrans="
				+ estafterTrans + ", retrab=" + retrab + ", loginCode="
				+ loginCode + "]";
	}
	
	
	
	
	
	

}
