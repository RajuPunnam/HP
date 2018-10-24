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
import com.techouts.hp.dto.BomFullReportPeriod;
import com.techouts.hp.pojo.FileUploadInfo;
import com.techouts.hp.pojo.FtpSupport;
import com.techouts.hp.service.DataService;
import com.techouts.hp.service.FtpService;
import com.techouts.hp.util.ExcelUtil;
import com.techouts.hp.util.OSUtil;


public class BomFullReportPeriodServiceImpl implements DataService{
	private static final  Logger LOGGER = Logger.getLogger(BomFullReportPeriodServiceImpl.class);
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
	private static final String FULL_REPORT_HEADER[]={"PL","family","sub family","product number",
			"prod description","level","part number","part description",
			"commodity","qty per","position","MPC","RoHs","PICA Code","eff date","end date","type"};
	private static final List<String> bomFullReportHeaderList=Arrays.asList(FULL_REPORT_HEADER);
	private static final String PL="PL";
	private static final String FAMILY="family";
	private static final String SUB_FAMILY="sub family";
	private static final String PRODUCT_NUMBER="product number";
	private static final String PROD_DESCRITION="prod description";
	private static final String LEVEL="level";
	private static final String PART_NUMBER="part number";
	private static final String PART_DESCRIPTION="part description";
	private static final String COMMODITY="commodity";
	private static final String QTY_PER="qty per";
	private static final String POSITION="position";
	private static final String MPC="MPC";
	private static final String ROHS="RoHs";
	private static final String PICA_CODE="PICA Code";
	private static final String EFF_DATE="eff date";
	private static final String END_DATE="end date";
	private static final String TYPE="type";
	public  void getLoadBomFullReportPeriodFiles() throws ParseException
	{
		FtpSupport ftpSupport = new FtpSupport();
		ftpSupport.setSubFolder(properties.getProperty("techouts.ftp.bom.sub.folder"));
		ftpSupport.setDownLoadDirectory(OSUtil.downloadDirectory());
		ftpSupport.setFileName("BOMFULLReportPeriod");
		ftpSupport.setCollectionName(properties
				.getProperty("bomfullreportperiod.collection"));
		ftpSupport.setFileType(".csv");
		ftpSupport.setFtpServer(properties.getProperty("techouts.ftp.server"));
		ftpSupport.setFtpUsername(properties.getProperty("techouts.ftp.username"));
		ftpSupport.setFtpPassword(properties.getProperty("techouts.ftp.password"));
		int count =techoutsFtpService.downloadFilesFromFtp(ftpSupport,0);
		LOGGER.info("["+count+"] New Files processed");
		LOGGER.info("BOMFULLReportPeriod thread completed");
	}
	public FileUploadInfo readDataFromFile(File absolutepath,FTPFile file) throws Exception
	{
		 FileUploadInfo fileUploadInfo=new FileUploadInfo();

		   Workbook workbook = new Workbook(absolutepath +File.separator+ file.getName());
		   int srNo=1;
		   boolean collDropStatus = true;
		   boolean uploadStatus=false;
		   int totalNumberOfRows=0;
		   for(int sheetCount=0;sheetCount<workbook.getWorksheets().getCount();sheetCount++)
		   {
		   boolean headerFound=false;
		   List<BomFullReportPeriod> bomFullReportPeriodList=new ArrayList<BomFullReportPeriod>();
		   Worksheet worksheet = workbook.getWorksheets().get(sheetCount);
		   Cells cells = worksheet.getCells();
		   RowCollection rows = cells.getRows();
		   LOGGER.info("sheet  is "+worksheet.getName());
		   LOGGER.info("number of rows are "+rows.getCount());
		   Map<Integer,String> bomFullReportPeriodHeaders=new LinkedHashMap<Integer,String>();
			for (int i = 0; i < rows.getCount(); i++)
				{
				Row row=rows.get(i);
				boolean insertRow = false;;
				if(row.isBlank())
				{
					LOGGER.info("empty row row not inserted");
				}
				else if(!headerFound)
				{
					for(int cellIndex=0;cellIndex<=row.getLastCell().getColumn();cellIndex++)
					{
						Cell cell=row.get(cellIndex);
						String headerValue=excelService.getAsposeWorkbookCellData(cell);
						if(headerValue!=null)
						{
							if(bomFullReportHeaderList.contains(headerValue))
							{
							if(!bomFullReportPeriodHeaders.containsValue(headerValue))
							{
								bomFullReportPeriodHeaders.put(cell.getColumn(), headerValue);
							}
							else 
							{
							for(int j=1;j<=row.getLastCell().getColumn();j++)
							{
									String modifiedHeader=headerValue+j;
									if(!bomFullReportPeriodHeaders.containsValue(modifiedHeader))
									{
										bomFullReportPeriodHeaders.put(cell.getColumn(), modifiedHeader);
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
					if(bomFullReportPeriodHeaders.size()>bomFullReportHeaderList.size()/2)
					{
						LOGGER.info("header found");
						LOGGER.info(bomFullReportPeriodHeaders);
						headerFound=true;
					}
				}
				else 
				{
					
					if(bomFullReportPeriodHeaders!=null && bomFullReportPeriodHeaders.size()>bomFullReportHeaderList.size()/2)
					{
						BomFullReportPeriod bomFullReportPeriod=new BomFullReportPeriod();
						bomFullReportPeriod.setSR_NO(srNo);
						bomFullReportPeriod.setDate(RDF.format(file.getTimestamp().getTime()));
						for(Entry<Integer, String> header:bomFullReportPeriodHeaders.entrySet())
						{
							String cellData=excelService.getAsposeWorkbookCellData(row.get(header.getKey()));
							switch(header.getValue())
							{
							case PL:
								bomFullReportPeriod.setPL(cellData);
								break;
							case FAMILY:
								bomFullReportPeriod.setFamily(cellData);
								break;
							case SUB_FAMILY:
								bomFullReportPeriod.setSub_family(cellData);
								break;
							case PRODUCT_NUMBER:
								bomFullReportPeriod.setProduct_number(cellData);
								break;
							case PROD_DESCRITION:
								bomFullReportPeriod.setProd_description(cellData);
								break;
							case LEVEL:
								bomFullReportPeriod.setLevel(cellData);
								break;
							case PART_NUMBER:
								if(StringUtils.isEmpty(cellData))
								{
									insertRow=false;
									LOGGER.info("part number not found row not inserted");
									break;
								}
								else
								{
								bomFullReportPeriod.setPart_number(cellData);
								insertRow=true;
								}
								break;
							case PART_DESCRIPTION:
								bomFullReportPeriod.setPart_description(cellData);
								break;
							case COMMODITY:
								bomFullReportPeriod.setCommodity(cellData);
								break;
							case QTY_PER:
								if(!StringUtils.isEmpty(cellData))
								{
										cellData=replaceSpecialCharacters(cellData);
										if(cellData.matches("^[0-9.]*$*"))
										{
										bomFullReportPeriod.setQty_per(Double.parseDouble(cellData));
										}
								}
								
								break;
							case POSITION:
								if(cellData.equals("00Y") || cellData.equals("#N/A") || cellData.equals("CMP") || cellData.endsWith("#EM"))
								{
									insertRow=false;
								LOGGER.info(cellData+"  row data not inserted to db");
								break;
								}
								else
								{
									if(!StringUtils.isEmpty(cellData))
									{
									cellData=replaceSpecialCharacters(cellData);
									if(cellData.matches("^[0-9.]*$*"))
									{
									bomFullReportPeriod.setPosition(Double.parseDouble(cellData));
									}
									}
								}
								break;
							case MPC:
								bomFullReportPeriod.setMPC(cellData);
								break;
							case ROHS:
								bomFullReportPeriod.setRoHs(cellData);
								break;
							case PICA_CODE:
								bomFullReportPeriod.setPICA_Code(cellData);
								break;
							case EFF_DATE:
								bomFullReportPeriod.setEff_date(cellData);
								break;
							case END_DATE:
								bomFullReportPeriod.setEnd_date(cellData);
								break;
							case TYPE:
							   bomFullReportPeriod.setType(cellData);
							}
						}
						if(insertRow)
						{
							++srNo;
							bomFullReportPeriodList.add(bomFullReportPeriod);
						}
					}
				}
				if(bomFullReportPeriodList.size()==100000)
				{
					totalNumberOfRows=totalNumberOfRows+bomFullReportPeriodList.size();
					if(dataDao.insertBomData(bomFullReportPeriodList,properties.getProperty("bomfullreportperiod.collection"),collDropStatus))
					{
						LOGGER.info(totalNumberOfRows+" inserted sucessfully");
					}
					collDropStatus=false;
					bomFullReportPeriodList.clear();
				}
				}
			if(bomFullReportPeriodList.size()>0)
			{
			totalNumberOfRows=totalNumberOfRows+bomFullReportPeriodList.size();
		    uploadStatus=dataDao.insertBomData(bomFullReportPeriodList,properties.getProperty("bomfullreportperiod.collection"),collDropStatus);
		    if(uploadStatus)
		    {
		    LOGGER.info(totalNumberOfRows+" inserted sucessusfully ");
		    }
			collDropStatus=false;
			bomFullReportPeriodList.clear();
			}
		   }
		   fileUploadInfo.setUploadStatus(uploadStatus);
		   fileUploadInfo.setRecordsCount(totalNumberOfRows);
		return fileUploadInfo;
	}
public String replaceSpecialCharacters(String data)
{
	return CharMatcher.WHITESPACE.trimFrom(data.replaceAll("[',',' ']", ""));
}
}
