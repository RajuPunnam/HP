package com.techouts.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.ParseException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.techouts.service.DataMoveService;
import com.techouts.service.FtpFileUploadService;
import com.techouts.service.MachineIpService;
import com.techouts.service.SkuAvailWorkbook;
import com.techouts.service.SkuAvalibility;

/**
 * class for calculating sku avilability and pipeline
 * 
 * @author P.raju
 *
 */
@Controller
public class HomepageController {
	private final static Logger LOGGER = Logger
			.getLogger(HomepageController.class);
	@Autowired
	private SkuAvalibility skuAvalibility;
	@Autowired
	private FtpFileUploadService ftpFileUploadService;
	@Autowired
	private SkuAvailWorkbook skuAvailWorkbook;
	@Autowired
	private DataMoveService dataMoveService;
	@Autowired
	private MachineIpService machineIpservice;

	/**
	 * method for caculating sku avil without bom
	 * 
	 * @return Replica
	 * @throws UnknownHostException
	 */
	@RequestMapping(value = "/UpdatekuAvilwithoutbom")
	public ModelAndView calculateSkuAvialabilityWithoutBom()
			throws UnknownHostException {
		try {
			calculateSkuAvailability();
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
		}
		return new ModelAndView("Replica", "machineip",
				machineIpservice.getMachineIpAddress());
	}

	/**
	 * method for caculating sku avil with bom
	 * 
	 * @return Replica
	 * @throws UnknownHostException
	 */
	@RequestMapping("/UpdatekuAvilwithbom")
	public ModelAndView calculateSkuAvilaWithBom() throws UnknownHostException {
		try {
			skuAvalibility.generateFinalQty();
			calculateSkuAvailability();
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
		}
		return new ModelAndView("Replica", "machineip",
				machineIpservice.getMachineIpAddress());
	}

	public void calculateSkuAvailability() throws FileNotFoundException,
			ParseException, UnknownHostException, InterruptedException {
		skuAvalibility.createPipeLineCollection();
		skuAvalibility.generateSkuAvilability(true);
		LOGGER.info("SKU availability Compleated");
		FTPClient ftpClient = new FTPClient();
		if (ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (Exception exception) {
				LOGGER.error("ftp logout exception" + exception.getMessage());
			}
		}
		File file = skuAvailWorkbook.generateSkuAvAvailWorkBook();
		LOGGER.info("got work book");
		if (ftpFileUploadService.getUploadSkuAvailToFtp(file)) {
			LOGGER.info("file upload to ftp sucefully");
		}
	}

	/**
	 * method for moving data from staging to production
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/movedata")
	public String getMoveDataFromStagToProduction(Model model) {
		model.addAttribute("replicaStatusMap",
				dataMoveService.moveProceessCollectionDataToProduction());
		return "ReplicaStatus";
	}

	/**
	 * method for replicating
	 * 
	 * @param model
	 * @return
	 * @throws UnknownHostException
	 */
	@RequestMapping(value = "/replication")
	public ModelAndView getReplicaPage(Model model) throws UnknownHostException {
		model.addAttribute("machineip", machineIpservice.getMachineIpAddress());
		return new ModelAndView("Replica");
	}

	/**
	 * method for refresh cache
	 * 
	 * @return cache
	 */
	@RequestMapping("/cacheRefresh")
	public String refreshCache() {
		return "cache";
	}
}
