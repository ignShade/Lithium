package net.leonemc.lithium.commands.tags;

import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.tags.TagHandler;
import net.leonemc.lithium.tags.objects.Tag;
import org.bukkit.command.CommandSender;

public class TagDeleteCommand {

    @Command(names = "tag delete", permission = "*", async = true)
    public static void execute(CommandSender sender, @Param(name = "tag") Tag tag) {
        TagHandler tagHandler = Lithium.getInstance().getTagHandler();
        tagHandler.deleteTag(tag);
        sender.sendMessage("§aTag deleted.");
    }

}
