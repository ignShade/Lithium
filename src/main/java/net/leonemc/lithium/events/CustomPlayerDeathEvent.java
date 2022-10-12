package net.leonemc.lithium.events;

import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class CustomPlayerDeathEvent extends Event {

    private Player player;
    private Entity killer;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public CustomPlayerDeathEvent(Player player, Entity killer) {
        this.player = player;
        this.killer = killer;
        this.isCancelled = false;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public Entity getKiller() {
        return killer;
    }
}