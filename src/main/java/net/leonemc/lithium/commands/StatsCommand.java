package net.leonemc.lithium.commands;

import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.profiles.Profile;
import net.leonemc.lithium.profiles.ProfileManager;
import net.leonemc.lithium.utils.LevelColorUtil;
import net.leonemc.lithium.utils.command.Command;
import net.leonemc.lithium.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.UUID;

public class StatsCommand {

    @Command(name = "stats", async = true)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        String targetName;
        UUID targetUUID;

        boolean fetchingOffline;

        if (args.getArgs().length == 0) {
            Player senderPlayer = (Player) sender;
            targetName = senderPlayer.getName();
            targetUUID = senderPlayer.getUniqueId();

            fetchingOffline = false;
        } else {
            Player target = Bukkit.getPlayer(args.getArgs(0));

            if (target == null) {
                sender.sendMessage("§aAttempting to fetch stats for offline player: " + args.getArgs(0));
                OfflinePlayer offline = Bukkit.getOfflinePlayer(args.getArgs(0));

                if (offline == null) {
                    sender.sendMessage("§cPlayer not found.");
                    return;
                }

                targetName = offline.getName();
                targetUUID = offline.getUniqueId();

                fetchingOffline = true;
            } else {
                targetUUID = target.getUniqueId();
                targetName = target.getName();

                fetchingOffline = false;
            }
        }

        ProfileManager profileManager = Lithium.getInstance().getProfileManager();

        Profile profile;

        if (fetchingOffline)
            profile = profileManager.getProfile(targetUUID);
        else {
            profile = profileManager.getOfflineProfile(targetUUID, targetName);
        }

        if (profile == null) {
            sender.sendMessage("§cProfile not found.");
            return;
        }

        DecimalFormat decimalFormat = new DecimalFormat("##.00");

        sender.sendMessage("§7§m-----------------------------------------------------");
        sender.sendMessage("§6§lStats of " + targetName);
        sender.sendMessage("  §eKills: §f" + profile.getKills());
        sender.sendMessage("  §eDeaths: §f" + profile.getDeaths());
        sender.sendMessage("  §eKDR: §f" + decimalFormat.format(getKDR(profile)));
        sender.sendMessage("  §eLevel: §f" + LevelColorUtil.getLevelColored(profile.getLevel(), 0));
        sender.sendMessage("  §eXP: §f" + profile.getXp());
        sender.sendMessage("  §eKillstreak: §f" + profile.getKillstreak());
        sender.sendMessage("  §eHighest Killstreak: §f" + profile.getHighestKillstreak());
        sender.sendMessage("§7§m-----------------------------------------------------");
    }

    public double getKDR(Profile profile) {
        if (profile.getDeaths() == 0) {
            return profile.getKills();
        }

        return (double) profile.getKills() / (double) profile.getDeaths();
    }
}
