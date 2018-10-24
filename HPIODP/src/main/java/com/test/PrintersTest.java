package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.io.services.po.OpenOrdersPrintersService;
import com.printers.service.AgingPrinterService;

public class PrintersTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:META-INF\\spring\\applicationContext.xml");
	
	
		/*OpenOrdersPrintersService openOrdersPrintersService = context.getBean(OpenOrdersPrintersService.class);
		openOrdersPrintersService.downloadFile();
	*/
		
		AgingPrinterService agingPrinterService = context.getBean(AgingPrinterService.class);
		agingPrinterService.downloadFtpFile();
	}
	
}
