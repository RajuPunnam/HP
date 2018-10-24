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
import com.techouts.hp.dto.AMLAVCostedBomReportSummary;
import com.techouts.hp.pojo.FileUploadInfo;
import com.techouts.hp.pojo.FtpSupport;
import com.techouts.hp.service.DataService;
import com.techouts.hp.service.FtpService;
import com.techouts.hp.util.ExcelUtil;
import com.techouts.hp.util.OSUtil;

public class AmlavCostedBomReportSummaryServiceImpl implements DataService {
	private static final Logger LOGGER = Logger
			.getLogger(AmlavCostedBomReportSummaryServiceImpl.class);
	@Autowired
	private OSUtil OSUtil;
	@Resource(name = "techoutsFtpServiceImpl")
	private FtpService techoutsFtpService;
	@Autowired
	private ExcelUtil excelService;
	@Resource(name = "bomDaoImpl")
	private DataDao dataDao;
	@Resource(name = "myProps")
	private Properties properties;
	private static  String AMLAVCostedBomReportSummaryHeaderArray[] = { "CM",
			"AV PN", "Part Number", "Position", "Type", "Qty Per", "Supplier",
			"Loc/Imp", "Mfg Lead Time", "Transp Lead Time", "Total Lead Time",
			"Currency", "Price", "Families", "SubFamilies", "Part Description",
			"MPC", "Commodity", "Level" };
	private static  List<String> AMLAVCostedBomReportSummaryHeaderList = Arrays
			.asList(AMLAVCostedBomReportSummaryHeaderArray);
	private static final SimpleDateFormat RDF = new SimpleDateFormat(
			"MM-dd-yyyy");
	private  static final String CM = "CM";
	private  static final String AV_PN = "AV PN";
	private  static final String PART_NUMBER = "Part Number";
	private  static final String POSITION = "Position";
	private  static final String TYPE = "Type";
	private  static final String QTY_PER = "Qty Per";
	private  static final String SUPPLIER = "Supplier";
	private  static final String LOC_IMP = "Loc/Imp";
	private  static final String MFG_LEAD_TIME = "Mfg Lead Time";
	private  static final String TRANSP_LEAD_TIME = "Transp Lead Time";
	private  static final String TOTAL_LEAD_TIME = "Total Lead Time";
	private  static final String CURRENCY = "Currency";
	private  static final String PRICE = "Price";
	private static final String FAMILIES = "Families";
	private static final String SUB_FAMILIES = "SubFamilies";
	private static final String PART_DESCRIPTION = "Part Description";
	private static final String MPC = "MPC";
	private static final String COMMODITY = "Commodity";
	private static final String LEVEL = "Level";

	public void getLoadAmlavCostedBomReportSummaryFiles() throws ParseException {
		FtpSupport ftpSupport = new FtpSupport();
		ftpSupport.setCollectionName(properties
				.getProperty("AMLAVCostedBomReportSummary.collection"));
		ftpSupport.setSubFolder(properties
				.getProperty("techouts.ftp.bom.sub.folder"));
		ftpSupport.setDownLoadDirectory(OSUtil.downloadDirectory());
		ftpSupport.setFileName("AMLAVCostedBomReportSummary");
		ftpSupport.setFileType(".csv");
		ftpSupport.setFtpServer(properties.getProperty("techouts.ftp.server"));
		ftpSupport.setFtpUsername(properties
				.getProperty("techouts.ftp.username"));
		ftpSupport.setFtpPassword(properties
				.getProperty("techouts.ftp.password"));
		int count = techoutsFtpService.downloadFilesFromFtp(ftpSupport, 0);
		LOGGER.info("[[" + count + "]] New file processed");
		LOGGER.info("AMLAVCostedBomReportSummary thread compleated");
	}

