package com.io.services.forecast;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Response;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.io.common.Resources;
import com.io.dao.CommonDao;
import com.io.dao.ForecastDao;
import com.io.pojos.AvPartFinalQtyPer;
import com.io.pojos.Combined_Price;
import com.io.pojos.FlexBom;
import com.io.pojos.SkuBom;
import com.io.pojos.SkuToAvToPnPojo;
import com.io.utill.Mail;

@Service
public class ForeCastService {

	@Autowired
	CommonDao commonDao;
	@Autowired
	ForecastDao forecastDao;

	private List<String> list;
	/**
	 * download a file from a sharepoint library
	 */
	private static Logger LOG=Logger.getLogger(ForeCastService.class);
	public void downloadFile() throws Exception {
		
		
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
	    credsProvider.setCredentials(
	        new AuthScope(AuthScope.ANY),
	        new NTCredentials("binoy@techouts.com", "Hp12345678!", "https://hp.sharepoint.com", ""));
	    CloseableHttpClient httpclient = HttpClients.custom()
	        .setDefaultCredentialsProvider(credsProvider)
	        .build();
	    try {
	        HttpGet httpget = new HttpGet("https://hp.sharepoint.com/teams/Procurement/BusinessSharedInformation/ForecastDeliveryvolumes/BNB(AN)/2013_11_07-Forecast_bNB_Flex_07_Novembro.xlsx");
//	    	HttpGet httpget = new HttpGet(
//					"/_api/web/GetFileByServerRelativeUrl('https://hp.sharepoint.com/teams/Procurement/BusinessSharedInformation/ForecastDeliveryvolumes/BNB(AN)')/Files('2013_11_07-Forecast_bNB_Flex_07_Novembro.xlsx')/$value()");
			
	        System.out.println("Executing request " + httpget.getRequestLine());
	        CloseableHttpResponse response = httpclient.execute(httpget);
	        try {
	            System.out.println("----------------------------------------");
	            System.out.println(response.getStatusLine());
	            EntityUtils.consume(response.getEntity());
	       } finally {
	        response.close();
	    }
	    } finally {
	        httpclient.close();
	    }
			
		
//		CloseableHttpClient httpclient = HttpClients.custom()
//				.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).build();
//
//		String user = "binoy@techouts.com";
//		String pwd = "Hp12345678!";
//		CredentialsProvider credsProvider = new BasicCredentialsProvider();
//		credsProvider.setCredentials(AuthScope.ANY, new NTCredentials(user, pwd, "", ""));
//
//		HttpHost target = new HttpHost("hp.sharepoint.com", 443, "https");
//		HttpClientContext context = HttpClientContext.create();
//		context.setCredentialsProvider(credsProvider);

		// The authentication is NTLM.
		// To trigger it, we send a minimal http request
//		HttpHead request1 = new HttpHead("/");
//		CloseableHttpResponse response1 = null;
//		try {
//			response1 = httpclient.execute(target, request1, context);
//			EntityUtils.consume(response1.getEntity());
//			System.out.println("1 : " + response1.getStatusLine().getStatusCode());
//		} finally {
//			if (response1 != null)
//				response1.close();
//		}
//
//		// The real request, reuse authentication
//		String file = "2013_11_07 - Forecast_bNB_Flex_07_Novembro.xlsx"; // source
//		String targetFolder = "C:/TEMP";
//		HttpGet request2 = new HttpGet(
//				"/_api/web/GetFileByServerRelativeUrl('/teams/Procurement/BusinessSharedInformation/ForecastDeliveryvolumes/BNB(AN)')/Files('2013_11_07-Forecast_bNB_Flex_07_Novembro.xlsx')/$value()");
//		CloseableHttpResponse response2 = null;
//		try {
//			response2 = httpclient.execute(target, request2, context);
//			HttpEntity entity = response2.getEntity();
//			int rc = response2.getStatusLine().getStatusCode();
//			String reason = response2.getStatusLine().getReasonPhrase();
//			if (rc == HttpStatus.SC_OK) {
//				System.out.println("Writing " + file + " to " + targetFolder);
//				File f = new File(file);
//				File ff = new File(targetFolder, f.getName()); // target
//				// writing the byte array into a file using Apache Commons IO
//				FileUtils.writeByteArrayToFile(ff, EntityUtils.toByteArray(entity));
//			} else {
//				throw new Exception("Problem while receiving " + file + "  reason : " + reason + " httpcode : " + rc);
//			}
//		} finally {
//			if (response2 != null)
//				response2.close();
//		}
//		return;
	}

