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
import java.util.Map.Entry;
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
import com.io.pojos.ForeCast_IPS_Supplies;
import com.io.pojos.ForeCast_LES_Supplies;
import com.inventory.utill.ExcelSupport;

@Service
public class ForeCastService_IPS_Supplies {

	@Resource(name ="myProps")
	private Properties properties;
	@Autowired
	private ForecastIps_SuppliesDao forecastDao;
	
	@Autowired
    private ForecastIps_SuppliesDao	forecastIps_SuppliesDao;
	
	
	Logger log = Logger.getLogger(ForeCast_IPS_Supplies.class);
	private String fileGenerationDate="";
	private int totalRecords;
	
	/**method to get
	 * 
	 * files from folder
	 * 
	 * @param staticheadersMap
	 * @return
	 */
	public boolean readFile(Map<Integer, String> staticheadersMap) {
		boolean status = false;
		
		File inputlocation = new File(properties.getProperty("Forecast.IPS.Supplies"));
		File[] files = inputlocation.listFiles();
		int count = 0;
		for (File file : files) {

			if (file.isFile() && file.getName().endsWith(".xlsx")) {
				log.info(file.getName());
				
				//calling to read xl
				readXSLXFile(file, staticheadersMap);
				/*boolean fileStatus = FileSupport.getMoveFilesToSucessOrFailureFolder(properties.getProperty("Forecast.IPS.HW"), 
					properties.getProperty("Forecast.LES.Supplies.Success"), file);*/
				/*boolean fileStatus = file.delete();
				if(fileStatus)
					log.info("file deleted");*/
				status= true;
				file.delete();
			} else {
				log.info("is directory");
			}
		}
	return status;
	}

	
	/**method to read 
	 * 
	 * headers
	 * 
	 * @param file
	 * @param staticheadersMap
	 */
	private void readXSLXFile(File file, Map<Integer, String> staticheadersMap) {
		
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		
		String fileDate = file.getName().substring(13, 21);
		fileGenerationDate = fileDate.substring(0,2)+"-"+fileDate.substring(2,4)+"-"+fileDate.substring(4,8);
		log.info("file date is:::::"+fileGenerationDate);
		try {
			workbook = new XSSFWorkbook(new FileInputStream(file));
		} catch (IOException e) {
			log.info(e.getMessage());
		}
		
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			
			if(workbook.getSheetAt(i).getSheetName().equals("DELIVERY Plan proposal")){
				log.info(workbook.getSheetAt(i).getSheetName());
				loadExelData(workbook.getSheetAt(i),staticheadersMap,file.getName());
			}
		}
	
	}
	
	
	/**method to load total 
	 * 
	 * 
	 * rows into db
	 * 
	 * @param sheet
	 * @param staticheadersMap
	 * @param fileName
	 */

	private void loadExelData(XSSFSheet sheet,Map<Integer, String> staticheadersMap,String fileName) {

		boolean status =false;
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
			
			if(!staticheadersMap.containsValue(FCastDate)){
				
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
	  status =loadDataToList(dynamicHeaders,headerIndex,staticheadersMap,sheet,headerRowNumer,issueDate);
	
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
			Map<Integer, String> staticheadersMap, XSSFSheet sheet,int headerRowNumer, Date issueDate) {

		boolean status = false;
		List<ForeCast_IPS_Supplies> foreCastIPSSuppliesList = new ArrayList<ForeCast_IPS_Supplies>();
		
		Iterator<Row> rowIterator = sheet.rowIterator();
	    log.info("List size befor adding::::::::::"+foreCastIPSSuppliesList.size()); 
		while (rowIterator.hasNext()) {
	    	  ForeCast_IPS_Supplies foreCastIPSSupplies = new ForeCast_IPS_Supplies();
	    	 Row row = rowIterator.next();
			
	    	 if(row.getCell(1, Row.RETURN_BLANK_AS_NULL)==null)
					continue;
				
	    	 
	    	 if (row.getRowNum() > headerRowNumer) {
	    		 
				int coloumIndex=0;
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell=null;
  
				String coloumnName =	headerIndex.get(coloumIndex++);	
			
				if(coloumnName.contains("-"))
					break;
				for (int j = 0; j <= staticheadersMap.size(); j++) {
					
					if (staticheadersMap.get(j)!=null && staticheadersMap.get(j).equals(coloumnName)) {
						 cell = row.getCell(j);
						String cellData = ExcelSupport.getCellData(cell);
						if(coloumnName.equals("APO Product") && cellData.isEmpty())
							break;
						switch (coloumnName) {
						
						case "APO Product":
							foreCastIPSSupplies.setApo_product(cellData);
									break;

						case "Initial":
							foreCastIPSSupplies.setInitial(cellData);
							break;
						
							default :
								break;
						}
						break;
						
					}
				}
				}
				foreCastIPSSupplies.setFcstGD(issueDate);
				foreCastIPSSupplies.setFileType("IPS");
				foreCastIPSSupplies.setCategory("Supplies");
			
				List<ForeCast_IPS_Supplies> foreCastIPSSupply =	loadFinalDataToList(dynamicHeaders,headerIndex, 
						  row,headerRowNumer,foreCastIPSSupplies);
			
				foreCastIPSSuppliesList.addAll(foreCastIPSSupply);
		}
	     }
	    
	     log.info(foreCastIPSSuppliesList.size());
	    
	    status = forecastDao.insertForecastIPSSupplies(foreCastIPSSuppliesList,"fcst_print_ips_supplies");
	    List<ForeCastPrintProcessed> foreCastPrintProcessedList = new ArrayList<ForeCastPrintProcessed>();
	    
	    for (ForeCast_IPS_Supplies foreCast_IPS_Supplies : foreCastIPSSuppliesList) {
			
	    	ForeCastPrintProcessed foreCastPrintProcessed = new ForeCastPrintProcessed();
			   foreCastPrintProcessed.setSku(foreCast_IPS_Supplies.getApo_product());
				foreCastPrintProcessed.setFcstDate(foreCast_IPS_Supplies.getFcstDate());
				foreCastPrintProcessed.setFcstGD(foreCast_IPS_Supplies.getFcstGD());
				foreCastPrintProcessed.setCategory(foreCast_IPS_Supplies.getCategory());
				foreCastPrintProcessed.setQty(foreCast_IPS_Supplies.getFcstQty());
				foreCastPrintProcessed.setType(foreCast_IPS_Supplies.getFileType());
				foreCastPrintProcessed.setWeek(foreCast_IPS_Supplies.getForecastWeek());
			   // System.out.println(foreCastPrintProcessed);
			    foreCastPrintProcessedList.add(foreCastPrintProcessed);
		}
	    
	    status =  forecastIps_SuppliesDao.insertForeCastProcessedData(foreCastPrintProcessedList);
	
	    totalRecords=foreCastIPSSuppliesList.size();
	return status;     
	}

	/**method to
	 * read cloumn wise data
	 * 
	 * @param dynamicHeaders
	 * @param headerIndex
	 * @param row
	 * @param headerRowNumer
	 * @param foreCastIPSSupplies
	 * @return
	 */
	
	private List<ForeCast_IPS_Supplies> loadFinalDataToList(Map<Integer, String> dynamicHeaders,Map<Integer, String> headerIndex, Row row, int headerRowNumer,
			ForeCast_IPS_Supplies foreCastIPSSupplies) {
		
		List<ForeCast_IPS_Supplies> foreCastIPSSuppliesList = new ArrayList<ForeCast_IPS_Supplies>();
		
		int j=0;
		try{
		
		if(row.getRowNum()>headerRowNumer){
			for (int i = 0; i < headerIndex.size(); i++) {
				if(headerIndex.get(i).equals(dynamicHeaders.get(i))){
				    ForeCast_IPS_Supplies forecast = new ForeCast_IPS_Supplies(); 
					forecast.setApo_product(foreCastIPSSupplies.getApo_product());
					forecast.setInitial(foreCastIPSSupplies.getInitial());
					forecast.setFcstGD(foreCastIPSSupplies.getFcstGD());
					forecast.setFileType(foreCastIPSSupplies.getFileType());
					forecast.setId1(foreCastIPSSupplies.getId1());
					forecast.setCategory(foreCastIPSSupplies.getCategory());
					String qty=ExcelSupport.getCellData(row.getCell(i));
					if(!qty.isEmpty())
						forecast.setFcstQty(Double.parseDouble(qty));
					else
						forecast.setFcstQty(0);	
					
					forecast.setFcstDate(dynamicHeaders.get(i));
					forecast.setForecastWeek(Integer.toString(j++));
					foreCastIPSSuppliesList.add(forecast);
				}
				
			}
			
			
		}
		}catch(Exception e){
			
			log.info("Error at record number::::"+row.getRowNum());
			log.info("Exceptio is::::"+e.getMessage());
		}
			
	   return foreCastIPSSuppliesList;
	}


}
