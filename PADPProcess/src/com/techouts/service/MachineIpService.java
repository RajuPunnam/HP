package com.techouts.service;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

/**
 * class for identifying ip address
 * 
 * @author TO-OWDG-02
 *
 */
@Service
public class MachineIpService {
	public String getMachineIpAddress() throws UnknownHostException {
		String machineIp = null;
		String inetAddress = Inet4Address.getLocalHost().getHostAddress();
		if (inetAddress.equals("192.168.2.98")) {
			machineIp = "proc.techouts.com";
		} else {
			machineIp = inetAddress;
		}
		return machineIp;
	}
}
