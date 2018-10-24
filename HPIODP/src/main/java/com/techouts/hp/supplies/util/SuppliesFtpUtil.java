/**
 * 
 */
package com.techouts.hp.supplies.util;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.hp.supplies.pojo.SuppliesFileSupport;
import com.techouts.hp.supplies.pojo.SuppliesFtpSupport;
import com.techouts.hp.supplies.service.impl.SuppliesAgingServiceImpl;
import com.techouts.hp.supplies.service.impl.SuppliesDOIServiceImpl;
import com.techouts.hp.supplies.service.impl.SuppliesOpenOrdersServiceImpl;

/**
 * @author raju.p
 *
 */
@Service
public class SuppliesFtpUtil {
	@Autowired
	private SuppliesAgingServiceImpl supplierService;
	@Autowired
	private SuppliesDOIServiceImpl suppliesDOIService;
	@Autowired
	private SuppliesOpenOrdersServiceImpl suppliesOpenOrdersService;
	private static final Logger LOG=Logger.getLogger(SuppliesFtpUtil.class);
	public void retriveFile(SuppliesFtpSupport suppliesFtpSupport,SuppliesFileSupport suppliesFileSupport)
	{
		File downloadPath = null;
		try
		{
			LOG.info("Downloading ............");
			LOG.info("********************");
			String path = suppliesFtpSupport.getDownLoadDirectory();
			String folderArray[]=suppliesFtpSupport.getFilePath().split("/");
			for(String folderName:folderArray)
			{
				path=path.concat(File.separator+folderName);
			}
			downloadPath = new File(path+File.separator+suppliesFtpSupport.getSubFolder());
			if (!downloadPath.exists())
			{
				downloadPath.mkdirs();
			}
			LOG.info("FILE NAME            :" + suppliesFileSupport.getFtpFile().getName());
			LOG.info("FILE DATE            :" + suppliesFileSupport.getFtpFile().getTimestamp().getTime());
			boolean downLoadStatus = suppliesFileSupport.getFtpClient().retrieveFile(suppliesFileSupport.getFtpFile().getName(), new FileOutputStream(new File(downloadPath.getAbsolutePath() + File.separator + suppliesFileSupport.getFtpFile().getName())));
			if(downLoadStatus)
			{
				if(suppliesFtpSupport.getFilePath().contains("Aging"))
				{
				supplierService.readSuppliesAgingFile(downloadPath.getAbsoluteFile(), suppliesFileSupport.getFtpFile());
				}
				else if(suppliesFtpSupport.getFilePath().contains("DOI"))
				{
					suppliesDOIService.readSuppliesAgingFile(downloadPath.getAbsoluteFile(), suppliesFileSupport.getFtpFile());
				}
				else if(suppliesFtpSupport.getFilePath().contains("Open Order"))
				{
					suppliesOpenOrdersService.readSuppliesAgingFile(downloadPath.getAbsoluteFile(), suppliesFileSupport.getFtpFile());	
				}
			}
	      }catch(Exception exception)
		  {
	    	  exception.printStackTrace();
		  }
	}
}
