package net.leonemc.lithium.staffchat;

import net.leonemc.api.API;
import net.leonemc.api.player.APIPlayer;
import net.leonemc.lithium.utils.RankUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StaffChatHandler {

    public void handleStaffchat(Player sender, String message) {
        List<Player> receivers = Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("rank.staff")).collect(Collectors.toList());

        APIPlayer apiPlayer = new APIPlayer(sender);
        String username = apiPlayer.isDisguised() ? Objects.requireNonNull(API.getInstance().disguiseHandler.getDisguise(sender)).getOriginalName() : sender.getName();

        for (Player player : receivers) {
            player.sendMessage("§9[Staff] " + RankUtil.getRankColor(sender) + sender.getName() + "§7: §f" + message);
        }
    }

}
