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

import com.inventory.utill.DoubleUtill;

@Document(collection="MASTER_BOM_FLEX")
@Entity
@Table(name="master_bom_flex_lt_tbl")
@Access(value=AccessType.FIELD)
public class MASTER_BOM_FLEX {
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="ID",unique = true, nullable = false)
	private int id1;
	@Field("Date")
	@Column(name="Date")
	private String Date;
	@Field("Flat BOM #")
	@Column(name="`Flat BOM #`")
	private String flatBom;
	@Field("Part")
	@Column(name="Part")
	private String part;
	@Field("Site")
	@Column(name="Site")
	private String site;      //-----------diff  int
	@Field("Level")
	@Column(name="Level")
	private String Level;     //-----------diff  int
	@Field("Flat Comp")
	@Column(name="`Flat Comp`")
	private String flatComp;
	@Field("Description")
	@Column(name="`Description`")
	private String description;
	@Field("Supply Type")
	@Column(name="`Supply Type`")
	private String supply_Type;
	@Field("BOM Type")
	@Column(name="`BOM Type`")
	private String bom_Type;
	@Field("Path?")
	@Column(name="`Path`")
	private String path;
	@Field("Quantity Per")
	@Column(name="`Quantity Per`")
	private double quantity_Per;
	@Field("Quantity Per1")
	@Column(name="`Quantity Per1`")
	private double quantityPer1;//-----------diff   string
	@Field("UoM")
	@Column(name="`UoM`")
	private String uom;
	@Field("This Level")
	@Column(name="`This Level`")
	private double this_Level;  //------diff int
	@Field("Cumulative")
	@Column(name="`Cumulative`")
	private double cumulative;  //------------diff string
	@Field("Unit Cost")
	@Column(name="`Unit Cost`")
	private double unit_Cost; 
	@Field("Code")
	@Column(name="`Code`")
	private String code;
	@Field("Total Demand")
	@Column(name="`Total Demand`")
	private double total_Demand;//--------diff int
	@Field("On-hand")
	@Column(name="`On-hand`")
	private double on_Hand;     //--------diff string
	@Field("Receipts")
	@Column(name="`Receipts`")
	private double receipts;    //------diff int
	@Field("Orders")
	@Column(name="`Orders`")
	private double orders;      //--------diff string
	@Field("Change")
	@Column(name="`Change`")
	private String change;
	@Field("In")
	@Column(name="`In`")
	private String in;
	@Field("Out")
	@Column(name="`Out`")
	private String out;
	@Field("Planner")
	@Column(name="`Planner`")
	private String planner;
	@Field("Buyer")
	@Column(name="`Buyer`")
	private String buyer;
	@Field("Supplier")
	@Column(name="`Supplier`")
	private String supplier;
	@Field("Group")
	@Column(name="`Group`")
	private String group;
	@Field("Site1")
	@Column(name="`Site1`")
	private double site1;     //------diff String
	@Field("Sequence")
	@Column(name="`Sequence`")
	private double sequence;  //------diff int
	@Field("Target")
	@Column(name="`Target`")
	private double target;    //------diff int
	
	
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getFlatBom() {
		return flatBom;
	}
	public void setFlatBom(String flatBom) {
		this.flatBom = flatBom;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getLevel() {
		return Level;
	}
	public void setLevel(String level) {
		Level = level;
	}
	public String getFlatComp() {
		return flatComp;
	}
	public void setFlatComp(String flatComp) {
		this.flatComp = flatComp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSupply_Type() {
		return supply_Type;
	}
	public void setSupply_Type(String supply_Type) {
		this.supply_Type = supply_Type;
	}
	public String getBom_Type() {
		return bom_Type;
	}
	public void setBom_Type(String bom_Type) {
		this.bom_Type = bom_Type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public double getQuantity_Per() {
		return quantity_Per;
	}
	public void setQuantity_Per(Object obj) {
		this.quantity_Per = DoubleUtill.getValue(obj);
	}
	public double getQuantityPer1() {
		return quantityPer1;
	}
	public void setQuantityPer1(Object obj) {
		this.quantityPer1 = DoubleUtill.getValue(obj);
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public double getThis_Level() {
		return this_Level;
	}
	public void setThis_Level(Object obj) {
		this.this_Level = DoubleUtill.getValue(obj);
	}
	public double getCumulative() {
		return cumulative;
	}
	public void setCumulative(Object obj) {
		this.cumulative = DoubleUtill.getValue(obj);
	}
	public double getUnit_Cost() {
		return unit_Cost;
	}
	public void setUnit_Cost(Object obj) {
		this.unit_Cost =DoubleUtill.getValue(obj);
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public double getTotal_Demand() {
		return total_Demand;
	}
	public void setTotal_Demand(Object obj) {
		this.total_Demand = DoubleUtill.getValue(obj);
	}
	public double getOn_Hand() {
		return on_Hand;
	}
	public void setOn_Hand(Object obj) {
		this.on_Hand = DoubleUtill.getValue(obj);
	}
	public double getReceipts() {
		return receipts;
	}
	public void setReceipts(Object obj) {
		this.receipts = DoubleUtill.getValue(obj);
	}
	public double getOrders() {
		return orders;
	}
	public void setOrders(Object obj) {
		this.orders = DoubleUtill.getValue(obj);
	}
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}
	public String getIn() {
		return in;
	}
	public void setIn(String in) {
		this.in = in;
	}
	public String getOut() {
		return out;
	}
	public void setOut(String out) {
		this.out = out;
	}
	public String getPlanner() {
		return planner;
	}
	public void setPlanner(String planner) {
		this.planner = planner;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public double getSite1() {
		return site1;
	}
	public void setSite1(Object obj) {
		this.site1 = DoubleUtill.getValue(obj);
	}
	public double getSequence() {
		return sequence;
	}
	public void setSequence(Object obj) {
		this.sequence = DoubleUtill.getValue(obj);
	}
	public double getTarget() {
		return target;
	}
	public void setTarget(Object obj) {
		this.target = DoubleUtill.getValue(obj);
	}
    

}
