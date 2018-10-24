package com.techouts.hp.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.techouts.hp.pojo.FileInfo;

@Repository
public class StatusDaoImpl {
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<String> getAllFileDates(String collectionName, String fieldName) {
		try {
			return mongoTemplate.getCollection(collectionName).distinct(
					fieldName);
		} catch (NullPointerException nullPointerException) {
			return new ArrayList<String>();
		}
	}

	public List<FileInfo> getUplodedFilesList() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		Query query = new Query();
		query.with(new Sort(Sort.Direction.ASC, "Source Folder"));
		query.addCriteria(Criteria.where("File Upload Date").gte(now.getTime()));
		return mongoTemplate.find(query, FileInfo.class);
	}

	public Date maxFileDate(String subFolder) {
		Query query = new Query(Criteria.where("Upload Status").is(true)
				.and("Source Folder").is(subFolder));
		query.with(new Sort(Sort.Direction.DESC, "File Received Date"));
		query.limit(1);
		try {
			return mongoTemplate.findOne(query, FileInfo.class)
					.getFileReceivedDate();
		} catch (NullPointerException nullPointerException) {
			return null;
		}

	}
}
