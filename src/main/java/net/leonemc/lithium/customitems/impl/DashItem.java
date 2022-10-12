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

@ItemInfo(
        name = "dash",
        displayName = "§aDash",
        description = "Dashes you in the direction you're looking",
        displayItem = XMaterial.FEATHER
)
public class DashItem extends CustomItem implements Listener {

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

        if (Cooldown.isOnCooldown("dash", player)) {
            player.sendMessage("§cYou must wait " + Cooldown.getCooldownForPlayerInt("dash", player) + " seconds before using this again.");
            return;
        }

        // send the player in the direction they're looking in
        player.setVelocity(player.getLocation().getDirection().multiply(4).setY(0.2));

        Cooldown.addCooldown("dash", player, 10);

        // play woosh sound
        player.getWorld().playSound(player.getLocation(), Sound.WITHER_SHOOT, 1, 0.2f);

        use(player);
    }
}
