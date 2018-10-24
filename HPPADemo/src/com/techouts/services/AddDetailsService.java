package com.techouts.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.techouts.dao.ExportDataDao;
import com.techouts.pojo.AvAvailability;
import com.techouts.pojo.AvAvbail;
import com.techouts.pojo.DOInventory;
import com.techouts.pojo.PA_FLEX_SUPER_BOM;
import com.techouts.pojo.PipelineDoiPojo;
import com.techouts.pojo.PipelinePojo;
import com.techouts.pojo.PriAltBom;

@Service
@Scope("prototype")
public class AddDetailsService {
	private static final Logger LOG = Logger.getLogger(AddDetailsService.class);
	@Autowired
	private ExportDataDao exportDataDao;
	@Autowired
	private CartService cartService;
	@Autowired
	private CacheService cacheService;
	private static final Logger logger = Logger
			.getLogger(AddDetailsService.class.getName());
	// Create blank workbook
	XSSFWorkbook workbook;
	Map<String, Double> doiMap = new HashMap<String, Double>();
	Map<String, Double> avAvilabilityMap = new HashMap<String, Double>();

	public Workbook exportData(List<String> configurations,
			List<String> shortages, String skuId) {
		for (String conf : configurations) {
			LOG.info("conf :: " + conf);
		}
		for (String conf : shortages) {
			LOG.info("shortages :: " + conf);
		}

		workbook = new XSSFWorkbook();
		List<DOInventory> doiList = exportDataDao.getDoiData();
		List<AvAvailability> avAvlList = exportDataDao.getAvAvialbilityData();
		List<PipelinePojo> pipelineQtyList = exportDataDao
				.getDistinctSkuAvAvilability(skuId);
		List<PipelineDoiPojo> pipelineDoiQtyList = exportDataDao
				.getDistinctSkuDoiAvilability(skuId);

		Map<String, PipelinePojo> pipelineavAvilabilityMap = new HashMap<String, PipelinePojo>();

		// code to map av and its pipeline quantity from pipelineavavilability
		for (PipelinePojo pipeLineAvAvbail : pipelineQtyList) {
			if (pipeLineAvAvbail.getAvId() != null) {
				logger.info("in pipeLineAvAvbail ");
				pipelineavAvilabilityMap.put(pipeLineAvAvbail.getAvId(),
						pipeLineAvAvbail);
			}
		}
		Map<String, PipelineDoiPojo> pipelineDoiQtyMap = new HashMap<String, PipelineDoiPojo>();
		// code to map part and its pipeline quantity from pipeline doi
		for (PipelineDoiPojo pipeLineDOI : pipelineDoiQtyList) {
			if (pipeLineDOI.getPart() != null) {
				pipelineDoiQtyMap.put(pipeLineDOI.getPart(), pipeLineDOI);
			}

		}

		// Code to make a doi map with partnumber and quantity
		for (DOInventory doInventory : doiList) {
			doiMap.put(doInventory.getPartId(),
					(double) doInventory.getQuantity());
		}

		// Code to make a avavilability map with av and quantity
		for (AvAvailability avAvailability : avAvlList) {
			logger.info("AvAvailability");
			avAvilabilityMap.put(avAvailability.getAvId(),
					avAvailability.getAvbail());
		}

		List<PA_FLEX_SUPER_BOM> paSuperBomData = new ArrayList<PA_FLEX_SUPER_BOM>();
		paSuperBomData = getData(configurations);
		createWorkBookSheet(paSuperBomData, "configurations",
				pipelineavAvilabilityMap, pipelineDoiQtyMap);
		// creating second sheet for alternate avs
		createWorkBookSheetForAlternatives(paSuperBomData,
				"configurationsAlternateavavailability");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (shortages != null && !shortages.isEmpty()) {
			paSuperBomData.clear();
			paSuperBomData = getData(shortages);
			createWorkBookSheet(paSuperBomData, "shortages",
					pipelineavAvilabilityMap, pipelineDoiQtyMap);
			// creating next sheet for alternate avs
			createWorkBookSheetForAlternatives(paSuperBomData,
					"shortagesAlternateavavailability");
		}

		logger.info("Writesheet.xlsx written successfully");

		return workbook;

	}

