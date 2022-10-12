package net.leonemc.lithium.customitems.impl;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.customitems.CustomItem;
import net.leonemc.lithium.customitems.annotation.ItemInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

@ItemInfo(
        name = "fake_pearl",
        displayName = "§aFake Pearl",
        description = "Throws a decoy ender pearl that doesn't teleport you when it lands",
        displayItem = XMaterial.ENDER_PEARL
)
public class FakePearlItem extends CustomItem implements Listener {

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

        player.setMetadata("fake_pearl", new FixedMetadataValue(Lithium.getInstance(), true));
    }

    @EventHandler(priority = org.bukkit.event.EventPriority.HIGHEST)
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            return;
        }

        if (!event.getPlayer().hasMetadata("fake_pearl")) {
            return;
        }

        event.setCancelled(true);
        event.getPlayer().removeMetadata("fake_pearl", Lithium.getInstance());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.getPlayer().removeMetadata("fake_pearl", Lithium.getInstance());
    }
}
