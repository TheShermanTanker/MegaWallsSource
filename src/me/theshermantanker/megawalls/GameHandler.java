package me.theshermantanker.megawalls;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import me.theshermantanker.withercraft.WitherCraft;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityExperienceOrb;

public class GameHandler implements Listener {
	
	MegaWallsPlugin plugin = MegaWallsPlugin.plugin;
	LogicHelper helper = plugin.helper;
	WitherCraft withercraft = plugin.withercraft;
	Map<World, EnhancedScheduler> handleList = new HashMap<World, EnhancedScheduler>();
	Map<World, Integer> gamePhase = new HashMap<World, Integer>();
	Map<World, BukkitTask> gameLoops = new HashMap<World, BukkitTask>();
	ScoreboardManager manager = Bukkit.getScoreboardManager();
	
	public void registerGame(World world, EnhancedScheduler scheduler){
		boolean valid = plugin.worlds.get(world);
		
		if(valid){
			handleList.put(world, scheduler);
		}
		
	}
	
	public boolean isEnemy(Player player, Player second) {
		boolean enemy = false;
		if(!plugin.worlds.get(player.getWorld())) return enemy;
		Map<Scoreboard, String> worldScoreboards = helper.scoreboards.get(player.getWorld());
		
		if(worldScoreboards.containsKey(player.getScoreboard())) {
			Scoreboard scoreboard = player.getScoreboard();
			Team team = scoreboard.getPlayerTeam(player);
			enemy = team.hasPlayer(second);
		}
		return enemy;
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		Player player = null;
		if(event.getEntity() instanceof Player) {
			player = (Player) event.getEntity();			
		}
        if(!(event.getCause() == DamageCause.DROWNING || event.getCause() == DamageCause.MAGIC || event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.THORNS) && player != null) {
			if(plugin.classHandler.isGame(player)) {
				plugin.classHandler.damageEffect(player);
			}
		}
		
	}
	
	public void tickGame(World world){
		gamePhase.put(world, 1);
		
		 BukkitTask runnable = new BukkitRunnable() {
			 
			 int ChatCooldown = 0;
			 boolean withersEnraged = true;	  
			 
				  public void run(){
					  
			    		  Map<Scoreboard, String> worldScoreboards = helper.scoreboards.get(world);
			    		  for(Scoreboard scoreboard : worldScoreboards.keySet()){
			    			  
			    			  if(worldScoreboards == null){
			    			      return;
			    		      }
			    			  
			    			  EntityGreenWither greenWither = null;
			    			  EntityYellowWither yellowWither = null;
			    			  EntityBlueWither blueWither = null;
			    			  EntityRedWither redWither = null;
			    			  
			    			  for(Player player : world.getPlayers()){
			    				  if(!player.hasPotionEffect(PotionEffectType.HEALTH_BOOST) && !(player.getScoreboard() == manager.getMainScoreboard())){
			    					  player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 20000000, 4));
			    				  }
			    				  if(gamePhase.get(world) < 3){
			    					  player.setFoodLevel(20);
			    				  }
			    			  }
			    			  
			    			  
			    			  
			    			  net.minecraft.server.v1_7_R4.World nmsworld = ((CraftWorld) world).getHandle();
			    			  for(Object entity : nmsworld.entityList){
			    				  if(entity instanceof Entity){
			    					  Entity target = (Entity) entity;
			    					  if(target instanceof EntityExperienceOrb) {
			    						  target.die();
			    					  }
			    					  if(target instanceof EntityGreenWither){
			    						  greenWither = (EntityGreenWither) target;
			    					  } else if(target instanceof EntityYellowWither){
			    						  yellowWither = (EntityYellowWither) target;
			    					  } else if(target instanceof EntityBlueWither){
			    						  blueWither = (EntityBlueWither) target;
			    					  } else if(target instanceof EntityRedWither){
			    						  redWither = (EntityRedWither) target;
			    					  }
			    				  }
			    			  }
			    			  
			    			  EnhancedScheduler timer = handleList.get(world);
			    			  if(timer.getTime() <= 0 && gamePhase.get(world) == 1){
			    				  gamePhase.put(world, 2);
			    				  greenWither.setDecay();
			    				  yellowWither.setDecay();
			    				  blueWither.setDecay();
			    				  redWither.setDecay();
			    				  greenWither.setEnraged(true);
			    				  yellowWither.setEnraged(true);
			    				  blueWither.setEnraged(true);
			    				  redWither.setEnraged(true);
			    				  timer.setTime(3000);
			    			  }
			    			  
			    			  if((timer.getTime() == 540 || timer.getTime() == 480 || timer.getTime() == 420 || timer.getTime() == 360 || timer.getTime() == 300 || timer.getTime() == 240 || timer.getTime() == 180 || timer.getTime() == 120 || timer.getTime() == 60) && gamePhase.get(world) == 1){
			    				  for(Player players : world.getPlayers()){
			    					  int Time = timer.getTime() / 60;
			    					  if(!(ChatCooldown == 0)){
			    						  ChatCooldown--;
			    					  } else {
			    							  ChatCooldown = 10;
				    						  if(Time > 1){
				    							  players.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Mega Walls" + ChatColor.GRAY + "]: " + ChatColor.YELLOW + Time + " minutes until the walls will fall down!");
				    						  } else if(Time == 1){
				    							  players.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Mega Walls" + ChatColor.GRAY + "]: " + ChatColor.YELLOW + Time + " minute until the walls will fall down!");
				    						  } else {
				    							  
				    						  }
				    					  
			    					  }
			    				  }
			    			  }
			    			  
