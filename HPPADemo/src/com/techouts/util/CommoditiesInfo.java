package com.techouts.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class CommoditiesInfo {
	private static final Logger LOGGER = Logger
			.getLogger(CommoditiesInfo.class);

	public Set<String> getKeyFeaturesSet() {
		Set<String> keyFeatures = new LinkedHashSet<String>();
		keyFeatures.add("Base Unit");
		keyFeatures.add("Processor");
		keyFeatures.add("HDD");
		keyFeatures.add("Memory");
		keyFeatures.add("Panel");
		keyFeatures.add("Graphic Card");
		keyFeatures.add("AC Adapter");
		keyFeatures.add("Battery");
		keyFeatures.add("SSD");
		keyFeatures.add("Operating System");
		keyFeatures.add("Keyboard");
		keyFeatures.add("Miscellaneous");
		return keyFeatures;
	}

	public List<String> getKeyFeatureRelatedCommodities(String keyFeature) {

		List<String> relatedComodyties = new ArrayList<String>();
		if (keyFeature.equals("Base Unit")) {
			relatedComodyties.add("Base Unit");
			relatedComodyties.add("BASE UNIT");
		} else if (keyFeature.equals("Processor")) {
			relatedComodyties.add("CPU");
		} else if (keyFeature.equals("HDD")) {
			relatedComodyties.add("HDD");
		} else if (keyFeature.equals("Memory")) {
			relatedComodyties.add("Memory");
			relatedComodyties.add("MEMORY");
		} else if (keyFeature.equals("Panel")) {
			relatedComodyties.add("Panels");
		} else if (keyFeature.equals("Graphic Card")) {
			relatedComodyties.add("Graphic Card");
			relatedComodyties.add("GRAPHIC CARD");
		} else if (keyFeature.equals("AC Adapter")) {
			relatedComodyties.add("AC Adapter/Power Supply");
		} else if (keyFeature.equals("Battery")) {
			relatedComodyties.add("Battery");
		} else if (keyFeature.equals("SSD")) {
			relatedComodyties.add("SSD");
		} else if (keyFeature.equals("Operating System")) {
			relatedComodyties.add("OS");
		} else if (keyFeature.equals("Keyboard")) {
			relatedComodyties.add("Keyboard");
			relatedComodyties.add("KEYBOARD");
		} else if (keyFeature.equals("Miscellaneous")) {
			relatedComodyties.add("Miscellaneous");
			relatedComodyties.add("PCA");
			relatedComodyties.add("CD/DVD SW");
			relatedComodyties.add("Documents");
			relatedComodyties.add("Document / Spec");
			relatedComodyties.add("Speaker");
			relatedComodyties.add("Cables");
			relatedComodyties.add("Chassis");
			relatedComodyties.add("CHASSIS");
			relatedComodyties.add("Power Cord");
			relatedComodyties.add("Nameplates / Labels");
			relatedComodyties.add("LABEL");
			relatedComodyties.add("ODM Parts");
			relatedComodyties.add("Options");
			relatedComodyties.add("Mouse");
			relatedComodyties.add("MOUSE");
			relatedComodyties.add("ODD");
			relatedComodyties.add("Packaging Material");
			relatedComodyties.add("Thermal Module");
			relatedComodyties.add("Wireless Card");
			relatedComodyties.add("Warranty");
			relatedComodyties.add("Connector");
			relatedComodyties.add("Motherboard");
			relatedComodyties.add("SERVICES");
			relatedComodyties.add("Services");
			relatedComodyties.add("Connector");
			relatedComodyties.add("Optional / Addon");

		}
		return relatedComodyties;
	}

}
