package com.techouts.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.dao.HomePageDao;
import com.techouts.dto.All_Skus_Availability;
import com.techouts.dto.SKu_Similarity_Matrix;
import com.techouts.pojo.AvAvbail;
import com.techouts.pojo.BUFamilyAvailability;
import com.techouts.pojo.Family;
import com.techouts.pojo.MasterSkuAvBom_PA;
import com.techouts.pojo.SkuDetailsInfo;

@Service
public class HomePageService {

	@Autowired
	private HomePageDao homePageDao;
	@Autowired
	private CacheService cacheService;
	// private Map<String, Set<String>> bufamilyMap;
	// private String selectedSkuId;

	private static final Logger LOG = Logger.getLogger(HomePageService.class);

	public Set<String> searchData() {
		return cacheService.getSearchData();
	}

	public Set<String> searchDisData() {
		return cacheService.getDisSearchData();
	}

	public Set<String> searchSalesDisData() {
		return cacheService.getDisSearchData();
	}

	public int getCartCount() {
		return cacheService.getUserCartqty();
	}

	private SkuDetailsInfo converttoskuDetails(All_Skus_Availability s) {
		SkuDetailsInfo info = new SkuDetailsInfo();
		String bu = s.getBu();
		if (bu.equalsIgnoreCase("BNB") || bu.equalsIgnoreCase("CNB")) {
			info.setImageType("NB");
		} else if (bu.equalsIgnoreCase("BPC") || bu.equalsIgnoreCase("CPC")) {
			info.setImageType("PC");
		} else if (bu.equalsIgnoreCase("WST")) {
			info.setImageType("WS");
		}
		info.setCount(0);
		info.setSkuId(s.getSkuId());
		info.setSkuAvailability((int) s.getSkuAvailability());
		info.setNewOrderDeliveryWeeks(s.getNewOrderDeliveryWeeks());
		info.setPipeLineQuantity((int) s.getPipeLineQuantity());
		info.setPipeLineWeeks(s.getPipeLineWeeks());
		List<String> conf = new ArrayList<String>();
		int j = 0;
		for (String temp : s.getConfigurations()) {
			String[] temps = temp.split(": ");
			conf.add(temps[1]);
			j++;
			if (j > 3) {
				break;
			}

		}
		info.setConfiguration(conf);
		j = 0;
		conf = new ArrayList<String>();
		if ((int) s.getSkuAvailability() < 1) {
			for (String temp : s.getShortages()) {
				String[] temps = temp.split(": ");
				conf.add(temps[1]);
				j++;
				if (j > 2) {
					break;
				}
			}
			info.setShortage(conf);
		}
		return info;
	}

	private SkuDetailsInfo converttoskuDetails(SkuDetailsInfo s) {
		SkuDetailsInfo info = null;
		info = new SkuDetailsInfo();
		info.setBusinessUnit(s.getBusinessUnit());
		info.setFamily(s.getFamily());
		info.setSkuId(s.getSkuId());
		info.setSkuAvailability((int) s.getSkuAvailability());
		info.setNewOrderDeliveryWeeks(s.getNewOrderDeliveryWeeks());
		info.setPipeLineQuantity((int) s.getPipeLineQuantity());
		info.setPipeLineWeeks(s.getPipeLineWeeks());
		info.setPavail15Days(s.getPavail15Days());
		info.setPavail30Days(s.getPavail30Days());
		info.setPavail45Days(s.getPavail45Days());
		info.setPavail60Days(s.getPavail60Days());
		info.setPavail75Days(s.getPavail75Days());
		info.setPavail90Days(s.getPavail90Days());
		info.setDescription(s.getDescription());
		String bu = s.getBusinessUnit();
		if (bu.equalsIgnoreCase("BNB") || bu.equalsIgnoreCase("CNB")) {
			info.setImageType("NB");
		} else if (bu.equalsIgnoreCase("BPC") || bu.equalsIgnoreCase("CPC")) {
			info.setImageType("PC");
		} else if (bu.equalsIgnoreCase("WST")) {
			info.setImageType("WS");
		}

		List<String> conf = new ArrayList<String>();
		int j = 0;
		// LOG.info(s);
		for (String temp : s.getConfiguration()) {
			// LOG.info("temp"+temp);
			String[] temps = temp.split(": ");
			conf.add(temps[1]);
			j++;
			if (j > 3) {
				break;
			}

		}
		info.setConfiguration(conf);
		j = 0;
		conf = new ArrayList<String>();
		if ((int) s.getSkuAvailability() < 1) {
			for (String temp : s.getShortage()) {
				String[] temps = temp.split(": ");
				conf.add(temps[1]);
				j++;
				if (j > 2) {
					break;
				}
			}
			info.setShortage(conf);
		}
		return info;
	}

