package com.io.utill;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.io.common.Resources;
import com.io.dao.ForecastDao;
import com.io.pojos.MasterBomPojo;

@Service
public class BomUtill {

	private static Logger LOG=Logger.getLogger(BomUtill.class);
	@Autowired
	private ForecastDao forecastDao;
	private List<MasterBomPojo> masterBomPojos=null;
	
	public void init() {
		LOG.info("----------IODP BomUtill init Start------------------->"+new Date());
		masterBomPojos = new ArrayList<MasterBomPojo>();
		masterBomPojos.addAll(forecastDao.getMasterBom());
		LOG.info("----------IODP BomUtill init DataLoaded-------------->"+new Date());
	}

	private List<MasterBomPojo> tempmasterBom = null;
	NavigableMap<Integer, MasterBomPojo> mapLevel2 = new TreeMap<Integer, MasterBomPojo>();
	
	public List<MasterBomPojo> getPrimaryData(String Av, String date) {
		
/*	masterBomPojos = new ArrayList<MasterBomPojo>();
	masterBomPojos.addAll(forecastDao.getMasterBom(Av.trim()));*/
		
		tempmasterBom = new ArrayList<MasterBomPojo>();
		List<MasterBomPojo> baseMasterBom = new ArrayList<MasterBomPojo>();
		for (MasterBomPojo masterBom : masterBomPojos) {
			if (masterBom.getAv().trim().equals(Av.trim())) {
				MasterBomPojo mb=new MasterBomPojo();
				mb.setAv(masterBom.getAv().trim());
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
		
		/* System.out.println("========================================================"); 
		 for(MasterBomPojo mbp : baseMasterBom) { 
			 System.out.println(mbp); 
			 }*/
		 
		/*for (MasterBomPojo mbp : baseMasterBom) {
			System.out.println(mbp.getSrNo()+"\t"+mbp.getLevel()+"\t"+mbp.getAv() + "\t" + mbp.getPartNumber()+"  \t"+mbp.getQtyPer());
		}*/
		tempmasterBom.clear();
		mapLevel2.clear();
		//cache.put(key, baseMasterBom);
		return baseMasterBom;
		//}
	}

	private List<MasterBomPojo> getPri(NavigableMap<Integer, MasterBomPojo> masterMap, String date)
			throws ParseException {
		//List<MasterBomPojo> masterBoms = new ArrayList<MasterBomPojo>();
		NavigableMap<Integer, MasterBomPojo> masterBoms = new TreeMap<Integer, MasterBomPojo>();
		List<MasterBomPojo> temp = new ArrayList<MasterBomPojo>();
		Date startDate = DateUtil.stringToDate("01/01/2001", "MM/dd/yyyy");
		Date endDate = DateUtil.stringToDate("12/31/2030", "MM/dd/yyyy");
		String d=date.replace("/", "-");
		Date currdate = DateUtil.stringToDate(d, "MM-dd-yyyy");
		MasterBomPojo next = null;
		MasterBomPojo curr = null;
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
			if (curr.getEndDate().isEmpty()) {
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
					double avqty=tempmasterBom.get(position).getQtyPer();
					MasterBomPojo mp = tempmasterBom.get(position + 1);
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
					// System.out.println("skip");
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
			// System.out.println("Catch"+plist);
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
			// System.out.println("skip");
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
	
	
	
	
	
	
	

	/*public static void main(String[] args) throws ParseException {
		Date startDate = DateUtil.stringToDate("01/01/2001", "MM/dd/yyyy");
		Date endDate = DateUtil.stringToDate("12/31/2030", "MM/dd/yyyy");
		Date currdate = DateUtil.stringToDate("01/01/2001", "MM/dd/yyyy");
		System.out.println(getDateInBetween(startDate, endDate, currdate));

		NavigableMap<Integer, String> map = new TreeMap<Integer, String>();
		map.put(1, "Jan");
		map.put(2, "Feb");
		map.put(3, "Mar");
		map.put(4, "Apr");
		map.put(5, "May");
		map.put(6, "Jun");
		System.out.println("itereting");
		for (Integer i : map.keySet()) {
			// for(Map.Entry<Integer, String> e:map.entrySet()){
			String nextval = null;
			if (map.higherEntry(i) == null) {

			} else {
				nextval = map.higherEntry(i).getValue();
			}
			System.out.println("curr key :: " + i + "\t" + map.get(i) + "\t Next value ::" + nextval);
		}

	}
*/}
