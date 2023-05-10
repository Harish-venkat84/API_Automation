package com.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileReaderManager {

	public static Properties properties;

	public FileReaderManager() throws IOException {

		String path = System.getProperty("user.dir") + File.separator + "./PropertiesFile/Application.Properties";

		File file = new File(path);

		FileInputStream fileinput = new FileInputStream(file);

		properties = new Properties();

		properties.load(fileinput);
	}

	public String getHosturl() {

		String url = properties.getProperty("hosturl");

		return url;

	}

	public String getauUsername() {

		String auUsername = properties.getProperty("auUsername");

		return auUsername;
	}

	public String getauPassword() {

		String auPassword = properties.getProperty("auPassword");

		return auPassword;
	}

	public String getpropertiesFilePath() {

		String propertiesFilePath = properties.getProperty("PropertiesFilePath");

		return propertiesFilePath;
	}

	public String getProperty(String string) {

		String property = properties.getProperty(string);

		return property;
	}

	public int getPropertyAsInt(String string) {

		int property = Integer.parseInt(properties.getProperty(string));

		return property;
	}

}
