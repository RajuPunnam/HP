package com.io.pojos;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="")
@TypeAlias("POOrder")
public class POOrder implements Serializable, Cloneable {

    @Id
    private String id;
    
    @Field("File Date")
    private String fileDate;
    
    @Field("AdjustedDate")
    private String adjustedDate;
    
    @Field("PORecDate")
    private String poRecDate;
    @Field("PO")
    private String po;
    @Field("SKU")
    private String sku;
    
    @Field("customer")
    private String customer;
    @Field("PL")
    private String pl;
    @Field("family")
    private String family;
    @Field("avId")
    private String avId;
    
   
    @Field("avCons")
    private double avCons;
    @Field("skuCons")
    private double skuCons;
    
    //additional columns
    @Field("partID")
    private String partId;
    @Field("QTY")
    private double quantity;
    @Field("partLevel")
    private String partLevel;
    @Field("Flag")
    private boolean flag;
    @Field("ImportLocal")
    private String importLocal;
    @Field("MPC")
    private String mpc;
    @Field("SkuPrice")
    private double skuPrice;
    @Field("AvPrice")
    private double avPrice;
    @Field("PartPrice")
    private double partPrice;
    @Field("Commodity")
    private String commodity;
    @Field("Supplier")
    private String supplier;
    @Field("BU")
    private String bu;
    @Field("DateObj")
    private Date dateobj;
    @Field("SO")
    private String SO;
    @Field("SKUQty_Del")
    private double SKUQty_Del;
    @Field("AVQty_Del")
    private double AVQty_Del;
    @Field("PNQty_Del")
    private double PNQty_Del;
    
    public POOrder() {
	}
    
    public POOrder(POOrder pOOrder){
    	this.adjustedDate = pOOrder.adjustedDate;
    	this.bu = pOOrder.bu;
    	this.family = pOOrder.family;
    	this.fileDate = pOOrder.fileDate;
    	this.pl = pOOrder.pl;
    	this.po = pOOrder.po;
    	this.poRecDate = pOOrder.poRecDate;
    	this.sku = pOOrder.sku;
    	this.skuCons = pOOrder.skuCons;
    	this.SO = pOOrder.SO;
    	this.skuPrice = pOOrder.skuPrice;
    	this.SKUQty_Del = pOOrder.SKUQty_Del;
    }
    
    public String getSO() {
    	if(SO!=null)return SO.trim();
    	else
		return SO;
	}

	public void setSO(String sO) {
		SO = sO;
	}

	public String getBu() {
		if(bu!=null)return bu.trim();
		else
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public Date getDateobj() {
		return dateobj;
	}

	public void setDateobj(Date dateobj) {
		this.dateobj = dateobj;
	}

	public String getSupplier() {
		if(supplier!=null)return supplier.trim();
		else
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getCommodity() {
		if(commodity!=null)return commodity.trim();
		else
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

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

	public String getMpc() {
		if(mpc!=null)return mpc.trim();
		else
		return mpc;
	}

	public void setMpc(String mpc) {
		this.mpc = mpc;
	}

	public Object clone()throws CloneNotSupportedException{  
	return super.clone();  
    }  
    
    public double getSkuCons() {
		return skuCons;
	}

	public void setSkuCons(double skuCons) {
		this.skuCons = skuCons;
	}

	public String getId() {
		if(id!=null)return id.trim();
		else
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPoRecDate() {
    	if(poRecDate!=null)return poRecDate.trim();
    	else
        return poRecDate;
    }
    public void setPoRecDate(String poRecDate) {
        this.poRecDate = poRecDate;
    }
    public String getPo() {
    	if(po!=null)return po.trim();
    	else
        return po;
    }
    public void setPo(String po) {
        this.po = po;
    }
    public String getSku() {
    	if(sku!=null)return sku.trim();
    	else
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
    public String getCustomer() {
    	if(customer!=null)return customer.trim();
    	else
        return customer;
    }
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    public String getPl() {
    	if(pl!=null)return pl.trim();
    	else
        return pl;
    }
    public void setPl(String pl) {
        this.pl = pl;
    }
    public String getFamily() {
    	if(family!=null)return family.trim();
    	else
        return family;
    }
    public void setFamily(String family) {
        this.family = family;
    }
    public String getAvId() {
    	if(avId!=null)return avId.trim();
    	else
        return avId;
    }
    public void setAvId(String avId) {
        this.avId = avId;
    }
    
    public double getAvCons() {
        return avCons;
    }
    public void setAvCons(double avCons) {
        this.avCons = avCons;
    }
    public String getPartId() {
    	if(partId!=null)return partId.trim();
    	else
        return partId;
    }
    public void setPartId(String partId) {
        this.partId = partId;
    }
    
    public boolean isFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public String getImportLocal() {
    	if(importLocal!=null)return importLocal.trim();
    	else
        return importLocal;
    }
    public void setImportLocal(String importLocal) {
        this.importLocal = importLocal;
    }
    
    public String getFileDate() {
    	if(fileDate!=null)return fileDate.trim();
    	else
        return fileDate;
    }
    

    public String getPartLevel() {
    	if(partLevel!=null)return partLevel.trim();
    	else
		return partLevel;
	}


	public void setPartLevel(String partLevel) {
		this.partLevel = partLevel;
	}


	public void setFileDate(String fileDate) {
        this.fileDate = fileDate;
    }


    public String getAdjustedDate() {
    	if(adjustedDate!=null)return adjustedDate.trim();
    	else
        return adjustedDate;
    }


    public void setAdjustedDate(String adjustedDate) {
        this.adjustedDate = adjustedDate;
    }
    
    

    public double getQuantity() {
		return quantity;
	}


	public void setQuantity(double quantity) {
		this.quantity = quantity;
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
		return "POOrder [id=" + id + ", fileDate=" + fileDate + ", adjustedDate=" + adjustedDate + ", poRecDate="
				+ poRecDate + ", po=" + po + ", sku=" + sku + ", customer=" + customer + ", pl=" + pl + ", family="
				+ family + ", avId=" + avId + ", avCons=" + avCons + ", skuCons=" + skuCons + ", partId=" + partId
				+ ", quantity=" + quantity + ", partLevel=" + partLevel + ", flag=" + flag + ", importLocal="
				+ importLocal + ", mpc=" + mpc + ", skuPrice=" + skuPrice + ", avPrice=" + avPrice + ", partPrice="
				+ partPrice + ", commodity=" + commodity + ", supplier=" + supplier + ", bu=" + bu + ", dateobj="
				+ dateobj + "]";
	}


   
   
   
    
    
    
    
}
