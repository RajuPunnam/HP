package com.techouts.hp.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.techouts.hp.service.impl.AmlavCostedBomReportSummaryServiceImpl;
import com.techouts.hp.service.impl.BomAvReportPeriodServiceImpl;
import com.techouts.hp.service.impl.BomFullReportPeriodServiceImpl;
import com.techouts.hp.service.impl.FtpMailService;
import com.techouts.hp.service.impl.IntendedBomServiceImpl;
import com.techouts.hp.service.impl.StatusService;
import com.techouts.hp.util.OSUtil;
import com.techouts.hp.workerthread.DoiWorkerThread;
import com.techouts.hp.workerthread.PcOpenOrdersWorkerThread;
import com.techouts.hp.workerthread.PcShipmentsToHpWorkerThread;
import com.techouts.hp.workerthread.PoFromHpWorkerThread;
import com.techouts.hp.workerthread.PpbPcWorker;

/**
 * DataProcessController reponsible to start all threads
 * 
 * @author raju.p
 *
 */
@Controller
public class DataProcessController extends AbstractDataProcessController {
	private static final Logger LOGGER = Logger
			.getLogger(DataProcessController.class);
	@Autowired
	private OSUtil oSUtil;
	@Autowired
	private FtpMailService ftpMailService;
	@Autowired
	private StatusService statusService;

	@RequestMapping(value = "/rawdataprocess", method = RequestMethod.GET)
	public String startAllRawDataProcessThreads(HttpServletResponse response,
			Model model) throws InterruptedException, IOException,
			MessagingException {
		synchronized (this) {
			getThreadPoolTaskExecutor().execute(getDoiWorkerThread());
			getThreadPoolTaskExecutor().execute(getPcOpenOrdersWorkerThread());
			getThreadPoolTaskExecutor().execute(getPcShipmentsWorkerThread());
			getThreadPoolTaskExecutor().execute(getPoFromHpWorkerThread());
			getThreadPoolTaskExecutor().execute(getPpbWorker());

			for (int i = 1; i <= 30; i++) {
				TimeUnit.MINUTES.sleep(1);
				if (getDoiWorkerThread()
						.getDoiDownloadMethodCompleationStatus()) {

					if (getPcOpenOrdersWorkerThread()
							.getPcOpenOrdersCompleationStatus()) {
						if (getPcShipmentsWorkerThread()
								.getPcshipmentsToHpMethodCompleationStatus()) {
							if (getPoFromHpWorkerThread()
									.getPofromHpMethodCompleationStatus()) {
								if (getPpbWorker()
										.getConsumptionPcDownloadMethodCompleationStatus()) {
									LOGGER.info("All Threads compleated ");
									break;
								}
							}
						}
					}
				}

			}
			ftpMailService.sendStatusMail(statusService
					.getCurentDayUploadedDataFile());
			model.addAttribute("machineip", oSUtil.getIP());
		}
		return "Paiodataprocess";

	}

	@RequestMapping(value = "/rawdatauploadstatus", method = RequestMethod.GET)
	public String getFileUploadStatus(Model model) {
		model.addAttribute("currentdayStatus", getStatusService()
				.getCurrentDayUploadedFilesList());
		return "Reportsummary";
	}

	@RequestMapping(value = "/PaIoDataprocess", method = RequestMethod.GET)
	public ModelAndView paioDataprocess() throws UnknownHostException {

		return new ModelAndView("Paiodataprocess", "machineip", oSUtil.getIP());
	}

	@RequestMapping(value = "/UpdateskuAvil", method = RequestMethod.GET)
	public String calculateSkuAvilabilityWithAndWithoutBom(Model model)
			throws UnknownHostException {
		model.addAttribute("machineip", oSUtil.getIP());
		return "PA";
	}

	@RequestMapping(value = "/home")
	public String getHomePage(Model model) throws UnknownHostException {
		model.addAttribute("machineip", oSUtil.getIP());
		return "Paiodataprocess";
	}

	@RequestMapping(value = "/loadbomfiles", method = RequestMethod.GET)
	public String loadBomFiles(Model model) throws UnknownHostException {
		model.addAttribute("machineip", oSUtil.getIP());
		return "Bom";
	}

	@RequestMapping(value = "/intendedbom", method = RequestMethod.GET)
	public String loadIntendedBomFiles(Model model) {
		getIntendedBomServiceImpl().processIntendebBomFile();
		model.addAttribute("currentdayStatus", getStatusService()
				.getCurrentDayUploadedFilesList());
		return "Reportsummary";
	}

	@RequestMapping(value = "/amlavCostedBom", method = RequestMethod.GET)
	public String loadamlavCostedBom(Model model) throws ParseException {
		getAmlavCostedBomReportSummaryServiceImpl()
				.getLoadAmlavCostedBomReportSummaryFiles();
		model.addAttribute("currentdayStatus", getStatusService()
				.getCurrentDayUploadedFilesList());
		return "Reportsummary";
	}

	@RequestMapping(value = "/bomavreportperiod", method = RequestMethod.GET)
	public String loadBomAvReportPeriod(Model model) throws ParseException {
		getBomAvReportPeriodServiceImpl().getLoadBomAvReportPeriodFiles();
		model.addAttribute("currentdayStatus", getStatusService()
				.getCurrentDayUploadedFilesList());
		return "Reportsummary";
	}

	@RequestMapping(value = "/bomfullreportperiod", method = RequestMethod.GET)
	public String loadBomFullReportPeriod(Model model) throws ParseException {
		getBomFullReportPeriodServiceImpl().getLoadBomFullReportPeriodFiles();
		model.addAttribute("currentdayStatus", getStatusService()
				.getCurrentDayUploadedFilesList());
		return "Reportsummary";
	}

}