package me.theshermantanker.megawalls;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_7_R4.EntityPlayer;

public class EntityWitherSkillRunnable extends BukkitRunnable {
	
	EntityCustomWither wither;
	int seconds = 7;
	int skill;
	
	public EntityWitherSkillRunnable(EntityCustomWither wither, int i) {
		this.skill = i;
		this.wither = wither;
		this.runTaskTimer(MegaWallsPlugin.plugin, 0L, 20L);
		List enemies = wither.world.a(EntityPlayer.class, wither.boundingBox.grow(40.0D, 4.0D, 40.0D), new EntitySelectorEnemyPlayers(wither, true));
		String name = ChatColor.RED + ChatColor.stripColor(wither.getCustomName().substring(0, wither.getCustomName().indexOf(' ')));
		for(Object target : enemies){
        	if(target instanceof EntityPlayer){
        		EntityPlayer enemy = (EntityPlayer) target;
        		CraftPlayer player = enemy.getBukkitEntity();
        		if(i == 0) {
        			player.sendMessage(ChatColor.RED + "The " + name + " Wither is charging his lightning skill!");
        		} else if(i == 1) {
        			player.sendMessage(ChatColor.RED + "The " + name + " Wither is charging his inferno skill!");
        		} else if(i == 2) {
        			player.sendMessage(ChatColor.RED + "The " + name + " Wither is charging his earthquake skill!");
        		} else if(i == 3) {
        			player.sendMessage(ChatColor.RED + "The " + name + " Wither is charging his vacuum skill!");
        		} else if(i == 4) {
        			player.sendMessage(ChatColor.RED + "The " + name + " Wither is charging his knockback skill!");
        		}
        	}
		}	
		
	}
	
	private void destroy() {
		this.cancel();
		wither.runnable = null;
	}
	
	@Override
	public void run() {
		if(wither.dead) this.destroy();
		if(!wither.isEnraged) this.destroy();
		if(seconds == 0) {
			List enemies = wither.world.a(EntityPlayer.class, wither.boundingBox.grow(40.0D, 4.0D, 40.0D), new EntitySelectorEnemyPlayers(wither, true));
	       	for(Object target : enemies){
	        	if(target instanceof EntityPlayer){
	        		EntityPlayer enemy = (EntityPlayer) target;
	        		CraftPlayer player = enemy.getBukkitEntity();
	        		
	        		if(skill == 0) {
	        			wither.world.getWorld().strikeLightningEffect(player.getLocation());
	        			player.damage(14, wither.getBukkitEntity());
	        			this.destroy();
	        		} else if(skill == 1) {
	        			wither.world.getWorld().strikeLightning(player.getLocation());
	        			this.destroy();
	        		} else if(skill == 2) {
	        			player.damage(6, wither.getBukkitEntity());
	        			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));
	        			this.destroy();
	        		} else if(skill == 3) {
	        			player.damage(10, wither.getBukkitEntity());
	        			Vector vector = wither.getBukkitEntity().getLocation().toVector();
	        			Vector targeted = player.getLocation().toVector();
	        			double x = targeted.getX() - vector.getX();
	        			double z = targeted.getZ() - vector.getZ();
	        			player.setVelocity(new Vector(x, -1.0D, z).normalize().multiply(7.5D));
	        			this.destroy();
	        		} else if(skill == 4) {
	        			player.damage(6, wither.getBukkitEntity());
	        			player.setVelocity(new Vector(0.0, 2.5, 0.0));
	        			this.destroy();
	        		}
	        	}
	        	
	    	}
		} else {
			seconds--;
		}
	}
	
}
