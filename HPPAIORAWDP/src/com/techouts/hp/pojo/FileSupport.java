package com.techouts.hp.pojo;

import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FileSupport 
{
	private FTPFile ftpFile;
	private FTPClient ftpClient;
	private int fileuploadCount;
	private int fileDownloadTrails;
	private List<String> folderList;
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
	public int getFileuploadCount() {
		return fileuploadCount;
	}
	public void setFileuploadCount(int fileuploadCount) {
		this.fileuploadCount = fileuploadCount;
	}
	public int getFileDownloadTrails() {
		return fileDownloadTrails;
	}
	public void setFileDownloadTrails(int fileDownloadTrails) {
		this.fileDownloadTrails = fileDownloadTrails;
	}
	public List<String> getFolderList() {
		return folderList;
	}
	public void setFolderList(List<String> folderList) {
		this.folderList = folderList;
	}
}
