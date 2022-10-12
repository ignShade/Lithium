package net.leonemc.lithium.profiles;

import lombok.Getter;
import net.leonemc.lithium.Lithium;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.logging.Level;
import java.util.regex.Pattern;

@Getter
public class ProfileManager {

    private final Map<UUID, Profile> profiles = new HashMap<>();

    public void handleProfileCreation(UUID uuid, String name) {
        Lithium.getInstance().getLogger().log(Level.INFO, "Loading profile " + uuid);

        if (!this.profiles.containsKey(uuid)) {
            profiles.put(uuid, new Profile(uuid, name));
        }
    }

    public Profile getOfflineProfile(UUID uuid, String name) {
        Profile profile = new Profile(uuid, name);
        profile.load();

        return profile;
    }

    public void unloadProfile(UUID uuid) {
        Lithium.getInstance().getLogger().log(Level.INFO, "Unloading profile " + uuid);

        if (!profiles.containsKey(uuid)) {
            profiles.remove(uuid);
            return;
        }

        try {
            profiles.get(uuid).save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        profiles.remove(uuid);
    }

    public Profile createProfile(UUID uuid, String name) {
        Lithium.getInstance().getLogger().log(Level.INFO, "Loading profile " + uuid);


        if (!this.profiles.containsKey(uuid)) {
            profiles.put(uuid, new Profile(uuid, name));
        }

        return profiles.get(uuid);
    }

    public Profile getProfile(UUID uuid) {
        if (profiles.containsKey(uuid)) {
            return profiles.get(uuid);
        }
        return null;
    }

    public Profile getProfile(Player player) {
        return getProfile(player.getUniqueId());
    }
}
