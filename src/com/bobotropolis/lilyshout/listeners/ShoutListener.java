package com.bobotropolis.lilyshout.listeners;

import java.io.UnsupportedEncodingException;

import org.bukkit.entity.Player;

import lilypad.client.connect.api.Connect;
import lilypad.client.connect.api.MessageEvent;
import lilypad.client.connect.api.MessageEventListener;

import com.bobotropolis.lilyshout.ShoutMain;

public class ShoutListener {
	
	final ShoutMain plugin;
	
	public ShoutListener(final ShoutMain plugin){
		this.plugin = plugin;
		
		Connect connect = plugin.getConnect();
		connect.registerMessageEventListener(new MessageEventListener() {
			
			@Override
			public void onMessage(Connect connect, MessageEvent event){
				/* Is this our message */
				if(!event.getChannel().equalsIgnoreCase("AdminChat")){
					return;
				}
				
				/* Get the message */
				String message = null;
				
				try {
					message = event.getMessageAsString();
				} catch (UnsupportedEncodingException e) {
					/* Something went wrong, don't broadcast */
					return;
				}
				
				/* Broadcast it! */
				for(Player player : plugin.getServer().getOnlinePlayers()){
					if(player.hasPermission("adminchat.use")) player.sendMessage(message);
				}
			}
			
		} );
	}
	
}
