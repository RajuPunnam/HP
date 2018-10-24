package com.techouts.hp.service.impl;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.techouts.hp.pojo.FileSupport;
import com.techouts.hp.pojo.FtpSupport;
import com.techouts.hp.service.FtpService;
import com.techouts.hp.util.FtpUtil;
/**
 * TechoutsFtpServiceImpl class for downloading FTP files from Techouts ftp server
 * @author raju.p
 *
 */
public class TechoutsFtpServiceImpl implements FtpService
{
	private static final Logger LOGGER=Logger.getLogger(TechoutsFtpServiceImpl.class);
	@Resource(name = "myProps")
	private Properties properties;
	@Autowired
	private FtpUtil ftpUtil;
	public final int downloadFilesFromFtp(FtpSupport ftpSupport,int connectionTrails)
	{
		int filesUploadCount = 0;
		FTPClient ftpClient = new FTPClient();
		List<String> folderList=null;
		folderList=new ArrayList<String>();
		for (int connectionCount = 0; connectionCount <= 5; connectionCount++)
		{
			if (getFtpConnection(ftpClient, connectionCount,ftpSupport))
			{
				LOGGER.info("Ftp connected.");
				try
				{
					for (FTPFile mainDir : ftpClient.listDirectories())
					{
					if(mainDir.isDirectory() && mainDir.getName().equals(properties.getProperty("techouts.ftp.bom.sub.folder")))
					{
						ftpClient.changeWorkingDirectory("/"+mainDir.getName());
						folderList.add(mainDir.getName());
						for(FTPFile file:ftpClient.listFiles())
						{
							if(file.isFile() && file.getSize()>0 && !file.getName().endsWith(ftpSupport.getFileType()))
							{
								if(file.getName().contains((ftpSupport.getFileName())))
								{
								for (int fileDownloadingTries = 0; fileDownloadingTries <= 10; fileDownloadingTries++)
								{
									FileSupport fileSupport=new FileSupport();
									fileSupport.setFtpFile(file);
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
								break;
							}
							}
						}
						break;
					}
					}
				}catch(ConnectException connectException)
				{
					if(connectionTrails<=5)
					{
						downloadFilesFromFtp(ftpSupport,connectionTrails++);
					}
					else
					{
						break;
					}
				}
				catch(Exception exception)
				{
					LOGGER.error(exception.getMessage());
				}
				break;
				}
			else
			{
				LOGGER.error("ftp connection failed");
				LOGGER.info("trying to connect ftp  again");
				continue;
			}
	       }
		return filesUploadCount;
	}
	/**
	 * method for connecting to ftp
	 * return connectionStatus
	 */
	public  boolean getFtpConnection(FTPClient ftpClient, int connectionCount,FtpSupport ftpSupport)
	{
		boolean connectionStatus = false;
		try
		{
			LOGGER.info("Ftp connecting ........");
			ftpClient.connect(ftpSupport.getFtpServer(), 21);
			connectionStatus = ftpClient.login(ftpSupport.getFtpUsername(), ftpSupport.getFtpPassword());
			ftpClient.enterLocalPassiveMode();
			ftpClient.setBufferSize(1024 * 1024);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (IOException exception)
		{
		}
		return connectionStatus;
	}
}
