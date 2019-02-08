package me.theshermantanker.megawalls;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scoreboard.Team;

public class LobbyProtection implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent event){
		
		if(event.getPlayer().getWorld().getName().equals("Lobby")){
			
			event.setCancelled(true);
			
		}
		
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event){
		Player player = event.getPlayer();
		Team playerteam = player.getScoreboard().getPlayerTeam(player);
		
		if(playerteam.getName() == null || playerteam.getName().equals("Spectator") && event.getPlayer().getWorld().getName().equals("Kingdom")){
			
			event.setCancelled(true);
			
		}
		
	}

}
