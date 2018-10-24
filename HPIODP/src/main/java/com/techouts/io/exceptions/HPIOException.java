package com.techouts.io.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HPIOException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(HPIOException.class);
	
	public HPIOException() {
		super();
	}
	
	public HPIOException(String msg) {
		super(msg);
		LOG.error(msg);
	}
	
	public HPIOException(String msg, Throwable t) {
		super(msg, t);
		LOG.error(t.getClass().getName()+":: \t"+msg);
	}
	
}