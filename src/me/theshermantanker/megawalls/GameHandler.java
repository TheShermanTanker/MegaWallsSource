package me.theshermantanker.megawalls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import me.theshermantanker.withercraft.WitherCraft;

public class GameHandler implements Listener {
	
	MegaWallsPlugin plugin = MegaWallsPlugin.plugin;
	LogicHelper helper = plugin.helper;
	WitherCraft withercraft = plugin.withercraft;
	Map<World, EnhancedScheduler> handleList = new HashMap<World, EnhancedScheduler>();
	Map<World, Integer> gamePhase = new HashMap<World, Integer>();
	Map<World, GameUpdater> gameLoops = new HashMap<World, GameUpdater>();
	ScoreboardManager manager = Bukkit.getScoreboardManager();
	
	public void registerGame(World world, EnhancedScheduler scheduler){
		boolean valid = plugin.worlds.get(world);
		
		if(valid){
			handleList.put(world, scheduler);
		}
		
	}
	
	public void tickGame(World world, List<Team> teams) {
		gamePhase.put(world, 1);
		
		GameUpdater runnable = null;
		try {
			runnable = new GameUpdater(teams, world, helper, this);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		if(runnable == null) {
			System.out.println("[CRITICAL] Unable to start game " + world.getName() + "!");
			return;
		}
		 runnable.runTaskTimer(plugin, 0, 1);
		 gameLoops.put(world, runnable);
			
	}
	
	public void stopGame(World world) {
		gameLoops.get(world).cancel();
	}
	
	public void stopHandling(World world){
		handleList.remove(world);
		this.stopGame(world);
	}

}
