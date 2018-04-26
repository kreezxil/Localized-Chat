package com.kreezcraft.localizedchat.Channels;

import java.util.Arrays;

import com.kreezcraft.localizedchat.Config;

public class Channel implements IChannel {
	
	private String station="global"; //the channel to which we are listening.

	@Override
	public String getChannel() {
		return station;
	}

	@Override
	public boolean setChannel(String channel) {
		if(Arrays.asList(Config.chanList.getStringList()).contains(channel)) {
			station = channel;
			return true;
		}
		return false;
	}

}
