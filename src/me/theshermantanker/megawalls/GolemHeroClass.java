package me.theshermantanker.megawalls;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.PlayerDeathEvent;

public class GolemHeroClass extends MegaWallsClass {
	
	public GolemHeroClass(Player player) {
		super(player);
		this.abilityCharge = 0;
		this.energyGainRate = 4;
		this.abilitySwordActivated = true;
		this.skillEvent = PlayerDeathEvent.class;
		this.passiveEvent = PlayerDeathEvent.class;
		this.gatherEvent = PlayerDeathEvent.class;
	}

	@Override
	public String getClassName() {
		return "Golem";
	}

	@Override
	public void startSkill(Event event) {
		player.sendMessage("Skill triggered");
	}

	@Override
	public void initiateKitItems() {
		
	}

	@Override
	public void triggerGather(Event event) {
		player.sendMessage("Gather triggered");
	}

	@Override
	public void activateAbility() {
		
	}

	@Override
	public void startPassive(Event event) {
		player.sendMessage("Passive triggered");
	}

}
