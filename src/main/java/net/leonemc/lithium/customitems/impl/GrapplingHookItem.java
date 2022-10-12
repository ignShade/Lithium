package net.leonemc.lithium.customitems.impl;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.customitems.CustomItem;
import net.leonemc.lithium.customitems.annotation.ItemInfo;
import net.leonemc.lithium.utils.Cooldown;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

@ItemInfo(
        name = "grappling_hook",
        displayName = "§aGrappling Hook",
        description = "Throws a grappling hook that pulls you towards the block it lands on",
        displayItem = XMaterial.FISHING_ROD,
        uses = 4
)
public class GrapplingHookItem extends CustomItem implements Listener {

    @EventHandler
    public void onFish(final PlayerFishEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (!isCustomItem(item)) {
            return;
        }

        if (event.getHook().getLocation().distance(event.getPlayer().getLocation()) < 3.0) {
            return;
        }

        if (Cooldown.isOnCooldown("grapple", player)) {
            player.sendMessage("§cYou must wait " + Cooldown.getCooldownForPlayerInt("grapple", player) + " seconds before using this again.");
            return;
        }

        Vector vec = event.getHook().getLocation().subtract(event.getPlayer().getLocation()).toVector();

        vec.multiply(new Vector(0.2, 0.19, 0.2));
        event.getPlayer().setVelocity(event.getPlayer().getVelocity().add(vec));

        Cooldown.addCooldown("grapple", player, 20);

        player.getWorld().playSound(player.getLocation(), Sound.MAGMACUBE_JUMP, 2.0f, 1.0f);

        use(player);
    }
}
