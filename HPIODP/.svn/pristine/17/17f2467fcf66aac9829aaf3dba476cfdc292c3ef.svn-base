package com.io.common;


import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.io.services.forecast.ForeCastSerivce_IPS_HW;
import com.io.services.forecast.ForeCastService_IPS_Supplies;
import com.io.services.forecast.ForeCastService_LES_HW;
import com.io.services.forecast.ForeCastService_LES_Supplies;
import com.io.services.forecast.ForecastProcessedService;
import com.io.services.po.EoCommodityService;
import com.io.services.po.OpenOrdersPrintersService;


@Service
@Path("/forecast")
public class ForeCastResource {

	@Autowired
	private ForeCastSerivce_IPS_HW  foreCastSerivce_IPS_HW;
	
	@Autowired
	private ForeCastService_IPS_Supplies foreCastSerivce_IPS_Supplies; 
	
	@Autowired
	private ForeCastService_LES_HW foreCastService_LES_HW;
	
	@Autowired
	private ForeCastService_LES_Supplies foreCastService_LES_Supplies;
	
	@Autowired
	 private OpenOrdersPrintersService openOrdersPrintersService;
	@Autowired
	private EoCommodityService eoCommodityService;
	
	Logger log = Logger.getLogger(ForeCastResource.class);
	
	/**All forecast printers
	 * 
	 * Data
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/runForecast")
	public Response runForeCastData(){
		
		boolean status=false;
		//for ips-hw
		String[] ips_Hw_headers=new String[]{"Part Number","Family","Country","Status"};
		//for ips-supplies
			String[] ips_supp_headers = new String[]{"DETAILS PLANNING BOOK","APO Product","Initial"};
		//les hw
		String[] les_Hw_headers=new String[]{"Location","Planning Product","Country","Category","PL","Family","PPB","Monday Date"};
		//for les supplies
		String[] les_supp_headers=new String[]{"APO Product","Model","Plt","Size Code","T/S","Familia"};
		
		Map<Integer,String> ips_Hw_headersMap=new HashMap<Integer,String>();
		
		for (int i = 0; i < ips_Hw_headers.length; i++) {
			ips_Hw_headersMap.put(i,ips_Hw_headers[i].trim());
		}
		
		
		Map<Integer,String> ips_supp_headersMap=new HashMap<Integer,String>();
		for (int i = 0; i < ips_supp_headers.length; i++) {
			ips_supp_headersMap.put(i,ips_supp_headers[i].trim());
		}
		
		Map<Integer,String> les_Hw_headersMap=new HashMap<Integer,String>();
		
		for (int i = 0; i < les_Hw_headers.length; i++) {
			les_Hw_headersMap.put(i,les_Hw_headers[i].trim());
		}
		
		Map<Integer,String> les_supp_headersMap=new HashMap<Integer,String>();

		for (int i = 0; i < les_supp_headers.length; i++) {
			les_supp_headersMap.put(i,les_supp_headers[i].trim());
		}		

		log.info("All headers created");
		log.info("---------------------------------");
		log.info("Data Importing started");
		log.info("---------------------------------");
		log.info("IPS Hardware importing...............");
		try{
		  if(foreCastSerivce_IPS_HW.readFile(ips_Hw_headersMap)){
			  log.info("**************IPS Hardware imported successfully*************");
			  
			 
			  
			
			  log.info("**************IPS Hardware imported successfully*************");
			  log.info("IPS Supplies importing...............");
			  
			  if(foreCastSerivce_IPS_Supplies.readFile(ips_supp_headersMap)){
				  log.info("**************IPS Supplies imported successfully*************");
				  log.info("LES HardWare importing...............");
				  
				  if(foreCastService_LES_HW.readFile(les_Hw_headersMap)){
					  log.info("LES HardWare imported Successfully");
					  log.info("LES Supplies importing...............");
					  
					  if(foreCastService_LES_Supplies.readFile(les_supp_headersMap)){
				
						  log.info("LES Supplies imported Successfully");
				
						  //status = forecastProcessedService.saveForecastData();
			           log.info("Final table created successfully");
			}
			}
			  
			  }
		 }
		  }catch(Exception e){
			  e.printStackTrace();
			
			log.info("Error occured due to "+e.getMessage());
			return Response.ok("Error while processing ForecastData").build();
		}
		java.net.URI location = null;
	    try {
			location = new java.net.URI("http://localhost:8943/HPIODP/foreCastSuccess.jsp");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}  
	   return  Response.temporaryRedirect(location).build();
		
	}
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/printers")
	public Response getPrintersPage(){
		java.net.URI location = null;
	    try {
			location = new java.net.URI("http://localhost:8080/HPIODP/printers.jsp");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}  
	   return  Response.temporaryRedirect(location).build();
	
	}
	
	/**Open orders,Eo Commodity Data
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/openOrders")
	public Response runOpenOrderData(){
		java.net.URI location = null;
		try{
		log.info("Open orders data started");
		log.info("****************************************************");
		openOrdersPrintersService.downloadFile();
		log.info("Open orders data completed");
		
		log.info("****************************************************");
		log.info("EO commodity data started");
		log.info("****************************************************");
		eoCommodityService.uploadDatatoMysql();
		log.info("****************************************************");
		log.info("EO commodity data completed");
		location = new java.net.URI("http://localhost:8863/HPIODP/printersSeccess.jsp");
		}catch(Exception e){
			log.info("Error occured while importing open orders data  "+e.getMessage());
		}
		 return  Response.temporaryRedirect(location).build();
	}
	
}
