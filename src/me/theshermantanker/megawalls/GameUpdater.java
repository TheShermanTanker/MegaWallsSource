package me.theshermantanker.megawalls;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityExperienceOrb;

public class GameUpdater extends BukkitRunnable implements Listener {
	
	int chatCooldown = 0;
	boolean withersEnraged = true;	  
	World world;
	LogicHelper helper;
	GameHandler handler;
	ScoreboardManager manager;
	List<Team> teams;
	EntityGreenWither greenWither = null;
	EntityYellowWither yellowWither = null;
	EntityBlueWither blueWither = null;
	EntityRedWither redWither = null;
	Map<Scoreboard, String> worldScoreboards;
	boolean allWithersDead = false;
	
	@EventHandler
	public void onClick(PlayerClickEvent event) {
		Player player = event.getPlayer();
		Player clicked = event.getLeftClicked();
		
		if(!(player.getWorld() == world) || !(clicked.getWorld() == world)) return;
		
		Team team = null;
		for(Team teams : this.teams) {
			if(teams.hasPlayer(player)) {
				team = teams;
			}
			if(!(team == null)) {
				if(team.hasPlayer(clicked)) {
					//Handle Energy Gain
					this.damageEffect(clicked);
				}
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if(!(event.getEntity().getWorld() == world)) return;
		Player player = null;
		if(event.getEntity() instanceof Player) {
			player = (Player) event.getEntity();			
		}
		
        if(!(event.getCause() == DamageCause.DROWNING || event.getCause() == DamageCause.MAGIC || event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.THORNS) && player != null) {
			this.damageEffect(player);
		}
		
	}
	
	public void damageEffect(Player player) {
		Location location = player.getLocation();
		location.setY(location.getY() + 1.2);
		player.getWorld().playEffect(location, Effect.STEP_SOUND, Material.REDSTONE_WIRE);
	}
	
	public GameUpdater(List<Team> teams, World world, LogicHelper helper, GameHandler handler) throws Exception {
		this.world = world;
		this.helper = helper;
		this.handler = handler;
		this.manager = handler.manager;
		Bukkit.getServer().getPluginManager().registerEvents(this, MegaWallsPlugin.plugin);
		this.teams = teams;
		worldScoreboards = helper.scoreboards.get(world);
		if(worldScoreboards == null){
		      throw new Exception("Critical error occured");
	      }
		System.out.println("Execution successful");
	}	
	
		  public void run(){
			  
	    		  for(Scoreboard scoreboard : worldScoreboards.keySet()) {
	    			  
	    			  for(Player player : world.getPlayers()){
	    				  if(!player.hasPotionEffect(PotionEffectType.HEALTH_BOOST) && !(player.getScoreboard() == manager.getMainScoreboard())){
	    					  player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 20000000, 4));
	    				  }
	    			  }
	    			  
	    			  
	    			  
	    			  net.minecraft.server.v1_7_R4.World nmsworld = ((CraftWorld) world).getHandle();
	    			  for(Object entity : nmsworld.entityList){
	    				  if(entity instanceof Entity){
	    					  Entity target = (Entity) entity;
	    					  if(target instanceof EntityExperienceOrb) {
	    						  target.die();
	    					  }
	    					  
	    					  if(greenWither == null || yellowWither == null || blueWither == null || redWither == null) {
	    						  if(target instanceof EntityGreenWither) {
		    						  greenWither = (EntityGreenWither) target;
		    					  } else if(target instanceof EntityYellowWither) {
		    						  yellowWither = (EntityYellowWither) target;
		    					  } else if(target instanceof EntityBlueWither) {
		    						  blueWither = (EntityBlueWither) target;
		    					  } else if(target instanceof EntityRedWither) {
		    						  redWither = (EntityRedWither) target;
		    					  }
	    					  }
	    					  
	    				  }
	    			  }
	    			  
	    			  EnhancedScheduler timer = handler.handleList.get(world);
	    			  if(timer.getTime() <= 0 && handler.gamePhase.get(world) == 1) {
	    				  handler.gamePhase.put(world, 2);
	    				  greenWither.setDecay();
	    				  yellowWither.setDecay();
	    				  blueWither.setDecay();
	    				  redWither.setDecay();
	    				  greenWither.setEnraged(true);
	    				  yellowWither.setEnraged(true);
	    				  blueWither.setEnraged(true);
	    				  redWither.setEnraged(true);
	    				  for(Player players : world.getPlayers()) {
	    					  players.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Mega Walls" + ChatColor.GRAY + "]: " + ChatColor.YELLOW + "The walls have come down! Prepare for battle!");
	    				  }
	    				  timer.setTime(3000);
	    				  timer.autoCancel = true;
	    			  }
	    			  
	    			  if(timer.getTime() % 60 == 0 && handler.gamePhase.get(world) == 1){
	    				  for(Player players : world.getPlayers()) {
	    					  int time = timer.getTime() / 60;
	    					  if(!(chatCooldown == 0)){
	    						  chatCooldown--;
	    					  } else {
	    							  chatCooldown = 12;
		    						  if(time > 1 && time < 10){
		    							  players.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Mega Walls" + ChatColor.GRAY + "]: " + ChatColor.YELLOW + time + " minutes until the walls will fall down!");
		    						  } else if(time == 1){
		    							  players.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Mega Walls" + ChatColor.GRAY + "]: " + ChatColor.YELLOW + time + " minute until the walls will fall down!");
		    						  } else {
		    							  
		    						  }
		    					  
	    					  }
	    				  }
	    			  }
	    			  
	    			  if(timer.getTime() == 2400 && handler.gamePhase.get(world) == 2 && !allWithersDead) {
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
	    			  
	    			  if(handler.gamePhase.get(world) == 3) {
	    				  for(Player players : world.getPlayers()) {
	    					  if(players.hasPotionEffect(PotionEffectType.HUNGER)) {
	    						  players.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 140, 1));
	    						  players.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Mega Walls" + ChatColor.GRAY + "]: " + ChatColor.BOLD + "" + ChatColor.RED + "Get to the middle to stop losing hunger!");
	    					  }
	    				  }
	    			  }
	    			  
	    			  if(!allWithersDead) {
	    				  if(greenWither.dead && yellowWither.dead && blueWither.dead && redWither.dead) {
	    					  allWithersDead = true;
	    					  withersEnraged = false;
	    					  handler.gamePhase.put(world, 3);
	    				  }
	    			  }
	    			  
	    			  if(!allWithersDead) {
	    				  if(worldScoreboards.get(scoreboard).equals("Green")){
		    				  Objective green = scoreboard.getObjective(DisplaySlot.SIDEBAR);
		    				  green.setDisplayName(ChatColor.GREEN + "Mega Walls " + ChatColor.RED + handler.handleList.get(world).formatStringTime());
		    				  if(!(greenWither == null)) {
		    					  float health = greenWither.getHealth();
		    					  int converted;
		    					  if(health > 0 && health < 1) {
		    						  converted = 1;
		    					  } else {
		    						  converted = (int) health;
		    					  }
		    					  green.getScore(ChatColor.GREEN + "Wither Health:").setScore(converted);
		    				  }
		    				  if(!(yellowWither == null)) {
		    					  float health = yellowWither.getHealth();
		    					  int converted;
		    					  if(health > 0 && health < 1) {
		    						  converted = 1;
		    					  } else {
		    						  converted = (int) health;
		    					  }
		    					  green.getScore(ChatColor.YELLOW + "Wither Health:").setScore(converted);
		    				  }
		    				  if(!(blueWither == null)) {
		    					  float health = blueWither.getHealth();
		    					  int converted;
		    					  if(health > 0 && health < 1) {
		    						  converted = 1;
		    					  } else {
		    						  converted = (int) health;
		    					  }
		    					  green.getScore(ChatColor.BLUE + "Wither Health:").setScore(converted);
		    				  }
		    				  if(!(redWither == null)) {
		    					  float health = redWither.getHealth();
		    					  int converted;
		    					  if(health > 0 && health < 1) {
		    						  converted = 1;
		    					  } else {
		    						  converted = (int) health;
		    					  }
		    					  green.getScore(ChatColor.RED + "Wither Health:").setScore(converted);
		    				  }
		    			  } else if(worldScoreboards.get(scoreboard).equals("Yellow")){
		    				  Objective yellow = scoreboard.getObjective(DisplaySlot.SIDEBAR);
		    				  yellow.setDisplayName(ChatColor.YELLOW + "Mega Walls " + ChatColor.RED + handler.handleList.get(world).formatStringTime());
		    				  if(!(greenWither == null)){
		    					  float health = greenWither.getHealth();
		    					  int converted;
		    					  if(health > 0 && health < 1) {
		    						  converted = 1;
		    					  } else {
		    						  converted = (int) health;
		    					  }
		    					  yellow.getScore(ChatColor.GREEN + "Wither Health:").setScore(converted);
		    				  }
		    				  if(!(yellowWither == null)) {
		    					  float health = yellowWither.getHealth();
		    					  int converted;
		    					  if(health > 0 && health < 1) {
		    						  converted = 1;
		    					  } else {
		    						  converted = (int) health;
		    					  }
		    					  yellow.getScore(ChatColor.YELLOW + "Wither Health:").setScore(converted);
		    				  }
		    				  if(!(blueWither == null)) {
		    					  float health = blueWither.getHealth();
		    					  int converted;
		    					  if(health > 0 && health < 1) {
		    						  converted = 1;
		    					  } else {
		    						  converted = (int) health;
		    					  }
		    					  yellow.getScore(ChatColor.BLUE + "Wither Health:").setScore(converted);
		    				  }
		    				  if(!(redWither == null)) {
		    					  float health = redWither.getHealth();
		    					  int converted;
		    					  if(health > 0 && health < 1) {
		    						  converted = 1;
		    					  } else {
		    						  converted = (int) health;
		    					  }
		    					  yellow.getScore(ChatColor.RED + "Wither Health:").setScore(converted);
		    				  }  
		    			  } else if(worldScoreboards.get(scoreboard).equals("Blue")){
		    				  Objective blue = scoreboard.getObjective(DisplaySlot.SIDEBAR);
		    				  blue.setDisplayName(ChatColor.BLUE + "Mega Walls " + ChatColor.RED + handler.handleList.get(world).formatStringTime());
		    				  if(!(greenWither == null)) {
		    					  float health = greenWither.getHealth();
		    					  int converted;
		    					  if(health > 0 && health < 1) {
		    						  converted = 1;
		    					  } else {
		    						  converted = (int) health;
		    					  }
		    					  blue.getScore(ChatColor.GREEN + "Wither Health:").setScore(converted);
		    				  }
		    				  if(!(yellowWither == null)) {
		    					  float health = yellowWither.getHealth();
		    					  int converted;
		    					  if(health > 0 && health < 1) {
		    						  converted = 1;
		    					  } else {
		    						  converted = (int) health;
		    					  }
		    					  blue.getScore(ChatColor.YELLOW + "Wither Health:").setScore(converted);
		    				  }
		    				  if(!(blueWither == null)) {
		    					  float health = blueWither.getHealth();
		    					  int converted;
		    					  if(health > 0 && health < 1) {
		    						  converted = 1;
		    					  } else {
		    						  converted = (int) health;
		    					  }
		    					  blue.getScore(ChatColor.BLUE + "Wither Health:").setScore(converted);
		    				  }
		    				  if(!(redWither == null)) {
		    					  float health = redWither.getHealth();
		    					  int converted;
		    					  if(health > 0 && health < 1) {
		    						  converted = 1;
		    					  } else {
		    						  converted = (int) health;
		    					  }
		    					  blue.getScore(ChatColor.RED + "Wither Health:").setScore(converted);
		    				  }
		    			  }	else if(worldScoreboards.get(scoreboard).equals("Red")) {
		    				  Objective red = scoreboard.getObjective(DisplaySlot.SIDEBAR);
		    				  red.setDisplayName(ChatColor.RED + "Mega Walls " + handler.handleList.get(world).formatStringTime());
		    				  if(!(greenWither == null)) {
		    					  float health = greenWither.getHealth();
		    					  int converted;
		    					  if(health > 0 && health < 1) {
		    						  converted = 1;
		    					  } else {
		    						  converted = (int) health;
		    					  }
		    					  red.getScore(ChatColor.GREEN + "Wither Health:").setScore(converted);
		    				  }
		    				  if(!(yellowWither == null)) {
		    					  float health = yellowWither.getHealth();
		    					  int converted;
		    					  if(health > 0 && health < 1) {
		    						  converted = 1;
		    					  } else {
		    						  converted = (int) health;
		    					  }
		    					  red.getScore(ChatColor.YELLOW + "Wither Health:").setScore(converted);
		    				  }
		    				  if(!(blueWither == null)) {
		    					  float health = blueWither.getHealth();
		    					  int converted;
		    					  if(health > 0 && health < 1) {
		    						  converted = 1;
		    					  } else {
		    						  converted = (int) health;
		    					  }
		    					  red.getScore(ChatColor.BLUE + "Wither Health:").setScore(converted);
		    				  }
		    				  if(!(redWither == null)) {
		    					  float health = redWither.getHealth();
		    					  int converted;
		    					  if(health > 0 && health < 1) {
		    						  converted = 1;
		    					  } else {
		    						  converted = (int) health;
		    					  }
		    					  red.getScore(ChatColor.RED + "Wither Health:").setScore(converted);
		    				  }
		    			  }
	    			  } else {
	    				  
	    			  }
	    				  
	    		  }
	    		  
		  }	  
		  	  
     
	
}
