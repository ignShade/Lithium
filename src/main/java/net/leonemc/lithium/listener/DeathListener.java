package net.leonemc.lithium.listener;

import me.imsanti.leoneevents.player.SimpleProfile;
import net.leonemc.api.player.APIPlayer;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.bounties.BountyHandler;
import net.leonemc.lithium.cosmetics.CosmeticHandler;
import net.leonemc.lithium.events.CustomPlayerDeathEvent;
import net.leonemc.lithium.profiles.Profile;
import net.leonemc.lithium.profiles.ProfileManager;
import net.leonemc.lithium.utils.Cooldown;
import net.leonemc.lithium.utils.LevelColorUtil;
import net.leonemc.lithium.utils.RankUtil;
import net.leonemc.lithium.utils.bukkit.PlayerUtil;
import net.milkbowl.vault.economy.Economy;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;

import static net.leonemc.api.util.text.TextUtil.sendCenteredMessage;

public class DeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(CustomPlayerDeathEvent event) {
        Player player = event.getPlayer();
        ProfileManager profileManager = Lithium.getInstance().getProfileManager();
        Profile killedProfile = profileManager.getProfile(player.getUniqueId());
        APIPlayer apiKilledPlayer = new APIPlayer(player);
        Entity killerEntity = event.getKiller(); //can be null
        int killedKills = apiKilledPlayer.isDisguised() ? killedProfile.getDisguisedKills() : killedProfile.getKills();
        String killMessage = "§a" + RankUtil.getRankColor(player) + player.getName() + "§7[" + "§c" + killedKills + "§7" + "]" + " §ehas died.";

        // plays the kill effect
        if (killerEntity instanceof Player) {
            Player killer = (Player) killerEntity;
            CosmeticHandler cosmeticHandler = Lithium.getInstance().getCosmeticHandler();
            cosmeticHandler.startEffect(killer, player);
        }

        // Don't handle death if the player is in an event
        SimpleProfile eventsProfile = SimpleProfile.fromUUID(event.getPlayer().getUniqueId());
        if (eventsProfile != null && eventsProfile.getCurrentGame() != null) {
            return;
        }

        // close their inventory
        player.closeInventory();

        // sets spawn stuff
        Location location = Bukkit.getWorld("world").getSpawnLocation();
        location.setX(-99.5);
        location.setZ(244);
        location.setYaw(90);

        player.teleport(location);

        // resets their level if they don't have perms to keep it
        if (!player.hasPermission("essentials.keepxp")) {
            player.setExp(0);
            player.setLevel(0);
        }

