package net.leonemc.lithium.vouchers;

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

public class VoucherListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        VoucherHandler banknoteHandler = Lithium.getInstance().getVoucherHandler();
        Player player = event.getPlayer();

        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }

        ItemStack item = player.getItemInHand();

        if (item == null) {
            return;
        }

        if (item.getItemMeta() == null) {
            return;
        }

        if (banknoteHandler.isVoucher(item)) {
            String command = banknoteHandler.getCommand(item);

            if (item.getAmount() < 1) {
                event.getPlayer().getInventory().removeItem(item);
            } else {
                item.setAmount(item.getAmount() - 1);
            }

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", player.getName()));
            player.sendMessage("§aSuccess! Voucher redeemed.");
        }
    }
}
