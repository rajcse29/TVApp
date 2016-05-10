package com.iptv.Listener;

public class ControllUnit {
	private boolean omxPlayerInstance;
	ExecutionThread exeThread;
	
	String urlCu;
	
	public String getUrlCu() {
		return urlCu;
	}

	public void setUrlCu(String urlCu) {
		this.urlCu = urlCu;
	}

	public boolean isOmxPlayerInstance() {
		return omxPlayerInstance;
	}

	public void setOmxPlayerInstance(boolean omxPlayerInstance) {
		this.omxPlayerInstance = omxPlayerInstance;
	}
	
	public ControllUnit(){
		setOmxPlayerInstance(false);
		this.exeThread = new ExecutionThread();
	}
	/**
	 * if playerInstance==false && key_listener==p
	 * then start omx thread with url and boolean true 
	 * */
	public void receiveKey(char key){
		
		
		if(!isOmxPlayerInstance()){
			/**
			 * player is not running
			 * means now in GUI interface
			 * */
			PlayerThread omxThread = new PlayerThread();
			
			if(key=='s'){//start player
				System.out.println("S key received");
				omxThread.setUrl(getUrlCu());
				System.out.println("URLLLL: "+getUrlCu());
				omxThread.startThread();
				setOmxPlayerInstance(true);
			}else if(key== 'q'){
				System.out.println("Q received");
				omxThread.playerCommand("");
			}
		}else{
			/**
			 * now in player mode
			 * */
			
			if(key=='f'){
				
			}else if(key== 'q'){
				exeThread.receivedCommand("ps -ef | grep /usr/bin/omxplayer | grep -v grep | awk '{print $2}' | xargs kill -9");
				setOmxPlayerInstance(false);
			}else if(key== '8'){
				exeThread.receivedCommand("/home/pi/volUp");
			}
			else if(key== '2'){
				exeThread.receivedCommand("/home/pi/volDown");
			}
		}
	}
}
