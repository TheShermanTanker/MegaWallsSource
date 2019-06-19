package me.theshermantanker.megawalls;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public abstract class MegaWallsClass implements Listener {
	
	int[] abilityLevels;
	int[] skillLevels;
	int[] passiveLevels;
	Map<Integer, List<ItemStack>> kitLevels;
	int[] gatheringLevels;
	int[] currentLevels; //0 = Ability, 1 = Skill, 2 = Passive, 3 = Kit, 4 = Gather
	int abilityCharge; //0 - Energy per Click, 1 - Energy per Bow Hit
	int energyGainRate;
	boolean abilitySwordActivated; //Set to false if skill is activated by left clicking bow
	
	Player player;
	MegaWallsPlugin plugin = MegaWallsPlugin.plugin;
	
	public MegaWallsClass(Player player) {
		this.player = player;
		
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if(player == this.player) {
			List<ItemStack> targetKit = kitLevels.get(currentLevels[3]);
			//Handles some personal events
		}
	}
	
	public void gainEnergy() {
		
		int energy = this.energyGainRate;
		
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
	
	public abstract void activateAbility();
	
	public abstract void startSkill();
	
	public abstract void startPassive();
	
	public abstract void initiateKitItems();
	
	public abstract void triggerGather();
	
	public abstract String getClassName();
	
}
