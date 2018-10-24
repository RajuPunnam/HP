package com.io.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.io.services.forecast.ForeCastService;
import com.io.services.po.POService;

@Service
@Scope("prototype")
public class OpenOrders_Shortage_SkuAvPnThread extends Thread{
	@Autowired
	private POService pOService;
	@Override
	public synchronized void run(){
		Map<String,String> buMap=new HashMap<String, String>();
		   buMap.put("5X", "WKS");
		   buMap.put("4T", "CPC");
		   buMap.put("8J", "BNB");
		   buMap.put("TB", "BNB");
		   buMap.put("AN", "BNB");
		   buMap.put("%W%", "WKS");
		   buMap.put("6J", "CPC");
		   buMap.put("KV", "CNB");
		   buMap.put("5U", "BPC");
		   buMap.put("AIO", "BPC");
		   buMap.put("TE CHAMEI", "BNB");
		   buMap.put("6U", "BNB");
		   buMap.put("TA", "BNB");
		   buMap.put("7F", "BPC");
		
		pOService.processPO(buMap);
	}

}
