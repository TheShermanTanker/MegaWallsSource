package me.theshermantanker.megawalls;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerClickEvent extends PlayerEvent {
	
	private static final HandlerList handlers = new HandlerList();
	private Player clicked;
	
	public PlayerClickEvent(Player player, Player clicked) {
		super(player);
		this.clicked = clicked;
	}
	
	public Player getLeftClicked() {
		return clicked;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
        return handlers;
    }

}
