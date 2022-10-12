package net.leonemc.lithium.commands.customitem;

import net.leonemc.api.command.Command;
import net.leonemc.lithium.customitems.menu.ItemListMenu;
import org.bukkit.entity.Player;

public class CustomItemListCommand {

    @Command(names = {"customitem list", "ci list", "ci", "ability", "ability list"}, permission = "*")
    public static void execute(Player sender) {
        new ItemListMenu().openMenu(sender);
    }
}
