package com.techouts.pojo;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="BomStatus")
public class BomStatus 
{
@Field("Collection")
private String collection;
@Field("File Date")
private Date fileDate;
@Field("Status")
private boolean collectionUpdatedStatus;
public String getCollection() {
	return collection;
}
public void setCollection(String collection) {
	this.collection = collection;
}
public Date getFileDate() {
	return fileDate;
}
public void setFileDate(Date fileDate) {
	this.fileDate = fileDate;
}
public boolean isCollectionUpdatedStatus() {
	return collectionUpdatedStatus;
}
public void setCollectionUpdatedStatus(boolean collectionUpdatedStatus) {
	this.collectionUpdatedStatus = collectionUpdatedStatus;
}
@Override
public String toString() {
	return "BomStatus [collection=" + collection + ", fileDate=" + fileDate
			+ ", collectionUpdatedStatus=" + collectionUpdatedStatus + "]";
}
}