	public List<SkuDetailsInfo> getSkusDetails(Set<String> shortlisted) {
		List<SkuDetailsInfo> list = new ArrayList<SkuDetailsInfo>();
		int i = 0;
		for (SkuDetailsInfo s : cacheService.getskuDetailsInfo()) {

			SkuDetailsInfo info = null;

			info = converttoskuDetails(s);
			if (shortlisted.contains(s.getSkuId())) {
				info.setIsShortListed("selected");
			}
			info.setCount(i);
			list.add(info);
			i++;
			if (i == 102) {
				break;
			}
		}
		Collections.sort(list);
		return list;
	}

	public List<SkuDetailsInfo> getSkusDetailsforShortlisted(Set<String> shortlisted) {
		List<SkuDetailsInfo> list = new ArrayList<SkuDetailsInfo>();
		List<String> skus = new ArrayList<String>(shortlisted);

		for (String sku : skus) {
			SkuDetailsInfo info = null;
			info = new SkuDetailsInfo();
			SkuDetailsInfo SkuData = cacheService.getskuDetailsInfo(sku);
			if (SkuData == null) {
				SkuData = cacheService.getSkuConfigInfo(sku);
			}
			info = converttoskuDetails(SkuData);
			info.setIsShortListed("selected");
			list.add(info);
		}

		/*
		 * for(All_Skus_Availability s:homePageDao.getAllSkuAvailability(skus)){
		 * SkuDetailsInfo info=null; info=new SkuDetailsInfo();
		 * info=converttoskuDetails(s); info.setIsShortListed("selected");
		 * info.setCount(i); i++; list.add(info);
		 * 
		 * }
		 */
		Collections.sort(list);
		return list;
	}

	public List<SkuDetailsInfo> getSkusDetailsforFamily(String family, String bu, Set<String> shortlisted) {
		List<SkuDetailsInfo> list = new ArrayList<SkuDetailsInfo>();
		int i = 0;
		List<String> skus = null;
		skus = new ArrayList<String>();
		if (bu != null) {
			skus.addAll(cacheService.getSkusforbufamily(bu, family));
		} else {
			skus.addAll(cacheService.getskusforFamily(family));
		}
		for (String sku : skus) {
			LOG.info(sku);
			SkuDetailsInfo info = null;
			info = new SkuDetailsInfo();
			info = converttoskuDetails(cacheService.getskuDetailsInfo(sku));
			if (shortlisted.contains(info.getSkuId())) {
				info.setIsShortListed("selected");
			}
			info.setCount(i);
			i++;
			list.add(info);
		}
		Collections.sort(list);
		return list;
	}

	public List<SkuDetailsInfo> getSimailarityIndex(String sku, Set<String> shortlisted) {
		List<String> skus = null;
		Map<String, String> skubuck = new HashMap<String, String>();
		skus = new ArrayList<String>();
		List<SkuDetailsInfo> list = new ArrayList<SkuDetailsInfo>();
		List<SkuDetailsInfo> temp = new ArrayList<SkuDetailsInfo>();

		SkuDetailsInfo searchedsku = null;
		searchedsku = new SkuDetailsInfo();
		searchedsku = getSkuInfo(sku);
		if (shortlisted.contains(searchedsku.getSkuId())) {
			searchedsku.setIsShortListed("selected");
		}
		temp.add(searchedsku);

		for (SKu_Similarity_Matrix s : homePageDao.getSku_SimilarityMatrix(sku)) {
			skus.add(s.getMatchedSku());
			LOG.info(s);
			if (s.getSimitarityIndex() >= 0.8 && s.getSimitarityIndex() < 0.85)
				skubuck.put(s.getMatchedSku(), "80%");
			else if (s.getSimitarityIndex() >= 0.85 && s.getSimitarityIndex() < 0.9)
				skubuck.put(s.getMatchedSku(), "85%");
			else if (s.getSimitarityIndex() >= 0.90 && s.getSimitarityIndex() < 0.95)
				skubuck.put(s.getMatchedSku(), "90%");
			else if (s.getSimitarityIndex() >= 0.95 && s.getSimitarityIndex() < 1)
				skubuck.put(s.getMatchedSku(), "95%");

		}
		int i = 1;
		for (String s : skus) {
			SkuDetailsInfo info = null;
			info = new SkuDetailsInfo();
			info = converttoskuDetails(cacheService.getSkuConfigInfo(s));
			if (shortlisted.contains(info.getSkuId())) {
				info.setIsShortListed("selected");
			}
			/*
			 * } for (All_Skus_Availability s :
			 * homePageDao.getAllSkuconfigAvailability(skus)) {
			 */
			if (info.getSkuAvailability() < 1) {
				continue;
			}

			info.setSimIndex(skubuck.get(info.getSkuId()));

			LOG.info(info.getCount());
			list.add(info);

		}
		Collections.sort(list);
		for (SkuDetailsInfo info : list) {
			info.setCount(i);
			i++;
			temp.add(info);
		}
		return temp;

	}

