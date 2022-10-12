package net.leonemc.lithium.modmode;

import net.leonemc.lithium.utils.bukkit.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ModModeHandler {

    private final HashMap<Player, PlayerInventory> inventories = new HashMap<>();

    public void enableModMode(Player player) {
        PlayerInventory inventory = new PlayerInventory(player.getInventory().getContents(), player.getInventory().getArmorContents());
        inventories.put(player, inventory);

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);

        player.setGameMode(GameMode.CREATIVE);

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!online.hasPermission("rank.staff")) {
                online.hidePlayer(player);
            }
        }

        player.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("§aTeleporter").toItemStack());
        player.getInventory().setItem(1, new ItemBuilder(Material.BOOK).setName("§aInventory Inspector").toItemStack());
        player.getInventory().setItem(8, new ItemBuilder(Material.SKULL_ITEM).setName("§aReports").toItemStack());
    }

    public void disableModMode(Player player) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.showPlayer(player);
        }

        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        PlayerInventory inventory = inventories.get(player);
        if (inventory != null) {
            player.getInventory().setContents(inventory.getInventory());
            player.getInventory().setArmorContents(inventory.getArmor());
        }

    }

}
