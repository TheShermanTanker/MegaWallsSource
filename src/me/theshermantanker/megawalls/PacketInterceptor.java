package me.theshermantanker.megawalls;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.util.io.netty.channel.Channel;

public class PacketInterceptor implements Listener {
	
	MegaWallsPlugin plugin;
	Field actionField;
	Field channelField;
	
	public PacketInterceptor(MegaWallsPlugin plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
		for(Field field : NetworkManager.class.getDeclaredFields()) {
			if (field.getType().isAssignableFrom(Channel.class)) {
                channelField = field;
                channelField.setAccessible(true);
                System.out.println("Channel Pathway successfully set");
                break;
            }
		}
	}
	
	@EventHandler
	public void onConnect(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		try {
			EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
			Channel channel = (Channel) channelField.get(entityPlayer.playerConnection.networkManager);
			if(channel != null) {
				System.out.println("Registered player " + player.getName());
				channel.pipeline().addAfter("decoder", "LEFT_CLICK_DECODER", new PacketDecoder(entityPlayer, this));
			}
		} catch(Exception exception) {
			System.out.println("Unable to set Interact Manager for player " + player.getName());
			System.out.println(exception.getMessage());
			exception.printStackTrace();
		}
	}
	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		try {
			Channel channel = (Channel) channelField.get(((CraftPlayer) player).getHandle().playerConnection.networkManager);
			if(channel != null) {
				if(channel.pipeline().get("LEFT_CLICK_DECODER") != null) {
					channel.pipeline().remove("LEFT_CLICK_DECODER");
					System.out.println("Successfully removed Interact Manager for " + player.getName());
				}
			}
		} catch(Exception exception) {
			System.out.println("An Error occured while disabling Interact Manager for " + player.getName());
			exception.printStackTrace();
		}
	}

}
