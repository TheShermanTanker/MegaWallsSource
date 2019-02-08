package me.theshermantanker.megawalls;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.theshermantanker.withercraft.WitherCraft;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class TeamHandler implements Listener{
	
	MegaWallsPlugin plugin = MegaWallsPlugin.plugin;
	WitherCraft withercraft = plugin.withercraft;
	LogicHelper helper = plugin.helper;
	
	@EventHandler
	public void onClick(PlayerInteractEvent event){
		Player player = event.getPlayer();
		World world = player.getWorld();
		double players = world.getPlayers().size();
		double members = players / 4;
		Team greenteam = null;
		Team yellowteam = null;
		Team blueteam = null;
		Team redteam = null;
		Map<Scoreboard, String> scoreboards = helper.scoreboards.get(world);
		for(Scoreboard scoreboard : scoreboards.keySet()){
			if(scoreboards.get(scoreboard).equals("Green")){
				greenteam = scoreboard.getTeam("GreenTeam");
			} else if(scoreboards.get(scoreboard).equals("Yellow")){
				yellowteam = scoreboard.getTeam("YellowTeam");
			} else if(scoreboards.get(scoreboard).equals("Blue")){
				blueteam = scoreboard.getTeam("BlueTeam");
			} else if(scoreboards.get(scoreboard).equals("Red")){
				redteam = scoreboard.getTeam("RedTeam");
			}
		}
		
		if((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && withercraft.knownWorlds.get(world).equals("Gameworld")){
			
			ItemStack item = player.getItemInHand();
			ItemMeta meta = item.getItemMeta();
			
			if(meta.getDisplayName().contains("Green")){
		
				if(!(greenteam.hasPlayer(player))){
					
					if(blueteam.hasPlayer(player)){
						blueteam.removePlayer(player);
					} else if(redteam.hasPlayer(player)){
						redteam.removePlayer(player);
					} else if(yellowteam.hasPlayer(player)){
						yellowteam.removePlayer(player);
					}
					
					if(greenteam.getSize() < members || players < 10){
						
						player.sendMessage(ChatColor.GREEN + "You are now on the Green team");
						greenteam.addPlayer(player);
						
					} else {
						
						player.sendMessage(ChatColor.RED + "This team is already full!");
						
					}
					
					
					
				} else {
					
					player.sendMessage(ChatColor.RED + "You are already on this team!");
					
				}
				
			} else if(meta.getDisplayName().contains("Yellow")){

				if(!(yellowteam.hasPlayer(player))){
					
					if(blueteam.hasPlayer(player)){
						blueteam.removePlayer(player);
					} else if(redteam.hasPlayer(player)){
						redteam.removePlayer(player);
					} else if(greenteam.hasPlayer(player)){
						greenteam.removePlayer(player);
					}
					
                    if(yellowteam.getSize() < members || players < 10){
						
						player.sendMessage(ChatColor.YELLOW + "You are now on the Yellow team");
						yellowteam.addPlayer(player);
						
					} else {
						
						player.sendMessage(ChatColor.RED + "This team is already full!");
						
					}
					
				} else {
					
					player.sendMessage(ChatColor.RED + "You are already on this team!");
					
				}
			
		} else if(meta.getDisplayName().contains("Blue")){

			if(!(blueteam.hasPlayer(player))){
				
				if(greenteam.hasPlayer(player)){
					greenteam.removePlayer(player);
				} else if(redteam.hasPlayer(player)){
					redteam.removePlayer(player);
				} else if(yellowteam.hasPlayer(player)){
					yellowteam.removePlayer(player);
				}
				
				if(blueteam.getSize() < members || players < 10){
					
					player.sendMessage(ChatColor.BLUE + "You are now on the Blue team");
					blueteam.addPlayer(player);
					
				} else {
					
					player.sendMessage(ChatColor.RED + "This team is already full!");
					
				}
				
			} else {
				
				player.sendMessage(ChatColor.RED + "You are already on this team!");
				
			}
		
		} else if(meta.getDisplayName().contains("Red")){

			if(!(redteam.hasPlayer(player))){
				
				if(blueteam.hasPlayer(player)){
					blueteam.removePlayer(player);
				} else if(greenteam.hasPlayer(player)){
					greenteam.removePlayer(player);
				} else if(yellowteam.hasPlayer(player)){
					yellowteam.removePlayer(player);
				}
				
				if(redteam.getSize() < members || players < 10){
					
					player.sendMessage(ChatColor.RED + "You are now on the Red team");
					redteam.addPlayer(player);
					
				} else {
					
					player.sendMessage(ChatColor.RED + "This team is already full!");
					
				}
				
			} else {
				
				player.sendMessage(ChatColor.RED + "You are already on this team!");
				
			} 	
		
	     }

     }

	
  }
		
}	
