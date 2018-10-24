package com.techouts.hp.dao;

import java.util.List;

import com.techouts.hp.pojo.FileInfo;

public interface DataDao {
	boolean insertFileData(List<?> datList);

	boolean insertFileInfo(FileInfo fileInfo);

	boolean insertBomData(List<?> bomList, String collectionName,
			boolean collectionExists);
	boolean generateLatestDataCollection(List<?> dataList,String collectionName,String newfileDate);
}
