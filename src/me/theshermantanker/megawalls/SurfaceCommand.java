package me.theshermantanker.megawalls;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import me.theshermantanker.withercraft.WitherCraft;

public class SurfaceCommand extends BukkitCommand {

	protected SurfaceCommand(String name) {
		super(name);
		this.description = "Surface Command for Mega Walls";
		this.usageMessage = "/surface";
		
	}

	@Override
	public boolean execute(CommandSender sender, String arg1, String[] args) {
		
		MegaWallsPlugin plugin = MegaWallsPlugin.plugin;
		WitherCraft withercraft = plugin.withercraft;
		GameStart start = plugin.start;
		GameHandler handler = start.gameHandler;
		
		if(sender instanceof Player){
			Player player = (Player) sender;
			String uuid = player.getUniqueId().toString();	
			if(!handler.handleList.containsKey(player.getWorld())){
				return false;
		    }else if(withercraft.customConfig.getString(uuid + ".Ranks").equals("Default")){
				player.sendMessage(ChatColor.RED + "You must be VIP or higher to use the /surface Command!");
				return false;
			} else if(!(handler.gamePhase.get(player.getWorld()) == 1)){
			    player.sendMessage(ChatColor.RED + "You can only use /surface before the game starts, and the walls are down!");
			    return false;
			}  else {
			
				int y = player.getWorld().getHighestBlockYAt(player.getLocation());
				Location location = new Location(player.getWorld(), player.getLocation().getBlockX(), y + 1, player.getLocation().getBlockZ());
				player.sendMessage(ChatColor.GREEN + "Teleported to the surface!");
				player.teleport(location);
				return true;
			}
			
		}
		
		return false;
	}

}
