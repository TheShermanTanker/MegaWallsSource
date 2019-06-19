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
	
	//Integer is the game id
	Map<Sign, Integer> signs = new HashMap<Sign, Integer>();
	Map<Integer, World> worlds = new HashMap<Integer, World>();
	
	public JoinSigns() {
		World world = Bukkit.getWorld("Lobby");
		
		Sign sign;
		Sign ssign;
		Sign signs;
		Sign ssigns;
		Sign signt;
		Sign ssignt;
		
		//0-0
		Block block = world.getBlockAt(-2503, 8, 743);
		Block sblock = world.getBlockAt(-2503, 8, 699);
		
		if(block.getType() == Material.WALL_SIGN && sblock.getType() == Material.WALL_SIGN) {
			sign = (Sign) block.getState();
			ssign = (Sign) sblock.getState();
			this.signs.put(sign, 0);
			this.signs.put(ssign, 0);
		}
		
		//1-0
		Block blocks = world.getBlockAt(-2504, 8, 743);
		Block sblocks = world.getBlockAt(-2504, 8, 699);
		
		if(blocks.getType() == Material.WALL_SIGN && sblocks.getType() == Material.WALL_SIGN) {
			signs = (Sign) blocks.getState();
			ssigns = (Sign) sblocks.getState();
			this.signs.put(signs, 1);
			this.signs.put(ssigns, 1);
		}
		
		//2-0
		Block blockt = world.getBlockAt(-2505, 8, 743);
		Block sblockt = world.getBlockAt(-2505, 8, 699);
		
		if(blockt.getType() == Material.WALL_SIGN && sblockt.getType() == Material.WALL_SIGN) {
			signt = (Sign) blockt.getState();
			ssignt = (Sign) sblockt.getState();
			this.signs.put(signt, 2);
			this.signs.put(ssignt, 2);
			
			System.out.println(this.signs.entrySet());
		}
	} 
	
	public void registerGame(World world) {
		boolean successful = false;
		int i = 0;
		for(int id = 0; id <= 2; id++) {
			if(!worlds.containsKey(id)) {
				worlds.put(id, world);
				i = id;
				successful = true;
				for(Sign sign : signs.keySet()) {
					if(signs.get(sign) == id) {
						sign.setLine(2, "0/100");
						sign.setLine(3, world.getName());
						sign.update();
					}
				}
				break;
			}
		}
		
		if(!successful) {
			this.unregisterGame(i);
			System.out.println("There is currently no space for another game!");
		}
	}
	
	public void unregisterGame(int id) {
		worlds.remove(id);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(PlayerInteractEvent event) {
								
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			Block block = event.getClickedBlock();
			if(block.getType() == Material.WALL_SIGN) {
				
				Sign sign = (Sign) block.getState();
				Player player = event.getPlayer(); 
				
				if(sign == null) {
					System.out.println("Error occured, could not join game");
					return;
				}
				
				int gameId = signs.get(sign);
				World world = worlds.get(gameId);
				
				player.sendMessage(ChatColor.GREEN + "Put you in a " + ChatColor.GOLD + world.getName() + ChatColor.GREEN + " queue!");
				
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
				
				player.teleport(new Location(world, MegaWallsPlugin.plugin.dataconfiguaration.getInt("Gameworlds." + world.getName() + ".Spawnpoints.Lobby.X"), MegaWallsPlugin.plugin.dataconfiguaration.getInt("Gameworlds." + world.getName() + ".Spawnpoints.Lobby.Y"), MegaWallsPlugin.plugin.dataconfiguaration.getInt("Gameworlds." + world.getName() + ".Spawnpoints.Lobby.Z")));
				player.getInventory().setItem(0, redwool);
				player.getInventory().setItem(1, bluewool);
				player.getInventory().setItem(2, greenwool);
				player.getInventory().setItem(3, yellowwool);
				player.updateInventory();
				int players = world.getPlayers().size();
				sign.setLine(2, players + "/100");
				sign.update();
				
				/*
                if(sign.getLine(2).equals(100/100)) {
					
					player.sendMessage(ChatColor.RED + "This game is already full!");
					event.setCancelled(true);
					return;
					
				}
				if(sign.getLine(3).equals("Forsaken")) {
																				
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
					int players = Bukkit.getWorld("Forsaken").getPlayers().size();
					sign.setLine(2, players + "/100");
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
					int players = Bukkit.getWorld("Dragonkeep").getPlayers().size();
					sign.setLine(2, players + "/100");
					sign.setLine(3, "Dragonkeep");
					
				} else if(sign.getLine(3).equals("Frozen")) {
					
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
					int players = Bukkit.getWorld("Frozen").getPlayers().size();
					sign.setLine(2, players + "/100");
					sign.setLine(3, "Frozen");
					sign.setLine(0, ChatColor.GREEN + "[Join]");
					sign.update();					
					
				} else if(sign.getLine(1).equalsIgnoreCase(ChatColor.RED + "Waiting for")) {
					
					player.sendMessage(ChatColor.RED + "This server is currently reloading!");
					event.setCancelled(true);
					
				}*/
				
			}
			
		}
		
	}
		
	
}
