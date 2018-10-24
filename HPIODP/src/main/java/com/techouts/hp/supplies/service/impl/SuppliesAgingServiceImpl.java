/**
 * 
 */
package com.techouts.hp.supplies.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.io.pojos.AgingPrinters;
import com.techouts.hp.supplies.dao.SuppliesPrinterDao;
import com.techouts.hp.supplies.util.ExcelService;

/**
 * @author TO-OWDG-02
 *
 */
@Service
public class SuppliesAgingServiceImpl 
{
@Autowired
private ExcelService excelService;
@Autowired
private SuppliesPrinterDao suppliesPrinterDao;
private  final String BASE_SHEET="Base";
private  final String WAREHOUSE="Warehouse";
private  final String ITEM="Item";
private  final String DESC="Description";
private  final String DATA_ULTIMA_CONTAGEM="Data Última Contagem";
private  final String ESTOQUE_FISICO="Estoque Físico";
private  final String LOCATION="Location";
private  final String ESTOQUE="Estoque";
private  final String LOTE="Lote";
private  final String FIFO="FIFO";
private  final String V1_UN_RS="Vl. Un. (R$)";
private  final String V1_UN_STD="Vl. Un. (STD)";
private  final String CODIGOABC="CódigoABC";
private  final String DTULTTRANS="DtUltTrans";
private  final String AGING="Aging";
private  final String TOTAL_USD="Total USD";
private  final String PCA="PCA";
private  final String TYPE="Type";
private  final String AGING_STATSUS="Aging Status";
private  final String SUPPLIER="Supplier";
private  final String PL="PL";
private  final String COMMODITIES="Commodities";
private  Map<String,String> headerFieldMap=new LinkedHashMap<String,String>();
private SuppliesAgingServiceImpl()
{
	headerFieldMap.put(WAREHOUSE, "warehouse");
	headerFieldMap.put(ITEM, "item");
	headerFieldMap.put(DESC, "description");
	headerFieldMap.put(DATA_ULTIMA_CONTAGEM, "data_Ultima_Contagem");
	headerFieldMap.put(ESTOQUE_FISICO, "estoque_Fisico");
	headerFieldMap.put(LOCATION, "location");
	headerFieldMap.put(ESTOQUE, "estoque");
	headerFieldMap.put(LOTE, "lote");
	headerFieldMap.put(FIFO, "fifo");
	headerFieldMap.put(V1_UN_RS, "vl_Un_R$");
	headerFieldMap.put(V1_UN_STD, "vl_Un_STD");
	headerFieldMap.put(CODIGOABC, "codigoABC");
	headerFieldMap.put(DTULTTRANS, "dtUltTrans");
	headerFieldMap.put(AGING, "aging");
	headerFieldMap.put(TOTAL_USD, "total_USD");
	headerFieldMap.put(PCA, "pCA");
	headerFieldMap.put(TYPE, "type");
	headerFieldMap.put(AGING_STATSUS, "aging_Status");
	headerFieldMap.put(SUPPLIER, "supplier");
	headerFieldMap.put(PL, "pL");
	headerFieldMap.put(COMMODITIES, "commodities");	
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
				 if(wb.getSheetAt(i).getSheetName().equalsIgnoreCase(BASE_SHEET))
				 {
					List<AgingPrinters> agingSupplieslist = new ArrayList<AgingPrinters>();
					Sheet sheet = wb.getSheetAt(i);
					boolean headerFound=false;
					Map<String,Integer> agingHeaderMap=new LinkedHashMap<String, Integer>();
					for(int rowNum=0;rowNum<=sheet.getLastRowNum();rowNum++)
					{
						Row row=sheet.getRow(rowNum);
						if(excelService.findEmptyRow(row))
						{
							
						}
						else if(!headerFound)
						{
							agingHeaderMap=excelService.findHeader(row,headerFieldMap.keySet());
							if(agingHeaderMap!=null && agingHeaderMap.containsKey(ITEM) && agingHeaderMap.size()>=headerFieldMap.keySet().size()/2)
							{
							headerFound=true;
							}
						}
						else if(agingHeaderMap!=null && agingHeaderMap.size()>0)
						{
							AgingPrinters agingDto=new AgingPrinters();
							agingDto.setCategory("SUPPLIES");
							agingDto.setFileDate(fileDate);
							for(Map.Entry<String, Integer> columnAndIndex:agingHeaderMap.entrySet())
							{
								Field field=agingDto.getClass().getDeclaredField(headerFieldMap.get(columnAndIndex.getKey()));
								field.setAccessible(true);
								field.set(agingDto, excelService.getTypeValue(field.getType(),row.getCell(columnAndIndex.getValue())));
						/*		switch(columnAndIndex.getKey())
								{
								case WAREHOUSE:
									if(!StringUtils.isEmpty(cellValue))
									{
									cellValue=excelService.replaceSpcialChar(cellValue);
									if (cellValue.matches("^[0-9]*$*"))
									{
										agingDto.setWarehouse(Double.valueOf(cellValue));
									}
									}
									break;
								case ITEM:
									agingDto.setItem(cellValue);
									break;
								case DESC:
									agingDto.setDescription(cellValue);
									break;
								case DATA_ULTIMA_CONTAGEM:
									agingDto.setData_Ultima_Contagem(cellValue);
									break;
								case ESTOQUE_FISICO:
									if(!StringUtils.isEmpty(cellValue))
									{
									cellValue=excelService.replaceSpcialChar(cellValue);
									if (cellValue.matches("^[0-9]*$*"))
									{
										agingDto.setEstoque_Fisico(Double.valueOf(cellValue));
									}
									}
									break;
								case LOCATION:
									agingDto.setLocation(cellValue);
									break;
								case ESTOQUE:
									if(!StringUtils.isEmpty(cellValue))
									{
									cellValue=excelService.replaceSpcialChar(cellValue);
									if (cellValue.matches("^[0-9]*$*"))
									{
										agingDto.setEstoque(Double.valueOf(cellValue));
									}
									}
									break;
								case LOTE:
									agingDto.setLote(cellValue);
									break;
								case FIFO:
								agingDto.setFifo(cellValue);
								break;
								case V1_UN_RS:
									if(!StringUtils.isEmpty(cellValue))
									{
									cellValue=excelService.replaceSpcialChar(cellValue);
									if (cellValue.matches("^[0-9]*$*"))
									{
										agingDto.setVl_Un_R$(Double.valueOf(cellValue));
									}
									}
									break;
								case V1_UN_STD:
									if(!StringUtils.isEmpty(cellValue))
									{
									cellValue=excelService.replaceSpcialChar(cellValue);
									if (cellValue.matches("^[0-9]*$*"))
									{
										agingDto.setVl_Un_STD(Double.valueOf(cellValue));
									}
									}
									break;
								case CODIGOABC:
									agingDto.setCodigoABC(cellValue);
									break;
								case DTULTTRANS:
									agingDto.setDtUltTrans(cellValue);
									break;
								case AGING:
									if(!StringUtils.isEmpty(cellValue))
									{
									cellValue=excelService.replaceSpcialChar(cellValue);
									if (cellValue.matches("^[0-9]*$*"))
									{
										agingDto.setAging(Double.valueOf(cellValue));
									}
									}
									break;
								case TOTAL_USD:
									if(!StringUtils.isEmpty(cellValue))
									{
									cellValue=excelService.replaceSpcialChar(cellValue);
									if (cellValue.matches("^[0-9]*$*"))
									{
										agingDto.setTotal_USD(Double.valueOf(cellValue));
									}
									}
									break;
								case TYPE:
									agingDto.setType(cellValue);
									break;
								case AGING_STATSUS:
									agingDto.setAging_Status(cellValue);
									break;
								case SUPPLIER:
									agingDto.setSupplier(cellValue);
									break;
								case PL:
									agingDto.setpL(cellValue);
									break;
								case COMMODITIES:
									agingDto.setCommodities(cellValue);
									break;
								}*/
							}
							//System.out.println(agingDto);
							agingSupplieslist.add(agingDto);
						}
					}
					suppliesPrinterDao.insertData(agingSupplieslist);
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
    	SimpleDateFormat dateFormat=new SimpleDateFormat("MM-dd-yyyy");
    	SimpleDateFormat edf=new SimpleDateFormat("dd_MM_yy");
    	return new Date(dateFormat.parse(dateFormat.format(edf.parse(ftpFile.getName().split("-")[1].split(".xl")[0]))).getTime());
	}
   
}
