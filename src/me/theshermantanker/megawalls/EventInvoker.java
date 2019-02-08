package me.theshermantanker.megawalls;

import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

public class EventInvoker implements EventExecutor {
	
	int type; //1 does first skill, 2 does second skill, 3 does gathering talent
	
	public EventInvoker(int type) {
		this.type = type;
	}
	
	private boolean isEventRelatedToPlayer(Event event) {
		
		return false;
	}
	
	@Override
	public void execute(Listener listener, Event event) throws EventException {
		if(listener instanceof MegaWallsClass) {
			MegaWallsClass oclass = (MegaWallsClass) listener;
			if(type == 1) {
				oclass.startSkill(event);
			} else if(type == 2) {
				oclass.startPassive(event);
			} else if(type == 3) {
				oclass.triggerGather(event);
			}
		}
	}

}
