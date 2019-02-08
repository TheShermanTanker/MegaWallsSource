package me.theshermantanker.megawalls;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.*;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.IEntitySelector;

public class EntitySelectorEnemyPlayers implements IEntitySelector{
	
	private EntityCustomWither wither;
	private boolean targetGroup;
	
	public EntitySelectorEnemyPlayers(EntityCustomWither entity, boolean targetGroup){
		
		this.wither = entity;
		this.targetGroup = targetGroup;
		
	}
	
	public boolean a(Entity entity) {
		Player player = null;
		
		CraftEntity craftentity = entity.getBukkitEntity();
		if(craftentity instanceof CraftPlayer){
			player = (Player) craftentity;
		}
		
		if(!targetGroup){
			return wither.allies.hasPlayer(player);
		}
		
		return !wither.allies.hasPlayer(player);
	}

}
