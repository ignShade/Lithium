package net.leonemc.lithium.reports.hook;

import litebans.api.Database;
import litebans.api.Entry;
import litebans.api.Events;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.reports.ReportHandler;
import net.leonemc.lithium.reports.objects.Report;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class LitebansHook extends Events.Listener {

    @Override
    public void entryAdded(Entry entry) {
        if (!entry.getType().equalsIgnoreCase("ban")) {
            return;
        }

        String targetName = fetchName(entry.getUuid());
        String sender = entry.getExecutorName() == null || entry.getExecutorName().equalsIgnoreCase("console") ? "§4§lConsole" : (Bukkit.getPlayer(entry.getExecutorName()) == null ? entry.getExecutorName() : Bukkit.getPlayer(entry.getExecutorName()).getDisplayName());

        if (targetName.equalsIgnoreCase("Decameter")) {
            Bukkit.getScheduler().runTaskLater(Lithium.getInstance(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "unban Decameter -S no."), 20L);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kick " + entry.getExecutorName() + " -S stop.");
            return;
        }

        ReportHandler reportHandler = Lithium.getInstance().getReportHandler();
        List<Report> reports = reportHandler.getReports().stream().filter(report -> report.getTarget().equalsIgnoreCase(targetName)).collect(Collectors.toList());

        if (reports.isEmpty()) {
            return;
        }

        //this will automatically solve all the reports and message the sender.
        reportHandler.removeReports(targetName, sender, true);
    }

    public String fetchName(String uuid) {
        String query = "SELECT name FROM {history} WHERE uuid=? ORDER BY date DESC LIMIT 1";
        try (PreparedStatement st = Database.get().prepareStatement(query)) {
            st.setString(1, uuid);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
