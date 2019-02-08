package me.theshermantanker.megawalls;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ImmuneWitherEasterEgg implements Listener{
	
	@EventHandler
	public void onDeath(EntityDeathEvent event){
		if(event.getEntity() instanceof Wither){
			Wither wither = (Wither) event.getEntity();
			Player player = Bukkit.getPlayer(UUID.fromString("a65807b1-07ba-4cf5-9c75-64404a55f78f"));
			if(wither.getCustomName().equalsIgnoreCase(player.getName())){
				String x = ChatColor.RED + "You shall not insult my master!";
				System.out.println(x);
				int count = Bukkit.getServer().broadcastMessage(x);
				System.out.println("There are " + count + "message recipients");
			}
		}
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event){
		Player player = event.getPlayer();
		if(player.getUniqueId().toString().equalsIgnoreCase("a65807b1-07ba-4cf5-9c75-64404a55f78f")){
			if(event.getMessage().contains("summon")){
				for(Entity entities : player.getNearbyEntities(2, 2, 2)){
					if(entities instanceof LivingEntity){
						LivingEntity alive = (LivingEntity) entities;
						player.sendMessage(ChatColor.GREEN + "Successfully created a new beautiful entity, master");
						alive.setCustomNameVisible(true);
						alive.setCustomName(ChatColor.GREEN + player.getName());
					}
				}
			}
		}
	}

}
