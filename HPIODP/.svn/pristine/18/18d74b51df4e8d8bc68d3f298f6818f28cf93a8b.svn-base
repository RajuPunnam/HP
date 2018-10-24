package com.mongo.mysql.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.inventory.utill.DateUtil;
import com.inventory.utill.DoubleUtill;
import com.io.common.Resources;
import com.mongo.mysql.dao.DataTransferDao;
import com.mongo.mysql.pojos.Demand_30Days;
import com.mongo.mysql.pojos.FTPFile_Status_Sosa_Shortage;
import com.mongo.mysql.pojos.Resumo;
import com.mongo.mysql.pojos.Shortage_detail_report;

@Service
public class ExceltoPojoService {
	private static Logger LOG=Logger.getLogger(ExceltoPojoService.class);
	@Autowired
	private  DataTransferDao dao;
	@Autowired
	private FTPService ftpService;
	
	public String processExcel(File file, Map<String, String> map){
		 String result = null;
	        try
	        {   String name=file.getAbsolutePath();
	            LOG.info(name);
	        	FileInputStream fileStream = new FileInputStream(file);
	            XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
	            XSSFSheet sheet;
	            String date=null;
	            try{
	            sheet = workbook.getSheet("Resumo Item"); 
	            LOG.info(sheet.getSheetName());
	            int index=name.indexOf("-");
	            String filedate=name.substring(index+1,index+10).trim();
	            LOG.info("Resumo Item------------------"+filedate);
	            String[] dateArray=filedate.split("\\.");
	            LOG.info("---------Splitted date size----------"+dateArray.length);
	            date=dateArray[1]+"-"+dateArray[0]+"-20"+dateArray[2];
	            
	            result=loadResumosToList(sheet,map,date,new Resumo());
	           
	            }catch(NullPointerException e){
	            	
	            sheet=workbook.getSheet("Shortage Report - Detailed");
	            
	            LOG.info(sheet.getSheetName());
	            String filedate=name.substring(name.indexOf(".xlsx")-6,name.indexOf(".xlsx")).trim();
	            LOG.info("Shortage Report - Detailed------------------"+filedate);
	            LOG.info(filedate.substring(2,4)+" "+filedate.substring(4));
	            
	            Calendar gregorianCalendar = new GregorianCalendar();
	            gregorianCalendar.set(Calendar.YEAR , Integer.parseInt(filedate.substring(2,4)));
	            gregorianCalendar.set(Calendar.WEEK_OF_YEAR , Integer.parseInt(filedate.substring(4)));
	            LOG.info("-----------gregorianCalendar.getTime(); ------"+gregorianCalendar.getTime()); 
	            
	            
	            date=gregorianCalendar.get(Calendar.MONTH) + 1+"-"+gregorianCalendar.get(Calendar.DAY_OF_MONTH)
	            		+"-20"+gregorianCalendar.get(Calendar.YEAR);
	            
	           
	            result=loadResumosToList(sheet,map,date,new Shortage_detail_report());
	            
	            }
	            
	        }
	        catch(FileNotFoundException e)
	        {
	         e.printStackTrace();
	        }
	        catch(IOException e)
	        {
	           e.printStackTrace();
	        } catch (SecurityException e) {
	            e.printStackTrace();
	        } catch (InstantiationException e) {
	            e.printStackTrace();
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	        } catch (NoSuchFieldException e) {
	            e.printStackTrace();
	        }
	        
			return result;        
	        

	}
	
	
	public String loadResumosToList(XSSFSheet sheet,
      Map<String, String> map, String date, Object obj) throws InstantiationException, 
            IllegalAccessException, SecurityException, NoSuchFieldException{
		List<?> list = null;
		String result="";
		
		LOG.info("sheet.getLastRowNum() "+sheet.getLastRowNum());
        Iterator<Row> rowIterator = sheet.iterator();
        
        if(obj instanceof Resumo || obj instanceof Demand_30Days){
        	 Row column = sheet.getRow(1);
        	 String columnNames[] = getColumnNames(column);
             for(String str:columnNames){
             	System.out.print("\""+str+"\",");
             }
             System.out.println("");
             result=getResumoData(rowIterator,columnNames,map,date);

        	LOG.info("obj instanceof Resumo || obj instanceof Demand_30Days "+result);
        }else{
        	list=getShortageHeaders(rowIterator,new Shortage_detail_report(),map,date);
        	LOG.info("--------------Shortage_detail_report list size is "+list.size());
        	result=dao.saveDataToMysql(list, "shortage_detail_report");
        	LOG.info("----------------shortage_detail_report status ---------------------"+result);
        }
		return result;
        
    }        
	
