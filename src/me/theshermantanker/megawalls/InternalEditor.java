package me.theshermantanker.megawalls;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.RegistryBlocks;
import net.minecraft.server.v1_7_R4.RegistryID;

public class InternalEditor {
	
	MegaWallsPlugin plugin;
	RegistryBlocks blocksRegistered;
	RegistryID blockIdentificationCode;
	Map blockMap;
	
	public InternalEditor(MegaWallsPlugin plugin) {
		this.plugin = plugin;
		blocksRegistered = (RegistryBlocks) Block.REGISTRY;
		try {
			Field field = blocksRegistered.getClass().getDeclaredField("a");
			field.setAccessible(true);
			blockIdentificationCode = (RegistryID) field.get(blocksRegistered);
		} catch (NoSuchFieldException exception) {			
			exception.printStackTrace();
		} catch (SecurityException exception) {
			exception.printStackTrace();
		} catch (IllegalArgumentException exception) {
			exception.printStackTrace();
		} catch (IllegalAccessException exception) {
			exception.printStackTrace();
		}
	}
	
	public void registerBlock(String rawName) {
		
	}
	
	public void unregisterBlock(String rawName) {
		try {
			Method method = blocksRegistered.getClass().getDeclaredMethod("c", String.class);
			Object object = method.invoke(blocksRegistered, rawName);
			if(blocksRegistered.d(object)) {
				blockMap.remove(object);
			}
		} catch (NoSuchMethodException exception) {
			exception.printStackTrace();
		} catch (SecurityException exception) {
			exception.printStackTrace();
		} catch (IllegalAccessException exception) {
			exception.printStackTrace();
		} catch (IllegalArgumentException exception) {
			exception.printStackTrace();
		} catch (InvocationTargetException exception) {
			exception.printStackTrace();
		}
	}

}
