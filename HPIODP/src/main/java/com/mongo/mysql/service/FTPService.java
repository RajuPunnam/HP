package com.mongo.mysql.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongo.mysql.pojos.FTPFile_Status_Sosa_Shortage;

@Service
public class FTPService {
	@Autowired
	private ExceltoPojoService exceltoPojoService;
	final Logger LOGGER = Logger.getLogger(FTPService.class);

	public String getDownloadFtpFiles(int i) throws ParseException, IOException
	{
		List<String> filesList=new ArrayList<String>();
		String finalpath="D://";
		FTPClient ftpClient = new FTPClient();
		
			if (getFtpConnection(ftpClient))
			{
				LOGGER.info("Ftp connected.");
				try
				{
					for (FTPFile mainDir : ftpClient.listDirectories())
					{
						if (mainDir.isDirectory() && mainDir.getName().equals("BINOY"))
						{
							filesList=exceltoPojoService.getDistinct_FileNames("FTPFile_Status_Sosa_Shortage","fileName");
							finalpath=finalpath.concat(mainDir.getName()+File.separator);
							LOGGER.info(mainDir.getName());
							for (FTPFile sub : ftpClient.listDirectories("/" + mainDir.getName()))
							{
								System.out.println(sub.getName());
								if (sub.isDirectory() && sub.getName().equals("Sosa"))
								{
									ftpClient.changeWorkingDirectory("/"+mainDir.getName() + "/"+sub.getName());
									List<String> list=new ArrayList<String>();
									list.add(mainDir.getName());
									list.add(sub.getName());
									
									    
										for (FTPFile file : ftpClient.listFiles())
										{
											
											if (file.isFile() &&  file.getName().startsWith("DOS") && file.getName().endsWith(".xlsx") && !filesList.contains(file.getName()))
											{
												Calendar cal=file.getTimestamp();
												LOGGER.info("File Timestamp val is "+cal.getTime());
												for (int fileDownloadingTries = 0; fileDownloadingTries <= 10; fileDownloadingTries++)
													{
													if(getDownloadFileByFile(file, ftpClient, list))
													{
														FTPFile_Status_Sosa_Shortage pojo=new FTPFile_Status_Sosa_Shortage();
														pojo.setFileName(file.getName());
														pojo.setUploadedDate(cal.getTime());
														exceltoPojoService.save_PojoData(pojo, "file_status_sosa_shortage_tbl");
														LOGGER.info("saved download file details to db");
														break;
													}
										            }
									  	 }
										
									}
		                      }
								else
									if(sub.isDirectory() && sub.getName().equals("HP Computing - Shortage"))
									{
										ftpClient.changeWorkingDirectory("/"+mainDir.getName() + "/"+sub.getName());
										List<String> list=new ArrayList<String>();
										list.add(mainDir.getName());
										list.add(sub.getName());
										    
											for (FTPFile file : ftpClient.listFiles())
											{  
												if (file.isFile() &&  file.getName().startsWith("Shortage") && file.getName().endsWith(".xlsx") && !filesList.contains(file.getName()))
												{
													Calendar cal=file.getTimestamp();
													LOGGER.info("File Timestamp val is "+cal.getTime());
													for (int fileDownloadingTries = 0; fileDownloadingTries <= 10; fileDownloadingTries++)
														{
														if(getDownloadFileByFile(file, ftpClient, list))
														{
															FTPFile_Status_Sosa_Shortage pojo=new FTPFile_Status_Sosa_Shortage();
															pojo.setFileName(file.getName());
															pojo.setUploadedDate(cal.getTime());
															exceltoPojoService.save_PojoData(pojo, "file_status_sosa_shortage_tbl");
															break;
														}
											            }
											 }
											
										}
			                      	
									}
							}
						}
					}
				}catch(ConnectException  connectException)
				{
					i++;
					if(i<=3){
					getDownloadFtpFiles(i);
					}else{
						connectException.printStackTrace();
					}
					
				}finally{
					if(ftpClient!=null && ftpClient.isConnected()){
						ftpClient.logout();
						ftpClient.disconnect();
					}
				}
			}
		
		return finalpath;
	}

	/**
	 * This method for connecting to ftp
	 * 
	 * @param ftpClient
	 * @return
	 * @throws InterruptedException
	 */
	public boolean getFtpConnection(FTPClient ftpClient) {
		boolean connectionStatus = false;
		try {
			LOGGER.info("Ftp connecting ........");
			final int port = 21;
			ftpClient.connect("ftpam.flextronics.com", port);
			connectionStatus = ftpClient.login("fhpcompu","h5w9hB(P");
			ftpClient.enterLocalPassiveMode();
			ftpClient.setBufferSize(1024 * 1024);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (IOException exception)
		{
			LOGGER.error(exception.getMessage());

		}
		return connectionStatus;
	}

	public boolean getDownloadFileByFile(FTPFile  ftpFile,FTPClient ftpClient,List<String> folderList) throws FileNotFoundException, IOException 
	{
     String path="D://";
     File downloadFile=null;
     boolean downloadStatus=false;
     for(String folderpath:folderList)
     {
    	 path = path.concat(folderpath+File.separator);
    	
     }
     downloadFile=new File(path);
     if(!downloadFile.exists())
     {
    	 downloadFile.mkdirs();
     }
     System.out.println(ftpFile.getName()+ "  downloading started");
     if(ftpClient.retrieveFile(ftpFile.getName(), new FileOutputStream(downloadFile+File.separator+ftpFile.getName())))
     {
    	 downloadStatus=true; 
    	 System.out.println("download compleated");
     }
     return downloadStatus;
	}

	
	/**
	 * This method responsible for moving files to sucess folder
	 * 
	 * @param sourceFile
	 * @param destinationFolder
	 * @param ftpFile
	 * @return file moving status
	 */
	public boolean getMoveFilesToSucessOrFailureFolder(File sourceFolder,
			String destinationFolder, File file) {
		File targetFolder = new File(destinationFolder);
		if (!targetFolder.exists()) {
			targetFolder.mkdirs();
		}
		boolean filemovingStatus = false;
		try {
			File oldFile = new File(sourceFolder, file.getName());
			if (oldFile.renameTo(new File(destinationFolder + File.separator,
					file.getName()))) {
				LOGGER.info(file.getName()
						+ "The file was moved successfully to the new folder");
				filemovingStatus = true;
			} else {
				LOGGER.info(file.getName() + "The File was not moved.");

			}

		} catch (Exception e) {

		}
		return filemovingStatus;
	}

}
