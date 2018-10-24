package com.techouts.hp.service.impl;

import java.io.IOException;
import java.net.ConnectException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.hp.dao.impl.StatusDaoImpl;
import com.techouts.hp.pojo.FileInfo;
import com.techouts.hp.pojo.FileSupport;
import com.techouts.hp.pojo.FtpSupport;
import com.techouts.hp.service.FtpService;
import com.techouts.hp.util.FtpUtil;

/**
 * Class is responsible to download csv from FTP and convert it.
 * 
 * @author TECH
 *
 */

public class FlectronicsFtpServiceImpl implements FtpService
{
	private static final Logger LOGGER = Logger.getLogger(FlectronicsFtpServiceImpl.class);
	@Autowired
	private FtpUtil ftpUtil;
	@Resource(name = "myProps")
	private Properties properties;
	@Autowired
	private StatusDaoImpl statusDao;
	@Override
	public final int downloadFilesFromFtp(FtpSupport ftpSupport,int connectionTrails) throws ParseException 
	{
		String fileDate = null;
		List<String> loadedDateList = null;
		int filesUploadCount = 0;
		int ftpFilesCount=0;
		FTPFile bomLatestFile=null;
		FTPClient ftpClient = new FTPClient();
		List<String> folderList=new ArrayList<String>();
		Date latestFileDate = maxFileDate(ftpSupport);
		
		LOGGER.info(ftpSupport.getCollectionName() + "  static or updated date [[" + latestFileDate+"]]");
		for (int connectionCount = 0; connectionCount <= 5; connectionCount++)
		{
			if (getFtpConnection(ftpClient, connectionCount,ftpSupport))
			{
				LOGGER.info("Ftp connected.");
				loadedDateList = statusDao.getAllFileDates(ftpSupport.getCollectionName(), ftpSupport.getFieldName());
				try
				{
					for (FTPFile mainDir : ftpClient.listDirectories())
					{
						if (mainDir.isDirectory() && mainDir.getName().equals(properties.getProperty("main.folder")))
						{
							folderList.add(mainDir.getName());
							LOGGER.info(mainDir.getName());
							for (FTPFile sub : ftpClient.listDirectories("/" + mainDir.getName()))
							{
								if (sub.isDirectory() && sub.getName().equals(ftpSupport.getSubFolder()))
								{
									if(ftpClient.changeWorkingDirectory(mainDir.getName() + "/" + sub.getName()))
									{
									folderList.add(sub.getName());
									LOGGER.info(sub.getName());
									for (FTPFile file : ftpClient.listFiles())
									{
										if (file.isFile() && !file.getName().contains(ftpSupport.getFileType()))
										{
											if(!ftpSupport.getSubFolder().equals(properties.getProperty("sub.folder.intended.bom")))
											{
												fileDate=getDateFromFileName(ftpSupport,file);
												loadedDateList=(loadedDateList!=null)?loadedDateList:new ArrayList<>();
											if (file.getTimestamp().getTime().after(latestFileDate) && !loadedDateList.contains(fileDate)
													&& file.getSize() > 0)
											{
												for (int fileDownloadingTries = 0; fileDownloadingTries <= 10; fileDownloadingTries++)
												{
													FileSupport fileSupport=new FileSupport();
													fileSupport.setFtpFile(file);
													fileSupport.setFtpClient(ftpClient);
													fileSupport.setFileuploadCount(filesUploadCount);
													fileSupport.setFileDownloadTrails(fileDownloadingTries);
													fileSupport.setFolderList(folderList);
													FtpSupport fileInof = ftpUtil.download(fileSupport,ftpSupport);
													if (fileInof.isDownloadStatus())
													{
														filesUploadCount = fileInof.getUploadCount();
														break;
													} else
													{
														filesUploadCount = fileInof.getUploadCount();
														continue;
													}
												}
											}
										}
											else if(ftpSupport.getSubFolder().equals(properties.getProperty("sub.folder.intended.bom")))
											{
												ftpFilesCount++;
												if(file.getName().startsWith("AV List For AV-PN Kinaxis"))
												{
													LOGGER.info("File name "+file.getName()+" its upload date "+file.getTimestamp().getTime());
													if(file.getTimestamp().getTime().after(latestFileDate))
													{
														latestFileDate=file.getTimestamp().getTime();
														bomLatestFile=file;
														
													}
												}
												if(ftpFilesCount==ftpClient.listFiles().length)
												{
													if(bomLatestFile!=null && bomLatestFile.getSize()>0)
													{
														for (int fileDownloadingTries = 0; fileDownloadingTries <= 10; fileDownloadingTries++)
														{
															FileSupport fileSupport=new FileSupport();
															fileSupport.setFtpFile(bomLatestFile);
															fileSupport.setFtpClient(ftpClient);
															fileSupport.setFileuploadCount(filesUploadCount);
															fileSupport.setFileDownloadTrails(fileDownloadingTries);
															fileSupport.setFolderList(folderList);
															FtpSupport ftppojo = ftpUtil.download(fileSupport,ftpSupport);
															if (ftppojo.isDownloadStatus())
															{
																filesUploadCount = ftppojo.getUploadCount();
																break;
															} else
															{
																filesUploadCount = ftppojo.getUploadCount();
																continue;
															}
														}
													}
												}
											}
										}
									}//for ending
								}
									break;
								}
							}
							break;
						} else
						{
							continue;
						}
					}
				} catch (ConnectException connectException)
				{
					if(connectionTrails<=5)
					{
						downloadFilesFromFtp(ftpSupport,connectionTrails++);
					}
					else
					{
						break;
					}
					LOGGER.error(connectException.getMessage());
				}
				catch(Exception exception)
				{
					LOGGER.error(exception.getMessage());
				}
				finally
				{
					if (ftpClient.isConnected())
					{
						try
						{
							ftpClient.logout();
							ftpClient.disconnect();

						} catch (Exception e1)
						{
							LOGGER.error(e1.getMessage());
						}
					}
				}
				break;
			} else
			{
				LOGGER.error("ftp connection failed");
				LOGGER.info("trying to connect ftp  again");
				continue;
			}
		}

		return filesUploadCount;
	}

