package net.leonemc.lithium.customitems.impl;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.customitems.CustomItem;
import net.leonemc.lithium.customitems.annotation.ItemInfo;
import net.leonemc.lithium.utils.Cooldown;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@ItemInfo(
        name = "attack_dogs",
        displayName = "§aK9 Unit",
        description = "Spawns a few dogs that attacks nearby enemies",
        displayItem = XMaterial.WOLF_SPAWN_EGG
)
public class AttackDogsItem extends CustomItem implements Listener {

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

        if (Cooldown.isOnCooldown("attack_dogs", player)) {
            player.sendMessage("§cYou cannot do this yet... Please wait!");
            return;
        }

        int spawned = 0;

        for (Entity p1 : player.getWorld().getNearbyEntities(player.getLocation(), 10, 10, 10)) {
            if (p1 == player) {
                continue;
            }

            if (spawned >= 3) {
                break;
            }

            if (p1 instanceof LivingEntity) {
                Wolf wolf = player.getWorld().spawn(player.getLocation(), Wolf.class);
                wolf.setOwner(player);
                wolf.setTarget((LivingEntity) p1);
                wolf.setAngry(true);

                spawned++;
            }
        }

        if (spawned == 0) {
            player.sendMessage("§cNo nearby enemies found.");
            event.setCancelled(true);
            return;
        }

        use(player);
        Cooldown.addCooldown("attack_dogs", player, 30);
        event.setCancelled(true);
    }
}
