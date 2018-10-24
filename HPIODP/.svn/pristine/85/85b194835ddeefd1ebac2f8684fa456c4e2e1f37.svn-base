package com.ftp.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import jdk.nashorn.internal.ir.annotations.Reference;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class FTPSupport {
	
	@Resource(name ="myProps")
	private static Properties properties;
	
	private static Logger log = Logger.getLogger(FTPSupport.class);
	
	public static String createLocation(){
		String outputLocation = "";
		String operSys = System.getProperty("os.name").toLowerCase();
		if (operSys.contains("win")) {
			outputLocation = "D:"
					+ File.separator + "avlist";
					
		} else if (operSys.contains("nix") || operSys.contains("nux")
				|| operSys.contains("aix")) {
			outputLocation = "/home/tech/ec2-user/BINOY/PRINTERS/Open Order";
		}

		File file =  new File(outputLocation);
		if(!file.exists()){
			log.info("creating Directory");
		boolean created =	file.mkdirs();
		log.info("Directory Created "+created);
		}else{
			log.info("Directory Exist");
		}
		return outputLocation;
	}
	
	public  static String  downLoadFileFromFTP(FTPFile file,List<String> directoryList,FTPClient client) {
		
		boolean downLoadStatus = false;
		File downloadFile = null;
		String ftpPath="";
		for (String folderName : directoryList) {
			ftpPath = ftpPath.concat(File.separator+folderName);
		}
		String outputLocation = "";
		String operSys = System.getProperty("os.name").toLowerCase();
		if (operSys.contains("win")) {
			outputLocation = "D:"
					+ File.separator + ftpPath;
					
		} else if (operSys.contains("nix") || operSys.contains("nux")
				|| operSys.contains("aix")) {
			outputLocation ="/home/tech/ec2-user"+File.separator+ftpPath;
		}

		downloadFile = new File(outputLocation);
		if (!downloadFile.exists())
		{
				downloadFile.mkdirs();
		}else{
			System.out.println("directory exist");
			
		}
		
			System.out.println("file downloadin");
		
			try {
				client.retrieveFile(file.getName(), new FileOutputStream(outputLocation+File.separator+file.getName()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return outputLocation;
		
	}
	
public static boolean getftpConnection(FTPClient ftpClient){
		
		boolean connectionStatus = false;
		try {
			log.info("Ftp connecting ........");
			final int port = 21;
			ftpClient.connect("ftpam.flextronics.com", port);
			connectionStatus = ftpClient.login("fhpcompu","h5w9hB(P");
			ftpClient.enterLocalPassiveMode();
			ftpClient.setBufferSize(1024 * 1024);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (IOException exception)
		{
			log.error(exception.getMessage());
		}
		return connectionStatus;
	}


}
