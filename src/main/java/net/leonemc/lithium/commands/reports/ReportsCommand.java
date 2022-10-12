package net.leonemc.lithium.commands.reports;

import net.leonemc.lithium.reports.menu.ReportsMenu;
import net.leonemc.lithium.utils.command.Command;
import net.leonemc.lithium.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class ReportsCommand {

    @Command(name = "reports", permission = "rank.staff")
    public void onCommand(CommandArgs args) {
        Player sender = args.getPlayer();

        new ReportsMenu().openMenu(sender);
    }
}
