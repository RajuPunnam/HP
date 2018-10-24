package com.inventory.utill;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


@Component
public class FileSupport {

private static	Logger log = Logger.getLogger(FileSupport.class);
	public static boolean getMoveFilesToSucessOrFailureFolder(String sourceFile, String destinationFolder, File file)
	{
		File targetFolder = new File(destinationFolder);
		if (!targetFolder.exists())
		{
			targetFolder.mkdirs();
		}
		boolean filemovingStatus = false;
		try
		{
			File oldFile = new File(sourceFile, file.getName());
			if (oldFile.renameTo(new File(destinationFolder + File.separator, file.getName())))
			{
				log.info(file.getName() + "The file was moved successfully to the new folder");
				filemovingStatus = true;
			} else
			{
				log.info(file.getName() + "The File was not moved.");

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return filemovingStatus;
	}

	
	
}
