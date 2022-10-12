package net.leonemc.lithium.commands;

import net.leonemc.lithium.leaderboard.menu.LeaderboardSelectionMenu;
import net.leonemc.lithium.utils.command.Command;
import net.leonemc.lithium.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class LeaderboardCommand {

    @Command(name = "leaderboard")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        new LeaderboardSelectionMenu().openMenu(player);
    }

}
