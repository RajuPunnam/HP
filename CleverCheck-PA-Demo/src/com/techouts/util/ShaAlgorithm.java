/**
 * 
 */
package com.techouts.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Narasimha
 *
 */
public class ShaAlgorithm {

	public static String encriptData(String data) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		digest.update(data.getBytes());
		byte byteData[] = digest.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		data = sb.toString();
		return data;
	}

}
