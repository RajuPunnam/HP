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
import com.io.pojos.ForeCast_LES_Supplies;
import com.inventory.utill.ExcelSupport;

@Service
public class ForeCastService_LES_Supplies {
	
	@Resource(name ="myProps")
	private Properties properties;
	
	@Autowired
	private ForecastIps_SuppliesDao forecastDao;
	
	@Autowired
    private ForecastIps_SuppliesDao	forecastIps_SuppliesDao;
	
	
	
	Logger log = Logger.getLogger(ForeCastService_LES_Supplies.class);
	private String fileGenerationDate="";
    private int totalRecords;
	
	public boolean readFile(Map<Integer,String> staticHeadersMap){
		boolean status = false;
		File inputlocation = new File(properties.getProperty("Forecast.LES.Supplies"));
		File[] files = inputlocation.listFiles();
		int count = 0;
		for (File file : files) {

			if (file.isFile() && file.getName().endsWith(".xlsx")) {
				log.info(file.getName());
				readXSLXFile(file, staticHeadersMap);
				/*boolean fileStatus = FileSupport.getMoveFilesToSucessOrFailureFolder(properties.getProperty("Forecast.IPS.HW"), 
					properties.getProperty("Forecast.LES.Supplies.Success"), file);*/
				/*boolean fileStatus = file.delete();
				if(fileStatus)
					log.info("file deleted");*/
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
		String fileDate ="";
		if(file.getName().contains("Toner_HP_Delivery Plan_Flextronics"))
		 fileDate = file.getName().substring(35, 48);
		else if (file.getName().contains("Toner_HP_Production Plan_Flextronics")) {
			fileDate = file.getName().substring(37, 51);
		}
			
		log.info(fileDate);
		String[] dateArray = fileDate.split("_");
		fileGenerationDate = getFileDate(dateArray);
		
		log.info("file date is:::::"+fileGenerationDate);
		try {
			workbook = new XSSFWorkbook(new FileInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			
			if(workbook.getSheetAt(i).getSheetName().contains("Weekly Delivery Plan") || workbook.getSheetAt(i).getSheetName().contains("Weekly Production Plan")){
				log.info(workbook.getSheetAt(i).getSheetName());
				loadExelData(workbook.getSheetAt(i),staticHeadersMap,file.getName());
			}
		}
	}
	
	private void loadExelData(XSSFSheet sheet,Map<Integer, String> staticHeadersMap,String fileName) {
		boolean status = false;
		Map<Integer, String> dynamicHeaders = new LinkedHashMap<Integer, String>();
		Map<Integer, String> headerIndex = new LinkedHashMap<Integer, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		int headerRowNumer = 0;
		String[] columnNames = null;
		List<String> columnNameList = null;
	    Date issueDate=null;
		try {
			issueDate = sdf.parse(fileGenerationDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if(row.getCell(1, Row.RETURN_BLANK_AS_NULL)==null && row.getCell(2, Row.RETURN_BLANK_AS_NULL)==null)
				continue;
			columnNames =ExcelSupport.getRowData(row);
			columnNameList = Arrays.asList(columnNames);
			if (columnNameList.contains("APO Product") || columnNameList.contains("Model")) {
				headerRowNumer = i;
				break;
			} else {
				continue;
			}
		}
		
		
		for (int i = 0; i < columnNameList.size(); i++) {
			if(columnNameList.get(i).isEmpty())
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
		status = loadDataToList(dynamicHeaders,headerIndex,staticHeadersMap,sheet,headerRowNumer,issueDate);

		
		if(status){
			  ForeCastStatus foreCastStatus = new ForeCastStatus();
				foreCastStatus.setFileName(fileName);
				foreCastStatus.setMaxDate(issueDate);
				foreCastStatus.setUploadfileDate(sdf.format(new Date()));
			    foreCastStatus.setTotalRecords(totalRecords);
				forecastDao.saveForecastStatus(foreCastStatus);
			
		  }
		
	}


	private boolean loadDataToList(Map<Integer, String> dynamicHeaders,Map<Integer, String> headerIndex,
			Map<Integer, String> staticHeadersMap, XSSFSheet sheet,int headerRowNumer,Date issueDate ) {
		
		boolean status = false;
		List<ForeCast_LES_Supplies> foreCast_LES_SuppliesList = new ArrayList<ForeCast_LES_Supplies>();
  
		Iterator<Row> rowIterator = sheet.rowIterator();
	     
	     while (rowIterator.hasNext()) {
	    	 
	    	 ForeCast_LES_Supplies foreCast_Toner_HP = new ForeCast_LES_Supplies();
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
						
						case "APO Product":
							foreCast_Toner_HP.setAPOProduct(cellData);
									break;

						case "Model":
							foreCast_Toner_HP.setModel(cellData);
							break;
						case "Plt":
							foreCast_Toner_HP.setPlt(Double.parseDouble(cellData));
							break;
						case "Size Code":
							foreCast_Toner_HP.setSizeCode(Double.parseDouble(cellData));
							break;
							
						case "T/S":
							foreCast_Toner_HP.setT_s(cellData);
							break;
							
						case "Familia":
							foreCast_Toner_HP.setFamily(cellData);
							break;	
						
						}
						break;
						
					}
				}
				}
				foreCast_Toner_HP.setFileDate(issueDate);
				foreCast_Toner_HP.setFileType("LES");
				foreCast_Toner_HP.setCategory("Supplies");
				List<ForeCast_LES_Supplies> forecastList = loadFinalDataToList(dynamicHeaders,headerIndex,row,
						headerRowNumer,foreCast_Toner_HP);
				foreCast_LES_SuppliesList.addAll(forecastList);
			}
		}
	   status = forecastDao.insertForecastLESSupplies(foreCast_LES_SuppliesList, "fcst_print_les_supplies");
	  
		List<ForeCastPrintProcessed> foreCastPrintProcessedList = new ArrayList<ForeCastPrintProcessed>();

	   for (ForeCast_LES_Supplies foreCast_LES_Supplies : foreCast_LES_SuppliesList) {
		   ForeCastPrintProcessed foreCastPrintProcessed = new ForeCastPrintProcessed();
		   foreCastPrintProcessed.setSku(foreCast_LES_Supplies.getAPOProduct());
			foreCastPrintProcessed.setFcstDate(foreCast_LES_Supplies.getForecastDate());
			foreCastPrintProcessed.setFcstGD(foreCast_LES_Supplies.getFileDate());
			foreCastPrintProcessed.setCategory(foreCast_LES_Supplies.getCategory());
			foreCastPrintProcessed.setQty(foreCast_LES_Supplies.getQty());
			foreCastPrintProcessed.setType(foreCast_LES_Supplies.getFileType());
			foreCastPrintProcessed.setWeek(foreCast_LES_Supplies.getForecastWeek());
		    foreCastPrintProcessedList.add(foreCastPrintProcessed);	   
	    }
	
	 
	   status =  forecastIps_SuppliesDao.insertForeCastProcessedData(foreCastPrintProcessedList);
	   totalRecords = foreCast_LES_SuppliesList.size();
	   log.info(foreCast_LES_SuppliesList.size());
	return status;
	}

	

