package com.io.utill;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.io.dao.DistinctDao;
import com.io.dao.PoDao;

@Service
public class POUtill {

	@Autowired
	private DistinctDao pddistinct;
	
	
	public List<String> getPnMaster(){
		List<String> openOrders_SKU_AV_PN=null;
		Set<String> masterPNs=null;
		openOrders_SKU_AV_PN =new ArrayList<String>();
		masterPNs =new HashSet<String>();
		
		openOrders_SKU_AV_PN.addAll(pddistinct.getPOPNDates());
		masterPNs.addAll(pddistinct.getPOPNMasterDates());
		List<String> newDates=null;
		newDates=new ArrayList<String>();
		for(String s:openOrders_SKU_AV_PN){
			String mondayDate=DateUtil.dateToString(Monday.getPreviousMonday(s), "MM/dd/yyyy");
			System.out.println("MONDAY"+mondayDate);
			if(masterPNs.contains(mondayDate)){
				continue;
			}else{
			newDates.add(s);
			}
		}
		
		for(String s:newDates){
			System.out.println("Remaining Dates -->"+s);
		}
		System.out.println("Remaining Dates size -->"+newDates.size());
		return newDates;
	}
	public List<String> getRemaingDatesPN(){
		List<String> openOrderClean=null;
		Set<String> openOrders=null;
		openOrderClean=new ArrayList<String>();
		openOrders =new HashSet<String>();
		
		openOrderClean.addAll(pddistinct.getOOCFileDates());
		openOrders.addAll(pddistinct.getPOPNFileDates());
		List<String> newDates=null;
		newDates=new ArrayList<String>();
		for(String s:openOrderClean){
			if(openOrders.contains(s)){
				continue;
			}else{
			newDates.add(s);
			}
		}
		for(String s:newDates){
			System.out.println("Remaining Dates -->"+s);
		}
		System.out.println("Remaining Dates size -->"+newDates.size());
		return newDates;
	}
	
	public List<String> getRemaingDatesShortage(){
		List<String> openOrders=null;
		Set<String> org_order_shortage=null;
		openOrders =new ArrayList<String>();
		org_order_shortage =new HashSet<String>();
		
		openOrders.addAll(pddistinct.getOOFileDates());
		org_order_shortage.addAll(pddistinct.getOOSFileDates());
		List<String> newDates=null;
		newDates=new ArrayList<String>();
		for(String s:openOrders){
			if(org_order_shortage.contains(s)){
				continue;
			}else{
			newDates.add(s);
			}
		}
		for(String s:newDates){
			System.out.println("Remaining Dates -->"+s);
		}
		System.out.println("Remaining Dates size -->"+newDates.size());
		return newDates;
	}
	
}
