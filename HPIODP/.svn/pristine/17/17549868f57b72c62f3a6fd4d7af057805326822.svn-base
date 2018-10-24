package com.io.services.forecast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.io.dao.ForecastIps_SuppliesDao;
import com.io.pojos.ForeCastPrintProcessed;
import com.io.pojos.ForeCastStatus;
import com.io.pojos.ForeCast_IPS_HW;
import com.inventory.utill.ExcelSupport;
import com.inventory.utill.FileSupport;
import com.inventory.utill.OperatingSystem;

@Service
public class ForeCastSerivce_IPS_HW {
	
	private String fileType="";
	private int totalRecords;
	
	@Resource(name ="myProps")
	private Properties myproperties;
	@Autowired
	private ForecastIps_SuppliesDao forecastDao;
	
	@Autowired
    private ForecastIps_SuppliesDao	forecastIps_SuppliesDao;
	
	
	Logger log = Logger.getLogger(ForeCastSerivce_IPS_HW.class);

	
	
	/**
	 * to read files
	 * from given location
	 * @param staticHeadersMap
	 */
	public boolean readFile(Map<Integer, String> staticHeadersMap) {
		File inputlocation = new File(myproperties.getProperty("Forecast.IPS.HW"));
		log.info(inputlocation);
		File[] files = inputlocation.listFiles();
		log.info("Size:::"+files.length);
		int count = 0;
		boolean status = false;
		for (File file : files) {

			if (file.isFile() && (file.getName().endsWith(".xlsx") || file.getName().endsWith(".XLSX"))) {
				log.info(file.getName());
				readXSLXFile(file, staticHeadersMap);
			/*boolean fileStatus = FileSupport.getMoveFilesToSucessOrFailureFolder(properties.getProperty("Forecast.IPS.HW"), 
					properties.getProperty("Forecast.IPS.HW.Success"), file);
			*/
				status = true;
				file.delete();
			} else {
				log.info("is directory");
			}
		}
	return status;
	}

	
	private void readXSLXFile(File file, Map<Integer, String> staticHeadersMap) {

		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		
		
		if(file.getName().contains("CRP")){
			fileType="CRP";
		}else if (file.getName().contains("NRP")) {
			fileType="NRP";
		}
		
		try {
			workbook = new XSSFWorkbook(new FileInputStream(file));
		} catch (IOException e) {
			log.info(e.getMessage());
		}
		
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			
			if(workbook.getSheetAt(i).getSheetName().contains("MCC Completion Plan")){
				log.info(workbook.getSheetAt(i).getSheetName());
				loadExelData(workbook.getSheetAt(i),staticHeadersMap,file.getName());
			}else if (workbook.getSheetAt(i).getSheetName().contains("HP Receipt")) {
				log.info(workbook.getSheetAt(i).getSheetName());	
				loadExelData(workbook.getSheetAt(i),staticHeadersMap,file.getName());
			}
		}
		}
		
	/**
	 * to load data to 
	 * list
	 * @author vijay
	 * @param sheet
	 * @param staticHeadersMap
	 */
	
	
	private void loadExelData(XSSFSheet sheet,Map<Integer, String> staticHeadersMap,String fileName) {
		boolean status = false;
		Map<Integer, String> dynamicHeaders = new LinkedHashMap<Integer, String>();
		Map<Integer, String> headerIndex = new LinkedHashMap<Integer, String>();
		int headerRowNumer = 0;
		String[] columnNames = null;
		List<String> columnNameList = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String fileDate = getFileDate(sheet.getSheetName());
	
		log.info("file Date:"+fileDate);
		
		Date issueDate = null;
		try {
			issueDate = sdf.parse(fileDate);
		} catch (ParseException e) {
			log.info("Date format is changed");
		}
		
		log.info("Issue date "+issueDate);
		
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			columnNames = ExcelSupport.getRowData(row);
			columnNameList = Arrays.asList(columnNames);
			if (columnNameList.contains("Part Number") || columnNameList.contains("Family")) {
				headerRowNumer = i;
				break;
			} else {
				continue;
			}
		}
		
		
		for (int i = 0; i < columnNameList.size(); i++) {
			if(columnNameList.get(i).equals("Total"))
				break;
			headerIndex.put(i, columnNameList.get(i));
		}
		
		
		for (int i = 0; i < headerIndex.size(); i++) {
			Date forecastDate =null;
			String FCastDate = headerIndex.get(i);
			
			if(!staticHeadersMap.containsValue(FCastDate)){
				
				if(FCastDate.contains("-")){
					try {
						 forecastDate = sdf.parse(FCastDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					if(forecastDate.after(issueDate) || forecastDate.getTime()==issueDate.getTime() )
						dynamicHeaders.put(i, FCastDate);
				}
			}
		}
	
		status=loadDataToList(dynamicHeaders,headerIndex,staticHeadersMap,sheet,headerRowNumer,issueDate);
		log.info(headerIndex);
		if(status){
			
			ForeCastStatus foreCastStatus = new ForeCastStatus();
			foreCastStatus.setFileName(fileName+"::"+sheet.getSheetName());
			foreCastStatus.setMaxDate(issueDate);
			foreCastStatus.setUploadfileDate(sdf.format(new Date()));
		    foreCastStatus.setTotalRecords(totalRecords);
			forecastDao.saveForecastStatus(foreCastStatus);
		}
	}

	
	private boolean loadDataToList(Map<Integer, String> dynamicHeaders,Map<Integer, String> headerIndex,Map<Integer, String> staticHeadersMap, 
			XSSFSheet sheet,int headerRowNumer,Date fileDate) {
		boolean status = false;
		List<ForeCast_IPS_HW> foreCast_CRPList = new ArrayList<ForeCast_IPS_HW>();
		Iterator<Row> rowIterator = sheet.rowIterator();
	     while (rowIterator.hasNext()) {
	    	 
	    	 ForeCast_IPS_HW foreCast_CRP = new ForeCast_IPS_HW();
	    	 Row row = rowIterator.next();
	    	 
			if (row.getRowNum() > headerRowNumer) {

				String checkData[] = ExcelSupport.getRowData(row);
				List<String> rowData = Arrays.asList(checkData);
				if (rowData.contains("Total") || rowData.contains("total"))
					break;
				if(ExcelSupport.getCellData(row.getCell(1)).isEmpty())
					continue;
				
				int nullCheck=0;
				int coloumIndex=0;
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell=null;
					
					if(ExcelSupport.getCellData(cellIterator.next()).isEmpty()){
						nullCheck++;
						if(nullCheck>50)
						break;
					}
   
				String coloumnName =	headerIndex.get(coloumIndex++);	
			
				if(coloumnName.contains("-"))
					break;
				for (int j = 0; j <= staticHeadersMap.size(); j++) {
					
					if (staticHeadersMap.get(j)!=null && staticHeadersMap.get(j).equals(coloumnName)) {
						 cell = row.getCell(j);
						String cellData = ExcelSupport.getCellData(cell);
						if(coloumnName.equals("Part Number") && cellData.isEmpty())
							break;
						switch (coloumnName) {
						
						case "Part Number":
							foreCast_CRP.setPartNumber(cellData);
									break;

						case "Family":
							foreCast_CRP.setFamily(cellData);
							break;
						case "Country":
							foreCast_CRP.setCountry(cellData);
							break;
						case "Status":
							foreCast_CRP.setStatus(cellData);
							break;
						
						default :
							break;
						
						}
						break;
						
					}
				}
				}
				foreCast_CRP.setFile_Date(fileDate);
				foreCast_CRP.setFileType("IPS");
				foreCast_CRP.setCategory("HardWare");
				List<ForeCast_IPS_HW> forecastList =	loadFinalDataToList(dynamicHeaders,headerIndex,row,headerRowNumer,foreCast_CRP);
				foreCast_CRPList.addAll(forecastList);
			}
	     }
	    
	    
	   status =  forecastDao.insertForecastIPSHW(foreCast_CRPList, "fcst_print_ips_hw");
	   List<ForeCastPrintProcessed> foreCastPrintProcessedList = new ArrayList<ForeCastPrintProcessed>();
	     for (ForeCast_IPS_HW foreCast_IPS_HW : foreCast_CRPList) {
			
	    	 ForeCastPrintProcessed foreCastPrintProcessed = new ForeCastPrintProcessed();
				foreCastPrintProcessed.setSku(foreCast_IPS_HW.getPartNumber());
				foreCastPrintProcessed.setFcstDate(foreCast_IPS_HW.getForecast_Date());
				foreCastPrintProcessed.setFcstGD(foreCast_IPS_HW.getFile_Date());
				foreCastPrintProcessed.setCategory(foreCast_IPS_HW.getCategory());
				foreCastPrintProcessed.setQty(foreCast_IPS_HW.getQty());
				foreCastPrintProcessed.setType(foreCast_IPS_HW.getFileType());
				foreCastPrintProcessed.setWeek(foreCast_IPS_HW.getForeCastWeek());
				
				foreCastPrintProcessedList.add(foreCastPrintProcessed);
		}
	   
	     forecastIps_SuppliesDao.insertForeCastProcessedData(foreCastPrintProcessedList);
	     log.info(foreCast_CRPList.size());
	
	     totalRecords=foreCast_CRPList.size();
	     return status;
	
	}

	
	
	private List<ForeCast_IPS_HW> loadFinalDataToList(Map<Integer, String> dynamicHeaders,Map<Integer, String> headerIndex,Row row,
			int headerRowNumer,ForeCast_IPS_HW foreCast_CRP) {
		
		List<ForeCast_IPS_HW> foreCast_CRPList = new ArrayList<ForeCast_IPS_HW>();
		int j=0;
		try{
		if(row.getRowNum()>headerRowNumer){
			for (int i = 0; i < headerIndex.size(); i++) {
				if(headerIndex.get(i).equals(dynamicHeaders.get(i))){
					ForeCast_IPS_HW forecast = new ForeCast_IPS_HW();
					forecast.setPartNumber(foreCast_CRP.getPartNumber());
					forecast.setCountry(foreCast_CRP.getCountry());
					forecast.setFamily(foreCast_CRP.getFamily());
					forecast.setFile_Date(foreCast_CRP.getFile_Date());
					forecast.setFileType(foreCast_CRP.getFileType());
					forecast.setStatus(foreCast_CRP.getStatus());
					forecast.setCategory(foreCast_CRP.getCategory());
					
					String qty=ExcelSupport.getCellData(row.getCell(i));
					if(!qty.isEmpty())
						forecast.setQty(Double.parseDouble(qty));
					else
						forecast.setQty(0);	
					forecast.setForecast_Date(dynamicHeaders.get(i));
					forecast.setForeCastWeek(Integer.toString(j++));
					forecast.setCrp_hrp(fileType);
				//	System.out.println(forecast);
					foreCast_CRPList.add(forecast);
				
				}
			}
		}
		}catch(Exception e){
			
			log.info("Error at record no ::::"+row.getRowNum());
			log.info("Error is::::"+e.getMessage());
		}
	   return foreCast_CRPList;
	
	}
	
  
	/**
	 * 
	 * to get file date
	 * 
	 * @param sheetName
	 * @return
	 */
	public String getFileDate(String sheetName){
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		format.setLenient(false);
		String fileDate="";
		String[] fileName=sheetName.split(" ");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.setLenient(false);
		
		for (int i = 0; i < fileName.length; i++) {
			if(fileName[i].contains(".")){
				fileDate=fileName[i].replace(".","-");
			}
		}
		
		Calendar date = Calendar.getInstance();
		try {
			date.setTime(sdf.parse(fileDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fileDate = format.format(date.getTime());
		return fileDate;
	}	

}

	
