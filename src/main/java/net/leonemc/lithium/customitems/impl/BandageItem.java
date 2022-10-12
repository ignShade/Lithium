package net.leonemc.lithium.customitems.impl;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.customitems.CustomItem;
import net.leonemc.lithium.customitems.annotation.ItemInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@ItemInfo(
        name = "bandage",
        displayName = "§aBandage",
        description = "Bandages you up",
        displayItem = XMaterial.PAPER
)
public class BandageItem extends CustomItem implements Listener {

    @EventHandler
    public void onPearlThrow(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (!isCustomItem(item)) {
            return;
        }

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        if (player.getHealth() >= 20) {
            player.sendMessage("§cYou are already at full health!");
            return;
        }

        if (player.getHealth() + 5 > 20) {
            player.setHealth(20);
        } else {
            player.setHealth(player.getHealth() + 5);
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 5, 1));

        use(player);
    }
}