        // reset health and food stuff
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);

        // has to be done a tick later for some reason
        Bukkit.getScheduler().runTaskLater(Lithium.getInstance(), () -> player.setFireTicks(0), 1L);

        // remove cooldown
        if (Cooldown.isOnCooldown("combat", player)) {
            Cooldown.removeCooldown("combat", player);
        }

        // clear pot effects
        for (PotionEffect pot : player.getActivePotionEffects()) {
            player.removePotionEffect(pot.getType());
        }

        // gives them a new kit
        String kitName;

        if (!player.isOp()) {
            if (player.hasPermission("lithium.kit.blue")) {
                kitName = "blue";
            } else if (player.hasPermission("lithium.kit.platinum")) {
                kitName = "platinum";
            } else if (player.hasPermission("lithium.kit.silver")) {
                kitName = "silver";
            } else if (player.hasPermission("lithium.kit.pink")) {
                kitName = "pink";
            } else if (player.hasPermission("lithium.kit.gold")) {
                kitName = "gold";
            } else if (player.hasPermission("lithium.kit.purple")) {
                kitName = "purple";
            } else {
                kitName = "pvp";
            }
        } else {
            kitName = "pvp";
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit " + kitName + " " + player.getName());

        // increase deaths
        killedProfile.setDeaths(killedProfile.getDeaths() + 1);
        killedProfile.setDisguisedDeaths(killedProfile.getDeaths() + 1);

        // broadcast killstreak stuff
        int currentKillstreak = apiKilledPlayer.isDisguised() ? killedProfile.getDisguisedKillstreak() : killedProfile.getKillstreak();
        if (killedProfile.getKillstreak() > 4) {
            Bukkit.broadcastMessage("§6§lKillstreak §8» §f" + (killerEntity != null ? "§f" + player.getName() + "§e's §ekillstreak of §f" + currentKillstreak + "§e has been ended by §c" + killerEntity.getName() + "§e!" : "§f" + player.getName() + "§e's §ekillstreak of §f" + killedProfile.getKillstreak() + "§e has ended."));
        }

        // reset their killstreak
        killedProfile.setKillstreak(0);
        killedProfile.setDisguisedKillstreak(0);

        if (killerEntity instanceof Player) {
            Player killer = (Player) killerEntity;
            APIPlayer apiPlayerKiller = new APIPlayer(killer);
            Profile killerProfile = profileManager.getProfile(killer.getUniqueId());

            // handle bounty
            BountyHandler bountyHandler = Lithium.getInstance().getBountyHandler();
            if (bountyHandler.hasBounty(player)) {
                bountyHandler.removeBounty(player, killer);
            }


            // set their kills and killstreak
            killerProfile.setKills(killerProfile.getKills() + 1);
            killerProfile.setKillstreak(killerProfile.getKillstreak() + 1);
            killerProfile.setDisguisedKills(killerProfile.getDisguisedKills() + 1);
            killerProfile.setDisguisedKillstreak(killerProfile.getDisguisedKillstreak() + 1);

            // give them a kill key every 10 kills
            if (killerProfile.getKills() % 10 == 0)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cr givekey " + killer.getName() + " Kill 1");

            // set their xp
            int xp = RandomUtils.nextInt(2, 5);
            killerProfile.setXp(killerProfile.getXp() + xp);

            killer.sendMessage("§b+" + xp + " XP §7(" + killerProfile.getXp() + "/100" + ")");

            // level up
            if (killerProfile.getXp() >= 100) {
                killerProfile.setXp(0);
                killerProfile.setLevel(killerProfile.getLevel() + 1);

                apiKilledPlayer.sendTitle(
                        "§a§lLEVEL UP!",
                        LevelColorUtil.getLevelColored(killerProfile.getLevel() - 1, 0) + " §7-> " + LevelColorUtil.getLevelColored(killerProfile.getLevel(), 0)
                );

                killer.sendMessage(" ");
                sendCenteredMessage(killer, "§a§lLEVEL UP!");
                sendCenteredMessage(killer, LevelColorUtil.getLevelColored(killerProfile.getLevel() - 1, 0) + " §7-> " + LevelColorUtil.getLevelColored(killerProfile.getLevel(), 0));
                killer.sendMessage(" ");
            }

            // Calculates the amount of money they'll get
            int money = 5;

            // Decreases the amount of money they'll get every 10 kills
            if (killerProfile.getKillstreak() > 10) {
                money -= (killerProfile.getKillstreak() / 10) * 0.50;
            }

            if (money < 1) {
                money = 1;
            }

            // round to nearest x.xx
            money = (int) Math.round(money * 100.0) / 100;

            Economy economy = Lithium.getInstance().getEcon();
            economy.depositPlayer(killer, money);

            // action bar stuff
            PlayerUtil.sendActionbar(killer, "§fYou received §6$" + money + "§f for killing §c" + player.getName() + "§f!");
            PlayerUtil.sendTitle(player, "§c§lDEATH!", "§fYou were killed by §c" + RankUtil.getRankColor(killer) + killer.getName() + "§f!");

            // killstreaks
            int killstreak = apiPlayerKiller.isDisguised() ? killerProfile.getDisguisedKillstreak() : killerProfile.getKillstreak();
            if (killstreak % 5 == 0) {
                Bukkit.broadcastMessage("§6§lKillstreak §8» §f" + killer.getName() + " §eis on a killstreak of §c" + killstreak + "§e!");
            }

            // sets their highest killstreak
            if (killerProfile.getHighestKillstreak() < killerProfile.getKillstreak()) {
                killerProfile.setHighestKillstreak(killerProfile.getKillstreak());
            }

            // get the amount of kills they have
            int killerKills = apiPlayerKiller.isDisguised() ? killerProfile.getDisguisedKills() : killerProfile.getKills();

            // creates the broadcast
            if (killer.getItemInHand() != null && killer.getItemInHand().getItemMeta() != null && killer.getItemInHand().getItemMeta().getDisplayName() != null)
                killMessage = "§a" + RankUtil.getRankColor(player) + player.getName() + "§7[" + "§c" + killedKills + "§7" + "] §ewas killed by §a" + RankUtil.getRankColor(killer) + killer.getName() + "§7[" + "§c" + killerKills + "§7" + "] §eusing §c" + killer.getItemInHand().getItemMeta().getDisplayName();
            else
                killMessage = "§a" + RankUtil.getRankColor(player) + player.getName() + "§7[" + "§c" + killedKills + "§7" + "] §ewas killed by §a" + RankUtil.getRankColor(killer) + killer.getName() + "§7[" + "§c" + killerKills + "§7" + "]";
        }

        Bukkit.broadcastMessage(killMessage);
    }
}
