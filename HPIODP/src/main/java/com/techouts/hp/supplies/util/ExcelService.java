/**
 * 
 */
package com.techouts.hp.supplies.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author raju.p
 *
 */
@Service
public class ExcelService {

	private static final SimpleDateFormat RDF = new SimpleDateFormat("MM/dd/yyyy");
	private static final DateFormat ARDF = new SimpleDateFormat("MM-dd-yyyy");

	/**
	 * method to get data from cell based on celltype
	 * 
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
						cellData = RDF.format(cell.getDateCellValue()) + "";
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
					cellData = RDF.format(cell.getDateCellValue()) + "";
				} else {
					cellData = cell.getNumericCellValue() + "";
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
	 * @return
	 */
	public Map<String, Integer> findHeader(Row row, Set<String> headerList) {
		Map<String, Integer> headerMap = new LinkedHashMap<String, Integer>();
		for (int i = 0; i <= row.getPhysicalNumberOfCells(); i++) {
			Cell cell = row.getCell(i);
			if (!(row.getCell(i, Row.RETURN_BLANK_AS_NULL) == null)) {
				String columnName = getCellData(cell);
				if (!StringUtils.isEmpty(columnName)) {
					if (headerList.contains(columnName)
							&& !headerMap.containsKey(columnName)) {
						headerMap.put(columnName, cell.getColumnIndex());
					}
				}
			}
		}
		return headerMap;
	}

	public boolean findEmptyRow(Row row)
	{
		boolean emptyRow = false;
		for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
			if (row.getCell(i, Row.RETURN_BLANK_AS_NULL) == null) {
				emptyRow = true;
				continue;
			} else {
				emptyRow = false;
				break;
			}
		}
		return emptyRow;
	}

	public Object getTypeValue(Class<?> type, Cell cell) {
		Object typedValue = null;
		if (type == int.class) {
			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) 
			{
				typedValue = cell.getNumericCellValue();
			} 
			else {
				typedValue = 0;
			}
		} else if (type == double.class)
		{
			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) 
			{
				typedValue = cell.getNumericCellValue();
			} else if (Cell.CELL_TYPE_FORMULA == cell.getCellType()) {
				typedValue = cell.getNumericCellValue();
			} else {
				typedValue = 0;
			}

		} else if (type == boolean.class) {
			if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
				typedValue = cell.getBooleanCellValue();
			} else {
				typedValue = false;
			}

		} else if (type == String.class) {
			if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
				typedValue = cell.getStringCellValue();
			} else {
				typedValue = "";
			}
		}
		else if(type==Date.class)
		{
			if (DateUtil.isCellDateFormatted(cell)) 
			{
				typedValue = cell.getDateCellValue();
			}
		}
		return typedValue;
	}
}
