package net.leonemc.lithium.task;

import net.leonemc.lithium.utils.bukkit.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class ItemClearTask extends BukkitRunnable {

    @Override
    public void run() {
        List<Entity> items = Bukkit.getWorld("world").getEntities().stream().filter(entity -> entity.getType() == org.bukkit.entity.EntityType.DROPPED_ITEM).collect(Collectors.toList());
        items.forEach(Entity::remove);
    }
}
