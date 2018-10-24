package com.techouts.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

public class CommonFeatures {

	private static final Logger LOG = Logger.getLogger(CommonFeatures.class);
	public static List<String> conditionIntValues = new ArrayList<String>();
	public static List<String> conditionStringValues = new ArrayList<String>();
	public static List<String> conditionDateValues = new ArrayList<String>();
	public static HashMap<String, String> conditionSymbols = new HashMap<String, String>();

	static {

		conditionDateValues.add("is greater");
		conditionDateValues.add("is equal");
		conditionDateValues.add("is greater or equal");
		conditionDateValues.add("is less");
		conditionDateValues.add("is less or equal");
		conditionDateValues.add("is empty");
		conditionDateValues.add("is not empty");

		conditionStringValues.add("contains");
		conditionStringValues.add("is equal");
		conditionStringValues.add("is like");
		conditionStringValues.add("starts with");
		conditionStringValues.add("ends with");
		conditionStringValues.add("is empty");
		conditionStringValues.add("is not empty");

		conditionIntValues = new ArrayList<String>(conditionDateValues);

		// symbols
		conditionSymbols.put("is greater", ">");
		conditionSymbols.put("is equal", "=");
		conditionSymbols.put("is greater or equal", ">=");
		conditionSymbols.put("is less", "<");
		conditionSymbols.put("is less or equal", "<=");
		conditionSymbols.put("is empty", "IS NULL");
		conditionSymbols.put("is not empty", "IS NOT NULL");

		conditionSymbols.put("contains", " LIKE ");
		conditionSymbols.put("is like", " LIKE ");
		conditionSymbols.put("starts with", "LIKE ");
		conditionSymbols.put("ends with", " LIKE ");

	}

	public static String getFormattedDate(String date) {

		if (date.length() != 10)
			return "";

		String str[] = date.split("/");
		String retValue = "";

		if (str.length > 0) {
			retValue = str[2] + "-" + str[1] + "-" + str[0];
		}
		return retValue;
	}

	public static Field[] getFields(String className) {

		Field[] fields = {};
		// List<String> list = new ArrayList<String>();
		try {
			Class cls = Class.forName(className);
			fields = cls.getDeclaredFields();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		/*
		 * for(Field f : fields ){ list.add(f.getName());
		 * //LOG.info("Fields is :"+f.getName()+"\t type is :"+f.getType()); }
		 */

		return fields;
	}

	public static String getFieldType(Field[] fields, String fieldName) {
		String type = "";
		for (Field f : fields) {
			if (f.getName().equals(fieldName))
				type = f.getGenericType().toString();
		}

		return type;
	}

	public static String getFieldTypeName(String name) {
		return name.substring(name.lastIndexOf('.') + 1);
	}

	// to upload the file
	public static void upload(String fileName, InputStream in) {

		LOG.info("FileUploadView.upload()");

		// if(file != null) {
		// LOG.info("FileUploadView.upload() if cond :"+file.getFileName());
		FacesMessage message = new FacesMessage("Succesful", fileName
				+ " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);

		// String destination =
		// "I:/XPInstallations/ERP/HMS_Workspace/HMS_iHub/WebContent/resources/images/";
		String myDest = "";
		String path = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/WebContent/resources/images");
		String[] paths = path.split(".metadata");
		if (paths.length > 1) {
			myDest = paths[0];
			String[] newPaths = paths[1].split("wtpwebapps");
			myDest += newPaths[1].substring(1) + "\\";
		}
		String destination = myDest;

		try {
			// in = file.getInputstream();
			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(destination
					+ fileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			LOG.info("New file created!");
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}

	}

	// Schedule methods
	public static String timeToString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		String strDate = dateFormat.format(date);
		return strDate;
	}

	public static Date stringToTime(String dateInString) {

		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		Date date = null;
		try {
			date = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;

	}
}
