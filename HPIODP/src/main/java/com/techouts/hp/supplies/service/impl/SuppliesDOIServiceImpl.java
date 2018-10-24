/**
 * 
 */
package com.techouts.hp.supplies.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.hp.supplies.dao.SuppliesPrinterDao;
import com.techouts.hp.supplies.dto.SuppliesDOI;
import com.techouts.hp.supplies.util.ExcelService;

/**
 * @author TO-OWDG-02
 *
 */
@Service
public class SuppliesDOIServiceImpl {
	
	@Autowired
	private ExcelService excelService;
	@Autowired
	private SuppliesPrinterDao suppliesPrinterDao;
	private  final String ITEM="ITEM";
	private  final String DESCRIPTION="DESCRIPTION";
	private  final String MAKE_BUY="MAKE_BUY";
	private  final String NETTABLE_INVENTORY="NETTABLE INVENTORY";
	private  final String MRB_RTV="MRB/ RTV";
	private  final String BONE_PILE="BONE PILE";
	private  final String TOTAL_STK="TOTAL STK";
	private  final String TRANSIT="TRANSIT";
	private  final String OO="OO";
	private  final String TOTAL_DEMAND_TO_ORDER="TOTAL DEMAND TO ORDER";
	private  final String SKU="SKU";
	private  final String BUSINESS_UNIT="BUSINESS UNIT";
	private  Map<String,String> headerFieldMap=new LinkedHashMap<String,String>();
	private SuppliesDOIServiceImpl()
	{
	headerFieldMap.put(ITEM, "item");
	headerFieldMap.put(DESCRIPTION, "description");
	headerFieldMap.put(MAKE_BUY, "makebuy");
	headerFieldMap.put(NETTABLE_INVENTORY, "nettableInventory");
	headerFieldMap.put(MRB_RTV, "MRB_RTV");
	headerFieldMap.put(BONE_PILE, "BONE_PILE");
	headerFieldMap.put(TOTAL_STK, "TOTAL_STK");
	headerFieldMap.put(TRANSIT, "transit");
	headerFieldMap.put(OO, "OO");
	headerFieldMap.put(TOTAL_DEMAND_TO_ORDER, "totalDemand");
	headerFieldMap.put(SKU, "sku");
	headerFieldMap.put(BUSINESS_UNIT, "baseunit");
	}
	public void readSuppliesAgingFile(File filePath,FTPFile ftpFile)
	{
		Workbook wb = null;
		try {
			Date fileDate=getFileDate(ftpFile);
			if (ftpFile.getName().endsWith("xlsx") ||ftpFile.getName().endsWith("xlsm")) {
				wb = new XSSFWorkbook(new FileInputStream(filePath + File.separator + ftpFile.getName()));
			}
			else if (ftpFile.getName().endsWith("xls")) 
			{
				wb = new HSSFWorkbook(new FileInputStream(filePath + File.separator + ftpFile.getName()));
			}
			else 
			{
				return;
			}
			 for(int i = 0; i < wb.getNumberOfSheets(); i++) 
			 {
					List<SuppliesDOI> doiSuppliesList = new ArrayList<SuppliesDOI>();
					Sheet sheet = wb.getSheetAt(i);
					boolean headerFound=false;
					Map<String,Integer> doiHeaderMap=new LinkedHashMap<String, Integer>();
					for(int rowNum=0;rowNum<=sheet.getLastRowNum();rowNum++)
					{
						Row row=sheet.getRow(rowNum);
						if(excelService.findEmptyRow(row))
						{
							
						}
						else if(!headerFound)
						{
							doiHeaderMap=excelService.findHeader(row,headerFieldMap.keySet());
							if(doiHeaderMap!=null && doiHeaderMap.containsKey(ITEM) && doiHeaderMap.size()>=headerFieldMap.keySet().size()/2)
							{
							headerFound=true;
							}
						}
						else if(doiHeaderMap!=null && doiHeaderMap.size()>0)
						{
							SuppliesDOI suppliesDOI=new SuppliesDOI();
							suppliesDOI.setCategory("SUPPLIES");
							suppliesDOI.setFileDate(fileDate);
							for(Map.Entry<String, Integer> columnAndIndex:doiHeaderMap.entrySet())
							{
								Field field=suppliesDOI.getClass().getDeclaredField(headerFieldMap.get(columnAndIndex.getKey()));
								field.setAccessible(true);
								field.set(suppliesDOI, excelService.getTypeValue(field.getType(),row.getCell(columnAndIndex.getValue())));
							}
							doiSuppliesList.add(suppliesDOI);
						}
					}
					suppliesPrinterDao.insertData(doiSuppliesList);
				
			 }
			}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
    public Date getFileDate(FTPFile ftpFile) throws ParseException 
	{
    	DateFormat dateFormat=new SimpleDateFormat("MM-dd-yyyy");
		
    	if(ftpFile.getName().startsWith("Report"))
    	{
    		DateFormat edf=new SimpleDateFormat("yyyy-dd-MM");
    		return dateFormat.parse(dateFormat.format(edf.parse(ftpFile.getName().split("-")[0])));	
    	}
    	else 
    	{
    		DateFormat edf=new SimpleDateFormat("dd mm yy");
    		return dateFormat.parse(dateFormat.format(edf.parse(ftpFile.getName().split("-")[2].split(".xl")[0])));
    	}
		
	}
}
