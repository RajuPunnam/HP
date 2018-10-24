package com.mongo.mysql.threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mongo.mysql.pojos.ShipmentsToHpPojo;
import com.mongo.mysql.service.DataTransferService;

@Service
@Scope("prototype")
public class MongoToMySql_InvoicesThread extends Thread 
{
	@Autowired
	private DataTransferService dataTransferService;
	@Override
	public synchronized void run() {
		dataTransferService.MigrateData("ShipmentsToHpPojo","fileDate","INVOICES","File Date",ShipmentsToHpPojo.class,"id1");
	}
}
