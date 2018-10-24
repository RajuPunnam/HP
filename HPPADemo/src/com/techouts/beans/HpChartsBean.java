package com.techouts.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.techouts.dto.All_Skus_Availability;
import com.techouts.pojo.BUFamily;
import com.techouts.services.HpChartsService;

@ManagedBean
@SessionScoped
public class HpChartsBean {
	@ManagedProperty("#{hpChartsService}")
	private HpChartsService hpChartsService;
	private List<BUFamily> buQuantitylist;
	public List<All_Skus_Availability> all_Skus_AvailabilityList;
	public HpChartsService getHpChartsService() {
		return hpChartsService;
	}
	public void setHpChartsService(HpChartsService hpChartsService) {
		this.hpChartsService = hpChartsService;
	}
    @PostConstruct
	public void init() 
	{
    	buQuantitylist=hpChartsService.getBuAvailPercentage();
    	all_Skus_AvailabilityList=hpChartsService.getSkuAvialability();
	}

	public List<BUFamily> getBuQuantitylist() {
		return buQuantitylist;
	}
	public void setBuQuantitylist(List<BUFamily> buQuantitylist) {
		this.buQuantitylist = buQuantitylist;
	}
	public List<All_Skus_Availability> getAll_Skus_AvailabilityList() {
		return all_Skus_AvailabilityList;
	}
	public void setAll_Skus_AvailabilityList(
			List<All_Skus_Availability> all_Skus_AvailabilityList) {
		this.all_Skus_AvailabilityList = all_Skus_AvailabilityList;
	}
}