	synchronized public String  readFile(String path) {
		long oldRecords=0;	
		long countingrecords=0;
		long totalRecords=0;
		try{
		// foreCastDao.removeFRData();
		// System.out.println("cleared forecast data");
		
		List<SkuBom> skuBomList = commonDao.getALLskuTOav();
		LOG.info("Got the SKUBOM Data");
		List<AvPartFinalQtyPer> avpartList = commonDao.getAVPartFinalLevel();
		LOG.info("Got the av part Data");
		Map<String, Double> priceMasterMap = commonDao.getPriceMasterData_New();
		LOG.info("Got the price master Data");
		Map<String, List<SkuBom>> skuAvMap = new HashMap<String, List<SkuBom>>();
		Map<String, String> skuFamily = new HashMap<String, String>();
		for (SkuBom skuBom : skuBomList) {
			skuFamily.put(skuBom.getSku(), skuBom.getFamily());
			List<SkuBom> avSkuBom = new ArrayList<SkuBom>();
			if (skuAvMap.containsKey(skuBom.getSku())) {
				avSkuBom.addAll(skuAvMap.get(skuBom.getSku()));
			}
			avSkuBom.add(skuBom);
			skuAvMap.put(skuBom.getSku(), avSkuBom);
		}
		Map<String, List<AvPartFinalQtyPer>> avPartMap = new HashMap<String, List<AvPartFinalQtyPer>>();
		for (AvPartFinalQtyPer avPartFinalQtyPer : avpartList) {
			List<AvPartFinalQtyPer> avPart = new ArrayList<AvPartFinalQtyPer>();
			if (avPartMap.containsKey(avPartFinalQtyPer.getAv())) {
				avPart.addAll(avPartMap.get(avPartFinalQtyPer.getAv()));
			}
			avPart.add(avPartFinalQtyPer);
			avPartMap.put(avPartFinalQtyPer.getAv(), avPart);
		}
		
		//----------getting distinct dates from mongo
		//list=forecastDao.getFcst_DistinctDates();
		oldRecords=forecastDao.getCollectionCount("FORECAST_COMBINED_DATA_NEW_W0To26");
		list=forecastDao.getDistinct_FcstDateBU("FORECAST_COMBINED_DATA_NEW_W0To26");//------------distinct date and bu
		String finalStatus=null;
		HashMap<String, String> failedFiles = new HashMap<String, String>();
		File inputlocation = new File(path);
		File[] files = inputlocation.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				if (files[i].toString().toUpperCase().endsWith(".XLSX")) {
					LOG.info(files[i].toString());
					try {
						String status=readXSLXFile(new File(files[i].getAbsolutePath()), i, skuAvMap, avPartMap, priceMasterMap, skuFamily);
						String str[]=status.split("=");
						countingrecords+=Integer.parseInt(str[1]);
						finalStatus=str[0];
					} catch (Exception e) {
						failedFiles.put(files[i].toString(), e.getMessage());
						//e.printStackTrace();
					}
				}
			}
		}
		for (Map.Entry<String, String> entry : failedFiles.entrySet()) {
			LOG.info("Failed files are "+entry.getKey() + "=============" + entry.getValue());
		}
		totalRecords=forecastDao.getCollectionCount("FORECAST_COMBINED_DATA_NEW_W0To26");
		
