package com.iptv.Listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ExecuteCommandClass implements ExecuteCommandInterface {

	@Override
	public void executeCommand(String command) {
		String s = "";
		StringBuilder str = new StringBuilder();
	    Process p;
	    
	    String[] cmd = {
				"/bin/sh",
				"-c",
				command
				};
	    
		try{
			 p = Runtime.getRuntime().exec(cmd);
	            BufferedReader br = new BufferedReader(
	                new InputStreamReader(p.getInputStream()));
	            while ((s = br.readLine()) != null){
	                str.append(s);
	            }
	            
	            p.waitFor();
	            p.destroy();		
	            
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			System.out.println("command: "+command);
			System.out.println("Response : "+str.toString());
		}
	}
	
}