	private List<ForeCast_LES_Supplies> loadFinalDataToList(Map<Integer, String> dynamicHeaders,Map<Integer, String> headerIndex, Row row, int headerRowNumer,
			ForeCast_LES_Supplies foreCast_Toner_HP) {
		
		List<ForeCast_LES_Supplies> foreCast_LES_SuppliesList = new ArrayList<ForeCast_LES_Supplies>();
		
		int j=0;
		try{
		if(row.getRowNum()>headerRowNumer){
			for (int i = 0; i < headerIndex.size(); i++) {
				if(headerIndex.get(i).equals(dynamicHeaders.get(i))){
					ForeCast_LES_Supplies foreCast_Toner = new ForeCast_LES_Supplies();
					foreCast_Toner.setAPOProduct(foreCast_Toner_HP.getAPOProduct());
					foreCast_Toner.setFamily(foreCast_Toner_HP.getFamily());
					foreCast_Toner.setFileDate(foreCast_Toner_HP.getFileDate());
					foreCast_Toner.setModel(foreCast_Toner_HP.getModel());
					foreCast_Toner.setPlt(foreCast_Toner_HP.getPlt());
					foreCast_Toner.setT_s(foreCast_Toner_HP.getT_s());
					foreCast_Toner.setSizeCode(foreCast_Toner_HP.getSizeCode());
					foreCast_Toner.setFileType(foreCast_Toner_HP.getFileType());
					foreCast_Toner.setCategory(foreCast_Toner_HP.getCategory());
					String qty=ExcelSupport.getCellData(row.getCell(i));
					if(!qty.isEmpty())
						foreCast_Toner.setQty(Double.parseDouble(qty));
					else
						foreCast_Toner.setQty(0);	
					foreCast_Toner.setForecastDate(dynamicHeaders.get(i));
					foreCast_Toner.setForecastWeek(Integer.toString(j++));
					foreCast_LES_SuppliesList.add(foreCast_Toner);
				}
			}
		}
		}catch(Exception e){
			
			log.info("Error at record number::::"+row.getRowNum());
			log.info("Exceptio is::::"+e.getMessage());
		}
		return foreCast_LES_SuppliesList;
	}


	public String getFileDate(String[] dateArray){
		
		String date="";
		String issueDate = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		dateFormat.setLenient(false);
		if(dateArray[1].length()>3)
		dateArray[1] = dateArray[1].substring(0, 3);
		
		Calendar calendar = Calendar.getInstance();
		if(dateArray[2].length()==4)
			issueDate =dateArray[1].trim()+"-"+dateArray[0].trim()+"-"+dateArray[2].trim();
		else
			issueDate = dateArray[1].trim()+"-"+dateArray[0].trim()+"-"+Integer.toString(calendar.get(Calendar.YEAR)).substring(0,2)+dateArray[2].trim();
		log.info(issueDate);
		SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
		sdf.setLenient(false);
		String year =Integer.toString(calendar.get(Calendar.YEAR)).substring(0,2);
		try {
			calendar.setTime(sdf.parse(issueDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date =  dateFormat.format(calendar.getTime());
		return date;
	}

}
