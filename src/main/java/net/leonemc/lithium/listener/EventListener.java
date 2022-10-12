package net.leonemc.lithium.listener;

import me.danjono.inventoryrollback.data.LogType;
import me.danjono.inventoryrollback.inventory.SaveInventory;
import me.imsanti.leoneevents.player.SimpleProfile;
import net.leonemc.duels.DuelPlugin;
import net.leonemc.duels.match.MatchHandler;
import net.leonemc.lithium.events.CustomPlayerDeathEvent;
import net.leonemc.lithium.events.PlayerDamageByEntityEvent;
import net.leonemc.lithium.events.PlayerDamageByPlayerEvent;
import net.leonemc.lithium.utils.bukkit.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EventListener implements Listener {

    private final HashMap<Player, Entity> lastDamager = new HashMap<>();

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL || event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            event.setCancelled(true);
            return;
        }

        //essentials /suicide and /kill fix
        if (event.getDamage() == Float.MAX_VALUE) {
            event.setCancelled(true);
            return;
        }

        //return if they're in a duel, otherwise stuff will break...
        DuelPlugin duelPlugin = DuelPlugin.getInstance();
        MatchHandler matchHandler = duelPlugin.matchHandler;

        if (matchHandler.isInMatch(player)) {
            return;
        }

        //Don't actually "kill" the player because viaversion takes like 5 seconds to actually respawn the player, so it's better to just do this.
        if (player.getHealth() - event.getFinalDamage() <= 0) {
            event.setCancelled(true);

            handleDeath(player);
        }
    }

    private void handleDeath(Player player) {
        ArrayList<ItemStack> drops = new ArrayList<>(Arrays.asList(player.getInventory().getContents()));
        drops.addAll(Arrays.asList(player.getInventory().getArmorContents()));

        try {
            String damager = lastDamager.get(player).getName() == null ? "???" : lastDamager.get(player).getName();
            new SaveInventory(player, LogType.DEATH, EntityDamageEvent.DamageCause.ENTITY_ATTACK, "Killed by " + damager, player.getInventory(), player.getEnderChest()).createSave(true);
        } catch (Exception e) {

        }

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        Location deathLocation = player.getLocation();
        drops.stream().filter(item -> item != null && item.getType() != Material.AIR && !ItemUtil.shouldDelete(item)).forEach(drop -> player.getWorld().dropItemNaturally(deathLocation, drop));

        CustomPlayerDeathEvent e = new CustomPlayerDeathEvent(player, lastDamager.get(player));
        Bukkit.getPluginManager().callEvent(e);

        lastDamager.remove(player);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        //return if they're in a duel, otherwise stuff will break...
        DuelPlugin duelPlugin = DuelPlugin.getInstance();
        MatchHandler matchHandler = duelPlugin.matchHandler;


        if (matchHandler.isInMatch((Player) event.getEntity())) {
            return;
        }

        if (event.getDamager() instanceof Player) {
            PlayerDamageByPlayerEvent e = new PlayerDamageByPlayerEvent((Player) event.getEntity(), (Player) event.getDamager());
            Bukkit.getPluginManager().callEvent(e);

            lastDamager.put((Player) event.getEntity(), event.getDamager());

            if (e.isCancelled()) {
                event.setCancelled(true);
                return;
            }
        } else {
            if (event.getDamager() instanceof Arrow && ((Arrow) event.getDamager()).getShooter() instanceof Player) {
                Arrow arrow = (Arrow) event.getDamager();
                PlayerDamageByPlayerEvent e = new PlayerDamageByPlayerEvent((Player) event.getEntity(), (Player) arrow.getShooter());
                Bukkit.getPluginManager().callEvent(e);

                lastDamager.put((Player) event.getEntity(), (Player) arrow.getShooter());

                if (e.isCancelled()) {
                    event.setCancelled(true);
                    return;
                }
            } else if (event.getDamager() instanceof FishHook && ((FishHook) event.getDamager()).getShooter() instanceof Player) {
                FishHook fishHook = (FishHook) event.getDamager();
                Player shooter = (Player) fishHook.getShooter();
                PlayerDamageByPlayerEvent e = new PlayerDamageByPlayerEvent((Player) event.getEntity(), shooter);
                Bukkit.getPluginManager().callEvent(e);

                lastDamager.put((Player) event.getEntity(), (Player) fishHook.getShooter());
            } else {
                PlayerDamageByEntityEvent e = new PlayerDamageByEntityEvent((Player) event.getEntity(), event.getDamager());
                Bukkit.getPluginManager().callEvent(e);

                lastDamager.put((Player) event.getEntity(), event.getDamager());

                if (e.isCancelled()) {
                    event.setCancelled(true);
                    return;
                }
            }
        }

        if (event.getEntity() instanceof Player) {

            //Don't actually "kill" the player because viaversion takes like 5 seconds to actually respawn the player, so it's better to just do this.
            if (((Player) event.getEntity()).getHealth() - event.getFinalDamage() <= 0) {
                Player player = (Player) event.getEntity();

                event.setCancelled(true);
                player.setLastDamageCause(event);

                handleDeath(player);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        //auto respawn
        player.spigot().respawn();

        //hide the death message
        event.setDeathMessage(null);

        //return if they're in a duel, otherwise stuff will break...
        DuelPlugin duelPlugin = DuelPlugin.getInstance();
        MatchHandler matchHandler = duelPlugin.matchHandler;

        if (matchHandler.isInMatch(player)) {
            return;
        }

        CustomPlayerDeathEvent e = new CustomPlayerDeathEvent(player, player.getKiller());
        Bukkit.getPluginManager().callEvent(e);

        lastDamager.remove(player);
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        lastDamager.remove(event.getPlayer());
    }
}
