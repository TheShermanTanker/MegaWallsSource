package me.theshermantanker.megawalls;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.theshermantanker.withercraft.WitherCraft;

public class MegaWallsPlugin extends JavaPlugin implements Listener {
	
	LogicHelper helper = new LogicHelper();	
	public Map<World, Boolean> worlds = new HashMap<World, Boolean>();
	public WitherCraft withercraft = (WitherCraft) Bukkit.getPluginManager().getPlugin("WitherCraft");
	public static MegaWallsPlugin plugin;
	public File playerstatsYml = new File(getDataFolder() + "/playerstatistics.yml");
	public FileConfiguration playerstatsConfig = YamlConfiguration.loadConfiguration(playerstatsYml);
	public File playerclassesYml = new File(getDataFolder() + "/playerselectedclass.yml");
	public FileConfiguration playerclassesConfig = YamlConfiguration.loadConfiguration(playerclassesYml);
	public File gamedata = new File(getDataFolder() + "/gamedata.yml");
	public FileConfiguration dataconfiguaration = YamlConfiguration.loadConfiguration(gamedata);
	WorldManager manager;
	GameStart start;
	GameHandler handler;
	PacketInterceptor interceptor;
	JoinSigns joinSigns;
	GameProtection protection;
	
	public String getWorldName(Player player){
		
		
		return player.getWorld().getName();
	}
	
	public void saveCustomYml(FileConfiguration ymlConfig, File ymlFile) {
		 try {
		      ymlConfig.save(ymlFile);
		 } catch (IOException exception) {
		      exception.printStackTrace();
		    }
		 }
	
	public void onEnable(){
		
		saveCustomYml(playerstatsConfig, playerstatsYml);
		saveCustomYml(playerclassesConfig, playerclassesYml);
		saveCustomYml(dataconfiguaration, gamedata);
		MegaWallsPlugin.plugin = this;
		World gameWorld = Bukkit.createWorld(new WorldCreator("Frozen"));
		World gameWorld2 = Bukkit.createWorld(new WorldCreator("Kingdom"));
		World gameWorld3 = Bukkit.createWorld(new WorldCreator("Egypt"));
		World gameWorld4 = Bukkit.createWorld(new WorldCreator("Dragonkeep"));
		World gameWorld5 = Bukkit.createWorld(new WorldCreator("Forsaken"));
		withercraft.knownWorlds.put(gameWorld, "Gameworld");
		withercraft.knownWorlds.put(gameWorld2, "Gameworld");
		withercraft.knownWorlds.put(gameWorld3, "Gameworld");
		withercraft.knownWorlds.put(gameWorld4, "Gameworld");
		withercraft.knownWorlds.put(gameWorld5, "Gameworld");
		worlds.put(gameWorld4, false);
		worlds.put(gameWorld5, false);
		gameWorld.setAutoSave(false);
		gameWorld2.setAutoSave(false);
		gameWorld3.setAutoSave(false);
		gameWorld4.setAutoSave(false);
		gameWorld5.setAutoSave(false);
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getPluginManager().registerEvents(joinSigns = new JoinSigns(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new LobbyProtection(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new GameStart(), this);
		Bukkit.getServer().getPluginManager().registerEvents(protection = new GameProtection(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new TeamHandler(), this);
		//Bukkit.getServer().getPluginManager().registerEvents(new GameChat(), this);
		manager = new WorldManager(helper);
		manager.prepareWorld(gameWorld4);
		manager.prepareWorld(gameWorld5);
		start = new GameStart();
		handler = start.gameHandler;
		Bukkit.getServer().getPluginManager().registerEvents(handler, this);
		interceptor = new PacketInterceptor(this);
		
	}
	
	public void onLoad(){
		
		registerCustomEntity(EntityGreenWither.class, "GreenWither", 64);
		registerCustomEntity(EntityYellowWither.class, "YellowWither", 64);
		registerCustomEntity(EntityBlueWither.class, "BlueWither", 64);
		registerCustomEntity(EntityRedWither.class, "RedWither", 64);
		registerCustomEntity(EntityCustomWither.class, "CustomWither", 64);
		registerCustomEntity(EntityCustomWitherSkull.class, "CustomWitherSkull", 19);
		
	}
	
	protected static Field mapStringToClassField, mapClassToStringField, mapClassToIdField, mapStringToIdField;
	 
	static {
	    try {
	        mapStringToClassField = net.minecraft.server.v1_7_R4.EntityTypes.class.getDeclaredField("c");
	        mapClassToStringField = net.minecraft.server.v1_7_R4.EntityTypes.class.getDeclaredField("d");
	        mapClassToIdField = net.minecraft.server.v1_7_R4.EntityTypes.class.getDeclaredField("f");
	        mapStringToIdField = net.minecraft.server.v1_7_R4.EntityTypes.class.getDeclaredField("g");
	 
	        mapStringToClassField.setAccessible(true);
	        mapClassToStringField.setAccessible(true);
	        mapClassToIdField.setAccessible(true);
	        mapStringToIdField.setAccessible(true);
	        
	    } catch(Exception exception) {
	    	
	    	exception.printStackTrace();
	    	
	    }
	    
	}
	 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static void registerCustomEntity(Class entityClass, String name, int id){
	    if (mapStringToClassField == null || mapStringToIdField == null || mapClassToStringField == null || mapClassToIdField == null){
	        return;
	    } else {
	        try {
	            Map mapStringToClass = (Map) mapStringToClassField.get(null);
	            Map mapStringToId = (Map) mapStringToIdField.get(null);
	            Map mapClasstoString = (Map) mapClassToStringField.get(null);
	            Map mapClassToId = (Map) mapClassToIdField.get(null);
	 
	            mapStringToClass.put(name, entityClass);
	            mapStringToId.put(name, Integer.valueOf(id));
	            mapClasstoString.put(entityClass, name);
	            mapClassToId.put(entityClass, Integer.valueOf(id));
	 
	            mapStringToClassField.set(null, mapStringToClass);
	            mapStringToIdField.set(null, mapStringToId);
	            mapClassToStringField.set(null, mapClasstoString);
	            mapClassToIdField.set(null, mapClassToId);
	            
	        } catch (Exception exception){
	        	
	            exception.printStackTrace();
	            
	        }
	    }
	}

}
