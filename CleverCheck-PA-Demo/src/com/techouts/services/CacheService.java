package com.techouts.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.dao.HomePageDao;
import com.techouts.dto.All_Skus_Availability;
import com.techouts.pojo.AvAvbail;
import com.techouts.pojo.BUFamilyAvailability;
import com.techouts.pojo.CartItems;
import com.techouts.pojo.DOInventory;
import com.techouts.pojo.SkuDetailsInfo;
import com.techouts.re.dao.SkuAvailDao;

@Service
public class CacheService {

	@Autowired
	private HomePageDao homePageDao;

	@Autowired
	private SkuAvailDao skuAvailDao;

	private Map<String, SkuDetailsInfo> skusinfo = null;
	private Map<String, SkuDetailsInfo> skusconfinfo = null;
	private Map<String, List<String>> bufamilysku = null;
	private Map<String, List<BUFamilyAvailability>> bufamily = null;
	private Map<String, List<String>> buskus = null;
	private Map<String, List<CartItems>> userscart = null;
	private List<AvAvbail> avAvbailList = null;
	private Set<String> skufamilybu = null;
	private Set<String> sku = null;
	private Map<String, List<String>> familysku = null;

	private Map<String, Double> doiMap = null;
	private Map<String, Double> skuDoiMap = null;
	private Map<String, Double> inventoryMap = null;
	private boolean addedSKUtocart;
	private boolean addedSKUConfigTocart;

	@PostConstruct
	public void init() {
		service();
	}

	public void service() {
		skusinfo = new HashMap<String, SkuDetailsInfo>();
		skusconfinfo = new HashMap<String, SkuDetailsInfo>();
		bufamilysku = new HashMap<String, List<String>>();
		familysku = new HashMap<String, List<String>>();
		bufamily = new HashMap<String, List<BUFamilyAvailability>>();
		userscart = new ConcurrentHashMap<String, List<CartItems>>();
		buskus = new HashMap<String, List<String>>();
		avAvbailList = new ArrayList<AvAvbail>();
		avAvbailList.addAll(homePageDao.getAvAVBAIL());
		skufamilybu = new HashSet<String>();
		sku = new HashSet<String>();

		doiMap = new HashMap<String, Double>();
		List<DOInventory> doiList = skuAvailDao.getDoiData();
		for (DOInventory dOInventory : doiList) {
			doiMap.put(dOInventory.getPartId(),
					(double) dOInventory.getQuantity());
		}
		skuDoiMap = new HashMap<String, Double>();
		inventoryMap = new HashMap<String, Double>();

		for (All_Skus_Availability skuData : homePageDao
				.getAllSkuAvailability()) {
			String key = null;
			key = skuData.getSkuId();
			skufamilybu.add(key);
			sku.add(key);
			skufamilybu.add(skuData.getFamily());
			skufamilybu.add(skuData.getBu());
			skusinfo.put(key, convert(skuData));
		}
		for (All_Skus_Availability skuData : homePageDao
				.getAllSkuConfAvailability()) {
			String key = null;
			key = skuData.getSkuId();
			skusconfinfo.put(key, convert(skuData));
		}
		loadfamilyskus();

		loadbufamilyData("BPC");
		loadbufamilyData("CPC");
		loadbufamilyData("CNB");
		loadbufamilyData("BNB");
		loadbufamilyData("WST");

		loadbuskus("BPC");
		loadbuskus("CPC");
		loadbuskus("CNB");
		loadbuskus("BNB");
		loadbuskus("WST");

		loadfamilyskus("BNB");
		loadfamilyskus("CNB");
		loadfamilyskus("CPC");
		loadfamilyskus("BPC");
		loadfamilyskus("WST");
	}

	public boolean familyexists(String family) {
		return familysku.containsKey(family);
	}

	public List<String> getskusforFamily(String family) {
		if (familysku.containsKey(family)) {
			return familysku.get(family);
		}
		return null;
	}

	public Set<String> getSearchData() {
		return skufamilybu;
	}
	
	public Set<String> getDisSearchData() {
		return sku;
	}
	
	public List<AvAvbail> getAvAvail() {
		return avAvbailList;
	}

	public Map<String, Double> getDoiMap() {
		return doiMap;
	}

	public Map<String, Double> getSkuDoiMap() {
		return skuDoiMap;
	}

	public Map<String, Double> getInventoryMap() {
		return inventoryMap;
	}

	public void addtoCart(CartItems cartItem) {
		String key = cartItem.getUsername();
		List<CartItems> cartItems = null;
		cartItems = new ArrayList<CartItems>();
		if (userscart.containsKey(key)) {
			cartItems.addAll(userscart.get(key));
		}
		cartItems.add(cartItem);
		userscart.put(key, cartItems);
	}

	public void removeAllFromCart() {
		userscart.clear();
		userscart = new ConcurrentHashMap<String, List<CartItems>>();
	}

	public void removeFromCart(CartItems cartItem) {
		String key = cartItem.getUsername();
		List<CartItems> cartItems = null;
		cartItems = new ArrayList<CartItems>();
		if (userscart.containsKey(key)) {
			for (CartItems c : userscart.get(key)) {
				if (cartItem.getAddTime().equals(c.getAddTime())) {
					// skip;
				} else
					cartItems.add(c);
			}
			if (cartItems.isEmpty()) {
				userscart.remove(key);
			}
			userscart.put(key, cartItems);
		}
	}

	public int getUserCartqty() {
		// String[]
		// user=SecurityContextHolder.getContext().getAuthentication().getName().split("@");
		// /System.out.println(user);
		int qty = 0;
		// if(userscart.containsKey(user[0])){
		for (String key : userscart.keySet()) {
			for (CartItems cart : userscart.get(key)) {
				qty += cart.getQuantity();
			}
		}
		// }
		return qty;
	}

