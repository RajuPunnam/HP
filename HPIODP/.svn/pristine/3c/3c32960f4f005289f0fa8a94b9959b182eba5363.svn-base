package com.mongo.mysql.threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mongo.mysql.pojos.InvPNConversion;
import com.mongo.mysql.service.DataTransferService;

@Service
@Scope("prototype")
public class MongoToMySql_InvoicesAvPnThread extends Thread 
{
	@Autowired
	private DataTransferService dataTransferService;
	@Override
	public synchronized void run() {
	dataTransferService.MigrateData("InvPNConversion","fileDate","INVOICES_AV_PN","File Date",InvPNConversion.class,"id1");
	}
}
