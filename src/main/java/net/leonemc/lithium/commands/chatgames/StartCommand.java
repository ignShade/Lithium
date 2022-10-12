package net.leonemc.lithium.commands.chatgames;


import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.chatgames.ChatGameHandler;
import net.leonemc.lithium.chatgames.object.ChatGame;
import org.bukkit.command.CommandSender;

public class StartCommand {

    @Command(names = "chatgame start", permission = "*")
    public static void execute(CommandSender sender, @Param(name = "game") ChatGame game) {
        Lithium plugin = Lithium.getInstance();
        ChatGameHandler handler = plugin.getChatGameHandler();

        if (handler.isActive()) {
            sender.sendMessage("§cA game is already running.");
            return;
        }

        handler.startChatGame(game);
    }

    @Command(names = "chatgame startrandom", permission = "*")
    public static void executeRandom(CommandSender sender) {
        Lithium plugin = Lithium.getInstance();
        ChatGameHandler handler = plugin.getChatGameHandler();

        if (handler.isActive()) {
            sender.sendMessage("§cA game is already running.");
            return;
        }

        handler.startGame();
    }

}
