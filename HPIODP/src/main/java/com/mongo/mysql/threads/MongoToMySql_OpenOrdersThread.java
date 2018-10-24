package com.mongo.mysql.threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mongo.mysql.pojos.OpenOrdersRaw;
import com.mongo.mysql.service.DataTransferService;
@Service
@Scope("prototype")
public class MongoToMySql_OpenOrdersThread extends Thread 
{
	@Autowired
	private DataTransferService dataTransferService;
	@Override
	public synchronized void run() {
	dataTransferService.MigrateData("OpenOrdersRaw","File_Date","OPEN_ORDERS","File Date",OpenOrdersRaw.class,"id1");
	}
}
