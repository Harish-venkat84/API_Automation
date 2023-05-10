package com.properties;

import java.io.IOException;

public class ConfigurationReader {

	private ConfigurationReader() {
	}

	public FileReaderManager getFRM() throws IOException {

		FileReaderManager frm = new FileReaderManager();

		return frm;
	}

	public static ConfigurationReader getCR() {

		ConfigurationReader cf = new ConfigurationReader();

		return cf;
	}

}