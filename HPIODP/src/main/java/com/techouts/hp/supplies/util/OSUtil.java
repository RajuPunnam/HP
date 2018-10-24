package com.techouts.hp.supplies.util;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
/**
 * OSUtil class for findout operating system
 * @author raju.p
 *
 */
@Component
public class OSUtil {
	private static Logger LOGGER = Logger.getLogger(OSUtil.class);
	@Resource(name = "myProps")
	private Properties properties;
	private String osDownloadPath;
	private static String machineIp;
	private static final String PROD_PRIVATE_IP = "192.168.2.98";
	private static final String PROD_PUBLIC_IP = "proc.techouts.com";
	private static final String WINDOWS = "windows";
	private static final String LINUX = "linux";
	private static final String MAC = "mac";
	private static final String SOLARIS = "solaris";

	enum OSTypes {
		windows, linux, solaris, mac
	}
/**
 * method to identify machine IP address
 * @throws UnknownHostException
 */
	@PostConstruct
	private void getMachineIpAddress() throws UnknownHostException {
		String inetAddress = Inet4Address.getLocalHost().getHostAddress();
		if (inetAddress.equals(PROD_PRIVATE_IP)) {
			machineIp = PROD_PUBLIC_IP;
		} else {
			machineIp = inetAddress;
		}

	}
/**
 * used to generate download path based on OS
 */
	@PostConstruct
	private void generateDownloadPath() {
		if (findOs().equals(WINDOWS)) {
			osDownloadPath = properties.getProperty("windows.os.drive");
		} else if (findOs().equals(LINUX)) {
			osDownloadPath = properties.getProperty("linux.os.drive");
		}
		LOGGER.info("os download path is " + osDownloadPath);
	}
/**
 * method to identify the operating system
 * @return operSys
 */
	private String findOs() {
		String operSys = System.getProperty("os.name").toLowerCase();
		if (operSys.contains("win")) {
			return WINDOWS;
		} else if (operSys.contains("nix") || operSys.contains("nux")
				|| operSys.contains("aix")) {
			return LINUX;
		} else if (operSys.contains("mac")) {
			return MAC;
		} else if (operSys.contains("sunos")) {
			return SOLARIS;
		}
		return null;
	}

	public String downloadDirectory() {
		return osDownloadPath;
	}

	public String getIP() {
		return machineIp;
	}
}
