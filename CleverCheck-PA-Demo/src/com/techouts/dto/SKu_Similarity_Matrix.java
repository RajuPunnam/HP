package com.techouts.dto;

import org.springframework.data.mongodb.core.mapping.Field;

public class SKu_Similarity_Matrix {

	@Field("ActualSku")
	private String actualSku;
	@Field("MatchedSku")
	private String matchedSku;
	@Field("Similarity_Index")
	private double simitarityIndex;
	@Field("Index_Bucket") 
	private String bucket;
	public String getActualSku() {
		return actualSku;
	}
	public void setActualSku(String actualSku) {
		this.actualSku = actualSku;
	}
	public String getMatchedSku() {
		return matchedSku;
	}
	public void setMatchedSku(String matchedSku) {
		this.matchedSku = matchedSku;
	}
	public double getSimitarityIndex() {
		return simitarityIndex;
	}
	public void setSimitarityIndex(double simitarityIndex) {
		this.simitarityIndex = simitarityIndex;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	@Override
	public String toString() {
		return "SKu_Similarity_Matrix [actualSku=" + actualSku + ", matchedSku=" + matchedSku + ", simitarityIndex="
				+ simitarityIndex + ", bucket=" + bucket + "]";
	}
	
	
}
