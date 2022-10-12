package net.leonemc.lithium.banknotes;

import net.leonemc.lithium.Lithium;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BanknoteListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        BanknoteHandler banknoteHandler = Lithium.getInstance().getBanknoteHandler();
        Player player = event.getPlayer();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        ItemStack item = player.getItemInHand();

        if (item == null) {
            return;
        }

        if (item.getType() != Material.PAPER) {
            return;
        }

        if (item.getItemMeta() == null) {
            return;
        }

        if (!banknoteHandler.isBankNote(item)) {
            return;
        }

        if (item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("Bank Note")) {
            int amount = banknoteHandler.getAmount(item);

            if (amount < 0) {
                player.sendMessage("§cUnable to redeem bank note. (Amount < 0)");
                return;
            }

            if (item.getAmount() <= 1) {
                event.getPlayer().getInventory().removeItem(item);
            } else {
                item.setAmount(item.getAmount() - 1);
            }

            Economy economy = Lithium.getInstance().getEcon();
            EconomyResponse response = economy.depositPlayer(player, amount);

            if (!response.transactionSuccess()) {
                player.sendMessage("§cAn error occurred whilst redeeming the bank note. [#1]");
                return;
            }

            player.sendMessage("§aYou've redeemed a bank note for " + amount + "$.");
        }
    }
}
