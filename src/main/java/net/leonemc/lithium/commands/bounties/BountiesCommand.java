package net.leonemc.lithium.commands.bounties;

import net.leonemc.lithium.bounties.menu.BountyMenu;
import net.leonemc.lithium.utils.command.Command;
import net.leonemc.lithium.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class BountiesCommand {

    @Command(name = "bounties")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        new BountyMenu().openMenu(player);
    }

}
