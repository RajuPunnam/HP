package com.techouts.hp.service;

import java.io.File;

import org.apache.commons.net.ftp.FTPFile;

import com.techouts.hp.pojo.FileUploadInfo;

public interface DataService {

	FileUploadInfo readDataFromFile(File inputLocation, FTPFile file) throws Exception;
}
