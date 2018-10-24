package com.techouts.hp.service.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.techouts.hp.dao.DataDao;
import com.techouts.hp.dto.DOI;
import com.techouts.hp.dto.DOICollection;
import com.techouts.hp.pojo.FileUploadInfo;
import com.techouts.hp.service.DataService;


public class DOIServiceImpl implements DataService
{
	private static final Logger LOGGER = Logger.getLogger(DOIServiceImpl.class);
	@Resource(name = "myProps")
	private Properties properties;
	@Resource(name="dataDaoImpl")
	private DataDao dataDaoImpl;
	private static final String STATIC_DOI_HEADER[] = { "ITEM", "DESCRIPTION", "MAKE_BUY",
		"NETTABLE INVENTORY", "MRB/ RTV", "BONE PILE", "TOTAL STK",
		"TRANSIT", "OO", "TOTAL DEMAND TO ORDER", "SKU", "BUSINESS UNIT",
		"NET OH" };
	private static List<String> DOI_HEADER_LIST=Arrays.asList(STATIC_DOI_HEADER);
	private static final String ITEM="ITEM";
	private static final String DESCRIPTION="DESCRIPTION";
	private static final String MAKE_BUY="MAKE_BUY";
	private static final String NETTABLE_INVENTORY="NETTABLE INVENTORY";
	private static final String MRB_RTV="MRB/ RTV";
	private static final String BONE_PILE="BONE PILE";
	private static final String TOTAL_STK="TOTAL STK";
	private static final String TRANSIT="TRANSIT";
	private static final String OO="OO";
	private static final String TOTAL_DEMAND_TO_ORDER="TOTAL DEMAND TO ORDER";
	private static final String SKU="SKU";
	private static final String BUSINESS_UNIT="BUSINESS UNIT";
	private static final String NET_OH="NET OH";
	
