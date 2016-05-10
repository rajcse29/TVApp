package com.iptv.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SingleContentPanel extends JPanel {
	
	public long channelId;
	public String channelName;
	public String link;
	public String logo;
	public Map<Integer, String> linkMap;

	public SingleContentPanel(long channelId, String channelName, String link, String logo) {
		this.channelId = channelId;
		this.channelName = channelName;
		this.link = link;
		this.logo = logo;
		linkMap = new HashMap<Integer, String>();
		setLayout(new BorderLayout());
		setBackground(Color.white);
		initComponents();	
	}
	
	private ImageIcon getLogo(String logo) {
		ImageIcon icon = null;
		try {
			URL url = new URL("http://www.livestreamer.com/images/" + logo);
			System.out.println("before image request:" +System.currentTimeMillis());
            BufferedImage img = ImageIO.read(url);
            System.out.println("after image request:" +System.currentTimeMillis());
            
            BufferedImage scaledImage = new BufferedImage(120, 90, BufferedImage.TRANSLUCENT);
            Graphics2D g2d = (Graphics2D) scaledImage.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(img, 0, 0, 120, 90, null);
            g2d.dispose();
            icon = new ImageIcon(scaledImage);
  
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return icon;
		
	}

	private void initComponents() {
		JLabel logoLabel = new JLabel();
		logoLabel.setIcon(getLogo(logo));
		
		JLabel nameLabel = new JLabel(channelName);
		this.add(logoLabel, BorderLayout.NORTH);
		this.add(nameLabel, BorderLayout.SOUTH);
		
	}


}
