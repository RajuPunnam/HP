package com.techouts.pojo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="POLeadTime_1.1")
@TypeAlias("POLeadTime")
public class POLeadTime {

    @Id
    private String id;
    @Field("item")
    private String partId;
    @Field("Qty")
    private int quantity;
    @Field("ETD")
    private String estDate;
    @Field("Confirmed_delivery")
    private String confDate;
    
    private double leadTime;
    private Date confirmDate;
    private Date estimateDate;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPartId() {
        return partId;
    }
    public void setPartId(String partId) {
        this.partId = partId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getEstDate() {
        return estDate;
    }
    public void setEstDate(String estDate) {
        this.estDate = estDate;
    }
    public String getConfDate() {
        return confDate;
    }
    public void setConfDate(String confDate) {
        this.confDate = confDate;
    }
    public double getLeadTime() {
        return leadTime;
    }
    public void setLeadTime(double leadTime) {
        this.leadTime = leadTime;
    }
    public Date getConfirmDate() {
        return confirmDate;
    }
    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }
    public Date getEstimateDate() {
        return estimateDate;
    }
    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }
    
    
}
