package com.iptv.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.iptv.main.MainPanel;

public class CountryPanel extends JPanel {
	public Long countryId;
	public String countryName;

	public CountryPanel(final Long countryId, String countryName) {
		this.countryId = countryId;
		this.countryName = countryName;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(0, 30));
		setBackground(Color.white);
		initComponents();
	}

	private void initComponents() {
		JLabel nameLabel = new JLabel(countryName);
	    nameLabel.setOpaque(false);
		JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		container.setOpaque(false);
		container.add(nameLabel);
		this.add(container, BorderLayout.CENTER);
	}

}
