package com.io.services.po;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.io.dao.ForecastIps_SuppliesDao;
import com.io.pojos.ForeCastStatus;
import com.ftp.support.FTPSupport;
import com.inventory.utill.ExcelSupport;
import com.io.dao.PrintersDao;
import com.io.pojos.OpenOrderPrinters;
import com.techouts.hp.supplies.dao.SuppliesPrinterDao;

@SuppressWarnings("unused")
@Component
public class OpenOrdersPrintersService {

	Logger log = Logger.getLogger(OpenOrdersPrintersService.class);

	@Autowired
	private PrintersDao printersDao;
	@Autowired
	private SuppliesPrinterDao suppliesPrinterDao;

	public String downloadFile() {
		List<String> directoryList = new ArrayList<String>();
		String directoryName = "";
		SimpleDateFormat requiredDate = new SimpleDateFormat("MM-dd-yyyy");
		requiredDate.setLenient(false);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.setLenient(false);
		String staticDate = "01-01-2016";
		Date date = null;
		try {
			date = requiredDate.parse(staticDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		String path = "";
		boolean status = false;
		FileOutputStream fos = null;
		Calendar cal = Calendar.getInstance();
		Date maxDate = printersDao.getMaxDate(OpenOrderPrinters.class);
		if (maxDate == null)
			maxDate = date;
		log.info("Last Uploaded file Date::::" + maxDate);

		FTPClient client = new FTPClient();

		for (int i = 0; i < 10; i++) {

			if (FTPSupport.getftpConnection(client)) {
				log.info("connected");
				try {
					for (FTPFile mainDir : client.listDirectories()) {

						if (mainDir.isDirectory()
								&& mainDir.getName().equals("BINOY")) {
							log.info("in BINOY Folder");
							directoryList.add(mainDir.getName());
							for (FTPFile subDir : client.listDirectories("/"
									+ mainDir.getName())) {

								if (subDir.isDirectory()
										&& subDir.getName().equals("PRINTERS")) {
									log.info("In PRINTERS Folder");
									directoryList.add(subDir.getName());
									for (FTPFile ssubDir : client
											.listDirectories(mainDir.getName()
													+ "/" + subDir.getName())) {

										if (ssubDir.isDirectory()
												&& ssubDir.getName().equals(
														"Open Order")) {
											log.info("In Open ORDER Folder");
											directoryList
													.add(ssubDir.getName());
											if (client
													.changeWorkingDirectory("/"
															+ mainDir.getName()
															+ "/"
															+ subDir.getName()
															+ "/"
															+ ssubDir.getName())) {
												for (FTPFile sssubDir : client
														.listDirectories()) {
													log.info("in "
															+ sssubDir
																	.getName()
															+ " Folder");
													if (sssubDir.isDirectory()) {
														if (directoryList
																.contains(directoryName)) {
															directoryList
																	.remove(directoryName);
															directoryList
																	.add(sssubDir
																			.getName());
														} else {
															directoryList
																	.add(sssubDir
																			.getName());
														}

														if (client
																.changeWorkingDirectory("/"
																		+ mainDir
																				.getName()
																		+ "/"
																		+ subDir.getName()
																		+ "/"
																		+ ssubDir
																				.getName()
																		+ "/"
																		+ sssubDir
																				.getName())) {
															for (FTPFile file : client
																	.listFiles()) {
																if (file.isFile()) {
																	try {
																		String s = requiredDate
																				.format(sdf
																						.parse(file
																								.getName()
																								.substring(
																										17,
																										27)));

																		Date fileDate = requiredDate
																				.parse(s);

																		if (fileDate
																				.after(maxDate)) {
																			log.info(file
																					.getName()
																					+ "::File Date:::"
																					+ fileDate);
																			String fileLocation = FTPSupport
																					.downLoadFileFromFTP(
																							file,
																							directoryList,
																							client);

																			status = readExelFile(
																					file,
																					fileLocation,
																					fileDate);
																			File file1 = new File(
																					fileLocation
																							+ "/"
																							+ file.getName());
																			file1.delete();
																			log.info("file deleted in local");
																		}
																	} catch (ParseException e) {
																		e.printStackTrace();
																	}
																}
															}
															directoryName = sssubDir
																	.getName();
															if (!status)
																log.info("no Data Found in "
																		+ directoryName);
														} else {
															log.info("Direcrty Not changing");
														}
													}
												}
											}
										}
									}

								}
							}
						}

					}
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}
				break;
			}
		}
		return path;

	}

	private boolean readExelFile(FTPFile file, String fileLocation,
			Date fileDate) {

		File inputlocation = new File(fileLocation + File.separator
				+ file.getName());

		log.info(file.getName());
		boolean status = false;
		Map<Integer, String> headers = new LinkedHashMap<Integer, String>();
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		String[] columnNames;
		List<String> columnNameList = null;
		int headerRowNumer = 0;
		try {
			workbook = new XSSFWorkbook(new FileInputStream(inputlocation));
		} catch (IOException e) {
			log.info(e.getMessage());
		}

		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			if (workbook.getSheetAt(i).getSheetName().equals("Sheet1")) {

				log.info("workbook.getSheetAt(i).getSheetName()");
				sheet = workbook.getSheetAt(i);
				int totalRows = sheet.getLastRowNum();
				log.info("total rows::" + totalRows);
				for (int j = 0; j < totalRows; j++) {
					Row row = sheet.getRow(j);
					columnNames = ExcelSupport.getRowData(row);
					columnNameList = Arrays.asList(columnNames);
					if (columnNameList.contains("PN")) {
						headerRowNumer = j;
						break;
					} else {
						continue;
					}
				}

				for (int j = 0; j < columnNameList.size(); j++) {
					headers.put(j, columnNameList.get(j).trim());
				}

				log.info(headers);
				log.info("header row::" + headerRowNumer);

				status = loadDataToList(headers, sheet, headerRowNumer,
						fileDate);

			}
		}
		return status;
	}

	private boolean loadDataToList(Map<Integer, String> headers,
			XSSFSheet sheet, int headerRowNumer, Date fileDate) {
		boolean status = false;
		SimpleDateFormat required = new SimpleDateFormat("MM-dd-yyyy");
		List<OpenOrderPrinters> openOrderPrintersList = new ArrayList<OpenOrderPrinters>();
		Iterator<Row> rowIterator = sheet.iterator();

		while (rowIterator.hasNext()) {

			OpenOrderPrinters openOrderPrinters = new OpenOrderPrinters();
			Row row = rowIterator.next();
			if (row.getRowNum() > headerRowNumer) {
				int maxNumOfCells = row.getLastCellNum();
				Iterator<Cell> cellIterator = row.cellIterator();
				Cell cellData = null;
				int i = 0;
				/* while (cellIterator.hasNext()) { */
				for (int cell = 0; cell <= maxNumOfCells; cell++) {
					cellData = row.getCell(cell);
					String coloumn = headers.get(i++);
					if (i <= 13) {
						switch (coloumn) {
						case "Buyer":
							if (cellData != null)
								openOrderPrinters.setBuyer(cellData
										.getStringCellValue());
							break;

						case "PN":
							if (cellData != null)
								openOrderPrinters.setPn(cellData
										.getStringCellValue());
							break;

						case "Desc.":
							if (cellData != null)
								openOrderPrinters.setDesc(cellData
										.getStringCellValue());
							break;

						case "PO DATE":
							String podate = ExcelSupport.getCellData(cellData);
							try {
								if (podate != null && podate != "")
									openOrderPrinters.setPo_DATE(required
											.parse(podate));
								else
									openOrderPrinters.setPo_DATE(null);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							break;

						case "PO/L":
							openOrderPrinters.setPO_L(ExcelSupport
									.getCellData(cellData));
							break;

						case "ST":
							openOrderPrinters.setST(ExcelSupport
									.getCellData(cellData));
							break;

						case "Dt Emb":
							String date1 = ExcelSupport.getCellData(cellData);
							try {
								if (date1 != null && date1 != "")
									openOrderPrinters.setDt_Emb(required
											.parse(date1));
								else
									openOrderPrinters.setDt_Emb(null);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							break;

						case "Dt Conf Emb":
							String date2 = ExcelSupport.getCellData(cellData);
							try {
								if (date2 != null && date2 != "")
									openOrderPrinters.setDdt_Conf_Emb(required
											.parse(date2));
								else
									openOrderPrinters.setDdt_Conf_Emb(null);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							break;

						case "Pick Up":
							String date3 = ExcelSupport.getCellData(cellData);
							try {
								if (date3 != null && date3 != "")
									openOrderPrinters.setPick_Up(required
											.parse(date3));
								else
									openOrderPrinters.setPick_Up(null);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							break;

						case "Dt Entr":
							String date4 = ExcelSupport.getCellData(cellData);
							try {
								if (date4 != null && date4 != "")
									openOrderPrinters.setDt_Entr(required
											.parse(date4));
								else
									openOrderPrinters.setDt_Entr(null);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							break;

						case "QT":
							String qty = ExcelSupport.getCellData(cellData);
							if (qty != null && qty != "")
								openOrderPrinters.setQty(Double
										.parseDouble(qty));
							else
								openOrderPrinters.setQty(0);
							break;

						case "Fornecedor":
							openOrderPrinters.setFornecedor(ExcelSupport
									.getCellData(cellData));
							break;

						case "Invoice# (Manual)":
							openOrderPrinters.setInvoice_Manual(ExcelSupport
									.getCellData(cellData));
							break;

						default:
							break;
						}
					}

				}

				openOrderPrinters.setFileDate(fileDate);
				if (openOrderPrinters.getPn() != null
						&& !openOrderPrinters.getPn().isEmpty()) {

					openOrderPrintersList.add(openOrderPrinters);
					System.out.println(openOrderPrinters);
				}
			}
		}

		status = suppliesPrinterDao.insertData(openOrderPrintersList);
		return status;
	}

}
