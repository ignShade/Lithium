package net.leonemc.lithium.utils.bukkit;

import net.leonemc.lithium.Lithium;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ServerUtil {

    public static void runCommandSync(String command) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
        }.runTask(Lithium.getInstance());
    }

    public static void sendToStaff(String s) {
        List<Player> staff = Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("rank.staff")).collect(java.util.stream.Collectors.toList());
        staff.forEach(p -> p.sendMessage(s));
    }

    public static void sendToDevs(String s) {
        List<Player> staff = Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("rank.dev")).collect(java.util.stream.Collectors.toList());
        staff.forEach(p -> p.sendMessage(s));
    }
}
