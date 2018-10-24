package com.techouts.hp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.techouts.hp.dao.DataDao;
import com.techouts.hp.pojo.FileInfo;

public class BomDaoImpl implements DataDao
{
	@Autowired
	private MongoTemplate mongoTemplate;
	public boolean insertBomData(List<?> bomList,String collectionName,boolean collectionExists)
	{
		try
		{
		if(mongoTemplate.getCollection(collectionName).count()>0 && collectionExists)
		{
			mongoTemplate.dropCollection(collectionName);
			mongoTemplate.insertAll(bomList);
		return true;
		}
		else
		{
			mongoTemplate.insertAll(bomList);
			return true;
		}
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean insertFileData(List<?> datList) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean insertFileInfo(FileInfo fileInfo)
	{
		try {
			mongoTemplate.insert(fileInfo);
			return true;
		} catch (Exception exception) {

		}
		return false;
	}
	@Override
	public boolean generateLatestDataCollection(List<?> dataList,
			String collectionName, String newfileDate) {
		// TODO Auto-generated method stub
		return false;
	}
	}

