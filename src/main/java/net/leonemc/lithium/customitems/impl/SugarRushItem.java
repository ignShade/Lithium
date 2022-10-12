package net.leonemc.lithium.customitems.impl;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.customitems.CustomItem;
import net.leonemc.lithium.customitems.annotation.ItemInfo;
import net.leonemc.lithium.events.CustomPlayerDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
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
        name = "sugar_rush",
        displayName = "§aSugar Rush",
        description = "Gives you a speed boost",
        displayItem = XMaterial.SUGAR
)
public class SugarRushItem extends CustomItem implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (!isCustomItem(item)) {
            return;
        }

        use(player);

        player.setMetadata("sugar_rush", new FixedMetadataValue(Lithium.getInstance(), true));
        player.getWorld().playSound(player.getLocation(), Sound.BURP, 2.0F, 1.0F);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 800, 1));

        Bukkit.getScheduler().scheduleSyncDelayedTask(Lithium.getInstance(), () -> {
            // Make sure the player isn't null as they could've logged out
            if (player == null) {
                return;
            }

            player.removePotionEffect(PotionEffectType.SPEED);
            player.removeMetadata("sugar_rush", Lithium.getInstance());
        }, 600L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.getPlayer().removeMetadata("sugar_rush", Lithium.getInstance());
    }

    @EventHandler
    public void onDeath(CustomPlayerDeathEvent event) {
        event.getPlayer().removeMetadata("sugar_rush", Lithium.getInstance());

        if (event.getKiller() instanceof Player) {
            event.getKiller().removeMetadata("sugar_rush", Lithium.getInstance());
        }
    }

}
