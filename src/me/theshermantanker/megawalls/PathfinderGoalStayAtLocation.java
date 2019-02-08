package me.theshermantanker.megawalls;

import org.bukkit.Location;

import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.PathfinderGoal;

public class PathfinderGoalStayAtLocation extends PathfinderGoal{
	
	private EntityInsentient entity;
	private Location location;

	public PathfinderGoalStayAtLocation(EntityInsentient entity, Location location){
		
		this.entity = entity;
		this.location = location;
		
	}
	
	public boolean a(){
		
		if(location.distanceSquared(entity.getBukkitEntity().getLocation()) > 25){
			
			return true;
		} else {
			
			return false;
		}
		
	}
	
	public void c(){
		
		entity.setPosition(location.getX(), location.getY(), location.getZ());
		
	}

}
