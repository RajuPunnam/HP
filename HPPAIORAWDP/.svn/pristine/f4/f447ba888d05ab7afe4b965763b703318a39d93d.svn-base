package com.techouts.hp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.techouts.hp.dao.DataDao;
import com.techouts.hp.pojo.FileInfo;
import com.techouts.hp.pojo.FileSupport;
import com.techouts.hp.pojo.FileUploadInfo;
import com.techouts.hp.pojo.FtpSupport;
import com.techouts.hp.service.DataService;
/**
 * common  download class for all files
 * @author raju.p
 *
 */
public class FtpUtil {
	private static final Logger LOGGER=Logger.getLogger(FtpUtil.class);
	@Resource(name = "myProps")
	private Properties properties;
	@Resource(name="dataDaoImpl")
	private DataDao dataDao;
	private DataService intendedBomService;
	private DataService amlAvcostedBomService;
	private DataService bomAvReportPeriodServiceImpl;
	private DataService bomFullReportPeriodServiceImpl;
	private DataService doiService;
	private DataService pofromhpService;
	private DataService pcshipmentsHpService;
	private DataService ppbPcService;
	private DataService pcOpenorderService;
	
	public DataDao getDataDao() {
		return dataDao;
	}
	public void setDataDao(DataDao dataDao) {
		this.dataDao = dataDao;
	}
	public DataService getIntendedBomService() {
		return intendedBomService;
	}
	public void setIntendedBomService(DataService intendedBomService) {
		this.intendedBomService = intendedBomService;
	}
	public DataService getAmlAvcostedBomService() {
		return amlAvcostedBomService;
	}
	public void setAmlAvcostedBomService(DataService amlAvcostedBomService) {
		this.amlAvcostedBomService = amlAvcostedBomService;
	}
	public DataService getBomAvReportPeriodServiceImpl() {
		return bomAvReportPeriodServiceImpl;
	}
	public void setBomAvReportPeriodServiceImpl(
			DataService bomAvReportPeriodServiceImpl) {
		this.bomAvReportPeriodServiceImpl = bomAvReportPeriodServiceImpl;
	}
	public DataService getBomFullReportPeriodServiceImpl() {
		return bomFullReportPeriodServiceImpl;
	}
	public void setBomFullReportPeriodServiceImpl(
			DataService bomFullReportPeriodServiceImpl) {
		this.bomFullReportPeriodServiceImpl = bomFullReportPeriodServiceImpl;
	}
	public DataService getDoiService() {
		return doiService;
	}
	public void setDoiService(DataService doiService) {
		this.doiService = doiService;
	}
	public DataService getPofromhpService() {
		return pofromhpService;
	}
	public void setPofromhpService(DataService pofromhpService) {
		this.pofromhpService = pofromhpService;
	}
	public DataService getPcshipmentsHpService() {
		return pcshipmentsHpService;
	}
	public void setPcshipmentsHpService(DataService pcshipmentsHpService) {
		this.pcshipmentsHpService = pcshipmentsHpService;
	}
	public DataService getPpbPcService() {
		return ppbPcService;
	}
	public void setPpbPcService(DataService ppbPcService) {
		this.ppbPcService = ppbPcService;
	}
	public DataService getPcOpenorderService() {
		return pcOpenorderService;
	}
	public void setPcOpenorderService(DataService pcOpenorderService) {
		this.pcOpenorderService = pcOpenorderService;
	}
	/**
	 * retrives FTP file
	 * @param fileSupport
	 * @param ftpSupport
	 * @return ftpPojo
	 */
	public FtpSupport download(FileSupport fileSupport, FtpSupport ftpSupport)
	{
		File downloadPath = null;
		FtpSupport ftpPojo = new FtpSupport();
		FileUploadInfo fileUploadInfo=null;
		try
		{
			LOGGER.info("Downloading ............");
			LOGGER.info("********************");
			String path = ftpSupport.getDownLoadDirectory();
			for(String folderName:fileSupport.getFolderList())
			{
				path=path.concat(File.separator+folderName);
			}
			downloadPath = new File(path);
			if (!downloadPath.exists())
			{
				downloadPath.mkdirs();
			}
			LOGGER.info("FILE NAME            :" + fileSupport.getFtpFile().getName());
			LOGGER.info("FILE DATE            :" + fileSupport.getFtpFile().getTimestamp().getTime());

			boolean downLoadStatus = fileSupport.getFtpClient().retrieveFile(fileSupport.getFtpFile().getName(), new FileOutputStream(new File(path + File.separator + fileSupport.getFtpFile().getName())));
			if (downLoadStatus)
			{
				ftpPojo.setDownloadStatus(downLoadStatus);
				LOGGER.info("File downloaded sucessfully");
				if (ftpSupport.getSubFolder().equals(properties.getProperty("sub.folder.doi")))
				{
					fileUploadInfo = getDoiService().readDataFromFile(downloadPath.getAbsoluteFile(), fileSupport.getFtpFile());
				} else if (ftpSupport.getSubFolder().equals(properties.getProperty("sub.folder.openoreders")))
				{
					fileUploadInfo = getPcOpenorderService().readDataFromFile(downloadPath.getAbsoluteFile(), fileSupport.getFtpFile());
				} else if (ftpSupport.getSubFolder().equals(properties.getProperty("sub.folder.shipments")))
				{
					fileUploadInfo = pcshipmentsHpService.readDataFromFile(downloadPath.getAbsoluteFile(), fileSupport.getFtpFile());
				} else if (ftpSupport.getSubFolder().equals(properties.getProperty("sub.folder.pofromhp")))
				{
					fileUploadInfo = getPofromhpService().readDataFromFile(downloadPath.getAbsoluteFile(), fileSupport.getFtpFile());
				} else if (ftpSupport.getSubFolder().equals(properties.getProperty("sub.folder.consumption")))
				{
					fileUploadInfo = getPpbPcService().readDataFromFile(downloadPath.getAbsoluteFile(), fileSupport.getFtpFile());
				}
				else if(ftpSupport.getSubFolder().equals(properties.getProperty("sub.folder.intended.bom")))
				{
					fileUploadInfo=getIntendedBomService().readDataFromFile(downloadPath.getAbsoluteFile(), fileSupport.getFtpFile());
				}
				else if(ftpSupport.getSubFolder().equals(properties.getProperty("techouts.ftp.bom.sub.folder")))
				{
					if(fileSupport.getFtpFile().getName().contains("AMLAVCostedBomReportSummary"))
					{
						fileUploadInfo=getAmlAvcostedBomService().readDataFromFile(downloadPath.getAbsoluteFile(), fileSupport.getFtpFile());
					}
					else if(fileSupport.getFtpFile().getName().contains("BOMAVReportPeriod"))
					{
						fileUploadInfo=getBomAvReportPeriodServiceImpl().readDataFromFile(downloadPath.getAbsoluteFile(), fileSupport.getFtpFile());
					}
					else if(fileSupport.getFtpFile().getName().contains("BOMFULLReportPeriod"))
					{
						fileUploadInfo=getBomFullReportPeriodServiceImpl().readDataFromFile(downloadPath.getAbsoluteFile(), fileSupport.getFtpFile());
					}
					
				}
				FileInfo status=new FileInfo();
				status.setSourceFolder(ftpSupport.getSubFolder());
				status.setFileName(fileSupport.getFtpFile().getName());
				status.setFileReceivedDate(fileSupport.getFtpFile().getTimestamp().getTime());
				status.setFileUploadedDate(new Date());
				status.setUploadStatus(fileUploadInfo.isUploadStatus());
				status.setNoOfRecords(fileUploadInfo.getRecordsCount());
				status.setFtpLocation(ftpSupport.getFtpServer());
				dataDao.insertFileInfo(status);
				if (fileUploadInfo!=null && fileUploadInfo.isUploadStatus())
				{
					LOGGER.info("file uploaded sucessfully");
					String sucessFolder = path + File.separator + properties.getProperty("sucess");
					LOGGER.info(fileSupport.getFtpFile().getName() + " loaded to db with collection name " + ftpSupport.getCollectionName() + " sucessfully");
					//movesFile(downloadPath.getAbsoluteFile(), sucessFolder, fileSupport.getFtpFile());
					int fileCount=fileSupport.getFileuploadCount()+1;
					ftpPojo.setUploadCount(fileCount);
					ftpPojo.setUploadStatus(fileUploadInfo.isUploadStatus());

				} else
				{
					LOGGER.info("file uploaded failed");
					int fileCount=fileSupport.getFileuploadCount();
					ftpPojo.setUploadCount(fileCount);
				}
			} else
			{
				int fileCount=fileSupport.getFileuploadCount();
				ftpPojo.setUploadCount(fileCount);
				ftpPojo.setDownloadStatus(downLoadStatus);
				if (fileSupport.getFileDownloadTrails() == 10)
				{
					LOGGER.error("file download has failed");
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			LOGGER.error("exception " + e.getMessage());
		}
		return ftpPojo;
	}
	public boolean movesFile(File sourceFolder, String destinationFolder, FTPFile ftpFile)
	{
		File targetFolder = new File(destinationFolder);
		if (!targetFolder.exists())
		{
			targetFolder.mkdirs();
		}
		boolean filemovingStatus = false;
		try
		{
			File source = new File(sourceFolder, ftpFile.getName());
			if (source.renameTo(new File(destinationFolder + File.separator, ftpFile.getName())))
			{
				LOGGER.info(ftpFile.getName() + "The file was moved successfully to the new folder");
				filemovingStatus = true;
			} else
			{
				LOGGER.info(ftpFile.getName() + "The File was not moved.");

			}

		} catch (Exception e)
		{
			LOGGER.error(e.getMessage());
		}
		return filemovingStatus;
	}
}
