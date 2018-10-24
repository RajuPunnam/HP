/**
 * 
 */
package com.techouts.test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author TO-OWDG-02
 *
 */
public class BlueDartFacadeImpl implements BlueDartFacade{


	private Courier courie;

	public void deliverShipment() {
		courie.delivery();

	}

	public Courier getCourie() {
		return courie;
	}

	public void setCourie(Courier courie) {
		this.courie = courie;
	}

}
