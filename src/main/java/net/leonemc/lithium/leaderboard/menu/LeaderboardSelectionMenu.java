package net.leonemc.lithium.leaderboard.menu;

import net.leonemc.lithium.leaderboard.LeaderboardType;
import net.leonemc.lithium.utils.bukkit.ItemBuilder;
import net.leonemc.lithium.utils.menu.Button;
import net.leonemc.lithium.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardSelectionMenu extends Menu {

    @Override
    public String getTitle(Player player) {
        return "Leaderboards";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();

        buttons.put(3, new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.DIAMOND_SWORD).setName("§eKill Leaderboard").setLore("§7Click to view the kill leaderboard").toItemStack();
            }

            @Override
            public void clicked(Player player, ClickType clickType) {
                new LeaderboardMenu(LeaderboardType.KILLS).openMenu(player);
            }
        });

        buttons.put(4, new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.SKULL_ITEM).setName("§eDeath Leaderboard").setLore("§7Click to view the death leaderboard").toItemStack();
            }

            @Override
            public void clicked(Player player, ClickType clickType) {
                new LeaderboardMenu(LeaderboardType.DEATHS).openMenu(player);
            }
        });


        buttons.put(5, new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.GOLD_SWORD).setName("§eKillstreak Leaderboard").setLore("§7Click to view the killstreak leaderboard").toItemStack();
            }

            @Override
            public void clicked(Player player, ClickType clickType) {
                new LeaderboardMenu(LeaderboardType.KILLSTREAK).openMenu(player);
            }
        });


        return buttons;
    }

    @Override
    public int size(Player player) {
        return 9;
    }
}
