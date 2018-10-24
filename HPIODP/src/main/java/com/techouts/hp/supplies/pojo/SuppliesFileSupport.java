package com.techouts.hp.supplies.pojo;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class SuppliesFileSupport 
{
	private FTPFile ftpFile;
	private FTPClient ftpClient;
	private int fileUploadCount;
	private int fileDownloadTrails;
	public FTPFile getFtpFile() {
		return ftpFile;
	}
	public void setFtpFile(FTPFile ftpFile) {
		this.ftpFile = ftpFile;
	}
	public FTPClient getFtpClient() {
		return ftpClient;
	}
	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}
	public int getFileUploadCount() {
		return fileUploadCount;
	}
	public void setFileUploadCount(int fileUploadCount) {
		this.fileUploadCount = fileUploadCount;
	}
	public int getFileDownloadTrails() {
		return fileDownloadTrails;
	}
	public void setFileDownloadTrails(int fileDownloadTrails) {
		this.fileDownloadTrails = fileDownloadTrails;
	}
}
