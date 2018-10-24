package com.techouts.services;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.pojo.CartItems;
import com.techouts.re.services.SkuAvalibility;

@Service
@Path("/cache")
public class Resource {
	private static final Logger LOG=Logger.getLogger(Resource.class);
	@Autowired
	private CacheService cacheService;
	@Autowired
	private SkuAvalibility skuAvalibility;
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/refresh")
	public Response refresh(){
		LOG.info("Cache Refreshing");
		cacheService.service();
		LOG.info("Cache Refreshed");
		for(CartItems s:cacheService.getAllUsersCart()){
			LOG.info(s);
		}
		return  Response.ok("Cache Refreshed Successfully").build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/dataRefreshWithBom")
	public Response dataRefreshWithBom(){
		try{
			skuAvalibility.generateFinalQty();	
			skuAvalibility.createPipeLineCollection();
			skuAvalibility.generateSkuAvilability(true);
			cacheService.service();
		}catch(Exception e){
			return Response.ok(e.toString()).build();
		}
		return  Response.ok("Data recaliculated sucessfully BOM").build();
	}
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/dataRefresh")
	public Response dataRefresh(){
		try{
			skuAvalibility.createPipeLineCollection();
			skuAvalibility.generateSkuAvilability(true);
			cacheService.service();
		}catch(Exception e){
			return Response.ok(e.toString()).build();
		}
		return  Response.ok("Data sucessfully recaliculated").build();
	}
	
	
	
}
