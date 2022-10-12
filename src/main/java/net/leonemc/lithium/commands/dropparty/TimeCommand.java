package net.leonemc.lithium.commands.dropparty;

import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.dropparty.DropPartyHandler;
import net.leonemc.lithium.utils.menu.Button;
import net.leonemc.lithium.utils.menu.pagination.PaginatedMenu;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TimeCommand {

    @Command(names = {"dropparty settime", "dp settime"}, permission = "*")
    public static void executeAdd(Player player, @Param(name = "time") double time) {
        FileConfiguration config = Lithium.getInstance().getConfig();
        Lithium plugin = Lithium.getInstance();
        DropPartyHandler handler = Lithium.getInstance().getDropPartyHandler();

        handler.setDelaySeconds(time);
        config.set("drop-party.delay-seconds", time);

        plugin.saveConfig();

        player.sendMessage("§aSet delay to " + time + " seconds...");
    }
}
