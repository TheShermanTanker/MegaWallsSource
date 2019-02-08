package me.theshermantanker.megawalls;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class DatabaseHandler {
	
	MegaWallsPlugin instance = MegaWallsPlugin.plugin;
	FileConfiguration gamedata = instance.dataconfiguaration;
	FileConfiguration playerclasses = instance.playerclassesConfig;
	FileConfiguration playerstatistics = instance.playerstatsConfig;
	
	public WorldDataHolder getWorldData(String worldName){
		
		if(gamedata.getBoolean("Gameworlds." + worldName + ".Registered")){
			
			World world = Bukkit.getWorld(worldName);
			Location greenSpawn = new Location(world, gamedata.getInt("Gameworlds." + worldName + ".Spawnpoints.Green.X"), gamedata.getInt("Gameworlds." + worldName + ".Spawnpoints.Green.Y"), gamedata.getInt("Gameworlds." + worldName + ".Spawnpoints.Green.Z"));
			Location yellowSpawn = new Location(world, gamedata.getInt("Gameworlds." + worldName + ".Spawnpoints.Yellow.X"), gamedata.getInt("Gameworlds." + worldName + ".Spawnpoints.Yellow.Y"), gamedata.getInt("Gameworlds." + worldName + ".Spawnpoints.Yellow.Z"));
			Location blueSpawn = new Location(world, gamedata.getInt("Gameworlds." + worldName + ".Spawnpoints.Blue.X"), gamedata.getInt("Gameworlds." + worldName + ".Spawnpoints.Blue.Y"), gamedata.getInt("Gameworlds." + worldName + ".Spawnpoints.Blue.Z"));
			Location redSpawn = new Location(world, gamedata.getInt("Gameworlds." + worldName + ".Spawnpoints.Red.X"), gamedata.getInt("Gameworlds." + worldName + ".Spawnpoints.Red.Y"), gamedata.getInt("Gameworlds." + worldName + ".Spawnpoints.Red.Z"));
			Location lobby = new Location(world, gamedata.getInt("Gameworlds." + worldName + ".Spawnpoints.Lobby.X"), gamedata.getInt("Gameworlds." + worldName + ".Spawnpoints.Lobby.Y"), gamedata.getInt("Gameworlds." + worldName + ".Spawnpoints.Lobby.Z"));
			Location greenWither = new Location(world, gamedata.getInt("Gameworlds." + worldName + ".Withers.Green.X"), gamedata.getInt("Gameworlds." + worldName + ".Withers.Green.Y"), gamedata.getInt("Gameworlds." + worldName + ".Withers.Green.Z"));
			Location yellowWither = new Location(world, gamedata.getInt("Gameworlds." + worldName + ".Withers.Yellow.X"), gamedata.getInt("Gameworlds." + worldName + ".Withers.Yellow.Y"), gamedata.getInt("Gameworlds." + worldName + ".Withers.Yellow.Z"));
			Location blueWither = new Location(world, gamedata.getInt("Gameworlds." + worldName + ".Withers.Blue.X"), gamedata.getInt("Gameworlds." + worldName + ".Withers.Blue.Y"), gamedata.getInt("Gameworlds." + worldName + ".Withers.Blue.Z"));
			Location redWither = new Location(world, gamedata.getInt("Gameworlds." + worldName + ".Withers.Red.X"), gamedata.getInt("Gameworlds." + worldName + ".Withers.Red.Y"), gamedata.getInt("Gameworlds." + worldName + ".Withers.Red.Z"));
			
			return new WorldDataHolder(lobby, greenSpawn, yellowSpawn, blueSpawn, redSpawn, greenWither, yellowWither, blueWither, redWither);
		} else {
			
			System.out.println("Unable to locate World Data for World " + worldName);
			return null;
		}
		
	}

}
