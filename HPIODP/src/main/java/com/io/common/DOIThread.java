package com.io.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.inventory.service.DOIServicefinal;
@Service
@Scope("prototype")
public class DOIThread extends Thread{
	
	@Autowired
	private DOIServicefinal dOIServicefinal;
	
	@Override
	public synchronized void run() {
		dOIServicefinal.processDOI();
	}
}
