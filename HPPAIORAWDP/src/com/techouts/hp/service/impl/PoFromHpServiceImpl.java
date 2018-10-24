package com.techouts.hp.service.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;

import javax.annotation.Resource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.techouts.hp.dao.DataDao;
import com.techouts.hp.dto.PoFromHpPojo;
import com.techouts.hp.pojo.FileUploadInfo;
import com.techouts.hp.service.DataService;
/**
 * PoFromHpServiceImpl class responsible to handle POFromhp files
 * @author raju.p
 *
 */
public class PoFromHpServiceImpl implements DataService
{

	private  static final Logger LOGGER = Logger.getLogger(PoFromHpServiceImpl.class);
	private static final SimpleDateFormat RDF = new SimpleDateFormat(
			"MM-dd-yyyy");
	private static final SimpleDateFormat EDF = new SimpleDateFormat("dd/MM/yyyy");
	@Resource(name = "dataDaoImpl")
	private DataDao dataDaoImpl;
	@Resource(name = "myProps")
	private Properties properties;

	private static final String POFROM_HP_HEADER[] = { "PO Received Date", "Aging",
			"PO", "SO", "Customer", "PL", "SKU", "Family", "Total", "Delta",
			"Status", "Novo ETS", "Split", "OM Comments" };
	private static final List<String> pofromhpHeadersList = Arrays.asList(POFROM_HP_HEADER);
	private static final String PO_RECEIVED_DATE="PO Received Date";
	private static final String AGEING="Aging";
	private static final String PO="PO";
	private static final String SO="SO";
	private static final String CUSTOMER="Customer";
	private static final String PL="PL";
	private static final String SKU="SKU";
	private static final String FAMILY="Family";
	private static final String TOTAL="Total";
	private static final String DELTA="Delta";
	private static final String STATUS="Status";
	private static final String NOVO_ETS="Novo ETS";
	private static final String SPLIT="Split";
	private static final String OM_COMENTS="OM Comments";

