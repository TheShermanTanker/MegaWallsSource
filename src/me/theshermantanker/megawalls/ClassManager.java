package me.theshermantanker.megawalls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClassManager implements Listener {
	
	List<World> list = new ArrayList<World>();
	Map<Player, MegaWallsClass> players = new HashMap<Player, MegaWallsClass>();
	
	public void newGame(World world) {
		if(!this.exists(world)) {
			list.add(world);
		}
	}
	
	public void unsetGame(World world) {
		if(this.exists(world)) {
			list.remove(world);
		}
	}
	
	public boolean exists(World world) {
		return list.contains(world);
	}
	
	public boolean registered(Player player) {
		
		return players.containsKey(player);
	}
	
	private void registerAllTraits(MegaWallsClass oclass) {
		Bukkit.getServer().getPluginManager().registerEvent(oclass.skillEvent, oclass, EventPriority.HIGH, new EventInvoker(1), MegaWallsPlugin.plugin, true);
		Bukkit.getServer().getPluginManager().registerEvent(oclass.passiveEvent, oclass, EventPriority.HIGH, new EventInvoker(2), MegaWallsPlugin.plugin, true);
		Bukkit.getServer().getPluginManager().registerEvent(oclass.gatherEvent, oclass, EventPriority.HIGH, new EventInvoker(3), MegaWallsPlugin.plugin, true);
	}
	
	public void registerPlayer(MegaWallsClass playerClass) {
		if(this.registered(playerClass.player)) return;
		this.registerAllTraits(playerClass);
		players.put(playerClass.player, playerClass);
		playerClass.setController(this);
	}
	
	public void damageEffect(Player player) {
		Location location = player.getLocation();
		location.setY(location.getY() + 1.2);
		player.getWorld().playEffect(location, Effect.STEP_SOUND, Material.REDSTONE_WIRE);
	}
	
	public void leftClick(Player source, Player other) {
		MegaWallsClass oclass = players.get(source);
		int energyGain = oclass.energyGainRate;
		if(oclass.abilityCharge == 0) {
			oclass.gainEnergy(energyGain);
		}
		this.damageEffect(other);
	}
	
	@EventHandler
	public void onAbility(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		if(!this.isGame(player)) return;		
		MegaWallsClass playerClass = players.get(player);
				
		
	}
	
	public boolean isGame(Player player) {
		return list.contains(player.getWorld());
	}
		
		
	
}
