package com.ken.reportGenerator;

import javax.swing.JOptionPane;

public class PopupMessage {
	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
}
