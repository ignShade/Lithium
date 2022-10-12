package net.leonemc.lithium.tips;

import net.leonemc.lithium.Lithium;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TipsManager {

    public TipsManager() {
        new BukkitRunnable() {
            @Override
            public void run() {
                handleTip();
            }
        }.runTaskTimer(Lithium.getInstance(), 0, 20 * 60);
    }

    public void handleTip() {
        FileConfiguration config = Lithium.getInstance().getConfig();
        List<String> tips = config.getStringList("auto-broadcast.messages");
        String message = tips.get(RandomUtils.nextInt(0, tips.size()));

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
