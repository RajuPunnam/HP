package com.mongo.mysql.pojos;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.inventory.utill.DateUtil;

@Document(collection="OPEN_ORDERS")
@Entity
@Table(name="open_orders_raw_tbl")
@Access(value=AccessType.FIELD)
public class OpenOrdersRaw {

	@Id
	@GenericGenerator(name="ordersgen" , strategy="increment")
	@GeneratedValue(generator="ordersgen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	@Field("File Date")
	@Column(name="FileDate")
	private String File_Date;
	@Field("PO Received Date")
	@Column(name="POReceivedDate")
	private String PO_Received_Date;
	@Field("Aging")
	@Column(name="Aging")
	private double Aging;
	@Field("PO")
	@Column(name="PO")
	private double PO;//--------string in mysql
	@Field("SO")
	@Column(name="SO")
	private double SO; //--------string in mysql
	@Field("Customer")
	@Column(name="Customer")
	private String Customer;
	@Field("PL")
	@Column(name="PL")
	private String PL;
	@Field("SKU")
	@Column(name="SKU")
	private String SKU;
	@Field("Family")
	@Column(name="Family")
	private String Family;
	@Field("Total")
	@Column(name="Total")
	private double Total;
	@Field("Delta")
	@Column(name="Delta")
	private double Delta; //--------string in mysql
	@Field("Pri")
	@Column(name="Pri")
	private String Pri;
	@Field("E")
	@Column(name="E")
	private String E;
	@Field("Ref")
	@Column(name="Ref")
	private String Ref;
	@Field("Status")
	@Column(name="Status")
	private String Status;
	@Field("ETS")
	@Column(name="ETS")
	private String ETS;
	
	
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public String getFile_Date() {
		return DateUtil.getRequiredDateFornate(File_Date);
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
	
	
	@Override
	public String toString() {
		return "OpenOrdersRaw [id1=" + id1 + ", File_Date=" + File_Date
				+ ", PO_Received_Date=" + PO_Received_Date + ", Aging=" + Aging
				+ ", PO=" + PO + ", SO=" + SO + ", Customer=" + Customer
				+ ", PL=" + PL + ", SKU=" + SKU + ", Family=" + Family
				+ ", Total=" + Total + ", Delta=" + Delta + ", Pri=" + Pri
				+ ", E=" + E + ", Ref=" + Ref + ", Status=" + Status + ", ETS="
				+ ETS + "]";
	}
	
	

}
