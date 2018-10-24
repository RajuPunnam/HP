package com.io.services.po;

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

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.utill.ExcelSupport;
import com.io.dao.PrintersDao;
import com.io.pojos.EoCommodityPojo;
import com.techouts.hp.supplies.dao.SuppliesPrinterDao;

@Service
public class EoCommodityService {

	Logger log = Logger.getLogger(EoCommodityService.class);
	
	@Autowired
	private PrintersDao printersDao;
	@Autowired
	private SuppliesPrinterDao suppliesPrinterDao;
	
	public void uploadDatatoMysql(){
		boolean status = false;
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		String path ="";
		path = downloadFileFromFTP();
		log.info(path);
		String[] fileDate = path.split("_");
		path = fileDate[0];
		Date date = null;
		try {
			date = format.parse(fileDate[1]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(!path.isEmpty()){
			File inputlocation = new File(path);
			System.out.println(path);
			File[] files = inputlocation.listFiles();
			int count = 0;
			for (File file : files) {
				if (file.isFile() && file.getName().endsWith(".xlsx")) {
					log.info(file.getName());
					status = readXSLXFile(file,date);
					
					file.delete();
				} else {
					log.info("is directory");
				}
			}
		}
	}
	
	
	
	private boolean readXSLXFile(File file,Date fileDate) {

		
		log.info(file.getName());
		//log.info("Absolute path::::"+file.getAbsolutePath());
		boolean status=false;
		Map<Integer,String> headers = new LinkedHashMap<Integer,String>();
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		String[] columnNames;
		List<String> columnNameList = null;
		int headerRowNumer = 0;
		try {
			workbook = new XSSFWorkbook(new FileInputStream(file));
		} catch (IOException e) {
			log.info(e.getMessage());
		}
		
for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
	System.out.println(workbook.getSheetAt(i).getSheetName());		
	if(workbook.getSheetAt(i).getSheetName().equals("DATA")){
				
				log.info("Data");
				sheet = workbook.getSheetAt(i);
				int totalRows = sheet.getLastRowNum();
				System.out.println("total rows::"+totalRows);
				for (int j = 0;j < totalRows; j++) {
					Row row = sheet.getRow(j);
					columnNames = ExcelSupport.getRowData(row);
					columnNameList = Arrays.asList(columnNames);
					System.out.println(columnNameList);
					if (columnNameList.contains("Part#FLEX")) {
						headerRowNumer = j;
						break;
					} else {
						continue;
					}
				}
				
				for (int j = 0; j < columnNameList.size(); j++) {
					headers.put(j,columnNameList.get(j).trim());
				}
				
				log.info(headers);
				log.info("header row::"+headerRowNumer);
				
				status = loadDataToList(headers,sheet,headerRowNumer,fileDate);
				
			}
		}
return status;
	}



	private boolean loadDataToList(Map<Integer, String> headers,
			XSSFSheet sheet, int headerRowNumer,Date fileDate) {
		boolean status = false;
	SimpleDateFormat required = new SimpleDateFormat("MM-dd-yyyy");
		List<EoCommodityPojo> commodityPojosList = new ArrayList<EoCommodityPojo>();
            Iterator<Row> rowIterator =  sheet.iterator();
            
            while (rowIterator.hasNext()) {

            	EoCommodityPojo commodityPojo = new EoCommodityPojo();
            	Row row = rowIterator.next();
            	if(row.getRowNum()>headerRowNumer){
            		
            		Iterator<Cell>  cellIterator =row.cellIterator();
            		Cell cellData =null;
            		int i = 0;
            		while (cellIterator.hasNext()) {

            			cellData = cellIterator.next();
            			String coloumn = headers.get(i++);
            			
            			switch (coloumn) {
						case "Part# HP":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setPartHP(ExcelSupport.getCellData(cellData).trim());
							else
								commodityPojo.setPartHP(null);
							break;
							
						case "Part#FLEX":
							if(ExcelSupport.getCellData(cellData)!="")
								commodityPojo.setPartFLEX(ExcelSupport.getCellData(cellData).trim());
							else
								commodityPojo.setPartFLEX(null);
							break;
							
						case "Description":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setDescription(ExcelSupport.getCellData(cellData).trim());
							else
								commodityPojo.setDescription(null);
							break;
							
						case "Family":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setFamily(ExcelSupport.getCellData(cellData).trim());
							else
								commodityPojo.setDescription(null);
							break;
							
						case "Commodity":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setCommodity(ExcelSupport.getCellData(cellData).trim());
							else
								commodityPojo.setDescription(null);
						
							break;
							
						case "PL":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setPl(ExcelSupport.getCellData(cellData).trim());
							else
								commodityPojo.setDescription(null);
						
							break;
							
						case "PCA/BOX":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setPca_box(ExcelSupport.getCellData(cellData).trim());
							else
								commodityPojo.setDescription(null);
						
							break;	

						case "Qty":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setQty(Double.parseDouble(getCellData(cellData)));
							else
								commodityPojo.setDescription(null);
						
							break;	

						case "> 1 Year":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setGT1Year(ExcelSupport.getCellData(cellData).trim());
							else
								commodityPojo.setDescription(null);
						
							break;	

						case "Demand":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setDemand(ExcelSupport.getCellData(cellData).trim());
							else
								commodityPojo.setDescription(null);
						
							break;	

						case "Aging > 1 Year":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setAgingGT1Year(ExcelSupport.getCellData(cellData).trim());
							else
								commodityPojo.setDescription(null);
						
							break;
							

						case "ISSUE":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setIssue(ExcelSupport.getCellData(cellData));
							else
								commodityPojo.setDescription(null);
						
							break;
						

						case "Data Início Accrual":
							Date date = null;
							try {
								if(ExcelSupport.getCellData(cellData)!="")
								date = required.parse(ExcelSupport.getCellData(cellData));
							} catch (ParseException e) {
								e.printStackTrace();
							}
							commodityPojo.setData_Inicio_Accrual(date);
							break;
							

						case "Nivel":
							String nivel = getCellData(cellData);
							if(nivel!=null && nivel!="")
								commodityPojo.setNivel(Integer.parseInt(nivel));
							else
								commodityPojo.setNivel(0);	
							break;
							

						case "Exposure":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setExposure(ExcelSupport.getCellData(cellData));
							else
								commodityPojo.setExposure(null);
							break;
							

						case "Exposure $ (160%)":
							String exposure = getCellData(cellData);
							if(exposure!=null && exposure!="")
							commodityPojo.setExposure_$(Double.parseDouble(exposure));
							else
								commodityPojo.setExposure_$(0);	
							break;
							

						case "Ação de mitigação":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setAcao_de_mitigacao(ExcelSupport.getCellData(cellData).trim());
							else
								commodityPojo.setAcao_de_mitigacao(null);	
							break;
							

						case "Onwer Mitigação":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setOnwer_Mitigacao(ExcelSupport.getCellData(cellData).trim());
							else
								commodityPojo.setAcao_de_mitigacao(null);	
						
							break;
							

						case "Family2":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setFamily2(ExcelSupport.getCellData(cellData).trim());
							else
								commodityPojo.setAcao_de_mitigacao(null);	
							break;
							

						case "Comentários Pivot Table":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setComentarios_Pivot_Table(ExcelSupport.getCellData(cellData).trim());
							else
								commodityPojo.setAcao_de_mitigacao(null);	
							break;
							

						case "Comentários":
							if(ExcelSupport.getCellData(cellData)!="")
							commodityPojo.setComentarios(ExcelSupport.getCellData(cellData).trim());
							else
								commodityPojo.setAcao_de_mitigacao(null);	
							break;
							

						case "Commodity Manager":
							String commodity = ExcelSupport.getCellData(cellData);
							if(commodity!="")
							commodityPojo.setCommodity_Manager(commodity.trim());
							else
								commodityPojo.setCommodity_Manager(null);	
							break;
							

						case "Aging":
							String aging = ExcelSupport.getCellData(cellData);
							if(aging!=null && aging!="")
							commodityPojo.setAging(Double.parseDouble(aging));
							else
								commodityPojo.setAging(0);	
							break;	

						case "Due Date":
							String dueDate = ExcelSupport.getCellData(cellData);
							if(dueDate!="")
							commodityPojo.setDue_Date(dueDate.trim());
							else
								commodityPojo.setDue_Date(null);	
							break;	
	
						case "Root Cause":
							String s = ExcelSupport.getCellData(cellData);
							if(s!=null  && s!="")
							commodityPojo.setRoot_Cause(s.trim());
							else
								commodityPojo.setRoot_Cause(null);	
							break;	
						
						default:
							break;
						}
            			
					}
            		
            	}
            	commodityPojo.setFileDate(fileDate);
            	System.out.println(commodityPojo);
            	if(commodityPojo.getPartHP()!=null)
            		commodityPojosList.add(commodityPojo);
            }
		
            status =  suppliesPrinterDao.insertData(commodityPojosList);
            return status;
	}



	private String getCellData(Cell cellData) {
		String cell = null;
		if(cellData!=null){
            switch (cellData.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				cell = (int)cellData.getNumericCellValue()+"";
				if(cell.contains(",") || cell.contains("$") || cell.contains("-") ){
					cell = cell.replaceAll("$,-", "");
					cell = cell.replace(",","");
				}
				break;

				
			case Cell.CELL_TYPE_STRING:	
				
				cell = cellData.getStringCellValue();
				if(cell.contains(",") || cell.contains("$") || cell.contains("-") ){
					cell = cell.replaceAll("$,-", "");
					cell = cell.replace(",","");
				}
				
			default:
				break;
			}			
		}else{
			cell ="";
		}
		return cell;
	}



	public String downloadFileFromFTP(){
		String downloadfileDate="";
		String path="";
		FileOutputStream fos = null;
		Date maxDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		sdf.setLenient(false);
		maxDate = printersDao.getMaxDateofEOC();
		String s = sdf.format(maxDate);
		log.info(s);
		try {
			maxDate = sdf.parse(s);
			log.info(maxDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		FTPClient client = new FTPClient();
		
		for (int i = 0; i < 10; i++) {
		
			if(getftpConnection(client)){
			log.info("connected");
				try {
					for (FTPFile mainDir : client.listDirectories()) {
						
						if (mainDir.isDirectory() && mainDir.getName().equals("Techouts")){
							log.info(mainDir.getName());	
							client.changeWorkingDirectory(mainDir.getName());
							for (FTPFile file : client.listFiles())
							{
								if(file.isFile() && file.getName().contains("EO Commodity")){
									
									Date date = null;
									try {
										String[] fileDate= file.getName().substring(30,38).split("-");
										String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(0,2);
										System.out.println();
										downloadfileDate = fileDate[0]+"-"+fileDate[1]+"-"+year+fileDate[2];
										date = sdf.parse(downloadfileDate);
									} catch (ParseException e) {
										e.printStackTrace();
									}
									System.out.println(date+":::"+maxDate);
									if(date.after(maxDate) || date.getTime()==maxDate.getTime()){
									    path = createLocation();
										fos = new FileOutputStream(path+File.separator+file.getName());
	    							    client.retrieveFile(file.getName(),fos);
										System.out.println("downloaded");	
									break;
									}
								}
							}
						}
					}
					client.logout();
					break;
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}
			}
		}
	return path+"_"+downloadfileDate;
	}
	

	
	public boolean getftpConnection(FTPClient ftpClient){
		
		boolean connectionStatus = false;
		try {
			log.info("Ftp connecting ........");
			final int port = 21;
			ftpClient.connect("ftp.hp.scm-system.com.br", port);
			connectionStatus = ftpClient.login("rfidhp\\ftpBinoy","1XD3ec2@73");
			/*ftpClient.connect("ftp.hp.scm-system.com.br", port);
			connectionStatus = ftpClient.login("fhpcompu","h5w9hB(P");
			*/
			ftpClient.enterLocalPassiveMode();
			ftpClient.setBufferSize(1024 * 1024);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (IOException exception)
		{
			log.error(exception.getMessage());
		}
		return connectionStatus;
	}
	
	public String createLocation(){
		String outputLocation = "";
		String operSys = System.getProperty("os.name").toLowerCase();
		if (operSys.contains("win")) {
			outputLocation = "D:"
					+ File.separator + "avlist";
					
		} else if (operSys.contains("nix") || operSys.contains("nux")
				|| operSys.contains("aix")) {
			outputLocation = "/home/tech/EoC";
		}

		File file =  new File(outputLocation);
		if(!file.exists()){
			log.info("creating Directory");
		boolean created =	file.mkdirs();
		log.info("Directory Created "+created);
		}else{
			log.info("Directory Exist");
		}
		return outputLocation;
	}
}
