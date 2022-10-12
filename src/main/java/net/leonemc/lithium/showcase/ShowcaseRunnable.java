package net.leonemc.lithium.showcase;

import net.leonemc.lithium.Lithium;
import org.bukkit.scheduler.BukkitRunnable;

public class ShowcaseRunnable extends BukkitRunnable {


    @Override
    public void run() {
        ShowcaseHandler showcaseHandler = Lithium.getInstance().getShowcaseHandler();
        showcaseHandler.getInventories().entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
}
