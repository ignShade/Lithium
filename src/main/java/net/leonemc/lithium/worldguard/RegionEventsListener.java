package net.leonemc.lithium.worldguard;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.worldguard.events.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.*;

//credit: https://www.spigotmc.org/resources/worldguard-region-events.28358/
//don't want to have another extra plugin to depend on, so I just copied the code from the link above
//the code could be improved, but I don't think it's worth it
//todo: maybe improve the code?
public class RegionEventsListener implements Listener {
    private WorldGuardPlugin wgPlugin = WorldGuardPlugin.inst();
    private Lithium plugin = Lithium.getInstance();
    private Map<Player, Set<ProtectedRegion>> playerRegions = new HashMap<>();

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e) {
        Set<ProtectedRegion> regions = this.playerRegions.remove(e.getPlayer());
        if (regions != null) {
            for (ProtectedRegion region : regions) {
                RegionLeaveEvent leaveEvent = new RegionLeaveEvent(region, e.getPlayer(), MovementWay.DISCONNECT, e);
                RegionLeftEvent leftEvent = new RegionLeftEvent(region, e.getPlayer(), MovementWay.DISCONNECT, e);
                this.plugin.getServer().getPluginManager().callEvent(leaveEvent);
                this.plugin.getServer().getPluginManager().callEvent(leftEvent);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Set<ProtectedRegion> regions = this.playerRegions.remove(e.getPlayer());
        if (regions != null) {
            for (ProtectedRegion region : regions) {
                RegionLeaveEvent leaveEvent = new RegionLeaveEvent(region, e.getPlayer(), MovementWay.DISCONNECT, e);
                RegionLeftEvent leftEvent = new RegionLeftEvent(region, e.getPlayer(), MovementWay.DISCONNECT, e);
                this.plugin.getServer().getPluginManager().callEvent(leaveEvent);
                this.plugin.getServer().getPluginManager().callEvent(leftEvent);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        e.setCancelled(this.updateRegions(e.getPlayer(), MovementWay.MOVE, e.getTo(), e));
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        e.setCancelled(this.updateRegions(e.getPlayer(), MovementWay.TELEPORT, e.getTo(), e));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        this.updateRegions(e.getPlayer(), MovementWay.SPAWN, e.getPlayer().getLocation(), e);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        this.updateRegions(e.getPlayer(), MovementWay.SPAWN, e.getRespawnLocation(), e);
    }


    private synchronized boolean updateRegions(final Player player, final MovementWay movement, Location to, final PlayerEvent event) {
        RegionEvent e;
        HashSet regions = this.playerRegions.get(player) == null ? new HashSet() : new HashSet(this.playerRegions.get(player));
        HashSet oldRegions = new HashSet(regions);
        RegionManager rm = this.wgPlugin.getRegionManager(to.getWorld());
        if (rm == null) {
            return false;
        }
        HashSet<ProtectedRegion> appRegions = new HashSet<ProtectedRegion>(rm.getApplicableRegions(to).getRegions());
        ProtectedRegion globalRegion = rm.getRegion("__global__");
        if (globalRegion != null) {
            appRegions.add(globalRegion);
        }
        for (final ProtectedRegion region : appRegions) {
            if (regions.contains(region)) continue;
            e = new RegionEnterEvent(region, player, movement, event);
            this.plugin.getServer().getPluginManager().callEvent(e);
            if (((RegionEnterEvent)e).isCancelled()) {
                regions.clear();
                regions.addAll(oldRegions);
                return true;
            }
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                RegionEnteredEvent e12 = new RegionEnteredEvent(region, player, movement, event);
                plugin.getServer().getPluginManager().callEvent(e12);
            }, 1L);
            regions.add(region);
        }
        Iterator itr = regions.iterator();
        while (itr.hasNext()) {
            final ProtectedRegion region = (ProtectedRegion)itr.next();
            if (appRegions.contains(region)) continue;
            if (rm.getRegion(region.getId()) != region) {
                itr.remove();
                continue;
            }
            e = new RegionLeaveEvent(region, player, movement, event);
            this.plugin.getServer().getPluginManager().callEvent(e);
            if (((RegionLeaveEvent)e).isCancelled()) {
                regions.clear();
                regions.addAll(oldRegions);
                return true;
            }
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                RegionLeftEvent e1 = new RegionLeftEvent(region, player, movement, event);
                plugin.getServer().getPluginManager().callEvent(e1);
            }, 1L);
            itr.remove();
        }
        this.playerRegions.put(player, regions);
        return false;
    }
}
