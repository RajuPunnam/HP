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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.io.dao.ForecastIps_SuppliesDao;
import com.io.pojos.ForeCastPrintProcessed;
import com.io.pojos.ForeCastStatus;
import com.io.pojos.ForeCast_Les_HW;
import com.io.pojos.ForeCast_LES_Supplies;
import com.inventory.utill.ExcelSupport;

@Service
public class ForeCastService_LES_HW {

	@Resource(name ="myProps")
	private Properties properties;
	@Autowired
	private ForecastIps_SuppliesDao forecastDao;
	@Autowired
    private ForecastIps_SuppliesDao	forecastIps_SuppliesDao;

	
	private int totalRecords;
	Logger log = Logger.getLogger(ForeCastService_LES_HW.class);
	private String fileGenerationDate="";

	
	public boolean readFile(Map<Integer, String> staticheadersMap) {

		boolean status = false;
		File inputlocation = new File(properties.getProperty("Forecast.LES.HW"));
		File[] files = inputlocation.listFiles();
		int count = 0;
		for (File file : files) {

			if (file.isFile() && (file.getName().endsWith(".xls") || file.getName().endsWith(".XlS"))) {
				log.info(file.getName());
				readXSLXFile(file, staticheadersMap);
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


	private void readXSLXFile(File file, Map<Integer, String> staticheadersMap) {

		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		
		String fileDate = file.getName().substring(27, 38);
		log.info(fileDate);
		String[] dateArray = fileDate.split("_");
		
		fileGenerationDate = getFileDate(dateArray);
		log.info("file date is:::::"+fileGenerationDate);
		try {
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} catch (IOException e) {
			log.info(e.getMessage());
		}
		
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			
			if(workbook.getSheetAt(i).getSheetName().contains("Ship_Plan")){
				log.info(workbook.getSheetAt(i).getSheetName());
				loadExelData(workbook.getSheetAt(i),staticheadersMap,file.getName());
			}
		}
	
		
	}
	

	private void loadExelData(HSSFSheet sheet,Map<Integer, String> staticHeadersMap,String fileName) {

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
			
		try{
			if((row.getCell(1, Row.RETURN_BLANK_AS_NULL)==null && row.getCell(2, Row.RETURN_BLANK_AS_NULL)==null))
				continue;
		}catch(Exception e){
			log.info("Exception is:::::"+e.getMessage());
			continue;
		}
			columnNames =ExcelSupport.getRowData(row);
			columnNameList = Arrays.asList(columnNames);
			if (columnNameList.contains("Planning Product") && columnNameList.contains("Country")) {
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
			Map<Integer, String> staticHeadersMap, HSSFSheet sheet,int headerRowNumer, Date issueDate) {
		
		boolean status = false;
		List<ForeCast_Les_HW> foreCast_Les_HWList = new ArrayList<ForeCast_Les_HW>();
 
		Iterator<Row> rowIterator = sheet.rowIterator();
	     
	     while (rowIterator.hasNext()) {
	    	 
	    	 ForeCast_Les_HW foreCastPrintLesHW = new ForeCast_Les_HW();
	    	 Row row = rowIterator.next();
	    	 
			if (row.getRowNum() > headerRowNumer) {

				try{
					if((row.getCell(1, Row.RETURN_BLANK_AS_NULL)==null && row.getCell(2, Row.RETURN_BLANK_AS_NULL)==null))
						break;
				}catch(Exception e){
					log.info("Exception is:::::"+e.getMessage());
					break;
				}
				
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
						
						
						case "Planning Product":
							foreCastPrintLesHW.setPlanningProduct(cellData);
							//System.out.println("Part Number::"+cellData);
									break;

						case "Location":
							foreCastPrintLesHW.setLocation(cellData);
						//	System.out.println("Family:::"+cellData);
							break;
						case "Country":
							foreCastPrintLesHW.setCountry(cellData);
							//System.out.println("Country:::"+cellData);
							break;
						case "Category":
							foreCastPrintLesHW.setCategory(cellData);
							//System.out.println("Status:::"+cellData);
							break;
							
						case "PL":
							foreCastPrintLesHW.setPl(cellData);
							//System.out.println("Status:::"+cellData);
							break;
							
						case "Family":
							foreCastPrintLesHW.setFamily(cellData);
							//System.out.println("Status:::"+cellData);
							break;	
							
						case "PPB":
							foreCastPrintLesHW.setPpb(cellData);
							//System.out.println("Status:::"+cellData);
							break;
							
						default :
							break;
							
							
						}
						break;
						
					}
				}
				}
				foreCastPrintLesHW.setFcstGD(issueDate);
				foreCastPrintLesHW.setType("LES");
				foreCastPrintLesHW.setCategory1("HardWare");;
				
				
			
				List<ForeCast_Les_HW> forecastList = loadFinalDataToList(dynamicHeaders,headerIndex,row,headerRowNumer,foreCastPrintLesHW);
			
				foreCast_Les_HWList.addAll(forecastList);
			}
		}
	     
