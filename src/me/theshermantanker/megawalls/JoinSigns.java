package me.theshermantanker.megawalls;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JoinSigns implements Listener{
	
	Map<Sign, World> map = new HashMap<Sign, World>();
	
	public void registerSignInterface(Location location, World world) {
		if(map.size() < 0 || map.size() > 6) {
			System.out.println("Severe error occured in World System, attempting to re-register World System");
			
		}
		if(map.size() == 6) return;
		if(map.size() < 6) {
			Block block = location.getWorld().getBlockAt(location);
			if(block.getType() == Material.SIGN) {
				Sign sign = (Sign) block.getState();
				map.put(sign, world);
			}
		}
	}
	
	public World getLinkedGame(Sign sign) {
		return map.get(sign);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(PlayerInteractEvent event){
								
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			
			Block block = event.getClickedBlock();
			if(block.getType() == Material.WALL_SIGN){
				
				Sign sign = (Sign) block.getState();
				Player player = event.getPlayer();
                if(sign.getLine(2).equals(100/100)){
					
					player.sendMessage(ChatColor.RED + "This game is already full!");
					event.setCancelled(true);
					return;
					
				}
				if(sign.getLine(3).equals("Forsaken")){
																				
					player.sendMessage(ChatColor.GREEN + "Put you in a" + ChatColor.GOLD + " Forsaken " + ChatColor.GREEN + "queue!");

					ItemStack greenwool = new ItemStack(Material.WOOL, 1, (byte)13);
					ItemStack yellowwool = new ItemStack(Material.WOOL, 1, (byte)4);
					ItemStack bluewool = new ItemStack(Material.WOOL, 1, (byte)11);
					ItemStack redwool = new ItemStack(Material.WOOL, 1, (byte)14);
					ItemMeta greenmeta = greenwool.getItemMeta();
					ItemMeta yellowmeta = yellowwool.getItemMeta();
					ItemMeta bluemeta = bluewool.getItemMeta();
					ItemMeta redmeta = redwool.getItemMeta();
					greenmeta.setDisplayName(ChatColor.GREEN + "Green Team - " + ChatColor.BOLD + "CLICK TO JOIN!");
					yellowmeta.setDisplayName(ChatColor.YELLOW + "Yellow Team - " + ChatColor.BOLD + "CLICK TO JOIN!");
					bluemeta.setDisplayName(ChatColor.BLUE + "Blue Team - " + ChatColor.BOLD + "CLICK TO JOIN!");
					redmeta.setDisplayName(ChatColor.RED + "Red Team - " + ChatColor.BOLD + "CLICK TO JOIN!");
					greenwool.setItemMeta(greenmeta);
					yellowwool.setItemMeta(yellowmeta);
					bluewool.setItemMeta(bluemeta);
					redwool.setItemMeta(redmeta);
					
					player.teleport(new Location(Bukkit.getWorld("Forsaken"), MegaWallsPlugin.plugin.dataconfiguaration.getInt("Gameworlds.Forsaken.Spawnpoints.Lobby.X"), MegaWallsPlugin.plugin.dataconfiguaration.getInt("Gameworlds.Forsaken.Spawnpoints.Lobby.Y"), MegaWallsPlugin.plugin.dataconfiguaration.getInt("Gameworlds.Forsaken.Spawnpoints.Lobby.Z")));
					player.getInventory().setItem(0, redwool);
					player.getInventory().setItem(1, bluewool);
					player.getInventory().setItem(2, greenwool);
					player.getInventory().setItem(3, yellowwool);
					player.updateInventory();
					int Players = Bukkit.getWorld("Forsaken").getPlayers().size();
					sign.setLine(2, Players + "/100");
					sign.setLine(3, "Forsaken");
					sign.update();
					
				} else if(sign.getLine(3).equals("Dragonkeep")){
					
					player.sendMessage(ChatColor.GREEN + "Put you in a" + ChatColor.GOLD + " Dragonkeep " + ChatColor.GREEN + "queue!");
					player.teleport(new Location(Bukkit.getWorld("Dragonkeep"),-775,104,-139));
					ItemStack greenwool = new ItemStack(Material.WOOL, 1, (byte)13);
					ItemStack yellowwool = new ItemStack(Material.WOOL, 1, (byte)4);
					ItemStack bluewool = new ItemStack(Material.WOOL, 1, (byte)11);
					ItemStack redwool = new ItemStack(Material.WOOL, 1, (byte)14);
					ItemMeta greenmeta = greenwool.getItemMeta();
					ItemMeta yellowmeta = yellowwool.getItemMeta();
					ItemMeta bluemeta = bluewool.getItemMeta();
					ItemMeta redmeta = redwool.getItemMeta();
					greenmeta.setDisplayName(ChatColor.GREEN + "Green Team - " + ChatColor.BOLD + "CLICK TO JOIN!");
					yellowmeta.setDisplayName(ChatColor.YELLOW + "Yellow Team - " + ChatColor.BOLD + "CLICK TO JOIN!");
					bluemeta.setDisplayName(ChatColor.BLUE + "Blue Team - " + ChatColor.BOLD + "CLICK TO JOIN!");
					redmeta.setDisplayName(ChatColor.RED + "Red Team - " + ChatColor.BOLD + "CLICK TO JOIN!");
					greenwool.setItemMeta(greenmeta);
					yellowwool.setItemMeta(yellowmeta);
					bluewool.setItemMeta(bluemeta);
					redwool.setItemMeta(redmeta);
					
					player.getInventory().setItem(0, redwool);
					player.getInventory().setItem(1, bluewool);
					player.getInventory().setItem(2, greenwool);
					player.getInventory().setItem(3, yellowwool);
					player.updateInventory();
					int Players = Bukkit.getWorld("Dragonkeep").getPlayers().size();
					sign.setLine(2, Players + "/100");
					sign.setLine(3, "Dragonkeep");
					
				} else if(sign.getLine(3).equals("Frozen")){
					
					player.sendMessage(ChatColor.GREEN + "Put you in a" + ChatColor.GOLD + " Frozen " + ChatColor.GREEN + "queue!");
					player.teleport(new Location(Bukkit.getWorld("Frozen"),-776,102,-139));
					ItemStack greenwool = new ItemStack(Material.WOOL, 1, (byte)13);
					ItemStack yellowwool = new ItemStack(Material.WOOL, 1, (byte)4);
					ItemStack bluewool = new ItemStack(Material.WOOL, 1, (byte)11);
					ItemStack redwool = new ItemStack(Material.WOOL, 1, (byte)14);
					ItemMeta greenmeta = greenwool.getItemMeta();
					ItemMeta yellowmeta = yellowwool.getItemMeta();
					ItemMeta bluemeta = bluewool.getItemMeta();
					ItemMeta redmeta = redwool.getItemMeta();
					greenmeta.setDisplayName(ChatColor.GREEN + "Green Team - " + ChatColor.BOLD + "CLICK TO JOIN!");
					yellowmeta.setDisplayName(ChatColor.YELLOW + "Yellow Team - " + ChatColor.BOLD + "CLICK TO JOIN!");
					bluemeta.setDisplayName(ChatColor.BLUE + "Blue Team - " + ChatColor.BOLD + "CLICK TO JOIN!");
					redmeta.setDisplayName(ChatColor.RED + "Red Team - " + ChatColor.BOLD + "CLICK TO JOIN!");
					greenwool.setItemMeta(greenmeta);
					yellowwool.setItemMeta(yellowmeta);
					bluewool.setItemMeta(bluemeta);
					redwool.setItemMeta(redmeta);
					
					player.getInventory().setItem(0, redwool);
					player.getInventory().setItem(1, bluewool);
					player.getInventory().setItem(2, greenwool);
					player.getInventory().setItem(3, yellowwool);
					player.updateInventory();
					int Players = Bukkit.getWorld("Frozen").getPlayers().size();
					sign.setLine(2, Players + "/100");
					sign.setLine(3, "Frozen");
					sign.setLine(0, ChatColor.GREEN + "[Join]");
					sign.update();					
					
				} else if(sign.getLine(1).equalsIgnoreCase(ChatColor.RED + "Waiting for")){
					
					player.sendMessage(ChatColor.RED + "This server is currently reloading!");
					event.setCancelled(true);
					
				}
				
			}
			
		}
		
	}
		
	
}
