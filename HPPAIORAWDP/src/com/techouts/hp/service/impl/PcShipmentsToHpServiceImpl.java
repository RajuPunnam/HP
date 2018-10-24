package com.techouts.hp.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.techouts.hp.dao.DataDao;
import com.techouts.hp.dto.PcShipmentsToHpPojo;
import com.techouts.hp.pojo.FileUploadInfo;
import com.techouts.hp.service.DataService;
/**
 * PcShipmentsToHpServiceImpl class for handle PCshipmentstohp files
 * @author raju.p
 *
 */
public class PcShipmentsToHpServiceImpl implements DataService
{
	private static final  Logger LOGGER = Logger
			.getLogger(PcShipmentsToHpServiceImpl.class);
	@Resource(name="dataDaoImpl")
	private DataDao dataDaoImpl;
	private  static final SimpleDateFormat RDF = new SimpleDateFormat("MM-dd-yyyy");
	private  static final SimpleDateFormat EDF = new SimpleDateFormat("dd/MM/yyyy");
	private  static final String INVOICES_STATIC_HEADER[] = { "HPM1-", "Family", "Date", "SKU", "PL",
			"PO", "Shipped", "NF", "Pallet", "PO Qty", "Customer","WO","WO Qty","Embarque" };
	private  static final String HPM1="HPM1-";
	private  static final String FAMILY="Family";
	private  static final String DATE="Date";
	private  static final String SKU="SKU";
	private  static final String PL="PL";
	private  static final String PO="PO";
	private  static final String SHIPPED="Shipped";
	private  static final String NF="NF";
	private  static final String PALLET="Pallet";
	private  static final String PO_QTY="PO Qty";
	private  static final String CUSTOMER="Customer";
	private  static final String EMBARQUE="Embarque";
	private static final String  WO="WO";
	private static final String WO_QTY="WO Qty";
	private  static final List<String> invoicesHeaderList=Arrays.asList(INVOICES_STATIC_HEADER);

