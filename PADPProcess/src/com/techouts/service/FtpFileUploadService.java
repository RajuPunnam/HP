package com.techouts.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * class responsible to upload sku avil in ftp
 * 
 * @author p.raju
 *
 */
@Service
public class FtpFileUploadService {
	private static Logger logger = Logger.getLogger(FtpFileUploadService.class);
	@Resource(name = "myProps")
	private Properties properties;
	private static final String FILE_NAME = String.format("%s.%s",
			"ALLSKU_AND_AVAVBLE", "xlsx");

	/**
	 * method for upload file into ftp
	 * 
	 * @param outputFile
	 * @return uploadStatus
	 */
	public boolean getUploadSkuAvailToFtp(File outputFile) {
		FTPClient ftpClient = new FTPClient();
		boolean uploadStatus = false;
		boolean fileFoundStatus = false;

		if (getLoginFtp(ftpClient)) {
			try {
				for (FTPFile mainDirs : ftpClient.listDirectories()) {
					if (mainDirs.isDirectory()
							&& mainDirs.getName().equals(
									properties.getProperty("main.folder"))) {
						for (FTPFile sub : ftpClient.listDirectories("/"
								+ mainDirs.getName())) {
							if (sub.isDirectory()
									&& sub.getName()
											.equals(properties
													.getProperty("sub.folder.padata"))) {
								ftpClient.changeWorkingDirectory(mainDirs
										.getName() + "/" + sub.getName());

								for (FTPFile file : ftpClient.listFiles()) {

									if (file.getName().equalsIgnoreCase(
											FILE_NAME)) {
										File skuAvilFile = new File(outputFile
												+ File.separator + FILE_NAME);
										if (skuAvilFile != null) {
											if (ftpClient.deleteFile(FILE_NAME)) {
												fileFoundStatus = true;
												logger.info("file deleted in ftp");
												if (ftpClient.storeFile(
														FILE_NAME,
														new FileInputStream(
																skuAvilFile))) {
													uploadStatus = true;
												}
											}
										}
									}
									break;
								}
								if (!fileFoundStatus) {
									if (ftpClient.storeFile(FILE_NAME,
											new FileInputStream(outputFile
													+ File.separator
													+ FILE_NAME))) {
										uploadStatus = true;
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {

			} finally {
				if (ftpClient.isConnected()) {
					try {
						ftpClient.logout();
						ftpClient.disconnect();
					} catch (Exception exception) {

					}

				}
			}
		} else {
			logger.info("ftp not connected");
		}
		return uploadStatus;
	}

	/**
	 * method for connecting FTP
	 * 
	 * @param ftpClient
	 * @return uploadStatus
	 */
	private boolean getLoginFtp(FTPClient ftpClient) {
		boolean connect = false;
		int portNumber = 21;
		try {
			ftpClient.connect(properties.getProperty("ftp.server"), portNumber);
			connect = ftpClient.login(properties.getProperty("ftp.userId"),
					properties.getProperty("ftp.password"));
			ftpClient.enterLocalPassiveMode();
			ftpClient.setBufferSize(1024 * 1024);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (Exception exception) {
			exception.getMessage();
		}
		return connect;
	}

}
