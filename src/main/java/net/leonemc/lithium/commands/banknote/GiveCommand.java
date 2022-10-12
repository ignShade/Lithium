package net.leonemc.lithium.commands.banknote;

import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.banknotes.BanknoteHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand {

    @Command(names = {"banknote give", "bn give"}, permission = "*")
    public static void onCommand(CommandSender player,
                                 @Param(name = "target", defaultValue = "self") Player target,
                                 @Param(name = "amount") int amount) {

        BanknoteHandler banknoteHandler = Lithium.getInstance().getBanknoteHandler();
        ItemStack bankNote = banknoteHandler.createBanknote(target, amount);

        target.getInventory().addItem(bankNote);

        player.sendMessage("§aYou gave a bank note worth $" + amount + " to " + target.getName() + ".");
    }

}
