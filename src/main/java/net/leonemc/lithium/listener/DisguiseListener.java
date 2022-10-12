package net.leonemc.lithium.listener;

import net.leonemc.api.events.PlayerDisguiseEvent;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.profiles.Profile;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DisguiseListener implements Listener {

    @EventHandler
    public void onDisguise(PlayerDisguiseEvent event) {
        Player player = event.getPlayer();
        Profile profile = Lithium.getInstance().getProfileManager().getProfile(player);

        profile.setDisguisedDeaths(0);
        profile.setDisguisedKills(0);
        profile.setDisguisedKillstreak(0);
        profile.setDisguisedLevel(RandomUtils.nextInt(0, 8));
    }
}
