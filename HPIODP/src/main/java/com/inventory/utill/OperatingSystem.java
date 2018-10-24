package com.inventory.utill;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


@Service
public class OperatingSystem
{
	private static Logger LOGGER = Logger.getLogger(OperatingSystem.class);
	@Resource(name = "myProps")
	private Properties properties;
	String osDownloadPath;

	@PostConstruct
	public void generatingDownloadFolder()
	{
		if (getOs().equals("windows"))
		{
			osDownloadPath = properties.getProperty("drive.name");
		} else if (getOs().equals("linux"))
		{
			osDownloadPath = properties.getProperty("linux.os.name.drive");
		}
		LOGGER.info("os download path is " + osDownloadPath);
	}

	public String getOs()
	{
		String osType = "";
		String operSys = System.getProperty("os.name").toLowerCase();
		if (operSys.contains("win"))
		{

			osType = "windows";
		} else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix"))
		{

			osType = "linux";
		} else if (operSys.contains("mac"))
		{

			osType = "mac";
		} else if (operSys.contains("sunos"))
		{

			osType = "solaris";
		}
		LOGGER.info("operating system is " + osType);
		return osType;
	}

	public String getOperatoingSystemDownloadPath()
	{
		return osDownloadPath;
	}
}