	public FileUploadInfo readDataFromFile(File inputLocation, FTPFile file) {
		CSVParser csvFileParser = null;
		String fileDate = null;
		List<DOI> doiList = null;
		FileUploadInfo fileUploadInfo=new FileUploadInfo();
		// Create the CSVFormat object with the header mapping
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.withDelimiter(';').withAllowMissingColumnNames(true)
				.withIgnoreEmptyLines();
		fileDate = getFileDate(file);
		try {
			csvFileParser = new CSVParser(new FileReader(inputLocation
					+ File.separator + file.getName()), csvFileFormat);
			if (!findHeaders(csvFileParser.getHeaderMap())) 
			{
				csvFileFormat = null;
				csvFileFormat = CSVFormat.DEFAULT.withHeader(STATIC_DOI_HEADER)
						.withDelimiter(';').withAllowMissingColumnNames(true)
						.withIgnoreEmptyLines();
				csvFileParser = new CSVParser(new FileReader(inputLocation
						+ File.separator + file.getName()), csvFileFormat);
			}
		} catch (IllegalArgumentException illegalArgumentException) 
		{
			csvFileFormat = null;
			csvFileFormat = CSVFormat.DEFAULT.withHeader(STATIC_DOI_HEADER)
					.withDelimiter(';').withAllowMissingColumnNames(true)
					.withIgnoreEmptyLines();
			try {
				csvFileParser = new CSVParser(new FileReader(inputLocation
						+ File.separator + file.getName()), csvFileFormat);
			} catch (Exception exception) {
				LOGGER.error(exception.getMessage());
			}
		} catch (Exception exception) {
			LOGGER.info(exception.getMessage());
		}

		try {
			List<CSVRecord> csvRecordsList = csvFileParser.getRecords();
			if (csvRecordsList != null && csvRecordsList.size() > 0) {
				doiList = new ArrayList<DOI>();
				for (int i = 0; i < csvRecordsList.size(); i++) 
				{
					DOI doi = new DOI();
					CSVRecord csvRecord = (CSVRecord) csvRecordsList.get(i);
					if(csvRecord.isMapped(ITEM) && !DOI_HEADER_LIST.contains(csvRecord.get(ITEM)))
					{
					doi.setDate(fileDate);
					if (csvRecord.isMapped(ITEM)) 
					{
						if (csvRecord.isSet(ITEM)) 
						{
							doi.setItem(csvRecord.get(ITEM).trim());
						}
					}
					if (csvRecord.isMapped(DESCRIPTION)) {
						if (csvRecord.isSet(DESCRIPTION)) {
							doi.setDescription(csvRecord.get(DESCRIPTION).trim());
						}
					}
					if (csvRecord.isMapped(MAKE_BUY)) {
						if (csvRecord.isSet(MAKE_BUY)) {
							doi.setMakebuy(csvRecord.get(MAKE_BUY).trim());
						}
					}
					if (csvRecord.isMapped(NETTABLE_INVENTORY)) {
						if (csvRecord.isSet(NETTABLE_INVENTORY)) {
							String nettableInventory = csvRecord
									.get(NETTABLE_INVENTORY);
							int nettable_Inventory = 0;
							if (!StringUtils.isEmpty(nettableInventory)) {
								nettableInventory = replaceAllSpecialCharacters(nettableInventory);
								if (nettableInventory
												.matches("^[0-9]*$*")) {
									nettable_Inventory = Integer
											.valueOf(nettableInventory);
								}
							}
							doi.setNetQty(nettable_Inventory);
						}
					}
					if (csvRecord.isMapped(MRB_RTV)) {
						if (csvRecord.isSet(MRB_RTV)) {
							String nonNettableInventory = csvRecord
									.get(MRB_RTV);
							int nonNettable_Inventory = 0;
							if (!StringUtils.isEmpty(nonNettableInventory)) {
								nonNettableInventory = replaceAllSpecialCharacters(nonNettableInventory);
								if (nonNettableInventory
												.matches("^[0-9]*$*")) {
									nonNettable_Inventory = Integer
											.valueOf(nonNettableInventory);
								}
							}
							doi.setNON_NETTABLE_INVENTORY(nonNettable_Inventory);
						}
					}
					if (csvRecord.isMapped(BONE_PILE)) {
						if (csvRecord.isSet(BONE_PILE)) {
							String bone_Pile = csvRecord.get(BONE_PILE);
							int bonePile = 0;
							if (!StringUtils.isEmpty(bone_Pile)) {
								bone_Pile = replaceAllSpecialCharacters(bone_Pile);
								if (bone_Pile.matches("^[0-9]*$*")) {
									bonePile = Integer.parseInt(bone_Pile);
								}
							}

							doi.setBONE_PILE(bonePile);
						}
					}
					if (csvRecord.isMapped(TOTAL_STK)) {
						if (csvRecord.isSet(TOTAL_STK)) {
							String totalStk = csvRecord.get(TOTAL_STK);
							int total_Stk = 0;
							if (!StringUtils.isEmpty(totalStk)) {
								totalStk = replaceAllSpecialCharacters(totalStk);
								if (totalStk.matches("^[0-9]*$*")) {
									total_Stk = Integer.valueOf(totalStk);
								}
							}
							doi.setTOTAL_STK(total_Stk);
						}
					}
					if (csvRecord.isMapped(TRANSIT)) {
						if (csvRecord.isSet(TRANSIT)) {
							String tran_sit = csvRecord.get(TRANSIT);
							int transit = 0;
							if (!StringUtils.isEmpty(tran_sit)) {
								tran_sit = replaceAllSpecialCharacters(tran_sit);
								if (tran_sit.matches("^[0-9]*$*")) {
									transit = Integer.valueOf(tran_sit);
								}
							}
							doi.setTransit(transit);
						}
					}
					if (csvRecord.isMapped(OO)) {
						if (csvRecord.isSet(OO)) {
							doi.setOO(csvRecord.get(OO).trim());
						}
					}
					if (csvRecord.isMapped(TOTAL_DEMAND_TO_ORDER)) {
						if (csvRecord.isSet(TOTAL_DEMAND_TO_ORDER)) {
							String totalDemandToOrder = csvRecord
									.get(TOTAL_DEMAND_TO_ORDER);
							int total_Demand_To_Order = 0;
							if (!StringUtils.isEmpty(totalDemandToOrder)) {
								totalDemandToOrder = replaceAllSpecialCharacters(totalDemandToOrder);
								if (totalDemandToOrder
												.matches("^[0-9]*$*")) {
									total_Demand_To_Order = Integer
											.parseInt(totalDemandToOrder);
								}
							}
							doi.setDemandQty(total_Demand_To_Order);
						}
					}
					if (csvRecord.isMapped(SKU)) {
						if (csvRecord.isSet(SKU)) {
							doi.setSku(csvRecord.get(SKU).trim());
						}
					}
					if (csvRecord.isMapped(BUSINESS_UNIT)) {
						if (csvRecord.isSet(BUSINESS_UNIT)) {
							doi.setBaseunit(csvRecord.get(BUSINESS_UNIT).trim());
						}
					}
					if (csvRecord.isMapped(NET_OH)) {
						if (csvRecord.isSet(NET_OH)) {
							doi.setNET_OH(csvRecord.get(NET_OH).trim());
						}
					}
					doiList.add(doi);
					}
				}

				if (doiList != null && doiList.size() > 0) 
				{
					if(dataDaoImpl.insertFileData(doiList))
					{
					fileUploadInfo.setUploadStatus(true);
					fileUploadInfo.setRecordsCount(doiList.size());
					}
					generateDOI_1_1(doiList,fileDate);
			}
			}
		} 
		catch (IllegalArgumentException illegalArgumentException) 
		{
			LOGGER.info(illegalArgumentException.getMessage());
		}
		catch (NullPointerException nullPointerException) {
			nullPointerException.printStackTrace();
			LOGGER.info(nullPointerException.getMessage());
		} catch (Exception exception) {
			exception.printStackTrace();
			LOGGER.error(exception.getMessage());
		} finally {
			try {

				if (csvFileParser != null) {
					csvFileParser.close();
				}

			} catch (IOException e) {
				LOGGER.info(e.getMessage());
			}
		}
		return fileUploadInfo;
	}
public String replaceAllSpecialCharacters(String value)
{
	return value.replaceAll("[',',' ','.']", "").trim();
}
private boolean findHeaders(Map<String, Integer> headerMap) {
		for (Entry<String, Integer> header : headerMap.entrySet()) 
		{
			if(!StringUtils.isEmpty(header.getKey()) && header.getKey().equals(ITEM))
			{
				return true;
			}
		}
		return false;
	}

