package net.leonemc.lithium.commands.filter;

import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.utils.command.Command;
import net.leonemc.lithium.utils.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

public class FilterListCommand {

    @Command(name = "chatfilter.list", aliases = "filter.list")
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        sender.sendMessage(ChatColor.GREEN + "Normal Filtered Phrases: " + ChatColor.YELLOW + Lithium.getInstance().getFilterManager().getOtherFiltered());
        sender.sendMessage(ChatColor.GREEN + "Mutable Filtered Phrases: " + ChatColor.YELLOW + Lithium.getInstance().getFilterManager().getMutableWords());
    }
}