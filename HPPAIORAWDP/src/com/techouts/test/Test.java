package com.techouts.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) throws Exception {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"spring-security.xml");
		BludeDartServiceImpl blude = applicationContext
				.getBean(BludeDartServiceImpl.class);
		DHLServiceImpl dhlServiceImpl = new DHLServiceImpl();
		blude.delivery();
		//dhlServiceImpl.delivery();

	}

}
