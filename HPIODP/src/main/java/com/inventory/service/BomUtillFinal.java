/**
 * @author Narasimha
 *
 */
package com.inventory.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.finalpojos.MasterBomPojo;
import com.inventory.utill.DateUtil;

public class BomUtillFinal {

	public BomUtillFinal(List<MasterBomPojo> masterBomPojos) {	
		this.masterBomPojos = masterBomPojos;
	}
	private List<MasterBomPojo> masterBomPojos;	
	private boolean lastLevel=false;
	
	/*private ForecastDao forecastDao;
	public void init() {
		System.out.println("Start");
		masterBomPojos = new ArrayList<MasterBomPojo>();
		masterBomPojos.addAll(forecastDao.getMasterBom());
		System.out.println("DataLoaded");
	}*/
	
	
	private List<MasterBomPojo> tempmasterBom = new ArrayList<MasterBomPojo>();
	NavigableMap<Integer, MasterBomPojo> mapLevel2 = new TreeMap<Integer, MasterBomPojo>();

	public List<MasterBomPojo> getPrimaryData(String Av, String date) {
		List<MasterBomPojo> baseMasterBom = new ArrayList<MasterBomPojo>();
		
		System.out.println("masterBomPojos::::::"+masterBomPojos.size());
		for (MasterBomPojo masterBom : masterBomPojos) {
			
		//	System.out.println(masterBom);
			if (masterBom.getAv().equals(Av)) {
				    MasterBomPojo mb=new MasterBomPojo();
				    mb.setAv(masterBom.getAv());
				    mb.setBomDate(masterBom.getBomDate());
				    mb.setCommodity(masterBom.getCommodity());
				    mb.setCommodityGroup(masterBom.getCommodityGroup());
				    mb.setEffDate(masterBom.getEffDate());
				    mb.setEndDate(masterBom.getEndDate());
				    mb.setId(masterBom.getId());
				    mb.setLastLevel(masterBom.getLastLevel());
				    mb.setLevel(String.valueOf(masterBom.getLevel()));
				    mb.setLocalImport(masterBom.getLocalImport());
				    mb.setMpc(masterBom.getMpc());
				    mb.setPartDescription(masterBom.getPartDescription());
				    mb.setPartNumber(masterBom.getPartNumber());
				    mb.setPosition(masterBom.getPosition());
				    mb.setQtyPer(String.valueOf(masterBom.getQtyPer()));
				    mb.setSrNo(masterBom.getSrNo());
				    mb.setType(masterBom.getType());
				    mb.setSupplier(masterBom.getSupplier());
				    tempmasterBom.add(mb);
				    // System.out.println(masterBom);
				   }
		}
		Collections.sort(tempmasterBom, new Comparator<MasterBomPojo>() {
			public int compare(MasterBomPojo o1, MasterBomPojo o2) {
				return o1.getSrNo() < o2.getSrNo() ? -1 : o1.getSrNo() > o2.getSrNo() ? 1 : 0;
			}
		});
		for (int i = 0; i < tempmasterBom.size(); i++) {
			if (tempmasterBom.get(i).getLevel() == 2) {

				// System.out.println(tempmasterBom.get(i));
				mapLevel2.put(i, tempmasterBom.get(i));
			}
		}
		sortByComparator(mapLevel2);
		/*
		 * for (Integer e : mapLevel2.keySet()) {
		 * System.out.println(mapLevel2.get(e)); }
		 */
		try {
			baseMasterBom.addAll(getPri(sortByComparator(mapLevel2), date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(baseMasterBom, new Comparator<MasterBomPojo>() {
			public int compare(MasterBomPojo o1, MasterBomPojo o2) {
				return o1.getSrNo() < o2.getSrNo() ? -1 : o1.getSrNo() > o2.getSrNo() ? 1 : 0;
			}
		});
		/*
		 * System.out.println(
		 * "========================================================"); for
		 * (MasterBomPojo mbp : baseMasterBom) { System.out.println(mbp); }
		 */
		/*for (MasterBomPojo mbp : baseMasterBom) {
			System.out.println(date+"\t"+mbp.getAv() + "\t" + mbp.getPartNumber()+"  \t"+mbp.getCommodity());
		}*/
		tempmasterBom.clear();
		mapLevel2.clear();
		return baseMasterBom;
	}

	private List<MasterBomPojo> getPri(NavigableMap<Integer, MasterBomPojo> masterMap, String date)
			throws ParseException {
		//List<MasterBomPojo> masterBoms = new ArrayList<MasterBomPojo>();
		NavigableMap<Integer, MasterBomPojo> masterBoms = new TreeMap<Integer, MasterBomPojo>();
		List<MasterBomPojo> temp = new ArrayList<MasterBomPojo>();
		Date startDate = DateUtil.stringToDate("01/01/2001", "MM/dd/yyyy");
		Date endDate = DateUtil.stringToDate("12/31/2030", "MM/dd/yyyy");
		Date currdate = DateUtil.stringToDate(date, "MM-dd-yyyy");
		MasterBomPojo next = null;
		MasterBomPojo curr = null;
		MasterBomPojo requireditem = null;
		for (Integer index : masterMap.keySet()) {
			int key = index;
			//System.out.println(key);

			int nextPosition = 0;
			int currentposition = 0;
			next = masterMap.get(key);
			if (masterMap.higherEntry(key) != null) {
				next = masterMap.higherEntry(key).getValue();
			}
			curr = masterMap.get(key);
			if (!next.getPosition().isEmpty()) {
				try{
				nextPosition = Integer.parseInt(next.getPosition());
				}catch(Exception e){
					
				}
				}
			if (!curr.getPosition().isEmpty()) {
				try{
				currentposition = Integer.parseInt(curr.getPosition());
				}catch(Exception e){
					
				}
				}

			if (curr.getEffDate().isEmpty()) {
				startDate = DateUtil.stringToDate("01/01/2001", "MM/dd/yyyy");
			} else {
				try {
					startDate = DateUtil.stringToDate(curr.getEffDate(), "MM/dd/yyyy");
				} catch (Exception e) {
					try {
						startDate = DateUtil.stringToDate(curr.getEffDate(), "dd-MMM-yy");
					} catch (Exception e2) {
						try {
							startDate = DateUtil.stringToDate(curr.getEffDate(), "dd-MM-yyyy");
						} catch (Exception e3) {
							e.printStackTrace();
						}
					}
				}
			}
			if (curr.getEndDate()==null || curr.getEndDate().isEmpty()) {
				endDate = DateUtil.stringToDate("12/31/2030", "MM/dd/yyyy");
			} else {
				try {
					endDate = DateUtil.stringToDate(curr.getEndDate(), "MM/dd/yyyy");
				} catch (Exception e) {
					try {
						endDate = DateUtil.stringToDate(curr.getEndDate(), "dd-MMM-yy");
					} catch (Exception e2) {
						try {
							endDate = DateUtil.stringToDate(curr.getEndDate(), "dd-MM-yyyy");
						} catch (Exception e3) {
							e.printStackTrace();
						}
					}
				}
			}
			if (currentposition == nextPosition && !curr.getPosition().isEmpty()) {
				if (getDateInBetween(startDate, endDate, currdate)) {
					if (curr.getQtyPer() > 0) {
						masterBoms.put(key,curr);
					}
				}
			} else {
				if (getDateInBetween(startDate, endDate, currdate)) {
					if (curr.getQtyPer() > 0) {
						masterBoms.put(key,curr);
					} else {

					}
				}
				if(!masterBoms.isEmpty()){
				MasterBomPojo plist = null;
				int position = 0;
				String mpc = null;
				for (Integer i:masterBoms.keySet()) {
					MasterBomPojo mp = masterBoms.get(i);
					if (mp.getMpc().equalsIgnoreCase("p")) {
						plist = new MasterBomPojo();
						plist = mp;
						position=i;
						break;
					} else if (mp.getMpc().equalsIgnoreCase("I")) {
						plist = new MasterBomPojo();
						plist = mp;
						position=i;
					} else if (mp.getMpc().equalsIgnoreCase("M")) {
						if(plist !=null){
						mpc = plist.getMpc();
						}else{
							mpc="";
						}
						if (!mpc.equalsIgnoreCase("I")){
							plist = new MasterBomPojo();
							plist = mp;
							position=i;
							}
					} else {
						if(plist !=null){
							//TODO
						}else{
							plist = new MasterBomPojo();
							plist = mp;
							position=i;
							}
					}
				}
				if(plist != null){
				if(plist.getLastLevel().equalsIgnoreCase("yes")){
					temp.add(plist);
				}else if(plist.getLastLevel().equalsIgnoreCase("NO")){
					NavigableMap<Integer, MasterBomPojo> mapLevel3 = new TreeMap<Integer, MasterBomPojo>();
					MasterBomPojo mp = tempmasterBom.get(position + 1);
				     double avqty=tempmasterBom.get(position).getQtyPer();
					for (int i = position + 1; i < tempmasterBom.size(); i++) {
						// System.out.println(mp.getLevel()+"\t"+tempmasterBom.get(i).getLevel());
						// System.out.println(mp.getSrNo()+"\t"+tempmasterBom.get(i).getSrNo());

						if (mp.getLevel() == tempmasterBom.get(i).getLevel()) {
							// System.out.println("LEVEL::"+tempmasterBom.get(i).getLevel()+"\t"+tempmasterBom.get(i));
							tempmasterBom.get(i).setQtyPer(String.valueOf(avqty*tempmasterBom.get(i).getQtyPer()));
						       mapLevel3.put(i, tempmasterBom.get(i));
						       mapLevel3.put(i, tempmasterBom.get(i));
						} else if(mp.getLevel() > tempmasterBom.get(i).getLevel()){
							break;
						}else{
							//System.out.println("skip Position");
						}
					}
					sortByComparator(mapLevel3);
					temp.addAll(getPri(sortByComparator(mapLevel3),date));
				}
				}
				else{
					System.out.println("skip");
				}
				}
				masterBoms.clear();
			}
				
		}

		

		if(!masterBoms.isEmpty()){
		MasterBomPojo plist = null;
		int position = 0;
		String mpc = null;
		for (Integer i:masterBoms.keySet()) {
			MasterBomPojo mp = masterBoms.get(i);
			if (mp.getMpc().equalsIgnoreCase("p")) {
				plist = new MasterBomPojo();
				plist = mp;
				position=i;
				break;
			} else if (mp.getMpc().equalsIgnoreCase("I")) {
				plist = new MasterBomPojo();
				plist = mp;
				position=i;
			} else if (mp.getMpc().equalsIgnoreCase("M")) {
				if(plist !=null){
				mpc = plist.getMpc();
				}else{
					mpc="";
				}
				if (!mpc.equalsIgnoreCase("I")){
					plist = new MasterBomPojo();
					plist = mp;
					position=i;
					}
			} else {
				if(plist !=null){
					//TODO
				}else{
					plist = new MasterBomPojo();
					plist = mp;
					position=i;
					}
			}
		}
		if(plist != null){
		if(plist.getLastLevel().equalsIgnoreCase("yes")){
			temp.add(plist);
		}else if(plist.getLastLevel().equalsIgnoreCase("NO")){
			NavigableMap<Integer, MasterBomPojo> mapLevel3 = new TreeMap<Integer, MasterBomPojo>();
			
			MasterBomPojo mp =null;
			try{
			 mp = tempmasterBom.get(position + 1);
			}catch(Exception e){
			System.out.println("Catch"+plist);
			}
			if(mp !=null){
			     double avqty=tempmasterBom.get(position).getQtyPer();
			for (int i = position + 1; i < tempmasterBom.size(); i++) {
				// System.out.println(mp.getLevel()+"\t"+tempmasterBom.get(i).getLevel());
				// System.out.println(mp.getSrNo()+"\t"+tempmasterBom.get(i).getSrNo());

				if (mp.getLevel() == tempmasterBom.get(i).getLevel()) {
					// System.out.println("LEVEL::"+tempmasterBom.get(i).getLevel()+"\t"+tempmasterBom.get(i));
					tempmasterBom.get(i).setQtyPer(String.valueOf(avqty*tempmasterBom.get(i).getQtyPer()));
				       mapLevel3.put(i, tempmasterBom.get(i));
				       mapLevel3.put(i, tempmasterBom.get(i));
				} else if(mp.getLevel() > tempmasterBom.get(i).getLevel()){
					break;
				}else{
					//System.out.println("skip Position");
				}
			}
			sortByComparator(mapLevel3);
			temp.addAll(getPri(sortByComparator(mapLevel3),date));
			}
		}
		}
		else{
			/*System.out.println("skip");*/
		}
		masterBoms.clear();
		}
		
	
		
		
		/*for (MasterBomPojo mp : temp) {
			System.out.println(mp);
		}*/

		return temp;
	}

	// To sort map based on Position no

	private static boolean getDateInBetween(Date sd, Date ed, Date cd) {
		return cd.after(sd) && cd.before(ed) || cd.equals(ed) || cd.equals(sd);
	}

	private NavigableMap<Integer, MasterBomPojo> sortByComparator(NavigableMap<Integer, MasterBomPojo> unsortMap) {

		// Convert Map to List
		List<Map.Entry<Integer, MasterBomPojo>> list = new LinkedList<Map.Entry<Integer, MasterBomPojo>>(
				unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<Integer, MasterBomPojo>>() {
			public int compare(Map.Entry<Integer, MasterBomPojo> o1, Map.Entry<Integer, MasterBomPojo> o2) {
				System.out.println("o1::"+o1);
				System.out.println("o2::"+o2);
				return (o1.getValue().getPosition()).compareTo(o2.getValue().getPosition());
			}
		});

		// Convert sorted map back to a Map
		NavigableMap<Integer, MasterBomPojo> sortedMap = new TreeMap<Integer, MasterBomPojo>();
		for (Iterator<Map.Entry<Integer, MasterBomPojo>> it = list.iterator(); it.hasNext();) {
			Map.Entry<Integer, MasterBomPojo> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	

	}

	public List<MasterBomPojo> getSubParts(final String partId,String doiDate){	
		//String av="";
		List<MasterBomPojo> masterBomSet=null;
		MasterBomPojo masterBomPart=null;
		for(MasterBomPojo  masterBomPojo:masterBomPojos){
			if(partId.equals(masterBomPojo.getPartNumber())){
				masterBomPart=masterBomPojo;
				break;
			}
		}
		
		if(masterBomPart != null){
		if(masterBomPart.getLastLevel().equalsIgnoreCase("Yes")){
			lastLevel=true;
			masterBomSet=new ArrayList<MasterBomPojo>();
			masterBomSet.add(masterBomPart);
			return masterBomSet;
			//String position=masterBomPart.getPosition();			 
		/*if((masterBomPart.getMpc().equalsIgnoreCase("p"))||(masterBomPart.getMpc().equalsIgnoreCase(""))){
			if(!(masterBomPart.getType()).equalsIgnoreCase("PHANTOM")){				
				
				if(masterBomPart.getPosition().equalsIgnoreCase("")){
					
					if((masterBomPart.getType().equalsIgnoreCase("ALT"))||(masterBomPart.getType().equalsIgnoreCase(""))){						
						if(masterBomPart.getQtyPer()>0){
							masterBomSet=new ArrayList<MasterBomPojo>();
							masterBomSet.add(masterBomPart);
							return masterBomSet;
						}
					}
					else{
						masterBomSet=new ArrayList<MasterBomPojo>();
						masterBomSet.add(masterBomPart);
						return masterBomSet;
						
					}
				}//empty if
				else{
					try{
					int intPosition=Integer.parseInt(masterBomPart.getPosition().trim());  
					if(intPosition>0){
						
						if((masterBomPart.getType().equalsIgnoreCase("ALT"))||(masterBomPart.getType().equalsIgnoreCase(""))){						
							if(masterBomPart.getQtyPer()>0){
								masterBomSet=new ArrayList<MasterBomPojo>();
								masterBomSet.add(masterBomPart);
								return masterBomSet;
							}
						}
						else{
							masterBomSet=new ArrayList<MasterBomPojo>();
							masterBomSet.add(masterBomPart);
							return masterBomSet;
							
						}
					}// >0
					
					}catch(Exception e){
						
						System.out.println("*************position parsing exception*****************");
					}
					
				}// phantom else
			}// phantom
		}*/
		
		}//if yes		
		else{
			tempmasterBom.clear();
			mapLevel2.clear();
			
				for (MasterBomPojo masterBom : masterBomPojos) {
					if (masterBomPart.getAv().equals(masterBom.getAv())) {
						MasterBomPojo mb=new MasterBomPojo();
					    mb.setAv(masterBom.getAv());
					    mb.setBomDate(masterBom.getBomDate());
					    mb.setCommodity(masterBom.getCommodity());
					    mb.setCommodityGroup(masterBom.getCommodityGroup());
					    mb.setEffDate(masterBom.getEffDate());
					    mb.setEndDate(masterBom.getEndDate());
					    mb.setId(masterBom.getId());
					    mb.setLastLevel(masterBom.getLastLevel());
					    mb.setLevel(String.valueOf(masterBom.getLevel()));
					    mb.setLocalImport(masterBom.getLocalImport());
					    mb.setMpc(masterBom.getMpc());
					    mb.setPartDescription(masterBom.getPartDescription());
					    mb.setPartNumber(masterBom.getPartNumber());
					    mb.setPosition(masterBom.getPosition());
					    mb.setQtyPer(String.valueOf(masterBom.getQtyPer()));
					    mb.setSrNo(masterBom.getSrNo());
					    mb.setType(masterBom.getType());
					    mb.setSupplier(masterBom.getSupplier());
						tempmasterBom.add( mb);
					}
				}
				lastLevel=false;
				int level = 0;
				int nextlevel = 0;
				boolean levelStatus = false;
				for (int index = 0; index < tempmasterBom.size(); index++) {
					MasterBomPojo masterBomPojo = tempmasterBom.get(index);
					if (masterBomPojo.getPartNumber().equals(partId)) {
						level = masterBomPojo.getLevel();
						//mapLevel2.put(index, masterBomPojo);
						if((index + 1)<tempmasterBom.size()){
						MasterBomPojo masterBomNext = tempmasterBom
								.get((index + 1));
						nextlevel = masterBomNext.getLevel();
						levelStatus = true;
						}
					} else if (levelStatus) {
						if ((level != 0) && (nextlevel != 0)
								&& (level < nextlevel)) {
							MasterBomPojo masterBom = tempmasterBom.get(index);
							int level3 = masterBom.getLevel();
							if (level3 == level) {
								levelStatus = false;
							}
							else if (levelStatus) {
								if (level3 == nextlevel) {
									mapLevel2.put(index, masterBom);
								}

							}
						}

					}// status
				}// index
				/*for(Map.Entry<Integer, MasterBomPojo> map:mapLevel2.entrySet()){
					System.out.println(" position  :"+map.getKey());
					System.out.println(map.getValue());
				}*/			
				if (! mapLevel2.isEmpty()) {
					List<MasterBomPojo> finalMasterBomList=null;
					try {
						finalMasterBomList = getPri(mapLevel2,doiDate);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if ((finalMasterBomList != null)
							&& (!finalMasterBomList.isEmpty())) {
					
						return finalMasterBomList;
					}
				}// !mapLevel2.isEmpty()
			}// else
		
		}// not null

		return null;
	}

	public boolean isLastLevel() {
		return lastLevel;
	}

	public void setLastLevel(boolean lastLevel) {
		this.lastLevel = lastLevel;
	}
}
