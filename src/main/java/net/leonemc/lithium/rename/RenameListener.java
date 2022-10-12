package net.leonemc.lithium.rename;

import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.utils.Cooldown;
import net.leonemc.lithium.utils.menu.menus.ConfirmMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RenameListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block clickedBlock = event.getClickedBlock();
        RenameHandler renameHandler = Lithium.getInstance().getRenameHandler();

        if (action == Action.RIGHT_CLICK_BLOCK && clickedBlock.getType().name().contains("BEACON")) {
            if (Cooldown.isOnCooldown("renamer", player)) {
                player.sendMessage("§cYou must wait before using this again.");
                return;
            }

            if (!renameHandler.hasEnough(player)) {
                player.sendMessage("§cYou do not have enough money to rename an item.");
                return;
            }

            net.leonemc.lithium.utils.menu.Button button = new net.leonemc.lithium.utils.menu.Button() {
                @Override
                public ItemStack getButtonItem(Player player) {
                    return new net.leonemc.lithium.utils.bukkit.ItemBuilder(Material.PAPER).setName("§aConfirm").setLore("§7Are you sure you want rename this item?", "§7Price: §e50$").toItemStack();
                }
            };
            net.leonemc.lithium.utils.menu.Button[] middleButtons = {button, button, button};
            new ConfirmMenu("Confirm purchase?", data -> {
                if (data) {
                    renameHandler.startRename(player, player.getItemInHand());
                }
            }, true, middleButtons).openMenu(player);
            event.setCancelled(true);
        }
    }
}
