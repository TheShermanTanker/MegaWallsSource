package me.theshermantanker.megawalls;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.theshermantanker.withercraft.WitherCraft;

public class GameChat implements Listener{
	
	MegaWallsPlugin plugin = MegaWallsPlugin.plugin;
	WitherCraft withercraft = plugin.withercraft;
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		
		FileConfiguration playerdata = withercraft.customConfig;
		Player player = event.getPlayer();
		Object value = withercraft.knownWorlds.get(player.getWorld());
		boolean gameStarted = plugin.worlds.get(player.getWorld());
		String uuid = player.getUniqueId().toString();
		String displayname = null;
		String rank = playerdata.getString(uuid + ".Rank");
				
		if(value.equals("Gameworld") && !gameStarted){
			event.setCancelled(true);
			
            if(rank.equals("Owner")){
				
				displayname = ChatColor.DARK_RED + "[ADMIN] " + player.getName();
				
			} else if(rank.equals("Admin")){
				
				displayname = ChatColor.RED + "[ADMIN] " + player.getName();
				
			} else if(rank.equals("Moderator")){
				
				displayname = ChatColor.DARK_GREEN + "[MOD] " + player.getName();
				
			} else if(rank.equals("Helper")){
				
				displayname = ChatColor.DARK_BLUE + "[HELPER] " + player.getName();
				
			} else if(rank.equals("MVP")){
				
				displayname = ChatColor.AQUA + "[MVP] " + player.getName();
				
			} else if(rank.equals("VIP")){
				
				displayname = ChatColor.GREEN + "[VIP] " + player.getName();
				
			} else if(rank.equals("Default")){
				
				displayname = ChatColor.GRAY + player.getName();
				
			}
            
            for(Player players : player.getWorld().getPlayers()){
            	String message = null;            	
            	if(!(rank.equals("Default"))){
            		
            		message = ChatColor.WHITE + event.getMessage();
            		
            	} else {
            		
            		message = ChatColor.GRAY + event.getMessage();
            		
            	}
            	
            	players.sendMessage(displayname + ": " + message);
            	
            }
			
		}
		
	}

}
