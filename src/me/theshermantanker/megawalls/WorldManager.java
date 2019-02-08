package me.theshermantanker.megawalls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class WorldManager {
	
	MegaWallsPlugin plugin = MegaWallsPlugin.plugin;
	ScoreboardManager manager = Bukkit.getScoreboardManager();
	Map<Sign, World> listedWorlds = new HashMap<Sign, World>();
	List<World> standbyWorlds = new ArrayList<World>();
	List<Sign> availableInterfaces = new ArrayList<Sign>();
	LogicHelper helper;
	
	public WorldManager(LogicHelper helper){
		
		this.helper = helper;
		
	}
	
	public void prepareWorld(World world){
			
			Scoreboard greenscoreboard = manager.getNewScoreboard();
			Scoreboard yellowscoreboard = manager.getNewScoreboard();
			Scoreboard bluescoreboard = manager.getNewScoreboard();
			Scoreboard redscoreboard = manager.getNewScoreboard();
			Team greenteam = greenscoreboard.registerNewTeam("GreenTeam");
			Team yellowteam = yellowscoreboard.registerNewTeam("YellowTeam");
			Team blueteam = bluescoreboard.registerNewTeam("BlueTeam");
			Team redteam = redscoreboard.registerNewTeam("RedTeam");
			Objective green = greenscoreboard.registerNewObjective("GreenScoreboard", "dummy");
			Objective yellow = yellowscoreboard.registerNewObjective("YellowScoreboard", "dummy");
			Objective blue = bluescoreboard.registerNewObjective("BlueScoreboard", "dummy");
			Objective red = redscoreboard.registerNewObjective("RedScoreboard", "dummy");
			greenteam.setAllowFriendlyFire(false);
			yellowteam.setAllowFriendlyFire(false);
			blueteam.setAllowFriendlyFire(false);
			redteam.setAllowFriendlyFire(false);
			green.setDisplayName(ChatColor.GREEN + "Mega Walls");
			yellow.setDisplayName(ChatColor.YELLOW + "Mega Walls");
			blue.setDisplayName(ChatColor.BLUE + "Mega Walls");
			red.setDisplayName(ChatColor.RED + "Mega Walls");
			green.setDisplaySlot(DisplaySlot.SIDEBAR);
			yellow.setDisplaySlot(DisplaySlot.SIDEBAR);
			blue.setDisplaySlot(DisplaySlot.SIDEBAR);
			red.setDisplaySlot(DisplaySlot.SIDEBAR);
			Map<Scoreboard, String> scoreboards = new HashMap<Scoreboard, String>();
			scoreboards.put(greenscoreboard, "Green");
			scoreboards.put(yellowscoreboard, "Yellow");
			scoreboards.put(bluescoreboard, "Blue");
			scoreboards.put(redscoreboard, "Red");
			helper.bindScoreboards(world, scoreboards);
			System.out.println(world.getName() + " has been fully set up!");
			
	}

}
