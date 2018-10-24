package com.techouts.hp.service.impl;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.techouts.hp.dao.DataDao;
import com.techouts.hp.dto.PpbPcPojo;
import com.techouts.hp.pojo.FileUploadInfo;
import com.techouts.hp.service.DataService;
/**
 * PpbPcServiceImpl class to handle PPB files
 * @author raju.p
 *
 */
public class PPBPcServiceImpl implements DataService {
	private final static Logger LOGGER = Logger.getLogger(PPBPcServiceImpl.class);
	@Resource(name = "dataDaoImpl")
	private DataDao dataDaoImpl;
	@Resource(name = "myProps")
	private Properties properties;
	private static final SimpleDateFormat RDF = new SimpleDateFormat(
			"MM-dd-yyyy");
	private static final SimpleDateFormat EDF = new SimpleDateFormat("dd/MM/yyyy");
	private final String CONSUMPTION_PC_HEADER[] = { "Codigo do Item",
			"Description", "Alm", "DataTrans", "Tipo Transacao", "No OP", "SKU",
			"Total da OP", "Qtd Baixa/Receb", "Saldo da OP", "Hr Trans",
			"Hr +4 hrs", "Retrab", "Login code", "Est apÃ³s Trans" };
	List<String> consumptionHeaderList = Arrays.asList(CONSUMPTION_PC_HEADER);
	private static final String CODIGO_DO_ITEM = "Codigo do Item";
	private static final String DESCRIPTION = "Description";
	private static final String ALM = "Alm";
	private static final String DATATTRANS = "DataTrans";
	private static final String TIPO_TRANSCO = "Tipo Transacao";
	private static final String NO_OP = "No OP";
	private static final String SKU = "SKU";
	private static final String TOTAL_DA_OP = "Total da OP";
	private static final String Qtd_Baixa_Receb = "Qtd Baixa/Receb";
	private static final String SALDO_DA_OP = "Saldo da OP";
	private static final String HR_TRANS = "Hr Trans";
	private static final String Hr_PLUS_FOUR_hrs = "Hr +4 hrs";
	private static final String RETRAB = "Retrab";
	private static final String LOGIN_CODE = "Login code";
	private static final String EST_AP_TRANS = "Est apÃ³s Trans";

