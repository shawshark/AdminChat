package com.bobotropolis.lilyshout;

import java.io.UnsupportedEncodingException;

import lilypad.client.connect.api.Connect;
import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.MessageRequest;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.bobotropolis.lilyshout.cmds.ShoutCommand;
import com.bobotropolis.lilyshout.listeners.ShoutListener;

public class ShoutMain extends JavaPlugin{
	
	private Connect connect;
	private ShoutListener listener;
	
	public void onEnable(){
		this.saveDefaultConfig();
		
		/* Get the connect */
		connect = getConnect();
		
		/* Prepare our stuff */
		this.getCommand("admin").setExecutor(new ShoutCommand(this));
		listener = new ShoutListener(this);
	}
	
	public void shout(String string){
		if(string == null) return;
		
		/* Do it for our server of course */
		this.getServer().broadcastMessage(string);
		
		/* Do this for every single server */
		for(String serverName : this.getConfig().getStringList("servers")){
			MessageRequest request = null;
			
			/* Prepare the request! */
			try {
				request = new MessageRequest(serverName, "AdminChat", string);
			} catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}
			
			/* Send it! */
			try {
				connect.request(request);
			} catch (RequestException e){
				e.printStackTrace();
			}
		}
		
	}
	
	public Connect getConnect(){
		Plugin pconnect = this.getServer().getPluginManager().getPlugin("LilyPad-Connect");
		
		if(pconnect == null){
			/* The plugin isn't loaded, shut down this plugin */
			this.setEnabled(false);
			this.getLogger().info("AdminChat shut down, LilyPad-Connect not found.");
			return null;
		}
		
		return pconnect.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
	}
	
}
