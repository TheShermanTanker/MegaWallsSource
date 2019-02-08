package me.theshermantanker.megawalls;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_7_R4.ItemAxe;
import net.minecraft.server.v1_7_R4.ItemPickaxe;
import net.minecraft.server.v1_7_R4.ItemSpade;
import net.minecraft.server.v1_7_R4.ItemSword;
import net.minecraft.server.v1_7_R4.ItemTool;

public class BasicItemData {
	
	public static ItemStack LOCATION_COMPASS = new ItemStack(Material.COMPASS);
	public static ItemStack KIT_STEAK = new ItemStack(Material.COOKED_BEEF);
	
	public static String baseItemType(ItemStack stack) {
		net.minecraft.server.v1_7_R4.ItemStack minecraftStack = CraftItemStack.asNMSCopy(stack);
		net.minecraft.server.v1_7_R4.Item item = minecraftStack.getItem();
		if(item instanceof ItemSword) {
			return "sword";
		}
		if(item instanceof ItemTool) {
			if(item instanceof ItemPickaxe) {
				return "pickaxe";
			} else if(item instanceof ItemAxe) {
				return "axe";
			} else if(item instanceof ItemSpade) {
				return "shovel";
			}
		}
		return "NULL";
	}
	
	public static ItemStack pickaxeLevel(MegaWallsClass oclass, String rank) {
		ItemStack pickaxe; 
		
		if(rank.equalsIgnoreCase("Default")) {
			pickaxe = new ItemStack(Material.STONE_PICKAXE);
		} else {
		    pickaxe = new ItemStack(Material.IRON_PICKAXE);
		    if(rank.equalsIgnoreCase("VIP+")) {
		    	pickaxe.addEnchantment(Enchantment.DIG_SPEED, 1);
		    }
    	}
		
		if(rank.equalsIgnoreCase("MVP")) {
			pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
			pickaxe.addEnchantment(Enchantment.DIG_SPEED, 1);
		}
		
		if(rank.equalsIgnoreCase("MVP+")) {
			pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
			pickaxe.addEnchantment(Enchantment.DIG_SPEED, 2);
		}
		
		ItemMeta meta = pickaxe.getItemMeta();
		switch(rank) {
		case "Default":
			meta.setDisplayName(ChatColor.GREEN + oclass.getClassName() + " Pickaxe");
			break;
		case "VIP":
			meta.setDisplayName(ChatColor.GREEN + "[VIP] " + oclass.getClassName() + " Pickaxe");
			break;	
		case "VIP+":
			meta.setDisplayName(ChatColor.GREEN + "[VIP" + ChatColor.GOLD + "+" + ChatColor.GREEN + "] " + oclass.getClassName() + " Pickaxe");
			break;	
		case "MVP":
			meta.setDisplayName(ChatColor.AQUA + "[MVP] " + oclass.getClassName() + " Pickaxe");
			break;	
		case "MVP+":
			meta.setDisplayName(ChatColor.AQUA + "[MVP" + ChatColor.GOLD + "+" + ChatColor.AQUA + "] " + oclass.getClassName() + " Pickaxe");
			break;		
		}
		
		pickaxe.setItemMeta(meta);
		
		return pickaxe;
	}	
}
