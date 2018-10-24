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
public class PoFromHpWorkerThread implements Runnable {
	private final Logger LOGGER = Logger.getLogger(PoFromHpWorkerThread.class);
	@Resource(name = "flectronicsFtpServiceImpl")
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
				.getProperty("pofromhp.static.date"));
		ftpSupport.setCollectionName(properties
				.getProperty("pofromhp.pc.collectionname"));
		ftpSupport.setDownLoadDirectory(OSUtil.downloadDirectory());
		ftpSupport.setFieldName("File Date");
		ftpSupport.setSubFolder(properties.getProperty("sub.folder.pofromhp"));
		ftpSupport.setFileType(".xl");
		ftpSupport.setFtpServer(properties
				.getProperty("flectronics.ftp.server"));
		ftpSupport.setFtpUsername(properties
				.getProperty("flextronics.ftp.userId"));
		ftpSupport.setFtpPassword(properties
				.getProperty("flectronice.ftp.password"));
		isMethodCompl = false;
		try {
			int count = getFlectronicsFtpService().downloadFilesFromFtp(
					ftpSupport, 0);
			LOGGER.info("[" + count + "] New Files processed");
		} catch (ParseException parseException) {
			LOGGER.error(parseException.getMessage());
		}
		isMethodCompl = true;
		LOGGER.info("PO From Hp Thread Completed");
	}

	public boolean getPofromHpMethodCompleationStatus() {
		return isMethodCompl;
	}

	public FtpService getFlectronicsFtpService() {
		return flectronicsFtpService;
	}

	public void setFlectronicsFtpService(FtpService flectronicsFtpService) {
		this.flectronicsFtpService = flectronicsFtpService;
	}

}
