package net.leonemc.lithium.commands;

import net.leonemc.lithium.shop.menu.ShopCategorySelection;
import net.leonemc.lithium.shop.menu.ShopMenu;
import net.leonemc.lithium.shop.objects.ShopCategory;
import net.leonemc.lithium.utils.command.Command;
import net.leonemc.lithium.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class ShopCommand {

    @Command(name = "shop")
    public void shopCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.getArgs().length != 1) {

            new ShopCategorySelection().openMenu(player);
        } else {
            String category = command.getArgs()[0];
            ShopCategory shopCategory = ShopCategory.valueOf(category.toUpperCase());

            new ShopMenu(shopCategory).openMenu(player);
        }

    }

}
