package net.leonemc.lithium.reports.menu;

import lombok.AllArgsConstructor;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.reports.objects.Report;
import net.leonemc.lithium.utils.bukkit.CC;
import net.leonemc.lithium.utils.bukkit.ItemBuilder;
import net.leonemc.lithium.utils.menu.Button;
import net.leonemc.lithium.utils.menu.menus.ConfirmMenu;
import net.leonemc.lithium.utils.menu.pagination.PaginatedMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReportsMenu extends PaginatedMenu {

    private static final Lithium plugin = Lithium.getInstance();

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Viewing all unsolved reports";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        plugin.getReportHandler().getReports().forEach(report -> buttons.put(buttons.size(), new ReportDisplayButton(report)));

        return buttons;
    }

    @AllArgsConstructor
    private static class ReportDisplayButton extends Button {
        private Report report;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.PAPER).setName("§c" + report.getTarget()).setLore(Arrays.asList(
                    CC.MENU_BAR,
                    "§eReported by: §c" + report.getSender(),
                    "§eReport Category: §c" + report.getCategory().getName(),
                    "§eReason: §c" + report.getReason(),
                    CC.MENU_BAR,
                    "§eLeft click to teleport to player",
                    "§eRight click to §aaccept §ereport",
                    "§eDrop to §cdeny §ereport",
                    "§eShift Left click to view logs",
                    "§eMiddle click to view punishment history",
                    CC.MENU_BAR
            )).toItemStack();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            switch (clickType) {
                case LEFT: {
                    Player target = Bukkit.getPlayer(report.getTarget());

                    if (target == null) {
                        player.sendMessage(CC.translate("&cThat player is not online anymore!"));
                        return;
                    }

                    player.chat("/tp " + target.getName());
                    break;
                }
                case SHIFT_LEFT: {
                    player.chat("/logs " + report.getTarget());
                    break;
                }
                case MIDDLE: {
                    player.chat("/hist " + report.getTarget());
                    break;
                }
                case DROP: {
                    Button button = new Button() {
                        @Override
                        public ItemStack getButtonItem(Player player) {
                            return new ItemBuilder(Material.PAPER).setName(CC.WHITE + "§cDelete report?").toItemStack();
                        }
                    };

                    Button[] middleButtons = {button, button, button};
                    new ConfirmMenu("Deny report?", data -> {
                        if (data) {
                            plugin.getReportHandler().solveReport(report, player.getDisplayName(), false);
                            player.sendMessage("§cReport denied!");

                            Bukkit.getScheduler().runTaskLater(plugin, () -> player.chat("/reports"), 1);
                        }
                    }, true, middleButtons).openMenu(player);
                    break;
                }
                case RIGHT: {
                    Button button = new Button() {
                        @Override
                        public ItemStack getButtonItem(Player player) {
                            return new ItemBuilder(Material.PAPER).setName(CC.WHITE + "§aAccept report?").toItemStack();
                        }
                    };

                    Button[] middleButtons = {button, button, button};
                    new ConfirmMenu("Accept report?", data -> {
                        if (data) {
                            plugin.getReportHandler().solveReport(report, player.getDisplayName(), true);
                            player.sendMessage("§aReport accepted!");

                            Bukkit.getScheduler().runTaskLater(plugin, () -> player.chat("/reports"), 1);
                        }
                    }, true, middleButtons).openMenu(player);
                    break;
                }
            }
        }
    }
}