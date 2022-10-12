package net.leonemc.lithium.listener;

import net.leonemc.duels.DuelPlugin;
import net.leonemc.duels.arena.ArenaHandler;
import net.leonemc.duels.match.MatchHandler;
import net.leonemc.lithium.events.PlayerDamageByPlayerEvent;
import net.leonemc.lithium.utils.bukkit.RegionUtil;
import net.leonemc.lithium.worldguard.events.RegionEnterEvent;
import net.leonemc.lithium.worldguard.events.RegionLeaveEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class SpawnFlyListener implements Listener {

    @EventHandler
    public void onRegionEnter(RegionEnterEvent event) {
        Player player = event.getPlayer();

        DuelPlugin plugin = DuelPlugin.getInstance();
        MatchHandler matchHandler = plugin.getMatchHandler();

        if (matchHandler.isInMatch(player) || matchHandler.isSpectating(player)) {
            return;
        }

        if (!event.getRegion().getId().contains("spawn")) {
            return;
        }

        if (player.hasPermission("lithium.spawnfly")) {
            player.setAllowFlight(true);
        }
    }

    @EventHandler
    public void onRegionEnter(RegionLeaveEvent event) {
        Player player = event.getPlayer();

        DuelPlugin plugin = DuelPlugin.getInstance();
        MatchHandler matchHandler = plugin.getMatchHandler();

        if (matchHandler.isInMatch(player) || matchHandler.isSpectating(player)) {
            return;
        }

        if (!event.getRegion().getId().contains("spawn")) {
            return;
        }

        if (player.getGameMode() != GameMode.CREATIVE && player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage("§cYour flight has been disabled!");
        }
    }

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().toLowerCase();

        if (!RegionUtil.isWithinRegionContains(player, "spawn") && player.hasPermission("lithium.spawnfly") &&
                !player.hasPermission("rank.staff") &&
                (command.contains("/fly") || command.contains("/efly"))) {

            event.setCancelled(true);
            player.sendMessage("§cYou can only use this command at spawn.");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerHit(PlayerDamageByPlayerEvent event) {
        Player damager = event.getDamager();

        DuelPlugin plugin = DuelPlugin.getInstance();
        MatchHandler matchHandler = plugin.getMatchHandler();

        if (matchHandler.isInMatch(damager) || matchHandler.isSpectating(damager)) {
            return;
        }

        if ((damager.getAllowFlight() || damager.isFlying()) && damager.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
            damager.setAllowFlight(false);
            damager.setFlying(false);
            damager.sendMessage("§cYour flight has been disabled!");
        }
    }

}
