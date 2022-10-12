package net.leonemc.lithium.commands.filter;

import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.utils.command.Command;
import net.leonemc.lithium.utils.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import java.util.List;

public class FilterRemovephraseCommand {

    @Command(name = "chatfilter.removephrase", aliases = "filter.remove")
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        if (command.getArgs().length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /chatfilter removephrase <phrase>");
            return;
        }
        if (Lithium.getInstance().getFilterManager().getMutableWords().contains(command.getArgs()[0]) || Lithium.getInstance().getFilterManager().getOtherFiltered().contains(command.getArgs()[0])) {
            if (Lithium.getInstance().getFilterManager().getOtherFiltered().contains(command.getArgs()[0])) {
                List<String> words = Lithium.getInstance().getConfig().getStringList("filtered-words.normal");
                words.remove(command.getArgs()[0]);
                Lithium.getInstance().getConfig().set("filtered-words.normal", words);
                Lithium.getInstance().saveConfig();
                Lithium.getInstance().reload();
                sender.sendMessage(ChatColor.GREEN + "Successfully removed the " + ChatColor.YELLOW + "NORMAL " + ChatColor.GREEN + "phrase " + ChatColor.YELLOW + command.getArgs()[0] + ChatColor.GREEN + ".");
            } else if (Lithium.getInstance().getFilterManager().getMutableWords().contains(command.getArgs()[0])) {
                List<String> words = Lithium.getInstance().getConfig().getStringList("filtered-words.mutable");
                words.remove(command.getArgs()[0]);
                Lithium.getInstance().getConfig().set("filtered-words.mutable", words);
                Lithium.getInstance().saveConfig();
                Lithium.getInstance().reload();
                sender.sendMessage(ChatColor.GREEN + "Successfully removed the " + ChatColor.YELLOW + "MUTABLE " + ChatColor.GREEN + "phrase " + ChatColor.YELLOW + command.getArgs()[0] + ChatColor.GREEN + ".");
            } else {
                sender.sendMessage(ChatColor.RED + "There was an error whilst attempting to use this command. Please contact a developer for further information.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "The phrase \"" + command.getArgs()[0] + "\" does not exist in the chat filter.");
        }
    }
}