	public String getResumoData(Iterator<Row> rowIterator,String columnNames[],Map<String, String> map,String date) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException{
		Resumo resumo=new Resumo();
		Demand_30Days demand_30Days=new Demand_30Days();
		
		List<Resumo> list=new ArrayList<Resumo>();
		List<Demand_30Days> listDemand_30Days=new ArrayList<Demand_30Days>();
		
		Map<String,String> demandMap=new LinkedHashMap<String, String>();
		demandMap.put("ITEM","item");
		demandMap.put("DEMANDA_30_DAYS","demand30Days");
	
		while(rowIterator.hasNext()){
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
          
            Resumo newRecord=resumo.getClass().newInstance(); //-------------check--------------
            Demand_30Days newRecord_Demand=demand_30Days.getClass().newInstance();
            
            // Skip First row coz that is column names
            if(row.getRowNum()>1){           
                int i=0;
                while (cellIterator.hasNext() && i<columnNames.length) {
                    Cell cell = cellIterator.next();
                    String columnName = columnNames[i++];
                    
                    try{
                    	
                    Field f1 = resumo.getClass().getDeclaredField(map.get(columnName.trim()));//-------------check----------
                    f1.setAccessible(true);
                    f1.set(newRecord, getTypeValue(f1.getType(),cell));
                    
                    Field f11 = demand_30Days.getClass().getDeclaredField(demandMap.get(columnName.trim()));//-------------check----------
                    f11.setAccessible(true);
                    f11.set(newRecord_Demand, getTypeValue(f11.getType(),cell));
                    
                    }catch(NullPointerException e)
                    {
                    	continue;
                    }
                        
                }
                newRecord.setFileDate(DateUtil.getDateForAnyFormate(date));
                newRecord_Demand.setFileDate(DateUtil.getDateForAnyFormate(date));
         
                list.add(newRecord);  
                listDemand_30Days.add(newRecord_Demand);
               
            }
           
        }//row iterating ending.................
		LOG.info("Resumo list size is "+list.size());
		String result= dao.saveDataToMysql(list, "resume_item_tbl");
    	LOG.info("-------------Resumo Data status----------"+result);
    	
    	LOG.info("Demand_30Days list size is "+listDemand_30Days.size());
    	String res= dao.saveDataToMysql(listDemand_30Days, "demand_30days_tbl");
 	    LOG.info("-------------Demand_30Days Data status----------"+result);
    	
        return result+"<----Resumo Status && Demand_30Days status---->"+res;
	}
	
