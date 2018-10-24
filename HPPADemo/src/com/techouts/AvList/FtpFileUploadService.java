package com.techouts.AvList;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class FtpFileUploadService {
	private static final Logger LOGGER = Logger
			.getLogger(FtpFileUploadService.class);
	@Resource(name = "myProps")
	private Properties properties;

	public boolean getUploadSkuAvailToFtp(File outputFile) {

		FTPClient ftpClient = new FTPClient();
		boolean uploadStatus = false;
		boolean fileFoundStatus = false;
		String fileName = "Techouts-AV List For AV-PN Kinaxis BOM.xlsx";

		if (getLoginFtp(ftpClient)) {
			try {
				for (FTPFile mainDirs : ftpClient.listDirectories()) {
					if (mainDirs.isDirectory()
							&& mainDirs.getName().equals("BINOY")) {
						for (FTPFile sub : ftpClient.listDirectories("/"
								+ mainDirs.getName())) {
							if (sub.isDirectory()
									&& sub.getName().equals("BOM")) {
								ftpClient.changeWorkingDirectory(mainDirs
										.getName() + "/" + sub.getName());

								for (FTPFile file : ftpClient.listFiles()) {

									if (file.getName().equalsIgnoreCase(
											fileName)) {
										File skuAvilFile = new File(outputFile
												+ File.separator + fileName);
										if (skuAvilFile != null) {
											if (ftpClient.deleteFile(fileName)) {
												fileFoundStatus = true;
												LOGGER.info("file deleted in ftp");
												if (ftpClient.storeFile(
														fileName,
														new FileInputStream(
																skuAvilFile))) {
													LOGGER.info(file
															.getTimestamp()
															.getTime());
													uploadStatus = true;
												}
											}
										}
										break;
									}

								}
								if (!fileFoundStatus) {
									if (ftpClient
											.storeFile(
													fileName,
													new FileInputStream(
															outputFile
																	+ File.separator
																	+ fileName))) {
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
			LOGGER.info("ftp not connected");
		}
		return uploadStatus;

	}

	protected boolean getLoginFtp(FTPClient ftpClient) {
		boolean uploadStatus = false;
		int portNumber = 21;
		try {
			ftpClient.connect("ftpam.flextronics.com", portNumber);
			uploadStatus = ftpClient.login("fhpcompu", "p3m4wX(J");
			ftpClient.enterLocalPassiveMode();
			ftpClient.setBufferSize(1024 * 1024);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (Exception exception) {
			exception.getMessage();
		}
		return uploadStatus;
	}

	public String createLocation() {
		String outputLocation = "";
		String operSys = System.getProperty("os.name").toLowerCase();
		if (operSys.contains("win")) {
			outputLocation = "D:" + File.separator + "avlist";

		} else if (operSys.contains("nix") || operSys.contains("nux")
				|| operSys.contains("aix")) {
			outputLocation = properties.getProperty("AvList.folder");

		}

		File file = new File(outputLocation);
		if (!file.exists()) {
			LOGGER.info("creating Directory");
			boolean created = file.mkdirs();
			LOGGER.info("Directory Created " + created);
		} else {
			LOGGER.info("Directory Exist");
		}
		return outputLocation;
	}

}
