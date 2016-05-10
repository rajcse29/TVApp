package com.iptv.panel;

import com.iptv.main.MainPanel;

public class SingleChannel extends Thread {

	public long channelId;
	public String channelName;
	public String link;
	public String logo;

	public SingleChannel(long channelId, String channelName, String link, String logo) {
		this.channelId = channelId;
		this.channelName = channelName;
		this.link = link;
		this.logo = logo;
	}

	@Override
	public void run() {
		try {
			SingleContentPanel singleContentPanel = new SingleContentPanel(channelId, channelName, link, logo);
			MainPanel.getMainPanel().scrollRightPanel.add(singleContentPanel);
			MainPanel.getMainPanel().scrollRightPanel.revalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
