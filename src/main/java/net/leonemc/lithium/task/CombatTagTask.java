package net.leonemc.lithium.task;

import net.leonemc.lithium.utils.Cooldown;
import net.leonemc.lithium.utils.bukkit.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class CombatTagTask extends BukkitRunnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (Cooldown.isOnCooldown("combat", player)) {
                PlayerUtil.sendActionbar(player, "§cCombat Tag: §f" + Cooldown.getCooldownForPlayerInt("combat", player) + "s");
            }
        });
    }
}