	    status = forecastDao.insertForecastLESHW(foreCast_Les_HWList, "fcst_print_les_hw");
	   
	    List<ForeCastPrintProcessed> foreCastPrintProcessedList = new ArrayList<ForeCastPrintProcessed>();
	    for (ForeCast_Les_HW foreCast_Les_HW : foreCast_Les_HWList) {
			
	    	ForeCastPrintProcessed foreCastPrintProcessed = new ForeCastPrintProcessed();
			   foreCastPrintProcessed.setSku(foreCast_Les_HW.getPlanningProduct());
				foreCastPrintProcessed.setFcstDate(foreCast_Les_HW.getFcstDate());
				foreCastPrintProcessed.setFcstGD(foreCast_Les_HW.getFcstGD());
				foreCastPrintProcessed.setCategory(foreCast_Les_HW.getCategory1());
				foreCastPrintProcessed.setQty(foreCast_Les_HW.getFcstQty());
				foreCastPrintProcessed.setType(foreCast_Les_HW.getType());
				foreCastPrintProcessed.setWeek(foreCast_Les_HW.getForecastWeek());
			   // System.out.println(foreCastPrintProcessed);
			    foreCastPrintProcessedList.add(foreCastPrintProcessed);
	    	
		}
	    
	    status =  forecastIps_SuppliesDao.insertForeCastProcessedData(foreCastPrintProcessedList);
	    
	    totalRecords= foreCast_Les_HWList.size();
	    log.info(foreCast_Les_HWList.size());
		return status;
	}


	private List<ForeCast_Les_HW> loadFinalDataToList(Map<Integer, String> dynamicHeaders,Map<Integer, String> headerIndex, Row row, int headerRowNumer,
			ForeCast_Les_HW foreCastPrintLesHW) {
		List<ForeCast_Les_HW> foreCast_Les_HWList = new ArrayList<ForeCast_Les_HW>();
		
		int j=0;
		try{
		if(row.getRowNum()>headerRowNumer){
			
			for (int i = 0; i < headerIndex.size(); i++) {
				if(headerIndex.get(i).equals(dynamicHeaders.get(i))){
					ForeCast_Les_HW foreCast = new ForeCast_Les_HW();
					foreCast.setPlanningProduct(foreCastPrintLesHW.getPlanningProduct());
					foreCast.setCountry(foreCastPrintLesHW.getCountry());
					foreCast.setCategory(foreCastPrintLesHW.getCategory());
					foreCast.setFamily(foreCastPrintLesHW.getFamily());
					foreCast.setFcstGD(foreCastPrintLesHW.getFcstGD());
					foreCast.setType(foreCastPrintLesHW.getType());
					foreCast.setPl(foreCastPrintLesHW.getPl());
					foreCast.setPpb(foreCastPrintLesHW.getPpb());
					foreCast.setId1(foreCastPrintLesHW.getId1());
					foreCast.setLocation(foreCastPrintLesHW.getLocation());
					foreCast.setCategory1(foreCastPrintLesHW.getCategory1());
					String qty=ExcelSupport.getCellData(row.getCell(i));
					if(qty!=null && !qty.isEmpty())
						foreCast.setFcstQty(Double.parseDouble(qty));
					else
						foreCast.setFcstQty(0);	
					
					foreCast.setFcstDate(dynamicHeaders.get(i));
					foreCast.setForecastWeek(Integer.toString(j++));
					//System.out.println(foreCast);
					foreCast_Les_HWList.add(foreCast);
				}
			}
		}

		}catch(Exception e){
			
			log.info("Error at record number::::"+row.getRowNum());
			log.info("Exceptio is::::"+e.getMessage());
		}
		return foreCast_Les_HWList;
		
	}


	public String getFileDate(String[] dateArray){
		
		String date="";
		String issueDate="";
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		dateFormat.setLenient(false);
		if(dateArray[1].length()>3)
		dateArray[1] = dateArray[1].substring(0, 3);
		
		Calendar calendar = Calendar.getInstance();
		if(dateArray[2].length()==4)
			issueDate =dateArray[1]+"-"+dateArray[0]+"-"+dateArray[2];
		else
			issueDate = dateArray[1]+"-"+dateArray[0]+"-"+Integer.toString(calendar.get(Calendar.YEAR)).substring(0,2)+dateArray[2];
		System.out.println(issueDate);
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
