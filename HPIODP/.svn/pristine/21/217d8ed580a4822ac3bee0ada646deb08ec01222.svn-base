package com.io.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.io.services.forecast.ForeCastService;

@Service
@Scope("prototype")
public class FcstThread extends Thread {
	@Autowired
	private ForeCastService foreCastService;
	@Override
	public synchronized void run(){
		foreCastService.readFile("");
	}
}
