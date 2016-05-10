package com.iptv.Listener;



public class ExecutionThread  {

	ExecuteCommandClass commandClass;
	public ExecutionThread(){
		this.commandClass = new ExecuteCommandClass();
	}

	public void receivedCommand(String command){
		commandClass.executeCommand(command);
	}
	
}
