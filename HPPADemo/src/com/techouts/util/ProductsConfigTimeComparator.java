package com.techouts.util;

import java.util.Comparator;

import com.techouts.pojo.ProductsConfig;

public class ProductsConfigTimeComparator implements Comparator<ProductsConfig> {

	@Override
	public int compare(ProductsConfig productConfig1,
			ProductsConfig productConfig2) {
		return ((Integer) productConfig2.getLeadTime())
				.compareTo(productConfig1.getLeadTime());
	}

}
