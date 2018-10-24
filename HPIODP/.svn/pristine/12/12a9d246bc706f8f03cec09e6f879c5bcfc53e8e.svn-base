package com.io.common;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import com.io.services.po.PORedistrubutionService;


public class POFromHpconvertSKUtoAVtoPN extends Thread{
	
	@Autowired
	private PORedistrubutionService pORedistrubutionService;
	@Override
	public synchronized void run(){
		try {
			Thread.sleep(5*60*1000);
			pORedistrubutionService.convertSKUtoAVtoPN();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
}