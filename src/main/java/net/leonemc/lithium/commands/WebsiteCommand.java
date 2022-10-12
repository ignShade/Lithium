package net.leonemc.lithium.commands;

import net.leonemc.lithium.utils.command.Command;
import net.leonemc.lithium.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class WebsiteCommand {

    @Command(name = "website")
    public void discordCommand(CommandArgs command) {
        Player player = command.getPlayer();

        player.sendMessage("§6§lWebsite §7| §fCheck out our website at leone.games");
    }

}
