package net.leonemc.lithium.task;

import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.leaderboard.CachedLeaderboard;
import net.leonemc.lithium.utils.LevelColorUtil;
import net.leonemc.lithium.utils.entity.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class LeaderboardRunnable extends BukkitRunnable {

    int leaderboard = 0;

    @Override
    public void run() {
        FileConfiguration config = Lithium.getInstance().getConfig();
        Location location = new Location(
                Bukkit.getWorld(config.getString("leaderboard.world")),
                config.getDouble("leaderboard.x"),
                config.getDouble("leaderboard.y"),
                config.getDouble("leaderboard.z")
        );

        if (location.getWorld() == null) {
            return;
        }

        ArrayList<String> hologram = new ArrayList<>();
        CachedLeaderboard cachedLeaderboard = Lithium.getInstance().getCachedLeaderboard();

        leaderboard++;

        if (leaderboard >= 5)
            leaderboard = 1;

        switch (leaderboard) {
            case 1: {
                hologram.add("§6§lKill Leaderboard");
                hologram.add(" ");

                AtomicInteger i = new AtomicInteger(1);
                cachedLeaderboard.killLeaderboard.forEach((place, statistic) -> {
                    hologram.add("§e#" + i + " §f" + place + " §7- §e" + statistic);
                    i.getAndIncrement();
                });
                break;
            }
            case 2: {
                hologram.add("§6§lDeath Leaderboard");
                hologram.add(" ");

                AtomicInteger i = new AtomicInteger(1);
                cachedLeaderboard.deathLeaderboard.forEach((place, statistic) -> {
                    hologram.add("§e#" + i + " §f" + place + " §7- §e" + statistic);
                    i.getAndIncrement();
                });
                break;
            }

            case 3: {
                hologram.add("§6§lKillstreak Leaderboard");
                hologram.add(" ");

                AtomicInteger i = new AtomicInteger(1);
                cachedLeaderboard.killstreakLeaderboard.forEach((place, statistic) -> {
                    hologram.add("§e#" + i + " §f" + place + " §7- §e" + statistic);
                    i.getAndIncrement();
                });
                break;
            }

            case 4: {
                hologram.add("§6§lLevel Leaderboard");
                hologram.add(" ");

                AtomicInteger i = new AtomicInteger(1);
                cachedLeaderboard.levelLeaderboard.forEach((place, statistic) -> {
                    hologram.add("§e#" + i + " §f" + place + " §7- §e" + LevelColorUtil.getLevelColored(statistic, 0));
                    i.getAndIncrement();
                });
                break;
            }
        }

        //converting kt code to java is fun :D
        final Location[] loc = {location.clone()};
        hologram.forEach(it -> {
            Hologram holo = new Hologram(it, loc[0]);
            holo.showAllTemp(10 * 20);
            loc[0] = loc[0].subtract(0.0, 0.35, 0.0);
        });
    }
}
