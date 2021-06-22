package com.teknisi.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.mail.MessagingException;

import org.supercsv.prefs.CsvPreference;

import net.sf.jasperreports.engine.JRException;

public interface FileService {
	
	File getLastModified(String path);
	CsvPreference customCsvPreference();
	File exportToCSV() throws IOException;
	File exportToPDF() throws FileNotFoundException, IOException, JRException, MessagingException;
	File exportToXLS() throws IOException, JRException;
	File exportToBarChart() throws FileNotFoundException, JRException, IOException;
}
