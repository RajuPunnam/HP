package com.techouts.hp.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.hp.dao.impl.StatusDaoImpl;
import com.techouts.hp.pojo.FileInfo;
import com.techouts.hp.util.OSUtil;

@Service
public class StatusService {
	@Autowired
	private StatusDaoImpl statusDaoImpl;
	@Autowired
	private OSUtil osUtil;
	@Resource(name = "myProps")
	private Properties properties;
	private static final DateFormat SDF = new SimpleDateFormat("MM-dd-yyyy");
/**
 * Generates current day uploaded files workbook
 * @return filelocation
 * @throws IOException
 * @throws MessagingException
 */
	public String getCurentDayUploadedDataFile() throws IOException,
			MessagingException {
		String currentDate = SDF.format(new Date());
		String fileFormat = String.format("%s_%s.%s", "HPRAWDP",currentDate, "xlsx");
		Cell cell;
		List<FileInfo> filesList = statusDaoImpl.getUplodedFilesList();
		XSSFWorkbook workbook = new XSSFWorkbook();
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(
				"mm/DD/yyyy hh:mm:ss"));
		XSSFSheet sheet = workbook.createSheet(currentDate);
		XSSFRow row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("FTP Location");
		cell = row.createCell(1);
		cell.setCellValue("Source Folder");
		cell = row.createCell(2);
		cell.setCellValue("File Name");
		cell = row.createCell(3);
		cell.setCellValue("UpLoad Status");
		cell = row.createCell(4);
		cell.setCellValue("File Received Date");
		cell = row.createCell(5);
		cell.setCellValue("File UpLoad Date");
		cell = row.createCell(6);
		cell.setCellValue("No Of Records");
		for (int i = 0; i < filesList.size(); i++) {
			row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(filesList.get(i).getFtpLocation());
			row.createCell(1).setCellValue(filesList.get(i).getSourceFolder());
			row.createCell(2).setCellValue(filesList.get(i).getFileName());
			row.createCell(3).setCellValue(filesList.get(i).isUploadStatus());
			row.createCell(4).setCellValue(
					SDF.format(filesList.get(i).getFileReceivedDate()));
			row.createCell(5).setCellValue(
					SDF.format(filesList.get(i).getFileUploadedDate()));
			row.createCell(6).setCellValue(filesList.get(i).getNoOfRecords());
		}
		String location = osUtil.downloadDirectory() + File.separator
				+ properties.getProperty("main.folder");
		File filePath = new File(location);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		workbook.write(new FileOutputStream(filePath + File.separator
				+ fileFormat));
		return filePath + File.separator + fileFormat;
	}
/**
 * gets the currentday uploaded files list
 * @return statusMap
 */
	public Map<String, FileInfo> getCurrentDayUploadedFilesList() {
		List<FileInfo> filesList = statusDaoImpl.getUplodedFilesList();
		Map<String, FileInfo> statusMap = new TreeMap<String, FileInfo>();
		for (FileInfo fileStatus : filesList) {
			if(fileStatus!=null && fileStatus.getFileName()!=null)
			{
				statusMap.put(fileStatus.getFileName(), fileStatus);
			}
		}
		return statusMap;
	}
}
