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
import com.techouts.hp.dto.BomAvReportPeriod;
import com.techouts.hp.pojo.FileUploadInfo;
import com.techouts.hp.pojo.FtpSupport;
import com.techouts.hp.service.DataService;
import com.techouts.hp.service.FtpService;
import com.techouts.hp.util.ExcelUtil;
import com.techouts.hp.util.OSUtil;


public class BomAvReportPeriodServiceImpl implements DataService{
	private final static Logger LOGGER = Logger.getLogger(BomAvReportPeriodServiceImpl.class);
	@Autowired
	private OSUtil OSUtil;
	@Resource(name="techoutsFtpServiceImpl")
	private FtpService techoutsFtpService;
	@Autowired 
	private ExcelUtil excelService;
	@Resource(name="bomDaoImpl")
	private DataDao dataDao;
	@Resource(name = "myProps")
	private Properties properties;
	private static final SimpleDateFormat RDF = new SimpleDateFormat("MM-dd-yyyy");
	private static final String avReportPeriodHeaderArray[]={"AV","cm","Level_Code","comp","description","Commodity",
			 "Qty_Per","Ext_Qty_Per","strPosition","strPnType","MPC","eff_date","end_date"};
	private static final List<String> avReportPeriodHeaderList=Arrays.asList(avReportPeriodHeaderArray);
    private static final String AV="AV";
    private static final String CM="cm";
    private static final String LEVEL_CODE="Level_Code";
    private static final String COMP="comp";
    private static final String DESCRIPTION="description";
    private static final String COMMODITY="Commodity";
    private static final String QTY_PER="Qty_Per";
    private static final String EXT_QTY_PER="Ext_Qty_Per";
    private static final String STR_POSITION="strPosition";
    private static final String STR_PN_TYPE="strPnType";
    private static final String MPC="MPC";
    private static final String EFF_DATE="eff_date";
    private static final String END_DATE="end_date";

