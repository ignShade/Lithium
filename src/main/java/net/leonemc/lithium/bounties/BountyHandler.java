package net.leonemc.lithium.bounties;

import lombok.Getter;
import net.leonemc.lithium.Lithium;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BountyHandler {

    private final ArrayList<Bounty> bounties = new ArrayList<>();

    public void addBounty(Bounty bounty) {
        this.bounties.add(bounty);
    }

    public void removeBounty(Bounty bounty) {
        this.bounties.remove(bounty);
    }

    public void removeBounty(Player player) {
        this.bounties.removeIf(bounty -> bounty.getTarget().equals(player.getName()));
    }

    public void removeBounty(Player player, Player killer) {
        List<Bounty> bountiesForPlayer = this.bounties.stream().filter(bounty -> bounty.getTarget().equals(player.getName())).collect(Collectors.toList());
        int combinedBalance = 0;

        for (Bounty bounty : bountiesForPlayer) {
            combinedBalance += bounty.getBalance();
        }

        Economy economy = Lithium.getInstance().getEcon();

        if (killer != null) {
            economy.depositPlayer(killer, combinedBalance);
            Bukkit.broadcastMessage("§6§lBounty §8» §f" + killer.getName() + " §ehas killed §c" + player.getName() + " §eand collected §f" + combinedBalance + "$§e!");
        }

        bounties.removeAll(bountiesForPlayer);
    }

    public boolean hasBounty(Player target) {
        return this.bounties.stream().anyMatch(bounty -> bounty.getTarget().equals(target.getName()));
    }
}