	public List<SkuDetailsInfo> getBUinfo(String bu, Set<String> shortlisted) {
		List<SkuDetailsInfo> skus = new ArrayList<SkuDetailsInfo>();
		List<SkuDetailsInfo> temp = new ArrayList<SkuDetailsInfo>();
		LOG.info("BU :: " + bu);
		int i = 0;
		for (String sku : cacheService.getbuskus(bu)) {
			SkuDetailsInfo info = null;
			info = new SkuDetailsInfo();
			info = converttoskuDetails(cacheService.getskuDetailsInfo(sku));
			if (shortlisted.contains(info.getSkuId())) {
				info.setIsShortListed("selected");
			}
			info.setCount(i);
			temp.add(info);

		}
		Collections.sort(temp);

		for (SkuDetailsInfo info : temp) {
			skus.add(info);
			i++;
			if (i == 102) {
				break;
			}
		}

		return skus;
	}

	private Map<String, Integer> buavail = null;

	@PostConstruct
	public void init() {
		buavail = new HashMap<String, Integer>();
	}

	public List<Family> loadbufamilyData(String bu) {
		List<Family> values = new ArrayList<Family>();
		Map<String, Integer> familyavaible = null;
		familyavaible = new HashMap<String, Integer>();
		for (BUFamilyAvailability bf : cacheService.getbufamily(bu)) {
			if (bf.getFamily().isEmpty()) {
				buavail.put(bf.getBu(), (int) bf.getBuAvail());
				LOG.info(" IN IF" + bf);
			} else {
				familyavaible.put(bf.getFamily(), (int) bf.getFamilyAvail());
				LOG.info(" IN ELSE " + bf);
			}

		}
		for (String s : getfamilybu(bu)) {
			Family f = new Family();
			f.setSkuid(s);
			f.setCount(String.valueOf(familyavaible.get(s)));
			LOG.info(f);
			if (!s.isEmpty())
				values.add(f);
		}
		values.sort(new Comparator<Family>() {

			@Override
			public int compare(Family o1, Family o2) {
				// TODO Auto-generated method stub
				return Integer.parseInt(o2.getCount()) - Integer.parseInt((o1.getCount()));
			}
		});
		return values;
	}

	public long getbucount(String bu) {
		/*
		 * double total=0; for(All_Skus_Availability
		 * a:homePageDao.getAllbuAvailability(bu)){
		 * total+=a.getSkuAvailability(); }
		 */
		return buavail.get(bu);
	}

	/*
	 * public Map<String, Set<String>> getBufamilyMap() { return bufamilyMap; }
	 */
	public List<String> getfamilybu(String bu) {
		return homePageDao.getdistinctfamilies(bu);
	}

	public boolean familycontains(String family) {
		return cacheService.familyexists(family);
	}

	/*
	 * public List<String> getallSkus() { return homePageDao.getdistinctskus();
	 * }
	 */
	public List<String> getfamilySku(String family) {
		return cacheService.getskusforFamily(family);
	}

	public SkuDetailsInfo getSkuconfigInfo(String skuid) {
		SkuDetailsInfo sinfo = new SkuDetailsInfo();
		SkuDetailsInfo SkuData = cacheService.getskuDetailsInfo(skuid);
		if (SkuData == null) {
			SkuData = cacheService.getSkuConfigInfo(skuid);
			sinfo = convert(SkuData);
			String str[] = SkuData.getSkuId().split("#Config#");
			sinfo.setSkuId(str[0]);
		} else {
			sinfo = convert(SkuData);
		}
		return sinfo;
	}

