package net.leonemc.lithium.dropparty;

import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.utils.bukkit.ItemUtil;
import net.leonemc.lithium.utils.java.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class DropPartyListener implements Listener {

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem().getItemStack();

        //check if it has a nbt tag
        if (ItemUtil.hasNBTTag(item, "drop-party")) {

            if (player.getInventory().firstEmpty() == -1) {
                event.setCancelled(true);
                return;
            }

            item = ItemUtil.removeNBTTag(item, "drop-party");

            String name = StringUtils.enumToName(item.getType().name().toLowerCase());
            String finalName = item.getItemMeta().getDisplayName() == null ?
                    name :
                    item.getItemMeta().getDisplayName() + " §7(" + name + ")";

            Bukkit.broadcastMessage("§6§lDrop Party §8» §e" + player.getName() + " §fhas picked up §e" + finalName + "§f.");

            event.getItem().remove();
            player.getInventory().addItem(item);

            event.setCancelled(true);
        }
    }

}
