package com.techouts.hp.workerthread;

import java.text.ParseException;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.techouts.hp.pojo.FtpSupport;
import com.techouts.hp.service.FtpService;
import com.techouts.hp.util.OSUtil;

@Component
public class DoiWorkerThread implements Runnable {
	private static final Log LOGGER = LogFactory.getLog(DoiWorkerThread.class);
	
	@Resource(name="flectronicsFtpServiceImpl")
	private FtpService ftpService;
	
	@Resource(name = "myProps")
	private Properties properties;
	private boolean isMethodCompl;
	@Autowired
	private OSUtil OSUtil;

	@Override
	public synchronized void run() {
		FtpSupport ftpSupport = new FtpSupport();
		ftpSupport.setStaticFileDate(properties
				.getProperty("doi.static.file.date"));
		ftpSupport.setCollectionName(properties
				.getProperty("doi.collectionName"));
		ftpSupport.setDownLoadDirectory(OSUtil.downloadDirectory());
		ftpSupport.setFieldName("Date");
		ftpSupport.setSubFolder(properties.getProperty("sub.folder.doi"));
		ftpSupport.setFileType(".xl");
		ftpSupport.setFtpServer(properties.getProperty("flectronics.ftp.server"));
		ftpSupport.setFtpUsername(properties.getProperty("flextronics.ftp.userId"));
		ftpSupport.setFtpPassword(properties.getProperty("flectronice.ftp.password"));
		isMethodCompl = false;
		try {
			int uploadCount=getFtpService().downloadFilesFromFtp(ftpSupport, 0);
			LOGGER.info("["+uploadCount+"] new files uploaded");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isMethodCompl = true;
		LOGGER.info("DOI worker thread completed.");
	}

	public boolean getDoiDownloadMethodCompleationStatus() {
		return isMethodCompl;
	}



	public FtpService getFtpService() {
		return ftpService;
	}

	public void setFtpService(FtpService ftpService) {
		this.ftpService = ftpService;
	}
}