	public FileUploadInfo readDataFromFile(File inputLocation, FTPFile file) {
		CSVParser csvFileParser = null;
		List<PcShipmentsToHpPojo> shipmentsList = null;
		FileUploadInfo fileUploadInfo=new FileUploadInfo();
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.withDelimiter(';').withAllowMissingColumnNames();
		String fileDate = getShipmentFileDate(file);
		try {
			csvFileParser = new CSVParser(new FileReader(inputLocation
					+ File.separator + file.getName()), csvFileFormat);
			if (!findHeaders(csvFileParser.getHeaderMap())) {
				LOGGER.warn("Header not found,inplace of static header placed [["+INVOICES_STATIC_HEADER+"]].need to check data");
				csvFileFormat = null;
				csvFileFormat = CSVFormat.DEFAULT.withHeader(INVOICES_STATIC_HEADER)
						.withDelimiter(';').withAllowMissingColumnNames();
				csvFileParser = new CSVParser(new FileReader(inputLocation
						+ File.separator + file.getName()), csvFileFormat);
			}
		} catch (IllegalArgumentException illegalArgumentException) 
		{
			LOGGER.warn("Duplicate Header found,inplace of static header placed [["+INVOICES_STATIC_HEADER+"]].need to check data");
			csvFileFormat = null;
			try {
				csvFileFormat = CSVFormat.DEFAULT.withHeader(INVOICES_STATIC_HEADER)
						.withDelimiter(';').withAllowMissingColumnNames();
				csvFileParser = new CSVParser(new FileReader(inputLocation
						+ File.separator + file.getName()), csvFileFormat);
			} catch (Exception exception) {
				LOGGER.error(exception.getMessage());
			}
		} catch (FileNotFoundException fileNotFoundException) 
		{
			LOGGER.error(fileNotFoundException.getMessage());
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
		}
		try {
			List<CSVRecord> csvRecordsList = csvFileParser.getRecords();
			if (csvRecordsList != null && csvRecordsList.size() > 0) {
				shipmentsList = new ArrayList<PcShipmentsToHpPojo>();
				for (int i = 0; i < csvRecordsList.size(); i++) {
					CSVRecord shipmentsRecord = (CSVRecord) csvRecordsList
							.get(i);
					if (!invoicesHeaderList.contains(shipmentsRecord
							.get(HPM1))) {
						PcShipmentsToHpPojo shipmentsToHpDto = new PcShipmentsToHpPojo();
						shipmentsToHpDto.setFileDate(fileDate);
						if (shipmentsRecord.isMapped(HPM1)) {
							if (shipmentsRecord.isSet(HPM1)) {
								shipmentsToHpDto.setHpm1(shipmentsRecord
										.get(HPM1).trim());
							}
						}

						if (shipmentsRecord.isMapped(FAMILY)) {
							if (shipmentsRecord.isSet(FAMILY)) {
								shipmentsToHpDto.setFamily(shipmentsRecord
										.get(FAMILY).trim());
							}
						}
						if (shipmentsRecord.isMapped(DATE)) {
							if (shipmentsRecord.isSet(DATE)) {
								String date = shipmentsRecord
										.get(DATE);
								if (!StringUtils.isEmpty(date)) {
									date = replaceAllSpecialCharacters(date);
									if (date.matches("^[0-9/]*$*")) {
										shipmentsToHpDto
												.setDate(RDF
														.format(EDF
																.parse(date)));
									} else {
										shipmentsToHpDto.setDate(date);
									}
								}
							}
						}
						if (shipmentsRecord.isMapped(SKU)) {
							if (shipmentsRecord.isSet(SKU)) {
								shipmentsToHpDto.setSKU(shipmentsRecord
										.get(SKU).trim());
							}
						}
						if (shipmentsRecord.isMapped(PL)) {
							if (shipmentsRecord.isSet(PL)) {
								shipmentsToHpDto.setPLs(shipmentsRecord
										.get(PL).trim());
							}
						}

						if (shipmentsRecord.isMapped(PO)) {
							if (shipmentsRecord.isSet(PO)) {
								String po = shipmentsRecord
										.get(PO);
								double PO = 0;
								if (!StringUtils.isEmpty(po)) {
									po = replaceAllSpecialCharacters(po);
									if (po.matches("^[0-9]*$*")) {
										PO = Double.parseDouble(po);
									}
								}
								shipmentsToHpDto.setPo(PO);
							}
						}
						if (shipmentsRecord.isMapped(SHIPPED)) {
							if (shipmentsRecord.isSet(SHIPPED)) {
								String shipped = shipmentsRecord
										.get(SHIPPED);
								double shippedQuantity = 0;
								if (!StringUtils.isEmpty(shipped )) {
									shipped = replaceAllSpecialCharacters(shipped);
									if (shipped.matches("^[0-9]*$*")) {
										shippedQuantity = Integer
												.parseInt(shipped);
									}
								}
								shipmentsToHpDto.setShipped(shippedQuantity);
							}
						}

						if (shipmentsRecord.isMapped(NF)) {
							if (shipmentsRecord.isSet("NF")) {
								String nf = shipmentsRecord
										.get(NF);
								double nfiscal = 0;
								if (!StringUtils.isEmpty(nf)) {
									nf = replaceAllSpecialCharacters(nf);
									if (nf.matches("^[0-9]*$*")) {
										nfiscal = Double.parseDouble(nf);
									}
								}
								shipmentsToHpDto.setNFiscal(nfiscal);
							}
						}

						if (shipmentsRecord.isMapped(PALLET)) {
							if (shipmentsRecord.isSet(PALLET)) {
								String pallet = shipmentsRecord
										.get(PALLET);
								double PALLET = 0.0;
								if (!StringUtils.isEmpty(pallet)) {
									pallet = replaceAllSpecialCharacters(pallet);
									if (pallet.matches("^[0-9]*$*")) {
										PALLET = Double.parseDouble(pallet);
									}
								}
								shipmentsToHpDto.setPallet(PALLET);
							}
						}
						if (shipmentsRecord.isMapped(PO_QTY)) {
							if (shipmentsRecord.isSet(PO_QTY)) {
								String poQty = shipmentsRecord
										.get(PO_QTY);
								double quantity = 0;
								if (!StringUtils.isEmpty(poQty)) {
									poQty = replaceAllSpecialCharacters(poQty);
									if (poQty.matches("^[0-9]*$*")) {
										quantity = Double.parseDouble(poQty);
									}
								}
								shipmentsToHpDto.setQuantity(quantity);
							}
						}
						if (shipmentsRecord.isMapped(CUSTOMER)) {
							if (shipmentsRecord.isSet(CUSTOMER)) {
								shipmentsToHpDto.setCustomer(shipmentsRecord
										.get(CUSTOMER).trim());
							}
						}

						if (shipmentsRecord.isMapped(EMBARQUE)) {
							if (shipmentsRecord.isSet(EMBARQUE)) {
								shipmentsToHpDto.setEmbarque(shipmentsRecord
										.get(EMBARQUE).trim());
							}
						}
						if(shipmentsRecord.isMapped(WO) && shipmentsRecord.isSet(WO))
						{
							String wo=shipmentsRecord.get(WO);
							int workOrder=0;
							if (!StringUtils.isEmpty(wo)) {
								wo = replaceAllSpecialCharacters(wo);
								if (wo.matches("^[0-9]*$*")) {
									workOrder = Integer.parseInt(wo);
								}
							}
							shipmentsToHpDto.setWo(workOrder);
									
						}
						
						if(shipmentsRecord.isMapped(WO_QTY) && shipmentsRecord.isSet(WO_QTY))
						{
							String woQty=shipmentsRecord.get(WO_QTY);
							double workOrderQty=0;
							if (!StringUtils.isEmpty(woQty)) {
								woQty = replaceAllSpecialCharacters(woQty);
								if (woQty.matches("^[0-9]*$*")) {
									workOrderQty = Double.parseDouble(woQty);
								}
							}
							shipmentsToHpDto.setWoQty(workOrderQty);
									
						}
						shipmentsList.add(shipmentsToHpDto);
					}
				}
				if (shipmentsList != null && shipmentsList.size() > 0) 
				{
					if(dataDaoImpl.insertFileData(shipmentsList))
					{
						fileUploadInfo.setUploadStatus(true);
						fileUploadInfo.setRecordsCount(shipmentsList.size());
					}
				}
				
			}
		} catch (NullPointerException nullPointerException) {
			LOGGER.error(nullPointerException.getMessage());

		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
		} finally {
			if (csvFileParser != null) {
				try {
					csvFileParser.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
		return fileUploadInfo;
	}
	/**
	 * Removes special characters
	 * @param value
	 * @return
	 */
	public String replaceAllSpecialCharacters(String value)
	{
		return value.replaceAll("[',',' ','.']", "").trim();
	}
	/**
	 * to find correct header
	 * @param headersMap
	 * @return
	 */
	public boolean findHeaders(Map<String, Integer> headersMap) {
		for (Entry<String, Integer> header : headersMap.entrySet()) {
			if(!StringUtils.isEmpty(header.getKey()) && header.getKey().equals(HPM1))
			{
				return true;
			}
		}

		return false;
	}
/**
 * To get file date from file name
 * @param ftpFile
 * @return
 */
	public String getShipmentFileDate(FTPFile ftpFile) 
	{
		String fileDate = ftpFile.getName().substring(3, 11);
		return fileDate.substring(4, 6) + "-" + fileDate.substring(6, 8) + "-"
				+ fileDate.substring(0, 4);
	}

}
