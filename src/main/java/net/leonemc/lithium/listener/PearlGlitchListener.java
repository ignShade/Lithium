package net.leonemc.lithium.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

public class PearlGlitchListener implements Listener {

    @EventHandler(ignoreCancelled=true, priority=EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.hasItem() && event.getItem().getType() == Material.ENDER_PEARL && (block = event.getClickedBlock()).getType().isSolid() && !(block.getState() instanceof InventoryHolder)) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            player.setItemInHand(event.getItem());
        }
    }
}
