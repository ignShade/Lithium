package net.leonemc.lithium.commands.voucher;

import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.api.util.bukkit.ItemBuilder;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.utils.bukkit.CC;
import net.leonemc.lithium.vouchers.VoucherHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class VoucherSetCommandCommand {

    @Command(names = "voucher setcommand", permission = "*")
    public static void onCommand(Player sender,
                                 @Param(name = "command", wildcard = true) String cmd) {

        ItemStack heldItem = sender.getItemInHand();
        VoucherHandler voucherHandler = Lithium.getInstance().getVoucherHandler();

        if (heldItem == null) {
            sender.sendMessage("§cYou aren't holding an item.");
            return;
        }

        String command = cmd.startsWith("/") ? cmd.replaceFirst("/", "") : cmd;

        ItemStack voucher = voucherHandler.createVoucher(sender, command);

        sender.setItemInHand(voucher);
        sender.sendMessage("§aDone.");
    }

}
