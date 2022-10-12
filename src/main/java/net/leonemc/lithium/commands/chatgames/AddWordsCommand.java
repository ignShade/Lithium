package net.leonemc.lithium.commands.chatgames;


import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.chatgames.ChatGameHandler;
import net.leonemc.lithium.chatgames.object.ChatGame;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class AddWordsCommand {

    @Command(names = "chatgame addscrambleword", permission = "*")
    public static void executeScramble(CommandSender sender, @Param(name = "word", wildcard = true) String word) {
        Lithium plugin = Lithium.getInstance();
        ChatGameHandler handler = plugin.getChatGameHandler();
        FileConfiguration config = plugin.getConfig();

        List<String> words = Lithium.getInstance().getConfig().getStringList("chat-games.scramble.words");
        words.add(word);
        Lithium.getInstance().getConfig().set("chat-games.first-to-say.words", words);

        plugin.saveConfig();
        handler.reload();

        sender.sendMessage("§aAdded word to scramble game.");
    }

    @Command(names = "chatgame addsayword", permission = "*")
    public static void executeFTS(CommandSender sender, @Param(name = "word", wildcard = true) String word) {
        Lithium plugin = Lithium.getInstance();
        ChatGameHandler handler = plugin.getChatGameHandler();
        FileConfiguration config = plugin.getConfig();

        List<String> words = Lithium.getInstance().getConfig().getStringList("chat-games.first-to-say.words");
        words.add(word);
        Lithium.getInstance().getConfig().set("chat-games.first-to-say.words", words);

        plugin.saveConfig();
        handler.reload();

        sender.sendMessage("§aAdded word to first to say game.");
    }

}
