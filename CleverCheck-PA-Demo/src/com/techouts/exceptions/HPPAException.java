package com.techouts.exceptions;

import org.apache.log4j.Logger;

public class HPPAException extends RuntimeException {

	/**
	  * 
	  */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(HPPAException.class);

	public HPPAException() {
		super();
	}

	public HPPAException(String msg) {
		super(msg);
		LOG.error(msg);
	}

	public HPPAException(String msg, Throwable t) {
		super(msg, t);
		LOG.error(t.getClass().getName() + ":: \t" + msg);
	}

}
