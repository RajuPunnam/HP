/**
 * 
 */
package com.techouts.hp.supplies.workerthread;

import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
public class AgingWokerThread implements Runnable
{
	private static final Logger LOG=Logger.getLogger(AgingWokerThread.class);
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
		sfs.setStaticFileDate("supplies.aging.date");
		sfs.setFilePath(prop.getProperty("supplies.aging.filepath"));
		sfs.setFtpServer(prop.getProperty("flectronics.ftp.server"));
		sfs.setFtpUserName(prop.getProperty("flectronics.ftp.userid"));
		sfs.setFtpPassword(prop.getProperty("flectronics.ftp.password"));
		try
		{
		suppliesFtpServiceImpl.downloadSuppliesFilesFromFtp(sfs);
		}catch(Exception exception)
		{
			LOG.error(exception);
		}
	}
}
