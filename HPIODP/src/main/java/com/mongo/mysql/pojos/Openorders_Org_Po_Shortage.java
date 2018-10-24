package com.mongo.mysql.pojos;

import java.util.Date;

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

@Document(collection="ORIG_PO_SHORTAGE")
@Entity
@Table(name="orig_po_shortages_tbl")
@Access(value=AccessType.FIELD)
public class Openorders_Org_Po_Shortage {
    @Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
    
    @Field("File Date")
    @Column(name="FileDate")
    private String fileDate;
    
    @Field("AdjustedDate")
    @Column(name="AdjustedDate")
    private String adjustedDate;
    
    @Field("PORecDate")
    @Column(name="PORecDate")
    private String poRecDate;
    @Field("PO")
    @Column(name="PO")
    private String po;
    @Field("SKU")
    @Column(name="SKU")
    private String sku;
    
   /* @Field("customer")
    @Column(name="customer")
    private String customer;*/
    @Field("PL")
    @Column(name="PL")
    private String pl;
    @Field("family")
    @Column(name="family")
    private String family;
    @Field("avId")
    @Column(name="avId")
    private String avId;
    
   
    @Field("avCons")
    @Column(name="avCons")
    private String avCons;
    @Field("skuCons")
    @Column(name="skuCons")
    private String skuCons;
    
    //additional columns
    @Field("partID")
    @Column(name="partID")
    private String partId;
    @Field("QTY")
    @Column(name="QTY")
    private double quantity;
   /* @Field("partLevel")
    @Column(name="partLevel")
    private String partLevel;*/
    @Field("Flag")
    @Column(name="Flag")
    private boolean flag;
   /* @Field("ImportLocal")
    @Column(name="ImportLocal")
    private String importLocal;
    @Field("MPC")
    @Column(name="MPC")
    private String mpc;*/
    @Field("SkuPrice")
    @Column(name="SkuPrice")
    private double skuPrice;
    @Field("AvPrice")
    @Column(name="AvPrice")
    private double avPrice;
    @Field("PartPrice")
    @Column(name="PartPrice")
    private double partPrice;
    @Field("Commodity")
    @Column(name="Commodity")
    private String commodity;
    @Field("BU")
    @Column(name="BU")
    private String bu;
    @Field("SO")
    @Column(name="SO")
    private String so;
    @Field("SKUQty_Del")
    @Column(name="SKUQty_Del")
    private double SKUQty_Del;
    @Field("AVQty_Del")
    @Column(name="AVQty_Del")
    private double AVQty_Del;
    @Field("PNQty_Del")
    @Column(name="PNQty_Del")
    private double PNQty_Del;
    
   /* @Field("Supplier")
    @Column(name="Supplier")
    private String supplier;*/
	
    public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public String getFileDate() {
		return DateUtil.getRequiredDateFornate(fileDate);
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public String getAdjustedDate() {
		return adjustedDate;
	}
	public void setAdjustedDate(String adjustedDate) {
		this.adjustedDate = adjustedDate;
	}
	public String getPoRecDate() {
		return poRecDate;
	}
	public void setPoRecDate(String poRecDate) {
		this.poRecDate = poRecDate;
	}
	public String getPo() {
		return po;
	}
	public void setPo(String po) {
		this.po = po;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	/*public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}*/
	public String getPl() {
		return pl;
	}
	public void setPl(String pl) {
		this.pl = pl;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getAvId() {
		return avId;
	}
	public void setAvId(String avId) {
		this.avId = avId;
	}
	public String getAvCons() {
		return avCons;
	}
	public void setAvCons(String avCons) {
		this.avCons = avCons;
	}
	public String getSkuCons() {
		return skuCons;
	}
	public void setSkuCons(String skuCons) {
		this.skuCons = skuCons;
	}
	public String getPartId() {
		return partId;
	}
	public void setPartId(String partId) {
		this.partId = partId;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	/*public String getPartLevel() {
		return partLevel;
	}
	public void setPartLevel(String partLevel) {
		this.partLevel = partLevel;
	}*/
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	/*public String getImportLocal() {
		return importLocal;
	}
	public void setImportLocal(String importLocal) {
		this.importLocal = importLocal;
	}*/
	/*public String getMpc() {
		return mpc;
	}
	public void setMpc(String mpc) {
		this.mpc = mpc;
	}*/
	public double getSkuPrice() {
		return skuPrice;
	}
	public void setSkuPrice(double skuPrice) {
		this.skuPrice = skuPrice;
	}
	public double getAvPrice() {
		return avPrice;
	}
	public void setAvPrice(double avPrice) {
		this.avPrice = avPrice;
	}
	public double getPartPrice() {
		return partPrice;
	}
	public void setPartPrice(double partPrice) {
		this.partPrice = partPrice;
	}
	public String getCommodity() {
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	/*public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}*/
	public String getBu() {
		return bu;
	}
	public void setBu(String bu) {
		this.bu = bu;
	}
	public String getSo() {
		return so;
	}
	public void setSo(String so) {
		this.so = so;
	}
	public double getSKUQty_Del() {
		return SKUQty_Del;
	}
	public void setSKUQty_Del(double sKUQty_Del) {
		SKUQty_Del = sKUQty_Del;
	}
	public double getAVQty_Del() {
		return AVQty_Del;
	}
	public void setAVQty_Del(double aVQty_Del) {
		AVQty_Del = aVQty_Del;
	}
	public double getPNQty_Del() {
		return PNQty_Del;
	}
	public void setPNQty_Del(double pNQty_Del) {
		PNQty_Del = pNQty_Del;
	}
	
	
	@Override
	public String toString() {
		return "Openorders_Org_Po_Shortage [id1=" + id1 + ", fileDate="
				+ fileDate + ", adjustedDate=" + adjustedDate + ", poRecDate="
				+ poRecDate + ", po=" + po + ", sku=" + sku + ", pl=" + pl
				+ ", family=" + family + ", avId=" + avId + ", avCons="
				+ avCons + ", skuCons=" + skuCons + ", partId=" + partId
				+ ", quantity=" + quantity + ", flag=" + flag + ", skuPrice="
				+ skuPrice + ", avPrice=" + avPrice + ", partPrice="
				+ partPrice + ", commodity=" + commodity + ", bu=" + bu
				+ ", so=" + so + ", SKUQty_Del=" + SKUQty_Del + ", AVQty_Del="
				+ AVQty_Del + ", PNQty_Del=" + PNQty_Del + "]";
	}
	
	
	
	
	
	

}
