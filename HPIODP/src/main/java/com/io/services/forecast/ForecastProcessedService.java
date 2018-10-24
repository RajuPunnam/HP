package com.io.services.forecast;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.io.dao.ForecastIps_SuppliesDao;
import com.io.pojos.ForeCastPrintProcessed;
import com.io.pojos.ForeCast_IPS_HW;
import com.io.pojos.ForeCast_IPS_Supplies;
import com.io.pojos.ForeCast_LES_Supplies;
import com.io.pojos.ForeCast_Les_HW;


@Service
public class ForecastProcessedService {
	
	@Autowired
    private ForecastIps_SuppliesDao	forecastIps_SuppliesDao;
	
	
	
	public boolean saveForecastData(){
		boolean status = false;
		List<ForeCastPrintProcessed> foreCastPrintProcessedList = new ArrayList<ForeCastPrintProcessed>();
		List<?> ips_hwlist =	forecastIps_SuppliesDao.getDatafromforeCast("fcst_print_ips_hw");
		
		for (Object obj : ips_hwlist) {
			ForeCast_IPS_HW foreCast_IPS_HW = (ForeCast_IPS_HW)obj;
			ForeCastPrintProcessed foreCastPrintProcessed = new ForeCastPrintProcessed();
			foreCastPrintProcessed.setSku(foreCast_IPS_HW.getPartNumber());
			foreCastPrintProcessed.setFcstDate(foreCast_IPS_HW.getForecast_Date());
			foreCastPrintProcessed.setFcstGD(foreCast_IPS_HW.getFile_Date());
			foreCastPrintProcessed.setCategory(foreCast_IPS_HW.getCategory());
			foreCastPrintProcessed.setQty(foreCast_IPS_HW.getQty());
			foreCastPrintProcessed.setType(foreCast_IPS_HW.getFileType());
			foreCastPrintProcessed.setWeek(foreCast_IPS_HW.getForeCastWeek());
		   // System.out.println(foreCastPrintProcessed);
		    foreCastPrintProcessedList.add(foreCastPrintProcessed);
		}
		
		List<?> ips_Suplieslist =	forecastIps_SuppliesDao.getDatafromforeCast("fcst_print_ips_supplies");
	
	
	    for (Object obj : ips_Suplieslist) {
		   ForeCast_IPS_Supplies foreCast_IPS_Supplies = (ForeCast_IPS_Supplies)obj;
		   ForeCastPrintProcessed foreCastPrintProcessed = new ForeCastPrintProcessed();
		   foreCastPrintProcessed.setSku(foreCast_IPS_Supplies.getApo_product());
			foreCastPrintProcessed.setFcstDate(foreCast_IPS_Supplies.getFcstDate());
			foreCastPrintProcessed.setFcstGD(foreCast_IPS_Supplies.getFcstGD());
			foreCastPrintProcessed.setCategory(foreCast_IPS_Supplies.getCategory());
			foreCastPrintProcessed.setQty(foreCast_IPS_Supplies.getFcstQty());
			foreCastPrintProcessed.setType(foreCast_IPS_Supplies.getFileType());
			foreCastPrintProcessed.setWeek(foreCast_IPS_Supplies.getForecastWeek());
		   // System.out.println(foreCastPrintProcessed);
		    foreCastPrintProcessedList.add(foreCastPrintProcessed);
	    }
	

	    List<?> les_hwList =	forecastIps_SuppliesDao.getDatafromforeCast("fcst_print_les_hw");
		
		
	    for (Object obj : les_hwList) {
		   ForeCast_Les_HW foreCast_Les_Hw = (ForeCast_Les_HW)obj;
		   ForeCastPrintProcessed foreCastPrintProcessed = new ForeCastPrintProcessed();
		   foreCastPrintProcessed.setSku(foreCast_Les_Hw.getPlanningProduct());
			foreCastPrintProcessed.setFcstDate(foreCast_Les_Hw.getFcstDate());
			foreCastPrintProcessed.setFcstGD(foreCast_Les_Hw.getFcstGD());
			foreCastPrintProcessed.setCategory(foreCast_Les_Hw.getCategory1());
			foreCastPrintProcessed.setQty(foreCast_Les_Hw.getFcstQty());
			foreCastPrintProcessed.setType(foreCast_Les_Hw.getType());
			foreCastPrintProcessed.setWeek(foreCast_Les_Hw.getForecastWeek());
		   // System.out.println(foreCastPrintProcessed);
		    foreCastPrintProcessedList.add(foreCastPrintProcessed);
	    }
	    
          List<?> les_suppliesList =	forecastIps_SuppliesDao.getDatafromforeCast("fcst_print_les_supplies");
		
		
	    for (Object obj : les_suppliesList) {
		   ForeCast_LES_Supplies foreCast_LES_Supplies = (ForeCast_LES_Supplies)obj;
		   ForeCastPrintProcessed foreCastPrintProcessed = new ForeCastPrintProcessed();
		   foreCastPrintProcessed.setSku(foreCast_LES_Supplies.getAPOProduct());
			foreCastPrintProcessed.setFcstDate(foreCast_LES_Supplies.getForecastDate());
			foreCastPrintProcessed.setFcstGD(foreCast_LES_Supplies.getFileDate());
			foreCastPrintProcessed.setCategory(foreCast_LES_Supplies.getCategory());
			foreCastPrintProcessed.setQty(foreCast_LES_Supplies.getQty());
			foreCastPrintProcessed.setType(foreCast_LES_Supplies.getFileType());
			foreCastPrintProcessed.setWeek(foreCast_LES_Supplies.getForecastWeek());
		    //System.out.println(foreCastPrintProcessed);
		    foreCastPrintProcessedList.add(foreCastPrintProcessed);	   
	    }
	
	 status =  forecastIps_SuppliesDao.insertForeCastProcessedData(foreCastPrintProcessedList);
	
	 if(status)
		 System.out.println("Completed");
	return status;
	}
}
