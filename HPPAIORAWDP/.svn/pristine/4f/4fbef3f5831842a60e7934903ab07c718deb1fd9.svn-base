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

import javax.annotation.Resource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.techouts.hp.dao.DataDao;
import com.techouts.hp.dao.impl.PcOpenOrdersDaoImpl;
import com.techouts.hp.dto.PcOpenorders;
import com.techouts.hp.pojo.FileUploadInfo;
import com.techouts.hp.service.DataService;

/**
 * PcOpenOrdersServiceImpl class to handle PcOpenOrders files
 * @author raju.p
 *
 */
public class PcOpenOrdersServiceImpl implements DataService {
	private static final Logger LOG = Logger
			.getLogger(PcOpenOrdersServiceImpl.class);
	@Autowired
	private PcOpenOrdersDaoImpl openOrdersDao;
	@Resource(name = "dataDaoImpl")
	private DataDao dataDaoImpl;
	private static final SimpleDateFormat RDF = new SimpleDateFormat(
			"MM-dd-yyyy");
	private static final SimpleDateFormat EDF = new SimpleDateFormat("dd/MM/yyyy");

	private static final String OPEN_ORDER_HEADERS[] = { "CIA", "Buyer",
			"Item Number", "Description", "PO Date", "PO #", "Line", "PO/Line",
			"PO STATUS", "Request Ship Date", "Confirmed Ship Date",
			"ETA (Flex)", "Open Qty", "Supplier", "Invoice# BaaN", "Where used" };
	private static final List<String> HEADER_LIST = Arrays.asList(OPEN_ORDER_HEADERS);
	private static final String CIA = "CIA";
	private static final String BUYER = "Buyer";
	private static final String ITEM_NUMBER = "Item Number";
	private static final String DESCRIPTION = "Description";
	private static final String PO_DATE = "PO Date";
	private static final String PO = "PO #";
	private static final String LINE = "Line";
	private static final String PO_LINE = "PO/Line";
	private static final String PO_STATUS = "PO STATUS";
	private static final String REQUEST_SHIP_DATE = "Request Ship Date";
	private static final String CONFIRMED_SHIP_DATE = "Confirmed Ship Date";
	private static final String ETA_FLEX = "ETA (Flex)";
	private static final String OPEN_QTY = "Open Qty";
	private static final String UNIT_PRICE = "Unit Price";
	private static final String CURR = "Curr";
	private static final String TOTAL = "Total";
	private static final String SUPPLIER = "Supplier";
	private static final String INVOICE_BANN = "Invoice# BaaN";
	private static final String WHERE_USED = "Where used";

