package com.mongo.mysql.threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mongo.mysql.pojos.DOI_IO;
import com.mongo.mysql.service.DataTransferService;
@Service
@Scope("prototype")
public class MongoToMySql_DOI_IOThread extends Thread {
	@Autowired
	private DataTransferService dataTransferService;
	@Override
	public synchronized void run() {
		dataTransferService.MigrateData("DOI_IO","date","DOI_IO","Date",DOI_IO.class,"id1");
	}
	
}
