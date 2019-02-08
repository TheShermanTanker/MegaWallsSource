package me.theshermantanker.megawalls;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.scoreboard.Team;

import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.PathfinderGoalInteract;
import net.minecraft.server.v1_7_R4.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_7_R4.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_7_R4.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_7_R4.World;

public class EntityBlueWither extends EntityCustomWither{
	
    public EntityBlueWither(World world, Location spawnpoint, Team team) {
		super(world);
        this.setCustomName(ChatColor.BLUE + "Blue Wither");
        this.setCustomNameVisible(true);
        
        this.allies = team;
        this.targetSelector.a(5, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, false, false, new EntitySelectorEnemyPlayers(this, true)));
        this.goalSelector.a(10, new PathfinderGoalInteract(this, EntityHuman.class, 5.0F, 2.0F));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 8.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
        this.goalSelector.a(9, new PathfinderGoalStayAtLocation(this, spawnpoint));
	}
    
    public void setEnraged(boolean enraged){
    	
    	if(enraged){
    	this.setCustomName(this.getCustomName() + " [ENRAGED]");
    	this.setCustomNameVisible(true);
    	} else {
    		this.setCustomName(ChatColor.BLUE + "Blue Wither");
    		this.setCustomNameVisible(true);
    	}
    	this.isEnraged = enraged;
    	
    }

}
