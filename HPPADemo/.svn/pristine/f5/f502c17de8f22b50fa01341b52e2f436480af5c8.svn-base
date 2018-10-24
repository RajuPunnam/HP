package com.techouts.services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.dao.HpDao;
import com.techouts.pojo.PADashboardSkuAvbl;
import com.techouts.pojo.User;

@Service
public class HpServices {

	@Autowired
	private HpDao hpDao;

	private static final Logger LOGGER = Logger.getLogger(HpServices.class);

	/**
	 * it will check weather giver credentials are correnct or not
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public User getAuthenticate(String username, String password) {
		LOGGER.info("HpServices.getAuthenticate()");
		return hpDao.checkAuthenticate(username, password);
	}

	/**
	 * it will update user login details while successful login
	 * 
	 * @param user
	 */
	public void updateUserLogin(User user) {
		// long currentLogin = new Date().getTime();
		hpDao.updateUserLoginDetails(user);
	}

	public Set<String> getUserRoles(String roles) {
		String[] role = roles.split(",");
		Set<String> userRoles = new HashSet<String>();
		if (role.length > 0) {
			for (String r : role) {
				userRoles.add(r);
			}
			return userRoles;
		}
		return null;
	}

	public User getCurrentUser(String username) {
		return hpDao.getCurrentUser(username);
	}

	public List<PADashboardSkuAvbl> getTopFiveShipSkus() {
		// TODO Auto-generated method stub
		LOGGER.info("top five ship skus");
		List<PADashboardSkuAvbl> listshipSkus = new ArrayList<PADashboardSkuAvbl>();
		listshipSkus.addAll(hpDao.getTopThreeShipSkus());
		List<PADashboardSkuAvbl> finallistShipSkus = new ArrayList<PADashboardSkuAvbl>();
		Set<String> skuset = new HashSet<String>();
		for (PADashboardSkuAvbl pa : listshipSkus) {

			if (skuset.contains(pa.getSku())) {
				continue;
			}
			skuset.add(pa.getSku());

			DecimalFormat df = new DecimalFormat("###.#");
			double qty = Double.parseDouble(pa.getSkuMaxAvailable());
			pa.setSkuMaxAvailable(df.format(qty));

			finallistShipSkus.add(pa);
			if (skuset.size() > 4) {
				break;
			}
		}

		/*
		 * for(PADashboardSkuAvbl pa:listshipSkus){ LOGGER.info(pa); }
		 * for(PADashboardSkuAvbl pad:listshipSkus){ String
		 * key=pad.getBu()+"-"+pad.getSku()+"-"+pad.getSkuMaxAvailable();
		 * if(listSkuAvbl.containsKey(key)){ listSkuAvbl.put(key,
		 * Double.valueOf(pad.getSkuMaxAvailable())); }else{
		 * listSkuAvbl.put(key, Double.valueOf(pad.getSkuMaxAvailable())); } }
		 * int i=0; List<PADashboardSkuAvbl> finallistShipSkus=new
		 * ArrayList<PADashboardSkuAvbl>(); for (Map.Entry<String, Double> entry
		 * : listSkuAvbl.entrySet()) { PADashboardSkuAvbl obj=new
		 * PADashboardSkuAvbl(); if(i>3){ break; } DecimalFormat df = new
		 * DecimalFormat("###.#");
		 * obj.setBu(entry.getKey().toString().substring(0,
		 * entry.getKey().toString().indexOf("-")));
		 * obj.setSku(entry.getKey().toString
		 * ().substring(entry.getKey().toString
		 * ().indexOf("-")+1,entry.getKey().toString().length()));
		 * obj.setSkuMaxAvailable(df.format(entry.getValue()));
		 * LOGGER.info(obj); finallistShipSkus.add(obj); //LOGGER.info("Key : "
		 * + entry.getKey() + " Value : " + entry.getValue());
		 * 
		 * i++; } Collections.sort(finallistShipSkus,new
		 * Comparator<PADashboardSkuAvbl>() {
		 * 
		 * @Override public int compare(PADashboardSkuAvbl o1,
		 * PADashboardSkuAvbl o2) { // TODO Auto-generated method stub return
		 * o1.getSkuMaxAvailable().compareTo(o2.getSkuMaxAvailable()); } });
		 */

		return finallistShipSkus;
	}

	public List<PADashboardSkuAvbl> getTopFiveShipSkus(String bu) {
		if (bu.equalsIgnoreCase("WST")) {
			bu = "WS";
		}
		LOGGER.info("top five ship skus" + bu);
		List<PADashboardSkuAvbl> listshipSkus = new ArrayList<PADashboardSkuAvbl>();
		listshipSkus.addAll(hpDao.getTopThreeShipSkus(bu));
		List<PADashboardSkuAvbl> finallistShipSkus = new ArrayList<PADashboardSkuAvbl>();
		Set<String> skuset = new HashSet<String>();
		for (PADashboardSkuAvbl pa : listshipSkus) {

			if (skuset.contains(pa.getSku())) {
				continue;
			}
			LOGGER.info(pa);
			skuset.add(pa.getSku());

			DecimalFormat df = new DecimalFormat("###.#");
			double qty = Double.parseDouble(pa.getSkuMaxAvailable());
			pa.setSkuMaxAvailable(df.format(qty));

			finallistShipSkus.add(pa);
			if (skuset.size() > 4) {
				break;
			}
		}

		return finallistShipSkus;
	}

	public List<PADashboardSkuAvbl> getTopFiveShipSkus(String bu, String family) {
		if (bu != null && bu.equalsIgnoreCase("WST")) {
			bu = "WS";
		}
		// TODO Auto-generated method stub
		LOGGER.info("top five ship skus" + bu + "\t" + family);
		List<PADashboardSkuAvbl> listshipSkus = new ArrayList<PADashboardSkuAvbl>();
		listshipSkus.addAll(hpDao.getTopThreeShipSkus(bu, family));
		List<PADashboardSkuAvbl> finallistShipSkus = new ArrayList<PADashboardSkuAvbl>();
		Set<String> skuset = new HashSet<String>();
		for (PADashboardSkuAvbl pa : listshipSkus) {

			if (skuset.contains(pa.getSku())) {
				continue;
			}
			LOGGER.info(pa);
			skuset.add(pa.getSku());

			DecimalFormat df = new DecimalFormat("###.#");
			double qty = Double.parseDouble(pa.getSkuMaxAvailable());
			pa.setSkuMaxAvailable(df.format(qty));

			finallistShipSkus.add(pa);
			if (skuset.size() > 4) {
				break;
			}
		}

		return finallistShipSkus;

	}

}
