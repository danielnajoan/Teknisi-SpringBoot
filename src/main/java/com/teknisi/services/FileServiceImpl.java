package com.teknisi.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.teknisi.model.BarChart;
import com.teknisi.model.Request;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Service
public class FileServiceImpl implements FileService{
	
	@Autowired RequestService requestService;
	@Autowired BarChartService barChartService;
	@Autowired MessageService messageService;
	
	@Override
	public File getLastModified(String path) {
		 File directory = new File(path);
		    File[] files = directory.listFiles(File::isFile);
		    long lastModifiedTime = Long.MIN_VALUE;
		    File chosenFile = null;

		    if (files != null)
		    {
		        for (File file : files)
		        {
		            if (file.lastModified() > lastModifiedTime)
		            {
		                chosenFile = file;
		                lastModifiedTime = file.lastModified();
		            }
		        }
		    }

		    return chosenFile;
	}
	
	@Override
    public CsvPreference customCsvPreference(){
        return new CsvPreference.Builder(',', '|', "\n").build();
    }
    
    @Override
    public byte[] exportToCSV() throws IOException {
        List<Request> listRequest = requestService.showAllPendingRequest();
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        File outputFile = File.createTempFile("./csv/"+ "REQUEST_"+ currentDateTime+"#", ".csv");
        FileWriter filePath = new FileWriter(outputFile, true);
        ICsvBeanWriter csvWriter = new CsvBeanWriter(filePath, customCsvPreference());
        String[] csvHeader = {"Teknisi ID", "Request ID", "Merchant Name", "Address", "City", "PIC", "Created_date", "Status"};
        String[] nameMapping = {"teknisi_id", "request_id", "merchant_name", "address", "city","person_in_charge","created_date","status"};
        csvWriter.writeHeader(csvHeader);
        for (Request request : listRequest) {
            csvWriter.write(request, nameMapping);
        }
        csvWriter.close();
        return Files.readAllBytes(Paths.get(outputFile.getPath()));
    }

	@Override
	public byte[] exportToPDF() throws FileNotFoundException, JRException, IOException {
		ArrayList<Request> arrayListRequest =(ArrayList<Request>) requestService.showAllStatusRequest("FINISHED");
		Object[] arrayObjectRequest = arrayListRequest.toArray();
		JRBeanArrayDataSource beanCollectionDataSource = new JRBeanArrayDataSource(arrayObjectRequest);
		JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream("./jasper/report.jrxml"));
		HashMap<String, Object> map = new HashMap<>();
		JasperPrint report = JasperFillManager.fillReport(jasperReport, map, beanCollectionDataSource);
        return JasperExportManager.exportReportToPdf(report);
	}
	
	@Override
	public byte[] exportToPDF(Date startDate, Date endDate) throws FileNotFoundException, JRException, IOException {
		ArrayList<Request> arrayListRequest =(ArrayList<Request>) requestService.showAllRecapitulationRequest(startDate, endDate);
		Object[] arrayObjectRequest = arrayListRequest.toArray();
		JRBeanArrayDataSource beanCollectionDataSource = new JRBeanArrayDataSource(arrayObjectRequest);
		JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream("./jasper/all-request-data.jrxml"));
		HashMap<String, Object> map = new HashMap<>();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");  
        String stringStartDate = dateFormat.format(startDate); 
        String stringEndDate = dateFormat.format(endDate); 
		map.put("reportStartDate", stringStartDate);
		map.put("reportEndDate", stringEndDate);
		JasperPrint report = JasperFillManager.fillReport(jasperReport, map, beanCollectionDataSource);
        return JasperExportManager.exportReportToPdf(report);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public byte[] exportToXLS() throws IOException, JRException {
		byte[] report = null;
        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream("./jasper/recapitulation.jrxml"));
        ArrayList<Request> arrayListRequest =(ArrayList<Request>) requestService.showAllRecapitulationRequest();
        Object[] arrayObjectRequest = arrayListRequest.toArray();
		JRBeanArrayDataSource beanCollectionDataSource = new JRBeanArrayDataSource(arrayObjectRequest);
        Map<String, Object> params = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanCollectionDataSource);
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setOnePagePerSheet(false);
        configuration.setIgnoreGraphics(false);
        configuration.setDetectCellType(true);
        try 
           {
        	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        	
            Exporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            exporter.setConfiguration(configuration);
            exporter.exportReport();
            report =  byteArrayOutputStream.toByteArray();
        }
        catch(Exception ex) {
        	ex.printStackTrace();
        }
		return report;
    }

//	@Override
//	public File exportToBarChart() throws FileNotFoundException, JRException, IOException {
//		ArrayList<BarChart> arrayListRequest =(ArrayList<BarChart>) barChartService.showAllRequestCount();
//		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(arrayListRequest);
//		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("./jasper/recapitulation-barchart.jrxml"));
//		HashMap<String, Object> map = new HashMap<>();
//		JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanColDataSource);
//        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
//        String currentDateTime = dateFormatter.format(new Date());
//        Calendar calender = Calendar.getInstance();
//        calender.add(Calendar.DATE, -7);
//        Date lastWeekDate = calender.getTime();    
//        String lastWeekFriday = dateFormatter.format(lastWeekDate);
//        File outputFile = File.createTempFile("./pdf/barchart/"+"REQUEST_"+lastWeekFriday+" - " +currentDateTime+"#", ".pdf");
//		JasperExportManager.exportReportToPdfStream(report, new FileOutputStream(outputFile));
//		return outputFile;
//	}
	@Override
	public byte[] exportToBarChart() throws FileNotFoundException, JRException, IOException {
		ArrayList<BarChart> arrayListRequest =(ArrayList<BarChart>) barChartService.showAllRequestCount();
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(arrayListRequest);
		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("./jasper/recapitulation-barchart.jrxml"));
		HashMap<String, Object> map = new HashMap<>();
		JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanColDataSource);
		return JasperExportManager.exportReportToPdf(report);
	}
}
