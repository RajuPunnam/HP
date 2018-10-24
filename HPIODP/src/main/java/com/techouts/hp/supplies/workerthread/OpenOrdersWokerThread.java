/**
 * 
 */
package com.techouts.hp.supplies.workerthread;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techouts.hp.supplies.pojo.SuppliesFtpSupport;
import com.techouts.hp.supplies.service.impl.SuppliesFtpServiceImpl;
import com.techouts.hp.supplies.util.OSUtil;

/**
 * @author raju.p
 *
 */
@Component
public class OpenOrdersWokerThread implements Runnable
{
	@Autowired
	private OSUtil oSUtil;
	@Resource(name = "myProps")
	private Properties prop;
	@Autowired
    private SuppliesFtpServiceImpl suppliesFtpServiceImpl;
	public synchronized void run() 
	{
		SuppliesFtpSupport sfs = new SuppliesFtpSupport();
		sfs.setDownLoadDirectory(oSUtil.downloadDirectory());
		sfs.setStaticFileDate("supplies.openorders.date");
		sfs.setFilePath(prop.getProperty("supplies.openorders.filepath"));
		sfs.setFtpServer(prop.getProperty("flectronics.ftp.server"));
		sfs.setFtpUserName(prop.getProperty("flectronics.ftp.userid"));
		sfs.setFtpPassword(prop.getProperty("flectronics.ftp.password"));
		try
		{
		suppliesFtpServiceImpl.downloadSuppliesFilesFromFtp(sfs);
		}
		catch(Exception e)
		{
			
		}
	}
}
