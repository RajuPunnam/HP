package com.printers.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftp.support.FTPSupport;
import com.inventory.utill.ExcelSupport;
import com.io.dao.PrintersDao;
import com.io.pojos.AgingPrinters;
import com.techouts.hp.supplies.dao.SuppliesPrinterDao;


@Service
public class AgingPrinterService {

Logger log = Logger.getLogger(AgingPrinterService.class);
	
	@Autowired
	private PrintersDao printersDao;
	
	@Autowired
	private SuppliesPrinterDao suppliesPrinterDao;
	
	public String downloadFtpFile(){
		

		List<String> directoryList = new ArrayList<String>();
		String directoryName = "";
		SimpleDateFormat requiredDate = new SimpleDateFormat("MM-dd-yyyy");
		requiredDate.setLenient(false);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.setLenient(false);
		String staticDate ="11-07-2016";
		Date date=null;
		try {
			 date =requiredDate.parse(staticDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		String path="";
		boolean status = false;
		FileOutputStream fos = null;
		Calendar cal  = Calendar.getInstance();
		Date maxDate = printersDao.getMaxDate(AgingPrinters.class); 
				
		
		log.info("Last Uploaded file Date========"+maxDate);
		if(maxDate==null)
			maxDate=date;
		log.info("Last Uploaded file Date::::"+maxDate);
		
		FTPClient client = new FTPClient();
		
		for (int i = 0; i < 10; i++) {
		
			if(FTPSupport.getftpConnection(client)){
			log.info("connected");
				try {
					for (FTPFile mainDir : client.listDirectories()) {
						
						if(mainDir.isDirectory() && mainDir.getName().equals("BINOY")){
							log.info("in BINOY Folder");
							directoryList.add(mainDir.getName());
							for (FTPFile subDir : client.listDirectories("/"+mainDir.getName())) {
								
								if(subDir.isDirectory() && subDir.getName().equals("PRINTERS")){
									log.info("In PRINTERS Folder");
									directoryList.add(subDir.getName());
									for (FTPFile ssubDir : client.listDirectories(mainDir.getName()+"/"+subDir.getName())) {
										
										if(ssubDir.isDirectory() && ssubDir.getName().equals("Aging")){
											log.info("In Aging Folder");
											directoryList.add(ssubDir.getName());
											if(client.changeWorkingDirectory("/"+mainDir.getName()+"/"+subDir.getName()+"/"+ssubDir.getName()))
											{
												for (FTPFile file : client.listFiles()) {
													if(file.isFile()){
														
													try {
														
														Date fileDate =	requiredDate.parse(getFileDate(file.getName()));
													
														if(fileDate.after(maxDate)){
															log.info(file.getName()+"::File Date:::"+fileDate);
															String fileLocation =	FTPSupport.downLoadFileFromFTP(file,directoryList,client);
														
													    status =	readExelFile(file,fileLocation,fileDate);
														//File file1 = new File(fileLocation+"/"+file.getName());
														//file1.delete();
														}else{
															log.info(fileDate+" date File is Already uploaded ");
														}
													} catch (ParseException e) {
														e.printStackTrace();
													}
												}
												}
											}
										}
									}
									
								}
							}
						}
						
					}
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}
			break;
			}
		}
	return path;
	}
	
	private boolean readExelFile(FTPFile file, String fileLocation,Date fileDate) {


		File inputlocation = new File(fileLocation+File.separator+file.getName());
		log.info(file.getName());
		boolean status=false;
		Map<Integer,String> headers = new LinkedHashMap<Integer,String>();
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		String[] columnNames;
		List<String> columnNameList = null;
		int headerRowNumer = 0;
		try {
			workbook = new XSSFWorkbook(new FileInputStream(inputlocation));
		
		} catch (IOException e) {
			log.info(e.getMessage());
		}
		

		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			if(workbook.getSheetAt(i).getSheetName().equals("Base") || workbook.getSheetAt(i).getSheetName().equals("Base Aging")){
				
				log.info("workbook.getSheetAt(i).getSheetName()");
				sheet = workbook.getSheetAt(i);
				int totalRows = sheet.getLastRowNum();
				log.info("total rows::"+totalRows);
				for (int j = 0;j < totalRows; j++) {
					Row row = sheet.getRow(j);
					columnNames = ExcelSupport.getRowData(row);
					columnNameList = Arrays.asList(columnNames);
					if (columnNameList.contains("Item")) {
						headerRowNumer = j;
						break;
					} else {
						continue;
					}
				}
				
				for (int j = 0; j < columnNameList.size(); j++) {
					headers.put(j,columnNameList.get(j).trim());
				}
				
				log.info(headers+":::Header row number::"+headerRowNumer);
				
				status=loadDataToList(headers,sheet,headerRowNumer,fileDate);
				
			}
		}
		return status;
		
	}

	private boolean loadDataToList(Map<Integer, String> headers,
			XSSFSheet sheet, int headerRowNumer, Date fileDate) {


		boolean status = false;
		List<AgingPrinters> agingPrintersList = new ArrayList<AgingPrinters>();
            Iterator<Row> rowIterator =  sheet.iterator();
            
            while (rowIterator.hasNext()) {

            	AgingPrinters agingPrinters = new AgingPrinters();
            	Row row = rowIterator.next();
            	if(row.getRowNum()>headerRowNumer){
            		 int maxNumOfCells = row.getLastCellNum();
            		 Iterator<Cell>  cellIterator =row.cellIterator();
            		Cell cellData =null;
            		int i = 0;
            		/*while (cellIterator.hasNext()) {*/
                    for(int cell = 0; cell<=maxNumOfCells;cell++){
            			cellData = row.getCell(cell);
            			String coloumn = headers.get(i++);
            			if(i<=21){
            			switch (coloumn) {
						case "Warehouse":
							String warehouse  = ExcelSupport.getCellData(cellData);
							if(warehouse!="")
								agingPrinters.setWarehouse(Double.parseDouble(warehouse));
							else
								agingPrinters.setWarehouse(0);
							break;
							
						case "Item":
							agingPrinters.setItem(cellData.getStringCellValue());
							break;
							
						case "Description":
								agingPrinters.setDescription(cellData.getStringCellValue());
							break;
							
						case "Data Última Contagem":
							agingPrinters.setData_Ultima_Contagem(ExcelSupport.getCellData(cellData));
							break;
							
						case "Estoque Físico":
							String fisico = ExcelSupport.getCellData(cellData);
							if(fisico!="")
							agingPrinters.setEstoque_Fisico(Double.parseDouble(fisico));
							else
								agingPrinters.setEstoque_Fisico(0);	
							break;
							
						case "Location":
							agingPrinters.setLocation(ExcelSupport.getCellData(cellData));
							break;
							
						case "Estoque":
							String estoque =ExcelSupport.getCellData(cellData);
							if(estoque!=null && estoque!="")
									agingPrinters.setEstoque(Double.parseDouble(estoque));
								else
									agingPrinters.setEstoque(0);
								break;	

						case "Lote":
									agingPrinters.setLote(ExcelSupport.getCellData(cellData));
							break;	

						case "FIFO":
									agingPrinters.setFifo(ExcelSupport.getCellData(cellData));
							break;	

						case "Vl. Un. (R$)":
							String rs =ExcelSupport.getCellData(cellData);
								if(rs!=null && rs!="")
									agingPrinters.setVl_Un_R$(Double.parseDouble(rs));
								else
									agingPrinters.setVl_Un_R$(0);	
							break;	

						case "Vl. Un. (STD)":
							String STD = ExcelSupport.getNumericlData(cellData);
							if(STD!=null && STD!="")
								agingPrinters.setVl_Un_STD(Double.parseDouble(STD));
							else
								agingPrinters.setVl_Un_STD(0);
							break;
							

						case "CódigoABC":
							agingPrinters.setCodigoABC(ExcelSupport.getCellData(cellData));
							break;
						

						case "DtUltTrans":
							agingPrinters.setDtUltTrans(ExcelSupport.getCellData(cellData));
							break;
                					

						case "Aging":
							String aging = ExcelSupport.getCellData(cellData);
							if(aging!= null && aging!="")
							agingPrinters.setAging(Double.parseDouble(aging));
							else
								agingPrinters.setAging(0);
							break;	
						
						case "Total USD":
							String USD = ExcelSupport.getNumericlData(cellData);
							if(USD!= null && USD!="")
							agingPrinters.setTotal_USD(Double.parseDouble(USD));
							else
								agingPrinters.setTotal_USD(0);
							break;
							

						case "PCA":
							agingPrinters.setpCA(ExcelSupport.getCellData(cellData));
							break;
							

						case "Type":
							agingPrinters.setType(ExcelSupport.getCellData(cellData));
							break;
							

						case "Aging Status":
							agingPrinters.setAging_Status(ExcelSupport.getCellData(cellData));
							break;
							

						case "Supplier":
							agingPrinters.setSupplier(ExcelSupport.getCellData(cellData));
							break;
							

						case "PL":
							agingPrinters.setpL(ExcelSupport.getCellData(cellData));
							break;
						

						case "Commodities":
							agingPrinters.setCommodities(ExcelSupport.getCellData(cellData));
							break;	
						default:
							break;
						}
            			}
            			
					}
            		
                    agingPrinters.setFileDate(fileDate);
            		
            			agingPrintersList.add(agingPrinters);
            		//	System.out.println(agingPrinters);
            		
            	}
			}
		
            status = suppliesPrinterDao.insertData(agingPrintersList);
            System.out.println(agingPrintersList.size()+"====="+fileDate);
		return status;
	
		
		
	}

	public String getFileDate(String fileName){
        String[] dates = fileName.substring(14).replace(".xlsx","").split("_");
        String date = dates[1].trim()+"-"+dates[0].replace("-","").trim()+"-"+Calendar.getInstance().get(Calendar.YEAR);
        System.out.println(date);
        return date;
	}
	
}
