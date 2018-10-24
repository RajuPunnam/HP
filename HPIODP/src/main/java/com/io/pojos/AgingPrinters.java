package com.io.pojos;







import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="aging_printer")
public class AgingPrinters {
	
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int ID;
	@Column(name="Warehouse")
	private double warehouse;
	@Column(name="Item")
	private String item;
	@Column(name="Description")
	private String description;
	@Column(name="`Data Ultima Contagem`")
	private String data_Ultima_Contagem;
	@Column(name="`Estoque Fisico`")
	private double estoque_Fisico;
	@Column(name="Location")
	private String location;
	@Column(name="Estoque")
	private double estoque;
	@Column(name="Lote")
	private String lote;
	@Column(name="FIFO")
	private String fifo;
	@Column(name="`Vl. Un. (R$)`")
	private double vl_Un_R$;
	@Column(name="`Vl. Un. (STD)`")
	private double vl_Un_STD;
	@Column(name="CodigoABC")
	private String codigoABC;
	@Column(name="DtUltTrans")
	private String dtUltTrans;
	@Column(name="Aging")
	private double aging;
	@Column(name="`Total USD`")
	private double total_USD;
	@Column(name="PCA")
	private String pCA;
	@Column(name="Type")
	private String type;
	@Column(name="`Aging Status`")
	private String aging_Status;
	@Column(name="Supplier")
	private String supplier;
	@Column(name="PL")
	private String pL;
	@Column(name="Commodities")
	private String commodities;
	@Column(name="FILEDATE",columnDefinition="DATE")
	private Date fileDate;
	@Column(name="category")
	private String category;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	public double getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(double warehouse) {
		this.warehouse = warehouse;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getData_Ultima_Contagem() {
		return data_Ultima_Contagem;
	}
	public void setData_Ultima_Contagem(String data_Ultima_Contagem) {
		this.data_Ultima_Contagem = data_Ultima_Contagem;
	}
	public double getEstoque_Fisico() {
		return estoque_Fisico;
	}
	public void setEstoque_Fisico(double estoque_Fisico) {
		this.estoque_Fisico = estoque_Fisico;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public double getEstoque() {
		return estoque;
	}
	public void setEstoque(double estoque) {
		this.estoque = estoque;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getFifo() {
		return fifo;
	}
	public void setFifo(String fifo) {
		this.fifo = fifo;
	}
	public double getVl_Un_R$() {
		return vl_Un_R$;
	}
	public void setVl_Un_R$(double vl_Un_R$) {
		this.vl_Un_R$ = vl_Un_R$;
	}
	public double getVl_Un_STD() {
		return vl_Un_STD;
	}
	public void setVl_Un_STD(double vl_Un_STD) {
		this.vl_Un_STD = vl_Un_STD;
	}
	public String getCodigoABC() {
		return codigoABC;
	}
	public void setCodigoABC(String codigoABC) {
		this.codigoABC = codigoABC;
	}
	public String getDtUltTrans() {
		return dtUltTrans;
	}
	public void setDtUltTrans(String dtUltTrans) {
		this.dtUltTrans = dtUltTrans;
	}
	public double getAging() {
		return aging;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setAging(double aging) {
		this.aging = aging;
	}
	public double getTotal_USD() {
		return total_USD;
	}
	public void setTotal_USD(double total_USD) {
		this.total_USD = total_USD;
	}
	public String getpCA() {
		return pCA;
	}
	public void setpCA(String pCA) {
		this.pCA = pCA;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAging_Status() {
		return aging_Status;
	}
	public void setAging_Status(String aging_Status) {
		this.aging_Status = aging_Status;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getpL() {
		return pL;
	}
	public void setpL(String pL) {
		this.pL = pL;
	}
	public String getCommodities() {
		return commodities;
	}
	public void setCommodities(String commodities) {
		this.commodities = commodities;
	}
	public Date getFileDate() {
		return fileDate;
	}
	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}
	@Override
	public String toString() {
		return "AgingPrinters [ID=" + ID + ", warehouse=" + warehouse
				+ ", item=" + item + ", description=" + description
				+ ", data_Ultima_Contagem=" + data_Ultima_Contagem
				+ ", estoque_Fisico=" + estoque_Fisico + ", location="
				+ location + ", estoque=" + estoque + ", lote=" + lote
				+ ", fifo=" + fifo + ", vl_Un_R$=" + vl_Un_R$ + ", vl_Un_STD="
				+ vl_Un_STD + ", codigoABC=" + codigoABC + ", dtUltTrans="
				+ dtUltTrans + ", aging=" + aging + ", total_USD=" + total_USD
				+ ", pCA=" + pCA + ", type=" + type + ", aging_Status="
				+ aging_Status + ", supplier=" + supplier + ", pL=" + pL
				+ ", commodities=" + commodities + ", fileDate=" + fileDate
				+ "]";
	} 
	
	
	 
	
}
