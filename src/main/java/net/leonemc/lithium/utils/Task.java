package net.leonemc.lithium.utils;


import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;

public final class Task {
    private static JavaPlugin plugin;
    private static BukkitScheduler scheduler;

    public Task(JavaPlugin plugin) {
        Task.plugin = plugin;
        scheduler = plugin.getServer().getScheduler();
    }

    public static BukkitTask runTaskLater(Runnable run, long delay, TimeUnit unit) {
        return Bukkit.getScheduler().runTaskLater(plugin, run, unit.toSeconds(delay) * 20L);
    }

    public static BukkitTask runTaskTimerAsync(Runnable run, long start, long repeat) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, run, start, repeat);
    }

    public static int scheduleSyncRepeatingTask(Runnable run, long start, long repeat) {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)plugin, run, start, repeat);
    }
}