	public List<Demand_30Days> getDemand_30DaysData(Iterator<Row> rowIterator,String columnNames[],Demand_30Days resumo,Map<String, String> map,String date) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException{
		List<Demand_30Days> list=new ArrayList<Demand_30Days>();
		
        while(rowIterator.hasNext()){
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            Demand_30Days newRecord=resumo.getClass().newInstance(); 
            // Skip First row coz that is column names
            if(row.getRowNum()>1){           
                int i=0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String columnName = columnNames[i++];
                    
                    try{
                    	/*LOG.info(columnName.trim()+" "+map.get(columnName.trim())+",");	*/
                    Field f1 = resumo.getClass().getDeclaredField(map.get(columnName.trim()));
                    f1.setAccessible(true);
                    f1.set(newRecord, getTypeValue(f1.getType(),cell));
                    
                    }catch(NullPointerException e)
                    {
                    	continue;
                    }
                        
                }
                newRecord.setFileDate(DateUtil.getDateForAnyFormate(date));
               // System.out.println(newRecord.toString());
                list.add(newRecord);    
            }
        }//row iterating ending.................
        return list;
	}
	
	
	/**
	 * for getting column names of Resumo Sheet
	 * @param column
	 * @return
	 */
	 public String[] getColumnNames(Row column) {
	        String columns[] = new String[column.getPhysicalNumberOfCells()];
	        Iterator<Cell> cellIterator = column.cellIterator();
	        int i = 0;
	        while (cellIterator.hasNext()) {
	            Cell cell = cellIterator.next();
	            columns[i++] = cell.getStringCellValue();
	        }        
	        return columns;
	    }  
	 
	 
	 public List<?> getShortageHeaders(Iterator<Row> rowIterator, Shortage_detail_report shortage_detail_report, Map<String, String> map, String date)
			 throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException{ 
		 
			Map<Integer,String> columnMap=new LinkedHashMap<Integer, String>();
			List<Shortage_detail_report> list=new ArrayList<Shortage_detail_report>();
			String[] columnNames = null;
			while(rowIterator.hasNext()){
				Row row=rowIterator.next();
				if(row.getRowNum()<=2){
				Iterator<Cell> cellIterator=row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell=cellIterator.next();
					int index=cell.getColumnIndex();
				   if(columnMap.containsKey(index)) {
					   columnMap.put(index,columnMap.get(index)+""+cell.getStringCellValue());
				   }else{
					   columnMap.put(index, cell.getStringCellValue());
				   }
				}
				}//header condition............
				else {
					if(row.getRowNum()==3){
						String check="";
						String test="";
						int i = 1;
						for(Map.Entry<Integer, String> entry:columnMap.entrySet()){
							if(entry.getValue().contains("Past")){
							check="true";
							LOG.info(entry.getKey()+" ;"+entry.getValue());
							continue;
							}if(entry.getValue().equalsIgnoreCase("future")){
								test="true";
								check="false";
							}
							if(check.equals("true") && test.equals("")){
								entry.setValue("Projected Date"+i++);
							}
							if(entry.getValue().equals("")){
								entry.setValue("Shortage_Status");
							}
							LOG.info(entry.getKey()+" ;"+entry.getValue());
						}
						columnNames = columnMap.values().toArray(new String[columnMap.size()]);
						LOG.info("------------shortage_detail_report columns name---------"+columnNames.length);
					}//row no three ending............
					
					Iterator<Cell> cellIterator = row.cellIterator();
		            Shortage_detail_report newRecord=shortage_detail_report.getClass().newInstance(); //-------------check--------------
		             
	                int i=0;
	                while (cellIterator.hasNext()) {
	                    Cell cell = cellIterator.next();
	                    String columnName = columnNames[i++];
	                    
	                    try{	
	                    Field f1 = shortage_detail_report.getClass().getDeclaredField(map.get(columnName.trim()).trim());//-------------check----------
	                    f1.setAccessible(true);
	                    f1.set(newRecord, getTypeValue(f1.getType(),cell));
	                    
	                    }catch(NullPointerException e)
	                    {
	                    	continue;
	                    }
	                        
	                }
	                newRecord.setFileDate(DateUtil.getDateForAnyFormate(date));
	                System.out.println(newRecord.toString());
	                list.add(newRecord);  
	             	
				}
			}
			return list;
		}
	 
	 
	 /**
	     * Method to get typed value
	     * @param type
	     * @param value 
	     * @return
	     */
	   public  Object getTypeValue(Class<?> type, Cell cell) {
	        Object typedValue = null;
	        String cellValue=getCellData(cell);
	        if(type == int.class){
	        	typedValue=DoubleUtill.getIntValue(cellValue);
	            //typedValue = (int) cell.getNumericCellValue();
	        } else if(type == double.class){
	        	typedValue=DoubleUtill.getValue(cellValue);
	            //typedValue = cell.getNumericCellValue();
	        } else if(type == boolean.class){
	        	typedValue=Boolean.parseBoolean(cellValue);
	           // typedValue = cell.getBooleanCellValue();
	        } else if(type == String.class){
	        	typedValue=cellValue;
	            //typedValue = cell.getStringCellValue();
	        }else if(type == Date.class){
	        	typedValue=DateUtil.getDateForAnyFormate(cellValue);
	            //typedValue = cell.getStringCellValue();
	        }
	        return typedValue;
	    }   
	   
	   public String getCellData(Cell cell) {
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
		       if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
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
		      if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
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
	   
	       public void save_PojoData(FTPFile_Status_Sosa_Shortage pojo, String name){
		   dao.save_DataToMysql(pojo,name);
	   }


		public List<String> getDistinct_FileNames(String entityName, String sqlFieldName) {
			return dao.getUniqueDatesFromMySql(entityName, sqlFieldName);
		}
}
