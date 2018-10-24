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

import com.io.pojos.OpenOrderPrinters;
import com.techouts.hp.supplies.dao.SuppliesPrinterDao;
import com.techouts.hp.supplies.util.ExcelService;

/**
 * @author raju
 *
 */
@Service
public class SuppliesOpenOrdersServiceImpl 
{
	@Autowired
	private ExcelService excelService;
	@Autowired
	private SuppliesPrinterDao suppliesPrinterDao;
	private  final String BUYER="Buyer";
	private  final String PN="PN";
	private  final String DESC="Desc.";
	private  final String PODATE="PODATE";
	private  final String PO_L="PO/L";
	private  final String ST="ST";
	private  final String DTEMB="DtEmb";
	private  final String DtConfEmb="DtConfEmb";
	private  final String DtEntr="DtEntr";
	private  final String QT="QT";
	private  final String Fornecedor="Fornecedor";
	private  final String Invoice_Manual="Invoice#(Manual)";
	private  Map<String,String> columnFieldMap=new LinkedHashMap<String,String>();
	private SuppliesOpenOrdersServiceImpl()
	{
		columnFieldMap.put(BUYER, "buyer");
		columnFieldMap.put(PN, "pn");
		columnFieldMap.put(DESC, "desc");
		columnFieldMap.put(PODATE, "po_DATE");
		columnFieldMap.put(PO_L, "PO_L");
		columnFieldMap.put(ST, "ST");
		columnFieldMap.put(DTEMB, "dt_Emb");
		columnFieldMap.put(DtConfEmb, "Ddt_Conf_Emb");
		columnFieldMap.put(DtEntr, "dt_Entr");
		columnFieldMap.put(QT, "qty");
		columnFieldMap.put(Fornecedor, "fornecedor");
		columnFieldMap.put(Invoice_Manual, "invoice_Manual");
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
				 if(wb.getSheetAt(i).getSheetName().equals("Sheet1"))
				 {
					List<OpenOrderPrinters> suppliesOpenordersList = new ArrayList<OpenOrderPrinters>();
					Sheet sheet = wb.getSheetAt(i);
					boolean headerFound=false;
					Map<String,Integer> doiHeaderMap=new LinkedHashMap<String, Integer>();
					for(int rowNum=0;rowNum<sheet.getPhysicalNumberOfRows();rowNum++)
					{
						Row row=sheet.getRow(rowNum);
						if(excelService.findEmptyRow(row))
						{
							
						}
						else if(!headerFound)
						{
							doiHeaderMap=excelService.findHeader(row,columnFieldMap.keySet());
							if(doiHeaderMap!=null && doiHeaderMap.containsKey(BUYER) && doiHeaderMap.size()>=columnFieldMap.keySet().size()/2)
							{
							headerFound=true;
							}
						}
						else if(doiHeaderMap!=null && doiHeaderMap.size()>0)
						{
							System.out.println(row.getRowNum());
							OpenOrderPrinters openOrders=new OpenOrderPrinters();
							openOrders.setCategory("SUPPLIES");
							openOrders.setFileDate(fileDate);
							for(Map.Entry<String, Integer> columnAndIndex:doiHeaderMap.entrySet())
							{
								Field field=openOrders.getClass().getDeclaredField(columnFieldMap.get(columnAndIndex.getKey()));
								field.setAccessible(true);
								field.set(openOrders, excelService.getTypeValue(field.getType(),row.getCell(columnAndIndex.getValue())));
							}
							suppliesOpenordersList.add(openOrders);
						}
					}
					suppliesPrinterDao.insertData(suppliesOpenordersList);
				
				 }
				
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
    	DateFormat edf=new SimpleDateFormat("yyyy.MM.dd");
    	return dateFormat.parse(dateFormat.format(edf.parse(ftpFile.getName().split(" ")[2].split(".xl")[0])));	
	}
}
