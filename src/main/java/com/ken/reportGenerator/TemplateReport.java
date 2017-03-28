package com.ken.reportGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TemplateReport {
	String tempName;
	String context;
	
	public TemplateReport(String tempName) {
		this.tempName = tempName;
	}
	public TemplateReport(String tempName, String filePath) {
		this.tempName = tempName;
		setContext(filePath);
	}
	
	public String getContext() {
		return this.context;
	}
	
	private void setContext(String filePath) {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			this.context = br.readLine();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) {
					br.close();
				}
				if(fr != null) {
					fr.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
