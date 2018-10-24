package com.techouts.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "ReplicaStatus")
public class ReplicaStatus {
	@Field("collection Name")
	private String collectionName;
	@Field("Replication Status")
	private String replicaStatus;

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getReplicaStatus() {
		return replicaStatus;
	}

	public void setReplicaStatus(String replicaStatus) {
		this.replicaStatus = replicaStatus;
	}
}
