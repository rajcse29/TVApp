package com.iptv.Listener;

public class Command implements Comparable<Command>{

	private int priority;
	private String command;
	
	public Command(String command,int priority){
		this.priority=priority;
		this.command=command;
	}
	
	public int getPriority() {
		return priority;
	}

	public String getCommand() {
		return command;
	}

	@Override
	public int compareTo(Command o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
