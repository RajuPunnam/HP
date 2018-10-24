package com.techouts.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.security.core.context.SecurityContextHolder;

import com.techouts.pojo.CartItems;
import com.techouts.pojo.PADashboardSkuAvbl;
import com.techouts.pojo.SkuDetailsInfo;
import com.techouts.pojo.skubo;
import com.techouts.services.AddDetailsService;
import com.techouts.services.CartService;
import com.techouts.services.HomePageService;

@ManagedBean
@ViewScoped
public class AddDetailsBean {

	@ManagedProperty("#{homePageService}")
	private HomePageService homePageService;

	@ManagedProperty("#{addDetailsService}")
	private AddDetailsService addDetailsService;

	@ManagedProperty("#{cartService}")
	private CartService cartService;

	@ManagedProperty("#{homeBean}")
	private HomeBean homeBean;

	public CartService getCartService() {
		return cartService;
	}

	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}

	private static Logger LOG = Logger.getLogger(HomeBean.class);
	List<PADashboardSkuAvbl> finallistShipSkus = new ArrayList<PADashboardSkuAvbl>();

	private List<skubo> familySkus;
	private List<String> topFiveSellingSkus;
	private List<String> topFiveAvailableSkus;
	private List<SkuDetailsInfo> skusDetailsInfo;
	private String selectedSku;
	private String quantity;
	private List<String> bnbfamily;
	private List<String> bpcfamily;
	private List<String> cpcfamily;
	private List<String> wstfamily;
	private List<String> cnbfamily;
	private String imageType = "PC";
	private SkuDetailsInfo selectedSkuDetails;

	public AddDetailsService getAddDetailsService() {
		return addDetailsService;
	}

	public void setAddDetailsService(AddDetailsService addDetailsService) {
		this.addDetailsService = addDetailsService;
	}

	private List<String> bufamily;
	private List<String> dates;
	private List<String> configurations;
	private List<String> shortages;

	public String getforwardDates(Date date, int noOfDays) {
		SimpleDateFormat formatter = null;
		formatter = new SimpleDateFormat("dd-MMM");
		Calendar cal = null;
		cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, noOfDays);
		Date d = cal.getTime();
		return formatter.format(d);
	}

	public String getDayOfMonthSuffix(final int n) {
		if (n < 1 && n > 31) {
			return "invalid Date";
		}
		if (n >= 11 && n <= 13) {
			return "th";
		}
		switch (n % 10) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}

	public String getdate(Date date, int start, int end) {
		String[] strs = getforwardDates(date, start).split("-");
		String starts = strs[0]
				+ getDayOfMonthSuffix(Integer.parseInt(strs[0])) + " "
				+ strs[1];
		strs = getforwardDates(date, end).split("-");
		String ends = strs[0] + getDayOfMonthSuffix(Integer.parseInt(strs[0]))
				+ " " + strs[1];

		return starts + " - " + ends;
	}

	private String filename;

	@PostConstruct
	public void init() {
		LOG.info(cartService);
		configurations = new ArrayList<String>();
		shortages = new ArrayList<String>();
		dates = new ArrayList<String>();
		String skuId = homeBean.getClickedSku();
		LOG.info(skuId);
		setSelectedSkuDetails(homePageService.getSkuClickInfo(skuId));
		filename = skuId;
		if (homeBean.isSearch() == true && homeBean.getAltCount() > 0) {
			getSelectedSkuDetails().setDescription(
					"Alternative  " + homeBean.getAltCount());
			filename = getSelectedSkuDetails().getSkuId() + "_Alt"
					+ homeBean.getAltCount();
		}
		Date d = new Date();
		dates.add(getdate(d, 0, 15));
		dates.add(getdate(d, 16, 30));
		dates.add(getdate(d, 31, 45));
		dates.add(getdate(d, 46, 60));
		dates.add(getdate(d, 61, 75));
		dates.add(getdate(d, 76, 90));
		SkuDetailsInfo selected = getSelectedSkuDetails();

		for (String s : selected.getConfiguration()) {

			String[] conf = s.split(": ");
			configurations.add(conf[0].trim());
			LOG.info("CONFIGS :: " + conf[0].trim());
		}

		if (selected.getShortage() != null) {
			for (String s : selected.getShortage()) {

				String[] sho = s.split(": ");
				shortages.add(sho[0].trim());
				LOG.info("Shortages :: " + sho[0].trim());

			}
		}

		/*
		 * bufamily=new ArrayList<String>(); bnbfamily=new ArrayList<String>();
		 * bpcfamily=new ArrayList<String>(); cpcfamily=new ArrayList<String>();
		 * wstfamily=new ArrayList<String>(); cnbfamily=new ArrayList<String>();
		 * LOG.info("INIT Called"); finallistShipSkus =
		 * hpServices.getTopFiveShipSkus(); topFiveSellingSkus=new
		 * ArrayList<String>(); for (PADashboardSkuAvbl p : finallistShipSkus) {
		 * LOG.info("padashboard ship skus::" + p.getBu() + "::" + p.getSku() +
		 * "::" + p.getSkuMaxAvailable()); topFiveSellingSkus.add(p.getSku()); }
		 * 
		 * topFiveAvailableSkus=new ArrayList<String>();
		 * 
		 * 
		 * skusDetailsInfo=homePageService.getSkusDetails(); int i=0;
		 * for(SkuDetailsInfo s:skusDetailsInfo){ i++;
		 * topFiveAvailableSkus.add(s.getSkuId()); if(i==5) break; }
		 * familySkus=new ArrayList<skubo>();
		 */
	}

	public void download() {
		LOG.info("Download Clicked");

		try {
			final String File_Name = filename + ".xlsx";

			Workbook workbook = addDetailsService.exportData(configurations,
					shortages, filename);

			if (workbook != null) {
				workbook.setActiveSheet(0);

				ExternalContext externalContext = FacesContext
						.getCurrentInstance().getExternalContext();
				HttpServletResponse response = (HttpServletResponse) externalContext
						.getResponse();
				response.reset();
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + File_Name + "\"");
				workbook.write(response.getOutputStream());
				FacesContext.getCurrentInstance().responseComplete();
			}// if workbook not null
			else {
				LOG.info("Excel sheet not created due to empty Avs :");
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO,
										"avs not matched",
										"Excel sheet not created due to some problems. Please try again "));
			}
		}// try
		catch (Exception ioe) {
			ioe.printStackTrace();
			LOG.info("Exception in exporting:" + ioe.getMessage() + " "
					+ ioe.getCause());
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Export error",
							"Error in exporting. Please try again."));
		}// else

		LOG.info("Download completed");
		addMessage("File Downloaded Successfully");
	}

	/*
	 * public void buttonAction(ActionEvent e){
	 * 
	 * bufamily=new ArrayList<String>();
	 * 
	 * LOG.info("Button clicked"); FacesContext
	 * fc=FacesContext.getCurrentInstance(); String value=getvalue(fc,"family");
	 * String bu=getvalue(fc,"bu"); bufamily.add(bu); bufamily.add(value);
	 * homeBean.setBufamily(bufamily); LOG.info(value);
	 * homeBean.setSkusDetailsInfo(homePageService.getSkusDetailsforFamily(value
	 * ,bu)); }
	 */
	public String buttonAction() {

		return "newhome.xhtml?faces-redirect=true";
	}

	public String clickOnFamily() {

		return "newhome.xhtml?faces-redirect=true";
	}

	public String gotoHomePage() {
		// homeBean.init();
		return "newhome.xhtml?faces-redirect=true";
	}

	public String getvalue(FacesContext fc, String param) {
		Map<String, String> params = fc.getExternalContext()
				.getRequestParameterMap();
		LOG.info("Faces obj created ::value" + params);
		return params.get(param);
	}

	public void addToCart(ActionEvent event) {
		LOG.info("Adding " + selectedSkuDetails);

		if (cartService.isAddedSKUtocart()) {
			addMessage("SKU Quantity are being Calculated.....");
			return;
		}
		int items = selectedSkuDetails.getSkuAvailability();
		int item = (int) items;
		if (item == 0) {
			addMessage("For SKU " + selectedSkuDetails.getSkuId()
					+ " Availability is 0");
		} else {
			LOG.info(getQuantity());
			int qty = 0;
			try {
				qty = Integer.parseInt(getQuantity());
			} catch (Exception e) {
				qty = items;
			}
			if (qty <= 0) {
				qty = items;
			}
			if (qty > items) {
				qty = items;
			}
			selectedSkuDetails.setSkuAvailability((int) (item - qty));
			LOG.info(qty);
			CartItems cartItems = null;
			cartItems = new CartItems();
			cartItems.setQuantity((int) qty);
			cartItems.setBrazilTime(getBrazilTime());
			cartItems.setAddTime(new Date());
			cartItems.setUsername(SecurityContextHolder.getContext()
					.getAuthentication().getName());
			SkuDetailsInfo info = convert(selectedSkuDetails);
			info.setSkuAvailability((int) qty);
			cartItems.setSkuDetailsInfo(info);
			cartService.addtoCart(cartItems);
			LOG.info(item + "\t" + cartItems);
			homeBean.setCartItems(homePageService.getCartCount());
			homeBean.loadbufamily();
		}
	}

	private String getBrazilTime() {
		SimpleDateFormat sdfAmerica = new SimpleDateFormat(
				"E hh:mm a MMM dd, yyyy");
		sdfAmerica.setTimeZone(TimeZone.getTimeZone("Brazil/West"));
		String sDateInAmerica = sdfAmerica.format(new Date());
		return sDateInAmerica;
	}

	private SkuDetailsInfo convert(SkuDetailsInfo skuData) {
		SkuDetailsInfo info = null;
		info = new SkuDetailsInfo();

		info.setBusinessUnit(skuData.getBusinessUnit());
		info.setFamily(skuData.getFamily());
		info.setSkuId(skuData.getSkuId());
		info.setSkuAvailability((int) skuData.getSkuAvailability());
		info.setNewOrderDeliveryWeeks(skuData.getNewOrderDeliveryWeeks());
		info.setPipeLineQuantity((int) skuData.getPipeLineQuantity());
		info.setPipeLineWeeks(skuData.getPipeLineWeeks());
		info.setConfiguration(skuData.getConfiguration());
		info.setPavail15Days(skuData.getPavail15Days());
		info.setPavail30Days(skuData.getPavail30Days());
		info.setPavail45Days(skuData.getPavail45Days());
		info.setPavail60Days(skuData.getPavail60Days());
		info.setPavail75Days(skuData.getPavail75Days());
		info.setPavail90Days(skuData.getPavail90Days());
		info.setShortage(skuData.getShortage());
		info.setDescription(skuData.getDescription());
		String bu = skuData.getBusinessUnit();
		if (bu.equalsIgnoreCase("BNB") || bu.equalsIgnoreCase("CNB")) {
			info.setImageType("NB");
		} else if (bu.equalsIgnoreCase("BPC") || bu.equalsIgnoreCase("CPC")) {
			info.setImageType("PC");
		} else if (bu.equalsIgnoreCase("WST")) {
			info.setImageType("WS");
		}
		return info;
	}

	public void addMessage(String summary) {
		LOG.info(summary);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("", summary));

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

	public List<String> getTopFiveSellingSkus() {
		return topFiveSellingSkus;
	}

	public void setTopFiveSellingSkus(List<String> topFiveSellingSkus) {
		this.topFiveSellingSkus = topFiveSellingSkus;
	}

	public List<String> getTopFiveAvailableSkus() {
		return topFiveAvailableSkus;
	}

	public void setTopFiveAvailableSkus(List<String> topFiveAvailableSkus) {
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

	public HomeBean getHomeBean() {
		return homeBean;
	}

	public void setHomeBean(HomeBean homeBean) {
		this.homeBean = homeBean;
	}

	public String getSelectedSku() {
		return selectedSku;
	}

	public void setSelectedSku(String selectedSku) {
		this.selectedSku = selectedSku;
	}

	public List<String> getBnbfamily() {
		return bnbfamily;
	}

	public void setBnbfamily(List<String> bnbfamily) {
		this.bnbfamily = bnbfamily;
	}

	public List<String> getBpcfamily() {
		return bpcfamily;
	}

	public void setBpcfamily(List<String> bpcfamily) {
		this.bpcfamily = bpcfamily;
	}

	public List<String> getCpcfamily() {
		return cpcfamily;
	}

	public void setCpcfamily(List<String> cpcfamily) {
		this.cpcfamily = cpcfamily;
	}

	public List<String> getWstfamily() {
		return wstfamily;
	}

	public void setWstfamily(List<String> wstfamily) {
		this.wstfamily = wstfamily;
	}

	public List<String> getCnbfamily() {
		return cnbfamily;
	}

	public void setCnbfamily(List<String> cnbfamily) {
		this.cnbfamily = cnbfamily;
	}

	public List<String> getBufamily() {
		return bufamily;
	}

	public void setBufamily(List<String> bufamily) {
		this.bufamily = bufamily;
	}

	/*
	 * public HomeBean getHomeBean() { return homeBean; }
	 * 
	 * public void setHomeBean(HomeBean homeBean) { this.homeBean = homeBean; }
	 */

	public SkuDetailsInfo getSelectedSkuDetails() {
		return selectedSkuDetails;
	}

	public void setSelectedSkuDetails(SkuDetailsInfo selectedSkuDetails) {
		this.selectedSkuDetails = selectedSkuDetails;
	}

	public List<String> getDates() {
		return dates;
	}

	public void setDates(List<String> dates) {
		this.dates = dates;
	}

	public List<String> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(List<String> configurations) {
		this.configurations = configurations;
	}

	public List<String> getShortages() {
		return shortages;
	}

	public void setShortages(List<String> shortages) {
		this.shortages = shortages;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	/*
	 * public Skuservice getSkuservice() { return skuservice; }
	 * 
	 * public void setSkuservice(Skuservice skuservice) { this.skuservice =
	 * skuservice; }
	 */

}