	public FileUploadInfo readDataFromFile(File inputLocation, FTPFile file) {
		CSVParser csvFileParser = null;
		List<PpbPcPojo> consumptionPcList = null;
		FileUploadInfo fileUploadInfo = new FileUploadInfo();
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.withDelimiter(';').withAllowMissingColumnNames(true);
		String fileDate = getFileDate(file);
		try {
			csvFileParser = new CSVParser(new FileReader(inputLocation
					+ File.separator + file.getName()), csvFileFormat);
			if (!findHeaders(csvFileParser.getHeaderMap())) {
				LOGGER.warn("Header not found,inplace of static header placed [["+CONSUMPTION_PC_HEADER+"]].need to check data");
				csvFileParser = null;
				csvFileFormat = CSVFormat.DEFAULT
						.withHeader(CONSUMPTION_PC_HEADER).withDelimiter(';')
						.withAllowMissingColumnNames(true);
				csvFileParser = new CSVParser(new FileReader(inputLocation
						+ File.separator + file.getName()), csvFileFormat);
			}
		} catch (IllegalArgumentException illegalArgumentException) {
			LOGGER.warn("Duplicate Header found,inplace of static header placed [["+CONSUMPTION_PC_HEADER+"]].need to check data");
			csvFileParser = null;
			csvFileFormat = CSVFormat.DEFAULT.withHeader(CONSUMPTION_PC_HEADER)
					.withDelimiter(';').withAllowMissingColumnNames(true);
			try {
				csvFileParser = new CSVParser(new FileReader(inputLocation
						+ File.separator + file.getName()), csvFileFormat);
			} catch (Exception exception) {
				LOGGER.error(exception.getMessage());
			}
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
		}
		consumptionPcList = new ArrayList<PpbPcPojo>();
		try {
			List<CSVRecord> consumptioPcRecordsList = csvFileParser
					.getRecords();
			if (consumptioPcRecordsList != null
					&& consumptioPcRecordsList.size() > 0) {
				for (int i = 0; i < consumptioPcRecordsList.size(); i++) {
					CSVRecord consumptionRecord = consumptioPcRecordsList
							.get(i);
					if (!consumptionHeaderList.contains(consumptionRecord
							.get(CODIGO_DO_ITEM))
							&& !consumptionHeaderList
									.contains(consumptionRecord
											.get(DESCRIPTION))) {
						PpbPcPojo consumption = new PpbPcPojo();
						consumption.setFileDate(fileDate);

						if (consumptionRecord.isMapped(CODIGO_DO_ITEM)) {
							if (consumptionRecord.isSet(CODIGO_DO_ITEM)) {
								consumption.setItem_code(consumptionRecord.get(
										CODIGO_DO_ITEM).trim());
							}

						}
						if (consumptionRecord.isMapped(DESCRIPTION)) {
							if (consumptionRecord.isSet(DESCRIPTION)) {
								consumption.setDescription(consumptionRecord
										.get(DESCRIPTION).trim());
							}
						}
						if (consumptionRecord.isMapped(ALM)) {
							if (consumptionRecord.isSet(ALM)) {
								String alm = consumptionRecord.get(ALM);
								double ALM = 0;
								if (!StringUtils.isEmpty(alm)) {
									alm = replaceAllSpecialCharacters(alm);
									if (alm.trim().matches("^[0-9]*$*")) {
										ALM = Double.parseDouble(alm);
									}
								}
								consumption.setAlm(ALM);
							}
						}
						if (consumptionRecord.isMapped(DATATTRANS)) {
							if (consumptionRecord.isSet(DATATTRANS)) {
								String dataTrans = consumptionRecord
										.get(DATATTRANS);
								if (!StringUtils.isEmpty(dataTrans)) {
									dataTrans = replaceAllSpecialCharacters(dataTrans);
									if (dataTrans.matches("^[0-9/]*$*")) {
										consumption
												.setDataTrans(RDF.format(EDF
														.parse(dataTrans)));
									} else {
										consumption.setDataTrans(dataTrans
												.trim());
									}
								}
							}
						}
						if (consumptionRecord.isMapped(TIPO_TRANSCO)) {
							if (consumptionRecord.isSet(TIPO_TRANSCO)) {
								consumption
										.setTransaction_type(consumptionRecord
												.get(TIPO_TRANSCO).trim());
							}
						}

						if (consumptionRecord.isMapped(NO_OP)) {
							if (consumptionRecord.isSet(NO_OP)) {
								String IP = consumptionRecord.get(NO_OP);
								double ip = 0;
								if (!StringUtils.isEmpty(IP)) {
									IP = replaceAllSpecialCharacters(IP);
									if (IP.matches("^[0-9]*$*")) {
										ip = Double.parseDouble(IP);
									}

								}
								consumption.setIP(ip);
							}
						}
						if (consumptionRecord.isMapped(SKU)) {
							if (consumptionRecord.isSet(SKU)) {
								consumption.setSKU(consumptionRecord.get(SKU)
										.trim());
							}
						}
						if (consumptionRecord.isMapped(TOTAL_DA_OP)) {
							if (consumptionRecord.isSet(TOTAL_DA_OP)) {
								String total_OP = consumptionRecord
										.get(TOTAL_DA_OP);
								double totalOP = 0;
								if (!StringUtils.isEmpty(total_OP))
									total_OP = replaceAllSpecialCharacters(total_OP);
								{
									if (total_OP.matches("^[0-9]*$*")) {
										totalOP = Double.parseDouble(total_OP);
									}
								}
								consumption.setTotal_OP(totalOP);
							}
						}

						if (consumptionRecord.isMapped(Qtd_Baixa_Receb)) {
							if (consumptionRecord.isSet(Qtd_Baixa_Receb)) {
								String low_Qty_Receive = consumptionRecord
										.get(Qtd_Baixa_Receb);
								double lowQtyReceive = 0;
								if (!StringUtils.isEmpty(low_Qty_Receive)) {
									low_Qty_Receive = replaceAllSpecialCharacters(low_Qty_Receive);
									if (low_Qty_Receive.matches("^[0-9]*$*")) {
										lowQtyReceive = Double
												.parseDouble(low_Qty_Receive);
									}
								}
								consumption.setLow_Qty_Receive(lowQtyReceive);
							}
						}
						if (consumptionRecord.isMapped(SALDO_DA_OP)) {
							if (consumptionRecord.isSet(SALDO_DA_OP)) {
								String OP_balance = consumptionRecord
										.get(SALDO_DA_OP);
								double opBalance = 0;
								if (!StringUtils.isEmpty(OP_balance)) {
									OP_balance = replaceAllSpecialCharacters(OP_balance);
									if (OP_balance.matches("^[0-9]*$*")) {
										opBalance = Double
												.parseDouble(OP_balance);
									}
								}
								consumption.setOP_balance(opBalance);
							}
						}
						if (consumptionRecord.isMapped(HR_TRANS)) {
							if (consumptionRecord.isSet(HR_TRANS)) {
								String Hr_Trans = consumptionRecord
										.get(HR_TRANS);
								consumption.setHr_Trans(Hr_Trans.trim());

							}
						}
						if (consumptionRecord.isMapped(Hr_PLUS_FOUR_hrs)) {
							if (consumptionRecord.isSet(Hr_PLUS_FOUR_hrs)) {
								String Hr_4_hrs = consumptionRecord
										.get(Hr_PLUS_FOUR_hrs);
								consumption.setHr__4_hrs(Hr_4_hrs.trim());
							}

						}

						if (consumptionRecord.isMapped(RETRAB)) {
							if (consumptionRecord.isSet(RETRAB)) {
								consumption.setRetrab(consumptionRecord.get(
										RETRAB).trim());
							}
						}
						if (consumptionRecord.isMapped(LOGIN_CODE)) {
							if (consumptionRecord.isSet(LOGIN_CODE)) {
								consumption.setLogin_code(consumptionRecord
										.get(LOGIN_CODE).trim());
							}
						}
						if (consumptionRecord.isMapped(EST_AP_TRANS)) {
							if (consumptionRecord.isSet(EST_AP_TRANS)) {
								String Est_after_Trans = consumptionRecord
										.get(EST_AP_TRANS);
								double estAfterTrans = 0.0;
								if (!StringUtils.isEmpty(Est_after_Trans)) {
									if (Est_after_Trans.matches("^[0-9]*$*")) {
										estAfterTrans = Double
												.parseDouble(Est_after_Trans);
									}
								}
								consumption.setEst_after_Trans(estAfterTrans);
							}
						}
						consumptionPcList.add(consumption);
					}
				}
				if (consumptionPcList != null && consumptionPcList.size() > 0) {
					if (dataDaoImpl.insertFileData(consumptionPcList)) {
						fileUploadInfo
								.setRecordsCount(consumptionPcList.size());
						fileUploadInfo.setUploadStatus(true);
					}

				}

			}
		} catch (Exception exception) {
			LOGGER.info(exception.getMessage());
		}

		return fileUploadInfo;
	}
/**
 * removes special charaters
 * @param value
 * @return 
 */
	public String replaceAllSpecialCharacters(String value) {
		return value.replaceAll("[',',' ','.']", "").trim();
	}
/**
 * method for findout header
 * @param headersMap
 * @return boolean
 */
	public boolean findHeaders(Map<String, Integer> headersMap) {
		for (Entry<String, Integer> header : headersMap.entrySet()) {
			if (!StringUtils.isEmpty(header.getKey())
					&& header.getKey().equals(CODIGO_DO_ITEM)) {
				return true;
			}
		}
		return false;
	}
/**
 * method for identifing file date from file name
 * @param ftpFile
 * @return
 */
	public String getFileDate(FTPFile ftpFile) {
		String file = ftpFile.getName().substring(6, 14);
		return file.substring(4, 6) + "-" + file.substring(6, 8) + "-"
				+ file.substring(0, 4);
	}

}