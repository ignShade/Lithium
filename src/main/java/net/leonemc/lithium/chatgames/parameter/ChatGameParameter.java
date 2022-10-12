package net.leonemc.lithium.chatgames.parameter;

import net.leonemc.api.command.data.parameter.ParameterType;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.chatgames.object.ChatGame;
import net.leonemc.lithium.customitems.CustomItem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ChatGameParameter implements ParameterType<ChatGame> {

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull Player player, @NotNull Set<String> set, @NotNull String s) {
        for (ChatGame item : Lithium.getInstance().getChatGameHandler().getChatGames()) {
            if (item.getName().startsWith(s)) {
                set.add(item.getName());
            }
        }

        return Collections.emptyList();
    }

    @Nullable
    @Override
    public ChatGame transform(@NotNull CommandSender commandSender, @NotNull String s) {
        ChatGame game = Lithium.getInstance().getChatGameHandler().getByName(s);

        if (game == null) {
            commandSender.sendMessage("§cGame not found.");
            return null;
        }

        return game;
    }
}
