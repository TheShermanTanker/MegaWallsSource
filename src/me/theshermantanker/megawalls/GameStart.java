package me.theshermantanker.megawalls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.theshermantanker.withercraft.WitherCraft;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class GameStart implements Listener{
	
	MegaWallsPlugin plugin = MegaWallsPlugin.plugin;
	WitherCraft withercraft = WitherCraft.instance;
	FileConfiguration playerdata = withercraft.customConfig;
	DatabaseHandler handler = new DatabaseHandler();
	GameHandler gameHandler = new GameHandler();
	LogicHelper helper = plugin.helper;
	int minimumCount = plugin.dataconfiguaration.getInt("General.MinimumPlayerCount");
	Map<World, BukkitTask> runnables = new HashMap<World, BukkitTask>();
	
	private void spawnGreenWither(Location location, Team allies){
		
	    net.minecraft.server.v1_7_R4.World nmsworld = ((CraftWorld) location.getWorld()).getHandle();
	    EntityGreenWither wither = new EntityGreenWither(nmsworld, location, allies);	    
	    wither.setPosition(location.getX(), location.getY(), location.getZ());
	    nmsworld.addEntity(wither);
	    
	}
	
     private void spawnYellowWither(Location location, Team allies){
    	 
	    net.minecraft.server.v1_7_R4.World nmsworld = ((CraftWorld) location.getWorld()).getHandle();
	    EntityYellowWither wither = new EntityYellowWither(nmsworld, location, allies);
	    wither.setPosition(location.getX(), location.getY(), location.getZ());
	    nmsworld.addEntity(wither);
	    
	}
     
     private void spawnBlueWither(Location location, Team allies){
    	 
 	    net.minecraft.server.v1_7_R4.World nmsworld = ((CraftWorld) location.getWorld()).getHandle();
 	    EntityBlueWither wither = new EntityBlueWither(nmsworld, location, allies);
 	    wither.setPosition(location.getX(), location.getY(), location.getZ());
 	    nmsworld.addEntity(wither);
 	    
 	}
     
     private void spawnRedWither(Location location, Team allies){
    	 
 	    net.minecraft.server.v1_7_R4.World nmsworld = ((CraftWorld) location.getWorld()).getHandle();
 	    EntityRedWither wither = new EntityRedWither(nmsworld, location, allies);
 	    wither.setPosition(location.getX(), location.getY(), location.getZ());
 	    nmsworld.addEntity(wither);
 	    
 	}
	
	@EventHandler
	public void onJoin(PlayerChangedWorldEvent event){
		Player player = event.getPlayer();
		World world = player.getWorld();
		
		if(plugin.joinSigns.worlds.containsValue(world) && player.getWorld().getPlayers().size() >= minimumCount && !runnables.containsKey(world)){
			WorldDataHolder holder = handler.getWorldData(plugin.getWorldName(player));
			
			BukkitTask runnable = new BukkitRunnable() {
	              int time = 30;
			       @Override
	               public void run(){
	             
	                   if(time == 30){
	                       for(Player players : world.getPlayers()){
	                            players.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Mega Walls" + ChatColor.GRAY + "]: " + ChatColor.YELLOW + "The game starts in " + time + " seconds!");
	                            
	                       }
	                   } else if(time < 6 && time > 1){
	                	   
	                	   for(Player players : world.getPlayers()){
	                		   
	                		   players.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Mega Walls" + ChatColor.GRAY + "]: " + ChatColor.YELLOW + time + " seconds until the game starts!");
	                		   
	                	   }
	                	   
	                   }
	                   else if(time == 1){
	                	   
	                	   for(Player players : world.getPlayers()){
	                            players.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Mega Walls" + ChatColor.GRAY + "]: " + ChatColor.YELLOW + time + " second until the game starts!");
	                             
	                	   }
	                	   
	                   }
	                     else if (time == 0){
	                             
	                       this.cancel();
	                       Scoreboard green = null;
	                       Scoreboard yellow = null;
	                       Scoreboard blue = null;
	                       Scoreboard red = null;
	                       Team greenTeam = null;
	                       Team yellowTeam = null;
	                       Team blueTeam = null;
	                       Team redTeam = null;
	                       Map<Scoreboard, String> scoreboards = helper.scoreboards.get(world);
	                       for(Scoreboard scoreboard : scoreboards.keySet()){
	                    	   if(scoreboards.get(scoreboard).equals("Green")){
	                    		   green = scoreboard;
	                    		   greenTeam = scoreboard.getTeam("GreenTeam");
	                    	   } else if(scoreboards.get(scoreboard).equals("Yellow")){
	                    		   yellow = scoreboard;
	                    		   yellowTeam = scoreboard.getTeam("YellowTeam");
	                    	   } else if(scoreboards.get(scoreboard).equals("Blue")){
	                    		   blue = scoreboard;
	                    		   blueTeam = scoreboard.getTeam("BlueTeam");
	                    	   } else if(scoreboards.get(scoreboard).equals("Red")){
	                    		   red = scoreboard;
	                    		   redTeam = scoreboard.getTeam("RedTeam");
	                    	   }
	                       }
	                       for(Player players : world.getPlayers()){
	                    	   
	                    	   players.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 20000000, 4));
	                    	   players.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 9));
	                    	   
	                       }
	                       for(OfflinePlayer offlineplayers : greenTeam.getPlayers()){
	                    	   Player players = offlineplayers.getPlayer();
	                    	   
	                    	   players.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Mega Walls" + ChatColor.GRAY + "]: " + ChatColor.YELLOW + "The game has started! You are on the Green team, the walls will fall down in 10 minutes.");
	                    	   players.teleport(holder.greenSpawn);
	                    	   players.setBedSpawnLocation(holder.greenSpawn, true);
	                    	   Objective greenboard = green.getObjective(DisplaySlot.SIDEBAR);
	                    	   greenboard.getScore(ChatColor.GREEN + "Wither Health:").setScore(1000);
	                    	   greenboard.getScore(ChatColor.YELLOW + "Wither Health:").setScore(1000);
	                    	   greenboard.getScore(ChatColor.BLUE + "Wither Health:").setScore(1000);
	                    	   greenboard.getScore(ChatColor.RED + "Wither Health:").setScore(1000);
	                    	   players.setScoreboard(green);
	                    	   players.getInventory().clear();
	                    	   
	                       }
	                       for(OfflinePlayer offlineplayers : yellowTeam.getPlayers()){
	                    	   Player players = offlineplayers.getPlayer();
	                    	   
	                    	   players.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Mega Walls" + ChatColor.GRAY + "]: " + ChatColor.YELLOW + "The game has started! You are on the Yellow team, the walls will fall down in 10 minutes.");
	                    	   players.teleport(holder.yellowSpawn);
	                    	   players.setBedSpawnLocation(holder.yellowSpawn, true);
	                    	   Objective yellowboard = yellow.getObjective(DisplaySlot.SIDEBAR);
	                    	   yellowboard.getScore(ChatColor.GREEN + "Wither Health:").setScore(1000);
	                    	   yellowboard.getScore(ChatColor.YELLOW + "Wither Health:").setScore(1000);
	                    	   yellowboard.getScore(ChatColor.BLUE + "Wither Health:").setScore(1000);
	                    	   yellowboard.getScore(ChatColor.RED + "Wither Health:").setScore(1000);
	                    	   players.setScoreboard(yellow);
	                    	   players.getInventory().clear();
	                    	   
	                       }
	                       for(OfflinePlayer offlineplayers : blueTeam.getPlayers()){
	                    	   Player players = offlineplayers.getPlayer();
	                    	   
	                    	   players.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Mega Walls" + ChatColor.GRAY + "]: " + ChatColor.YELLOW + "The game has started! You are on the Blue team, the walls will fall down in 10 minutes.");
	                    	   players.teleport(holder.blueSpawn);
	                    	   players.setBedSpawnLocation(holder.blueSpawn, true);
	                    	   Objective blueboard = blue.getObjective(DisplaySlot.SIDEBAR);
	                    	   blueboard.getScore(ChatColor.GREEN + "Wither Health:").setScore(1000);
	                    	   blueboard.getScore(ChatColor.YELLOW + "Wither Health:").setScore(1000);
	                    	   blueboard.getScore(ChatColor.BLUE + "Wither Health:").setScore(1000);
	                    	   blueboard.getScore(ChatColor.RED + "Wither Health:").setScore(1000);
	                    	   players.setScoreboard(blue);
	                    	   players.getInventory().clear();
	                    	   
	                       }
	                       for(OfflinePlayer offlineplayers : redTeam.getPlayers()){
	                    	   Player players = offlineplayers.getPlayer();
	                    	   
	                    	   players.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Mega Walls" + ChatColor.GRAY + "]: " + ChatColor.YELLOW + "The game has started! You are on the Red team, the walls will fall down in 10 minutes.");
	                    	   players.teleport(holder.redSpawn);
	                    	   players.setBedSpawnLocation(holder.redSpawn, true);
	                    	   Objective redboard = red.getObjective(DisplaySlot.SIDEBAR);
	                    	   redboard.getScore(ChatColor.GREEN + "Wither Health:").setScore(1000);
	                    	   redboard.getScore(ChatColor.YELLOW + "Wither Health:").setScore(1000);
	                    	   redboard.getScore(ChatColor.BLUE + "Wither Health:").setScore(1000);
	                    	   redboard.getScore(ChatColor.RED + "Wither Health:").setScore(1000);
	                    	   players.setScoreboard(red);
	                    	   players.getInventory().clear();
	                    	   
	                       }                     
	                       spawnGreenWither(holder.greenWitherSpawn, greenTeam);
	                       spawnBlueWither(holder.blueWitherSpawn, blueTeam);
	                       spawnRedWither(holder.redWitherSpawn, redTeam);
	                       spawnYellowWither(holder.yellowWitherSpawn, yellowTeam);
	                       plugin.worlds.put(world, true);
	                       EnhancedScheduler scheduler = new EnhancedScheduler(600, false);
	                       scheduler.startTimer();
	                       gameHandler.registerGame(world, scheduler);
	                       List<Team> teams = new ArrayList<Team>();
	                       teams.add(greenTeam);
	                       teams.add(yellowTeam);
	                       teams.add(blueTeam);
	                       teams.add(redTeam);
	                       gameHandler.tickGame(world, teams);
	                       plugin.manager.releaseWorld(world);
	                       	                       	                       
	                                               }
	                  time--;
	               }
	           }.runTaskTimer(plugin, 0, 20);
	           runnables.put(world, runnable);
	       }
		
			
		}
	
	@EventHandler
	public void onLeave(PlayerChangedWorldEvent event){
		Player player = event.getPlayer();
		World world = event.getFrom();
		
		if(plugin.joinSigns.worlds.containsValue(world)) {
			
			if(world.getPlayers().size() < minimumCount && runnables.containsKey(world)){
				
				runnables.get(world).cancel();
				runnables.remove(world);
				for(Player players : world.getPlayers()){
					
					players.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Mega Walls" + ChatColor.GRAY + "]: " + ChatColor.RED + "Not enough players! Start cancelled!");
					
				}
				
			}
			
			String uuid = player.getUniqueId().toString();
			String rank = playerdata.getString(uuid + ".Rank");
			String displayname = null;
			
			if(rank.equals("Owner")){
				
				displayname = ChatColor.DARK_RED + player.getName();
				
			} else if(rank.equals("Admin")){
				
				displayname = ChatColor.RED + player.getName();
				
			} else if(rank.equals("Moderator")){
				
				displayname = ChatColor.DARK_GREEN + player.getName();
				
			} else if(rank.equals("Helper")){
				
				displayname = ChatColor.DARK_BLUE + player.getName();
				
			} else if(rank.equals("MVP")){
				
				displayname = ChatColor.AQUA + player.getName();
				
			} else if(rank.equals("VIP")){
				
				displayname = ChatColor.GREEN + player.getName();
				
			} else if(rank.equals("Default")){
				
				displayname = ChatColor.GRAY + player.getName();
				
			}
			
			int playerCount = world.getPlayers().size();
			
            for(Player players : world.getPlayers()){
				
				if(!(displayname == null)){
					
					players.sendMessage(displayname + ChatColor.YELLOW + " has left! (" + ChatColor.AQUA + playerCount + ChatColor.YELLOW + "/" + ChatColor.AQUA + "100" + ChatColor.YELLOW + ")");
					
				}
				
			}
            
            int id = -1;
            
            for(Map.Entry<Integer, World> entry : plugin.joinSigns.worlds.entrySet()) {
            	if(entry.getValue() == world) {
            		id = entry.getKey();
            		break;
            	}
            }
            
			for(Sign sign : plugin.joinSigns.signs.keySet()) {
				if(plugin.joinSigns.signs.get(sign) == id) {
					sign.setLine(2, playerCount + "/100");
				}
			}
			
		}
		
	}
	
		
	}


