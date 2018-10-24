package com.techouts.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.exceptions.HPPAException;
import com.techouts.pojo.AvAvbail;
import com.techouts.pojo.AvPartFinalQtyPer;
import com.techouts.pojo.BUFamilyAvailability;
import com.techouts.pojo.CartItems;
import com.techouts.pojo.FlexBom;
import com.techouts.pojo.OverAllSkusAvailability;
import com.techouts.pojo.PriAltBom;
import com.techouts.pojo.SkuDetailsInfo;
import com.techouts.re.dao.SkuAvailDao;
import com.techouts.re.services.SkuAvalibility;

@Service
public class CartService {
	private static final Logger LOG = Logger.getLogger(CartService.class);
	@Autowired
	private SkuAvailDao skuAvailDao;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private SkuAvalibility skuAvalibility;

	Logger logger = Logger.getLogger(CartService.class);

	public List<CartItems> getcatitems() {
		return cacheService.getAllUsersCart();
	}

	public int getCartCount() {
		return cacheService.getUserCartqty();
	}

	public void addbufamily(SkuDetailsInfo s) {
		for (BUFamilyAvailability bu : cacheService.getbufamily(s
				.getBusinessUnit())) {
			if (bu.getFamily().isEmpty()) {
				bu.setBuAvail(bu.getBuAvail() - s.getSkuAvailability());
			}
			if (bu.getFamily().equals(s.getFamily())) {
				bu.setFamilyAvail(bu.getFamilyAvail() - s.getSkuAvailability());
			}
		}
	}

	public void removebufamily(SkuDetailsInfo s) {
		for (BUFamilyAvailability bu : cacheService.getbufamily(s
				.getBusinessUnit())) {
			if (bu.getFamily().isEmpty()) {
				bu.setBuAvail(bu.getBuAvail() + s.getSkuAvailability());
			}
			if (bu.getFamily().equals(s.getFamily())) {
				bu.setFamilyAvail(bu.getFamilyAvail() + s.getSkuAvailability());
			}
		}
	}

	private List<String> getAvs(SkuDetailsInfo info) {
		List<String> avs = null;
		avs = new ArrayList<String>();
		for (String conf : info.getConfiguration()) {
			String[] confs = conf.split(": ");
			avs.add(confs[0]);
		}
		return avs;
	}

	private int cartsize = 0;

	public void removeAllFromCart() {
		LOG.info("Empty cart");
		cartsize = cacheService.getAllUsersCart().size() - 1;
		for (CartItems c : cacheService.getAllUsersCart()) {
			removeFromCart(c);
		}
		cacheService.removeAllFromCart();
	}

	public void removeFromCart(CartItems cartItems) {
		cacheService.removeFromCart(cartItems);
		removebufamily(cartItems.getSkuDetailsInfo());
		cacheService.setAddedSKUtocart(true);
		SkuDetailsInfo skuDetailsInfo = null;
		skuDetailsInfo = cacheService.getskuDetailsInfo(cartItems
				.getSkuDetailsInfo().getSkuId());
		if (skuDetailsInfo == null) {
			skuDetailsInfo = cacheService.getSkuConfigInfo(cartItems
					.getSkuDetailsInfo().getSkuId());
		}
		skuDetailsInfo.setSkuAvailability(skuDetailsInfo.getSkuAvailability()
				+ cartItems.getSkuDetailsInfo().getSkuAvailability());
		new Thread(new Runnable() {
			public void run() {
				Removedtocart(cartItems);
			}
		}).start();
	}

	public boolean isAddedSKUtocart() {
		return cacheService.isAddedSKUtocart();
	}

	public boolean isAddedSKUConfigTocart() {
		return cacheService.isAddedSKUConfigTocart();
	}

