package net.leonemc.lithium.commands.tags;

import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.tags.TagHandler;
import net.leonemc.lithium.tags.objects.Tag;
import org.bukkit.command.CommandSender;

public class TagCreateCommand {

    @Command(names = "tag create", permission = "*")
    public static void execute(CommandSender sender, @Param(name = "name") String name) {
        TagHandler tagHandler = Lithium.getInstance().getTagHandler();
        tagHandler.createTag(name);

        sender.sendMessage("§aTag created.");
    }

}
