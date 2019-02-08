package me.theshermantanker.megawalls;

import org.bukkit.Location;

public class WorldDataHolder {
	
	Location greenSpawn;
	Location yellowSpawn;
	Location blueSpawn;
	Location redSpawn;
	Location greenWitherSpawn;
	Location yellowWitherSpawn;
	Location blueWitherSpawn;
	Location redWitherSpawn;
	Location lobby;
	
	public WorldDataHolder(Location lobby, Location greenSpawn, Location yellowSpawn, Location blueSpawn, Location redSpawn, Location greenWitherSpawn, Location yellowWitherSpawn, Location blueWitherSpawn, Location redWitherSpawn){
		
		this.greenSpawn = greenSpawn;
		this.yellowSpawn = yellowSpawn;
		this.blueSpawn = blueSpawn;
		this.redSpawn = redSpawn;
		this.greenWitherSpawn = greenWitherSpawn;
		this.yellowWitherSpawn = yellowWitherSpawn;
		this.blueWitherSpawn = blueWitherSpawn;
		this.redWitherSpawn = redWitherSpawn;
		this.lobby = lobby;
		
	}

}
