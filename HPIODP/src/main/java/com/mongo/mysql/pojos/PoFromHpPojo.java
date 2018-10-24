package com.mongo.mysql.pojos;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="OPEN_ORDERS")
public class PoFromHpPojo
{
	@Field("File Date")
	private String File_Date;
	@Field("PO Received Date")
	private String PO_Received_Date;
	@Field("Aging")
	private double Aging;
	@Field("PO")
	private double PO;
	@Field("SO")
	private double SO;
	@Field("Customer")
	private String Customer;
	@Field("PL")
	private String PL;
	@Field("SKU")
	private String SKU;
	@Field("Family")
	private String Family;
	@Field("Total")
	private double Total;
	@Field("Delta")
	private double Delta;
	@Field("Pri")
	private String Pri;
	@Field("E")
	private String E;
	@Field("Ref")
	private String Ref;
	@Field("Status")
	private String Status;
	@Field("ETS")
	private String ETS;
	@Field("Split")
	private String split;
	@Override
	public String toString() {
		return "PoFromHpPojo [File_Date=" + File_Date + ", PO_Received_Date="
				+ PO_Received_Date + ", Aging=" + Aging + ", PO=" + PO
				+ ", SO=" + SO + ", Customer=" + Customer + ", PL=" + PL
				+ ", SKU=" + SKU + ", Family=" + Family + ", Total=" + Total
				+ ", Delta=" + Delta + ", Pri=" + Pri + ", E=" + E + ", Ref="
				+ Ref + ", Status=" + Status + ", ETS=" + ETS + ", split="
				+ split + ", om_Comments=" + om_Comments + "]";
	}
	@Field("OM Comments")
	private String om_Comments;
	public String getFile_Date() {
		return File_Date;
	}
	public void setFile_Date(String file_Date) {
		File_Date = file_Date;
	}
	public String getPO_Received_Date() {
		return PO_Received_Date;
	}
	public void setPO_Received_Date(String pO_Received_Date) {
		PO_Received_Date = pO_Received_Date;
	}
	public double getAging() {
		return Aging;
	}
	public void setAging(double aging) {
		Aging = aging;
	}
	public double getPO() {
		return PO;
	}
	public void setPO(double pO) {
		PO = pO;
	}
	public double getSO() {
		return SO;
	}
	public void setSO(double sO) {
		SO = sO;
	}
	public String getCustomer() {
		return Customer;
	}
	public void setCustomer(String customer) {
		Customer = customer;
	}
	public String getPL() {
		return PL;
	}
	public void setPL(String pL) {
		PL = pL;
	}
	public String getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	public String getFamily() {
		return Family;
	}
	public void setFamily(String family) {
		Family = family;
	}
	public double getTotal() {
		return Total;
	}
	public void setTotal(double total) {
		Total = total;
	}
	public double getDelta() {
		return Delta;
	}
	public void setDelta(double delta) {
		Delta = delta;
	}
	public String getPri() {
		return Pri;
	}
	public void setPri(String pri) {
		Pri = pri;
	}
	public String getE() {
		return E;
	}
	public void setE(String e) {
		E = e;
	}
	public String getRef() {
		return Ref;
	}
	public void setRef(String ref) {
		Ref = ref;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getETS() {
		return ETS;
	}
	public void setETS(String eTS) {
		ETS = eTS;
	}
	public String getSplit() {
		return split;
	}
	public void setSplit(String split) {
		this.split = split;
	}
	public String getOm_Comments() {
		return om_Comments;
	}
	public void setOm_Comments(String om_Comments) {
		this.om_Comments = om_Comments;
	}
	
}
