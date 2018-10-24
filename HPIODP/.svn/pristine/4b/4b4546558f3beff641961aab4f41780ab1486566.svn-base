package com.inventory.utill;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class AgingBucketAggregation {
    @Field("Age")
	private String bucket;
    private double bucketTotalPrice;
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public double getBucketTotalPrice() {
		return bucketTotalPrice;
	}
	public void setBucketTotalPrice(double bucketTotalPrice) {
		this.bucketTotalPrice = bucketTotalPrice;
	}
	@Override
	public String toString() {
		return "AgingBucketAggregation [bucket=" + bucket
				+ ", bucketTotalPrice=" + bucketTotalPrice + "]";
	}
}
