package me.theshermantanker.megawalls;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.scoreboard.Scoreboard;

public class LogicHelper {
	
	MegaWallsPlugin plugin = MegaWallsPlugin.plugin;
	
	public Map<World, Map<Scoreboard, String>> scoreboards = new HashMap<World, Map<Scoreboard, String>>();
	
	public void bindScoreboards(World world, Map<Scoreboard, String> scoreboards){
		this.scoreboards.put(world, scoreboards);
	}
	
	public void clearAllScoreboards(){
		scoreboards.clear();
	}
	
	public void removeScoreboards(World world, Map<Scoreboard, String> scoreboard){
		scoreboards.remove(world, scoreboard);
	}

}
