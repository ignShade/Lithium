package net.leonemc.lithium.customitems.impl;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.customitems.CustomItem;
import net.leonemc.lithium.customitems.annotation.ItemInfo;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@ItemInfo(
        name = "blade_vortex",
        displayName = "§aBlade Vortex",
        description = "Pulls all players in a 5 block radius towards you",
        displayItem = XMaterial.DIAMOND_SWORD
)
public class PullItem extends CustomItem implements Listener {

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

        player.getWorld().getNearbyEntities(player.getLocation(), 6, 5, 6).forEach(entity -> entity.setVelocity(player.getLocation().toVector().subtract(entity.getLocation().toVector()).normalize().multiply(2)));

        // play woosh sound
        player.getWorld().playSound(player.getLocation(), Sound.WITHER_SHOOT, 1, 0.2f);
        use(player);

        // for some reason the item doesn't get removed if you don't do this
        event.setCancelled(true);
    }
}
