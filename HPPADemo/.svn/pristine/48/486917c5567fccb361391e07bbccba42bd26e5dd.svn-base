package com.techouts.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class MyLogListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(MyLogListener.class);

	public void contextInitialized(ServletContextEvent sce) {
		LOG.info("MyLogListener.contextInitialized()");

		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

	}

	public void contextDestroyed(ServletContextEvent arg) {
		LOG.info("MyLogListener.contextDestroyed()");
	}

}
