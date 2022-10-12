package net.leonemc.lithium.profiles;

import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.utils.RankUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.logging.Level;

public class ProfileListener implements Listener {

    private final Lithium plugin = Lithium.getInstance();

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        Player player = Bukkit.getPlayer(event.getUniqueId());

        if (player != null && player.isOnline()) {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(ChatColor.RED + "You tried to login too quickly after disconnecting.\nTry again in a few seconds.");
            return;
        }

        ProfileManager profileManager = plugin.getProfileManager();
        Profile profile = profileManager.createProfile(event.getUniqueId(), event.getName());

        try {
            profile.load();
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to load profile for " + event.getName(), e);
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Failed to load profile, please attempt logging in again. If this issue persists please contact the server administrators on our discord \n https://discord.gg/XvYhxPY79J");
            return;
        }

        Bukkit.getScheduler().runTaskLaterAsynchronously(Lithium.getInstance(), () -> {
            Player player1 = Bukkit.getPlayer(event.getUniqueId());
            if (player1 == null) {
                profileManager.unloadProfile(event.getUniqueId());
            }
        }, 20 * 20L);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        ProfileManager profileManager = plugin.getProfileManager();
        Profile profile = profileManager.getProfile(event.getPlayer());

        if (profile == null) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cYour profile data was not loaded at the pre login stage, please try logging in again.");
            return;
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        ProfileManager profileManager = plugin.getProfileManager();
        Profile profile = profileManager.getProfile(event.getPlayer());
        Player player = event.getPlayer();

        if (profile == null) {
            player.kickPlayer("§cYour profile data was not loaded at the pre login stage, please try logging in again.");
            return;
        }

        event.setJoinMessage(player.hasPlayedBefore() ? "§8» §f" + RankUtil.getRankColor(player) + player.getName() + " §ehas joined the server." : "§8» §f" + RankUtil.getRankColor(player) + player.getName() + " §ejoined the server for the first time. §7[#" + Bukkit.getOfflinePlayers().length + "]");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ProfileManager profileManager = plugin.getProfileManager();
        Profile profile = profileManager.getProfile(player);

        event.setQuitMessage("§8» §f" + RankUtil.getRankColor(player) + player.getName() + " §ehas left the server.");

        Bukkit.getScheduler().runTaskAsynchronously(Lithium.getInstance(), () -> {
            profile.save();

            Player player1 = Bukkit.getPlayer(profile.getUuid());

            //They might have already re-logged before everything is saved, so don't unload their profile if they're online on the server.
            if (player1 == null) {
                profileManager.unloadProfile(profile.getUuid());
            }
        });
    }
}
