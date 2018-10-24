package com.techouts.hp.service.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Row;
import com.aspose.cells.RowCollection;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.google.common.base.CharMatcher;
import com.techouts.hp.dao.DataDao;
import com.techouts.hp.dto.IntendedBom;
import com.techouts.hp.pojo.FileUploadInfo;
import com.techouts.hp.pojo.FtpSupport;
import com.techouts.hp.service.DataService;
import com.techouts.hp.service.FtpService;
import com.techouts.hp.util.ExcelUtil;
import com.techouts.hp.util.OSUtil;

public class IntendedBomServiceImpl implements DataService {
	private static final  Logger LOGGER = Logger.getLogger(IntendedBomServiceImpl.class);
	@Autowired
	private OSUtil OSUtil;
	@Resource(name ="flectronicsFtpServiceImpl")
	private FtpService flectronicsFtpService;
	@Autowired
	private ExcelUtil excelService;
	@Resource(name ="bomDaoImpl")
	private DataDao dataDao;
	@Resource(name = "myProps")
	private Properties properties;
	private static final String HEADER[]={"Flat BOM #","Part","Site","Level","Flat Comp",
			 "Description","Supply Type","BOM Type","Path?","Quantity Per","Quantity Per1","Option Ratio",
			 "UoM","This Level","Cumulative",
			 "Unit Cost","Code","Total Demand","On-hand","Receipts","Orders","Change","In","Out","Planner","Buyer","Supplier","Group",
			 "Site1","Sequence","Target"}; 
	private static  final String FLAT_BOM="Flat BOM #";
	private static  final String PART="Part";
	private static  final String SITE="Site";
	private static  final String LEVEL="Level";
	private static  final String FLAT_COMP="Flat Comp";
	private static  final String DESCRIPTION="Description";
	private static  final String SUPPLY_TYPE="Supply Type";
	private static  final String BOM_TYPE="BOM Type";
	private static  final String PATH="Path?";
	private static  final String QUANTITY_PER="Quantity Per";
	private static  final String QUANTITY_PER_1="Quantity Per1";
	private static  final String OPTION_RATIO="Option Ratio";
	private static  final String UOM="UoM";
	private static  final String THIS_LEVEL="This Level";
	private static  final String CUMULATIVE="Cumulative";
	private static  final String UNIT_COST="Unit Cost";
	private static  final String CODE="Code";
	private static  final String TOTAL_DEMAND="Total Demand";
	private static  final String ON_HAND="On-hand";
	private static  final String RECEIPTS="Receipts";
	private static  final String ORDERS="Orders";
	private static  final String CHANGE="Change";
	private static  final String IN="In";
	private static  final String OUT="Out";
	private static  final String PLANNER="Planner";
	private static  final String BUYER="Buyer";
	private static  final String SUPPLIER="Supplier";
	private static  final String GROUP="Group";
	private static  final String SITE1="Site1";
	private static  final String SEQUENCE="Sequence";
	private static  final String TARGET="Target";
	private static  final List<String> INTENDEBOMHEADELIST=Arrays.asList(HEADER);
	private static final SimpleDateFormat RDF = new SimpleDateFormat("MM-dd-yyyy");

