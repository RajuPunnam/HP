package com.mongo.mysql.threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mongo.mysql.pojos.OpenOrders_SkuAvPn;
import com.mongo.mysql.service.DataTransferService;

@Service
@Scope("prototype")
public class MongoToMySql_OpenOrdersSkuAvPnThread extends Thread 
{
	@Autowired
	private DataTransferService dataTransferService;
	@Override
	public synchronized void run() {
	dataTransferService.MigrateData("OpenOrders_SkuAvPn","fileDate","PO_ORDERS_SKU_AV_PN","File Date",OpenOrders_SkuAvPn.class,"id1");
   }
}
