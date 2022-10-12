package net.leonemc.lithium.customitems.impl;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.customitems.CustomItem;
import net.leonemc.lithium.customitems.annotation.ItemInfo;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@ItemInfo(
        name = "switcher",
        displayName = "§aSwitcher",
        description = "Switches your location with the player you hit",
        displayItem = XMaterial.SNOWBALL
)
public class SwitcherItem extends CustomItem implements Listener {

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

        player.setMetadata("threw_switcher", new FixedMetadataValue(Lithium.getInstance(), true));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Snowball && ((Snowball) event.getDamager()).getShooter() instanceof Player) {
            Player player = (Player) ((Snowball) event.getDamager()).getShooter();

            if (player.hasMetadata("threw_switcher")) {
                Player target = (Player) event.getEntity();

                Location playerLocation = player.getLocation();
                Location targetLocation = target.getLocation();

                player.teleport(targetLocation);
                target.teleport(playerLocation);

                player.removeMetadata("threw_switcher", Lithium.getInstance());

                player.sendMessage("§aYou switched locations with §e" + target.getName() + "§a!");
                target.sendMessage("§aYou switched locations with §e" + player.getName() + "§a!");

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.getPlayer().removeMetadata("threw_switcher", Lithium.getInstance());
    }
}
