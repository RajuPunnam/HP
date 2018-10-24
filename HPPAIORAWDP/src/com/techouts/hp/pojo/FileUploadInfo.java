package com.techouts.hp.pojo;

public class FileUploadInfo {
	private boolean uploadStatus;
	private int recordsCount;
	public boolean isUploadStatus() {
		return uploadStatus;
	}
	public void setUploadStatus(boolean uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	public int getRecordsCount() {
		return recordsCount;
	}
	public void setRecordsCount(int recordsCount) {
		this.recordsCount = recordsCount;
	}

}