	public List<PA_FLEX_SUPER_BOM> getData(List<String> avs) {
		List<PA_FLEX_SUPER_BOM> paSuperBomData = new ArrayList<PA_FLEX_SUPER_BOM>();
		List<PA_FLEX_SUPER_BOM> temp = new ArrayList<PA_FLEX_SUPER_BOM>();
		temp.addAll(exportDataDao.getSuberBomData(avs));
		int count = 0;
		for (PA_FLEX_SUPER_BOM bom : temp) {
			if (bom.getBomLevel().equalsIgnoreCase("0.1")) {
				LOG.info(":: " + bom.getAv());
			}
		}
		for (int i = 0; i < temp.size(); i++) {
			PA_FLEX_SUPER_BOM bom = new PA_FLEX_SUPER_BOM();
			bom = temp.get(i);
			if (bom.getBomLevel().equalsIgnoreCase("0.1")) {
				LOG.info(bom.getAv());
			}
			bom.setSno(++count);
			paSuperBomData.add(bom);
			if (bom.getBomType().equalsIgnoreCase("pri")) {
				Set<String> bompns = null;
				bompns = new HashSet<String>();
				for (int j = i + 1; j < temp.size(); j++) {
					PA_FLEX_SUPER_BOM bom1 = new PA_FLEX_SUPER_BOM();
					bom1 = temp.get(j);
					if (bom1.getBomType().equalsIgnoreCase("PRI")) {
						break;
					}
					bompns.add(bom1.getPartNumber());
				}
				for (PriAltBom s : exportDataDao.getAltPns(bom.getPartNumber())) {
					/*
					 * if(bompns.contains(s.getAltPart())){ //continue; }
					 */

					PA_FLEX_SUPER_BOM pabom = new PA_FLEX_SUPER_BOM();
					pabom.setPartNumber(s.getAltPart());
					pabom.setPartDecsription(s.getAltDes());
					pabom.setQtyPer(bom.getQtyPer());
					pabom.setAv(bom.getAv());
					pabom.setBomPosition(bom.getBomPosition());
					pabom.setIncludeOnScreen(bom.getIncludeOnScreen());
					pabom.setBomType("ALT");
					pabom.setMpc(bom.getMpc());
					pabom.setSno(++count);
					pabom.setBomLevel(String.valueOf(bom.getBomLevel()));
					pabom.setAvlScreenCommodity(bom.getAvlScreenCommodity());
					pabom.setCommodity(bom.getCommodity());
					pabom.setPn_SOURCE(s.getPn_SOURCE());
					// LOG.info(bom.getPartNumber()+":::::::"+s.getAltPart()+":::::::"+s.getPn_SOURCE());
					paSuperBomData.add(pabom);
				}
			}

		}

		Collections.sort(paSuperBomData, new Comparator<PA_FLEX_SUPER_BOM>() {

			@Override
			public int compare(PA_FLEX_SUPER_BOM o1, PA_FLEX_SUPER_BOM o2) {
				return o2.getSno() - o1.getSno();
			}
		});

		String priqty = null;
		int priQty = 0;
		  List<String> partsList=new ArrayList<String>();
		for (PA_FLEX_SUPER_BOM pa : paSuperBomData) {
			if(!partsList.contains(pa.getPartNumber()))
			{
			partsList.add(pa.getPartNumber());
			int level = Integer.parseInt(pa.getBomLevel().replaceAll("[0 , .]",
					""));
			if (level == 1) {
				continue;
			}

			if (pa.getBomType().equalsIgnoreCase("PRI")) {
				int qty = 0;
				Double pnqty = 0.0;
				if (doiMap.containsKey(pa.getPartNumber())) {
					pnqty = doiMap.get(pa.getPartNumber());
				}
				if (priqty != null) {
					qty = (int) (Integer.parseInt(priqty) + pnqty);
				} else {
					qty = (int) (0 + pnqty);
				}
				// LOG.info(qty);
				pa.setPriQty(String.valueOf(qty));
				priqty = null;
				priQty = 0;
			} else {
				Double pnqty = 0.0;
				if (doiMap.containsKey(pa.getPartNumber())) {
					pnqty = doiMap.get(pa.getPartNumber());
				}
				priQty = (int) (priQty + pnqty);
				priqty = String.valueOf(priQty);
			}
		}
		}
		Collections.sort(paSuperBomData, new Comparator<PA_FLEX_SUPER_BOM>() {

			@Override
			public int compare(PA_FLEX_SUPER_BOM o1, PA_FLEX_SUPER_BOM o2) {
				return o1.getSno() - o2.getSno();
			}
		});

		return paSuperBomData;
	}

