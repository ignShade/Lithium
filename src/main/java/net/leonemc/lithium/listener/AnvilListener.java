package net.leonemc.lithium.listener;

import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.events.AnvilUseEvent;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_8_R3.ContainerAnvil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

public class AnvilListener implements Listener {

    /*/@EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        HumanEntity ent = event.getWhoClicked();

        if (!(ent instanceof Player)) {
            return;
        }

        Player player = (Player) ent;
        Inventory inv = event.getInventory();

        if (!(inv instanceof AnvilInventory)) {
            return;
        }

        InventoryView view = event.getView();
        int rawSlot = event.getRawSlot();

        if (rawSlot != view.convertSlot(rawSlot)) {
            return;
        }
        if (rawSlot != 2) {
            return;
        }

        Economy economy = Lithium.getInstance().getEcon();

        if (!economy.has(player, 5000)) {
            player.sendMessage("§cYou don't have enough money.");
            event.setCancelled(true);
            return;
        }

        economy.withdrawPlayer(player, 5000);
        player.sendMessage("§aSuccess! You paid $5000 for an anvil.");
    }/*/
}
