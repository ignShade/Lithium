package net.leonemc.lithium.commands.reports;

import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.lithium.reports.menu.ReportMenu;
import net.leonemc.lithium.utils.Cooldown;
import org.bukkit.entity.Player;

public class ReportCommand {

    @Command(names = "report")
    public static void onCommand(Player sender,
                                 @Param(name = "target") Player target,
                                 @Param(name = "reason", wildcard = true) String reason) {

        if (Cooldown.isOnCooldown("report", sender)) {
            int cooldown = Cooldown.getCooldownForPlayerInt("report", sender);
            sender.sendMessage("§cYou must wait " + cooldown + " seconds before using this command again.");
            return;
        }

        new ReportMenu(sender, target, reason).openMenu(sender);
    }
}