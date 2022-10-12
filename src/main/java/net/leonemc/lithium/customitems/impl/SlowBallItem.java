package net.leonemc.lithium.customitems.impl;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.customitems.CustomItem;
import net.leonemc.lithium.customitems.annotation.ItemInfo;
import org.bukkit.Location;
import org.bukkit.entity.Egg;
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
        name = "slowegg",
        displayName = "§aSlow Egg",
        description = "Slows the player you hit",
        displayItem = XMaterial.EGG
)
public class SlowBallItem extends CustomItem implements Listener {

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

        player.setMetadata("threw_slowball", new FixedMetadataValue(Lithium.getInstance(), true));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Egg && ((Egg) event.getDamager()).getShooter() instanceof Player) {
            Player player = (Player) ((Egg) event.getDamager()).getShooter();

            if (player.hasMetadata("threw_slowball")) {
                Player target = (Player) event.getEntity();

                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));

                player.removeMetadata("threw_slowball", Lithium.getInstance());

                player.sendMessage("§aYou have just hit §e" + target.getName() + " §awith a slow egg!");
                target.sendMessage("§e" + player.getName() + " §ahas just hit you with a slow egg!");

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.getPlayer().removeMetadata("threw_switcher", Lithium.getInstance());
    }
}