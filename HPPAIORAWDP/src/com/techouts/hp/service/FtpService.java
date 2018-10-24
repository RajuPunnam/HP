package com.techouts.hp.service;

import java.text.ParseException;

import org.apache.commons.net.ftp.FTPClient;

import com.techouts.hp.pojo.FtpSupport;

public interface FtpService {
	int downloadFilesFromFtp(FtpSupport ftpSupport,int connectionTrails) throws ParseException;
	boolean getFtpConnection(FTPClient ftpClient, int connectionCount,FtpSupport ftpSupport);

}
