package net.leonemc.lithium.listener;

import net.leonemc.duels.DuelPlugin;
import net.leonemc.lithium.utils.Cooldown;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Material.ENDER_PEARL;
import static org.bukkit.Material.GOLDEN_APPLE;

public class ItemCooldownListener implements Listener {

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();

        DuelPlugin duelPlugin = DuelPlugin.getInstance();
        if (duelPlugin.matchHandler.isInMatch(player)) {
            return;
        }

        if (itemStack.getType() == GOLDEN_APPLE && itemStack.getDurability() != 1) {
            if (Cooldown.isOnCooldown("gapple", player)) {
                player.sendMessage("§cYou must wait another §l" + Cooldown.getCooldownForPlayerInt("gapple", player) + "s§c before using this again.");
                event.setCancelled(true);
            } else {
                Cooldown.addCooldown("gapple", player, 3);
            }
        }
    }

    @EventHandler
    public void onEnderpearlThrow(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInHand();

        DuelPlugin duelPlugin = DuelPlugin.getInstance();
        if (duelPlugin.matchHandler.isInMatch(player)) {
            return;
        }

        if (itemStack == null) {
            return;
        }

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (itemStack.getType() == ENDER_PEARL) {
            if (Cooldown.isOnCooldown("enderpearl", player)) {
                player.sendMessage("§cYou must wait another §l" + Cooldown.getCooldownForPlayerInt("enderpearl", player) + "s§c before using this again.");
                event.setCancelled(true);
                player.updateInventory();
                return;
            }

            Cooldown.addCooldown("enderpearl", player, 13);
        }
    }

}
