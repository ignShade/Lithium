package net.leonemc.lithium.task;

import net.leonemc.lithium.utils.bukkit.RegionUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AFKRegionTask extends BukkitRunnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (RegionUtil.isWithinRegion(player, "afk")) {
                player.sendMessage("§eYou've been given a key for afk-ing!");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cr givekey " + player.getName() + " AFK 1");
            }
        });
    }
}
