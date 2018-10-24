package com.techouts.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.techouts.security.CustomAuthenticationProvider;

/**
 * @author Techouts
 *
 */
@ManagedBean
@SessionScoped
public class IndexBean implements Serializable {
	private final static Logger LOGGER = Logger.getLogger(IndexBean.class);

	@PostConstruct
	public void init() {
		LOGGER.info("IndexBean INIT()");
	}

	public String productAvail() {
		LOGGER.info("IndexBean.productAvail");
		return "ProductAvailbility.xhtml?faces-redirect=true";
	}

	public String inventoryOpt() {
		LOGGER.info("IndexBean.inventoryOpt");
		return "InventoryOptimization.xhtml?faces-redirect=true";
	}

	/*
	 * public void logTest(ActionEvent event){
	 * 
	 * System.out.println("IndexBean.logTest()"); // logs debug if
	 * (logger.isDebugEnabled()) { logger.debug("PageController.process()"); }
	 * 
	 * logger.trace("Hello This is TRACE Message");
	 * logger.debug("Hello This is DEBUG Message");
	 * logger.info("Hello This is INFO Message");
	 * 
	 * logger.warn("Hello This is WARN Message");
	 * logger.error("Hello This is ERROR Message");
	 * logger.fatal("Hello This is FATAL Message");
	 * 
	 * // logs exception //logger.error("This is Error message", new
	 * Exception("Log Testing Testing"));
	 * 
	 * }
	 */

}