	public void addtoCart(CartItems cartItems) {
		addbufamily(cartItems.getSkuDetailsInfo());
		cacheService.addtoCart(cartItems);
		cacheService.setAddedSKUtocart(true);
		cacheService.setAddedSKUConfigTocart(true);
		SkuDetailsInfo skuDetailsInfo = null;
		skuDetailsInfo = cacheService.getskuDetailsInfo(cartItems
				.getSkuDetailsInfo().getSkuId());
		if (skuDetailsInfo == null) {
			skuDetailsInfo = cacheService.getSkuConfigInfo(cartItems
					.getSkuDetailsInfo().getSkuId());
		}
		skuDetailsInfo.setSkuAvailability(skuDetailsInfo.getSkuAvailability()
				- cartItems.getSkuDetailsInfo().getSkuAvailability());
		new Thread(new Runnable() {
			public void run() {
				addedtocart(cartItems);
			}
		}).start();
	}

	public synchronized void addedtocart(CartItems cartItems) {
		LOG.info("add to cart started");
		long sec = new Date().getTime();
		try {
			boolean isCalc = false;
			double skuQty = cartItems.getQuantity();
			List<AvAvbail> avAvbailList = cacheService.getAvAvail();
			List<SkuDetailsInfo> overAllSkusAvailabilityList = cacheService
					.getskuDetailsInfo();
			List<SkuDetailsInfo> overAllonfigSkusAvailabilityList = cacheService
					.getskuConfigInfo();
			Map<String, Double> cartDoiMap = cacheService.getDoiMap();
			Map<String, Double> skuDoiMap = cacheService.getSkuDoiMap();
			List<String> avList = getAvs(cartItems.getSkuDetailsInfo());
			SkuDetailsInfo skuDetailsInfo = cartItems.getSkuDetailsInfo();
			if (cartDoiMap.containsKey(skuDetailsInfo.getSkuId())) {
				double doiqty = cartDoiMap.get(skuDetailsInfo.getSkuId());
				if (skuQty >= doiqty) {
					skuQty = skuQty - doiqty;
					cartDoiMap.put(skuDetailsInfo.getSkuId(), 0.0);
					if (skuDoiMap.containsKey(skuDetailsInfo.getSkuId())) {
						skuDoiMap.put(skuDetailsInfo.getSkuId(),
								skuDoiMap.get(skuDetailsInfo.getSkuId())
										+ doiqty);
					} else {
						skuDoiMap.put(skuDetailsInfo.getSkuId(), doiqty);
					}
					if (skuQty > 0)
						isCalc = true;
				} else {
					if (skuDoiMap.containsKey(skuDetailsInfo.getSkuId())) {
						skuDoiMap.put(skuDetailsInfo.getSkuId(),
								skuDoiMap.get(skuDetailsInfo.getSkuId())
										+ skuQty);
					} else {
						skuDoiMap.put(skuDetailsInfo.getSkuId(), skuQty);
					}
					skuQty = doiqty - skuQty;
					cartDoiMap.put(skuDetailsInfo.getSkuId(), skuQty);
				}
			} else {
				isCalc = true;

			}
			if (isCalc) {
				Map<String, Double> inventoryMap = cacheService
						.getInventoryMap();
				List<FlexBom> flexBomList = skuAvailDao.getFlexBomData();
				List<PriAltBom> priAltList = skuAvailDao.getPriAltData();
				List<AvPartFinalQtyPer> avPartFinalQtyPerList = skuAvailDao
						.getAvPartFinalQtyPer();
				Map<String, List<String>> priAltMap = new HashMap<String, List<String>>();
				for (PriAltBom priAltBom : priAltList) {
					String id = priAltBom.getPriPart();
					List<String> tempList = new ArrayList<String>();
					if (priAltMap.containsKey(id))
						tempList.addAll(priAltMap.get(id));
					tempList.add(priAltBom.getAltPart());
					priAltMap.put(id, tempList);
				}
				Map<String, List<AvPartFinalQtyPer>> avPartsMap = new HashMap<String, List<AvPartFinalQtyPer>>();
				for (AvPartFinalQtyPer avPartFinalQtyPer : avPartFinalQtyPerList) {
					List<AvPartFinalQtyPer> partList = new ArrayList<AvPartFinalQtyPer>();
					if (avPartsMap.containsKey(avPartFinalQtyPer.getAv())) {
						partList.addAll(avPartsMap.get(avPartFinalQtyPer
								.getAv()));
					}
					partList.add(avPartFinalQtyPer);
					avPartsMap.put(avPartFinalQtyPer.getAv(), partList);
				}
				Map<String, List<FlexBom>> avFlexBomMap = new HashMap<String, List<FlexBom>>();
				for (FlexBom flexBom : flexBomList) {
					List<FlexBom> tempFlexBom = new ArrayList<FlexBom>();
					if (avFlexBomMap.containsKey(flexBom.getAv()))
						tempFlexBom.addAll(avFlexBomMap.get(flexBom.getAv()));
					tempFlexBom.add(flexBom);
					avFlexBomMap.put(flexBom.getAv(), tempFlexBom);
				}
				List<FlexBom> skuFlexBomList = new ArrayList<FlexBom>();
				List<AvPartFinalQtyPer> skuPartList = new ArrayList<AvPartFinalQtyPer>();
				for (String av : avList) {
					if (avFlexBomMap.containsKey(av))
						skuFlexBomList.addAll(avFlexBomMap.get(av));
					if (avPartsMap.containsKey(av))
						skuPartList.addAll(avPartsMap.get(av));
				}

				skuAvalibility.inventoryReduction(skuQty, skuFlexBomList,
						cartDoiMap, avList, priAltMap, inventoryMap,
						skuDetailsInfo.getSkuId());
				for (SkuDetailsInfo overAllSkusAvailability : overAllSkusAvailabilityList) {
					avList.clear();
					Map<String, Double> tempDoiMap = new HashMap<String, Double>();
					tempDoiMap.putAll(cartDoiMap);
					for (String av : overAllSkusAvailability.getConfiguration()) {
						avList.add(av.substring(0, av.indexOf(":")));
					}
					List<FlexBom> skuFlexBom = new ArrayList<FlexBom>();
					List<AvPartFinalQtyPer> skuPart = new ArrayList<AvPartFinalQtyPer>();
					for (String av : avList) {
						if (avFlexBomMap.containsKey(av))
							skuFlexBom.addAll(avFlexBomMap.get(av));
						if (avPartsMap.containsKey(av))
							skuPart.addAll(avPartsMap.get(av));
					}
					if (overAllSkusAvailability.getSkuId()
							.equals("W5W99LT#AC4"))
						System.out.println();
					OverAllSkusAvailability shrtagelist = new OverAllSkusAvailability();
					double avbailSku = skuAvalibility.generateskuQuantity(
							overAllSkusAvailability.getSkuId(), tempDoiMap,
							priAltMap, skuPart, skuFlexBom, avPartsMap,
							avAvbailList, shrtagelist, null);
					if (cartDoiMap.containsKey(overAllSkusAvailability
							.getSkuId()))
						avbailSku = avbailSku
								+ cartDoiMap.get(overAllSkusAvailability
										.getSkuId());
					overAllSkusAvailability.setShortage(shrtagelist
							.getShortage());
					overAllSkusAvailability.setSkuAvailability((int) avbailSku);
					/*
					 * System.out.println("ADDING"+(count++) + " " +
					 * overAllSkusAvailability.getSkuId() + "====" +
					 * overAllSkusAvailability.getSkuAvailability());
					 * tempDoiMap.clear();
					 */
				}
				LOG.info("SKU AVALibility Calculated");
				for (SkuDetailsInfo overAllConfigSkusAvailability : overAllonfigSkusAvailabilityList) {
					avList.clear();
					Map<String, Double> tempDoiMap = new HashMap<String, Double>();
					tempDoiMap.putAll(cartDoiMap);
					for (String av : overAllConfigSkusAvailability
							.getConfiguration()) {
						avList.add(av.substring(0, av.indexOf(":")));
					}
					skuFlexBomList = new ArrayList<FlexBom>();
					skuPartList = new ArrayList<AvPartFinalQtyPer>();
					for (String av : avList) {
						if (avFlexBomMap.containsKey(av))
							skuFlexBomList.addAll(avFlexBomMap.get(av));
						if (avPartsMap.containsKey(av))
							skuPartList.addAll(avPartsMap.get(av));
					}
					OverAllSkusAvailability shrtagelist = new OverAllSkusAvailability();
					double avbailSku = skuAvalibility.generateskuQuantity(
							overAllConfigSkusAvailability.getSkuId(),
							tempDoiMap, priAltMap, skuPartList, skuFlexBomList,
							avPartsMap, avAvbailList, shrtagelist, null);
					overAllConfigSkusAvailability.setShortage(shrtagelist
							.getShortage());
					if (tempDoiMap.containsKey(overAllConfigSkusAvailability
							.getSkuId()))
						avbailSku = avbailSku
								+ tempDoiMap.get(overAllConfigSkusAvailability
										.getSkuId());
					overAllConfigSkusAvailability
							.setSkuAvailability((int) avbailSku);
					/*
					 * LOG.info("ADDING"+(count++) + " " +
					 * overAllConfigSkusAvailability.getSkuId() + "====" +
					 * overAllConfigSkusAvailability.getSkuAvailability());
					 * tempDoiMap.clear();
					 */
				}
			}
			// else {
			// for (SkuDetailsInfo overAllSkusAvailability :
			// overAllSkusAvailabilityList) {
			// if
			// (overAllSkusAvailability.getSkuId().contentEquals(skuDetailsInfo.getSkuId()))
			// overAllSkusAvailability
			// .setSkuAvailability(overAllSkusAvailability.getSkuAvailability()
			// - (int) skuQty);
			// }
			// }
		} catch (Exception e) {
			SkuDetailsInfo skuDetailsInfo = null;
			skuDetailsInfo = cacheService.getskuDetailsInfo(cartItems
					.getSkuDetailsInfo().getSkuId());
			if (skuDetailsInfo == null) {
				skuDetailsInfo = cacheService.getSkuConfigInfo(cartItems
						.getSkuDetailsInfo().getSkuId());
			}
			skuDetailsInfo.setSkuAvailability(skuDetailsInfo
					.getSkuAvailability()
					+ cartItems.getSkuDetailsInfo().getSkuAvailability());

			throw new HPPAException(
					"Exception in cartService.addedtocart(CartItems cartItems)"
							+ e.getMessage());

		} finally {
			LOG.info("Total Time :-" + (new Date().getTime() - sec) / (1000));
			cacheService.setAddedSKUtocart(false);
			cacheService.setAddedSKUConfigTocart(false);
		}
	}

