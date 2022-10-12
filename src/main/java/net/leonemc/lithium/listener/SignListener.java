package net.leonemc.lithium.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {

    @EventHandler
    public void onSignCreate(SignChangeEvent event) {
        if (event.getLine(0).equalsIgnoreCase("[KitPreview]")) {
            event.setLine(0, "§e§lKit Preview");
        }
    }

    @EventHandler
    public void onSignInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
            Sign sign = (Sign) event.getClickedBlock().getState();

            if (!sign.getLine(0).equals("§e§lKit Preview")) {
                return;
            }

            String kit = sign.getLine(1);
            player.performCommand("previewkit " + kit);
        }
    }
}
