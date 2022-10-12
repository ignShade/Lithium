package net.leonemc.lithium.commands.filter;

import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.utils.command.Command;
import net.leonemc.lithium.utils.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class FilterAddphraseCommand {

    @Command(name = "chatfilter.addphrase", aliases = "filter.add", permission = "rank.manager")
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        if (command.getArgs().length != 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /chatfilter addphrase <filter type> <phrase>");
            return;
        }
        switch (command.getArgs()[0].toLowerCase()) {
            case "normal": {
                if (Lithium.getInstance().getFilterManager().getOtherFiltered().contains(command.getArgs()[1])) {
                    sender.sendMessage(ChatColor.RED + "The specified phrase is already filtered.");
                    return;
                }
                List<String> words = Lithium.getInstance().getConfig().getStringList("filtered-words.normal");
                words.add(command.getArgs()[1]);
                Lithium.getInstance().getConfig().set("filtered-words.normal", words);
                Lithium.getInstance().saveConfig();
                Lithium.getInstance().reload();
                sender.sendMessage(ChatColor.GREEN + "Successfully added " + ChatColor.YELLOW + command.getArgs()[1] + ChatColor.GREEN + " as a " + ChatColor.YELLOW + "NORMAL " + ChatColor.GREEN + "phrase.");
                break;
            }
            case "mutable": {
                if (Lithium.getInstance().getFilterManager().getMutableWords().contains(command.getArgs()[1])) {
                    sender.sendMessage(ChatColor.RED + "The specified phrase is already filtered.");
                    return;
                }
                List<String> words = Lithium.getInstance().getConfig().getStringList("filtered-words.mutable");
                words.add(command.getArgs()[1]);
                Lithium.getInstance().getConfig().set("filtered-words.mutable", words);
                Lithium.getInstance().saveConfig();
                Lithium.getInstance().reload();
                sender.sendMessage(ChatColor.GREEN + "Successfully added " + ChatColor.YELLOW + command.getArgs()[1] + ChatColor.GREEN + " as a " + ChatColor.YELLOW + "MUTABLE " + ChatColor.GREEN + "phrase.");
                break;
            }

            default: {
                sender.sendMessage(ChatColor.RED + "The specified filter type does not exist.");
                sender.sendMessage(ChatColor.RED + "Valid filter types include: NORMAL, MUTABLE, REPLACE");
                break;
            }
        }

    }

}