			    			  if(timer.getTime() == 2400 && gamePhase.get(world) == 2){
			    				  greenWither.setEnraged(false);
			    				  yellowWither.setEnraged(false);
			    				  blueWither.setEnraged(false);
			    				  redWither.setEnraged(false);
			    				  if(withersEnraged){
			    					  withersEnraged = false;
			    					  for(Player player : world.getPlayers()){
				    					  player.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Mega Walls" + ChatColor.GRAY + "]: " + ChatColor.YELLOW + "Withers are no longer enraged!");
				    				  }
			    				  }
			    				  
			    			  }
			    			  
			    			  if(worldScoreboards.get(scoreboard).equals("Green")){
			    				  Objective green = scoreboard.getObjective(DisplaySlot.SIDEBAR);
			    				  green.setDisplayName(ChatColor.GREEN + "Mega Walls " + ChatColor.RED + handleList.get(world).formatStringTime());
			    				  if(!(greenWither == null)){
			    					  green.getScore(ChatColor.GREEN + "Wither Health:").setScore((int) greenWither.getHealth());
			    				  }
			    				  if(!(yellowWither == null)){
			    					  green.getScore(ChatColor.YELLOW + "Wither Health:").setScore((int) yellowWither.getHealth());
			    				  }
			    				  if(!(blueWither == null)){
			    					  green.getScore(ChatColor.BLUE + "Wither Health:").setScore((int) blueWither.getHealth());
			    				  }
			    				  if(!(redWither == null)){
			    					  green.getScore(ChatColor.RED + "Wither Health:").setScore((int) redWither.getHealth());
			    				  }
			    			  } else if(worldScoreboards.get(scoreboard).equals("Yellow")){
			    				  Objective yellow = scoreboard.getObjective(DisplaySlot.SIDEBAR);
			    				  yellow.setDisplayName(ChatColor.YELLOW + "Mega Walls " + ChatColor.RED + handleList.get(world).formatStringTime());
			    				  if(!(greenWither == null)){
			    					  yellow.getScore(ChatColor.GREEN + "Wither Health:").setScore((int) greenWither.getHealth());
			    				  }
			    				  if(!(yellowWither == null)){
			    					  yellow.getScore(ChatColor.YELLOW + "Wither Health:").setScore((int) yellowWither.getHealth());
			    				  }
			    				  if(!(blueWither == null)){
			    					  yellow.getScore(ChatColor.BLUE + "Wither Health:").setScore((int) blueWither.getHealth());
			    				  }
			    				  if(!(redWither == null)){
			    					  yellow.getScore(ChatColor.RED + "Wither Health:").setScore((int) redWither.getHealth());
			    				  }  
			    			  } else if(worldScoreboards.get(scoreboard).equals("Blue")){
			    				  Objective blue = scoreboard.getObjective(DisplaySlot.SIDEBAR);
			    				  blue.setDisplayName(ChatColor.BLUE + "Mega Walls " + ChatColor.RED + handleList.get(world).formatStringTime());
			    				  if(!(greenWither == null)){
			    					  blue.getScore(ChatColor.GREEN + "Wither Health:").setScore((int) greenWither.getHealth());
			    				  }
			    				  if(!(yellowWither == null)){
			    					  blue.getScore(ChatColor.YELLOW + "Wither Health:").setScore((int) yellowWither.getHealth());
			    				  }
			    				  if(!(blueWither == null)){
			    					  blue.getScore(ChatColor.BLUE + "Wither Health:").setScore((int) blueWither.getHealth());
			    				  }
			    				  if(!(redWither == null)){
			    					  blue.getScore(ChatColor.RED + "Wither Health:").setScore((int) redWither.getHealth());
			    				  }
			    			  }	else if(worldScoreboards.get(scoreboard).equals("Red")){
			    				  Objective red = scoreboard.getObjective(DisplaySlot.SIDEBAR);
			    				  red.setDisplayName(ChatColor.RED + "Mega Walls " + handleList.get(world).formatStringTime());
			    				  if(!(greenWither == null)){
			    					  red.getScore(ChatColor.GREEN + "Wither Health:").setScore((int) greenWither.getHealth());
			    				  }
			    				  if(!(yellowWither == null)){
			    					  red.getScore(ChatColor.YELLOW + "Wither Health:").setScore((int) yellowWither.getHealth());
			    				  }
			    				  if(!(blueWither == null)){
			    					  red.getScore(ChatColor.BLUE + "Wither Health:").setScore((int) blueWither.getHealth());
			    				  }
			    				  if(!(redWither == null)){
			    					  red.getScore(ChatColor.RED + "Wither Health:").setScore((int) redWither.getHealth());
			    				  }
			    			  }
			    				  
			    		  }
			    		  
				  }	  
				  	  
		      }.runTaskTimer(plugin, 0, 5);
		      gameLoops.put(world, runnable);
			
	}
	
	public void leftClick(Player player, Player other) {
		
	}
	
	public void stopGame(World world){
		gameLoops.get(world).cancel();
	}
	
	public void stopHandling(World world){
		handleList.remove(world);
		this.stopGame(world);
	}

}