	public String getFileDate(FTPFile ftpFile) {
		String fileDate = ftpFile.getName().substring(3, 11);
		return fileDate.substring(4, 6) + "-" + fileDate.substring(6, 8) + "-"
				+ fileDate.substring(0, 4);
	}

	
	@SuppressWarnings("unused")
	 public boolean generateDOI_1_1(List<DOI> doilist,String fileDate)
	 {
		List<DOICollection> finallist = new ArrayList<DOICollection>();
	    for (DOI doi : doilist) 
	    {
	    DOICollection doicollection = new DOICollection();
	    doicollection.setBusinessUnit(doi.getBaseunit());
	    doicollection.setMakeBuy(doi.getMakebuy());
	    doicollection.setSku(doi.getSku());
	    doicollection.setDescription(doi.getDescription());
	    doicollection.setDate(doi.getDate());
	    String partId = doi.getItem();
	    if (partId.contains("HPM1-")) {
	     String trimedpartId = partId.substring(5);
	     doicollection.setPartId(trimedpartId);
	    } else {

	     doicollection.setPartId(partId);
	    }
	    int totalQty = (doi.getNetQty() - doi.getDemandQty());

	    if (totalQty < 0) {
	     doicollection.setQuantity(0);
	    } else {
	     doicollection.setQuantity(totalQty);
	    }
	    if (doi.getDemandQty() > 0) 
	    {
	     LOGGER.debug("finalqty :" + totalQty + ": netqty : "
	       + doi.getNetQty() + ":  demandqty :"
	       + doi.getDemandQty() + ": partID  :" + partId);
	    }
	    finallist.add(doicollection);
	   }
	   LOGGER.debug("finallist size:" + finallist.size());
	   dataDaoImpl.generateLatestDataCollection(finallist, "DOI_1.1", fileDate);
	   return false;
	   }
}
