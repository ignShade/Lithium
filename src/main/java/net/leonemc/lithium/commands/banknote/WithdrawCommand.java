package net.leonemc.lithium.commands.banknote;

import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.banknotes.BanknoteHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WithdrawCommand {

    @Command(names = {"withdraw", "banknote withdraw", "bn withdraw"}, description = "Creates a bank note with the specified amount")
    public static void onCommand(Player sender, @Param(name = "amount") int amount) {
        BanknoteHandler banknoteHandler = Lithium.getInstance().getBanknoteHandler();
        Economy economy = Lithium.getInstance().getEcon();

        if (amount <= 25) {
            sender.sendMessage("§cThe amount must be greater than 25");
            return;
        }

        if (sender.getInventory().firstEmpty() == -1) {
            sender.sendMessage("§cError. Unable to create a banknote because your inventory is full.");
            return;
        }

        if (!economy.has(sender, amount)) {
            sender.sendMessage("§cYou do not have " + amount + "$.");
            return;
        }

        ItemStack bankNote = banknoteHandler.createBanknote(sender, amount);

        economy.withdrawPlayer(sender, amount);
        sender.getInventory().addItem(bankNote);
    }

}
