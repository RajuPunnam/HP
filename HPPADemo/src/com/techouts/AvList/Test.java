package com.techouts.AvList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) throws Exception {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"/WEB-INF/applicationContext.xml");
		AllAvsService dao = applicationContext.getBean(AllAvsService.class);
		dao.getAvsData();
		if (applicationContext != null) {
			((ClassPathXmlApplicationContext) applicationContext).close();
		}
	}

}
