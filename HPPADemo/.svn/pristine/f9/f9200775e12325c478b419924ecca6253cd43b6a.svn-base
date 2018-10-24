package com.techouts.AvList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.mail.internet.AddressException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.techouts.util.SendMails;

@Service
@EnableScheduling
public class AllAvsService {
	private static final Logger LOG = Logger.getLogger(AllAvsService.class);
	@Autowired
	private AllAvsDao allAvsDao;
	@Resource(name = "myProps")
	private Properties properties;

	@Autowired
	private FtpFileUploadService ftpFileUploadService;
	XSSFWorkbook workbook = null;

	/*@Scheduled(cron="0 0/1 * * * ?")*/
	@Scheduled(cron = "0 0 16 ? * THU")
	public void getAvsData() {
		LOG.info("Old file downloading from ftp.......");
		List<String> oldAvs = downloadOldFile();
		List<String> newAddedAvs = new ArrayList<String>();
		LOG.info("collecting all avs......");
		Set<String> finalAvsList = new HashSet<String>();

		List<String> av1 = allAvsDao.getMasterSuperFlexBomData(
				"MASTER_BOM_FLEX", "Flat BOM #");

		LOG.info("Collecting distinct MASTER_BOM_FLEX Avs from " + av1.size()
				+ " Avs");

		for (String string : av1) {
			String finalav = string.replace("HPM1-", "");
			if (!string.isEmpty()) {
				if (!finalAvsList.contains(finalav.trim())) {
					finalAvsList.add(finalav.trim());
				}
			}
		}

		LOG.info("Total Collected MASTER_BOM_FLEX Avs are::::"
				+ finalAvsList.size());

		int av2count = 0;
		List<String> av2 = allAvsDao.getMasterSuperFlexBomData(
				"COMBINED_SKUBOM(BPC,BNB,WS)", "PN");
		LOG.info("Collecting distinct COMBINED_SKUBOM(BPC,BNB,WS) Avs from "
				+ av2.size() + " Avs");
		for (String string : av2) {
			if (!string.isEmpty()) {
				String finalav = string.replace("HPM1-", "");
				if (!finalAvsList.contains(finalav.trim())) {
					finalAvsList.add(finalav.trim());
					av2count++;
				}
			}
		}

		LOG.info("Total Collected COMBINED_SKUBOM(BPC,BNB,WS) Avs are "
				+ finalAvsList.size() + " and added Avs count are " + av2count);
		int av3count = 0;
		List<String> av3 = allAvsDao.getMasterSuperFlexBomData(
				"BOM_AV_REPORT_PERIOD", "AV");
		LOG.info("Collecting distinct BOM_AV_REPORT_PERIOD Avs are "
				+ av3.size());
		for (String string : av3) {
			if (!string.isEmpty()) {
				String finalav = string.replace("HPM1-", "");
				if (!finalAvsList.contains(finalav.trim())) {
					av3count++;
					finalAvsList.add(finalav.trim());

				}
			}
		}
		LOG.info(" Total collected BOM_AV_REPORT_PERIOD added avs count::"
				+ av3count);

		List<String> av4 = allAvsDao.getMasterSuperFlexBomData(
				"BOM_AMLCOSTEDBOM_AVSUMMARY", "AV PN");
		int av4count = 0;
		LOG.info("Collecting distinct BOM_AMLCOSTEDBOM_AVSUMMARY avs count:::::"
				+ av4.size());
		for (String string : av4) {
			if (!string.isEmpty()) {
				String finalav = string.replace("HPM1-", "");
				if (!finalAvsList.contains(finalav.trim())) {
					av4count++;
					finalAvsList.add(finalav.trim());
				}

			}
		}

		LOG.info("BOM_AMLCOSTEDBOM_AVSUMMARY added avs count::" + av4count);

		for (String string : finalAvsList) {
			if (oldAvs.contains(string)) {
				continue;
			} else {
				LOG.info(" new avs from new file:::::::" + string);
				newAddedAvs.add(string);
			}
		}

		LOG.info("checking new data with old data.......");

		for (String string : oldAvs) {
			if (!finalAvsList.contains(string)) {
				LOG.info("Avs from old file::::" + string);
				finalAvsList.add(string);
			}
		}

		LOG.info("****************got all av data****************");
		LOG.info("New AVs are found:::::::::::::" + newAddedAvs);
		
		LOG.info("Total Avs count::::::::::"+finalAvsList.size());
		File file = createWorkbook(finalAvsList);

		LOG.info("**************file created*******************");

		/* ftpFileUploadService.getUploadSkuAvailToFtp(file) */
		if (ftpFileUploadService.getUploadSkuAvailToFtp(file)) {
			LOG.info("**************uploaded successfully***************");
			String msgBody = "Hi All,\n\n"
					+ "We have replaced a new file in FTP BOM with filename as"
					+ "' Techouts-AV List For AV-PN Kinaxis BOM'.\n"
					+ "We had prepared a new file of AV's which consists of "
					+ finalAvsList.size() + " Av's."
					+ "\n\nThanks&Regards,\nno-reply@techouts.com.";
			SendMails.sendMail("bhuvanesh.m@techouts.com",
					"Updated Av's List ", msgBody);
		} else {
			LOG.info("********not uploaded to ftp************");
		}

		/*
		 * File inputlocation = new
		 * File(properties.getProperty("AvList.folder")); File[] files =
		 * inputlocation.listFiles();
		 * 
		 * 
		 * for (File deletedFilefile : files) { try{ deletedFilefile.delete();
		 * }catch(Exception e){ deletedFilefile.delete(); }
		 * log.info("File removed from server"); }
		 */
	}

