package net.leonemc.lithium.leaderboard.menu;

import lombok.AllArgsConstructor;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.leaderboard.LeaderboardType;
import net.leonemc.lithium.utils.bukkit.ItemBuilder;
import net.leonemc.lithium.utils.menu.Button;
import net.leonemc.lithium.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class LeaderboardMenu extends Menu {

    private LeaderboardType leaderboardType;

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        final HashMap<Integer, Button> buttons = new HashMap<>();
        Map<String, Integer> scores = new HashMap<>();
        final Lithium plugin = Lithium.getInstance();


        switch (leaderboardType) {
            case KILLS:
                scores = plugin.getCachedLeaderboard().getKillLeaderboard();
                break;
            case DEATHS:
                scores = plugin.getCachedLeaderboard().getDeathLeaderboard();
                break;
            case KILLSTREAK:
                scores = plugin.getCachedLeaderboard().getKillstreakLeaderboard();
                break;
            default:
                return buttons;
        }

        scores.forEach((name, score) -> buttons.put(buttons.size(), new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.SKULL_ITEM).setDurability((short) 3).setName("§7#" + buttons.size() + " §f" + name).setLore("§e" + leaderboardType.getName() + "§7: §f" + score).toItemStack();
            }
        }));

        return buttons;
    }

    @Override
    public String getTitle(Player player) {
        return leaderboardType.getName() + " Leaderboard";
    }

    @Override
    public int size(Player player) {
        return 9;
    }
}
