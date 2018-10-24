package com.techouts.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

import com.techouts.dto.All_Skus_Availability;
import com.techouts.pojo.Family;
import com.techouts.pojo.MasterSkuAvBom_PA;
import com.techouts.pojo.PADashboardSkuAvbl;
import com.techouts.pojo.SkuDetailsInfo;
import com.techouts.pojo.User;
import com.techouts.pojo.skubo;
import com.techouts.services.HomePageService;
import com.techouts.services.HpServices;

@ManagedBean
@SessionScoped
public class HomeBean {
	/*
	 * @ManagedProperty("#{skuservice}") private Skuservice skuservice;
	 */
	@ManagedProperty("#{hpServices}")
	private HpServices hpServices;
	@ManagedProperty("#{homePageService}")
	private HomePageService homePageService;
	private static Logger LOG = Logger.getLogger(HomeBean.class);
	List<PADashboardSkuAvbl> finallistShipSkus = null;
	private List<skubo> familySkus;
	private List<Family> topFiveSellingSkus;
	private List<Family> topFiveAvailableSkus;
	private List<SkuDetailsInfo> skusDetailsInfo;
	private String selectedSku;
	private List<Family> bnbfamily;
	private List<Family> bpcfamily;
	private List<Family> cpcfamily;
	private List<Family> wstfamily;
	private List<SkuDetailsInfo> shortlistedskus;
	private Set<String> shortlistskus = new HashSet<String>();;
	private int shortlistedcount;
	private List<Family> cnbfamily;
	private String imageType = "PC";
	private long bnbcount;
	private long cnbcount;
	private long cpccount;
	private long bpccount;
	private long wscount;
	private List<String> bufamily;
	private Map<String, String> bufamilyMap;
	private int cartItems;
	private SkuDetailsInfo selectedSkuDetails;
	private String selectedSkuid;
	private String searchedSku;
	private String searchedDisSku;
	private All_Skus_Availability selectedDisSku;
	private List<SkuDetailsInfo> shortListedskusforPanel;
	// private Set<String> skus;
	private String description;
	private Map<String, Double> skuSimilarityMap;
	Map<String, List<String>> skuAvTableData;
	private boolean skuSimilarity = false;

	public String home() {
		norecords = false;
		search = false;
		LOG.info(":::::::::::::::::::::::::::::::::::::::::::::::");
		boolean status = true;
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User currentUser = hpServices.getCurrentUser(username);
		LOG.info("user::" + currentUser);
		if (currentUser.getLastLogin() == 0) {
			status = false;
			// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			try {
				request.logout();
			} catch (ServletException e) {
				LOG.info("Session invalidated::::" + e.getMessage());
			}
		}