	/**
	 * This method for connecting to ftp
	 * 
	 * @param ftpClient
	 * @return
	 * @throws InterruptedException
	 */
	@Override
	public final boolean getFtpConnection(FTPClient ftpClient, int connectionCount,FtpSupport ftpSupport)
	{
		boolean connectionStatus = false;
		try
		{
			LOGGER.info("Ftp connecting ........");
			final int port = 21;
			ftpClient.connect(ftpSupport.getFtpServer(), port);
			connectionStatus = ftpClient.login(ftpSupport.getFtpUsername(), ftpSupport.getFtpPassword());
			ftpClient.enterLocalPassiveMode();
			ftpClient.setBufferSize(1024 * 1024);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (IOException exception)
		{
			
		}
		return connectionStatus;
	}

	
	@SuppressWarnings("unused")
private Date maxFileDate(FtpSupport ftpSupport) throws ParseException
{
		final DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", Locale.ENGLISH);
		dateFormat.parse(ftpSupport.getStaticFileDate());
		Date maxDate=statusDao.maxFileDate(ftpSupport.getSubFolder());
		return maxDate!= null?maxDate:dateFormat.parse(ftpSupport.getStaticFileDate());
	}
private String getDateFromFileName(FtpSupport ftpSupport,FTPFile file)
{
	String fileDate=null;
	if (ftpSupport.getSubFolder().equals(properties.getProperty("sub.folder.doi")))
	{
		fileDate = file.getName().substring(3, 11);
		
	} else if (ftpSupport.getSubFolder().equals(properties.getProperty("sub.folder.openoreders")))
	{
		fileDate = file.getName().substring(2, 10);
		
	} else if (ftpSupport.getSubFolder().equals(properties.getProperty("sub.folder.shipments")))
	{
		fileDate = file.getName().substring(3, 11);
		
	} else if (ftpSupport.getSubFolder().equals(properties.getProperty("sub.folder.pofromhp")))
	{
		fileDate = file.getName().substring(2, 10);
		
	} else if (ftpSupport.getSubFolder().equals(properties.getProperty("sub.folder.consumption")))
	{
		fileDate = file.getName().substring(6, 14);
		
	}
	return fileDate.substring(4, 6) + "-" + fileDate.substring(6, 8) + "-" + fileDate.substring(0, 4);	
}
}
