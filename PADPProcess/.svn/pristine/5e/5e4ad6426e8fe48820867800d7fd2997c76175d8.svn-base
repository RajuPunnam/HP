package com.techouts.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.dao.SkuAvilWorkbookDao;
import com.techouts.pojo.AvAvbail;
import com.techouts.pojo.OverAllSkusAvailability;

/**
 * class for sku avil workbook data
 * 
 * @author p.raju
 *
 */
@Service
public class SkuAvailWorkbook {
	@Resource(name = "myProps")
	private Properties properties;
	@Autowired
	private SkuAvilWorkbookDao SkuAvilWorkbookDao;
	XSSFWorkbook workbook = null;
	private static final String FILE_FORMAT = String.format("%s.%s",
			"ALLSKU_AND_AVAVBLE", "xlsx");
/**
 * method for generating Skuavilworkbook sheet
 * @param overAllSkusAvailability
 */
	public void createSkuAvailWorkBookSheet(
			List<OverAllSkusAvailability> overAllSkusAvailability) {
		XSSFSheet spreadsheet = workbook.createSheet("skuAvailability");
		// Create row object
		XSSFRow row;
		// This data needs to be written
		int rowid = 0;
		int cellid = 0;
		row = spreadsheet.createRow(rowid);
		row.createCell(cellid++).setCellValue("SKU");
		row.createCell(cellid++).setCellValue("Av Availability");
		row.createCell(cellid++).setCellValue("Description");

		rowid = 1;
		for (OverAllSkusAvailability skuavail : overAllSkusAvailability) {
			row = spreadsheet.createRow(rowid++);
			cellid = 0;
			row.createCell(cellid++).setCellValue(skuavail.getSkuId());
			row.createCell(cellid++).setCellValue(skuavail.getAvailability());
			row.createCell(cellid++).setCellValue(skuavail.getSkuDesc());
		}
	}
/**
 * method for generating avavil workbook sheet
 * @param avAvlList
 */
	public void creatAvAvailWorkBookSheet(List<AvAvbail> avAvlList) {
		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet("AvAvailable");
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		XSSFRow row;
		// This data needs to be written
		int rowid = 0;
		int cellid = 0;
		row = spreadsheet.createRow(rowid);
		row.createCell(cellid++).setCellValue("Av");
		row.createCell(cellid++).setCellValue("Av Availability");
		row.createCell(cellid++).setCellValue("Commodity Description");

		rowid = 1;
		for (AvAvbail avAvailability : avAvlList) {
			row = spreadsheet.createRow(rowid++);
			cellid = 0;
			row.createCell(cellid++).setCellValue(avAvailability.getAvId());

			if (avAvailability.getAvbail() == -1) {
				row.createCell(cellid++).setCellValue(0);
			} else {
				row.createCell(cellid++).setCellValue(
						avAvailability.getAvbail());
			}
			row.createCell(cellid++).setCellValue(
					avAvailability.getComodityDesc());
		}
	}

	public synchronized File generateSkuAvAvailWorkBook()
			throws FileNotFoundException {
		String outputLocation = "";
		String operSys = System.getProperty("os.name").toLowerCase();
		if (operSys.contains("win")) {
			outputLocation = properties.getProperty("drive.name")
					+ File.separator + properties.getProperty("main.folder")
					+ File.separator
					+ properties.getProperty("skuavail.folder");

		} else if (operSys.contains("nix") || operSys.contains("nux")
				|| operSys.contains("aix")) {
			outputLocation = properties.getProperty("linux.os.name.drive")
					+ File.separator + properties.getProperty("main.folder")
					+ File.separator
					+ properties.getProperty("skuavail.folder");

		}
		File outputFile = null;
		workbook = new XSSFWorkbook();
		outputFile = new File(outputLocation);
		if (!outputFile.exists()) {
			outputFile.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(outputLocation
				+ File.separator + FILE_FORMAT);
		List<AvAvbail> avAvlList = SkuAvilWorkbookDao.getavAvilableData();
		List<OverAllSkusAvailability> overAllSkusAvailability = SkuAvilWorkbookDao
				.getoverllSkuAvialbilityData();
		createSkuAvailWorkBookSheet(overAllSkusAvailability);
		creatAvAvailWorkBookSheet(avAvlList);
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
}
