package com.kreezcraft.localizedchat.Channels;

public interface IChannel {

	public String getChannel(); //return the current channel
	
	public boolean setChannel(String channel); //set the current channel, return true for success, false otherwise
}
