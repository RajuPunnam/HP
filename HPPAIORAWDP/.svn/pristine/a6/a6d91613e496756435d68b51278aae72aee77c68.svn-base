package com.techouts.hp.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.techouts.hp.service.impl.AmlavCostedBomReportSummaryServiceImpl;
import com.techouts.hp.service.impl.BomAvReportPeriodServiceImpl;
import com.techouts.hp.service.impl.BomFullReportPeriodServiceImpl;
import com.techouts.hp.service.impl.IntendedBomServiceImpl;
import com.techouts.hp.service.impl.StatusService;
import com.techouts.hp.workerthread.DoiWorkerThread;
import com.techouts.hp.workerthread.PcOpenOrdersWorkerThread;
import com.techouts.hp.workerthread.PcShipmentsToHpWorkerThread;
import com.techouts.hp.workerthread.PoFromHpWorkerThread;
import com.techouts.hp.workerthread.PpbPcWorker;

public abstract class AbstractDataProcessController {
	@Autowired
	private DoiWorkerThread doiWorkerThread;
	@Autowired
	private PcOpenOrdersWorkerThread pcOpenOrdersWorkerThread;
	@Autowired
	private PcShipmentsToHpWorkerThread pcShipmentsWorkerThread;
	@Autowired
	private PoFromHpWorkerThread poFromHpWorkerThread;
	@Autowired
	private PpbPcWorker ppbWorker;
	@Autowired
	private StatusService statusService;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	@Resource(name="intendedBomServiceImpl")
	private IntendedBomServiceImpl intendedBomServiceImpl;
	@Resource(name="amlavCostedBomReportSummaryServiceImpl")
	private AmlavCostedBomReportSummaryServiceImpl amlavCostedBomReportSummaryServiceImpl;
	@Resource(name="bomAvReportPeriodServiceImpl")
	private BomAvReportPeriodServiceImpl bomAvReportPeriodServiceImpl;
	@Resource(name="bomFullReportPeriodServiceImpl")
	private BomFullReportPeriodServiceImpl bomFullReportPeriodServiceImpl;

	public DoiWorkerThread getDoiWorkerThread() {
		return doiWorkerThread;
	}
 
	public void setDoiWorkerThread(DoiWorkerThread doiWorkerThread) {
		this.doiWorkerThread = doiWorkerThread;
	}

	public PcOpenOrdersWorkerThread getPcOpenOrdersWorkerThread() {
		return pcOpenOrdersWorkerThread;
	}
	
	public void setPcOpenOrdersWorkerThread(
			PcOpenOrdersWorkerThread pcOpenOrdersWorkerThread) {
		this.pcOpenOrdersWorkerThread = pcOpenOrdersWorkerThread;
	}

	public PcShipmentsToHpWorkerThread getPcShipmentsWorkerThread() {
		return pcShipmentsWorkerThread;
	}

	public void setPcShipmentsWorkerThread(
			PcShipmentsToHpWorkerThread pcShipmentsWorkerThread) {
		this.pcShipmentsWorkerThread = pcShipmentsWorkerThread;
	}

	public PoFromHpWorkerThread getPoFromHpWorkerThread() {
		return poFromHpWorkerThread;
	}
	
	public void setPoFromHpWorkerThread(
			PoFromHpWorkerThread poFromHpWorkerThread) {
		this.poFromHpWorkerThread = poFromHpWorkerThread;
	}

	public PpbPcWorker getPpbWorker() {
		return ppbWorker;
	}

	public void setPpbWorker(PpbPcWorker ppbWorker) {
		this.ppbWorker = ppbWorker;
	}
	
	public StatusService getStatusService() {
		return statusService;
	}

	public void setStatusService(StatusService statusService) {
		this.statusService = statusService;
	}

	public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		return threadPoolTaskExecutor;
	}

	public void setThreadPoolTaskExecutor(
			ThreadPoolTaskExecutor threadPoolTaskExecutor) {
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
	}

	public IntendedBomServiceImpl getIntendedBomServiceImpl() {
		return intendedBomServiceImpl;
	}

	public void setIntendedBomServiceImpl(
			IntendedBomServiceImpl intendedBomServiceImpl) {
		this.intendedBomServiceImpl = intendedBomServiceImpl;
	}

	public AmlavCostedBomReportSummaryServiceImpl getAmlavCostedBomReportSummaryServiceImpl() {
		return amlavCostedBomReportSummaryServiceImpl;
	}

	public void setAmlavCostedBomReportSummaryServiceImpl(
			AmlavCostedBomReportSummaryServiceImpl amlavCostedBomReportSummaryServiceImpl) {
		this.amlavCostedBomReportSummaryServiceImpl = amlavCostedBomReportSummaryServiceImpl;
	}

	public BomAvReportPeriodServiceImpl getBomAvReportPeriodServiceImpl() {
		return bomAvReportPeriodServiceImpl;
	}

	public void setBomAvReportPeriodServiceImpl(
			BomAvReportPeriodServiceImpl bomAvReportPeriodServiceImpl) {
		this.bomAvReportPeriodServiceImpl = bomAvReportPeriodServiceImpl;
	}

	public BomFullReportPeriodServiceImpl getBomFullReportPeriodServiceImpl() {
		return bomFullReportPeriodServiceImpl;
	}
	
	public void setBomFullReportPeriodServiceImpl(
			BomFullReportPeriodServiceImpl bomFullReportPeriodServiceImpl) {
		this.bomFullReportPeriodServiceImpl = bomFullReportPeriodServiceImpl;
	}
}
