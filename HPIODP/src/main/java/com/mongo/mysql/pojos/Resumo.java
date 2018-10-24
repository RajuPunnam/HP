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
import com.inventory.utill.DoubleUtill;

@Entity
@Table(name="resumo_item_tbl")
@Access(value=AccessType.FIELD)
public class Resumo {
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	@Column(name="`FileDate`")
	private Date fileDate; 
	@Column(name="`ITEM`")
	private String item; 
	@Column(name="`DESCRIPTION`")
	private String description; 
	@Column(name="`MAKE_BUY`")
	private String makeBuy; 
	@Column(name="`ABC_CODE`")
	private String abc_Code;
	@Column(name="`BUYER`")
	private String buyer; 
	@Column(name="`CST_STD`")
	private double cst_Std; 
	@Column(name="`NETTABLE_INVENTORY`")
	private double net_inventory; 
	@Column(name="`USDNETTABLE_INVENTORY`")
	private double usdNet_Inventory; 
	@Column(name="`PLANNER`")
	private String palnner; 
	@Column(name="`MINIMUM_ORDER_QUANTITY`")
	private double minOrderQty; 
	@Column(name="`FIXED_LOT_MULTIPLIER`")
	private double fixedLot_Multiplier; 
	@Column(name="`DEMANDA_3M`")
	private double demand3M; 
	@Column(name="`DEMANDA_6M`")
	private double demand6M; 
	@Column(name="`DEMANDA_12M`")
	private double demand12M; 
	@Column(name="`TOTAL_DEMAND`")
	private double totalDemand; 
	@Column(name="`ONORDER_3M`")
	private double onOrder3M;
	@Column(name="`ONORDER_6M`")
	private double onOrder6M; 
	@Column(name="`ONORDER_12M`")
	private double onOrder12M; 
	@Column(name="`EXCESSO_3M`")
	private double excess3M; 
	@Column(name="`EXCESSO_6M`")
	private double excess6M;
	@Column(name="`EXCESSO_12M`")
	private double excess12M; 
	@Column(name="`EXCESSO_ONORDER_3M`")
	private double excessOnOrder3M; 
	@Column(name="`EXCESSO_ONORDER_6M`")
	private double excessOnOrder6M; 
	@Column(name="`EXCESSO_ONORDER_12M`")
	private double excessOnOrder12M; 
	@Column(name="`TOTAL_OH (712, 713, 715)`")
	private double total_Oh; 
	@Column(name="`IN TRANSIT QTY`")
	private double inTransitQty; 
	@Column(name="`IN TRANSIT USD`")
	private double inTransitUSD; 
    @Column(name="`OPEN PO QTY`")
	private double openPOQty; 
    @Column(name="`OPEN PO USD`")
    private double openPoUSD; 
    @Column(name="`Supplier_Code`")
    private String supplierCode; 
    @Column(name="`Supplier`")
    private String supplier; 
    @Column(name="`Commodities`")
    private String commodities;
    @Column(name="`L06 / L10`")
    private String zeroSix_Ten;
    @Column(name="`E&O UNDER`")
    private double eo_Under;
    @Column(name="`E&O FLEX`")
    private double eo_Flex;
    @Column(name="`E&O HP`")
    private double eo_Hp;
    @Column(name="` 0_dayto7_days`")
    private double zero_Sevendays; 
    @Column(name="`8_4days`")
    private double eight_FourTeenDays; 
    @Column(name="`15_21days`")
    private double fifteen_TwentyOneDays; 
    @Column(name="`22_30days`")
    private double twentyTwo_ThirtyDays; 
    @Column(name="`31_60days`")
    private double ThirtyOne_SixtyDays;
    @Column(name="`61_90days`")
    private double sixtyOne_NintyDays; 
    @Column(name="`91_120days`")
    private double nintyOne_OneTwentyDays; 
    @Column(name="`121_150days`")
    private double oneTwnetyOne_OneFifty;
    @Column(name="`above_151days`")
    private double above150Dayes; 
    @Column(name="`1year`")
    private double oneYear; 
	@Column(name="`DEMAND_30_DAYS`")
	private double demand30Days;
	
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
	public String getMakeBuy() {
		return makeBuy;
	}
	public void setMakeBuy(String makeBuy) {
		this.makeBuy = makeBuy;
	}
	public String getAbc_Code() {
		return abc_Code;
	}
	public void setAbc_Code(String abc_Code) {
		this.abc_Code = abc_Code;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public double getCst_Std() {
		return cst_Std;
	}
	public void setCst_Std(Object obj) {
		this.cst_Std = DoubleUtill.getValue(obj);;
	}
	public double getNet_inventory() {
		return net_inventory;
	}
	public void setNet_inventory(Object obj) {
		this.net_inventory = DoubleUtill.getValue(obj);;
	}
	public double getUsdNet_Inventory() {
		return usdNet_Inventory;
	}
	public void setUsdNet_Inventory(Object obj) {
		this.usdNet_Inventory = DoubleUtill.getValue(obj);;
	}
	public String getPalnner() {
		return palnner;
	}
	public void setPalnner(String palnner) {
		this.palnner = palnner;
	}
	public double getMinOrderQty() {
		return minOrderQty;
	}
	public void setMinOrderQty(Object obj) {
		this.minOrderQty = DoubleUtill.getValue(obj);;
	}
	public double getFixedLot_Multiplier() {
		return fixedLot_Multiplier;
	}
	public void setFixedLot_Multiplier(Object obj) {
		this.fixedLot_Multiplier = DoubleUtill.getValue(obj);;
	}
	public double getDemand3M() {
		return demand3M;
	}
	public void setDemand3M(Object obj) {
		demand3M = DoubleUtill.getValue(obj);;
	}
	public double getDemand6M() {
		return demand6M;
	}
	public void setDemand6M(Object obj) {
		demand6M = DoubleUtill.getValue(obj);;
	}
	public double getDemand12M() {
		return demand12M;
	}
	public void setDemand12M(Object obj) {
		demand12M = DoubleUtill.getValue(obj);;
	}
	public double getTotalDemand() {
		return totalDemand;
	}
	public void setTotalDemand(Object obj) {
		this.totalDemand = DoubleUtill.getValue(obj);;
	}
	public double getOnOrder3M() {
		return onOrder3M;
	}
	public void setOnOrder3M(Object obj) {
		this.onOrder3M = DoubleUtill.getValue(obj);;
	}
	public double getOnOrder6M() {
		return onOrder6M;
	}
	public void setOnOrder6M(Object obj) {
		this.onOrder6M = DoubleUtill.getValue(obj);;
	}
	public double getOnOrder12M() {
		return onOrder12M;
	}
	public void setOnOrder12M(Object obj) {
		this.onOrder12M = DoubleUtill.getValue(obj);;
	}
	public double getExcess3M() {
		return excess3M;
	}
	public void setExcess3M(Object obj) {
		excess3M = DoubleUtill.getValue(obj);;
	}
	public double getExcess6M() {
		return excess6M;
	}
	public void setExcess6M(Object obj) {
		excess6M = DoubleUtill.getValue(obj);;
	}
	public double getExcess12M() {
		return excess12M;
	}
	public void setExcess12M(Object obj) {
		excess12M = DoubleUtill.getValue(obj);;
	}
	public double getExcessOnOrder3M() {
		return excessOnOrder3M;
	}
	public void setExcessOnOrder3M(Object obj) {
		this.excessOnOrder3M = DoubleUtill.getValue(obj);;
	}
	public double getExcessOnOrder6M() {
		return excessOnOrder6M;
	}
	public void setExcessOnOrder6M(Object obj) {
		this.excessOnOrder6M = DoubleUtill.getValue(obj);;
	}
	public double getExcessOnOrder12M() {
		return excessOnOrder12M;
	}
	public void setExcessOnOrder12M(Object obj) {
		this.excessOnOrder12M = DoubleUtill.getValue(obj);;
	}
	public double getTotal_Oh() {
		return total_Oh;
	}
	public void setTotal_Oh(Object obj) {
		this.total_Oh = DoubleUtill.getValue(obj);;
	}
	public double getInTransitQty() {
		return inTransitQty;
	}
	public void setInTransitQty(Object obj) {
		this.inTransitQty = DoubleUtill.getValue(obj);;
	}
	public double getInTransitUSD() {
		return inTransitUSD;
	}
	public void setInTransitUSD(Object obj) {
		this.inTransitUSD = DoubleUtill.getValue(obj);;
	}
	public double getOpenPOQty() {
		return openPOQty;
	}
	public void setOpenPOQty(Object obj) {
		this.openPOQty = DoubleUtill.getValue(obj);;
	}
	public double getOpenPoUSD() {
		return openPoUSD;
	}
	public void setOpenPoUSD(Object obj) {
		this.openPoUSD = DoubleUtill.getValue(obj);;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	public String getCommodities() {
		return commodities;
	}
	public void setCommodities(String commodities) {
		this.commodities = commodities;
	}
	public String getZeroSix_Ten() {
		return zeroSix_Ten;
	}
	public void setZeroSix_Ten(String zeroSix_Ten) {
		this.zeroSix_Ten = zeroSix_Ten;
	}
	public double getEo_Under() {
		return eo_Under;
	}
	public void setEo_Under(Object obj) {
		this.eo_Under = DoubleUtill.getValue(obj);
	}
	public double getEo_Flex() {
		return eo_Flex;
	}
	public void setEo_Flex(Object obj) {
		this.eo_Flex = DoubleUtill.getValue(obj);
	}
	public double getEo_Hp() {
		return eo_Hp;
	}
	public void setEo_Hp(Object obj) {
		this.eo_Hp = DoubleUtill.getValue(obj);
	}
	public double getOne_Sevendays() {
		return zero_Sevendays;
	}
	public void setOne_Sevendays(Object obj) {
		this.zero_Sevendays = DoubleUtill.getValue(obj);;
	}
	public double getEight_FourDays() {
		return eight_FourTeenDays;
	}
	public void setEight_FourDays(Object obj) {
		this.eight_FourTeenDays = DoubleUtill.getValue(obj);;
	}
	public double getFifteen_TwentyOneDays() {
		return fifteen_TwentyOneDays;
	}
	public void setFifteen_TwentyOneDays(Object obj) {
		this.fifteen_TwentyOneDays = DoubleUtill.getValue(obj);;
	}
	public double getTwentyTwo_ThirtyDays() {
		return twentyTwo_ThirtyDays;
	}
	public void setTwentyTwo_ThirtyDays(Object obj) {
		this.twentyTwo_ThirtyDays = DoubleUtill.getValue(obj);;
	}
	public double getThirtyOne_SixtyDays() {
		return ThirtyOne_SixtyDays;
	}
	public void setThirtyOne_SixtyDays(Object obj) {
		ThirtyOne_SixtyDays = DoubleUtill.getValue(obj);;
	}
	public double getSixtyOne_NintyDays() {
		return sixtyOne_NintyDays;
	}
	public void setSixtyOne_NintyDays(Object obj) {
		this.sixtyOne_NintyDays = DoubleUtill.getValue(obj);;
	}
	public double getNintyOne_OneTwentyDays() {
		return nintyOne_OneTwentyDays;
	}
	public void setNintyOne_OneTwentyDays(Object obj) {
		this.nintyOne_OneTwentyDays = DoubleUtill.getValue(obj);;
	}
	public double getOneTwnetyOne_OneFifty() {
		return oneTwnetyOne_OneFifty;
	}
	public void setOneTwnetyOne_OneFifty(Object obj) {
		this.oneTwnetyOne_OneFifty = DoubleUtill.getValue(obj);
	}
	public double getAbove150Dayes() {
		return above150Dayes;
	}
	public void setAbove150Dayes(Object obj) {
		this.above150Dayes = DoubleUtill.getValue(obj);
	}
	public double getOneYear() {
		return oneYear;
	}
	public void setOneYear(Object obj) {
		this.oneYear = DoubleUtill.getValue(obj);
	}
	public double getDemand30Days() {
		return demand30Days;
	}
	public void setDemand30Days(Object obj) {
		this.demand30Days = DoubleUtill.getValue(obj);
	}
	
	
	@Override
	public String toString() {
		return "Resumo [id1=" + id1 + ", fileDate=" + fileDate + ", item="
				+ item + ", description=" + description + ", makeBuy="
				+ makeBuy + ", abc_Code=" + abc_Code + ", buyer=" + buyer
				+ ", cst_Std=" + cst_Std + ", net_inventory=" + net_inventory
				+ ", usdNet_Inventory=" + usdNet_Inventory + ", palnner="
				+ palnner + ", minOrderQty=" + minOrderQty
				+ ", fixedLot_Multiplier=" + fixedLot_Multiplier
				+ ", demand3M=" + demand3M + ", demand6M=" + demand6M
				+ ", demand12M=" + demand12M + ", totalDemand=" + totalDemand
				+ ", onOrder3M=" + onOrder3M + ", onOrder6M=" + onOrder6M
				+ ", onOrder12M=" + onOrder12M + ", excess3M=" + excess3M
				+ ", excess6M=" + excess6M + ", excess12M=" + excess12M
				+ ", excessOnOrder3M=" + excessOnOrder3M + ", excessOnOrder6M="
				+ excessOnOrder6M + ", excessOnOrder12M=" + excessOnOrder12M
				+ ", total_Oh=" + total_Oh + ", inTransitQty=" + inTransitQty
				+ ", inTransitUSD=" + inTransitUSD + ", openPOQty=" + openPOQty
				+ ", openPoUSD=" + openPoUSD + ", supplierCode=" + supplierCode
				+ ", supplier=" + supplier + ", zero_Sevendays="
				+ zero_Sevendays + ", eight_FourTeenDays=" + eight_FourTeenDays
				+ ", fifteen_TwentyOneDays=" + fifteen_TwentyOneDays
				+ ", twentyTwo_ThirtyDays=" + twentyTwo_ThirtyDays
				+ ", ThirtyOne_SixtyDays=" + ThirtyOne_SixtyDays
				+ ", sixtyOne_NintyDays=" + sixtyOne_NintyDays
				+ ", nintyOne_OneTwentyDays=" + nintyOne_OneTwentyDays
				+ ", oneTwnetyOne_OneFifty=" + oneTwnetyOne_OneFifty
				+ ", above150Dayes=" + above150Dayes + ", oneYear=" + oneYear
				+ ", demand30Days=" + demand30Days + "]";
	}
	

}
