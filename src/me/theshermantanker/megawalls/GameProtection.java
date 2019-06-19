package me.theshermantanker.megawalls;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import me.theshermantanker.withercraft.WitherCraft;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class GameProtection implements Listener {
	
	MegaWallsPlugin plugin = MegaWallsPlugin.plugin;
	WitherCraft withercraft = plugin.withercraft;
	FileConfiguration data = plugin.dataconfiguaration;
	GameStart gamestart = new GameStart();
	
	@EventHandler
	public void onDestroy(BlockBreakEvent event){
		Location block = event.getBlock().getLocation();	
		Player player = event.getPlayer();
		World world = player.getWorld();
	    Object value = withercraft.knownWorlds.get(world);
		
        
		
	}
	
	@EventHandler
	public void onConstruct(BlockPlaceEvent event){
		Location block = event.getBlock().getLocation();		
		
        
		
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event){		
		Player player = event.getPlayer();
		World world = player.getWorld();
	    Object value = withercraft.knownWorlds.get(world);
		String worldName = plugin.getWorldName(player);
		int buildLimit = data.getInt("Gameworlds." + worldName + ".HeightLimit");
	    
		if(event.getBlock().getY() > buildLimit && value.equals("Gameworld")){
			
			event.setCancelled(true);
			
		}
		
	} 
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event){
		
		Player player = event.getPlayer();
		World world = player.getWorld();
	    Object value = withercraft.knownWorlds.get(world);
		String worldName = plugin.getWorldName(player);
		int BuildLimit = data.getInt("Gameworlds." + worldName + ".HeightLimit");
	    
		if(event.getBlock().getY() > BuildLimit && value.equals("Gameworld")){
			
			event.setCancelled(true);
			
		}
		
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event){
		Player player = event.getPlayer();
		Object value = withercraft.knownWorlds.get(player.getWorld());
		int BuildLimit = data.getInt("Gameworlds." + plugin.getWorldName(player) + ".HeightLimit");
		
		if(value.equals("Gameworld") && player.getLocation().getBlockY() > BuildLimit){
			
			event.setCancelled(true);
			
		}
		
	}

}
