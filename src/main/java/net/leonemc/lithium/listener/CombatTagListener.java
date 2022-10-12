package net.leonemc.lithium.listener;

import me.imsanti.leoneevents.player.SimpleProfile;
import net.leonemc.duels.DuelPlugin;
import net.leonemc.duels.match.MatchHandler;
import net.leonemc.lithium.events.PlayerDamageByPlayerEvent;
import net.leonemc.lithium.utils.Cooldown;
import net.leonemc.lithium.utils.bukkit.ItemUtil;
import net.leonemc.lithium.utils.bukkit.RegionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class CombatTagListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerAttack(PlayerDamageByPlayerEvent event) {
        Player player = event.getPlayer();
        Player attacker = event.getDamager();

        if (player == attacker) {
            return;
        }

        // don't combat tag people in duels
        DuelPlugin duelPlugin = DuelPlugin.getInstance();
        MatchHandler matchHandler = duelPlugin.matchHandler;

        if (matchHandler.isInMatch(player)) {
            return;
        }

        // don't combat tag people in events
        SimpleProfile profile = SimpleProfile.fromUUID(player.getUniqueId());
        if (profile.getCurrentGame() != null) {
            return;
        }

        //sometimes the server sets it to something lower for some reason so just revert it to 20
        player.setMaximumNoDamageTicks(20);
        attacker.setMaximumNoDamageTicks(20);

        Cooldown.addCooldown("combat", attacker, 15);
        Cooldown.addCooldown("combat", player, 15);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (Cooldown.isOnCooldown("combat", player)) {
            Location deathLocation = player.getLocation();
            ArrayList<ItemStack> drops = new ArrayList<>(Arrays.asList(player.getInventory().getContents()));
            drops.addAll(Arrays.asList(player.getInventory().getArmorContents()));

            drops.stream().filter(item -> item != null && item.getType() != Material.AIR && !ItemUtil.shouldDelete(item)).forEach(drop -> player.getWorld().dropItemNaturally(deathLocation, drop));

            player.getInventory().clear();
            player.getInventory().setArmorContents(null);

            player.setHealth(0);
            player.sendMessage("§cYou have been killed for logging out in combat.");

            Bukkit.broadcastMessage(" ");
            Bukkit.broadcastMessage("§c" + player.getName() + " has been killed for logging out in combat.");
            Bukkit.broadcastMessage(" ");
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage().toLowerCase();

        //don't allow pv's and repair to be used in the arena
        if (!RegionUtil.isWithinRegionContains(player, "spawn") && !player.hasPermission("lithium.arenacmds.bypass")) {
            if (message.contains("pv") ||
                    message.contains(":") || // people can use /essentials:repair to bypass for example
                    message.contains("playervault") ||
                    message.contains("repair") ||
                    message.contains("repairall") ||
                    message.contains("fix") ||
                    message.contains("chest") ||
                    message.contains("vault") ||
                    message.contains("enderchest") ||
                    message.contains("echest") ||
                    message.contains("playervaults") ||
                    message.contains("erepair") ||
                    message.contains("erepairall") ||
                    message.contains("efix")) {
                player.sendMessage("§cYou cannot use this command in the arena.");
                event.setCancelled(true);
                return;
            }
        }

        //allow staff to use these commands in the arena
        if (message.contains("ban") || message.contains("mute") || message.contains("sc") || message.contains("report"))
            return;

        if (Cooldown.isOnCooldown("combat", player) && !player.hasPermission("lithium.combat.bypass")) {
            event.setCancelled(true);
            player.sendMessage("§cYou cannot use commands in combat.");
        }
    }
}