	public SkuDetailsInfo getSkuClickInfo(String sku) {
		SkuDetailsInfo sinfo = new SkuDetailsInfo();
		SkuDetailsInfo SkuData = cacheService.getskuDetailsInfo(sku);
		if (SkuData == null) {
			SkuData = cacheService.getSkuConfigInfo(sku);
			sinfo = convert(SkuData);
			String str[] = SkuData.getSkuId().split("#Config#");
			sinfo.setSkuId(str[0]);
		} else {
			sinfo = convert(SkuData);
		}
		return sinfo;
	}

	public SkuDetailsInfo getSkuInfo(String sku) {
		return converttoskuDetails(cacheService.getskuDetailsInfo(sku));
	}

	private SkuDetailsInfo convert(SkuDetailsInfo skuData) {
		SkuDetailsInfo info = new SkuDetailsInfo();
		info.setBusinessUnit(skuData.getBusinessUnit());
		info.setFamily(skuData.getFamily());
		info.setSkuId(skuData.getSkuId());
		info.setCount(skuData.getCount());
		info.setSkuAvailability((int) skuData.getSkuAvailability());
		info.setNewOrderDeliveryWeeks(skuData.getNewOrderDeliveryWeeks());
		info.setPipeLineQuantity((int) skuData.getPipeLineQuantity());
		info.setPipeLineWeeks(skuData.getPipeLineWeeks());
		info.setConfiguration(skuData.getConfiguration());
		info.setPavail15Days(skuData.getPavail15Days());
		info.setPavail30Days(skuData.getPavail30Days());
		info.setPavail45Days(skuData.getPavail45Days());
		info.setPavail60Days(skuData.getPavail60Days());
		info.setPavail75Days(skuData.getPavail75Days());
		info.setPavail90Days(skuData.getPavail90Days());
		info.setDescription(skuData.getDescription());
		if ((int) skuData.getSkuAvailability() < 1)
			info.setShortage(skuData.getShortage());
		String bu = skuData.getBusinessUnit();
		if (bu.equalsIgnoreCase("BNB") || bu.equalsIgnoreCase("CNB")) {
			info.setImageType("NB");
		} else if (bu.equalsIgnoreCase("BPC") || bu.equalsIgnoreCase("CPC")) {
			info.setImageType("PC");
		} else if (bu.equalsIgnoreCase("WST")) {
			info.setImageType("WS");
		}
		return info;
	}

	public List<MasterSkuAvBom_PA> getMasterAvBomData(String family) {
		return homePageDao.getMasterAvBomData(family);
	}

	public List<MasterSkuAvBom_PA> getMasterAvBomDataSales(String family) {
		return homePageDao.getMasterAvBomDataSales(family);
	}

	public Map<String, Double> getSimilarSkus(All_Skus_Availability all_Skus_Availability,
			Map<String, Set<String>> skuAvsMap) {
		Set<String> orgAvsList = homePageDao.getSkuAvList(all_Skus_Availability.getSkuId());
		double avCount = orgAvsList.size();
		Map<String, Double> skuSimilarityMap = new HashMap<String, Double>();
		for (Entry<String, Set<String>> skuAvs : skuAvsMap.entrySet()) {
			if (skuAvs.getValue().contains("Total") || skuAvs.getKey().equals(all_Skus_Availability.getSkuId()))
				continue;
			Set<String> avList = skuAvs.getValue();
			double matchedCount = 0;
			for (String orgAv : orgAvsList) {
				for (String av : avList) {
					if (orgAv.equals(av)) {
						matchedCount++;
					}
				}
			}
			skuSimilarityMap.put(skuAvs.getKey(), matchedCount / avCount);
		}
		skuSimilarityMap = sortByComparator(skuSimilarityMap, false);
		return skuSimilarityMap;
	}

	public All_Skus_Availability getSkuData(String skuId) {
		All_Skus_Availability all_Skus_Availability = null;
		MasterSkuAvBom_PA masterSkuAvBom_PA = homePageDao.getSkuDataSales(skuId);
		if (masterSkuAvBom_PA != null) {
			all_Skus_Availability = new All_Skus_Availability();
			all_Skus_Availability.setSkuId(masterSkuAvBom_PA.getSku());
			all_Skus_Availability.setFamily(masterSkuAvBom_PA.getFamily());
			all_Skus_Availability.setBu(masterSkuAvBom_PA.getBu());
		}
		return all_Skus_Availability;
	}

