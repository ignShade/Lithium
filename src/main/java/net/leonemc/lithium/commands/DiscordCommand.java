package net.leonemc.lithium.commands;

import net.leonemc.lithium.utils.command.Command;
import net.leonemc.lithium.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class DiscordCommand {

    @Command(name = "discord")
    public void discordCommand(CommandArgs command) {
        Player player = command.getPlayer();

        player.sendMessage("§6§lDiscord §7| §fJoin our discord at discord.gg/leonegaming");
    }

}
