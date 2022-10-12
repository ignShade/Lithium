package net.leonemc.lithium.utils;

import net.leonemc.api.player.APIPlayer;
import net.leonemc.lithium.Lithium;
import org.bukkit.entity.Player;

public class RankUtil {

    public static String getRankName(Player player) {
        return Lithium.getInstance().getChat().getPlayerSuffix(player).replaceAll("&", "§") + Lithium.getInstance().getPerms().getPrimaryGroup(player);
    }

    public static String getRankColor(Player player) {
        APIPlayer apiPlayer = new APIPlayer(player);

        if (apiPlayer.isDisguised()) {
            return "§a";
        }

        return Lithium.getInstance().getChat().getPlayerSuffix(player).replaceAll("&", "§");
    }

    public static String getRankPrefix(Player player) {
        APIPlayer apiPlayer = new APIPlayer(player);

        if (apiPlayer.isDisguised()) {
            return " §7[§aMember§7] §8|§7 ";
        }

        return Lithium.getInstance().getChat().getPlayerPrefix(player).replaceAll("&", "§");
    }

}
