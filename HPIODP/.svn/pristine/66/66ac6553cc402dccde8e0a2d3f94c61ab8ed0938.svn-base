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
	public class Consumption_tbl_MySql {
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
		private double alm;
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
		private double ip;
		@Field("SKU")
		@Column(name="SKU")
		private String sku;
		@Field("Total OP")
		@Column(name="Totalop")
		private double totalOp;
		@Field("Low Qty / Receive")
		@Column(name="`QtyLow/Receive`")
		private double qtyLow;
		@Field("OP balance")
		@Column(name="Opbalance")
		private double opBalance;
		@Field("Est after Trans")
		@Column(name="EstafterTrans")
		private double estafterTrans;
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
			return alm;
		}
		public void setAlm(Object obj) {
			this.alm=DoubleUtill.getValue(obj);
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
			return ip;
		}
		public void setIp(Object obj) {
			this.ip = DoubleUtill.getValue(obj);
		}
		public String getSku() {
			return sku;
		}
		public void setSku(String sku) {
			this.sku = sku;
		}
		public double getTotalOp() {
			return totalOp;
		}
		public void setTotalOp(Object obj) {
			this.totalOp = DoubleUtill.getValue(obj);
		}
		public double getQtyLow() {
			return qtyLow;
		}
		public void setQtyLow(Object obj) {
			this.qtyLow = DoubleUtill.getValue(obj);
		}
		public double getOpBalance() {
			return opBalance;
		}
		public void setOpBalance(Object obj) {
			this.opBalance = DoubleUtill.getValue(obj);
		}
		public double getEstafterTrans() {
			return estafterTrans;
		}
		public void setEstafterTrans(Object obj) {
			this.estafterTrans = DoubleUtill.getValue(obj);
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
			return "Consumption_tbl_MySql [id1=" + id1 + ", itemCode="
					+ itemCode + ", description=" + description + ", alm="
					+ alm + ", date=" + date + ", transHr=" + transHr
					+ ", hr_4Hrs=" + hr_4Hrs + ", transactionType="
					+ transactionType + ", ip=" + ip + ", sku=" + sku
					+ ", totalOp=" + totalOp + ", qtyLow=" + qtyLow
					+ ", opBalance=" + opBalance + ", estafterTrans="
					+ estafterTrans + ", retrab=" + retrab + ", loginCode="
					+ loginCode + "]";
		}
		
		
}
