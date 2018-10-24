package com.techouts.hp.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Service;

import com.aspose.cells.CellValueType;

/**
 * ExcelUtil class to handle workbook cells based on celltype
 * @author raju.p
 *
 */
@Service
public class ExcelUtil {
	private static final SimpleDateFormat RDF = new SimpleDateFormat("MM/dd/yyyy");
	private static final DateFormat ARDF = new SimpleDateFormat("MM-dd-yyyy");
	/**
	 * method to get data from cell based on celltype
	 * @param cell
	 * @return cellData
	 */
	public String getCellData(Cell cell) {
		if (cell == null) {
			return "";
		}
		
		String cellData = null;
		try {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				cellData = cell.getBooleanCellValue() + "";
				break;
			case Cell.CELL_TYPE_FORMULA:

				switch (cell.getCachedFormulaResultType()) {
				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) {
						cellData = RDF.format(cell
								.getDateCellValue()) + "";
					} else {
						cellData = cell.getNumericCellValue() + "";
					}
					break;
				case Cell.CELL_TYPE_STRING:
					cellData = cell.getStringCellValue() + "";
					break;
				case Cell.CELL_TYPE_ERROR:
					cellData = cell.getErrorCellValue() + "";
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					cellData = cell.getBooleanCellValue() + "";
					break;
				default:
					cellData = cell.getStringCellValue() + "";
					break;
				}
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					cellData = RDF.format(cell
							.getDateCellValue()) + "";
				} else {

					cellData = (int) cell.getNumericCellValue() + "";
				}
				break;
			case Cell.CELL_TYPE_STRING:
				String str = cell.getStringCellValue();
				String st1 = new String();

				if (str.contains("\"")) {
					String st[] = str.split("\"");
					for (int k = 0; k < st.length; k++) {
						st1 = st1 + st[k];
					}
					cellData = st1 + "";
				} else if (str.toString().equals("-")) {
					cellData = "";
				}

				else {
					cellData = cell.getStringCellValue() + "";
				}
				break;
			case Cell.CELL_TYPE_ERROR:
				if (cell.getErrorCellValue() == 42) {
					cellData = "";
				} else {
					cellData = cell.getErrorCellValue() + "";
				}
				break;
			case Cell.CELL_TYPE_BLANK:
				cellData = "";
				break;
			}
		} catch (Exception e) {

		}
		return cellData.trim();
	}
/**
 * Method for getting celldata based on celltype
 * @param cell
 * @return value
 */
	public String getAsposeWorkbookCellData(com.aspose.cells.Cell cell) {
		
		String value = null;
		switch (cell.getType()) {
		case CellValueType.IS_STRING:
			value = cell.getStringValue();
			break;
		case CellValueType.IS_NUMERIC:
			value = cell.getValue() + "";
			break;
		case CellValueType.IS_BOOL:
			value = cell.getBoolValue() + "";
			break;
		case CellValueType.IS_DATE_TIME:
			value = ARDF.format(cell.getDateTimeValue().toDate())
					+ "";
			break;
		case CellValueType.IS_ERROR:
			value = cell.getValue() + "";
			break;
		case CellValueType.IS_NULL:
			value = "";
			break;
		case CellValueType.IS_UNKNOWN:
			value = "";
			break;
		}
		return value.trim();
	}
}