	public synchronized void Removedtocart(CartItems cartItems) {
		LOG.info("remove from cart started"
				+ cartItems.getSkuDetailsInfo().getSkuId());
		long sec = new Date().getTime();
		try {
			boolean isCalc = false;
			double skuQty = cartItems.getQuantity();
			cacheService.removeFromCart(cartItems);
			List<AvAvbail> avAvbailList = cacheService.getAvAvail();
			List<SkuDetailsInfo> overAllSkusAvailabilityList = cacheService
					.getskuDetailsInfo();
			List<SkuDetailsInfo> overAllonfigSkusAvailabilityList = cacheService
					.getskuConfigInfo();
			Map<String, Double> cartDoiMap = cacheService.getDoiMap();
			Map<String, Double> skuDoiMap = cacheService.getSkuDoiMap();
			List<String> avList = getAvs(cartItems.getSkuDetailsInfo());
			SkuDetailsInfo skuDetailsInfo = cartItems.getSkuDetailsInfo();
			if (skuDoiMap.containsKey(skuDetailsInfo.getSkuId())) {
				double doiqty = skuDoiMap.get(skuDetailsInfo.getSkuId());
				if (skuQty >= doiqty) {
					skuDoiMap.remove(skuDetailsInfo.getSkuId());
					if (cartDoiMap.containsKey(skuDetailsInfo.getSkuId())) {
						cartDoiMap.put(skuDetailsInfo.getSkuId(),
								cartDoiMap.get(skuDetailsInfo.getSkuId())
										+ doiqty);
					} else {
						cartDoiMap.put(skuDetailsInfo.getSkuId(), doiqty);
					}
					skuQty = skuQty - doiqty;
					if (skuQty > 0)
						isCalc = true;
				} else {
					skuDoiMap.put(skuDetailsInfo.getSkuId(), doiqty - skuQty);
					if (cartDoiMap.containsKey(skuDetailsInfo.getSkuId())) {
						cartDoiMap.put(skuDetailsInfo.getSkuId(),
								cartDoiMap.get(skuDetailsInfo.getSkuId())
										+ skuQty);
					} else {
						cartDoiMap.put(skuDetailsInfo.getSkuId(), skuQty);
					}
				}
			} else {
				isCalc = true;
			}
			if (isCalc) {
				Map<String, Double> inventoryMap = cacheService
						.getInventoryMap();
				List<FlexBom> flexBomList = skuAvailDao.getFlexBomData();
				List<PriAltBom> priAltList = skuAvailDao.getPriAltData();
				List<AvPartFinalQtyPer> avPartFinalQtyPerList = skuAvailDao
						.getAvPartFinalQtyPer();
				Map<String, List<String>> priAltMap = new HashMap<String, List<String>>();
				for (PriAltBom priAltBom : priAltList) {
					String id = priAltBom.getPriPart();
					List<String> tempList = new ArrayList<String>();
					if (priAltMap.containsKey(id))
						tempList.addAll(priAltMap.get(id));
					tempList.add(priAltBom.getAltPart());
					priAltMap.put(id, tempList);
				}
				Map<String, List<AvPartFinalQtyPer>> avPartsMap = new HashMap<String, List<AvPartFinalQtyPer>>();
				for (AvPartFinalQtyPer avPartFinalQtyPer : avPartFinalQtyPerList) {
					List<AvPartFinalQtyPer> partList = new ArrayList<AvPartFinalQtyPer>();
					if (avPartsMap.containsKey(avPartFinalQtyPer.getAv())) {
						partList.addAll(avPartsMap.get(avPartFinalQtyPer
								.getAv()));
					}
					partList.add(avPartFinalQtyPer);
					avPartsMap.put(avPartFinalQtyPer.getAv(), partList);
				}
				Map<String, List<FlexBom>> avFlexBomMap = new HashMap<String, List<FlexBom>>();
				for (FlexBom flexBom : flexBomList) {
					List<FlexBom> tempFlexBom = new ArrayList<FlexBom>();
					if (avFlexBomMap.containsKey(flexBom.getAv()))
						tempFlexBom.addAll(avFlexBomMap.get(flexBom.getAv()));
					tempFlexBom.add(flexBom);
					avFlexBomMap.put(flexBom.getAv(), tempFlexBom);
				}
				List<FlexBom> skuFlexBomList = new ArrayList<FlexBom>();
				List<AvPartFinalQtyPer> skuPartList = new ArrayList<AvPartFinalQtyPer>();
				for (String av : avList) {
					if (avFlexBomMap.containsKey(av))
						skuFlexBomList.addAll(avFlexBomMap.get(av));
					if (avPartsMap.containsKey(av))
						skuPartList.addAll(avPartsMap.get(av));
				}
				for (Map.Entry<String, Double> entry : inventoryMap.entrySet()) {
					if (entry.getKey().contains(skuDetailsInfo.getSkuId())) {
						String[] skuPart = entry.getKey().split("::");
						if (cartDoiMap.containsKey(skuPart[1])) {
							cartDoiMap.put(
									skuPart[1],
									cartDoiMap.get(skuPart[1])
											+ entry.getValue());
						} else {
							cartDoiMap.put(skuPart[1], entry.getValue());
						}
					}
				}
				for (SkuDetailsInfo overAllSkusAvailability : overAllSkusAvailabilityList) {
					avList.clear();
					Map<String, Double> tempDoiMap = new HashMap<String, Double>();
					tempDoiMap.putAll(cartDoiMap);
					for (String av : overAllSkusAvailability.getConfiguration()) {
						avList.add(av.substring(0, av.indexOf(":")));
					}
					List<FlexBom> skuFlexBom = new ArrayList<FlexBom>();
					List<AvPartFinalQtyPer> skuPart = new ArrayList<AvPartFinalQtyPer>();
					for (String av : avList) {
						if (avFlexBomMap.containsKey(av))
							skuFlexBom.addAll(avFlexBomMap.get(av));
						if (avPartsMap.containsKey(av))
							skuPart.addAll(avPartsMap.get(av));
					}
					OverAllSkusAvailability shrtagelist = new OverAllSkusAvailability();
					double avbailSku = skuAvalibility.generateskuQuantity(
							overAllSkusAvailability.getSkuId(), tempDoiMap,
							priAltMap, skuPart, skuFlexBom, avPartsMap,
							avAvbailList, shrtagelist, null);
					if (cartDoiMap.containsKey(overAllSkusAvailability
							.getSkuId()))
						avbailSku = avbailSku
								+ cartDoiMap.get(overAllSkusAvailability
										.getSkuId());
					overAllSkusAvailability.setShortage(shrtagelist
							.getShortage());
					overAllSkusAvailability.setSkuAvailability((int) avbailSku);
					/*
					 * LOG.info((count++) + " " +
					 * overAllSkusAvailability.getSkuId() + "====" +
					 * overAllSkusAvailability.getSkuAvailability());
					 * tempDoiMap.clear();
					 */
				}
				LOG.info("SKU Removed From Cart");
				for (SkuDetailsInfo overAllConfigSkusAvailability : overAllonfigSkusAvailabilityList) {
					avList.clear();
					Map<String, Double> tempDoiMap = new HashMap<String, Double>();
					tempDoiMap.putAll(cartDoiMap);
					for (String av : overAllConfigSkusAvailability
							.getConfiguration()) {
						avList.add(av.substring(0, av.indexOf(":")));
					}
					skuFlexBomList = new ArrayList<FlexBom>();
					skuPartList = new ArrayList<AvPartFinalQtyPer>();
					for (String av : avList) {
						if (avFlexBomMap.containsKey(av))
							skuFlexBomList.addAll(avFlexBomMap.get(av));
						if (avPartsMap.containsKey(av))
							skuPartList.addAll(avPartsMap.get(av));
					}
					OverAllSkusAvailability shrtagelist = new OverAllSkusAvailability();
					double avbailSku = skuAvalibility.generateskuQuantity(
							overAllConfigSkusAvailability.getSkuId(),
							tempDoiMap, priAltMap, skuPartList, skuFlexBomList,
							avPartsMap, avAvbailList, shrtagelist, null);
					overAllConfigSkusAvailability.setShortage(shrtagelist
							.getShortage());
					if (tempDoiMap.containsKey(overAllConfigSkusAvailability
							.getSkuId()))
						avbailSku = avbailSku
								+ tempDoiMap.get(overAllConfigSkusAvailability
										.getSkuId());
					overAllConfigSkusAvailability
							.setSkuAvailability((int) avbailSku);
					/*
					 * LOG.info((count++) + " " +
					 * overAllConfigSkusAvailability.getSkuId() + "====" +
					 * overAllConfigSkusAvailability.getSkuAvailability());
					 * tempDoiMap.clear();
					 */
				}
			}
			// else {
			// for (SkuDetailsInfo overAllSkusAvailability :
			// overAllSkusAvailabilityList) {
			// if
			// (overAllSkusAvailability.getSkuId().contentEquals(skuDetailsInfo.getSkuId()))
			// overAllSkusAvailability
			// .setSkuAvailability(overAllSkusAvailability.getSkuAvailability()
			// + (int) skuQty);
			// }
			// }
		} catch (Exception e) {
			SkuDetailsInfo skuDetailsInfo = null;
			skuDetailsInfo = cacheService.getskuDetailsInfo(cartItems
					.getSkuDetailsInfo().getSkuId());
			if (skuDetailsInfo == null) {
				skuDetailsInfo = cacheService.getSkuConfigInfo(cartItems
						.getSkuDetailsInfo().getSkuId());
			}
			skuDetailsInfo.setSkuAvailability(skuDetailsInfo
					.getSkuAvailability()
					- cartItems.getSkuDetailsInfo().getSkuAvailability());

			throw new HPPAException(
					"Exception in cartservice.Removedtocart(CartItems cartItems)"
							+ e.getMessage());

		} finally {
			LOG.info("Total Time :-" + (new Date().getTime() - sec) / (1000));
			LOG.info("Removed From Cart");
			cacheService.setAddedSKUtocart(false);
			cacheService.setAddedSKUConfigTocart(false);
		}
	}

}