	public  void getLoadBomAvReportPeriodFiles() throws ParseException
	{
		FtpSupport ftpSupport = new FtpSupport();
		ftpSupport.setSubFolder(properties.getProperty("techouts.ftp.bom.sub.folder"));
		ftpSupport.setDownLoadDirectory(OSUtil.downloadDirectory());
		ftpSupport.setFileName("BOMAVReportPeriod");
		ftpSupport.setCollectionName(properties
				.getProperty("bomavreportperiod.collection"));
		ftpSupport.setFileType(".csv");
		ftpSupport.setFtpServer(properties.getProperty("techouts.ftp.server"));
		ftpSupport.setFtpUsername(properties.getProperty("techouts.ftp.username"));
		ftpSupport.setFtpPassword(properties.getProperty("techouts.ftp.password"));
		int count = techoutsFtpService.downloadFilesFromFtp(ftpSupport, 0);
		LOGGER.info("[["+count+"]] New file processed");
		LOGGER.info("BomAvreport period thread compleated");
	}
	public FileUploadInfo readDataFromFile(File absolutepath,FTPFile file) throws Exception
	{
		 FileUploadInfo fileUploadInfo=new FileUploadInfo();
	
		   Workbook workbook = new Workbook(absolutepath +File.separator+ file.getName());
		   int srNo=1;
		   boolean collectionExists = true;
		   int totalNumberOfRecords=0;
		   boolean fileUploadStatus=false;
		   for(int sheetCount=0;sheetCount<workbook.getWorksheets().getCount();sheetCount++)
		   {
		   boolean headerFound=false;
		   List<BomAvReportPeriod> bomAvReportPeriodsList=new ArrayList<BomAvReportPeriod>();
		   Worksheet worksheet = workbook.getWorksheets().get(sheetCount);
		   Cells cells = worksheet.getCells();
		   RowCollection rows = cells.getRows();
		   LOGGER.info("Sheet is "+worksheet.getName());
		   LOGGER.info("number of rows in a sheet "+rows.getCount());
		   Map<Integer,String> bomAvReportPeriodHeaders=new LinkedHashMap<Integer,String>();
			for (int i = 0; i < rows.getCount(); i++)
				{
				Row row=rows.get(i);
				boolean inserRow = false;
				if(row.isBlank())
				{
					LOGGER.info("empty row row not inserted");
				}
				else if(!headerFound)
				{
					for(int cellIndex=0;cellIndex<=row.getLastCell().getColumn();cellIndex++)
					{
						Cell cell=row.get(cellIndex);
						String header=excelService.getAsposeWorkbookCellData(cell);
						if(header!=null)
						{
							if(avReportPeriodHeaderList.contains(header))
							{
							if(!bomAvReportPeriodHeaders.containsValue(header))
							{
								bomAvReportPeriodHeaders.put(cell.getColumn(), header);
							}
							else 
							{
							for(int j=1;j<=row.getLastCell().getColumn();j++)
							{
									String modifiedHeader=header+j;
									if(!bomAvReportPeriodHeaders.containsValue(modifiedHeader))
									{
										bomAvReportPeriodHeaders.put(cell.getColumn(), modifiedHeader);
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
					if(bomAvReportPeriodHeaders.size()>avReportPeriodHeaderList.size()/2)
					{
						LOGGER.info("header found");
						LOGGER.info(bomAvReportPeriodHeaders);
						headerFound=true;
					}
				}
				else 
				{
					if(bomAvReportPeriodHeaders!=null && bomAvReportPeriodHeaders.size()>avReportPeriodHeaderList.size()/2)
					{
						BomAvReportPeriod bomAvReportPeriod=new BomAvReportPeriod();
						bomAvReportPeriod.setSrNo(srNo);
						bomAvReportPeriod.setDate(RDF.format(file.getTimestamp().getTime()));
						for(Entry<Integer, String> header:bomAvReportPeriodHeaders.entrySet())
						{
							String cellData=excelService.getAsposeWorkbookCellData(row.get(header.getKey()));
							switch(header.getValue())
							{
							case AV:
								if(StringUtils.isEmpty(cellData))
								{
									inserRow=false;
									LOGGER.info("av not found row not inserted");
									break;
								}
								else
								{
								bomAvReportPeriod.setAv(cellData);
								inserRow=true;
								}
								break;
							case CM:
								bomAvReportPeriod.setCm(cellData);
								break;	
							case LEVEL_CODE:
								if(!StringUtils.isEmpty(cellData))
								{
										cellData=replaceSpecialCharacters(cellData);
										if(cellData.matches("^[0-9.]*$*"))
										{
										double levelCode=Double.parseDouble(cellData);
										bomAvReportPeriod.setLevel_Code(levelCode);
										}
								}
								
								break;
							case COMP:
								bomAvReportPeriod.setComp(cellData);
								break;
							case DESCRIPTION:
								bomAvReportPeriod.setDescription(cellData);
								break;
							case COMMODITY:
								bomAvReportPeriod.setCommodity(cellData);
								break;
							case QTY_PER:
								if(!StringUtils.isEmpty(cellData))
								{
										cellData=replaceSpecialCharacters(cellData);
										if(cellData.matches("^[0-9.]*$*"))
										{
										bomAvReportPeriod.setQty_Per(Double.parseDouble(cellData));
										}
								}
								break;
							case EXT_QTY_PER:
								if(!StringUtils.isEmpty(cellData))
								{
										cellData=replaceSpecialCharacters(cellData);
										if(cellData.matches("^[0-9.]*$*"))
										{
										bomAvReportPeriod.setExt_Qty_Per(Double.parseDouble(cellData));
										}
								}
								break;
							case STR_POSITION:
								if(!StringUtils.isEmpty(cellData))
								{
										if(cellData.equals("00Y") || cellData.equals("#N/A") || cellData.equals("CMP"))
										{
											inserRow=false;
										LOGGER.info(cellData+" data row not inserted to db");
										break;
										}
										else
										{
											cellData=replaceSpecialCharacters(cellData);
											if(cellData.matches("^[0-9.]*$*"))
											{
											bomAvReportPeriod.setStrPosition(Double.parseDouble(cellData));
											}
										}
								}
								break;
							case STR_PN_TYPE:
								bomAvReportPeriod.setStrPnType(cellData);
							case MPC:
								bomAvReportPeriod.setMPC(cellData);
								break;
							case EFF_DATE:
								bomAvReportPeriod.setEff_date(cellData);
								break;
							case END_DATE:
								bomAvReportPeriod.setEnd_date(cellData);
								break;
							}
						}
						if(inserRow)
						{
						++srNo;
						bomAvReportPeriodsList.add(bomAvReportPeriod);
						}
					}
				}
				if(bomAvReportPeriodsList.size()==100000)
				{
					
					if(dataDao.insertBomData(bomAvReportPeriodsList,properties.getProperty("bomavreportperiod.collection"),collectionExists))
					{
						totalNumberOfRecords=totalNumberOfRecords+bomAvReportPeriodsList.size();
						LOGGER.info(totalNumberOfRecords+" records inserted sucessfully");
					}
					collectionExists=false;
					bomAvReportPeriodsList.clear();
				}
				}
			if(bomAvReportPeriodsList.size()>0)
			{
				fileUploadStatus=dataDao.insertBomData(bomAvReportPeriodsList,properties.getProperty("bomavreportperiod.collection"),collectionExists);
				if(fileUploadStatus)
				{
					totalNumberOfRecords=totalNumberOfRecords+bomAvReportPeriodsList.size();
					LOGGER.info(totalNumberOfRecords+" records inserted sucessusfully ");
				}
				collectionExists=false;
			bomAvReportPeriodsList.clear();
			}
		   }
		   fileUploadInfo.setUploadStatus(fileUploadStatus);
		   fileUploadInfo.setRecordsCount(totalNumberOfRecords);
	return fileUploadInfo;	
	}
	public String replaceSpecialCharacters(String data)
	{
		return CharMatcher.WHITESPACE.trimFrom(data.replaceAll("[',',' ']", ""));
	}
}
