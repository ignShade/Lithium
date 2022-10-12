package net.leonemc.lithium.listener;

import com.booksaw.betterTeams.Team;
import net.leonemc.api.player.APIPlayer;
import net.leonemc.api.util.time.TimeUtil;
import net.leonemc.duels.DuelPlugin;
import net.leonemc.duels.match.MatchHandler;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.chatgames.ChatGameHandler;
import net.leonemc.lithium.chatgames.object.ChatGame;
import net.leonemc.lithium.filter.FilterManager;
import net.leonemc.lithium.profiles.Profile;
import net.leonemc.lithium.showcase.PreviewableInventory;
import net.leonemc.lithium.showcase.ShowcaseHandler;
import net.leonemc.lithium.utils.Cooldown;
import net.leonemc.lithium.utils.LevelColorUtil;
import net.leonemc.lithium.utils.RankUtil;
import net.leonemc.lithium.utils.bukkit.ItemUtil;
import net.leonemc.lithium.utils.bukkit.PlayerUtil;
import net.leonemc.lithium.utils.bukkit.ServerUtil;
import net.leonemc.lithium.utils.java.StringUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class GeneralListener implements Listener {

    private final Pattern IP_PATTERN = Pattern.compile("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");

    private HashMap<String, String> replace = new HashMap<>();

    public GeneralListener() {
        replace.put("/shrug", "¯\\_(\u30C4)_/¯");
        replace.put(":skull:", "\u2620");
        replace.put(":heart:", "§c§l\u2764<n>");
        replace.put("<3", "§c§l\u2764<n>");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //give them kit pvp when they first log in
        if (!player.hasPlayedBefore())
            player.chat("/kit pvp");

        PlayerUtil.sendTitle(player, "§6§lWELCOME!", "§fWelcome to §eLeone PvP§f!");

        //notify staff of reports
        if (player.hasPermission("rank.staff")) {
            int reports = Lithium.getInstance().getReportHandler().getReports().size();

            if (reports > 0) {
                player.sendMessage("§6§lReports §7// §eThere are currently §c" + reports + " §ereports pending. §eType §b/reports §efor more information.");
            }
        }

        // reset their spectator mode if they were in it
        if (player.getGameMode() == GameMode.SPECTATOR) {
            player.setGameMode(GameMode.SURVIVAL);
        }

        player.setDisplayName(RankUtil.getRankColor(player) + player.getName());
        player.removeMetadata("build", Lithium.getInstance());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        FilterManager filterManager = Lithium.getInstance().getFilterManager();
        ChatGameHandler chatGames = Lithium.getInstance().getChatGameHandler();
        ChatGame currentGame = chatGames.getCurrentGame();

        //don't allow regular chat messages...
        event.setCancelled(true);

        // check if they won the game
        if (chatGames.isActive() && currentGame.handleMessage(player, event.getMessage())) {
            chatGames.end(player);
            return;
        }

        //translate colors if they're an admin
        String sentMessage = filterManager.filterMessage(!player.hasPermission("rank.admin") ? event.getMessage() : ChatColor.translateAlternateColorCodes('&', event.getMessage()));

        //check if we should automatically mute the player
        FilterManager.FilterCategory category = filterManager.checkMessage(event.getMessage());
        if (category == FilterManager.FilterCategory.MUTABLE) {
            String offendingWord = filterManager.getOffendingWord(event.getMessage());
            ServerUtil.runCommandSync("tempmute -S " + player.getName() + " 1h " + (offendingWord == null ? "[Filter] Inappropriate Message" : "(Filter) Inappropriate Message - \"" + offendingWord + "\""));
            ServerUtil.sendToStaff("§c[Filter] §f" + RankUtil.getRankColor(player) + player.getName() + " §7has been muted for 1 hour: §f\"" + offendingWord + "\"");
        } else if (Pattern.matches(IP_PATTERN.toString(), event.getMessage())) {
            String offendingWord = event.getMessage();
            ServerUtil.runCommandSync("mute -S " + player.getName() + " [Filter] Posted an ip-address (\"" + offendingWord + "\")");
            ServerUtil.sendToStaff("§c[Filter] §f" + RankUtil.getRankColor(player) + player.getName() + " §7has been permanently muted: §f\"" + offendingWord + "\"");

            event.setCancelled(true);
            return;
        }

        boolean gotFiltered = category != FilterManager.FilterCategory.NONE;

        ItemStack item = player.getInventory().getItemInHand();
        String itemName = "null";
        boolean isItemInfo = false;

        //if the item is null, then just get rid of the item thing
        if (item == null && sentMessage.toLowerCase().contains("[item]")) {
            sentMessage = sentMessage.replaceAll("(?i)\\[item]", "");
        } else if (sentMessage.toLowerCase().contains("[item]") && item != null) {
            itemName = (item.getItemMeta() != null && item.getItemMeta().hasDisplayName()) ? item.getItemMeta().getDisplayName() : WordUtils.capitalize(item.getType().toString().replace("_", " ").toLowerCase());
            sentMessage = sentMessage.replaceAll("(?i)\\[item]", "§b" + itemName + "§f");
            isItemInfo = true;
        }

        // Handle inventory info thing
        ShowcaseHandler showcaseHandler = Lithium.getInstance().getShowcaseHandler();
        boolean isInventoryShowcase = false;
        if (sentMessage.toLowerCase().contains("[inv]")) {
            sentMessage = sentMessage.replaceAll("(?i)\\[inv]", "§b" + player.getName() + "'s Inventory§f");
            isInventoryShowcase = true;
            showcaseHandler.addInventory(player.getUniqueId(), new PreviewableInventory(System.currentTimeMillis(), player.getName(), player.getInventory().getContents(), player.getInventory().getArmorContents()));
        }

        String[] split = event.getMessage().split(" ");

        // Handle mentioning players using @name
        for (String arg : split) {
            if (arg.startsWith("@")) {
                String name = arg.substring(1);
                Player target = Bukkit.getPlayer(name);
                if (target != null) {
                    sentMessage = sentMessage.replace(arg, "§e@" + target.getName() + "§f");
                    target.playSound(target.getLocation(), Sound.NOTE_PLING, 1, 1);
                }
            }
        }

        // Handles player emoticons
        if (player.hasPermission("lithium.chat.emotes")) {
            String message1 = sentMessage;
            for (String key : replace.keySet()) {
                if (message1.contains(key)) {
                    sentMessage = sentMessage.replace(key, replace.get(key));
                }
            }
            sentMessage = sentMessage.replaceAll("<n>", "§f");
        }

        Profile profile = Lithium.getInstance().getProfileManager().getProfile(player);
        APIPlayer apiPlayer = new APIPlayer(player);

        Team playerTeam = apiPlayer.isDisguised() ? null : Team.getTeam(player);
        String playerRank = RankUtil.getRankPrefix(player);
        String level = apiPlayer.isDisguised() ? LevelColorUtil.getLevelColored(profile.getDisguisedLevel(), 0) : LevelColorUtil.getLevelColored(profile.getLevel(), 0) + " ";

        //build the message shown in console
        String tPrefix = playerTeam == null ? "" : "§7[§c" + playerTeam.getName() + "§7] ";
        String msg = level + tPrefix + "§f" + playerRank + player.getName() + "§7: §f" + sentMessage;

        //handles item info
        StringBuilder itemLore = new StringBuilder();
        if (isItemInfo) {
            //add the item name
            itemLore.append("§b").append(itemName).append("§r\n");

            //get all the enchantments
            List<Enchantment> enchants = Arrays.asList(item.getEnchantments().keySet().toArray(new Enchantment[0]));

            //add all the enchants to the item lore
            if (enchants.size() > 0) {
                for (Enchantment enchant : enchants) {
                    itemLore.append("§7").append(ItemUtil.getEnchantmentName(enchant)).append(" ").append(StringUtils.intToRoman(item.getEnchantmentLevel(enchant))).append(" ").append("\n");
                }
            }

            //add the lore if there is one
            if (item.getItemMeta() != null && item.getItemMeta().hasLore()) {
                for (String lore : item.getItemMeta().getLore()) {
                    itemLore.append("§7").append(lore).append("§r").append("\n");
                }
            }
        }

        //remove the last new line
        if (itemLore.toString().endsWith("\n")) {
            itemLore.deleteCharAt(itemLore.length() - 1);
        }

        String tag = profile.getTag() == null ? "" : " " + profile.getTag().getPrefix();

        for (Player receiver : event.getRecipients()) {
            Team receiverTeam = Team.getTeam(receiver);
            TextComponent chatMessage = new TextComponent();

            //color the team name according to relation status

            String teamPrefix = playerTeam == null ? "" // if no team, don't set a prefix for team
                    : receiverTeam == null ? "§7[§c" + playerTeam.getName() + "§7] " //if receiver team is null, return the red team name
                    : receiverTeam.getName().equals(playerTeam.getName()) ? "§7[§a" + playerTeam.getName() + "§7] " // if the teams are the same, return the green team name
                    : "§7[§c" + playerTeam.getName() + "§7] "; // if the teams are different, return the red team name

            String message = level + teamPrefix + "§f" + playerRank + player.getName() + tag + "§7: §f" + sentMessage;
            chatMessage.setText(message);

            String infoMessage = isItemInfo ? String.valueOf(itemLore) : //don't display the item lore if the message got filtered
                    ("§r§eTeam: §f" + (playerTeam == null ? "None" : playerTeam.getName()) + "\n" +
                            "§r§eKills: §f" + profile.getKills() + "\n" +
                            "§r§eDeaths: §f" + profile.getDeaths() + "\n" +
                            "§r§eXP: §f" + profile.getXp() + "\n" +
                            "§r§eLevel: §f" + profile.getLevel());

            //clear all bold from the lore, otherwise it'll make the whole hover message bold, not sure if this is a minecraft bug or spigot bug
            infoMessage = infoMessage.replaceAll("§l", "");

            //display the unfiltered message if they're staff
            String hoverMessage = gotFiltered && receiver.hasPermission("rank.staff") ? "§eUnfiltered: §f" + event.getMessage() + "\n \n" + infoMessage : infoMessage;

            chatMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(hoverMessage)));

            // Handle inventory showcase
            if (isInventoryShowcase) {
                chatMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/previewinventory " + player.getUniqueId()));
            }

            receiver.spigot().sendMessage(chatMessage);
        }

        //send the message to console
        Bukkit.getConsoleSender().sendMessage(msg);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        MatchHandler matchHandler = DuelPlugin.getInstance().getMatchHandler();
        if (matchHandler.isInMatch(player)) {
            return;
        }

        if (player.hasPermission("*") && !player.hasMetadata("build")) {
            player.sendMessage("§cYou must enable build mode to place blocks. §7(/build)");
            event.setCancelled(true);
            return;
        }

        if (event.isCancelled()) {
            Profile profile = Lithium.getInstance().getProfileManager().getProfile(player.getUniqueId());
            player.teleport(profile.getLastGroundLocation());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        MatchHandler matchHandler = DuelPlugin.getInstance().getMatchHandler();
        if (matchHandler.isInMatch(player)) {
            return;
        }

        if (player.hasPermission("*") && !player.hasMetadata("build")) {
            player.sendMessage("§cYou must enable build mode to break blocks. §7(/build)");
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onProjectileHit(EntityDamageByEntityEvent e) {
        Arrow arrow;
        if (e.getDamager() instanceof Arrow && (arrow = (Arrow) e.getDamager()).getShooter() instanceof Player && e.getEntity() instanceof Player) {
            Player shooter = (Player) arrow.getShooter();
            Player victim = (Player) e.getEntity();
            if (Objects.equals(shooter.getName(), victim.getName())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (ItemUtil.hasNBTTag(event.getItemDrop().getItemStack(), "antidupe")) {
            event.getPlayer().sendMessage("§cYou cannot drop this item due to it being a display item.");
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage().toLowerCase();
        Player player = event.getPlayer();

        for (String cmd : Lithium.getInstance().getConfig().getStringList("banned-commands.cmds")) {
            if (msg.startsWith(cmd.toLowerCase())) {
                event.setCancelled(true);
                player.sendMessage("§cYou cannot execute this command.");
                return;
            }
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) { // prevent "normal" mob spawning
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM ||
                event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG ||
                event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block blockUnder = player.getLocation().getBlock().getRelative(0, -1, 0);

        if (blockUnder.getType() == Material.SPONGE) {
            player.setVelocity(player.getLocation().getDirection().multiply(4).setY(0.4));
        }

        if (player.isOnGround()) {
            Profile profile = Lithium.getInstance().getProfileManager().getProfile(player.getUniqueId());
            profile.setLastGroundLocation(player.getLocation());
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();

        if (item != null && item.getType() == Material.GLASS_BOTTLE) {
            event.getItemDrop().remove();
        }
    }

    @EventHandler
    public void onItemInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) {
            return;
        }

        if (ItemUtil.hasNBTTag(event.getItem(), "antidupe")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cYou cannot use this item.");
            event.getPlayer().setItemInHand(null);
            return;
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        List<String> bannedKickReasons = Arrays.asList("disconnect.spam", "Flying is not enabled on this server");

        if (bannedKickReasons.contains(event.getReason())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String msg = event.getMessage().toLowerCase();
        APIPlayer apiPlayer = new APIPlayer(player);

        if (player.hasMetadata("modmode")) {
            return;
        }

        if (msg.startsWith("/warp beginnerarena")) {
            if (!apiPlayer.hasOnlyIronArmor()) {
                player.sendMessage("§c§lError! §cYou cannot enter the beginner arena with your current armor.");
                event.setCancelled(true);
            }
            return;
        }

        if (msg.startsWith("/warp oparena")) {
            if (!apiPlayer.hasDiamondArmor()) {
                player.sendMessage("§c§lError! §cYou cannot enter the op-arena with your current armor.");
                event.setCancelled(true);
            }
            return;
        }

        if (msg.startsWith("/fix") || msg.contains("/repair") && !player.hasPermission("lithium.repair.bypass")) {
            if (Cooldown.isOnCooldown("repair", player)) {
                event.setCancelled(true);
                player.sendMessage("§cYou cannot use this command for another " + TimeUtil.formatIntoDetailedString(Cooldown.getCooldownForPlayerInt("repair", player)));
            } else {
                Cooldown.addCooldown("repair", player, TimeUnit.HOURS.toSeconds(2));
            }
        }
    }
}
