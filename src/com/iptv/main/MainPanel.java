package com.iptv.main;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLDocument.Iterator;
import javax.xml.ws.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.iptv.Listener.ControllUnit;
import com.iptv.panel.CountryPanel;
import com.iptv.panel.SingleChannel;
import com.iptv.panel.SingleContentPanel;

import java.lang.reflect.Type;

public class MainPanel extends JPanel {
	private static MainPanel mainPanel;
	private JPanel container;
	public JComboBox dropDown;
	private JScrollPane scrollPane;
	public JPanel scrollLeftPanel;
	public JPanel scrollRightPanel;
	public int channelNumber = 1;
	public int countryNumber = 1;
	
	final ControllUnit cu = new ControllUnit();
	static Queue<String> queueLinks = new ArrayDeque<String>();

	public MainPanel() {
		this.setLayout(new BorderLayout());
		initContents();
	}
	
	public static MainPanel getMainPanel(){
		if(mainPanel == null){
			mainPanel = new MainPanel();
		}
		return mainPanel;
	}

	private void initContents() {
		JPanel leftContentPanel = new JPanel(new BorderLayout());
		leftContentPanel.setOpaque(false);
		leftContentPanel.setPreferredSize(new Dimension(200, 20));

		dropDown = new JComboBox();
		dropDown.addItem("Country");
		dropDown.addItem("Category");
		dropDown.addItem("Language");

		dropDown.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					int dropDownType = dropDown.getSelectedIndex();
					if (dropDownType == 0) {
						dropDown.setBackground(Color.white);
					    getAllCountry("http://www.livestreamer.com/api/allCountry");
					    scrollLeftPanel.requestFocusInWindow();
						CountryPanel countryPanel = (CountryPanel)scrollLeftPanel.getComponent(0);
	        			countryPanel.setBackground(Color.orange);
					    
					} else if (dropDownType == 1) {
						

					} else if (dropDownType == 2) {

					}
					
				} else if(e.getKeyCode() == KeyEvent.VK_SPACE){
					dropDown.setBackground(Color.white);
					scrollLeftPanel.requestFocusInWindow();
					CountryPanel countryPanel = (CountryPanel)scrollLeftPanel.getComponent(0);
        			countryPanel.setBackground(Color.orange);
				}
			}
		});
			
		leftContentPanel.add(dropDown, BorderLayout.NORTH);
	
		scrollLeftPanel = new JPanel();
        scrollLeftPanel.setLayout(new BoxLayout(scrollLeftPanel, BoxLayout.Y_AXIS));
        scrollLeftPanelListener();

        
        JPanel wrappper = new JPanel(new BorderLayout());
        wrappper.setBackground(Color.white);
        wrappper.add(scrollLeftPanel, BorderLayout.NORTH);

		scrollPane = new JScrollPane(wrappper);
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftContentPanel.add(scrollPane, BorderLayout.CENTER);


        scrollRightPanel = new JPanel(new GridLayout(0, 6, 30, 30));
        scrollRightPanel.setBackground(Color.white);
        scrollRightPanel.setBorder(new EmptyBorder(30, 10, 0, 0));
        scrollRightPanelListener();
        
        JPanel wrappperRight = new JPanel(new BorderLayout());
        wrappperRight.setBackground(Color.white);
        wrappperRight.add(scrollRightPanel, BorderLayout.NORTH);
		
		JScrollPane rightScrollPane = new JScrollPane(wrappperRight);
		rightScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
        container = new JPanel(new BorderLayout());
        container.setBackground(Color.white);
		container.add(leftContentPanel, BorderLayout.WEST);
		container.add(rightScrollPane, BorderLayout.CENTER);

		//getAllCountry("http://www.livestreamer.com/api/allCountry");
		
		this.add(container, BorderLayout.CENTER);
	}

	private void scrollLeftPanelListener() {
		 scrollLeftPanel.addKeyListener(new KeyAdapter() {
	        	@Override
	        	public void keyPressed(KeyEvent e) {
	        		if(e.getKeyCode() == KeyEvent.VK_UP){
	        			if(countryNumber > 1){
	        				CountryPanel countryPanel = (CountryPanel)scrollLeftPanel.getComponent(countryNumber - 2);
		        			countryPanel.setBackground(Color.orange);
		        			CountryPanel prevCountryPanel = (CountryPanel)scrollLeftPanel.getComponent(countryNumber - 1);
				        	prevCountryPanel.setBackground(Color.white);
			        		countryNumber--;
	        			}
	        			
	        		} else if(e.getKeyCode() == KeyEvent.VK_DOWN){
	        			if(countryNumber < scrollLeftPanel.getComponentCount()){
	        				CountryPanel countryPanel = (CountryPanel)scrollLeftPanel.getComponent(countryNumber);
		        			countryPanel.setBackground(Color.orange);
		        		    CountryPanel prevCountryPanel = (CountryPanel)scrollLeftPanel.getComponent(countryNumber - 1);
			        	    prevCountryPanel.setBackground(Color.white);
		        			countryNumber++;
	        			}
	        			
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						CountryPanel countryPanel = (CountryPanel) scrollLeftPanel.getComponent(countryNumber - 1);
						System.out.println("countryPanel.countryName:"+ countryPanel.countryName);
						countryPanel.setBackground(Color.white);
						channelNumber = 1;
						scrollRightPanel.requestFocusInWindow();
						String fullUrl = "http://www.livestreamer.com/api/liveChannel/"+ countryPanel.countryId;
						
						String responseData = MainPanel.getMainPanel().getJSONData(fullUrl);
						MainPanel.getMainPanel().scrollRightPanel.removeAll();
						MainPanel.getMainPanel().scrollRightPanel.repaint();
						JSONObject root = (JSONObject) new JSONParser().parse(responseData);
						Boolean success = (boolean) root.get("success");
						JSONArray jsonArray = null;
						if (success) {
							jsonArray = (JSONArray) root.get("datarows");
							for (int i = 0; i < jsonArray.size(); i++) {
								JSONObject obj = (JSONObject) jsonArray.get(i);
								long channelId = (long) obj.get("channelId");
								String channelName = (String) obj.get("channelName");
								String link = (String) obj.get("link");
								String logo = (String) obj.get("logo");
								System.out.println("channelName:" + channelName);
		
								new SingleChannel(channelId, channelName, link, logo).start();
							}
						}

						boolean check = true;
						while(check){
							//System.out.println("scrollRightPanel.getComponentCount():" +scrollRightPanel.getComponentCount());
							System.out.print("");
							if(scrollRightPanel.getComponentCount() == jsonArray.size()){
								SingleContentPanel single = (SingleContentPanel) scrollRightPanel.getComponent(0);
								single.setBackground(Color.orange);	
								check = false;		
							}
						}

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else if(e.getKeyCode() == KeyEvent.VK_SPACE){
					    CountryPanel countryPanel = (CountryPanel) scrollLeftPanel.getComponent(countryNumber - 1);
					    countryPanel.setBackground(Color.white);
					    SingleContentPanel single = (SingleContentPanel)MainPanel.getMainPanel().scrollRightPanel.getComponent(0);
					    single.setBackground(Color.orange);
	        			scrollRightPanel.requestFocusInWindow();
	        			channelNumber = 1;
	        		}
	        	}
			});
	}

	private void scrollRightPanelListener() {
		
        scrollRightPanel.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		
        		
        		if(cu.isOmxPlayerInstance()){
        			
        			if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE){
    					cu.receiveKey('q');
    				}
    				else if(e.getKeyChar()=='f'){
    					cu.receiveKey('f');
    				}
    				else if(e.getKeyCode() == KeyEvent.VK_UP){
    					cu.receiveKey('8');
    				}
    				else if(e.getKeyCode() == KeyEvent.VK_DOWN){
    					cu.receiveKey('2');
    				}
    				else if(e.getKeyChar()=='s'){
    					if(!queueLinks.isEmpty()){
    						String nowPlay = queueLinks.poll();
    						cu.setUrlCu(nowPlay);
    						cu.receiveKey('q');
    					}
    					cu.receiveKey('s');
    				}
            		
        			
        		}
        		
        		else{//all raj vai
        			
            		if(e.getKeyCode() == KeyEvent.VK_LEFT){
            			if(channelNumber > 1){
            			    SingleContentPanel single = (SingleContentPanel)MainPanel.getMainPanel().scrollRightPanel.getComponent(channelNumber - 2);
            				System.out.println("single.channelName" +single.channelName);
            				single.setBackground(Color.orange);
            				SingleContentPanel prevSingle = (SingleContentPanel)MainPanel.getMainPanel().scrollRightPanel.getComponent(channelNumber - 1);
            				prevSingle.setBackground(Color.white);
                            channelNumber--;
            			}
            		} 
        			
        			
        			else if(e.getKeyCode() == KeyEvent.VK_UP){
                        if(channelNumber > 6){
                        	SingleContentPanel single = (SingleContentPanel)MainPanel.getMainPanel().scrollRightPanel.getComponent(channelNumber - 7);
            				System.out.println("single.channelName" +single.channelName);
            				single.setBackground(Color.orange);
            				SingleContentPanel prevSingle = (SingleContentPanel)MainPanel.getMainPanel().scrollRightPanel.getComponent(channelNumber - 1);
            				prevSingle.setBackground(Color.white);
            			    channelNumber = channelNumber - 6;
                        }
            		}
        			
        			
        			else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                        if(channelNumber < MainPanel.getMainPanel().scrollRightPanel.getComponentCount()){
                        	SingleContentPanel single = (SingleContentPanel)MainPanel.getMainPanel().scrollRightPanel.getComponent(channelNumber);
            				System.out.println("single.channelName" +single.channelName);
            				single.setBackground(Color.orange);
            				SingleContentPanel prevSingle = (SingleContentPanel)MainPanel.getMainPanel().scrollRightPanel.getComponent(channelNumber - 1);
            				prevSingle.setBackground(Color.white);
            			    channelNumber++;
                        }
            			
            		}
        			
            		
        			else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                        if(scrollRightPanel.getComponentCount() > (channelNumber + 5)){
                        	SingleContentPanel single = (SingleContentPanel)MainPanel.getMainPanel().scrollRightPanel.getComponent(channelNumber + 5);
            				System.out.println("single.channelName" +single.channelName);
            				single.setBackground(Color.orange);
            				SingleContentPanel prevSingle = (SingleContentPanel)MainPanel.getMainPanel().scrollRightPanel.getComponent(channelNumber - 1);
            				prevSingle.setBackground(Color.white);
            			    channelNumber = channelNumber + 6;
                        }
            		}
            		
            		
            		
        			else if (e.getKeyCode() == KeyEvent.VK_ENTER){
            			SingleContentPanel single = (SingleContentPanel)MainPanel.getMainPanel().scrollRightPanel.getComponent(channelNumber - 1);
            				System.out.println("channelPlaying:" + single.channelName);
            				try {
            					String inputUrl = "http://www.livestreamer.com/api/channelLink/" + single.channelId;
            					String responseData = getJSONData(inputUrl);
            					JSONObject root = (JSONObject) new JSONParser().parse(responseData);
            					Boolean success = (boolean) root.get("success");
            					if (success) {
            						    JSONArray jsonArray = (JSONArray) root.get("datarows");
            							JSONObject obj = (JSONObject) jsonArray.get(0);
            							String liveStreamerLink720 = (String) obj.get("liveStreamerLink720");
            							single.linkMap.put(1, liveStreamerLink720);
            							String liveStreamerLink480 = (String) obj.get("liveStreamerLink480");
            							single.linkMap.put(2, liveStreamerLink480);
            							String liveStreamerLink360 = (String) obj.get("liveStreamerLink360");
            							single.linkMap.put(3, liveStreamerLink360);
            							String liveStreamerLink180 = (String) obj.get("liveStreamerLink180");
            							single.linkMap.put(4, liveStreamerLink180);

            						
            						if(!queueLinks.isEmpty())
            							queueLinks.clear();
            						
            						for(int i = 1; i <= single.linkMap.size(); i++){
            							System.out.println(""+single.linkMap.get(i));
            							queueLinks.add(single.linkMap.get(i));
            						}
            						
            						cu.setUrlCu(single.linkMap.get(4));
            						cu.receiveKey('s');
            						
            					}

            				} catch (Exception ex) {
            					ex.printStackTrace();
            				}
            			
            		}
            		
            		
            		
        			else if(e.getKeyCode() == KeyEvent.VK_SPACE){
            			System.out.println("channel number:" +channelNumber);
            			dropDown.setBackground(Color.orange);	
            			if(scrollRightPanel.getComponentCount() > 1){
            				SingleContentPanel single = (SingleContentPanel)scrollRightPanel.getComponent(channelNumber - 1);
            				single.setBackground(Color.white);
            			} else {
            				SingleContentPanel single = (SingleContentPanel)scrollRightPanel.getComponent(0);
            				single.setBackground(Color.white);
            			}
            			countryNumber = 1;
            			dropDown.requestFocus();
            		}
            		
        			
        		}
        		    
        		
        	}
		});
		
	}

	private void getAllCountry(String inputUrl) {
		try {
			scrollLeftPanel.removeAll();
			String responseData = getJSONData(inputUrl);
            JSONObject root = (JSONObject) new JSONParser().parse(responseData);
            Boolean success = (boolean)root.get("success");
            if(success){
            	JSONArray jsonArray = (JSONArray) root.get("datarows");
    			for (int i = 0; i < jsonArray.size(); i++) {
    				JSONObject obj = (JSONObject) jsonArray.get(i);
    				Long countryId = (Long) obj.get("countryId");
    				String countryName = (String) obj.get("countryName");
    				CountryPanel countryPanel = new CountryPanel(countryId, countryName);
                    scrollLeftPanel.add(countryPanel);
                  
    			}
            }
            scrollLeftPanel.revalidate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public String getJSONData(String inputUrl) {
		String responseData = "";
		try {
			URL url = new URL(inputUrl);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			request.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				responseData = responseData + line;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseData;
	}

}
