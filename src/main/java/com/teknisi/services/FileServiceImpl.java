package com.teknisi.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.teknisi.model.Request;
import com.teknisi.model.Teknisi;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

@Service
public class FileServiceImpl implements FileService{
	
	@Autowired RequestService requestService;
	
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
    public void exportToCSV() throws IOException {
        List<Request> listRequest = requestService.showAllPendingRequest();
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        FileWriter filePath = new FileWriter("./csv/"+ "REQUEST_"+ currentDateTime + ".csv", true);
        ICsvBeanWriter csvWriter = new CsvBeanWriter(filePath, customCsvPreference());
        String[] csvHeader = {"Teknisi ID", "Request ID", "Merchant Name", "Address", "City", "PIC", "Created_date", "Status"};
        String[] nameMapping = {"teknisi_id", "request_id", "merchant_name", "address", "city","person_in_charge","created_date","status"};
        csvWriter.writeHeader(csvHeader);
        for (Request request : listRequest) {
            csvWriter.write(request, nameMapping);
        }
        csvWriter.close();
    }

	@Override
	public void exportToPDF() throws FileNotFoundException, JRException {
		ArrayList<Request> arrayListRequest =(ArrayList<Request>) requestService.showAllStatusRequest("FINISHED");
		Object[] arrayObjectRequest = arrayListRequest.toArray();
		JRBeanArrayDataSource beanCollectionDataSource = new JRBeanArrayDataSource(arrayObjectRequest);
		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("./jasper/report.jrxml"));
		HashMap<String, Object> map = new HashMap<>();
		JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
		JasperExportManager.exportReportToPdfFile(report, "./pdf/"+"FINISHED_REQUEST_"+ currentDateTime + ".pdf");
	}
	
	@SuppressWarnings("resource")
	@Override
	public void exportToXLS() throws IOException {
		Workbook workbook = new XSSFWorkbook();

		Sheet sheet = workbook.createSheet("Persons");
		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 4000);
		
		CellStyle headerStyle = workbook.createCellStyle();

		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		headerStyle.setFont(font);
		
		String[] xlsHeader = {"No", "Tanggal Process", "Request ID", "Merchant Name", "Address", "City", "PIC", "Teknisi ID","Teknisi Name", "Status"};
	    
		Row headlineHeader = sheet.createRow(0);
		Cell headlineCell = headlineHeader.createCell(1);
		headlineCell.setCellValue("Rekapitulasi Data Request");
		headlineCell.setCellStyle(headerStyle);

		Row dateHeader = sheet.createRow(1);
		Cell dateCell = dateHeader.createCell(1);
		DateFormat dateXLSFormatter = new SimpleDateFormat("dd/MM/yyyy_hh:mm:ss");
        String currentXLSDateTime = dateXLSFormatter.format(new Date());
        dateCell.setCellValue("Report Date: " + currentXLSDateTime);
        
        Row dataHeader = sheet.createRow(3);
        for(int i = 0; i < xlsHeader.length; i++) {
        	Cell nameCell = dataHeader.createCell(i);
        	nameCell.setCellValue(xlsHeader[i]);
        	nameCell.setCellStyle(headerStyle);
        }
        
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        List<Request> listRequest = requestService.showAllRecapitulationRequest();
        int index = 0;
        for (Request request : listRequest) {
        	Teknisi teknisi = new Teknisi();
        	Row row = sheet.createRow(4+index);
        	Cell columnOne = row.createCell(0);
        	columnOne.setCellValue(index+1);
        	Cell columnTwo = row.createCell(1);
        	if(request.getUpdate_date() == null) {
        		columnTwo.setCellValue(request.getCreated_date());
        	}else {
        		columnTwo.setCellValue(request.getUpdate_date());
        	}
        	Cell columnThree = row.createCell(2);
        	columnThree.setCellValue(request.getRequest_id());
        	Cell columnFour = row.createCell(3);
        	columnFour.setCellValue(request.getMerchant_name());
        	Cell columnFive = row.createCell(4);
        	columnFive.setCellValue(request.getAddress());
        	Cell columnSix = row.createCell(5);
        	columnSix.setCellValue(request.getCity());
        	Cell columnSeven = row.createCell(6);
        	columnSeven.setCellValue(request.getPerson_in_charge());
        	Cell columnEight = row.createCell(7);
        	columnEight.setCellValue(teknisi.getId());
        	Cell columnNine = row.createCell(8);
        	columnNine.setCellValue(teknisi.getName());
        	Cell columnTen = row.createCell(9);
        	columnTen.setCellValue(request.getStatus());
        	index++;
        }
        DateFormat dateFileFormatter = new SimpleDateFormat("dd-MM-yyyy_hh-MM-ss");
        String currentFileDateTime = dateFileFormatter.format(new Date());
        File file = new File("./xls/"+ "REKAP_REQUEST_"+ currentFileDateTime + ".xls");
        String filePath = file.getAbsolutePath();
        FileOutputStream outputStream = new FileOutputStream(filePath);
        workbook.write(outputStream);
        workbook.close();
    }
}
