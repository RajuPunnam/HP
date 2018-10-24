package com.mongo.mysql.threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mongo.mysql.pojos.Clean;
import com.mongo.mysql.service.DataTransferService;

@Service
@Scope("prototype")
public class MongoToMySql_OpenOrdersCleanThread extends Thread 
{
	@Autowired
	private DataTransferService dataTransferService;
	@Override
	public synchronized void run() {
	dataTransferService.MigrateData("Clean","fileDate","OPEN_ORDER_CLEAN","File Date",Clean.class,"id1");
	}
}
