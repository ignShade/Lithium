package net.leonemc.lithium.commands.tags;

import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.tags.TagHandler;
import net.leonemc.lithium.tags.menu.TagListMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagCommand {

    @Command(names = {"tag", "tags", "prefix"})
    public static void execute(Player sender) {
        new TagListMenu().openMenu(sender);
    }

}
