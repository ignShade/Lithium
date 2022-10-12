package net.leonemc.lithium.commands.voucher;

import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.api.util.bukkit.ItemBuilder;
import net.leonemc.lithium.utils.bukkit.CC;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class VoucherCreateCommand {

    @Command(names = "voucher create", permission = "*")
    public static void onCommand(Player sender,
                                 @Param(name = "item-name", wildcard = true) String name) {

        ItemStack heldItem = sender.getItemInHand();

        if (heldItem == null) {
            sender.sendMessage("§cYou aren't holding an item.");
            return;
        }

        ItemStack voucher = new ItemBuilder(heldItem).name(name).setLore(Arrays.asList(
                CC.MENU_BAR,
                "§7Right click to redeem.",
                CC.MENU_BAR
        )).build();

        sender.setItemInHand(voucher);
        sender.sendMessage("§aCreated! Add a command using /voucher setcommand <command> to finish setting up the voucher.");
    }

}