	public File createWorkbook(Set<String> finalAvsList) {

		String outputLocation = ftpFileUploadService.createLocation();
		FileOutputStream fos = null;
		File outputFile = null;
		workbook = new XSSFWorkbook();
		outputFile = new File(outputLocation);
		if (!outputFile.exists()) {
			outputFile.mkdirs();
		}
		try {
			fos = new FileOutputStream(outputLocation + File.separator
					+ "Techouts-AV List For AV-PN Kinaxis BOM.xlsx");
		} catch (Exception e) {
			e.printStackTrace();

		}
		XSSFSheet spreadsheet = workbook.createSheet("AvsList");
		// Create row object
		XSSFRow row;
		// This data needs to be written
		int rowid = 0;
		int cellid = 0;
		for (String av : finalAvsList) {
			row = spreadsheet.createRow(rowid);
			cellid = 0;
			row.createCell(cellid++).setCellValue(av);
			rowid++;
		}
		try {
			workbook.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return outputFile;
	}

	public List<String> downloadOldFile() {
		FTPClient ftpClient = new FTPClient();
		FileOutputStream fos = null;
		boolean download = false;
		boolean status;

		String location = "";
		location = ftpFileUploadService.createLocation();
		try {
			if (ftpFileUploadService.getLoginFtp(ftpClient)) {
				for (FTPFile mainDirs : ftpClient.listDirectories()) {

					if (mainDirs.isDirectory()
							&& mainDirs.getName().equals("BINOY")) {
						for (FTPFile sub : ftpClient.listDirectories("/"
								+ mainDirs.getName())) {
							if (sub.isDirectory()
									&& sub.getName().equals("BOM")) {
								ftpClient.changeWorkingDirectory(mainDirs
										.getName() + "/" + sub.getName());

								for (FTPFile file : ftpClient.listFiles()) {
									if (file.getName()
											.equalsIgnoreCase(
													"Techouts-AV List For AV-PN Kinaxis BOM.xlsx")) {
										fos = new FileOutputStream(
												location
														+ File.separator
														+ "Techouts-AV List For AV-PN Kinaxis BOM-old.xlsx");
										download = ftpClient
												.retrieveFile(
														"Techouts-AV List For AV-PN Kinaxis BOM.xlsx",
														fos);
									}
								}
							}

						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (download) {
			LOG.info("******Old file downloaded*******");
		} else {
			LOG.info("******Old file not downloaded again trying*******");
			downloadOldFile();
		}
		location = ftpFileUploadService.createLocation();
		List<String> oldAvs = new ArrayList<String>();
		try {
			FileInputStream inputStream = new FileInputStream(new File(location
					+ File.separator
					+ "Techouts-AV List For AV-PN Kinaxis BOM-old.xlsx"));

			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();

			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					if (!cell.getStringCellValue().isEmpty()) {
						oldAvs.add(cell.getStringCellValue());
					}
				}
			}
			inputStream.close();

			ftpClient.logout();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return oldAvs;
	}

}
