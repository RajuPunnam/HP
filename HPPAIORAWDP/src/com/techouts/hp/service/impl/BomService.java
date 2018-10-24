/*package com.techouts.hp.service.impl;

import java.io.File;
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
import org.springframework.stereotype.Service;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Row;
import com.aspose.cells.RowCollection;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.google.common.base.CharMatcher;
import com.techouts.hp.dto.AMLAVCostedBomReportSummary;
import com.techouts.hp.dto.BomAvReportPeriod;
import com.techouts.hp.dto.BomFullReportPeriod;
import com.techouts.hp.util.OSUtil;

@Service
public class BomService {
	final static Logger LOGGER = Logger.getLogger(BomService.class);
	@Autowired
	private OSUtil OSUtil;
	@Autowired
	private FlectronicsFtpServiceImpl ftpService;
	@Autowired
	private ExcelService excelService;
	@Autowired
	private FtpDao ftpDao;
	@Resource(name = "myProps")
	private Properties properties;

	public boolean readBomAvReportPeriodFile(File absolutepath, FTPFile file)
			throws Exception {
		final String avReportPeriodHeaderArray[] = { "AV", "cm", "Level_Code",
				"comp", "description", "Commodity", "Qty_Per", "Ext_Qty_Per",
				"strPosition", "strPnType", "MPC", "eff_date", "end_date" };
		List<String> avReportPeriodHeaderList = Arrays
				.asList(avReportPeriodHeaderArray);
		SimpleDateFormat requiredDateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Workbook workbook = new Workbook(absolutepath + File.separator
				+ file.getName());
		int srNo = 1;
		boolean collectionExists = true;
		int totalNumberOfRecords = 0;
		boolean fileUploadStatus = false;
		for (int sheetCount = 0; sheetCount < workbook.getWorksheets()
				.getCount(); sheetCount++) {
			boolean headerFound = false;
			List<BomAvReportPeriod> bomAvReportPeriodsList = new ArrayList<BomAvReportPeriod>();
			Worksheet worksheet = workbook.getWorksheets().get(sheetCount);
			Cells cells = worksheet.getCells();
			RowCollection rows = cells.getRows();
			LOGGER.info("Sheet is " + worksheet.getName());
			LOGGER.info("number of rows in a sheet " + rows.getCount());
			Map<Integer, String> bomAvReportPeriodHeaders = new LinkedHashMap<Integer, String>();
			for (int i = 0; i < rows.getCount(); i++) {
				Row row = rows.get(i);
				boolean inserRow = false;
				if (row.isBlank()) {
					LOGGER.info("empty row row not inserted");
				} else if (!headerFound) {
					for (int cellIndex = 0; cellIndex <= row.getLastCell()
							.getColumn(); cellIndex++) {
						Cell cell = row.get(cellIndex);
						String header = excelService
								.getAsposeWorkbookCellData(cell);
						if (header != null) {
							if (avReportPeriodHeaderList.contains(header)) {
								if (!bomAvReportPeriodHeaders
										.containsValue(header)) {
									bomAvReportPeriodHeaders.put(
											cell.getColumn(), header);
								} else {
									for (int j = 1; j <= row.getLastCell()
											.getColumn(); j++) {
										String modifiedHeader = header + j;
										if (!bomAvReportPeriodHeaders
												.containsValue(modifiedHeader)) {
											bomAvReportPeriodHeaders.put(
													cell.getColumn(),
													modifiedHeader);
											break;
										} else {
											continue;
										}
									}
								}
							}
						}
					}
					if (bomAvReportPeriodHeaders.size() > avReportPeriodHeaderList
							.size() / 2) {
						LOGGER.info("header found");
						LOGGER.info(bomAvReportPeriodHeaders);
						headerFound = true;
					}
				} else {
					if (bomAvReportPeriodHeaders != null
							&& bomAvReportPeriodHeaders.size() > avReportPeriodHeaderList
									.size() / 2) {
						BomAvReportPeriod bomAvReportPeriod = new BomAvReportPeriod();
						bomAvReportPeriod.setSrNo(srNo);
						bomAvReportPeriod.setDate(requiredDateFormat
								.format(file.getTimestamp().getTime()));
						for (Entry<Integer, String> header : bomAvReportPeriodHeaders
								.entrySet()) {
							String cellData = excelService
									.getAsposeWorkbookCellData(row.get(header
											.getKey()));
							switch (header.getValue()) {
							case "AV":
								if (cellData.equals("")
										|| cellData.toString().isEmpty()) {
									inserRow = false;
									LOGGER.info("av not found row not inserted");
									break;
								} else {
									bomAvReportPeriod.setAv(cellData);
									inserRow = true;
								}
								break;
							case "cm":
								bomAvReportPeriod.setCm(cellData);
								break;
							case "Level_Code":
								if (cellData != null) {
									if (!cellData.equals("")) {
										cellData = CharMatcher.WHITESPACE
												.trimFrom(cellData.replaceAll(
														"[',',' ']", ""));
										if (cellData.matches("^[0-9.]*$*")) {
											double levelCode = Double
													.parseDouble(cellData);
											bomAvReportPeriod
													.setLevel_Code(levelCode);
										}
									}
								}

								break;
							case "comp":
								bomAvReportPeriod.setComp(cellData);
								break;
							case "description":
								bomAvReportPeriod.setDescription(cellData);
								break;
							case "Commodity":
								bomAvReportPeriod.setCommodity(cellData);
								break;
							case "Qty_Per":
								if (cellData != null) {
									if (!cellData.equals("")) {
										cellData = CharMatcher.WHITESPACE
												.trimFrom(cellData.replaceAll(
														"[',',' ']", ""));
										if (cellData.matches("^[0-9.]*$*")) {
											double qtyPer = Double
													.parseDouble(cellData);
											bomAvReportPeriod
													.setQty_Per(qtyPer);
										}
									}
								}
								break;
							case "Ext_Qty_Per":
								if (cellData != null) {
									if (!cellData.equals("")) {
										cellData = CharMatcher.WHITESPACE
												.trimFrom(cellData.replaceAll(
														"[',',' ']", ""));
										if (cellData.matches("^[0-9.]*$*")) {
											double Ext_Qty_Per = Double
													.parseDouble(cellData);
											bomAvReportPeriod
													.setExt_Qty_Per(Ext_Qty_Per);
										}
									}
								}
								break;
							case "strPosition":
								if (cellData != null) {
									if (cellData.equals("00Y")
											|| cellData.equals("#N/A")
											|| cellData.equals("CMP")) {
										inserRow = false;
										LOGGER.info(cellData
												+ " data row not inserted to db");
										break;
									} else {
										if (!cellData.equals("")) {
											cellData = CharMatcher.WHITESPACE
													.trimFrom(cellData
															.replaceAll(
																	"[',',' ']",
																	""));
											if (cellData.matches("^[0-9.]*$*")) {
												double strPosition = Double
														.parseDouble(cellData);
												bomAvReportPeriod
														.setStrPosition(strPosition);
											}
										}
									}
								}
								break;
							case "strPnType":
								bomAvReportPeriod.setStrPnType(cellData);
							case "MPC":
								bomAvReportPeriod.setMPC(cellData);
								break;
							case "eff_date":
								bomAvReportPeriod.setEff_date(cellData);
								break;
							case "end_date":
								bomAvReportPeriod.setEnd_date(cellData);
								break;
							}
						}
						if (inserRow) {
							++srNo;
							bomAvReportPeriodsList.add(bomAvReportPeriod);
						}
					}
				}
				if (bomAvReportPeriodsList.size() == 100000) {

					if (ftpDao
							.insertBomsData(
									bomAvReportPeriodsList,
									properties
											.getProperty("bomavreportperiod.collection"),
									collectionExists)) {
						totalNumberOfRecords = totalNumberOfRecords
								+ bomAvReportPeriodsList.size();
						LOGGER.info(totalNumberOfRecords
								+ " records inserted sucessfully");
					}
					collectionExists = false;
					bomAvReportPeriodsList.clear();
				}
			}
			if (bomAvReportPeriodsList.size() > 0) {
				fileUploadStatus = ftpDao.insertBomsData(
						bomAvReportPeriodsList,
						properties.getProperty("bomavreportperiod.collection"),
						collectionExists);
				if (fileUploadStatus) {
					totalNumberOfRecords = totalNumberOfRecords
							+ bomAvReportPeriodsList.size();
					LOGGER.info(totalNumberOfRecords
							+ " records inserted sucessusfully ");
					ReportSummary reportSummary = new ReportSummary();
					reportSummary.setCollectionName(properties
							.getProperty("bomavreportperiod.collection"));
					reportSummary.setLastUploadedFileDate(requiredDateFormat
							.format(file.getTimestamp().getTime()));
					ftpDao.getUpDateReportSummary(reportSummary);
				}
				collectionExists = false;
				bomAvReportPeriodsList.clear();
			}
		}
		return fileUploadStatus;
	}

	public boolean readAMLAVCostedBomReportSummary(File absolutepath,
			FTPFile file) throws Exception {
		final String AMLAVCostedBomReportSummaryHeaderArray[] = { "CM",
				"AV PN", "Level", "Part Number", "Position", "Type", "Qty Per",
				"Supplier", "Loc/Imp", "Mfg Lead Time", "Transp Lead Time",
				"Total Lead Time", "Currency", "Price", "Families",
				"SubFamilies", "Part Description", "MPC", "Commodity" };
		List<String> AMLAVCostedBomReportSummaryHeaderList = Arrays
				.asList(AMLAVCostedBomReportSummaryHeaderArray);
		SimpleDateFormat requiredDateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Workbook workbook = new Workbook(absolutepath + File.separator
				+ file.getName());
		int srNo = 1;
		int totalNumberOfRows = 0;
		boolean collectionExists = true;
		boolean fileUploadStatus = false;
		for (int sheetCount = 0; sheetCount < workbook.getWorksheets()
				.getCount(); sheetCount++) {
			List<AMLAVCostedBomReportSummary> amlAvCostedBomReportSummaryList = new ArrayList<AMLAVCostedBomReportSummary>();
			Worksheet worksheet = workbook.getWorksheets().get(sheetCount);
			boolean headerFound = false;
			Cells cells = worksheet.getCells();
			RowCollection rows = cells.getRows();
			LOGGER.info("sheet  is " + worksheet.getName());
			LOGGER.info("number of rows are " + rows.getCount());
			Map<Integer, String> amlAvCostedBomReportSummaryHeaders = new LinkedHashMap<Integer, String>();
			for (int i = 0; i < rows.getCount(); i++) {
				Row row = rows.get(i);
				boolean insertRow = false;
				;
				if (row.isBlank()) {
					LOGGER.info("empty row row not inserted");
				} else if (!headerFound) {
					for (int cellIndex = 0; cellIndex <= row.getLastCell()
							.getColumn(); cellIndex++) {
						Cell cell = row.get(cellIndex);
						String headerValue = excelService
								.getAsposeWorkbookCellData(cell);
						if (headerValue != null) {
							if (AMLAVCostedBomReportSummaryHeaderList
									.contains(headerValue)) {
								if (!amlAvCostedBomReportSummaryHeaders
										.containsValue(headerValue)) {
									amlAvCostedBomReportSummaryHeaders.put(
											cell.getColumn(), headerValue);
								} else {
									for (int j = 1; j <= row.getLastCell()
											.getColumn(); j++) {
										String modifiedHeader = headerValue + j;
										if (!amlAvCostedBomReportSummaryHeaders
												.containsValue(modifiedHeader)) {
											amlAvCostedBomReportSummaryHeaders
													.put(cell.getColumn(),
															modifiedHeader);
											break;
										} else {
											continue;
										}
									}
								}
							}
						}
					}
					if (amlAvCostedBomReportSummaryHeaders.size() > AMLAVCostedBomReportSummaryHeaderList
							.size() / 2) {
						LOGGER.info("header found");
						LOGGER.info(amlAvCostedBomReportSummaryHeaders);
						headerFound = true;
					}
				} else {
					if (amlAvCostedBomReportSummaryHeaders != null
							&& amlAvCostedBomReportSummaryHeaders.size() > 5) {
						AMLAVCostedBomReportSummary AMLAVCostedBomReportSummary = new AMLAVCostedBomReportSummary();
						AMLAVCostedBomReportSummary.setSrNo(srNo);
						AMLAVCostedBomReportSummary.setDate(requiredDateFormat
								.format(file.getTimestamp().getTime()));
						for (Entry<Integer, String> header : amlAvCostedBomReportSummaryHeaders
								.entrySet()) {
							String cellData = excelService
									.getAsposeWorkbookCellData(row.get(header
											.getKey()));
							switch (header.getValue()) {
							case "CM":
								AMLAVCostedBomReportSummary.setCM(cellData);
								break;
							case "AV PN":
								AMLAVCostedBomReportSummary.setAV_PN(cellData);
								break;
							case "Level":
								AMLAVCostedBomReportSummary.setLevel(cellData);
								break;
							case "Part Number":
								if (cellData.equals("")
										|| cellData.toString().isEmpty()) {
									insertRow = false;
									LOGGER.info("part number not found row not inserted");
									break;
								} else {
									AMLAVCostedBomReportSummary
											.setPart_Number(cellData);
									insertRow = true;
								}
								break;
							case "Position":
								if (cellData != null) {
									if (cellData.equals("00Y")
											|| cellData.equals("#N/A")
											|| cellData.equals("CMP")) {
										insertRow = false;
										LOGGER.info(cellData
												+ " data row not inserted to db");
										break;
									} else {
										AMLAVCostedBomReportSummary
												.setPosition(cellData);
									}
								}

								break;
							case "Type":
								AMLAVCostedBomReportSummary.setType(cellData);
								break;
							case "Qty Per":
								if (!cellData.isEmpty() || !cellData.equals("")) {
									cellData = CharMatcher.WHITESPACE
											.trimFrom(cellData.replaceAll(
													"[',',' ']", ""));
									if (cellData.matches("^[0-9.]*$*")) {
										double qtyPer = Double
												.parseDouble(cellData);
										AMLAVCostedBomReportSummary
												.setQtyPer(qtyPer);
									}
								}
								break;
							case "Supplier":
								AMLAVCostedBomReportSummary
										.setSupplier(cellData);
								break;
							case "Loc/Imp":
								AMLAVCostedBomReportSummary
										.setLoc_Imp(cellData);
								break;
							case "Mfg Lead Time":
								if (!cellData.isEmpty() || !cellData.equals("")) {
									cellData = CharMatcher.WHITESPACE
											.trimFrom(cellData.replaceAll(
													"[',',' ']", ""));
									if (cellData.matches("^[0-9.]*$*")) {
										double mfgleadTime = Double
												.parseDouble(cellData);
										AMLAVCostedBomReportSummary
												.setMfg_Lead_Time(mfgleadTime);
									}
								}

								break;
							case "Transp Lead Time":
								if (!cellData.isEmpty() || !cellData.equals("")) {
									cellData = CharMatcher.WHITESPACE
											.trimFrom(cellData.replaceAll(
													"[',',' ']", ""));
									if (cellData.matches("^[0-9.]*$*")) {
										double transLeadTime = Double
												.parseDouble(cellData);
										AMLAVCostedBomReportSummary
												.setTransp_Lead_Time(transLeadTime);
									}
								}

								break;
							case "Total Lead Time":
								if (!cellData.isEmpty() || !cellData.equals("")) {
									cellData = CharMatcher.WHITESPACE
											.trimFrom(cellData.replaceAll(
													"[',',' ']", ""));
									if (cellData.matches("^[0-9.]*$*")) {
										double totalLeadTime = Double
												.parseDouble(cellData);
										AMLAVCostedBomReportSummary
												.setTotal_Lead_Time(totalLeadTime);
									}
								}

								break;
							case "Currency":
								AMLAVCostedBomReportSummary
										.setCurrency(cellData);
								break;
							case "Price":
								if (cellData != null) {
									if (!cellData.equals("")
											|| !cellData.toString().isEmpty()) {
										cellData = CharMatcher.WHITESPACE
												.trimFrom(cellData.replaceAll(
														"[',',' ']", ""));
										if (cellData.matches("^[0-9.]*$*")) {
											double price = Double
													.parseDouble(cellData);
											AMLAVCostedBomReportSummary
													.setPrice(price);
										}
									}
								}

								break;
							case "Families":
								AMLAVCostedBomReportSummary
										.setFamilies(cellData);
								break;
							case "SubFamilies":
								AMLAVCostedBomReportSummary
										.setSubFamilies(cellData);
								break;
							case "Part Description":
								AMLAVCostedBomReportSummary
										.setPart_Description(cellData);
								break;
							case "MPC":
								AMLAVCostedBomReportSummary.setMPC(cellData);
								break;
							case "Commodity":
								AMLAVCostedBomReportSummary
										.setCommodity(cellData);
								break;
							}
						}
						if (insertRow) {
							++srNo;
							amlAvCostedBomReportSummaryList
									.add(AMLAVCostedBomReportSummary);
						}
					}
				}
				if (amlAvCostedBomReportSummaryList.size() == 100000) {
					if (ftpDao
							.insertBomsData(
									amlAvCostedBomReportSummaryList,
									properties
											.getProperty("AMLAVCostedBomReportSummary.collection"),
									collectionExists)) {
						totalNumberOfRows = totalNumberOfRows
								+ amlAvCostedBomReportSummaryList.size();
						LOGGER.info(totalNumberOfRows
								+ " records inserted sucessfully");
					}
					collectionExists = false;
					amlAvCostedBomReportSummaryList.clear();
				}
			}
			if (amlAvCostedBomReportSummaryList.size() > 0) {
				fileUploadStatus = ftpDao
						.insertBomsData(
								amlAvCostedBomReportSummaryList,
								properties
										.getProperty("AMLAVCostedBomReportSummary.collection"),
								collectionExists);
				if (fileUploadStatus) {
					totalNumberOfRows = totalNumberOfRows
							+ amlAvCostedBomReportSummaryList.size();
					LOGGER.info(totalNumberOfRows
							+ " records inserted sucessusfully ");
					ReportSummary reportSummary = new ReportSummary();
					reportSummary
							.setCollectionName(properties
									.getProperty("AMLAVCostedBomReportSummary.collection"));
					reportSummary.setLastUploadedFileDate(requiredDateFormat
							.format(file.getTimestamp().getTime()));
					ftpDao.getUpDateReportSummary(reportSummary);
				}
				collectionExists = false;
			}
		}
		return fileUploadStatus;
	}

	public boolean readBomFullreportPeriodFiles(File absolutepath, FTPFile file)
			throws Exception {
		final String fullReportHeaderArray[] = { "PL", "family", "sub family",
				"product number", "prod description", "level", "part number",
				"part description", "commodity", "qty per", "position", "MPC",
				"RoHs", "PICA Code", "eff date", "end date" };
		List<String> bomFullReportHeaderList = Arrays
				.asList(fullReportHeaderArray);
		SimpleDateFormat requiredDateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Workbook workbook = new Workbook(absolutepath + File.separator
				+ file.getName());
		int srNo = 1;
		boolean collDropStatus = true;
		boolean uploadStatus = false;
		int totalNumberOfRows = 0;
		for (int sheetCount = 0; sheetCount < workbook.getWorksheets()
				.getCount(); sheetCount++) {
			boolean headerFound = false;
			List<BomFullReportPeriod> bomFullReportPeriodList = new ArrayList<BomFullReportPeriod>();
			Worksheet worksheet = workbook.getWorksheets().get(sheetCount);
			Cells cells = worksheet.getCells();
			RowCollection rows = cells.getRows();
			LOGGER.info("sheet  is " + worksheet.getName());
			LOGGER.info("number of rows are " + rows.getCount());
			Map<Integer, String> bomFullReportPeriodHeaders = new LinkedHashMap<Integer, String>();
			for (int i = 0; i < rows.getCount(); i++) {
				Row row = rows.get(i);
				boolean insertRow = false;
				;
				if (row.isBlank()) {
					LOGGER.info("empty row row not inserted");
				} else if (!headerFound) {
					for (int cellIndex = 0; cellIndex <= row.getLastCell()
							.getColumn(); cellIndex++) {
						Cell cell = row.get(cellIndex);
						String headerValue = excelService
								.getAsposeWorkbookCellData(cell);
						if (headerValue != null) {
							if (bomFullReportHeaderList.contains(headerValue)) {
								if (!bomFullReportPeriodHeaders
										.containsValue(headerValue)) {
									bomFullReportPeriodHeaders.put(
											cell.getColumn(), headerValue);
								} else {
									for (int j = 1; j <= row.getLastCell()
											.getColumn(); j++) {
										String modifiedHeader = headerValue + j;
										if (!bomFullReportPeriodHeaders
												.containsValue(modifiedHeader)) {
											bomFullReportPeriodHeaders.put(
													cell.getColumn(),
													modifiedHeader);
											break;
										} else {
											continue;
										}
									}
								}
							}
						}
					}
					if (bomFullReportPeriodHeaders.size() > bomFullReportHeaderList
							.size() / 2) {
						LOGGER.info("header found");
						LOGGER.info(bomFullReportPeriodHeaders);
						headerFound = true;
					}
				} else {

					if (bomFullReportPeriodHeaders != null
							&& bomFullReportPeriodHeaders.size() > bomFullReportHeaderList
									.size() / 2) {
						BomFullReportPeriod bomFullReportPeriod = new BomFullReportPeriod();
						bomFullReportPeriod.setSR_NO(srNo);
						bomFullReportPeriod.setDate(requiredDateFormat
								.format(file.getTimestamp().getTime()));
						for (Entry<Integer, String> header : bomFullReportPeriodHeaders
								.entrySet()) {
							String cellData = excelService
									.getAsposeWorkbookCellData(row.get(header
											.getKey()));
							switch (header.getValue()) {
							case "PL":
								bomFullReportPeriod.setPL(cellData);
								break;
							case "family":
								bomFullReportPeriod.setFamily(cellData);
								break;
							case "sub family":
								bomFullReportPeriod.setSub_family(cellData);
								break;
							case "product number":
								bomFullReportPeriod.setProduct_number(cellData);
								break;
							case "prod description":
								bomFullReportPeriod
										.setProd_description(cellData);
								break;
							case "level":
								bomFullReportPeriod.setLevel(cellData);
								break;
							case "part number":
								if (cellData.equals("")
										|| cellData.toString().isEmpty()) {
									insertRow = false;
									LOGGER.info("part number not found row not inserted");
									break;
								} else {
									bomFullReportPeriod
											.setPart_number(cellData);
									insertRow = true;
								}
								break;
							case "part description":
								bomFullReportPeriod
										.setPart_description(cellData);
								break;
							case "commodity":
								bomFullReportPeriod.setCommodity(cellData);
								break;
							case "qty per":
								if (cellData != null) {
									if (!cellData.equals("")
											|| !cellData.toString().isEmpty()) {
										cellData = CharMatcher.WHITESPACE
												.trimFrom(cellData.replaceAll(
														"[',',' ']", ""));
										if (cellData.matches("^[0-9.]*$*")) {
											double qtyPer = Double
													.parseDouble(cellData);
											bomFullReportPeriod
													.setQty_per(qtyPer);
										}
									}
								}

								break;
							case "position":
								if (cellData.equals("00Y")
										|| cellData.equals("#N/A")
										|| cellData.equals("CMP")) {
									insertRow = false;
									LOGGER.info(cellData
											+ "  row data not inserted to db");
									break;
								} else {
									if (!cellData.equals("")
											|| !cellData.toString().isEmpty()) {
										cellData = CharMatcher.WHITESPACE
												.trimFrom(cellData.replaceAll(
														"[',',' ']", ""));
										double strPosition = Double
												.parseDouble(cellData);
										if (cellData.matches("^[0-9.]*$*")) {
											bomFullReportPeriod
													.setPosition(strPosition);
										}
									}
								}
								break;
							case "MPC":
								bomFullReportPeriod.setMPC(cellData);
								break;
							case "RoHs":
								bomFullReportPeriod.setRoHs(cellData);
								break;
							case "PICA Code":
								bomFullReportPeriod.setPICA_Code(cellData);
								break;
							case "eff date":
								bomFullReportPeriod.setEff_date(cellData);
								break;
							case "end date":
								bomFullReportPeriod.setEnd_date(cellData);
								break;
							}
						}
						if (insertRow) {
							++srNo;
							bomFullReportPeriodList.add(bomFullReportPeriod);
						}
					}
				}
				if (bomFullReportPeriodList.size() == 100000) {
					totalNumberOfRows = totalNumberOfRows
							+ bomFullReportPeriodList.size();
					if (ftpDao
							.insertBomsData(
									bomFullReportPeriodList,
									properties
											.getProperty("bomfullreportperiod.collection"),
									collDropStatus)) {
						LOGGER.info(totalNumberOfRows + " inserted sucessfully");
					}
					collDropStatus = false;
					bomFullReportPeriodList.clear();
				}
			}
			if (bomFullReportPeriodList.size() > 0) {
				totalNumberOfRows = totalNumberOfRows
						+ bomFullReportPeriodList.size();
				uploadStatus = ftpDao.insertBomsData(bomFullReportPeriodList,
						properties
								.getProperty("bomfullreportperiod.collection"),
						collDropStatus);
				if (uploadStatus) {
					LOGGER.info(totalNumberOfRows + " inserted sucessusfully ");
					ReportSummary reportSummary = new ReportSummary();
					reportSummary.setCollectionName(properties
							.getProperty("bomfullreportperiod.collection"));
					reportSummary.setLastUploadedFileDate(requiredDateFormat
							.format(file.getTimestamp().getTime()));
					ftpDao.getUpDateReportSummary(reportSummary);
				}
				collDropStatus = false;
				bomFullReportPeriodList.clear();
			}
		}
		return uploadStatus;
	}
}
*/