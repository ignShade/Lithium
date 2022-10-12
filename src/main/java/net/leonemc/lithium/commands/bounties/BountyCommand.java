package net.leonemc.lithium.commands.bounties;

import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.bounties.Bounty;
import net.leonemc.lithium.bounties.menu.BountyMenu;
import net.leonemc.lithium.utils.command.Command;
import net.leonemc.lithium.utils.command.CommandArgs;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BountyCommand {

    @Command(name = "bounty")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        Economy economy = Lithium.getInstance().getEcon();

        if (args.getArgs().length != 2) {
            args.getSender().sendMessage("§cUsage: /bounty <player> <amount>");
            return;
        }

        String target = args.getArgs()[0];

        double amount;
        try {
            amount = Double.parseDouble(args.getArgs()[1]);
        } catch (NumberFormatException e) {
            player.sendMessage("\"" + args.getArgs()[1] + "\" §cisn't a number.");
            return;
        }

        if (amount < 25) {
            player.sendMessage("§cBounties must be at least $500.");
            return;
        }

        if (economy.getBalance(player) < amount) {
            player.sendMessage("§cYou don't have enough money.");
            return;
        }

        economy.withdrawPlayer(player, amount);
        Lithium.getInstance().getBountyHandler().addBounty(new Bounty(target, player.getName(), (int) amount));

        Bukkit.broadcastMessage("§6§lBounty §8» §f" + player.getName() + " §ehas placed a bounty on §c" + target + " §efor §a$" + amount + "§e.");
    }
}