	public void processIntendebBomFile() {
		FtpSupport ftpSupport = new FtpSupport();
		ftpSupport.setStaticFileDate(properties
				.getProperty("bom.static.file.date"));
		ftpSupport.setCollectionName(properties
				.getProperty("intendedbom.collection"));
		ftpSupport.setDownLoadDirectory(OSUtil.downloadDirectory());
		ftpSupport.setFieldName("File Date");
		ftpSupport.setSubFolder(properties
				.getProperty("sub.folder.intended.bom"));
		ftpSupport.setFileType(".csv");
		ftpSupport.setFtpServer(properties.getProperty("flectronics.ftp.server"));
		ftpSupport.setFtpUsername(properties.getProperty("flextronics.ftp.userId"));
		ftpSupport.setFtpPassword(properties.getProperty("flectronice.ftp.password"));
		try {
			int count=flectronicsFtpService.downloadFilesFromFtp(ftpSupport, 0);
			LOGGER.info("["+count+"] New files processed");
		} catch (ParseException e) {
			LOGGER.error(e.getMessage());
		}
		LOGGER.info("Intented Bom Thread Completed");
	}
	public FileUploadInfo readDataFromFile(File absolutePath, FTPFile file) throws Exception 
	{
		   FileUploadInfo fileUploadInfo=new FileUploadInfo();
		   Workbook workbook = new Workbook(absolutePath +File.separator+ file.getName());
		   boolean uploadStatus = false;
		   int recorsCount=0;
		   boolean collectionExists = true;
		   for(int sheetCount=0;sheetCount<workbook.getWorksheets().getCount();sheetCount++)
		   {
		   Worksheet worksheet = workbook.getWorksheets().get(sheetCount);
		  /* if(worksheet.getName().equalsIgnoreCase("Sheet1"))
		   {*/
		   boolean headerFound=false;
		   Cells cells = worksheet.getCells();
		   RowCollection rows = cells.getRows();
		   LOGGER.info("Sheet Name  ["+worksheet.getName()+" ]");
		   LOGGER.info("Number of rows in a sheet [["+rows.getCount()+"]]");
		   Map<Integer,String> intendedBomHeaders=new LinkedHashMap<Integer,String>();
		   List<IntendedBom> intendedBomList=new ArrayList<IntendedBom>();
			int srNoCount=1;
			for (int i = 0; i < rows.getCount(); i++)
				{
				Row row=rows.get(i);
				boolean insertRow=false;
				if(row.isBlank())
				{
					LOGGER.info("empty row  not inserted");
				}
				else if(!headerFound)
				{
					for(int cellIndex=0;cellIndex<=row.getLastCell().getColumn();cellIndex++)
					{
						Cell cell=row.get(cellIndex);
						String header=excelService.getAsposeWorkbookCellData(cell);
						if(!StringUtils.isEmpty(header))
						{
							if(INTENDEBOMHEADELIST.contains(header))
							{
							if(!intendedBomHeaders.containsValue(header))
							{
								intendedBomHeaders.put(cell.getColumn(), header);
							}
							else 
							{
							for(int j=1;j<=row.getLastCell().getColumn();j++)
							{
									String modifiedHeader=header+j;
									if(!intendedBomHeaders.containsValue(modifiedHeader))
									{
										intendedBomHeaders.put(cell.getColumn(), modifiedHeader);
										break;
									}
									else 
									{
										continue;
									}
							}
							}
							}
						}
					}
					if(intendedBomHeaders.size()>INTENDEBOMHEADELIST.size()/2)
					{
						LOGGER.info("header found");
						LOGGER.info(intendedBomHeaders);
						headerFound=true;
					}
				}
				else 
				{
						if(intendedBomHeaders!=null && intendedBomHeaders.size()>INTENDEBOMHEADELIST.size()/2)
						{
							IntendedBom intendedBom=new IntendedBom();
							intendedBom.setDate(RDF.format(file.getTimestamp().getTime()));
							intendedBom.setSrNo(srNoCount);
							for(Entry<Integer, String> header:intendedBomHeaders.entrySet())
							{
								String cellValue =excelService.getAsposeWorkbookCellData(row.get(header.getKey()));
								switch (header.getValue())
								{
								case FLAT_BOM:
									intendedBom.setFlatBom(cellValue);
									break;
								case PART:
									if(StringUtils.isEmpty(cellValue))
									{
										insertRow=false;
										LOGGER.info("part not found row not inserted ");
										break;
									}
									else
									{
									intendedBom.setPart(cellValue);
									insertRow=true;
									}
									break;
								case SITE:
									if(!StringUtils.isEmpty(cellValue))
									{
									cellValue=removeSpecialCharacters(cellValue);
									if(cellValue.matches("^[0-9.]*$*"))
									{
									double site=Double.parseDouble(cellValue);
									intendedBom.setSite(site);
									}
									}
									break;
								case LEVEL:
									if(!StringUtils.isEmpty(cellValue))
									{
									cellValue=removeSpecialCharacters(cellValue);
									if(cellValue.matches("^[0-9.]*$*"))
									{
									double level=Double.parseDouble(cellValue);
									intendedBom.setLevel(level);
									}
									}
									break;
								case FLAT_COMP:
									intendedBom.setFlatComp(cellValue);
									break;
								case DESCRIPTION:
									intendedBom.setDescription(cellValue);
									break;
								case SUPPLY_TYPE:
									intendedBom.setSupply_Type(cellValue);
									break;
								case BOM_TYPE:
									intendedBom.setBom_Type(cellValue);
									break;
								case PATH:
									intendedBom.setPath(cellValue);
									break;
								case QUANTITY_PER:
									if(!StringUtils.isEmpty(cellValue))
									{
										cellValue=removeSpecialCharacters(cellValue);
										if(cellValue.matches("^[0-9.]*$*"))
										{
										double quntityPer=Double.parseDouble(cellValue);	
										intendedBom.setQuantity_Per(quntityPer);
										}
									}
									break;
								case QUANTITY_PER_1:
									intendedBom.setQuantityPer1(cellValue);
									break;
								case OPTION_RATIO:
									if(!StringUtils.isEmpty(cellValue))
									{
											cellValue=removeSpecialCharacters(cellValue);
											if(cellValue.matches("^[0-9.]*$*"))
											{
										    double option_ratio=Double.parseDouble(cellValue);
										     intendedBom.setOption_Ratio(option_ratio);
											}
									}
									break;
								case UOM:
									intendedBom.setUom(cellValue);
									break;
								case THIS_LEVEL:
									if(!StringUtils.isEmpty(cellValue))
									{
											cellValue=removeSpecialCharacters(cellValue);
											if(cellValue.matches("^[0-9.]*$*"))
											{
										    double thisLevel=Double.parseDouble(cellValue);
										     intendedBom.setThis_Level(thisLevel);
											}
									}
									
									break;
								case CUMULATIVE:
									intendedBom.setCumulative(cellValue);
									break;
								case UNIT_COST:
									if(!StringUtils.isEmpty(cellValue))
									{
											cellValue=removeSpecialCharacters(cellValue);
										    double unitCost=Double.parseDouble(cellValue);
										    intendedBom.setUnit_Cost(unitCost);
									}
									break;
								case CODE:
									intendedBom.setCode(cellValue);
									break;
								case TOTAL_DEMAND:
									if(!StringUtils.isEmpty(cellValue))
									{
											cellValue=removeSpecialCharacters(cellValue);
											if(cellValue.matches("^[0-9.]*$*"))
											{
										double totalDemand=Double.parseDouble(cellValue);
										intendedBom.setTotal_Demand(totalDemand);
											}
									}
									break;
								case ON_HAND:
									if(!StringUtils.isEmpty(cellValue))
									{
											cellValue=removeSpecialCharacters(cellValue);
											if(cellValue.matches("^[0-9.]*$*"))
											{
										double onHand=Double.parseDouble(cellValue);
										intendedBom.setOn_Hand(onHand);
											}
									}
									
									break;
								case RECEIPTS:
									if(!StringUtils.isEmpty(cellValue))
									{
											cellValue=removeSpecialCharacters(cellValue);
											if(cellValue.matches("^[0-9.]*$*"))
											{
										    double receipts=Double.parseDouble(cellValue);
										    intendedBom.setReceipts(receipts);
											}
									}
									break;
								case ORDERS:
									intendedBom.setOrders(cellValue);
									break;
								case CHANGE:
									intendedBom.setChange(cellValue);
									break;
								case IN:
									intendedBom.setIn(cellValue);
									break;
								case OUT:
									intendedBom.setOut(cellValue);
									break;
								case PLANNER:
									intendedBom.setPlanner(cellValue);
									break;
								case BUYER:
									intendedBom.setBuyer(cellValue);
									break;
								case SUPPLIER:
									intendedBom.setSupplier(cellValue);
									break;
								case GROUP:
									intendedBom.setGroup(cellValue);
									break;
								case SITE1:
									intendedBom.setSite1(cellValue);
									break;
								case SEQUENCE:
									if(!StringUtils.isEmpty(cellValue))
									{
											cellValue=removeSpecialCharacters(cellValue);
											if(cellValue.matches("^[0-9.]*$*"))
											{
										double sequence=Double.parseDouble(cellValue);
										intendedBom.setSequence(sequence);
											}
									}
									break;
								case TARGET:
									if(!StringUtils.isEmpty(cellValue))
									{
											cellValue=removeSpecialCharacters(cellValue);
											if(cellValue.matches("^[0-9.]*$*"))
											{
										    double target=Double.parseDouble(cellValue);
										    intendedBom.setTarget(target);
											}
									}
									break;
								}
							
							}
							if(insertRow)
							{
								++srNoCount;
								intendedBomList.add(intendedBom);
							}
						}
		             }
				if(intendedBomList.size()==100000)
				{
				if(dataDao.insertBomData(intendedBomList,properties.getProperty("intendedbom.collection"),collectionExists))
				{
					recorsCount=recorsCount+intendedBomList.size();
				LOGGER.info(recorsCount+" records inserted sucessusfully into MASTER_BOM_FLEX");
				}
				collectionExists=false;
				intendedBomList.clear();
				}
				}
			if(intendedBomList.size()>0)
			{
			uploadStatus=dataDao.insertBomData(intendedBomList,properties.getProperty("intendedbom.collection"),collectionExists);
			collectionExists=false;
			if(uploadStatus)
			{
			recorsCount=recorsCount+intendedBomList.size();
			LOGGER.info(recorsCount+" records inserted sucessusfully into "+properties.getProperty("intendedbom.collection"));
			}
			intendedBomList.clear();
			}
		}
		   fileUploadInfo.setRecordsCount(recorsCount);
		   fileUploadInfo.setUploadStatus(uploadStatus);
		return fileUploadInfo;
	}
	
	public String removeSpecialCharacters(String cellValue)
	{
		return CharMatcher.WHITESPACE.trimFrom(cellValue.replaceAll("[',',' ']", ""));
	}
}
