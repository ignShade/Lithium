package net.leonemc.lithium.commands.customitem;

import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.lithium.customitems.CustomItem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomItemGiveCommand {

    @Command(names = {"customitem give", "ci give", "ability give"}, permission = "*")
    public static void execute(CommandSender sender,
                               @Param(name = "player", defaultValue = "self") Player player,
                               @Param(name = "item") CustomItem item,
                               @Param(name = "amount") int amount) {

        for (int i = 0; i < amount; i++) {
            player.getInventory().addItem(item.getItemStack());
        }

        player.updateInventory();
        player.sendMessage("§aYou have been given " + amount + " of " + "\"" + item.getDisplayName() + "\"" + "§a.");
    }
}
