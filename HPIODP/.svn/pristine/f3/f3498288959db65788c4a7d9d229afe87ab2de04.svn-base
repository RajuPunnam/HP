package com.inventory.utill;

import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelSupport {

	public static String getCellData(Cell cell) {

		SimpleDateFormat requiredDateFormat = new SimpleDateFormat("MM-dd-yyyy");
		String cellData = null;
		try {
			if (!(cell == null)) {
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_BOOLEAN:
					cellData = cell.getBooleanCellValue() + "";
					break;
				case Cell.CELL_TYPE_FORMULA:

					switch (cell.getCachedFormulaResultType()) {
					case Cell.CELL_TYPE_NUMERIC:
						if (org.apache.poi.ss.usermodel.DateUtil
								.isCellDateFormatted(cell)) {
							cellData = requiredDateFormat.format(cell
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
					if (org.apache.poi.ss.usermodel.DateUtil
							.isCellDateFormatted(cell)) {
						cellData = requiredDateFormat.format(cell
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
			} else {
				cellData = "";
			}
		} catch (Exception e) {

		}
		return cellData;
	}
	
	
	public static String[] getRowData(Row column) {
		String columns[] = new String[column.getLastCellNum()];
		for (int i = 0; i < column.getLastCellNum(); i++) {
			String value = ExcelSupport.getCellData(column.getCell(i));
			if (value != null && !value.isEmpty()) {
				columns[i] = value;
			}
		}
		return columns;
	}
	
	public static  String  getNumericlData(Cell cellData) {
		String cell = null;
		if(cellData!=null){
            switch (cellData.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				cell = (int)cellData.getNumericCellValue()+"";
				if(cell.contains(",") || cell.contains("$") || cell.contains("-") ){
					cell = cell.replaceAll("$,-", "");
					cell = cell.replace(",","");
				}
				break;

				
			case Cell.CELL_TYPE_STRING:	
				
				cell = cellData.getStringCellValue();
				if(cell.contains(",") || cell.contains("$") || cell.contains("-") ){
					cell = cell.replaceAll("$,-", "");
					cell = cell.replace(",","");
				}
				
			default:
				break;
			}			
		}else{
			cell ="";
		}
		return cell;
	}

	
	
}
