package net.leonemc.lithium.commands.filter;

import net.leonemc.lithium.utils.command.Command;
import net.leonemc.lithium.utils.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

public class FilterCommand {

    @Command(name = "chatfilter", aliases = "filter", permission = "rank.manager")
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        sender.sendMessage(ChatColor.RED + "Usages:");
        sender.sendMessage(ChatColor.RED + "/chatfilter addphrase <filter type> <phrase>");
        sender.sendMessage(ChatColor.RED + "/chatfilter removephrase <phrase>");
        sender.sendMessage(ChatColor.RED + "/chatfilter list");
    }

}