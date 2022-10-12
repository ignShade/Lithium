package net.leonemc.lithium.commands;

import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.utils.command.Command;
import net.leonemc.lithium.utils.command.CommandArgs;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetLeaderboardCommand {

    @Command(name = "setleaderboard", permission = "*")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        Location location = player.getLocation();
        FileConfiguration config = Lithium.getInstance().getConfig();

        config.set("leaderboard.x", location.getX());
        config.set("leaderboard.y", location.getY());
        config.set("leaderboard.z", location.getZ());
        config.set("leaderboard.world", location.getWorld().getName());

        Lithium.getInstance().saveConfig();
        Lithium.getInstance().reloadConfig();

        player.sendMessage("§aLeaderboard location set.");
    }

}
