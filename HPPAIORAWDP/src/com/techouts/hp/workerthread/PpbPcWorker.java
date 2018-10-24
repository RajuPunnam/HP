package com.techouts.hp.workerthread;

import java.text.ParseException;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.techouts.hp.pojo.FtpSupport;
import com.techouts.hp.service.FtpService;
import com.techouts.hp.util.OSUtil;

@Component
public class PpbPcWorker extends Thread {
	private  final Logger LOGGER = Logger.getLogger(PpbPcWorker.class);
	@Resource(name="flectronicsFtpServiceImpl")
	private FtpService flectronicsFtpService;
	@Resource(name = "myProps")
	private Properties properties;
	private boolean isMethodCompl;
	@Autowired
	private OSUtil OSUtil;

	@Override
	public synchronized void run() {
		FtpSupport ftpSupport = new FtpSupport();
		ftpSupport.setStaticFileDate(properties
				.getProperty("consumption.static.pc.date"));
		ftpSupport.setCollectionName(properties
				.getProperty("consumption.pc.collectionname"));
		ftpSupport.setDownLoadDirectory(OSUtil.downloadDirectory());
		ftpSupport.setFieldName("Date");
		ftpSupport.setSubFolder(properties
				.getProperty("sub.folder.consumption"));
		ftpSupport.setFileType(".xl");
		ftpSupport.setFtpServer(properties.getProperty("flectronics.ftp.server"));
		ftpSupport.setFtpUsername(properties.getProperty("flextronics.ftp.userId"));
		ftpSupport.setFtpPassword(properties.getProperty("flectronice.ftp.password"));
		isMethodCompl = false;
		try {
			int count=getFlectronicsFtpService().downloadFilesFromFtp(ftpSupport, 0);
			LOGGER.info("["+count+"]   New files processed");
		} catch (ParseException exception) {
			LOGGER.error(exception.getMessage());
		}
		isMethodCompl = true;
		LOGGER.info("PPB PC Thread completed.");
	}

	public boolean getConsumptionPcDownloadMethodCompleationStatus() {
		return isMethodCompl;
	}

	public FtpService getFlectronicsFtpService() {
		return flectronicsFtpService;
	}

	public void setFlectronicsFtpService(FtpService flectronicsFtpService) {
		this.flectronicsFtpService = flectronicsFtpService;
	}

}
