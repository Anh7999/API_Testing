package com.api.auto.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileUtils {
	private static String CONFIG_PATH_PROPERTIES = "./configuration/configs.properties";
	private static String CONFIG_PATH_TOKEN = "./configuration/token.properties";

	//  Lấy ra giá trị property bất kỳ theo key.
	public static String getProperty(String key) {
		Properties properties = new Properties();
		String value = null;
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(CONFIG_PATH_PROPERTIES);
			properties.load(fileInputStream);
			value = properties.getProperty(key);
			return value;

		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println("Xảy ra lỗi khi đọc giá trị của  " + key);
			ex.printStackTrace();

		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return value;

	}
	
//  Lấy ra giá trị token bất kỳ theo key.
	public static String getToken() {
		Properties properties = new Properties();
		String value = null;
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(CONFIG_PATH_TOKEN);
			properties.load(fileInputStream);
			value = properties.getProperty("token");
			return value;

		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println("Xảy ra lỗi khi đọc giá trị của token ");
			ex.printStackTrace();

		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return value;

	}

	// ghi token vào file
	public static void setToken(String key, String value) {
		Properties properties = new Properties();
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(CONFIG_PATH_TOKEN);
			properties.setProperty(key, value);
			properties.store(fileOutputStream, "Set new value in properties");
			System.out.println("Set new value in file properties success.");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
