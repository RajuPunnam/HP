package com.io.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.inventory.service.InvoiceService;
//@Service
@Scope("prototype")
public class InvoiceThread extends Thread{
	//@Autowired
	private InvoiceService invoiceService;
	@Override
	public synchronized void run() {
		invoiceService.invoiceProcess2();
	}

}