	public HashMap<String, List<String>> getSkuAvTableData(All_Skus_Availability all_Skus_Availability,
			Map<String, Double> similarityIndex, Map<String, Set<String>> skuAvsMap) {
		HashMap<String, List<String>> skuAvTableData = new HashMap<String, List<String>>();
		List<AvAvbail> avAvbailList = homePageDao.getAvAVBAIL();		
		Set<String> orgAvsList = homePageDao.getSkuAvList(all_Skus_Availability.getSkuId());
		for (Entry<String, Double> similarSku : similarityIndex.entrySet()) {
			skuAvTableData.put(similarSku.getKey(), getCommodityDesc(skuAvsMap.get(similarSku.getKey()), avAvbailList,
					orgAvsList, all_Skus_Availability.getBu()));
			if (skuAvTableData.size() > 150)
				break;
		}
		all_Skus_Availability.setConfigurations(
				getCommodityDesc(orgAvsList, avAvbailList, orgAvsList, all_Skus_Availability.getBu()));
		List<String> cpuAvList = homePageDao.getAltAvs(all_Skus_Availability.getConfigurations().get(0).split(":")[0].trim(),true,"CPU");
		List<String> nonMatchSkuList = new ArrayList<String>();
		for(Entry<String, List<String>> entry:skuAvTableData.entrySet()){
			if(cpuAvList.contains(entry.getValue().get(0).replace("<font color='red'>", "").replace("</font>", "").split(":")[0].trim())){
				List<String> memoryAvList = homePageDao.getAltAvs(all_Skus_Availability.getConfigurations().get(1).split(":")[0].trim(),false,"Memory");
				if(memoryAvList.contains(entry.getValue().get(1).replace("<font color='red'>", "").replace("</font>", "").split(":")[0].trim())){
					List<String> diskAvList = homePageDao.getAltAvs(all_Skus_Availability.getConfigurations().get(2).split(":")[0].trim(),false,"HDD");
					if(diskAvList.contains(entry.getValue().get(2).replace("<font color='red'>", "").replace("</font>", "").split(":")[0].trim())){
						continue;
					}else{
						nonMatchSkuList.add(entry.getKey());
					}
				}else{
					nonMatchSkuList.add(entry.getKey());
				}
			}else{
				nonMatchSkuList.add(entry.getKey());
			}
		}
		for(String sku:nonMatchSkuList){
			skuAvTableData.remove(sku);
			similarityIndex.remove(sku);
		}
		return skuAvTableData;
	}

	private List<String> getCommodityDesc(Set<String> avs, List<AvAvbail> avAvbailList, Set<String> orgAvs, String bu) {
		List<String> comodities = new ArrayList<String>();
		comodities.add("CPU");
		comodities.add("Base Unit");
		comodities.add("Memory");
		comodities.add("HDD");
		comodities.add("SSD");
		comodities.add("LCD / LED");
		comodities.add("AC Adapter / Power Supply");
		if (bu.equals("CPC") || bu.equals("BPC") || bu.equals("WST"))
			comodities.add("Battery");
		comodities.add("SW License");
		comodities.add("CD/DVD SW");
		HashMap<String, String> descMap = new HashMap<String, String>();
		List<String> descAvList = new ArrayList<String>();
		for (AvAvbail avAvbail : avAvbailList) {
			if (avs.contains(avAvbail.getAvId())) {
				if (descMap.containsKey(avAvbail.getComodity())) {
					descMap.put(avAvbail.getComodity(), descMap.get(avAvbail.getComodity()) + " :: "
							+ avAvbail.getAvId() + ": " + avAvbail.getComodityDesc());
					descAvList.add(avAvbail.getAvId().trim());
				} else {
					descMap.put(avAvbail.getComodity(), avAvbail.getAvId() + ": " + avAvbail.getComodityDesc());
					descAvList.add(avAvbail.getAvId().trim());
				}
			}
		}

		if (!(descMap.get("CPU") != null) && descMap.get("Motherboard") != null) {
			descMap.put("CPU", descMap.get("Motherboard"));
			descMap.remove("Motherboard");
		}
		for (String av : avs) {
			if (!descAvList.contains(av)) {
				descMap.put(av, av);
			}
		}
		List<String> descList = new ArrayList<String>();
		List<String> extraAvList = new ArrayList<String>();
		for (String commodity : comodities) {
			if (descMap.containsKey(commodity)) {
				String[] avlist = descMap.get(commodity).split("::");
				boolean firstAv = true;
				for (String av : avlist) {
					if (firstAv) {
						if (orgAvs.contains(av.split(":")[0].trim())) {
							descList.add(av);
						} else {
							descList.add("<font color='red'>" + av + "</font>");
						}
						firstAv = false;
					} else {
						if (orgAvs.contains(av.split(":")[0].trim())) {
							extraAvList.add(av);
						} else {
							extraAvList.add("<font color='red'>" + av + "</font>");
						}
					}
				}
				descMap.remove(commodity);
			} else {
				if (commodity.equals("Base Unit") && descList.size() < 1) {
					descList.add(" ");
				} else if (commodity.equals("Memory") && descList.size() < 2) {
					descList.add(" ");
				} else if (commodity.equals("SSD") && descList.size() < 3) {
					descList.add(" ");
				} else if (commodity.equals("LCD / LED") && descList.size() < 4) {
					descList.add(" ");
				} else if (commodity.equals("Battery") && descList.size() < 5) {
					descList.add(" ");
				} else if (commodity.equals("CD/DVD SW") && descList.size() < 6) {
					descList.add(" ");
				}
			}
		}
		for (Map.Entry<String, String> entry : descMap.entrySet()) {
			String[] avlist = entry.getValue().split("::");
			for (String av : avlist) {
				if (orgAvs.contains(av.split(":")[0].trim())) {
					descList.add(av);
				} else {
					descList.add("<font color='red'>" + av + "</font>");
				}
			}
		}
		descList.addAll(extraAvList);
		return descList;
	}

