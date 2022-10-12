package net.leonemc.lithium.reports;


import lombok.Getter;
import net.leonemc.lithium.reports.objects.Report;
import net.leonemc.lithium.reports.objects.ReportCategory;
import net.leonemc.lithium.utils.Cooldown;
import net.leonemc.lithium.utils.bukkit.ServerUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReportHandler {

    @Getter
    private final List<Report> reports = new ArrayList<>();

    public void sendReport(Player sender, Player target, ReportCategory category, String description) {
        sender.sendMessage("§aThanks for your report! Your report has been submitted. It will be looked at shortly.");
        TextComponent textComponent = new TextComponent();
        textComponent.setText("§9§l[Report] §a" + sender.getName() + " §fhas reported §c" + target.getName() + " §ffor §b" + description + " §7(" + category.getName() + ")");
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§fClick to teleport to §c" + target.getName() + "§f.").create()));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + target.getName()));

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("rank.staff")) {
                player.spigot().sendMessage(textComponent);
            }
        });

        reports.add(new Report(UUID.randomUUID(), sender.getName(), target.getName(), category, description));
        Cooldown.addCooldown("report", sender, 30);
    }

    public void removeReports(String target, String sender, boolean accepted) {
        ServerUtil.sendToStaff("§9§l[Reports] §a" + sender + " §7has " + (accepted ? "accepted" : "denied") + " all reports on §f" + target + "§7.");
        removeReports(target, accepted);
    }

    public void removeReports(String target, boolean accepted) {
        List<Report> all = reports.stream().filter(report -> Objects.equals(report.getTarget(), target)).collect(Collectors.toList());

        all.forEach(report -> {
            if (accepted) {
                Player player = Bukkit.getPlayer(report.getSender());
                if (player != null) {
                    player.sendMessage("§aThanks for reporting! Your report on §f" + target + " §ahas been accepted.");
                }
            }
        });

        reports.removeAll(all);
    }

    public void solveReport(Report report, String solver, boolean accepted) {
        ServerUtil.sendToStaff("§9§l[Reports] §a" + solver + " §7has " + (accepted ? "accepted" : "denied") + " a report on §f" + report.getTarget() + " §7for §b" + report.getCategory() + "§7.");
        solveReport(report, accepted);
    }

    public void solveReport(Report report, boolean accepted) {
        if (accepted) {
            Player sender = Bukkit.getPlayer(report.getSender());

            if (sender != null) {
                sender.sendMessage("§aThanks for reporting! Your report on §f" + report.getTarget() + " §ahas been accepted.");
            }
        }

        reports.remove(report);
    }

}