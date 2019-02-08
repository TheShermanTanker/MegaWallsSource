package me.theshermantanker.megawalls;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class EnhancedScheduler {
	
	MegaWallsPlugin plugin = MegaWallsPlugin.plugin;
	boolean timeEdited = false;
	boolean autoCancel;
	int time;
	BukkitTask runnable;
	
	public EnhancedScheduler(int time, boolean terminating){
		this.time = time;
		this.autoCancel = terminating;
	}
	
	public int getTime(){
		return time;
	}
	
	public void setTime(int Time){
		this.time = Time;
		timeEdited = true;
	}
	
	public void startTimer(){
		runnable = new BukkitRunnable(){
			int systemTime = time;
			
			public void run() {
				
				if(timeEdited){
					systemTime = time;
					timeEdited = false;
				}
				
				if(systemTime == 0){
					if(autoCancel){
						this.cancel();
					}
				}
				time = systemTime;
				systemTime--;
			}
			
		}.runTaskTimer(plugin, 0, 20);
	}
	
	public String formatStringTime(){
		int ReturnTime = getTime();
		int Minutes = ReturnTime / 60;
		int Seconds = ReturnTime % 60;
		String ConvertedMinutes;
		String ConvertedSeconds;
		
		if(Minutes < 10){
			ConvertedMinutes = String.format("%02d", Minutes);
		} else {
			ConvertedMinutes = String.format("%d", Minutes);
		}
		if(Seconds == 0){
			ConvertedSeconds = "00";
		} else if(Seconds > 0 && Seconds < 10){
			ConvertedSeconds = String.format("%02d", Seconds);
	    }  else {
			ConvertedSeconds = String.format("%d", Seconds);
		}
		
		return ConvertedMinutes + ":" + ConvertedSeconds;
	}
	
	public void stopTimer(){
		runnable.cancel();
	}

}
