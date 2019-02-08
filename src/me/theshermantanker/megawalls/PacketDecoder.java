package me.theshermantanker.megawalls;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.EnumEntityUseAction;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayInUseEntity;
import net.minecraft.server.v1_7_R4.World;
import net.minecraft.server.v1_7_R4.WorldServer;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.handler.codec.MessageToMessageDecoder;

public class PacketDecoder extends MessageToMessageDecoder<Packet> {
	
	EntityPlayer entityPlayer;
	PacketInterceptor interceptor;
	
	public PacketDecoder(EntityPlayer entityPlayer, PacketInterceptor interceptor) {
		this.entityPlayer = entityPlayer;
		this.interceptor = interceptor;
	}
	
	@Override
	protected void decode(ChannelHandlerContext context, Packet packet, List<Object> output) throws Exception {
		if(packet instanceof PacketPlayInUseEntity) {
			PacketPlayInUseEntity entityPacket = (PacketPlayInUseEntity) packet;
			WorldServer worldserver = MinecraftServer.getServer().getWorldServer(entityPlayer.dimension);
		    Entity entity = entityPacket.a((World) worldserver);
		    if(entityPacket.c() == EnumEntityUseAction.ATTACK) {
				if(entity instanceof EntityPlayer) {
					EntityPlayer potentialEnemy = (EntityPlayer) entity;
					Player craftPlayer = entityPlayer.getBukkitEntity();
					Player craftEnemy = potentialEnemy.getBukkitEntity();
						
					Bukkit.getServer().getPluginManager().callEvent(new PlayerClickEvent(craftPlayer, craftEnemy));
				}
			}		    
			
		}
		output.add(packet);
	}

}
