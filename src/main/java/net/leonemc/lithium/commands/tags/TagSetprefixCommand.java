package net.leonemc.lithium.commands.tags;

import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.tags.TagHandler;
import net.leonemc.lithium.tags.objects.Tag;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TagSetprefixCommand {

    @Command(names = "tag setprefix", permission = "*", async = true)
    public static void execute(CommandSender sender, @Param(name = "tag") Tag tag, @Param(name = "prefix", wildcard = true) String prefix) {
        tag.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
        tag.save();

        sender.sendMessage("§aTag prefix set.");
    }

}
