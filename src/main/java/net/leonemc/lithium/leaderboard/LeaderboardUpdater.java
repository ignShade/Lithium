package net.leonemc.lithium.leaderboard;

import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.storage.Yaml;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.logging.Level;

public class LeaderboardUpdater extends BukkitRunnable {

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        Lithium.getInstance().getLogger().log(Level.INFO, "Starting to fetch leaderboard");
        CachedLeaderboard cachedLeaderboard = Lithium.getInstance().getCachedLeaderboard();

        cachedLeaderboard.setDeathLeaderboard(getLeaderboardFromKey("deaths"));
        cachedLeaderboard.setKillLeaderboard(getLeaderboardFromKey("kills"));
        cachedLeaderboard.setKillstreakLeaderboard(getLeaderboardFromKey("highestKillstreak"));
        cachedLeaderboard.setLevelLeaderboard(getLeaderboardFromKey("level"));

        Lithium.getInstance().getLogger().log(Level.INFO, "Cached leaderboard in " + (System.currentTimeMillis() - time) + "ms");
    }

    public static LinkedHashMap<String, Integer> sortByValues(Map<String, Integer> map) {
        LinkedList<java.util.Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());

        Collections.sort(list, (o1, o2) -> (o2.getValue().compareTo(o1.getValue())));

        LinkedHashMap<String, Integer> sortedHashMap = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : list) {
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }

        return (sortedHashMap);
    }

    public static Map<String, Integer> getLeaderboardFromKey(String key) {
        Map<String, Integer> allPlayers = sortByValues(getAllByKey(key));
        Map<String, Integer> lb = new HashMap<>();

        int index = 0;

        for (Map.Entry<String, Integer> scores : allPlayers.entrySet()) {
            if (10 <= index) {
                break;
            }

            lb.put(scores.getKey(), scores.getValue());
            index++;
        }

        return sortByValues(lb);
    }

    public static Map<String, Integer> getAllByKey(String key) {
        Map<String, Integer> counts = new HashMap<>();

        for (Yaml data : Lithium.getInstance().getData().getAllPlayersYaml()) {
            if (data.getString("name") == null) {
                continue;
            }

            counts.put(data.getString("name"), data.getInteger(key));
        }

        return counts;
    }
}