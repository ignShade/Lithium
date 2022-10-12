package net.leonemc.lithium.task;

import net.leonemc.lithium.utils.bukkit.PlayerUtil;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class ModModeTask extends BukkitRunnable {

    @Override
    public void run() {
        List<Player> modmoded = Bukkit.getOnlinePlayers().stream().filter(player -> player.hasMetadata("modmode")).collect(Collectors.toList());

        for (Player player : modmoded) {
            double tps = MinecraftServer.getServer().recentTps[0];
            PlayerUtil.sendActionbar(player,
                    "§fVanish: §b" + (player.hasMetadata("vanish") ? "§aOn" : "§cOff") +
                            " §7\u2503 §fTPS: §b" + formatBasicTps(tps) + " §7\u2503 " +
                    "§fStaff Chat: §b" + (player.hasMetadata("staffchat") ? "§aOn" : "§cOff")
            );
        }
    }


    private String formatBasicTps(final double tps) {
        return ((tps > 18.0) ? ChatColor.AQUA : ((tps > 16.0) ? ChatColor.YELLOW : ChatColor.RED)).toString() + Math.min(Math.round(tps * 10.0) / 10.0, 20.0);
    }
}