		Mail.sendMail("Fcst IODP Completed successfully."+"Old count is "+oldRecords+" Processed count "+countingrecords+"Total count "+totalRecords+" "+new Date());
		finalStatus=finalStatus+" "+oldRecords+" "+countingrecords+" "+totalRecords;
		return finalStatus;
		} catch (Exception e) {
			e.printStackTrace();
			Mail.sendMail("Error while Executing IODP Fcst Data"+e.getMessage()+"Old count is "+oldRecords+" Processed count "+countingrecords+"Total count "+oldRecords+" "+new Date());
			return  e.getMessage()+" "+oldRecords+" "+countingrecords+" "+oldRecords;
		}
	}

	public String readXSLXFile(File file, int fileCount, Map<String, List<SkuBom>> skuAvMap,
			Map<String, List<AvPartFinalQtyPer>> avPartMap, Map<String, Double> priceMasterMap,Map<String, String> skuFamily) throws Exception {
		long processedRecords=0;
		try{
		ArrayList<SkuToAvToPnPojo> skuToAvToPnPojoList = new ArrayList<SkuToAvToPnPojo>();
		String fileDateBU[] = getFileDateBU(file.getName());
		SimpleDateFormat fileSDF = new SimpleDateFormat("MM-dd-yyyy");
		fileSDF.setLenient(false);
		Calendar fileDate = Calendar.getInstance();
		fileDate.setTime(fileSDF.parse(fileDateBU[2]));
		while (fileDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
			fileDate.add(Calendar.DATE, 1);
		String fileDateString = fileSDF.format(fileDate.getTime());
		
		if(list.contains(fileDateString+"="+fileDateBU[0])){
			LOG.info("----------------------->Already file generation date is "+fileDateString+" there in mongo for file date------->"+fileDateBU[2]+"="+fileDateBU[0]);
			return "No data Found!"+"="+processedRecords;
		}else{
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			if (workbook.getSheetName(i).startsWith(fileDateBU[1])) {

				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				int skuCol = -1;
				String sku = null;
				HashMap<String, Integer> dateColNos = new HashMap<String, Integer>();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					// For each row, iterate through all the columns
					Iterator<Cell> cellIterator = row.cellIterator();
					if (row.getCell(0, Row.RETURN_BLANK_AS_NULL) == null)
						continue;
					if (row.getCell(0).getStringCellValue().equalsIgnoreCase("APO Planning Version")
							|| row.getCell(0).getStringCellValue().equalsIgnoreCase("Product #")) {
						int colNo = 0;
						while (cellIterator.hasNext()) {
							Cell cell = cellIterator.next();
							if (cell.getCellType() == Cell.CELL_TYPE_STRING
									&& (cell.getStringCellValue().equalsIgnoreCase("SKU")
											|| cell.getStringCellValue().equalsIgnoreCase("Product #"))) {
								skuCol = colNo;
								colNo++;
								continue;
							}
							SimpleDateFormat fctForamt = new SimpleDateFormat("dd-MMM-yyyy");
							fctForamt.setLenient(false);
							String fctDateStr = null;
							if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
								switch (cell.getCachedFormulaResultType()) {
								case Cell.CELL_TYPE_NUMERIC:
									fctDateStr = fctForamt.format(cell.getDateCellValue());
									break;
								case Cell.CELL_TYPE_STRING:
									fctDateStr = cell.getRichStringCellValue() + "";
									break;
								}
							} else {
								fctDateStr = cell.toString();
							}
							try {
								Calendar fctDate = Calendar.getInstance();
								fctDate.setTime(fctForamt.parse(fctDateStr));
								while (fctDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
									fctDate.add(Calendar.DATE, -1);
								if (fctDate.after(fileDate) || fctDate.equals(fileDate)) {
									fctDateStr = fileSDF.format(fctDate.getTime());
									dateColNos.put(fctDateStr, colNo);
								}
							} catch (Exception e) {
							}
							colNo++;
						}
						continue;
					} else {
						String rowData[] = new String[row.getLastCellNum()];					
						for (int j = 0; j < rowData.length; j++) {
							Cell cell = row.getCell(j, Row.RETURN_BLANK_AS_NULL);
							if (cell == null) {
								rowData[j] = " ";
							} else {
								if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
									switch (cell.getCachedFormulaResultType()) {
									case Cell.CELL_TYPE_NUMERIC:
										rowData[j] = cell.getNumericCellValue() + "";
										break;
									case Cell.CELL_TYPE_STRING:
										rowData[j] = cell.getRichStringCellValue() + "";
										break;
									}
								} else {
									rowData[j] = cell.toString();
								}
							}
						}
						for (Map.Entry<String, Integer> entry : dateColNos.entrySet()) {
							Calendar foreCastDate = Calendar.getInstance();
							foreCastDate.setTime(fileSDF.parse(entry.getKey()));
							int days = (int) TimeUnit.DAYS.convert(
									(foreCastDate.getTime().getTime()) - (fileDate.getTime().getTime()),
									TimeUnit.MILLISECONDS);
							SkuToAvToPnPojo skuToAvToPnPojo = new SkuToAvToPnPojo();
							sku = rowData[skuCol];
							skuToAvToPnPojo.setType(fileDateBU[0]);
							skuToAvToPnPojo.setSKU(sku);
							skuToAvToPnPojo.setFamily(skuFamily.containsKey(sku)?skuFamily.get(sku):"NA");
							skuToAvToPnPojo.setCommodity("NA");
							skuToAvToPnPojo.setFD(entry.getKey());
							skuToAvToPnPojo.setFGD(fileDateString);
							skuToAvToPnPojo.setSkuPrice(priceMasterMap.containsKey(skuToAvToPnPojo.getSKU())
									? priceMasterMap.get(skuToAvToPnPojo.getSKU()) : 0);
							if (rowData[entry.getValue()] != null && rowData[entry.getValue()].length() > 0) {
								try {
									skuToAvToPnPojo.setSkuFQ(Double.parseDouble(rowData[entry.getValue()]));
								} catch (NumberFormatException e) {
									skuToAvToPnPojo.setSkuFQ(0.0);
								}
							} else {
								skuToAvToPnPojo.setSkuFQ(0.0);
							}
							skuToAvToPnPojo.setWeek(days / 7);
							skuToAvToPnPojoList = breakDownAVPNLevel(skuToAvToPnPojoList, skuToAvToPnPojo, skuAvMap,
									avPartMap, priceMasterMap);
						}
					}
					LOG.info(
							row.getRowNum() + "===" + sku + "===" + fileDateBU[0] + "===" + skuToAvToPnPojoList.size());
					if (skuToAvToPnPojoList.size() > 1000000) {
						processedRecords+=forecastDao.saveSkuToAvToPnPojo(skuToAvToPnPojoList);
						skuToAvToPnPojoList.clear();
					}
				}
				if (skuToAvToPnPojoList.size() > 0) {
					processedRecords+=forecastDao.saveSkuToAvToPnPojo(skuToAvToPnPojoList);
				}
			}
		}
		return "Success="+processedRecords;
	 }//............date checking ending else block.
		} catch (Exception e) {
			e.printStackTrace();
			Mail.sendMail("Error while Executing IODP Fcst Data"+e.getMessage()+new Date());
			return  e.getMessage()+"="+processedRecords;
		}
	}

	public ArrayList<SkuToAvToPnPojo> breakDownAVPNLevel(ArrayList<SkuToAvToPnPojo> skuToAvToPnPojoList,
			SkuToAvToPnPojo skuToAvToPnPojo, Map<String, List<SkuBom>> skuAvMap,
			Map<String, List<AvPartFinalQtyPer>> avPartMap, Map<String, Double> priceMasterMap) {
		if (skuAvMap.containsKey(skuToAvToPnPojo.getSKU())) {
			List<SkuBom> avList = skuAvMap.get(skuToAvToPnPojo.getSKU());
			for (SkuBom skuBom : avList) {
				if (avPartMap.containsKey(skuBom.getAvs())) {
					List<AvPartFinalQtyPer> avPartFinalQtyPerList = avPartMap.get(skuBom.getAvs());
					for (AvPartFinalQtyPer avPartFinalQtyPer : avPartFinalQtyPerList) {
						SkuToAvToPnPojo skuToAvToPnPojoPN = new SkuToAvToPnPojo(skuToAvToPnPojo);
						skuToAvToPnPojoPN.setAV(avPartFinalQtyPer.getAv());
						skuToAvToPnPojoPN.setAvFQ(skuToAvToPnPojo.getSkuFQ());
						skuToAvToPnPojoPN.setPN(avPartFinalQtyPer.getPartNumber());
						skuToAvToPnPojoPN.setCommodity(avPartFinalQtyPer.getCommodity());
						skuToAvToPnPojoPN.setPFQ(skuToAvToPnPojoPN.getAvFQ() * avPartFinalQtyPer.getFinalQty());
						skuToAvToPnPojoPN.setPnPrice(priceMasterMap.containsKey(skuToAvToPnPojoPN.getPN())
								? priceMasterMap.get(skuToAvToPnPojoPN.getPN()) : 0);
						skuToAvToPnPojoList.add(skuToAvToPnPojoPN);
					}
				} else {
					SkuToAvToPnPojo skuToAvToPnPojoPN = new SkuToAvToPnPojo(skuToAvToPnPojo);
					skuToAvToPnPojoPN.setAV(skuBom.getAvs());
					skuToAvToPnPojoPN.setAvFQ(skuToAvToPnPojo.getSkuFQ());
					skuToAvToPnPojoPN.setPN(skuBom.getAvs());
					skuToAvToPnPojoPN.setPFQ(skuToAvToPnPojo.getSkuFQ());
					skuToAvToPnPojoPN.setPnPrice(0.0);
					skuToAvToPnPojoList.add(skuToAvToPnPojoPN);
				}
			}
		} else {
			skuToAvToPnPojo.setAV(skuToAvToPnPojo.getSKU());
			skuToAvToPnPojo.setAvFQ(skuToAvToPnPojo.getSkuFQ());
			skuToAvToPnPojo.setAvPrice(0.0);
			skuToAvToPnPojo.setPN(skuToAvToPnPojo.getSKU());
			skuToAvToPnPojo.setPFQ(skuToAvToPnPojo.getSkuFQ());
			skuToAvToPnPojo.setPnPrice(0.0);
			skuToAvToPnPojoList.add(skuToAvToPnPojo);
		}
		return skuToAvToPnPojoList;
	}

	public String[] getFileDateBU(String fileName) {
		String fdBU[] = new String[3];
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		format.setLenient(false);
		if (fileName.contains("5X")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			sdf.setLenient(false);
			fdBU[0] = "WST";
			fdBU[1] = "OPT_5X";
			Calendar fileDate = Calendar.getInstance();
			try {
				fileDate.setTime(sdf.parse(fileName.substring(0, 6)));
				fdBU[2] = format.format(fileDate.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (fileName.contains("7F")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			sdf.setLenient(false);
			fdBU[0] = "BPC";
			fdBU[1] = "OPT_7F";
			Calendar fileDate = Calendar.getInstance();
			try {
				fileDate.setTime(sdf.parse(fileName.substring(0, 6)));
				fdBU[2] = format.format(fileDate.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (fileName.contains("Consumer")) {
			SimpleDateFormat sdf = new SimpleDateFormat("MMMdd_yyyy");
			sdf.setLenient(false);
			fdBU[0] = "CNB";
			fdBU[1] = "FCST";
			String array[] = fileName.split(" ");
			Calendar fileDate = Calendar.getInstance();
			try {
				fileDate.setTime(sdf.parse(array[3].substring(0, 10)));
				fdBU[2] = format.format(fileDate.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (fileName.contains("BNB")) {
			SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
			fdBU[0] = "BNB";
			fdBU[1] = "OPT_NA";
			String array[] = fileName.split("_");
			Calendar fileDate = Calendar.getInstance();
			try {
				fileDate.setTime(sdf.parse(array[3].substring(0, 3) + "-" + array[3].substring(3, 5) + "-" 
									+ Calendar.getInstance().get(Calendar.YEAR)));
						//+"2015"));
				
				fdBU[2] = format.format(fileDate.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (fileName.contains("WIF")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_dd_MMM");
			sdf.setLenient(false);
			fdBU[0] = "CPC";
			String array[] = fileName.split(" ");
			array[1] = array[1].substring(0, 11);
			String array2[] = array[1].split("_");
			fdBU[1] = array2[2] + "_" + array2[1];
			Calendar fileDate = Calendar.getInstance();
			try {
				fileDate.setTime(sdf.parse(array[1]));
				fdBU[2] = format.format(fileDate.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fdBU;
	}

	public void generateFinalQty() {
		Map<String, List<FlexBom>> avflexBomMap = new HashMap<String, List<FlexBom>>();
		List<FlexBom> initialflexBomList = commonDao.getFlexBomData();
		for (FlexBom flexBom : initialflexBomList) {
			List<FlexBom> avFlexBom = new ArrayList<FlexBom>();
			String id = flexBom.getAv();
			if (avflexBomMap.containsKey(id)) {
				avFlexBom.addAll(avflexBomMap.get(id));
			}
			avFlexBom.add(flexBom);
			avflexBomMap.put(id, avFlexBom);
		}

		List<AvPartFinalQtyPer> finalFlexBomList = new ArrayList<AvPartFinalQtyPer>();
		List<String> keyList = new ArrayList<String>(avflexBomMap.keySet());
		for (String key : keyList) {
			List<FlexBom> flexBomList = avflexBomMap.get(key);
			for (int count = flexBomList.size() - 1; count >= 0; count--) {
				boolean saveData = false;
				FlexBom outerFlexBom = flexBomList.get(count);
				int check = outerFlexBom.getLevel();
				if (outerFlexBom.getPartNumber().equals("666756-00A"))
					System.out.println();
				if (outerFlexBom.getBomTypep().equals("PRI") && count + 1 < flexBomList.size()
						&& outerFlexBom.getLevel() >= flexBomList.get(count + 1).getLevel()) {
					saveData = true;
				}
				if (count == flexBomList.size() - 1 && outerFlexBom.getBomTypep().equals("PRI"))
					saveData = true;
				AvPartFinalQtyPer avPartFinalQtyPer = new AvPartFinalQtyPer();
				double finalQty = outerFlexBom.getQtyPer();
				for (int index = count - 1; index >= 0; index--) {
					FlexBom innerFlexBom = flexBomList.get(index);
					try {
						if (outerFlexBom.getLevel() > innerFlexBom.getLevel()) {
							if (check > innerFlexBom.getLevel()) {
								finalQty = finalQty * (innerFlexBom.getQtyPer() > 0 ? innerFlexBom.getQtyPer() : 1);
								if (saveData && innerFlexBom.getBomTypep().equals("ALT")) {
									saveData = false;
								}
								check = innerFlexBom.getLevel();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				avPartFinalQtyPer.setAv(outerFlexBom.getAv());
				avPartFinalQtyPer.setPartNumber(outerFlexBom.getPartNumber());
				avPartFinalQtyPer.setFinalQty(finalQty);
				avPartFinalQtyPer.setMPC(outerFlexBom.getMpc());
				avPartFinalQtyPer.setCommodity(outerFlexBom.getCommodity());
				if (saveData) {
					finalFlexBomList.add(avPartFinalQtyPer);
				}
			}
		}
		commonDao.saveFinalQuantity(finalFlexBomList);
	}
}
