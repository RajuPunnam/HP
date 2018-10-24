package com.techouts.hp.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.techouts.hp.dao.DataDao;
import com.techouts.hp.dto.PcOpenorders;
import com.techouts.hp.pojo.FileInfo;

public class DataDaoImpl implements DataDao {
	private static final Logger LOG = Logger.getLogger(DataDaoImpl.class);
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public boolean insertFileData(List<?> dataList) {
		try {
			mongoTemplate.insertAll(dataList);
			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		return false;
	}

	@Override
	public boolean insertFileInfo(FileInfo fileInfo) 
	{
		Query query = new Query();
		query.addCriteria(Criteria.where("Source Folder").is(fileInfo.getSourceFolder()).and("File Name").is(fileInfo.getFileName()));
		Update update = new Update();
		try
		{
		update.set("Ftp Location", fileInfo.getFtpLocation());
		update.set("Source Folder", fileInfo.getSourceFolder());
		update.set("File Name", fileInfo.getFileName());
		update.set("File Received Date", fileInfo.getFileReceivedDate());
		update.set("File Upload Date", fileInfo.getFileUploadedDate());
		update.set("Records Count", fileInfo.getNoOfRecords());
		update.set("Upload Status", fileInfo.isUploadStatus());
		mongoTemplate.upsert(query, update, FileInfo.class);
		return true;
		}
		catch(Exception exception)
		{
		return false;	
		}
		
	}

	@Override
	public boolean insertBomData(List<?> bomList, String collectionName,
			boolean collectionExists) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean generateLatestDataCollection(List<?> dataList,
			String collectionName, String newfileDate) {
		Date latestDate = null;
		try {
			latestDate = new SimpleDateFormat("MM-dd-yyyy").parse(newfileDate);
			if (mongoTemplate.getCollection(collectionName).count() > 0) {
				Object fileDate = mongoTemplate.getCollection(collectionName)
						.findOne().get("Date");
				Date oldOrdersDate = new SimpleDateFormat("MM-dd-yyyy")
						.parse(String.valueOf(fileDate));
				if (oldOrdersDate.before(latestDate)) {
					mongoTemplate.dropCollection(collectionName);
					mongoTemplate.insert(dataList, collectionName);
					return true;
				} else {
					return false;
				}
			} else {
				mongoTemplate.insert(dataList, collectionName);
				return true;
			}
		} catch (Exception exception) {
		}
		return false;
	}
}
