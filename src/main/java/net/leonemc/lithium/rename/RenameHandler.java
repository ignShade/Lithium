package net.leonemc.lithium.rename;

import net.leonemc.api.player.APIPlayer;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.utils.Cooldown;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RenameHandler {

    private List<String> names = Lithium.getInstance().getConfig().getStringList("renamer.names");

    public boolean hasEnough(Player player) {
        Economy economy = Lithium.getInstance().getEcon();
        return economy.getBalance(player) >= Lithium.getInstance().getConfig().getInt("renamer.cost");
    }

    public void refund(Player player) {
        Economy economy = Lithium.getInstance().getEcon();
        economy.depositPlayer(player, Lithium.getInstance().getConfig().getInt("renamer.cost"));
    }

    public void deduct(Player player) {
        Economy economy = Lithium.getInstance().getEcon();
        economy.withdrawPlayer(player, Lithium.getInstance().getConfig().getInt("renamer.cost"));
    }

    public void startRename(Player player, ItemStack itemStack) {
        if (names.isEmpty()) {
            player.sendMessage("§cAn error has occurred... Please contact an administrator!");
            if (player.isOp()) player.sendMessage("§cThe list of names is empty!");
            return;
        }

        if (Cooldown.isOnCooldown("renamer", player)) {
            player.sendMessage("§cYou cannot do this yet... Please wait!");
            return;
        }

        if (itemStack == null) {
            player.sendMessage("§cYou must be holding an item!");
            return;
        }

        if (!(itemStack.getType().name().contains("AXE") || itemStack.getType().name().contains("SWORD"))) {
            player.sendMessage("§cYou must be holding an axe or sword!");
            return;
        }

        Cooldown.addCooldown("renamer", player, 8);
        deduct(player);

        new RenameTask(player).runTaskTimer(Lithium.getInstance(), 20, 20);
    }

    public void sendDislike(Player player) {
        APIPlayer apiPlayer = new APIPlayer(player);

        List<String> dislikers = Lithium.getInstance().getConfig().getStringList("renamer.dislike");
        String randomDislike = dislikers.get((int) (Math.random() * dislikers.size()));
        String randomName = names.get((int) (Math.random() * names.size()));
        apiPlayer.sendActionbar(ChatColor.translateAlternateColorCodes('&', randomDislike.replace("%name%", randomName)));
    }

    public void rename(Player player) {
        APIPlayer apiPlayer = new APIPlayer(player);
        ItemStack itemStack = player.getItemInHand();

        if (itemStack == null) {
            apiPlayer.sendActionbar("§cYou must be holding an item!");

            Cooldown.removeCooldown("renamer", player);
            return;
        }

        if (itemStack.getType().name().contains("AXE") || itemStack.getType().name().contains("SWORD")) {
            String randomName = names.get((int) (Math.random() * names.size()));

            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', randomName));
            itemStack.setItemMeta(itemMeta);

            String randomLiker = Lithium.getInstance().getConfig().getStringList("renamer.like").get((int) (Math.random() * Lithium.getInstance().getConfig().getStringList("renamer.like").size()));
            apiPlayer.sendActionbar(ChatColor.translateAlternateColorCodes('&', randomLiker.replace("%name%", randomName)));

            player.playSound(player.getLocation(), Sound.ANVIL_USE, 1, 1);
        } else {
            apiPlayer.sendActionbar("§cYou must be holding a sword or axe!");
            refund(player);
        }
        Cooldown.removeCooldown("renamer", player);
    }
}