	public FileUploadInfo readDataFromFile(File inputLocation, FTPFile file) {
		CSVParser csvFileParser = null;
		List<PoFromHpPojo> pofromHpList = null;
		FileUploadInfo fileUploadInfo=new FileUploadInfo();
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.withDelimiter(';').withAllowMissingColumnNames(true)
				.withIgnoreEmptyLines(true);
		String fileDate = getPoFromHpFileDate(file);

		try {
			csvFileParser = new CSVParser(new FileReader(inputLocation
					+ File.separator + file.getName()), csvFileFormat);
			if (!findHeaders(csvFileParser.getHeaderMap())) {
				LOGGER.warn("Header not found,inplace of static header placed [["+POFROM_HP_HEADER+"]].need to check data");
				csvFileFormat = null;
				csvFileFormat = CSVFormat.DEFAULT.withHeader(POFROM_HP_HEADER)
						.withDelimiter(';').withAllowMissingColumnNames()
						.withIgnoreEmptyLines(true);
				csvFileParser = new CSVParser(new FileReader(inputLocation
						+ File.separator + file.getName()), csvFileFormat);
			}
		} catch (IllegalArgumentException illegalArgumentException) {
			LOGGER.warn("Duplicate Header found,inplace of static header placed [["+POFROM_HP_HEADER+"]].need to check data");
			csvFileFormat = null;
			csvFileFormat = CSVFormat.DEFAULT.withHeader(POFROM_HP_HEADER)
					.withDelimiter(';').withAllowMissingColumnNames()
					.withIgnoreEmptyLines(true);
			try {
				csvFileParser = new CSVParser(new FileReader(inputLocation
						+ File.separator + file.getName()), csvFileFormat);
			} catch (Exception exception) {
				LOGGER.error(exception.getMessage());
			}
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
		}
		try {
			List<CSVRecord> orderRecordsList = csvFileParser.getRecords();
			if (orderRecordsList != null && orderRecordsList.size() > 0) {
				pofromHpList = new ArrayList<PoFromHpPojo>();
				for (int i = 0; i < orderRecordsList.size(); i++) {
					CSVRecord orderRecord = orderRecordsList.get(i);
					if (!pofromhpHeadersList.contains(orderRecord.get(PO_RECEIVED_DATE))) {
						PoFromHpPojo poFromHpDto = new PoFromHpPojo();
						poFromHpDto.setFile_Date(fileDate);
						if (orderRecord.isMapped(PO_RECEIVED_DATE)) {
							if (orderRecord.isSet(PO_RECEIVED_DATE)) {
								String poReceivedDate = orderRecord
										.get(PO_RECEIVED_DATE);
								if (!StringUtils.isEmpty(poReceivedDate)) {
									poReceivedDate = replaceAllSpecialCharacters(poReceivedDate);
										if (poReceivedDate
												.matches("^[0-9/]*$*")) {
											poFromHpDto
													.setPO_Received_Date(RDF
															.format(EDF
																	.parse(poReceivedDate)));
										} else {
											poFromHpDto
													.setPO_Received_Date(poReceivedDate);
										}
								}
							}
						}
						if (orderRecord.isMapped(AGEING)) {
							if (orderRecord.isSet(AGEING)) {
								double aging = 0;
								String agingInString = orderRecord
										.get(AGEING);
								if (!StringUtils.isEmpty(agingInString)) {
									agingInString = replaceAllSpecialCharacters(agingInString);
									if (agingInString.matches("^[0-9]*$*")) {
										aging = Double.valueOf(agingInString);
									}
								}
								poFromHpDto.setAging(aging);
							}
						}
						if (orderRecord.isMapped(PO)) {
							if (orderRecord.isSet(PO)) {
								String poInString = orderRecord
										.get(PO);
								double po = 0;
								if (!StringUtils.isEmpty(poInString)) {
									poInString = replaceAllSpecialCharacters(poInString);
									if (poInString.matches("^[0-9]*$*")) {
										po = Double.parseDouble(poInString);
									}
								}
								poFromHpDto.setPO(po);
							}
						}
						if (orderRecord.isMapped(SO)) {
							if (orderRecord.isSet(SO)) {
								String soInString = orderRecord
										.get(SO);
								double so = 0;
								if (!StringUtils.isEmpty(soInString)) {
									soInString = replaceAllSpecialCharacters(soInString);
									if (soInString.matches("^[0-9]*$*")) {
										so = Double.parseDouble(soInString);
									}

								}
								poFromHpDto.setSO(so);
							}
						}
						if (orderRecord.isMapped(CUSTOMER)) {
							if (orderRecord.isSet(CUSTOMER)) {
								poFromHpDto.setCustomer(orderRecord
										.get(CUSTOMER).trim());
							}
						}
						if (orderRecord.isMapped(PL)) {
							if (orderRecord.isSet(PL)) {
								poFromHpDto.setPL(orderRecord
										.get(PL).trim());
							}
						}
						if (orderRecord.isMapped(SKU)) {
							if (orderRecord.isSet(SKU)) {
								poFromHpDto.setSKU(orderRecord
										.get(SKU).trim());
							}
						}
						if (orderRecord.isMapped(FAMILY)) {
							if (orderRecord.isSet(FAMILY)) {
								poFromHpDto.setFamily(orderRecord
										.get(FAMILY).trim());
							}
						}
						if (orderRecord.isMapped(TOTAL)) {
							if (orderRecord.isSet(TOTAL)) {
								double total = 0;
								String totalInString = orderRecord
										.get(TOTAL);
								if (!StringUtils.isEmpty(totalInString)) {
									totalInString = replaceAllSpecialCharacters(totalInString);
									if (totalInString.matches("^[0-9]*$*")) {
										total = Double.valueOf(totalInString);
									}
								}
								poFromHpDto.setTotal(total);
							}
						}
						if (orderRecord.isMapped(DELTA)) {
							if (orderRecord.isSet(DELTA)) {
								int delta = 0;
								String deltaInString = orderRecord
										.get(DELTA);
								if (!StringUtils.isEmpty(deltaInString)) {
									deltaInString = replaceAllSpecialCharacters(deltaInString);
									if (deltaInString.matches("^[0-9]*$*")) {
										delta = Integer.valueOf(deltaInString);
									}
								}
								poFromHpDto.setDelta(delta);
							}
						}
						if (orderRecord.isMapped(STATUS)) {
							if (orderRecord.isSet(STATUS)) {
								poFromHpDto.setStatus(orderRecord
										.get(STATUS).trim());
							}
						}
						if (orderRecord.isMapped(NOVO_ETS)) {
							if (orderRecord.isSet(NOVO_ETS)) {
								String novoEts = orderRecord
										.get(NOVO_ETS);
								if (!StringUtils.isEmpty(novoEts)) {
									novoEts = replaceAllSpecialCharacters(novoEts);
									if (!StringUtils.isEmpty(novoEts) && novoEts.matches("^[0-9/]*$*")) {
										poFromHpDto.setETS(RDF
												.format(EDF
														.parse(novoEts)));
									} else {
										poFromHpDto.setETS(novoEts);
									}
								}
							}
						}

						if (orderRecord.isMapped(SPLIT)) {
							if (orderRecord.isSet(SPLIT)) {
								poFromHpDto.setSplit(orderRecord
										.get(SPLIT).trim());
							}
						}
						if (orderRecord.isMapped(OM_COMENTS)) {
							if (orderRecord.isSet(OM_COMENTS)) {
								poFromHpDto.setOm_Comments(orderRecord
										.get(OM_COMENTS).trim());
							}
						}
						pofromHpList.add(poFromHpDto);
					}
				}
				if (pofromHpList != null && pofromHpList.size() > 0) {
					if(dataDaoImpl.insertFileData(pofromHpList))
					{
						fileUploadInfo.setRecordsCount(pofromHpList.size());
						fileUploadInfo.setUploadStatus(true);	
					}
				}
				
			}
		} catch (NullPointerException nullPointerException) {
			LOGGER.error(nullPointerException.getMessage());
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
		} finally {
			if (csvFileParser != null) {
				try {
					csvFileParser.close();
				} catch (IOException e) {

					LOGGER.error(e.getMessage());
				}
			}
		}
		return fileUploadInfo;
	}
/**
 * get file date from file name
 * @param file
 * @return
 */
	public String getPoFromHpFileDate(FTPFile file) {
		String fileDate = file.getName().substring(2, 10);
		return fileDate.substring(4, 6) + "-" + fileDate.substring(6, 8) + "-"
				+ fileDate.substring(0, 4);
	}
	/**
	 * removes special characters
	 * @param value
	 * @return
	 */
	public String replaceAllSpecialCharacters(String value)
	{
		return value.replaceAll("[',',' ','.']", "").trim();
	}
	/**
	 * method for finding header
	 * @param headerMap
	 * @return
	 */
	public boolean findHeaders(Map<String, Integer> headerMap) {
		for (Entry<String, Integer> header : headerMap.entrySet()) {
			if(!StringUtils.isEmpty(header.getKey()) && header.getKey().equals(PO))
			{
				return true;
			}
		}
		return false;
	}

}