package com.iptv.main;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.iptv.DBActivity.DBActivityDAO;

public class Main {

	static Integer frameType = 1;
	static JFrame mainFrame;
	public static JFrame getMainFrame() {
		return mainFrame;
	}
	public static void main(String[] args) {
		mainFrame = new JFrame("Listener Frame");
		mainFrame.setUndecorated(true);
		mainFrame.setResizable(false);
		mainFrame.setLocation(0, 0);

		Toolkit tk = Toolkit.getDefaultToolkit();
		int width = (int) tk.getScreenSize().getWidth();
		int height = (int) tk.getScreenSize().getHeight();
		mainFrame.setSize(1300, 600);
		
		mainFrame.setContentPane(MainPanel.getMainPanel());
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);		
		MainPanel.getMainPanel().dropDown.requestFocus();
		MainPanel.getMainPanel().dropDown.setBackground(Color.orange);
		System.out.println("database started");
		 if (DBActivityDAO.conn == null) {
			 DBActivityDAO.getinstance().createDatabaseConnection();
				DBActivityDAO.getinstance().createCountryTable();
				DBActivityDAO.getinstance().createCategoryTable();
				DBActivityDAO.getinstance().createLanguageTable();
		 }
		System.out.println("database over");
	}

}