	public void createWorkBookSheet(List<PA_FLEX_SUPER_BOM> paSuperBomData,
			String sheet, Map<String, PipelinePojo> pipelineavAvilabilityMap,
			Map<String, PipelineDoiPojo> pipelineDoiQtyMap) {
		Map<String, String> map = new HashMap<String, String>();
		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet(sheet);

		// Create row object
		XSSFRow row;
		// This data needs to be written
		int rowid = 0;
		int cellid = 0;
		row = spreadsheet.createRow(rowid);
		row.createCell(cellid++).setCellValue("SR NO");
		row.createCell(cellid++).setCellValue("BOM_LEVEL");
		row.createCell(cellid++).setCellValue("AV");
		row.createCell(cellid++).setCellValue("PART_NUMBER");
		row.createCell(cellid++).setCellValue("PN_SOURCE");
		row.createCell(cellid++).setCellValue("PRI_ALT_FLAG");

		row.createCell(cellid++).setCellValue("PART_DESCRIPTION");
		row.createCell(cellid++).setCellValue("COMMODITY");
		row.createCell(cellid++).setCellValue("AVBL_SCREEN_COMMODITY");

		row.createCell(cellid++).setCellValue("GROUP_SECONDARY");
		row.createCell(cellid++).setCellValue("SCREEN_COMMODITY_GROUP_DESC");
		row.createCell(cellid++).setCellValue("GROUP_SECONDARY_DESC");
		row.createCell(cellid++).setCellValue("INCLUDE_ON_SCREEN");
		// row.createCell(cellid++).setCellValue("LOCAL_IMPORT" );
		row.createCell(cellid++).setCellValue("BOM_POSITION");

		row.createCell(cellid++).setCellValue("MPC");
		row.createCell(cellid++).setCellValue("QTY_PER");
		row.createCell(cellid++).setCellValue("EFF_DATE");
		row.createCell(cellid++).setCellValue("END_DATE");
		row.createCell(cellid++).setCellValue("Part_Availability");
		row.createCell(cellid++).setCellValue("PRI_Final_Total");
		row.createCell(cellid++).setCellValue("Av_Availability");

		row.createCell(cellid++).setCellValue("15Days DOI");
		row.createCell(cellid++).setCellValue("15Days AV");
		row.createCell(cellid++).setCellValue("30Days DOI");
		row.createCell(cellid++).setCellValue("30Days AV");
		row.createCell(cellid++).setCellValue("45Days DOI");
		row.createCell(cellid++).setCellValue("45Days AV");
		row.createCell(cellid++).setCellValue("60Days DOI ");
		row.createCell(cellid++).setCellValue("60Days AV");
		row.createCell(cellid++).setCellValue("75Days DOI");
		row.createCell(cellid++).setCellValue("75Days AV");
		row.createCell(cellid++).setCellValue("90Days DOI");
		row.createCell(cellid++).setCellValue("90Days AV");
		row.createCell(cellid++).setCellValue("12Weeks DOI");
		row.createCell(cellid++).setCellValue("12Weeks AV");

		// row.createCell(cellid++).setCellValue("PO");
		rowid = 1;
		for (PA_FLEX_SUPER_BOM bom : paSuperBomData) {
			if (map.containsKey(bom.getPartNumber())) {
				continue;
			} else {
				map.put(bom.getPartNumber(), bom.getPn_SOURCE());
			}
			row = spreadsheet.createRow(rowid++);
			cellid = 0;
			row.createCell(cellid++).setCellValue(bom.getSno());
			row.createCell(cellid++).setCellValue(bom.getBomLevel());
			row.createCell(cellid++).setCellValue(bom.getAv());
			row.createCell(cellid++).setCellValue(bom.getPartNumber());
			row.createCell(cellid++).setCellValue(bom.getPn_SOURCE());
			row.createCell(cellid++).setCellValue(bom.getBomType());
			row.createCell(cellid++).setCellValue(bom.getPartDecsription());
			row.createCell(cellid++).setCellValue(bom.getCommodity());
			row.createCell(cellid++).setCellValue(bom.getAvlScreenCommodity());

			row.createCell(cellid++).setCellValue(bom.getGroupSecondary());
			row.createCell(cellid++).setCellValue(
					bom.getScreenCommodityGrpDesc());
			row.createCell(cellid++).setCellValue(bom.getGrpSecDesc());
			row.createCell(cellid++).setCellValue(bom.getIncludeOnScreen());
			// row.createCell(cellid++).setCellValue(bom.getLocImport());
			row.createCell(cellid++).setCellValue(bom.getBomPosition());

			row.createCell(cellid++).setCellValue(bom.getMpc());
			row.createCell(cellid++).setCellValue(bom.getQtyPer());
			row.createCell(cellid++).setCellValue(bom.getEffDate());
			row.createCell(cellid++).setCellValue(bom.getEndDate());

			// ------------setting part avilability--------------------//
			if (doiMap.containsKey(bom.getPartNumber()))
				row.createCell(cellid++).setCellValue(
						doiMap.get(bom.getPartNumber()));
			else
				row.createCell(cellid++).setCellValue(0);

			row.createCell(cellid++).setCellValue(bom.getPriQty());

			// ------------setting av avilability--------------------//
			// if(){}lse{}
			// started
			int level = Integer.parseInt(bom.getBomLevel().replaceAll(
					"[0 , .]", ""));
			if (level == 1) {
				// if started
				if (avAvilabilityMap.containsKey(bom.getAv())) {
					if (avAvilabilityMap.get(bom.getAv()) > 0) {
						row.createCell(cellid++).setCellValue(
								avAvilabilityMap.get(bom.getAv()));
					} else {
						if (bom.getIncludeOnScreen().equalsIgnoreCase("yes")) {
							row.createCell(cellid++).setCellValue(0);
						} else {
							row.createCell(cellid++).setCellValue("");
						}
					}
				}// end if start else
				else {
					row.createCell(cellid++).setCellValue("");
				}// else end

				// if start
				if (pipelineavAvilabilityMap.containsKey(bom.getAv())) {
					PipelinePojo pipeLineAvAvbail = pipelineavAvilabilityMap
							.get(bom.getAv());
					cellid = cellid + 1;
					row.createCell(cellid++).setCellValue(
							pipeLineAvAvbail.getAvbail15());
					cellid = cellid + 1;
					row.createCell(cellid++).setCellValue(
							pipeLineAvAvbail.getAvbail30());
					cellid = cellid + 1;
					row.createCell(cellid++).setCellValue(
							pipeLineAvAvbail.getAvbail45());
					cellid = cellid + 1;
					row.createCell(cellid++).setCellValue(
							pipeLineAvAvbail.getAvbail60());
					cellid = cellid + 1;
					row.createCell(cellid++).setCellValue(
							pipeLineAvAvbail.getAvbail75());
					cellid = cellid + 1;
					row.createCell(cellid++).setCellValue(
							pipeLineAvAvbail.getAvbail90());
					cellid = cellid + 1;
					row.createCell(cellid++).setCellValue(
							pipeLineAvAvbail.getAvbail());
				} else {
					row.createCell(cellid++).setCellValue("");
				}

			} else {
				if (pipelineDoiQtyMap.containsKey(bom.getPartNumber())) {

					PipelineDoiPojo pipeLineDOI = pipelineDoiQtyMap.get(bom
							.getPartNumber());
					cellid = cellid + 1;
					row.createCell(cellid++).setCellValue(
							pipeLineDOI.getQty15());
					cellid = cellid + 1;
					row.createCell(cellid++).setCellValue(
							pipeLineDOI.getQty30());
					cellid = cellid + 1;
					row.createCell(cellid++).setCellValue(
							pipeLineDOI.getQty45());
					cellid = cellid + 1;
					row.createCell(cellid++).setCellValue(
							pipeLineDOI.getQty60());
					cellid = cellid + 1;
					row.createCell(cellid++).setCellValue(
							pipeLineDOI.getQty75());
					cellid = cellid + 1;
					row.createCell(cellid++).setCellValue(
							pipeLineDOI.getQty90());
					cellid = cellid + 1;
					row.createCell(cellid++).setCellValue(pipeLineDOI.getQty());

				} else {
					row.createCell(cellid++).setCellValue("");
				}

			}

			// logger.info(sheet+" row number ----------------------------> "+rowid);
		}
	}

