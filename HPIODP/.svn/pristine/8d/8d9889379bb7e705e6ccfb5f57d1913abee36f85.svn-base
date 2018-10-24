package com.io.common;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.io.services.po.PORedistrubutionService;
@Component
@Scope("prototype")
public class POFromHpgenerateOrginalOrderShortag extends Thread{

	@Autowired
	private PORedistrubutionService pORedistrubutionService;
	@Override
	public synchronized void run(){
		try {
			pORedistrubutionService.generateOrginalOrderShortag();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
	}
}