	public FileUploadInfo readDataFromFile(File inputLocation, FTPFile file) {
		CSVParser csvFileParser = null;
		List<PcOpenorders> openordersList = null;
		String fileDate = null;
		FileUploadInfo fileUploadInfo = new FileUploadInfo();
		// Create the CSVFormat object with the header mapping
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.withDelimiter(';').withAllowMissingColumnNames(true);
		fileDate = getOpenOrdersFileDate(file);
		try {
			csvFileParser = new CSVParser(new FileReader(inputLocation
					+ File.separator + file.getName()), csvFileFormat);
			if (!findHeader(csvFileParser.getHeaderMap())) {
				LOG.warn("Header not found,inplace of static header placed [["+OPEN_ORDER_HEADERS+"]].need to check data");
				csvFileFormat = null;
				csvFileFormat = CSVFormat.DEFAULT
						.withHeader(OPEN_ORDER_HEADERS).withDelimiter(';')
						.withAllowMissingColumnNames().withIgnoreEmptyLines();
				csvFileParser = new CSVParser(new FileReader(inputLocation
						+ File.separator + file.getName()), csvFileFormat);
			}
		} catch (IllegalArgumentException illegalArgumentException) {
			LOG.warn("Duplicate Headerfound,inplace of static header placed [["+OPEN_ORDER_HEADERS+"]].need to check data");
			csvFileFormat = null;
			csvFileFormat = CSVFormat.DEFAULT.withHeader(OPEN_ORDER_HEADERS)
					.withDelimiter(';').withAllowMissingColumnNames()
					.withIgnoreEmptyLines();
			try {
				csvFileParser = new CSVParser(new FileReader(inputLocation
						+ File.separator + file.getName()), csvFileFormat);
			} catch (FileNotFoundException fileNotFoundException) {
				LOG.error(fileNotFoundException.getMessage());

			} catch (IOException ioException) {
				LOG.error(ioException.getMessage());
			}
		} catch (Exception exception) {
			LOG.error(exception.getMessage());
		}
		try {

			List<CSVRecord> csvRecordsList = csvFileParser.getRecords();
			if (csvRecordsList != null && csvRecordsList.size() > 0) {
				openordersList = new ArrayList<PcOpenorders>();
				for (int i = 0; i < csvRecordsList.size(); i++) {
					PcOpenorders openOrdersDto = new PcOpenorders();
					CSVRecord csvRecord = (CSVRecord) csvRecordsList.get(i);
					if (csvRecord.isMapped(CIA)
							&& !HEADER_LIST.contains(csvRecord.get(CIA))) {
						openOrdersDto.setDate(fileDate);
						if (csvRecord.isMapped(CIA)) {
							if (csvRecord.isSet(CIA)) {
								openOrdersDto.setCIA(csvRecord.get(CIA).trim());
							}
						}
						if (csvRecord.isMapped(BUYER)) {
							if (csvRecord.isSet(BUYER)) {
								openOrdersDto.setBuyer(csvRecord.get(BUYER
										.trim()));
							}
						}
						if (csvRecord.isMapped(ITEM_NUMBER)) {
							if (csvRecord.isSet(ITEM_NUMBER)) {
								openOrdersDto.setItem(csvRecord
										.get(ITEM_NUMBER).trim());
							}
						}
						if (csvRecord.isMapped(DESCRIPTION)) {
							if (csvRecord.isSet(DESCRIPTION)) {
								openOrdersDto.setDescription(csvRecord.get(
										DESCRIPTION).trim());
							}
						}
						if (csvRecord.isMapped(PO_DATE)) {
							if (csvRecord.isSet(PO_DATE)) {
								String poDate = csvRecord.get(PO_DATE);
								if (!StringUtils.isEmpty(poDate)) {
									poDate = replaceAllSpecialCharacters(poDate);
									if (poDate.matches("^[0-9/]*$*")) {
										openOrdersDto
												.setPODate(RDF
														.format(EDF
																.parse(poDate)));
									}
								}
							}
						}
						if (csvRecord.isMapped(PO)) {
							if (csvRecord.isSet(PO)) {
								openOrdersDto.setPO(csvRecord.get(PO).trim());
							}
						}
						if (csvRecord.isMapped(LINE)) {
							if (csvRecord.isSet(LINE)) {
								String lineInString = csvRecord.get(LINE);
								int line = 0;
								if (!StringUtils.isEmpty(lineInString)) {
									lineInString = replaceAllSpecialCharacters(lineInString);
									if (lineInString.matches("^[0-9]*$*")) {
										line = Integer.valueOf(lineInString);
									}
								}
								openOrdersDto.setLine(line);
							}
						}

						if (csvRecord.isMapped(PO_LINE)) {
							if (csvRecord.isSet(PO_LINE)) {
								openOrdersDto.setPO_Line(csvRecord.get(PO_LINE)
										.trim());
							}
						}
						if (csvRecord.isMapped(PO_STATUS)) {
							if (csvRecord.isSet(PO_STATUS)) {
								openOrdersDto.setPO_STATUS(csvRecord.get(
										PO_STATUS).trim());
							}
						}
						if (csvRecord.isMapped(REQUEST_SHIP_DATE)) {
							if (csvRecord.isSet(REQUEST_SHIP_DATE)) {
								String requestShipDate = csvRecord
										.get(REQUEST_SHIP_DATE);
								if (!StringUtils.isEmpty(requestShipDate)) {
									requestShipDate = replaceAllSpecialCharacters(requestShipDate);
									if (requestShipDate.matches("^[0-9/]*$*")) {
										openOrdersDto
												.setRequest_Ship_Date(RDF.format(EDF
														.parse(requestShipDate)));
									}
								}
							}
						}
						if (csvRecord.isMapped(CONFIRMED_SHIP_DATE)) {
							if (csvRecord.isSet(CONFIRMED_SHIP_DATE)) {
								String confirmedShipDate = csvRecord
										.get(CONFIRMED_SHIP_DATE);
								if (!StringUtils.isEmpty(confirmedShipDate)) {
									confirmedShipDate = replaceAllSpecialCharacters(confirmedShipDate);
									if (confirmedShipDate.matches("^[0-9/]*$*")) {
										openOrdersDto
												.setConfirmed_delivery(RDF.format(EDF
														.parse(confirmedShipDate)));
									}
								}
							}
						}
						if (csvRecord.isMapped(ETA_FLEX)) {
							if (csvRecord.isSet(ETA_FLEX)) {
								String etaFlex = csvRecord.get(ETA_FLEX);
								if (!StringUtils.isEmpty(etaFlex)) {
									etaFlex = replaceAllSpecialCharacters(etaFlex);
									if (etaFlex.matches("^[0-9/]*$*")) {
										openOrdersDto.setETD(RDF
												.format(EDF
														.parse(etaFlex)));
									}
								}
							}
						}
						if (csvRecord.isMapped(OPEN_QTY)) {
							if (csvRecord.isSet(OPEN_QTY)) {
								String openQuantity = csvRecord.get(OPEN_QTY);
								int quantity = 0;
								if (!StringUtils.isEmpty(openQuantity)) {
									openQuantity = replaceAllSpecialCharacters(openQuantity);
									if (openQuantity.matches("^[0-9]*$*")) {
										quantity = Integer
												.valueOf(openQuantity);
									}
								}
								openOrdersDto.setQty(quantity);
							}
						}
						if (csvRecord.isMapped(UNIT_PRICE)) {
							if (csvRecord.isSet(UNIT_PRICE)) {
								String unitPrice = csvRecord.get(UNIT_PRICE);
								double price = 0.0;
								if (!StringUtils.isEmpty(unitPrice)) {
									unitPrice = replaceAllSpecialCharacters(unitPrice);
									if (unitPrice.matches("^[0-9]*$*")) {
										price = Integer.valueOf(unitPrice);
									}
								}
								openOrdersDto.setPrice(price);
							}
						}
						if (csvRecord.isMapped(CURR)) {
							if (csvRecord.isSet(CURR)) {
								openOrdersDto.setCurr(csvRecord.get(CURR)
										.trim());
							}
						}
						if (csvRecord.isMapped(TOTAL)) {
							if (csvRecord.isSet(TOTAL)) {
								String total = csvRecord.get(TOTAL);
								double Total = 0.0;
								if (!StringUtils.isEmpty(total)) {
									total = replaceAllSpecialCharacters(total);
									if (total.matches("^[0-9]*$*")) {
										Total = Integer.valueOf(total);
									}
								}
								openOrdersDto.setTotal(Total);
							}
						}
						if (csvRecord.isMapped(SUPPLIER)) {
							if (csvRecord.isSet(SUPPLIER)) {
								String supplier = csvRecord.get(SUPPLIER);
								openOrdersDto.setSupplier(supplier.trim());
							}
						}
						if (csvRecord.isMapped(INVOICE_BANN)) {
							if (csvRecord.isSet(INVOICE_BANN)) {
								openOrdersDto.setInvoice_BaaN(csvRecord.get(
										INVOICE_BANN).trim());
							}
						}
						if (csvRecord.isMapped(WHERE_USED)) {
							if (csvRecord.isSet(WHERE_USED)) {
								openOrdersDto.setWhere_used(csvRecord.get(
										WHERE_USED).trim());
							}
						}
						openordersList.add(openOrdersDto);
					}
				}
				if (openordersList != null && openordersList.size() > 0) {
					if (dataDaoImpl.insertFileData(openordersList)) {
						fileUploadInfo.setRecordsCount(openordersList.size());
						fileUploadInfo.setUploadStatus(true);
					}
					generateLatestOpenOrders(openordersList, fileDate);
				}
			}
		} catch (Exception exception) {
			LOG.error(exception.getMessage());
		}
		return fileUploadInfo;
	}
/**
 * removes special characters
 * @param value
 * @return
 */
	public String replaceAllSpecialCharacters(String value) {
		return value.replaceAll("[',',' ','.']", "").trim();
	}
/**
 * Method for generate latest open orders file
 * @param openordersList
 * @param fileDate
 * @return
 */
	private boolean generateLatestOpenOrders(List<PcOpenorders> openordersList,
			String fileDate) {
		for (PcOpenorders openOrders : openordersList) {
			String item = openOrders.getItem();
			if (item != null) {
				openOrders.setItem(item.replace("HPM1-", ""));
			}
		}
		return dataDaoImpl.generateLatestDataCollection(openordersList,
				"NEWOPNORDERS_LATEST", fileDate);
	}
/**
 * To get file date from file name
 * @param ftpFile
 * @return
 */
	public String getOpenOrdersFileDate(FTPFile ftpFile) {
		String fileDate = ftpFile.getName().substring(2, 10);
		return fileDate.substring(4, 6) + "-" + fileDate.substring(6, 8) + "-"
				+ fileDate.substring(0, 4);
	}
/**
 * To find correct header
 * @param headersMap
 * @return
 */
	public boolean findHeader(Map<String, Integer> headersMap) {

		for (java.util.Map.Entry<String, Integer> header : headersMap
				.entrySet()) {
			if (!StringUtils.isEmpty(header.getKey())
					&& header.getKey().equals(ITEM_NUMBER)) {
				return true;
			}
		}

		return false;
	}

}
