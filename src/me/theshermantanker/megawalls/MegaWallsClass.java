package me.theshermantanker.megawalls;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class MegaWallsClass implements Listener {
	
	ClassManager manager;
	int[] abilityLevels;
	int[] skillLevels;
	int[] passiveLevels;
	Map<Integer, List<ItemStack>> kitLevels;
	int[] gatheringLevels;
	int abilityCharge; //0 - Energy per Click, 1 - Energy per Bow Hit
	int energyGainRate;
	boolean abilitySwordActivated; //Set to false if skill is activated by left clicking bow
	Class<? extends Event> skillEvent;
	Class<? extends Event> passiveEvent;
	Class<? extends Event> gatherEvent;
	
	Player player;
	MegaWallsPlugin plugin = MegaWallsPlugin.plugin;
	
	public MegaWallsClass(Player player) {
		this.player = player;
		
	}
	
	public void gainEnergy(int energy) {
		if(energy >= 100 || energy <= 0) return;
		if(player.getLevel() >= 100) {
			player.setLevel(100);
			return;
		} else {
			if(player.getLevel() + energy > 100) {
				energy = 100 - player.getLevel();
			}
			player.giveExpLevels(energy);
			float progress = player.getExp() + energy/100;
			if(progress >= player.getExpToLevel()) {
				player.setExp(0.99f);
			} else {
				player.setExp(progress);
			}
		}
	}
	
	public void setController(ClassManager manager) {
		this.manager = manager;
	}
	
	public abstract void activateAbility();
	
	public abstract void startSkill(Event event);
	
	public abstract void startPassive(Event event);
	
	public abstract void initiateKitItems();
	
	public abstract void triggerGather(Event event);
	
	public abstract String getClassName();
	
}
