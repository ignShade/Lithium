package net.leonemc.lithium.worldguard.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.leonemc.lithium.worldguard.MovementWay;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;

public class RegionLeaveEvent
        extends RegionEvent
        implements Cancellable {
    private boolean cancelled = false;
    private boolean cancellable = true;

    public RegionLeaveEvent(ProtectedRegion region, Player player, MovementWay movement, PlayerEvent parent) {
        super(region, player, movement, parent);
        if (movement == MovementWay.SPAWN || movement == MovementWay.DISCONNECT) {
            this.cancellable = false;
        }
    }

    public void setCancelled(boolean cancelled) {
        if (!this.cancellable) {
            return;
        }
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public boolean isCancellable() {
        return this.cancellable;
    }

    protected void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
        if (!this.cancellable) {
            this.cancelled = false;
        }
    }
}