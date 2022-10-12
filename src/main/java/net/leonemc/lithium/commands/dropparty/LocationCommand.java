package net.leonemc.lithium.commands.dropparty;

import net.leonemc.api.command.Command;
import net.leonemc.duels.utils.LocationUtil;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.dropparty.DropPartyHandler;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class LocationCommand {

    @Command(names = {"dropparty seta", "dp seta"}, permission = "*")
    public static void executeA(Player player) {
        Location location = player.getLocation();
        FileConfiguration config = Lithium.getInstance().getConfig();
        DropPartyHandler handler = Lithium.getInstance().getDropPartyHandler();


        config.set("drop-party.locA", LocationUtil.INSTANCE.serialize(location));
        handler.setLocA(location);

        Lithium.getInstance().saveConfig();
        player.sendMessage("§aDrop party location A set to your location.");
    }


    @Command(names = {"dropparty setb", "dp setb"}, permission = "*")
    public static void executeB(Player player) {
        Location location = player.getLocation();
        FileConfiguration config = Lithium.getInstance().getConfig();
        DropPartyHandler handler = Lithium.getInstance().getDropPartyHandler();


        config.set("drop-party.locB", LocationUtil.INSTANCE.serialize(location));
        handler.setLocB(location);

        Lithium.getInstance().saveConfig();
        player.sendMessage("§aDrop party location B set to your location.");
    }
}
