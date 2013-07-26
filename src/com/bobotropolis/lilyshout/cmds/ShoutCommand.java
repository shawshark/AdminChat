package com.bobotropolis.lilyshout.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bobotropolis.lilyshout.ShoutMain;

public class ShoutCommand implements CommandExecutor{

	ShoutMain plugin;
	
	public ShoutCommand(ShoutMain plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(!label.equalsIgnoreCase("admin")){
			/* How did this get here? */
			return false;
		} else if (!sender.hasPermission("adminchat.use")){
			/* Doesn't have permission, don't shout. */
			sender.sendMessage(ChatColor.RED+"You do not have permission to use "+ChatColor.YELLOW+"/admin"+ChatColor.RED+".");
			return true;
		} else if (args.length == 0){
			/* Command was only '/shout', no message */
			sender.sendMessage(ChatColor.RED+"Correct usage: "+ChatColor.YELLOW+"/admin [MESSAGE]"+ChatColor.RED+".");
			return true;
		}
		
		String message = "";
		/* Build the message */
		for(int i = 0; i < args.length; i++){
			message = message + args[i] + " ";
		}
		
		String format = "";
		
		if(sender instanceof Player){
			Player player = (Player) sender;
			/* Get the player's format */
			format = plugin.getConfig().getString("chat-format");
			
			format = format.replaceAll("<displayname>", player.getDisplayName());
			format = format.replaceAll("<username>", player.getName());
			format = format.replaceAll("<server>", plugin.getConfig().getString("server-name"));
			
			format = ChatColor.translateAlternateColorCodes('&', format);
			
		} else {
			/* Console, API, or other, lets just say console */
			format = "&5[Console]&7: ";
			
			format = ChatColor.translateAlternateColorCodes('&', format);
		}
		
		/* Compile it all together */
		message = format + message;
		
		plugin.shout(message);
		return true;
	}
	
}
