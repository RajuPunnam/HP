package com.techouts.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="FamilyMaster")
@TypeAlias("FamilyMaster")
public class FamilyMaster {

    @Id
    private String id;
    @Field("BASE UNIT")
    private String baseUnit;
    @Field("Family")
    private String family;
    @Field("SubFamily")
    private String subFamily;
    @Field("FamilyGroup")
    private String familyGroup;
    @Field("ProductType")
    private String productType;
    
    
    //Setters and Getters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getBaseUnit() {
        return baseUnit;
    }
    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }
    public String getFamily() {
        return family;
    }
    public void setFamily(String family) {
        this.family = family;
    }
    
    public String getSubFamily() {
        return subFamily;
    }
    public void setSubFamily(String subFamily) {
        this.subFamily = subFamily;
    }
    public String getFamilyGroup() {
        return familyGroup;
    }
    public void setFamilyGroup(String familyGroup) {
        this.familyGroup = familyGroup;
    }
    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    
    
    
}
