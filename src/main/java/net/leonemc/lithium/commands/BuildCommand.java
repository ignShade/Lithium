package net.leonemc.lithium.commands;

import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.lithium.Lithium;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class BuildCommand {

    @Command(names = "build", permission = "lithium.build")
    public static void buildMode(Player sender, @Param(name = "target", defaultValue = "self") Player target) {
        if (target.hasMetadata("build")) {
            target.removeMetadata("build", Lithium.getInstance());
            sender.sendMessage("§cBuild mode disabled.");
        } else {
            target.setMetadata("build", new FixedMetadataValue(Lithium.getInstance(), true));
            sender.sendMessage("§aBuild mode enabled.");
        }
    }
}
