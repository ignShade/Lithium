package net.leonemc.lithium.profiles.task;

import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.profiles.Profile;
import org.bukkit.scheduler.BukkitRunnable;

public class ProfileSaveRunnable extends BukkitRunnable {

    @Override
    public void run() {
        Lithium.getInstance().getProfileManager().getProfiles().values().forEach(Profile::save);
    }
}
