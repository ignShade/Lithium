package net.leonemc.lithium.customitems.impl;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.customitems.CustomItem;
import net.leonemc.lithium.customitems.annotation.ItemInfo;
import net.leonemc.lithium.utils.Cooldown;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

@ItemInfo(
        name = "updraft",
        displayName = "§aUpdraft",
        description = "Dashes you upwards",
        displayItem = XMaterial.FEATHER
)
public class UpdraftItem extends CustomItem implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (!isCustomItem(item)) {
            return;
        }

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (Cooldown.isOnCooldown("updraft", player)) {
            player.sendMessage("§cYou must wait " + Cooldown.getCooldownForPlayerInt("updraft", player) + " seconds before using this again.");
            return;
        }

        player.setVelocity(new Vector(0, 1.5, 0));
        Cooldown.addCooldown("updraft", player, 20);

        // play woosh sound
        player.getWorld().playSound(player.getLocation(), Sound.WITHER_SHOOT, 0.2f, 1f);

        use(player);
    }
}
