package com.techouts.hp.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="PPB_PC")
public class PpbPcPojo 
{	
	@Field("Date")
	private String fileDate;
	@Field("Item code")
	private String Item_code;
	@Field("Description")
	private String Description;
	@Field("Alm")
	private double Alm;
	@Field("DataTrans")
	private String DataTrans;
	@Field("hr Trans")
	private String hr_Trans;
	@Field("Hr + 4 hrs")
	private String Hr__4_hrs;
	@Field("Transaction type")
	private String Transaction_type;
	@Field("IP")
	private double IP;
	@Field("SKU")
	private String SKU;
	@Field("Total OP")
	private double Total_OP;
	@Field("Low Qty / Receive")
	private double Low_Qty_Receive;
	@Field("OP balance")
	private double OP_balance;
	@Field("Est after Trans")
	private double Est_after_Trans;
	@Field("Retrab")
	private String Retrab;
	@Field("Login code")
	private String Login_code;
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public String getItem_code() {
		return Item_code;
	}
	public void setItem_code(String item_code) {
		Item_code = item_code;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public double getAlm() {
		return Alm;
	}
	public void setAlm(double alm) {
		Alm = alm;
	}
	public String getDataTrans() {
		return DataTrans;
	}
	public void setDataTrans(String dataTrans) {
		DataTrans = dataTrans;
	}
	public String getHr_Trans() {
		return hr_Trans;
	}
	public void setHr_Trans(String hr_Trans) {
		this.hr_Trans = hr_Trans;
	}
	public String getHr__4_hrs() {
		return Hr__4_hrs;
	}
	public void setHr__4_hrs(String hr__4_hrs) {
		Hr__4_hrs = hr__4_hrs;
	}
	public String getTransaction_type() {
		return Transaction_type;
	}
	public void setTransaction_type(String transaction_type) {
		Transaction_type = transaction_type;
	}
	public double getIP() {
		return IP;
	}
	public void setIP(double iP) {
		IP = iP;
	}
	public String getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	public double getTotal_OP() {
		return Total_OP;
	}
	public void setTotal_OP(double total_OP) {
		Total_OP = total_OP;
	}
	public double getLow_Qty_Receive() {
		return Low_Qty_Receive;
	}
	public void setLow_Qty_Receive(double low_Qty_Receive) {
		Low_Qty_Receive = low_Qty_Receive;
	}
	public double getOP_balance() {
		return OP_balance;
	}
	public void setOP_balance(double oP_balance) {
		OP_balance = oP_balance;
	}
	public double getEst_after_Trans() {
		return Est_after_Trans;
	}
	public void setEst_after_Trans(double est_after_Trans) {
		Est_after_Trans = est_after_Trans;
	}
	public String getRetrab() {
		return Retrab;
	}
	public void setRetrab(String retrab) {
		Retrab = retrab;
	}
	public String getLogin_code() {
		return Login_code;
	}
	public void setLogin_code(String login_code) {
		Login_code = login_code;
	}
		
}