	public Workbook exportData(String skuId, List<String> avList) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet(skuId);
		XSSFFont my_font = workbook.createFont();
		XSSFCellStyle my_style = workbook.createCellStyle();
		my_font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		my_style.setFont(my_font);
		XSSFRow row;
		// This data needs to be written
		int rowid = 0;
		int cellid = 0;
		row = spreadsheet.createRow(rowid);
		Cell cell = row.createCell(cellid++);
		cell.setCellValue("SKU Name");
		cell.setCellStyle(my_style);
		cell = row.createCell(cellid++);
		cell.setCellValue("AV ID");
		cell.setCellStyle(my_style);
		cell = row.createCell(cellid++);
		cell.setCellValue("AV Description");
		cell.setCellStyle(my_style);
		for (String av : avList) {
			if (av.trim().length() < 1)
				continue;
			cellid = 0;
			rowid++;
			row = spreadsheet.createRow(rowid);
			row.createCell(cellid++).setCellValue(skuId);
			for (String splitAv : av.split((":"))) {
				row.createCell(cellid++)
						.setCellValue(splitAv.trim().replace("<font color='red'>", "").replace("</font>", ""));
			}
		}
		spreadsheet.autoSizeColumn(0); // adjust width of the first column
		spreadsheet.autoSizeColumn(1); // adjust width of the second column
		spreadsheet.autoSizeColumn(2); // adjust width of the third column
		LOG.info("Writesheet.xlsx written successfully");
		return workbook;
	}

	private static Map<String, Double> sortByComparator(Map<String, Double> unsortMap, final boolean order) {

		List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, Double>>() {
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				if (order) {
					return o1.getValue().compareTo(o2.getValue());
				} else {
					return o2.getValue().compareTo(o1.getValue());

				}
			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
		for (Entry<String, Double> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	/*
	 * public String getSelectedSkuId() { return selectedSkuId; }
	 * 
	 * public void setSelectedSkuId(String selectedSkuId) { this.selectedSkuId =
	 * selectedSkuId; }
	 */

	/*
	 * public List<SkuDetailsInfo> gettop5avaiBU(String bu) {
	 * List<SkuDetailsInfo> list = new ArrayList<SkuDetailsInfo>(); int i = 0;
	 * for (All_Skus_Availability s :
	 * homePageDao.getTop5SkuAvailabilityforBU(bu)) {
	 * 
	 * SkuDetailsInfo info = null; info = new SkuDetailsInfo();
	 * info.setSkuId(s.getSkuId()); info.setSkuAvailability((int)
	 * s.getSkuAvailability()); info.setCount(i); list.add(info); i++; if (i ==
	 * 102) { break; } } Collections.sort(list); return list; }
	 */
	/*
	 * public String getskuDescription(String skuid) { return
	 * homePageDao.getSkuDescription(skuid).getDescription(); }
	 */

}
