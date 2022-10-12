package net.leonemc.lithium.commands.tags;

import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.tags.TagHandler;
import net.leonemc.lithium.tags.objects.Tag;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class TagGiveCommand {

    @Command(names = "tag give", permission = "*")
    public static void execute(CommandSender sender, @Param(name = "target") String target, @Param(name = "tag") Tag tag) {
        Bukkit.dispatchCommand(sender, "lp user " + target + " permission set tag." + tag.getName() +  " true");
        sender.sendMessage("§aTag given.");
    }
}
