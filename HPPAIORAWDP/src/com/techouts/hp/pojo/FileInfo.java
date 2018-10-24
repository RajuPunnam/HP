package com.techouts.hp.pojo;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection="Status")
public class FileInfo
{
	@Field("Ftp Location")
    private String ftpLocation;
	@Field("Source Folder")
	private String sourceFolder;
	@Field("File Name")
	private String fileName;
	@Field("File Received Date")
	private Date fileReceivedDate;
	@Field("Upload Status")
	private boolean uploadStatus;
	@Field("File Upload Date")
	private Date fileUploadedDate;
	@Field("Records Count")
	private int noOfRecords;
	public String getFtpLocation() {
		return ftpLocation;
	}
	public void setFtpLocation(String ftpLocation) {
		this.ftpLocation = ftpLocation;
	}
	public String getSourceFolder() {
		return sourceFolder;
	}
	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getFileReceivedDate() {
		return fileReceivedDate;
	}
	public void setFileReceivedDate(Date fileReceivedDate) {
		this.fileReceivedDate = fileReceivedDate;
	}
	public boolean isUploadStatus() {
		return uploadStatus;
	}
	public void setUploadStatus(boolean uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	public Date getFileUploadedDate() {
		return fileUploadedDate;
	}
	public void setFileUploadedDate(Date fileUploadedDate) {
		this.fileUploadedDate = fileUploadedDate;
	}
	public int getNoOfRecords() {
		return noOfRecords;
	}
	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}
	@Override
	public String toString() {
		return "FileInfo [ftpLocation=" + ftpLocation + ", sourceFolder="
				+ sourceFolder + ", fileName=" + fileName
				+ ", fileReceivedDate=" + fileReceivedDate + ", uploadStatus="
				+ uploadStatus + ", fileUploadedDate=" + fileUploadedDate
				+ ", noOfRecords=" + noOfRecords + "]";
	}
}