	public FileUploadInfo readDataFromFile(File absolutepath, FTPFile file)
			throws Exception {
		FileUploadInfo fileUploadInfo = new FileUploadInfo();
		Workbook workbook = new Workbook(absolutepath + File.separator
				+ file.getName());
		int srNo = 1;
		int totalNumberOfRows = 0;
		boolean fileUploadStatus = false;
		boolean collectionExists = true;
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
						AMLAVCostedBomReportSummary.setDate(RDF
								.format(file.getTimestamp().getTime()));
						for (Entry<Integer, String> header : amlAvCostedBomReportSummaryHeaders
								.entrySet()) {
							String cellData = excelService
									.getAsposeWorkbookCellData(row.get(header
											.getKey()));
							switch (header.getValue()) {
							case CM:
								AMLAVCostedBomReportSummary.setCM(cellData);
								break;
							case AV_PN:
								AMLAVCostedBomReportSummary.setAV_PN(cellData);
								break;
							case LEVEL:
								AMLAVCostedBomReportSummary.setLevel(cellData);
								break;
							case PART_NUMBER:
								if (StringUtils.isEmpty(cellData)) {
									insertRow = false;
									LOGGER.info("part number not found row not inserted");
									break;
								} else {
									AMLAVCostedBomReportSummary
											.setPart_Number(cellData);
									insertRow = true;
								}
								break;
							case POSITION:

								if (!StringUtils.isEmpty(cellData)
										&& cellData.equals("00Y")
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

								break;
							case TYPE:
								AMLAVCostedBomReportSummary.setType(cellData);
								break;
							case QTY_PER:
								if (!StringUtils.isEmpty(cellData)) {
									cellData = removeSpecialCharacters(cellData);
									if (cellData.matches("^[0-9.]*$*")) {
										AMLAVCostedBomReportSummary
												.setQtyPer(Double
														.parseDouble(cellData));
									}
								}
								break;
							case SUPPLIER:
								AMLAVCostedBomReportSummary
										.setSupplier(cellData);
								break;
							case LOC_IMP:
								AMLAVCostedBomReportSummary
										.setLoc_Imp(cellData);
								break;
							case MFG_LEAD_TIME:
								if (!StringUtils.isEmpty(cellData)) {
									cellData = removeSpecialCharacters(cellData);
									if (cellData.matches("^[0-9.]*$*")) {
										AMLAVCostedBomReportSummary
												.setMfg_Lead_Time(Double
														.parseDouble(cellData));
									}
								}

								break;
							case TRANSP_LEAD_TIME:
								if (!StringUtils.isEmpty(cellData)) {
									cellData = removeSpecialCharacters(cellData);
									if (cellData.matches("^[0-9.]*$*")) {
										AMLAVCostedBomReportSummary
												.setTransp_Lead_Time(Double
														.parseDouble(cellData));
									}
								}

								break;
							case TOTAL_LEAD_TIME:
								if (!StringUtils.isEmpty(cellData)) {
									cellData = removeSpecialCharacters(cellData);
									if (cellData.matches("^[0-9.]*$*")) {
										AMLAVCostedBomReportSummary
												.setTotal_Lead_Time(Double
														.parseDouble(cellData));
									}
								}

								break;
							case CURRENCY:
								AMLAVCostedBomReportSummary
										.setCurrency(cellData);
								break;
							case PRICE:
								if (cellData != null) {
									if (!StringUtils.isEmpty(cellData)) {
										cellData = removeSpecialCharacters(cellData);
										AMLAVCostedBomReportSummary
												.setPrice(Double
														.parseDouble(cellData));
									}
								}

								break;
							case FAMILIES:
								AMLAVCostedBomReportSummary
										.setFamilies(cellData);
								break;
							case SUB_FAMILIES:
								AMLAVCostedBomReportSummary
										.setSubFamilies(cellData);
								break;
							case PART_DESCRIPTION:
								AMLAVCostedBomReportSummary
										.setPart_Description(cellData);
								break;
							case MPC:
								AMLAVCostedBomReportSummary.setMPC(cellData);
								break;
							case COMMODITY:
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
					if (dataDao
							.insertBomData(
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

				fileUploadStatus = dataDao
						.insertBomData(
								amlAvCostedBomReportSummaryList,
								properties
										.getProperty("AMLAVCostedBomReportSummary.collection"),
								collectionExists);
				if (fileUploadStatus) {
					totalNumberOfRows = totalNumberOfRows
							+ amlAvCostedBomReportSummaryList.size();
					LOGGER.info(totalNumberOfRows
							+ " records inserted sucessusfully ");
				}
				collectionExists = false;
			}
		}
		fileUploadInfo.setUploadStatus(fileUploadStatus);
		fileUploadInfo.setRecordsCount(totalNumberOfRows);
		return fileUploadInfo;
	}

	public String removeSpecialCharacters(String cellValue) {
		return CharMatcher.WHITESPACE.trimFrom(cellValue.replaceAll(
				"[',',' ']", ""));
	}
}
