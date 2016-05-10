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

public class TestClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final ControllUnit cu = new ControllUnit();
		
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		//qcu.receiveKey('q');
		System.out.println("main");
		
		
		
		////////
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
		panel.setBackground(Color.red);
		JButton btn = new JButton("Click me");
        btn.setFocusable(false);
        btn.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		JButton btn1 = new JButton("Click me too");
                btn1.setFocusable(false);
                panel.add(btn1, BorderLayout.WEST);
                panel.revalidate();
        	}
		});
		panel.add(btn, BorderLayout.EAST);
		frame.setContentPane(panel);
		frame.setLocationRelativeTo(null);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				System.out.println("Key Pressed: " + c);
				
				if (c == 's') {
					cu.receiveKey('s');
				}
				else if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE){
					cu.receiveKey('q');
				}
				else if(c=='f'){
					cu.receiveKey('f');
				}
				else if(e.getKeyCode() == KeyEvent.VK_UP){
					cu.receiveKey('8');
				}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
					cu.receiveKey('2');
				}

			}
		});

		frame.setFocusable(true);
		frame.setVisible(true);
		///////
	}

}
