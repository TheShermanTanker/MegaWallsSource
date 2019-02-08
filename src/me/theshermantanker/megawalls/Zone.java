package me.theshermantanker.megawalls;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class Zone implements Listener {
	
	public Zone(Location location, Location corner, boolean isPluginTargetable) {
		this.plugin = isPluginTargetable;
		this.setZone(location, corner);
		Bukkit.getServer().getPluginManager().registerEvents(this, MegaWallsPlugin.plugin);
	}
	
	List<Block> list = new ArrayList<Block>();
	boolean setProtected = false;
	protected final boolean plugin;
	
	public boolean contains(Block block) {
		return list.contains(block);
	}
	
    public boolean within(Entity entity) {
		Location location = entity.getLocation();
		return this.contains(entity.getWorld().getBlockAt(location));
	}
	
	public void setZone(Location location, Location corner) {
		if(location.getWorld() != corner.getWorld()) throw new IllegalArgumentException("You cannot set Vectors that san across more than one World!");
		World world = location.getWorld();
		double ax, ay, az;
		double bx, by, bz;
		ax = location.getX();
		ay = location.getY();
		az = location.getZ();
		bx = corner.getX();
		by = corner.getY();
		bz = corner.getZ();
		
		double x, y, z, mx, my, mz;
		x = Math.min(ax, bx);
		y = Math.min(ay, by);
		z = Math.min(az, bz);
		mx = Math.max(ax, bx);
		my = Math.max(ay, by);
		mz = Math.max(az, bz);
		
		for(double dx = x; dx <= mx; dx++) {
			for(double dy = y; dy <= my; dy++) {
				for(double dz = z; dz <= mz; dz++) {
					Block block = world.getBlockAt(new Location(world, dx, dy, dz));
					list.add(block);
				}
			}
		}
	}
	
	@EventHandler
	public void onDestroy(BlockBreakEvent event) {
		Block block = event.getBlock();
		
		if(list.contains(block)) {
			event.setCancelled(this.setProtected);
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Block block = event.getBlockReplacedState().getBlock();
		
		if(list.contains(block)) {
			event.setCancelled(this.setProtected);
		}
	}
	
	public void clear() throws Exception {
		if(plugin) throw new Exception("Plugins are not allowed to destroy invisible zones!");
		if(this.setProtected) {
			System.out.println("Plugin " + MegaWallsPlugin.plugin.getName() + " tried to destroy a protected area");
			return;
		}
		for(Block block : list) {
			block.setType(Material.AIR);
		}
	}
	
}
