package net.leonemc.lithium.commands.dropparty;

import net.leonemc.api.command.Command;
import net.leonemc.duels.utils.LocationUtil;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.dropparty.DropPartyHandler;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class StartCommand {


    @Command(names = {"dropparty start", "dp start"}, permission = "*")
    public static void executeA(Player player) {
        DropPartyHandler handler = Lithium.getInstance().getDropPartyHandler();
        handler.start();

        player.sendMessage("§aDrop party started.");
    }
}
