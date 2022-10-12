package net.leonemc.lithium.commands;

import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.showcase.ShowcaseHandler;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PreviewInventoryCommand {

    @Command(names = "previewinventory")
    public static void previewInventory(Player sender, @Param(name = "uuid") String target) {
        ShowcaseHandler showcaseHandler = Lithium.getInstance().getShowcaseHandler();
        UUID uuid = UUID.fromString(target);

        showcaseHandler.open(sender, uuid);
    }

}
