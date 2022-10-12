package net.leonemc.lithium.scoreboard;

import com.booksaw.betterTeams.Team;
import com.earth2me.essentials.Essentials;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
import me.imsanti.leoneevents.arena.SimpleArena;
import me.imsanti.leoneevents.event.PointEvent;
import me.imsanti.leoneevents.event.SimpleEvent;
import me.imsanti.leoneevents.event.TimedEvent;
import me.imsanti.leoneevents.event.phase.EventPhaseType;
import me.imsanti.leoneevents.event.phase.StartingPhase;
import me.imsanti.leoneevents.player.SimpleProfile;
import net.leonemc.api.util.bukkit.cuboid.Cuboid;
import net.leonemc.api.util.time.TimeUtil;
import net.leonemc.duels.DuelPlugin;
import net.leonemc.duels.ladder.LadderRule;
import net.leonemc.duels.match.Match;
import net.leonemc.duels.match.MatchHandler;
import net.leonemc.duels.match.teams.MatchTeam;
import net.leonemc.duels.match.teams.TeamColor;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.profiles.Profile;
import net.leonemc.lithium.utils.Cooldown;
import net.leonemc.lithium.utils.RankUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import subside.plugins.koth.KothPlugin;
import subside.plugins.koth.gamemodes.RunningKoth;
import subside.plugins.koth.modules.KothHandler;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ScoreboardAdapter implements AssembleAdapter {

    @Override
    public String getTitle(Player player) {
        if (player.hasMetadata("frozen")) {
            return "§4§lFROZEN!";
        }

        final DuelPlugin duelPlugin = DuelPlugin.getInstance();
        final MatchHandler matchHandler = duelPlugin.matchHandler;
        final SimpleProfile eventsProfile = SimpleProfile.fromUUID(player.getUniqueId());

        if (matchHandler.isInMatch(player) || matchHandler.isSpectating(player)) {
            return "§6§lDUELS";
        } else if (eventsProfile.getCurrentGame() != null)
            return "§6§lEVENT";
        else
            return "§6§lPVP";
    }

    @Override
    public List<String> getLines(Player player) {
        final List<String> lines = new ArrayList<>();

        // Don't display the scoreboard if essentials isn't loaded
        // It'll spam errors in console when the server is shutting down
        // because the scoreboard is still updating but essentials is already unloaded
        final Essentials essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        if (essentials == null) {
            return lines;
        }

        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh:mm aa");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        final String date = dateFormat.format(new Date());

        final Profile profile = Lithium.getInstance().getProfileManager().getProfile(player.getUniqueId());
        final Economy economy = Lithium.getInstance().getEcon();
        final SimpleProfile eventsProfile = SimpleProfile.fromUUID(player.getUniqueId());

        // This shouldn't happen but just in case
        if (profile == null) {
            lines.add("§7" + date);
            lines.add(" ");
            lines.add(" §4§lError!");
            lines.add("  §fNo profile found.");
            lines.add("  §fPlease relog.");
            lines.add(" ");
            lines.add("§eshop.leonemc.net");
            return lines;
        }

        // If the player is frozen, display the frozen scoreboard
        if (player.hasMetadata("frozen")) {
            lines.add("§7" + date);
            lines.add(" ");
            lines.add(" §fYou're frozen!");
            lines.add(" §7* §fJoin §bdiscord§f!");
            lines.add(" §7* §fYou have §c3 minutes§f to join!");
            lines.add(" ");
            lines.add(" §c§lDon't log out!");
            lines.add(" ");
            lines.add("§cdiscord.gg/leonegaming");
            return lines;
        }

        // If the player is in an event, display the event information
        if (eventsProfile.getCurrentGame() != null) {
            final SimpleEvent event = eventsProfile.getCurrentGame();

            lines.add("§7" + date);
            lines.add(" ");

            if (event.getCurrentPhase() instanceof StartingPhase) {
                lines.add(" §fStarting soon!");
                lines.add(" §fPlease wait...");
            } else {
                SimpleArena arena = event.getAssignedArena();
                lines.add(" §fArena: §e" + arena.getIdentifier());

                if (eventsProfile.getCurrentGame() instanceof TimedEvent) {
                    TimedEvent timedEvent = (TimedEvent) arena.getAssignedGame();
                    lines.add(" §fTime Remaining: §e" + timedEvent.getTotalTime());
                }

                if (event instanceof PointEvent) {
                    final PointEvent pointEvent = (PointEvent) event;

                    lines.add(" §fYour Points: §e" + pointEvent.getPoints(eventsProfile));
                }
            }

            lines.add(" ");
            lines.add("§eshop.leonemc.net");

            return lines;
        }

        // If the player is in a duel match, display a different scoreboard
        DuelPlugin duels = DuelPlugin.getInstance();
        MatchHandler matchHandler = duels.getMatchHandler();
        boolean isSpectating = matchHandler.isSpectating(player);
        if (matchHandler.isInMatch(player) || isSpectating) {
            Match match = !isSpectating ? matchHandler.getMatch(player) : matchHandler.getSpectatingMatch(player);
            lines.add("§7" + date);
            lines.add(" ");

            //check if the duel isn't a bridge duel
            if (!match.getLadder().hasGamerule(LadderRule.THE_BRIDGE)) {
                if (!isSpectating) {
                    boolean isParty = match.getPlayers().size() > 2;
                    MatchTeam team = match.getTeam(player.getUniqueId());

                    //this shouldn't happen...
                    if (team == null) {
                        return new ArrayList<>();
                    }

                    //get their opponent
                    MatchTeam opponent = match.getOppositeTeam(team);

                    //display their team and the enemy, so it's easier to understand
                    if (isParty) {
                        lines.add("§aYour team §7(" + team.getAlive().size() + "/" + team.getPlayers().size() + ")");
                        lines.add("§f- vs. -");
                        lines.add("§cEnemy team §7(" + opponent.getAlive().size() + "/" + opponent.getPlayers().size() + ")");
                    } else {
                        Player opp = match.getOppositeTeam(team).getAllPlayers().get(0);

                        lines.add("§e" + RankUtil.getRankColor(player) + player.getName() + " §e(" + ((CraftPlayer) player).getHandle().ping + " ms)");
                        lines.add("§f- vs. -");
                        lines.add("§e" + RankUtil.getRankColor(opp) + opp.getName() + " §e(" + ((CraftPlayer) opp).getHandle().ping + " ms)");
                    }
                } else { //if they're spectating display the match info
                    boolean isParty = match.getPlayers().size() > 2;
                    MatchTeam team = match.getTeamA();
                    MatchTeam opponent = match.getOppositeTeam(team);

                    //display team a and b if it's a party duel
                    if (isParty) {
                        lines.add("§aTeam A §7(" + team.getAlive().size() + "/" + team.getPlayers().size() + ")");
                        lines.add("§f- vs. -");
                        lines.add("§cTeam B §7(" + opponent.getAlive().size() + "/" + opponent.getPlayers().size() + ")");
                    } else {
                        Player player1 = team.getAllPlayers().get(0);
                        Player opp = match.getOppositeTeam(team).getAllPlayers().get(0);

                        lines.add("§e" + RankUtil.getRankColor(player1) + player1.getName() + " §e(" + ((CraftPlayer) player1).getHandle().ping + " ms)");
                        lines.add("§f- vs. -");
                        lines.add("§e" + RankUtil.getRankColor(opp) + opp.getName() + " §e(" + ((CraftPlayer) opp).getHandle().ping + " ms)");
                    }
                }

                //handle bridge scoreboard
            } else {
                int blueScores = match.getScore(match.getTeam(TeamColor.BLUE));
                int redScores = match.getScore(match.getTeam(TeamColor.RED));

                lines.add("§9[B] " + net.leonemc.lithium.utils.java.ProgressBar.getProgressBar(blueScores, 5, 5, '\u2588', ChatColor.BLUE, ChatColor.GRAY));
                lines.add("§c[R] " + net.leonemc.lithium.utils.java.ProgressBar.getProgressBar(redScores, 5, 5, '\u2588', ChatColor.RED, ChatColor.GRAY));
            }

            lines.add(" ");

            //add boxing hits
            if (match.getLadder().hasGamerule(LadderRule.BOXING)) {
                //if they're spectating, show the player names, otherwise just show your hits and the enemy hits
                if (isSpectating) {
                    Player player1 = match.getTeamA().getAllPlayers().get(0);
                    Player player2 = match.getTeamB().getAllPlayers().get(0);

                    int player1Score = match.getScore(Objects.requireNonNull(match.getTeam(player1.getUniqueId())));
                    int player2Score = match.getScore(Objects.requireNonNull(match.getTeam(player2.getUniqueId())));

                    lines.add("§e" + player1.getName() + " §7- §f" + player1Score);
                    lines.add("§e" + player2.getName() + " §7- §f" + player2Score);
                } else {
                    int player1Score = match.getScore(Objects.requireNonNull(match.getTeam(player.getUniqueId())));
                    int player2Score = match.getScore(match.getOppositeTeam(Objects.requireNonNull(match.getTeam(player.getUniqueId()))));

                    lines.add("§aYour hits: §f" + player1Score);
                    lines.add("§cEnemy hits: §f" + player2Score);
                }
                lines.add(" ");
            }

            lines.add("§fKit: §e" + match.getLadder().getName());
            lines.add("§fMap: §e" + match.getArena().getDisplayName());
            lines.add(" ");
            lines.add("§eshop.leonemc.net");
            return lines;
        }

        lines.add("§7" + date);

        if (player.hasMetadata("modmode") || player.hasMetadata("vanish")) {
            lines.add(" ");
            lines.add(" §fVanish: " + (player.hasMetadata("vanish") ? "§aOn" : "§cOff"));
        }

        lines.add("                                     ");
        lines.add(" §fRank: §7" + RankUtil.getRankName(player));
        lines.add(" §fBalance: §6" + economy.format(economy.getBalance(player)));
        lines.add(" §fTeam: " + (Team.getTeam(player) == null ? "§cNone" : "§d" + Team.getTeam(player).getName()));
        lines.add(" ");


        lines.add(" §fKills: §a" + profile.getKills());
        lines.add(" §fDeaths: §c" + profile.getDeaths());
        lines.add(" §fKillstreak: §e" + profile.getKillstreak());

        boolean combatTag = Cooldown.isOnCooldown("combat", player);
        boolean gapple = Cooldown.isOnCooldown("gapple", player);
        boolean godApple = Cooldown.isOnCooldown("godapple", player);
        boolean pearl = Cooldown.isOnCooldown("enderpearl", player);
        boolean repair = Cooldown.isOnCooldown("repair", player);
        boolean dash = Cooldown.isOnCooldown("dash", player);
        boolean updraft = Cooldown.isOnCooldown("updraft", player);
        boolean grapple = Cooldown.isOnCooldown("grapple", player);
        boolean attackdogs = Cooldown.isOnCooldown("attack_dogs", player);

        boolean onCooldown = combatTag || gapple || godApple || pearl || repair || dash || updraft || grapple || attackdogs;

        if (onCooldown) {
            lines.add(" ");
        }

        if (combatTag) {
            String formatted = TimeUtil.formatIntoMMSS(Cooldown.getCooldownForPlayerInt("combat", player));
            lines.add(" §fCombat Tag: §c" + formatted);
        }

        if (pearl) {
            String formatted = TimeUtil.formatIntoMMSS(Cooldown.getCooldownForPlayerInt("enderpearl", player));
            lines.add(" §fEnderpearl: §c" + formatted);
        }

        if (gapple) {
            String formatted = TimeUtil.formatIntoMMSS(Cooldown.getCooldownForPlayerInt("gapple", player));
            lines.add(" §fGolden Apple: §c" + formatted);
        }

        if (godApple) {
            String formatted = TimeUtil.formatIntoMMSS(Cooldown.getCooldownForPlayerInt("godapple", player));
            lines.add(" §fNotch Apple: §c" + formatted);
        }

        if (repair) {
            String formatted = TimeUtil.formatIntoMMSS(Cooldown.getCooldownForPlayerInt("repair", player));
            lines.add(" §fRepair: §c" + formatted);
        }

        if (dash) {
            String formatted = TimeUtil.formatIntoMMSS(Cooldown.getCooldownForPlayerInt("dash", player));
            lines.add(" §fDash: §c" + formatted);
        }

        if (updraft) {
            String formatted = TimeUtil.formatIntoMMSS(Cooldown.getCooldownForPlayerInt("updraft", player));
            lines.add(" §fUpdraft: §c" + formatted);
        }

        if (grapple) {
            String formatted = TimeUtil.formatIntoMMSS(Cooldown.getCooldownForPlayerInt("grapple", player));
            lines.add(" §fGrapple: §c" + formatted);
        }

        if (attackdogs) {
            String formatted = TimeUtil.formatIntoMMSS(Cooldown.getCooldownForPlayerInt("attack_dogs", player));
            lines.add(" §fAttack Dogs: §c" + formatted);
        }

        /*/Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("KoTH");
        if (plugin != null) {
            KothPlugin kothPlugin = (KothPlugin) plugin;
            KothHandler kothHandler = kothPlugin.getKothHandler();
            RunningKoth runningKoth = kothHandler.getRunningKoth();
            if (runningKoth != null) {
                if (addExtraLineKoth) {
                    lines.add(" ");
                }
                lines.add(" §e§l" + runningKoth.getKoth().getName() + " KOTH: §7(" + runningKoth.getKoth().getMiddle().getBlockX() + ", " + runningKoth.getKoth().getMiddle().getBlockZ() + ")");
                lines.add(" §7» §f" + runningKoth.getTimeObject().getTimeLeftFormatted());
            }
        }/*/
        lines.add(" ");
        lines.add("§eshop.leonemc.net");

        return lines;
    }
}
