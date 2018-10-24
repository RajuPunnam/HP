package com.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.io.services.forecast.ForeCastSerivce_IPS_HW;
import com.io.services.forecast.ForeCastService_IPS_Supplies;
import com.io.services.forecast.ForeCastService_LES_HW;
import com.io.services.forecast.ForeCastService_LES_Supplies;
import com.io.services.forecast.ForecastProcessedService;
import com.io.services.po.EoCommodityService;

public class ForecastTest {

	public static void main(String[] args) {
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:META-INF\\spring\\applicationContext.xml");
			
			ForeCastSerivce_IPS_HW foreCastSerivce_IPS_HW=context.getBean(ForeCastSerivce_IPS_HW.class);
			ForeCastService_IPS_Supplies foreCastIpssuppliesService=context.getBean(ForeCastService_IPS_Supplies.class);
			
			ForeCastService_LES_HW foreCastService_LES_HW=context.getBean(ForeCastService_LES_HW.class);
			ForeCastService_LES_Supplies foreCastService_LES_Supplies = context.getBean(ForeCastService_LES_Supplies.class);
			ForecastProcessedService forecastProcessedService =context.getBean(ForecastProcessedService.class);
			EoCommodityService eoCommodityService = context.getBean(EoCommodityService.class);
			/*System.out.println("------------iam in main starting call------------>");
			readAndWriteUtill.TransferData();
			System.out.println("------------completed----------------------------->"+new Date());*/
          //for ips-hw
			String[] xlheaders=new String[]{"Part Number","Family","Country","Status"};
			
			
			//for ips-supplies
			//	String[] xlheaders = new String[]{"DETAILS PLANNING BOOK","APO Product","Initial"};
			
			//les hw
			//String[] xlheaders=new String[]{"Location","Planning Product","Country","Category","PL","Family","PPB","Monday Date"};
	
			
			
			//for les supplies
			//String[] xlheaders=new String[]{"APO Product","Model","Plt","Size Code","T/S","Familia"};
			
			
			
			
			Map<Integer,String> xlheadersMap=new HashMap<Integer,String>();
			
			for (int i = 0; i < xlheaders.length; i++) {
				xlheadersMap.put(i,xlheaders[i].trim());
			System.out.println(xlheaders[i].trim());
			}
			
			foreCastSerivce_IPS_HW.readFile(xlheadersMap);
			//foreCastIpssuppliesService.readFile(xlheadersMap);
			//foreCastService_LES_HW.readFile(xlheadersMap);
			//foreCastService_LES_Supplies.readFile(xlheadersMap);
			//forecastProcessedService.saveForecastData();
        	
			//eoCommodityService.uploadDatatoMysql();
			
	}

}
