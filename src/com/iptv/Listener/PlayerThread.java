package com.iptv.Listener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlayerThread extends Thread {
	private Thread t;
	private String url;
	ExecuteCommandClass commandClass;
	
	public PlayerThread() {
		this.commandClass = new ExecuteCommandClass();
		//System.out.println("url to play " + url);	
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	public void startThread(){
		start();	
	}
	
	public void run() {
		try{
			//buildUI();
			commandClass.executeCommand("/home/pi/play " +url); // --win \"0 0 720 576\" xterm -e 
			
		}catch(Exception e ){
			e.printStackTrace();
		}
	}

	public void playerCommand(String command){
		commandClass.executeCommand(command);
	}
	
	public void buildUI(){
		JFrame frame = new JFrame("Listener frame");
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setLocation(0, 0);
		Toolkit tk = Toolkit.getDefaultToolkit();
		int width = (int) tk.getScreenSize().getWidth();
		int height = (int) tk.getScreenSize().getHeight();
		frame.setSize(width, height);
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setFocusable(false);
		panel.setBackground(Color.blue);

		frame.setContentPane(panel);
		frame.setLocationRelativeTo(null);
	

		frame.setFocusable(true);
		frame.setVisible(true);
	}
	
}