	public void createWorkBookSheetForAlternatives(
			List<PA_FLEX_SUPER_BOM> paSuperBomData, String sheet) {
		Set<String> avsSet = new LinkedHashSet<String>();
		for (PA_FLEX_SUPER_BOM bom : paSuperBomData) {
			if (bom.getBomType().equals("PRI")) {
				avsSet.add(bom.getAv());
			}
		}
		List<AvAvbail> avavilableList = cacheService.getAvAvail();
		Map<String, AvAvbail> avsMap = new HashMap<String, AvAvbail>();
		for (AvAvbail avAvbail : avavilableList) {
			if (avAvbail.getAvId() != null) {
				avsMap.put(avAvbail.getAvId(), avAvbail);
			}
		}
		XSSFSheet spreadsheet = workbook.createSheet(sheet);
		XSSFFont font = workbook.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		XSSFRow row;
		int initialCellNum = 0;
		int endingCellNum = 1;
		for (String av : avsSet) {
			if (avsMap.containsKey(av)) {
				AvAvbail avAvbailPojo = avsMap.get(av);
				if (spreadsheet.getRow(0) == null) {
					row = spreadsheet.createRow(0);
				} else {
					row = spreadsheet.getRow(0);
				}
				XSSFCell cell = row.createCell(initialCellNum);
				double primaryAvQty = avAvbailPojo.getAvbail();
				if (primaryAvQty == -1) {
					primaryAvQty = 0;
				}
				cell.setCellValue(avAvbailPojo.getAvId() + "         "
						+ (int) primaryAvQty);
				cell.setCellStyle(style);
				spreadsheet.addMergedRegion(new CellRangeAddress(0, 0,
						initialCellNum, endingCellNum));
				int rowNum = 1;
				for (AvAvbail avAvbail : avavilableList) {
					if (!avAvbail.getAvId().equals(av)) {
						if (avAvbailPojo.getComodity().equals(
								avAvbail.getComodity())) {
							if (spreadsheet.getRow(rowNum) == null) {
								row = spreadsheet.createRow(rowNum);
							} else {
								row = spreadsheet.getRow(rowNum);
							}
							row.createCell(initialCellNum).setCellValue(
									avAvbail.getAvId());
							double avavilability = avAvbail.getAvbail();
							if (avavilability == -1) {
								avavilability = 0;
							}
							row.createCell(endingCellNum).setCellValue(
									(int) avavilability);
							rowNum++;
						}
					}

				}
				initialCellNum = endingCellNum + 2;
				endingCellNum = initialCellNum + 1;
			}

		}
	}
}
