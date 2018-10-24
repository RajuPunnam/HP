/**
 * 
 */
package com.techouts.hp.supplies.service.impl;

import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.hp.supplies.pojo.SuppliesFileSupport;
import com.techouts.hp.supplies.pojo.SuppliesFtpSupport;
import com.techouts.hp.supplies.util.SuppliesFtpUtil;

/**
 * SuppliesFtpService used to handle all supplies files from ftp
 * @author raju.p
 *
 */
@Service
public class SuppliesFtpServiceImpl 
{
  @Autowired
  private SuppliesFtpUtil suppliesFtpUtil;
   private static final Logger LOG=Logger.getLogger(SuppliesFtpServiceImpl.class);
	public void downloadSuppliesFilesFromFtp(SuppliesFtpSupport suppliesFtpSupport) throws IOException
	{
		FTPClient ftpClient=new FTPClient();
		if(checkFtpConnection(ftpClient,suppliesFtpSupport))
		{
			if (ftpClient.changeWorkingDirectory(suppliesFtpSupport.getFilePath()))
			{
				for(FTPFile file : ftpClient.listDirectories())
				{
					if(file.isDirectory())
					{
						LOG.info(file.getName());
						if (ftpClient.changeWorkingDirectory(suppliesFtpSupport.getFilePath()+file.getName()+"/"))
						{
							suppliesFtpSupport.setSubFolder(file.getName());
						for (FTPFile ftpfile : ftpClient.listFiles())
						{
							if(ftpfile.isFile())
							{
								SuppliesFileSupport suppliesFileSupport=new SuppliesFileSupport();
								suppliesFileSupport.setFtpFile(ftpfile);
								suppliesFileSupport.setFtpClient(ftpClient);
								suppliesFtpUtil.retriveFile(suppliesFtpSupport,suppliesFileSupport);
							}
						}
						}
					}
				}
			}
		}
	}
	
	private boolean checkFtpConnection(FTPClient ftpClient,SuppliesFtpSupport suppliesFtpSupport)
	{
		boolean connectionStatus = false;
		try
		{
			LOG.info("Ftp connecting ........");
			ftpClient.connect(suppliesFtpSupport.getFtpServer(), 21);
			connectionStatus = ftpClient.login(suppliesFtpSupport.getFtpUserName(), suppliesFtpSupport.getFtpPassword());
			ftpClient.enterLocalPassiveMode();
			ftpClient.setBufferSize(1024 * 1024);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (IOException exception)
		{
			LOG.error(exception.getMessage());	
		}
		return connectionStatus;
	}
}
