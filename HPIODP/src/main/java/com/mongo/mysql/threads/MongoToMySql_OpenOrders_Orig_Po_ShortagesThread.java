package com.mongo.mysql.threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mongo.mysql.pojos.Openorders_Org_Po_Shortage;
import com.mongo.mysql.service.DataTransferService;

@Service
@Scope("prototype")
public class MongoToMySql_OpenOrders_Orig_Po_ShortagesThread extends Thread 
{
	@Autowired
	private DataTransferService dataTransferService;
	@Override
	public synchronized void run() {
	dataTransferService.MigrateData("Openorders_Org_Po_Shortage","fileDate","ORIG_PO_SHORTAGE","File Date",Openorders_Org_Po_Shortage.class,"id1");
   }
}