	public List<CartItems> getAllUsersCart() {
		List<CartItems> cartItems = null;
		cartItems = new ArrayList<CartItems>();
		for (String user : userscart.keySet()) {
			cartItems.addAll(userscart.get(user));
		}
		return cartItems;
	}

	public List<CartItems> getUserCart(String user) {
		if (userscart.containsKey(user)) {
			return userscart.get(user);
		}
		return null;
	}

	public List<BUFamilyAvailability> getbufamily(String bu) {
		if (bufamily.containsKey(bu)) {
			return bufamily.get(bu);
		}
		return null;
	}

	public void updatebufamily(String bu, List<BUFamilyAvailability> family) {
		bufamily.put(bu, family);
	}

	public List<String> getbuskus(String bu) {
		if (buskus.containsKey(bu)) {
			return buskus.get(bu);
		}
		return null;
	}

	public List<String> getSkusforbufamily(String bu, String family) {
		String key = bu + "-" + family;
		if (bufamilysku.containsKey(key)) {
			return bufamilysku.get(key);
		}
		return null;
	}

	public List<SkuDetailsInfo> getskuDetailsInfo() {
		List<SkuDetailsInfo> skuDetailsInfos = null;
		skuDetailsInfos = new ArrayList<SkuDetailsInfo>();
		for (String key : skusinfo.keySet()) {
			skuDetailsInfos.add(skusinfo.get(key));
		}
		Collections.sort(skuDetailsInfos);
		return skuDetailsInfos;
	}

	public List<SkuDetailsInfo> getskuConfigInfo() {
		List<SkuDetailsInfo> skuDetailsInfos = null;
		skuDetailsInfos = new ArrayList<SkuDetailsInfo>();
		for (String key : skusconfinfo.keySet()) {
			skuDetailsInfos.add(skusconfinfo.get(key));
		}
		Collections.sort(skuDetailsInfos);
		return skuDetailsInfos;
	}

	public SkuDetailsInfo getskuDetailsInfo(String sku) {

		if (skusinfo.containsKey(sku)) {
			return skusinfo.get(sku);
		}
		return null;
	}

	public SkuDetailsInfo getSkuConfigInfo(String sku) {
		if (skusconfinfo.containsKey(sku)) {
			return skusconfinfo.get(sku);
		}
		return null;
	}

	public void updateSkuDetailsInfo(SkuDetailsInfo skuDetailsInfo) {
		skusinfo.put(skuDetailsInfo.getSkuId(), skuDetailsInfo);
	}

	public void updateSkuConfigInfo(SkuDetailsInfo skuDetailsInfo) {
		skusconfinfo.put(skuDetailsInfo.getSkuId(), skuDetailsInfo);
	}

	private void loadfamilyskus(String bu) {
		for (String family : getfamilybu(bu)) {
			if (!family.isEmpty()) {
				List<String> bufamilys = null;
				bufamilys = new ArrayList<String>();
				for (String skus : homePageDao.getDistinctSkusforFamilyBU(
						family, bu)) {
					bufamilys.add(skus);
				}
				bufamilysku.put(bu + "-" + family, bufamilys);

			}
		}

	}

	private void loadfamilyskus() {
		for (String family : homePageDao.getDistinctfamilys()) {
			familysku.put(family, homePageDao.getdistinctskusForFamily(family));
		}
	}

	private void loadbufamilyData(String bu) {
		bufamily.put(bu, homePageDao.getAllAvailabilitycount(bu));
	}

	private void loadbuskus(String bu) {
		buskus.put(bu, homePageDao.getAllbuSkus(bu));
	}

	private List<String> getfamilybu(String bu) {
		return homePageDao.getdistinctfamilies(bu);
	}

	private SkuDetailsInfo convert(All_Skus_Availability skuData) {
		SkuDetailsInfo info = null;
		info = new SkuDetailsInfo();

		info.setBusinessUnit(skuData.getBu());
		info.setFamily(skuData.getFamily());
		info.setSkuId(skuData.getSkuId());
		info.setSkuAvailability((int) skuData.getSkuAvailability());
		info.setNewOrderDeliveryWeeks(skuData.getNewOrderDeliveryWeeks());
		info.setPipeLineQuantity((int) skuData.getPipeLineQuantity());
		info.setPipeLineWeeks(skuData.getPipeLineWeeks());
		info.setConfiguration(skuData.getConfigurations());
		info.setPavail15Days(skuData.getPavail15Days());
		info.setPavail30Days(skuData.getPavail30Days());
		info.setPavail45Days(skuData.getPavail45Days());
		info.setPavail60Days(skuData.getPavail60Days());
		info.setPavail75Days(skuData.getPavail75Days());
		info.setPavail90Days(skuData.getPavail90Days());
		info.setShortage(skuData.getShortages());
		info.setDescription(skuData.getDescription());
		String bu = skuData.getBu();
		if (bu.equalsIgnoreCase("BNB") || bu.equalsIgnoreCase("CNB")) {
			info.setImageType("NB");
		} else if (bu.equalsIgnoreCase("BPC") || bu.equalsIgnoreCase("CPC")) {
			info.setImageType("PC");
		} else if (bu.equalsIgnoreCase("WST")) {
			info.setImageType("WS");
		}
		return info;
	}

	public boolean isAddedSKUtocart() {
		return addedSKUtocart;
	}

	public void setAddedSKUtocart(boolean addedSKUtocart) {
		this.addedSKUtocart = addedSKUtocart;
	}

	public boolean isAddedSKUConfigTocart() {
		return addedSKUConfigTocart;
	}

	public void setAddedSKUConfigTocart(boolean addedSKUConfigTocart) {
		this.addedSKUConfigTocart = addedSKUConfigTocart;
	}

}
