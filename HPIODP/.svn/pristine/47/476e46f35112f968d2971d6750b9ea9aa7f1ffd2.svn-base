package com.mongo.mysql.threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mongo.mysql.pojos.DOI;
import com.mongo.mysql.service.DataTransferService;
@Service
@Scope("prototype")
public class MongoToMySql_DOIThread extends Thread {
	@Autowired
	private DataTransferService dataTransferService;
	@Override
	public synchronized void run() {
		dataTransferService.MigrateData("DOI","date","DOI","Date",DOI.class,"id1");
	}
	
}
