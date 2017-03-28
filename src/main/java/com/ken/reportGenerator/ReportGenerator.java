package com.ken.reportGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReportGenerator {
	final static String REPORT_PROP_NAME = "report.properties"; // settings for whole program
	final static String TEMPLATE_PROP_NAME = "temp.properties";	// list of template report map
	final static String RESULT_NAME = "results/result";			// result folder and file
	final static String NAME_LIST = "nameList.xlsx";			// name of excel sheet, contains student name, gender, grade
	
	final static String[] keys = {"%NAME%", "%TA_C%", "%TAS_C%", "%TA%", "%TAS%"};	// keyword in the template, can be replaced by name, his/he/she/her
	
	static Gender male;
	static Gender female;
	static Map<String, TemplateReport> tempMap;
	
	private static void init() {
		// initial gender instances
		male = new Gender("m");
		female = new Gender("f");
		
		// Load template properties
		tempMap = new HashMap<String, TemplateReport>();
		Properties tempProp = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(TEMPLATE_PROP_NAME);
			tempProp.load(is);
			Set<Entry<Object, Object>> entries = tempProp.entrySet();
			for(Entry<Object, Object> entry : entries) {
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				TemplateReport tr = new TemplateReport(key, value);
				// build up template map
				tempMap.put(key, tr);
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(is != null) {
					is.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		// initial all important instances
		init();
		String message = "Initial Message";
		
		// initial input file
		InputStream is = null;
		File inputNameList = new File(NAME_LIST);	// Excel list of student name, gender and grade
		int index = 0;								// Serial tracking number
		
		// initial result file
		BufferedWriter bw = null;
		FileWriter fw = null;
		File outputResultFile = null;
		try {
			// read excel name list from input file path
			is = new FileInputStream(inputNameList);
		    Workbook wb = WorkbookFactory.create(is);
		    Sheet sheet = wb.getSheetAt(0);
		    Row row;
		    int numOfRows = sheet.getPhysicalNumberOfRows();	// number of student
		    
		    // prepare output file
		    outputResultFile = new File(RESULT_NAME);
		    fw = new FileWriter(outputResultFile);
		    bw = new BufferedWriter(fw);
		    
		    // Put time at top
		    Date currentTime = new Date();
			bw.write("Report generate at : " + currentTime.toString());
			bw.newLine();
		    
		    for(int i = 0; i < numOfRows; i++) {
		    	index++;
		    	// read each row from excel sheet
			    row = sheet.getRow(i);
			    String stuName = row.getCell(0).getStringCellValue();
			    String stuGender = row.getCell(1).getStringCellValue();
			    String stuGrade = row.getCell(2).getStringCellValue();
			    
			    // get context from template report, base on the grade
			    // Example: if student get grade A, use template 'templates/A'
			    String tempContaxt;
			    tempContaxt = tempMap.get(stuGrade).getContext();
			    
			    // Choose gender instance base on student's gender
			    Gender tempGender = null;
			    if(stuGender.equals("m") || stuGender.equals("M")) {
			    	tempGender = male;
			    } else if(stuGender.equals("f") || stuGender.equals("F")) {
			    	tempGender = female;
			    }
			    
				// Replace template keywords with students information(Name, He/his/she/her)
				String[] values = {stuName, tempGender.ta_i, tempGender.tas_i, tempGender.ta, tempGender.tas};
				String replacedContext = StringUtils.replaceEach(tempContaxt, keys, values);
				
				// Write to result file
				bw.write(Integer.toString(index) + ".");
				bw.newLine();
				bw.write(replacedContext);
				bw.newLine();
				bw.write("=========================================");
				bw.newLine();
		    }
		    message = "Reports Generated successful!";
		} catch(Exception e) {
			e.printStackTrace();
			message = "[ERROR] Reports Generation Failed!";
		} finally {
			try {
				if(is != null) {
					is.close();
				}
				if(bw != null) {
					bw.close();
				}
				if(fw != null) {
					fw.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		PopupMessage.infoBox(message, "Result");
		
	}
}
