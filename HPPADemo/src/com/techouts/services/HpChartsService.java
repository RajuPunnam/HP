package com.techouts.services;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.techouts.dao.HPChartsDao;
import com.techouts.dto.All_Skus_Availability;
import com.techouts.pojo.BUFamily;
import com.techouts.pojo.BUFamilyAvailability;

@Service
public class HpChartsService {
	
	@Autowired
	private HPChartsDao hpChartsDao;
	public List<BUFamily> getBuAvailPercentage()
	{	
		return hpChartsDao.getBuQuantity();
	}

	public List<All_Skus_Availability> getSkuAvialability()
	{
		return hpChartsDao.getSkuAvilabilty();
	}
}