		if (status)
			init();
		return "newhome.xhtml?faces-redirect=true";
	}

	public String disHome() {
		skuSimilarity = false;
		skuSimilarityMap = null;
		skuAvTableData = null;
		searchedDisSku = null;
		search = false;
		LOG.info(":::::::::::::::::::::::::::::::::::::::::::::::");
		boolean status = true;
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User currentUser = hpServices.getCurrentUser(username);
		LOG.info("user::" + currentUser);
		if (currentUser.getLastLogin() == 0) {
			status = false;
			// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			try {
				request.logout();
			} catch (ServletException e) {
				LOG.info("Session invalidated::::" + e.getMessage());
			}
		}

		if (status)
			init();
		return "dishome.xhtml?faces-redirect=true";
	}

	public String mobileHome() {
		norecords = false;
		search = false;

		if (isSessionValidate())
			init();
		return "search.xhtml?faces-redirect=true";
	}

	public List<String> getSkus(String sku) {

		LOG.info("==============hai from get search================");
		List<String> result = new ArrayList<String>();
		LOG.info(sku);
		for (String s : homePageService.searchData()) {
			String substr = null;
			if (s.length() >= sku.length()) {
				substr = s.substring(0, sku.length()).toString();
			} else {
				substr = s.substring(0, s.length()).toString();
			}
			if (substr.equalsIgnoreCase(sku)) {
				LOG.info(substr);
				result.add(s);
			}

			if (result.size() > 100) {
				break;
			}
		}

		return result;
	}

	public List<String> getDisSkus(String sku) {

		LOG.info("==============hai from get search================");
		List<String> result = new ArrayList<String>();
		LOG.info(sku);
		for (String s : homePageService.searchDisData()) {
			String substr = null;
			if (s.length() >= sku.length()) {
				substr = s.substring(0, sku.length()).toString();
			} else {
				substr = s.substring(0, s.length()).toString();
			}
			if (substr.equalsIgnoreCase(sku)) {
				LOG.info(substr);
				result.add(s);
			}

			if (result.size() > 100) {
				break;
			}
		}

		return result;
	}

	public void mobileSearch(SelectEvent event) {
		// ...
		LOG.info(event.getObject().toString());
		setSearchedSku(event.getObject().toString());
		LOG.info("before");
		search();
		LOG.info("after");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("searchResults.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().responseComplete();
	}

	private boolean norecords = false;
	private boolean search = false;

	public void disListener(ActionEvent event) {
		String skuid = (String) event.getComponent().getAttributes().get("skuId");
		setSearchedDisSku(skuid);
		disSearch();
	}

	public void disSearch() {
		skuSimilarity = false;
		selectedDisSku = homePageService.getSkuData(getSearchedDisSku());
		if (selectedDisSku != null) {			
			List<MasterSkuAvBom_PA> masterSkuAvBom_PAList = homePageService
					.getMasterAvBomDataSales(selectedDisSku.getFamily());
			Map<String, Set<String>> skuAvsMap = new HashMap<String, Set<String>>();
			for (MasterSkuAvBom_PA masterSkuAvBom_PA : masterSkuAvBom_PAList) {
				Set<String> avsList = new TreeSet<String>();
				String sku = masterSkuAvBom_PA.getSku();
				if (skuAvsMap.containsKey(sku)) {
					avsList.addAll(skuAvsMap.get(sku));
				}
				avsList.add(masterSkuAvBom_PA.getAv());
				skuAvsMap.put(sku, avsList);
			}
			skuSimilarityMap = homePageService.getSimilarSkus(selectedDisSku, skuAvsMap);
			skuAvTableData = homePageService.getSkuAvTableData(selectedDisSku, skuSimilarityMap, skuAvsMap);
			skuSimilarity = true;
		}else{
			selectedDisSku = new All_Skus_Availability();
		}
	}

	public void search() {
		LOG.info("in search dtat");
		bufamily = new ArrayList<String>();
		bufamilyMap = new HashMap<String, String>();
		LOG.info("Clicked");
		LOG.info(getSearchedSku());
		if (getSearchedSku().equalsIgnoreCase("BNB")) {
			forBU("BNB");
			return;
		} else if (getSearchedSku().equalsIgnoreCase("BPC")) {
			forBU("BPC");
			return;
		} else if (getSearchedSku().equalsIgnoreCase("CPC")) {
			forBU("CPC");
			return;
		} else if (getSearchedSku().equalsIgnoreCase("CNB")) {
			forBU("CNB");
			return;
		} else if (getSearchedSku().equalsIgnoreCase("WST")) {
			forBU("WST");
			return;
		}
		if (homePageService.familycontains(getSearchedSku())) {
			forFamilyBu(getSearchedSku());
			return;
		}

		try {
			norecords = false;
			search = true;
			skusDetailsInfo = homePageService.getSimailarityIndex(getSearchedSku().trim(), shortlistskus);
			SkuDetailsInfo s = homePageService.getSkuconfigInfo(getSearchedSku().trim());
			String bu = s.getBusinessUnit();
			if (bu.equalsIgnoreCase("bnb"))
				bu = "BUSINESS NOTEBOOK";
			else if (bu.equalsIgnoreCase("bpc"))
				bu = "BUSINESS PC";
			else if (bu.equalsIgnoreCase("cpc"))
				bu = "CONSUMER PC";
			else if (bu.equalsIgnoreCase("cnb"))
				bu = "CONSUMER NOTEBOOK";
			else if (bu.equalsIgnoreCase("wst"))
				bu = "WORKSTATIONS";
			bufamily.add(bu);
			bufamily.add(s.getFamily().toUpperCase());
			bufamilyMap.put(bu, s.getBusinessUnit());
			bufamilyMap.put(s.getFamily().toUpperCase(), s.getFamily());
			searchedSku = null;
		} catch (Exception e) {
			LOG.info("Skip");
			skusDetailsInfo = new ArrayList<SkuDetailsInfo>();
			norecords = true;
			search = false;
		}
	}

	@PostConstruct
	public void init() {
		LOG.info(SecurityContextHolder.getContext().getAuthentication().getName());
		if (getShortlistedcount() == 0) {
			shortListedskusforPanel = new ArrayList<SkuDetailsInfo>();
		} else {
			shortListedskusforPanel = homePageService.getSkusDetailsforShortlisted(shortlistskus);
		}
		cartItems = homePageService.getCartCount();
		norecords = false;
		search = false;
		// skus=new HashSet<String>();
		searchedSku = null;
		finallistShipSkus = new ArrayList<PADashboardSkuAvbl>();
		// skus.addAll(homePageService.getallSkus());
		bufamily = new ArrayList<String>();
		bufamilyMap = new HashMap<String, String>();
		LOG.info("INIT Called");
		finallistShipSkus = hpServices.getTopFiveShipSkus();
		topFiveSellingSkus = new ArrayList<Family>();
		for (PADashboardSkuAvbl p : finallistShipSkus) {
			LOG.info("padashboard ship skus::" + p.getBu() + "::" + p.getSku() + "::" + p.getSkuMaxAvailable());
			Family f = new Family();
			f.setSkuid(p.getSku());
			f.setCount(String.valueOf(homePageService.getSkuInfo(p.getSku()).getSkuAvailability()));
			topFiveSellingSkus.add(f);
		}
		loadbufamily();
		topFiveAvailableSkus = new ArrayList<Family>();

		skusDetailsInfo = homePageService.getSkusDetails(shortlistskus);
		int i = 0;
		for (SkuDetailsInfo s : skusDetailsInfo) {
			i++;
			Family f = new Family();
			f.setSkuid(s.getSkuId());
			f.setCount(String.valueOf(s.getSkuAvailability()));
			topFiveAvailableSkus.add(f);
			if (i == 5)
				break;
		}
		familySkus = new ArrayList<skubo>();
	}

	public void loadbufamily() {
		bnbfamily = new ArrayList<Family>();
		bpcfamily = new ArrayList<Family>();
		cpcfamily = new ArrayList<Family>();
		wstfamily = new ArrayList<Family>();
		cnbfamily = new ArrayList<Family>();
		bpcfamily.addAll(homePageService.loadbufamilyData("BPC"));
		bnbfamily.addAll(homePageService.loadbufamilyData("BNB"));
		cpcfamily.addAll(homePageService.loadbufamilyData("CPC"));
		cnbfamily.addAll(homePageService.loadbufamilyData("CNB"));
		wstfamily.addAll(homePageService.loadbufamilyData("WST"));
		setBnbcount(homePageService.getbucount("BNB"));
		setBpccount(homePageService.getbucount("BPC"));
		setCpccount(homePageService.getbucount("CPC"));
		setCnbcount(homePageService.getbucount("CNB"));
		setWscount(homePageService.getbucount("WST"));
	}

	public void breadCrumb(ActionEvent event) {
		norecords = false;
		search = false;
		String value = (String) event.getComponent().getAttributes().get("bufamily");
		LOG.info(bufamilyMap.get(bufamily.get(0)) + "\t" + value);
		if (value.equals("BUSINESS NOTEBOOK"))
			forBU("BNB");
		else if (value.equals("BUSINESS PC"))
			forBU("BPC");
		else if (value.equals("CONSUMER PC"))
			forBU("CPC");
		else if (value.equals("CONSUMER NOTEBOOK"))
			forBU("CNB");
		else if (value.equals("WORKSTATIONS"))
			forBU("WST");
		else
			forFamilyBu(bufamilyMap.get(bufamily.get(0)), bufamilyMap.get(value));

	}

	private void forFamilyBu(String family) {
		searchedSku = null;
		norecords = false;
		search = false;
		topFiveAvailableSkus = new ArrayList<Family>();
		finallistShipSkus = new ArrayList<PADashboardSkuAvbl>();
		bufamily = new ArrayList<String>();
		bufamilyMap = new HashMap<String, String>();
		skusDetailsInfo = homePageService.getSkusDetailsforFamily(family, null, shortlistskus);
		finallistShipSkus = hpServices.getTopFiveShipSkus(null, family);
		bufamily.add(family.toUpperCase());
		bufamilyMap.put(family.toUpperCase(), family);
		LOG.info(family);
		int i = 0;
		for (SkuDetailsInfo s : skusDetailsInfo) {
			i++;
			Family f = new Family();
			f.setSkuid(s.getSkuId());
			f.setCount(String.valueOf(s.getSkuAvailability()));
			topFiveAvailableSkus.add(f);
			if (i == 5)
				break;
		}
		topFiveSellingSkus = new ArrayList<Family>();
		for (PADashboardSkuAvbl p : finallistShipSkus) {
			LOG.info("padashboard ship skus::" + p.getBu() + "::" + p.getSku() + "::" + p.getSkuMaxAvailable());
			Family f = new Family();
			f.setSkuid(p.getSku());
			f.setCount(String.valueOf(homePageService.getSkuInfo(p.getSku()).getSkuAvailability()));
			topFiveSellingSkus.add(f);
		}
	}

	public void overallButton(ActionEvent e) {
		FacesContext fc = FacesContext.getCurrentInstance();
		String bu = getvalue(fc, "bu");
		forBU(bu);
	}

	private void forBU(String bu) {
		topFiveAvailableSkus = new ArrayList<Family>();
		finallistShipSkus = new ArrayList<PADashboardSkuAvbl>();
		searchedSku = null;
		norecords = false;
		search = false;
		bufamily = new ArrayList<String>();
		bufamilyMap = new HashMap<String, String>();
		LOG.info(bu);
		String bussinessunit = bu;
		skusDetailsInfo = homePageService.getBUinfo(bu, shortlistskus);
		finallistShipSkus = hpServices.getTopFiveShipSkus(bu);
		if (bu.equalsIgnoreCase("bnb"))
			bu = "BUSINESS NOTEBOOK";
		else if (bu.equalsIgnoreCase("bpc"))
			bu = "BUSINESS PC";
		else if (bu.equalsIgnoreCase("cpc"))
			bu = "CONSUMER PC";
		else if (bu.equalsIgnoreCase("cnb"))
			bu = "CONSUMER NOTEBOOK";
		else if (bu.equalsIgnoreCase("wst"))
			bu = "WORKSTATIONS";
		bufamily.add(bu);
		bufamilyMap.put(bu, bussinessunit);
		int i = 0;
		for (SkuDetailsInfo s : skusDetailsInfo) {
			i++;
			Family f = new Family();
			f.setSkuid(s.getSkuId());
			f.setCount(String.valueOf(s.getSkuAvailability()));
			topFiveAvailableSkus.add(f);
			if (i == 5)
				break;
		}

		topFiveSellingSkus = new ArrayList<Family>();
		for (PADashboardSkuAvbl p : finallistShipSkus) {
			LOG.info("padashboard ship skus::" + p.getBu() + "::" + p.getSku() + "::" + p.getSkuMaxAvailable());
			Family f = new Family();
			f.setSkuid(p.getSku());
			f.setCount(String.valueOf(homePageService.getSkuInfo(p.getSku()).getSkuAvailability()));
			topFiveSellingSkus.add(f);
		}

	}

	public String returnnull() {
		return null;
	}

	public void buttonAction(ActionEvent e) {
		LOG.info("Button clicked");
		FacesContext fc = FacesContext.getCurrentInstance();
		String value = getvalue(fc, "family");
		String bu = getvalue(fc, "bu");
		forFamilyBu(bu, value);
	}

	private void forFamilyBu(String bu, String family) {
		searchedSku = null;
		norecords = false;
		search = false;
		topFiveAvailableSkus = new ArrayList<Family>();
		finallistShipSkus = new ArrayList<PADashboardSkuAvbl>();
		bufamily = new ArrayList<String>();
		bufamilyMap = new HashMap<String, String>();
		String bussinessunit = bu;
		skusDetailsInfo = homePageService.getSkusDetailsforFamily(family, bu, shortlistskus);
		finallistShipSkus = hpServices.getTopFiveShipSkus(bu, family);

		if (bu.equalsIgnoreCase("bnb"))
			bu = "BUSINESS NOTEBOOK";
		else if (bu.equalsIgnoreCase("bpc"))
			bu = "BUSINESS PC";
		else if (bu.equalsIgnoreCase("cpc"))
			bu = "CONSUMER PC";
		else if (bu.equalsIgnoreCase("cnb"))
			bu = "CONSUMER NOTEBOOK";
		else if (bu.equalsIgnoreCase("wst"))
			bu = "WORKSTATIONS";
		bufamily.add(bu);
		bufamily.add(family.toUpperCase());
		bufamilyMap.put(bu, bussinessunit);
		bufamilyMap.put(family.toUpperCase(), family);
		LOG.info(family);
		int i = 0;
		for (SkuDetailsInfo s : skusDetailsInfo) {
			i++;
			Family f = new Family();
			f.setSkuid(s.getSkuId());
			f.setCount(String.valueOf(s.getSkuAvailability()));
			topFiveAvailableSkus.add(f);
			if (i == 5)
				break;
		}
		topFiveSellingSkus = new ArrayList<Family>();
		for (PADashboardSkuAvbl p : finallistShipSkus) {
			LOG.info("padashboard ship skus::" + p.getBu() + "::" + p.getSku() + "::" + p.getSkuMaxAvailable());
			Family f = new Family();
			f.setSkuid(p.getSku());
			f.setCount(String.valueOf(homePageService.getSkuInfo(p.getSku()).getSkuAvailability()));
			topFiveSellingSkus.add(f);
		}
	}

	public String getvalue(FacesContext fc, String param) {
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		LOG.info("Faces obj created ::value" + params);
		LOG.info("value" + params.get(param));
		return params.get(param);
	}

	public String viewAddDetailsPage(ActionEvent event) {
		LOG.info("AddDetails.xhtml?faces-redirect=true");

		FacesContext fc = FacesContext.getCurrentInstance();
		String skuid = getvalue(fc, "skuId");
		/*
		 * Map<Object,Object> params =
		 * FacesContext.getCurrentInstance().getAttributes(); String skuid =
		 * (String)params.get("skuId");
		 */
		LOG.info("selected sku id  :" + skuid);

		return "AddDetails.xhtml?faces-redirect=true";
	}

	public void shortListed(ActionEvent event) {
		LOG.info("shortListed");
		String skuid = (String) event.getComponent().getAttributes().get("skuId");
		for (SkuDetailsInfo s : skusDetailsInfo) {
			if (s.getSkuId().equalsIgnoreCase(skuid)) {
				if (shortlistskus.contains(skuid)) {
					s.setIsShortListed(null);
					shortlistedcount--;
					LOG.info("Contains key :: " + s);
					shortlistskus.remove(s.getSkuId());
					break;
				}
				s.setIsShortListed("selected");
				LOG.info(" Doesent contain :: " + s);
				shortlistedcount++;
				shortlistskus.add(s.getSkuId());

			}
		}
		shortListedskusforPanel = homePageService.getSkusDetailsforShortlisted(shortlistskus);

	}

	public void getAllShortlisted() {

		LOG.info("getAllShortlisted");
		// shortListedskus=homePageService.getSkusDetailsforShortlisted(shortlistskus);
		for (SkuDetailsInfo sku : shortListedskusforPanel) {
			LOG.info(sku.getSkuId());
		}
	}

	private int altCount;
	private String clickedSku;

	public void attrListener(ActionEvent event) {
		searchedSku = null;
		bufamily = new ArrayList<String>();
		bufamilyMap = new HashMap<String, String>();
		String skuid = (String) event.getComponent().getAttributes().get("skuId");
		int count;
		try {
			count = (int) event.getComponent().getAttributes().get("count");
			LOG.info(count);
			setAltCount(count);
		} catch (Exception e) {
			count = 0;
		}
		LOG.info(skuid);
		getSkuInfoBySkuId(skuid);
		LOG.info("action listner  sku  id :" + skuid);
		// homePageService.setSelectedSkuId(skuid);
		setClickedSku(skuid);
		SkuDetailsInfo s = homePageService.getSkuClickInfo(skuid);
		if (search == true && count > 0) {
			s.setCount(count);
		} else if (search == false || count == 0) {
			try {
				// setDescription(s.getDescription());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		LOG.info(s);
		String bu = s.getBusinessUnit();
		if (bu.equalsIgnoreCase("bnb"))
			bu = "BUSINESS NOTEBOOK";
		else if (bu.equalsIgnoreCase("bpc"))
			bu = "BUSINESS PC";
		else if (bu.equalsIgnoreCase("cpc"))
			bu = "CONSUMER PC";
		else if (bu.equalsIgnoreCase("cnb"))
			bu = "CONSUMER NOTEBOOK";
		else if (bu.equalsIgnoreCase("wst"))
			bu = "WORKSTATIONS";
		bufamily.add(bu);
		bufamily.add(s.getFamily().toUpperCase());
		bufamilyMap.put(bu, s.getBusinessUnit());
		bufamilyMap.put(s.getFamily().toUpperCase(), s.getFamily());
	}

	public void getSkuInfoBySkuId(String skuId) {
		for (SkuDetailsInfo skuInfo : skusDetailsInfo) {
			if (skuId.equals(skuInfo.getSkuId())) {
				setSelectedSkuDetails(skuInfo);
				break;
			}
		}
	}

	public void downloadOrgSku(All_Skus_Availability sku) {
		LOG.info("Download Clicked" + sku.getSkuId());
		try {
			final String File_Name = sku.getSkuId() + ".xlsx";

			Workbook workbook = homePageService.exportData(sku.getSkuId(), sku.getConfigurations());

			if (workbook != null) {
				workbook.setActiveSheet(0);

				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
				response.reset();
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + File_Name + "\"");
				workbook.write(response.getOutputStream());
				FacesContext.getCurrentInstance().responseComplete();
			} // if workbook not null
			else {
				LOG.info("Excel sheet not created due to empty Avs :");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"avs not matched", "Excel sheet not created due to some problems. Please try again "));
			}
		} // try
		catch (Exception ioe) {
			ioe.printStackTrace();
			LOG.info("Exception in exporting:" + ioe.getMessage() + " " + ioe.getCause());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Export error", "Error in exporting. Please try again."));
		} // else

		LOG.info("Download completed");
		addMessage("File Downloaded Successfully");
	}

	public void download(String skuid) {
		LOG.info("Download Clicked" + skuid);
		try {
			final String File_Name = skuid + ".xlsx";

			Workbook workbook = homePageService.exportData(skuid, skuAvTableData.get(skuid));

			if (workbook != null) {
				workbook.setActiveSheet(0);

				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
				response.reset();
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + File_Name + "\"");
				workbook.write(response.getOutputStream());
				FacesContext.getCurrentInstance().responseComplete();
			} // if workbook not null
			else {
				LOG.info("Excel sheet not created due to empty Avs :");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"avs not matched", "Excel sheet not created due to some problems. Please try again "));
			}
		} // try
		catch (Exception ioe) {
			ioe.printStackTrace();
			LOG.info("Exception in exporting:" + ioe.getMessage() + " " + ioe.getCause());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Export error", "Error in exporting. Please try again."));
		} // else

		LOG.info("Download completed");
		addMessage("File Downloaded Successfully");
	}

	public void addMessage(String summary) {
		LOG.info(summary);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("", summary));

	}

	public void removeAllShortListed() {

		for (SkuDetailsInfo sku : getShortListedskusforPanel()) {
			sku.setShowstar(false);
		}
		for (SkuDetailsInfo sku : getSkusDetailsInfo()) {
			sku.setIsShortListed("");
		}
		getShortListedskusforPanel().clear();
		shortlistskus.clear();
		shortlistedcount = 0;
	}

	public void removeShortlisted(ActionEvent event) {
		String skuid = (String) event.getComponent().getAttributes().get("skuId");

		LOG.info(skuid);
		getShortListedskusforPanel().clear();
		if (getShortlistskus().contains(skuid)) {
			getShortlistskus().remove(skuid);
			shortlistedcount--;
		}
		for (SkuDetailsInfo sku : getSkusDetailsInfo()) {
			if (skuid.equalsIgnoreCase(sku.getSkuId()))
				sku.setIsShortListed("");
		}
		shortListedskusforPanel = homePageService.getSkusDetailsforShortlisted(getShortlistskus());
	}

	public Set<String> getShortlistskus() {
		return shortlistskus;
	}

	public void setShortlistskus(Set<String> shortlistskus) {
		this.shortlistskus = shortlistskus;
	}

	public String getCartDetails() {

		if (isSessionValidate())
			init();
		return "cartDetails.xhtml?faces-redirect=true";
	}

	public String getEmptyCart() {

		if (isSessionValidate())
			init();
		return "cartDetails.xhtml?faces-redirect=true";
	}

	public boolean isSessionValidate() {
		boolean status = true;
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User currentUser = hpServices.getCurrentUser(username);
		LOG.info("user::" + currentUser);
		LOG.info(":::::::::::::::::::::::::::::::::::::::::::::::");
		if (currentUser.getLastLogin() == 0) {
			status = false;
			// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			try {
				request.logout();
			} catch (ServletException e) {
				LOG.info("Session invalidated::::" + e.getMessage());
			}
		}
		return status;
	}

	public String getsearchPage() {
		if (isSessionValidate())
			init();

		return "search.xhtml?faces-redirect=true";
	}

	public void setHpServices(HpServices hpServices) {
		this.hpServices = hpServices;
	}

	public List<PADashboardSkuAvbl> getFinallistShipSkus() {
		return finallistShipSkus;
	}

	public void setFinallistShipSkus(List<PADashboardSkuAvbl> finallistShipSkus) {
		this.finallistShipSkus = finallistShipSkus;
	}

	public List<skubo> getFamilySkus() {
		return familySkus;
	}

	public void setFamilySkus(List<skubo> familySkus) {
		this.familySkus = familySkus;
	}

	public List<Family> getTopFiveSellingSkus() {
		return topFiveSellingSkus;
	}

	public void setTopFiveSellingSkus(List<Family> topFiveSellingSkus) {
		this.topFiveSellingSkus = topFiveSellingSkus;
	}

	public List<Family> getTopFiveAvailableSkus() {
		return topFiveAvailableSkus;
	}

	public void setTopFiveAvailableSkus(List<Family> topFiveAvailableSkus) {
		this.topFiveAvailableSkus = topFiveAvailableSkus;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {

		this.imageType = imageType;

	}

	public List<SkuDetailsInfo> getSkusDetailsInfo() {
		return skusDetailsInfo;
	}

	public void setSkusDetailsInfo(List<SkuDetailsInfo> skusDetailsInfo) {
		this.skusDetailsInfo = skusDetailsInfo;
	}

	public HomePageService getHomePageService() {
		return homePageService;
	}

	public void setHomePageService(HomePageService homePageService) {
		this.homePageService = homePageService;
	}

	public String getSelectedSku() {
		return selectedSku;
	}

	public void setSelectedSku(String selectedSku) {
		this.selectedSku = selectedSku;
	}

	public List<String> getBufamily() {
		return bufamily;
	}

	public void setBufamily(List<String> bufamily) {
		this.bufamily = bufamily;
	}

	public int getCartItems() {
		return cartItems;
	}

	public void setCartItems(int cartItems) {
		this.cartItems = cartItems;
	}

	public SkuDetailsInfo getSelectedSkuDetails() {
		return selectedSkuDetails;
	}

	public void setSelectedSkuDetails(SkuDetailsInfo selectedSkuDetails) {
		this.selectedSkuDetails = selectedSkuDetails;
	}

	public long getBnbcount() {
		return bnbcount;
	}

	public void setBnbcount(long bnbcount) {
		this.bnbcount = bnbcount;
	}

	public long getCnbcount() {
		return cnbcount;
	}

	public void setCnbcount(long cnbcount) {
		this.cnbcount = cnbcount;
	}

	public long getCpccount() {
		return cpccount;
	}

	public void setCpccount(long cpccount) {
		this.cpccount = cpccount;
	}

	public long getBpccount() {
		return bpccount;
	}

	public void setBpccount(long bpccount) {
		this.bpccount = bpccount;
	}

	public long getWscount() {
		return wscount;
	}

	public void setWscount(long wscount) {
		this.wscount = wscount;
	}

	public String getSelectedSkuid() {
		return selectedSkuid;
	}

	private void setSelectedSkuid(String selectedSkuid) {
		this.selectedSkuid = selectedSkuid;
	}

	public String getSearchedSku() {
		return searchedSku;
	}

	public void setSearchedSku(String searchedSku) {
		this.searchedSku = searchedSku;
	}

	public boolean isNorecords() {
		return norecords;
	}

	public void setNorecords(boolean norecords) {
		this.norecords = norecords;
	}

	public List<SkuDetailsInfo> getShortlistedskus() {
		return shortlistedskus;
	}

	public void setShortlistedskus(List<SkuDetailsInfo> shortlistedskus) {
		this.shortlistedskus = shortlistedskus;
	}

	public int getShortlistedcount() {
		return shortlistedcount;
	}

	public void setShortlistedcount(int shortlistedcount) {
		this.shortlistedcount = shortlistedcount;
	}

	public List<Family> getBnbfamily() {
		return bnbfamily;
	}

	public void setBnbfamily(List<Family> bnbfamily) {
		this.bnbfamily = bnbfamily;
	}

	public List<Family> getBpcfamily() {
		return bpcfamily;
	}

	public void setBpcfamily(List<Family> bpcfamily) {
		this.bpcfamily = bpcfamily;
	}

	public List<Family> getCpcfamily() {
		return cpcfamily;
	}

	public void setCpcfamily(List<Family> cpcfamily) {
		this.cpcfamily = cpcfamily;
	}

	public List<Family> getWstfamily() {
		return wstfamily;
	}

	public void setWstfamily(List<Family> wstfamily) {
		this.wstfamily = wstfamily;
	}

	public List<Family> getCnbfamily() {
		return cnbfamily;
	}

	public void setCnbfamily(List<Family> cnbfamily) {
		this.cnbfamily = cnbfamily;
	}

	public Map<String, String> getBufamilyMap() {
		return bufamilyMap;
	}

	public void setBufamilyMap(Map<String, String> bufamilyMap) {
		this.bufamilyMap = bufamilyMap;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addtocart(String skuId, String name, SkuDetailsInfo selectedSkuDetails) {

	}

	public int getAltCount() {
		return altCount;
	}

	public void setAltCount(int altCount) {
		this.altCount = altCount;
	}

	public String getClickedSku() {
		return clickedSku;
	}

	public void setClickedSku(String clickedSku) {
		this.clickedSku = clickedSku;
	}

	/*
	 * public Skuservice getSkuservice() { return skuservice; }
	 * 
	 * public void setSkuservice(Skuservice skuservice) { this.skuservice =
	 * skuservice; }
	 */

	public List<SkuDetailsInfo> getShortListedskusforPanel() {
		return shortListedskusforPanel;
	}

	public void setShortListedskusforPanel(List<SkuDetailsInfo> shortListedskusforPanel) {
		this.shortListedskusforPanel = shortListedskusforPanel;
	}

	public Map<String, Double> getSkuSimilarityMap() {
		return skuSimilarityMap;
	}

	public boolean isSkuSimilarity() {
		return skuSimilarity;
	}

	public All_Skus_Availability getSelectedDisSku() {
		return selectedDisSku;
	}

	public Map<String, List<String>> getSkuAvTableData() {
		return skuAvTableData;
	}

	public String getSearchedDisSku() {
		return searchedDisSku;
	}

	public void setSearchedDisSku(String searchedDisSku) {
		this.searchedDisSku = searchedDisSku;
	}

}
