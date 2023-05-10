package com.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class PropertiesWriter {

	static Properties properties;
	static String path = System.getProperty("user.dir") + "./PropertiesFile/Application.Properties";
	static FileBasedConfigurationBuilder<FileBasedConfiguration> builder;
	static Configuration config;

	public PropertiesWriter() throws IOException, ConfigurationException {

		FileInputStream in = new FileInputStream(path);

		properties = new Properties();

		properties.load(in);

		in.close();

		Parameters params = new Parameters();
		builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
				.configure(params.properties().setFileName(path));
		config = builder.getConfiguration();

	}

	public static PropertiesWriter getPropertiesWriter() throws Exception {

		PropertiesWriter property = new PropertiesWriter();

		return property;
	}

	public void setProperties(String string, String string2) throws Exception {

		config.setProperty(string, string2);

		builder.save();
	}


}
