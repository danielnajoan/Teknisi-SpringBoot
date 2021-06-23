package com.teknisi.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.supercsv.prefs.CsvPreference;

import net.sf.jasperreports.engine.JRException;

public interface FileService {
	
	File getLastModified(String path);
	CsvPreference customCsvPreference();
	byte[] exportToCSV() throws IOException;
	byte[] exportToPDF() throws FileNotFoundException, IOException, JRException;
	byte[] exportToPDF(Date startDate, Date endDate) throws FileNotFoundException, IOException, JRException;
	byte[] exportToXLS() throws IOException, JRException;
	byte[] exportToBarChart() throws FileNotFoundException, JRException, IOException;
}
