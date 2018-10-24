package com.techouts.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="SKUBOM_1.1")
@TypeAlias("SkuBom")
public class SkuBom {
	

    @Id
    private String id;
    @Field("SKUID")
    private String skuId;
    @Field("FAMILY")
    private String family;
    @Field("PL")
    private String pl;
    @Field("AC ADAPTER")
    private String acAdapter;
    @Field("AC ADAPTER45W")
    private String acAdapter5w;
    @Field("BASE UNIT")
    private String baseUnit;
    @Field("CABLES")
    private String cables;
    @Field("CHASSIS")
    private String chassis;
    @Field("CPU")
    private String cpu;
    @Field("3RD-PARTY")
    private String thirdParty;
    @Field("GRAPHIC CARD")
    private String graphicCard;
    @Field("HDD")
    private String hdd;
    @Field("KEYBOARD")
    private String keyboard;
    @Field("MODEM")
    private String modem;
    @Field("KIT MOUSE / KEYBOARD")
    private String mskb;
    @Field("LABEL")
    private String label;
    @Field("MEMORY")
    private String memory;
    @Field("BATTERY")
    private String battery;
    @Field("MOUSE")
    private String mouse;
    @Field("ODD")
    private String odd;
    @Field("SERVICES")
    private String services;
    @Field("MISC")
    private String mesc;
    @Field("ODDUSB")
    private String oddUsb;
    @Field("OPTION")
    private String option;
    @Field("PLD")
    private String pld;
    @Field("N#A")
    private String notAvble;
    @Field("PLD2")
    private String pld2;
    @Field("POWER CORD")
    private String powerCard;
    @Field("LCD")
    private String lcd;
    @Field("POWER SUPPLY")
    private String powerSupply;
    @Field("SSD")
    private String ssd;
    @Field("SSD Local")
    private String ssdLocal;
    @Field("ODM KIT")
    private String odmKit;
    @Field("SSHD")
    private String sshd;
    @Field("WLAN")
    private String wlan;
    @Field("OS")
    private String os;
    
    
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSkuId() {
        return skuId;
    }
    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
    public String getFamily() {
        return family;
    }
    public void setFamily(String family) {
        this.family = family;
    }
    public String getPl() {
        return pl;
    }
    public void setPl(String pl) {
        this.pl = pl;
    }
    public String getAcAdapter() {
        return acAdapter;
    }
    public void setAcAdapter(String acAdapter) {
        this.acAdapter = acAdapter;
    }
    public String getAcAdapter5w() {
        return acAdapter5w;
    }
    public void setAcAdapter5w(String acAdapter5w) {
        this.acAdapter5w = acAdapter5w;
    }
    public String getBaseUnit() {
        return baseUnit;
    }
    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }
    public String getCables() {
        return cables;
    }
    public void setCables(String cables) {
        this.cables = cables;
    }
    public String getChassis() {
        return chassis;
    }
    public void setChassis(String chassis) {
        this.chassis = chassis;
    }
    public String getCpu() {
        return cpu;
    }
    public void setCpu(String cpu) {
        this.cpu = cpu;
    }
    public String getThirdParty() {
        return thirdParty;
    }
    public void setThirdParty(String thirdParty) {
        this.thirdParty = thirdParty;
    }
    public String getGraphicCard() {
        return graphicCard;
    }
    public void setGraphicCard(String graphicCard) {
        this.graphicCard = graphicCard;
    }
    public String getHdd() {
        return hdd;
    }
    public void setHdd(String hdd) {
        this.hdd = hdd;
    }
    public String getKeyboard() {
        return keyboard;
    }
    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }
    
    public String getModem() {
        return modem;
    }
    public void setModem(String modem) {
        this.modem = modem;
    }
    public String getMskb() {
        return mskb;
    }
    public void setMskb(String mskb) {
        this.mskb = mskb;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getMemory() {
        return memory;
    }
    public void setMemory(String memory) {
        this.memory = memory;
    }
    public String getBattery() {
        return battery;
    }
    public void setBattery(String battery) {
        this.battery = battery;
    }
    public String getMouse() {
        return mouse;
    }
    public void setMouse(String mouse) {
        this.mouse = mouse;
    }
    public String getOdd() {
        return odd;
    }
    public void setOdd(String odd) {
        this.odd = odd;
    }
    public String getServices() {
        return services;
    }
    public void setServices(String services) {
        this.services = services;
    }
    public String getMesc() {
        return mesc;
    }
    public void setMesc(String mesc) {
        this.mesc = mesc;
    }
    public String getOddUsb() {
        return oddUsb;
    }
    public void setOddUsb(String oddUsb) {
        this.oddUsb = oddUsb;
    }
    public String getOption() {
        return option;
    }
    public void setOption(String option) {
        this.option = option;
    }
    public String getPld() {
        return pld;
    }
    public void setPld(String pld) {
        this.pld = pld;
    }
    public String getNotAvble() {
        return notAvble;
    }
    public void setNotAvble(String notAvble) {
        this.notAvble = notAvble;
    }
    public String getPld2() {
        return pld2;
    }
    public void setPld2(String pld2) {
        this.pld2 = pld2;
    }
    public String getPowerCard() {
        return powerCard;
    }
    public void setPowerCard(String powerCard) {
        this.powerCard = powerCard;
    }
    public String getLcd() {
        return lcd;
    }
    public void setLcd(String lcd) {
        this.lcd = lcd;
    }
    public String getPowerSupply() {
        return powerSupply;
    }
    public void setPowerSupply(String powerSupply) {
        this.powerSupply = powerSupply;
    }
    public String getSsd() {
        return ssd;
    }
    public void setSsd(String ssd) {
        this.ssd = ssd;
    }
    public String getSsdLocal() {
        return ssdLocal;
    }
    public void setSsdLocal(String ssdLocal) {
        this.ssdLocal = ssdLocal;
    }
    public String getOdmKit() {
        return odmKit;
    }
    public void setOdmKit(String odmKit) {
        this.odmKit = odmKit;
    }
    public String getSshd() {
        return sshd;
    }
    public void setSshd(String sshd) {
        this.sshd = sshd;
    }
    public String getWlan() {
        return wlan;
    }
    public void setWlan(String wlan) {
        this.wlan = wlan;
    }
    public String getOs() {
        return os;
    }
    public void setOs(String os) {
        this.os = os;
    }
    
    
    @Override
    public String toString() {
	return "SkuBom [id=" + id + ", skuId=" + skuId + ", family=" + family
		+ ", pl=" + pl + ", acAdapter=" + acAdapter + ", acAdapter5w="
		+ acAdapter5w + ", baseUnit=" + baseUnit + ", cables=" + cables
		+ ", chassis=" + chassis + ", cpu=" + cpu + ", thirdParty="
		+ thirdParty + ", graphicCard=" + graphicCard + ", hdd=" + hdd
		+ ", keyboard=" + keyboard + ", modem=" + modem + ", mskb="
		+ mskb + ", label=" + label + ", memory=" + memory
		+ ", battery=" + battery + ", mouse=" + mouse + ", odd=" + odd
		+ ", services=" + services + ", mesc=" + mesc + ", oddUsb="
		+ oddUsb + ", option=" + option + ", pld=" + pld
		+ ", notAvble=" + notAvble + ", pld2=" + pld2 + ", powerCard="
		+ powerCard + ", lcd=" + lcd + ", powerSupply=" + powerSupply
		+ ", ssd=" + ssd + ", ssdLocal=" + ssdLocal + ", odmKit="
		+ odmKit + ", sshd=" + sshd + ", wlan=" + wlan + ", os=" + os
		+ "]";
    }
    
    
   
	
	
	
	
	
}
