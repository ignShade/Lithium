package net.leonemc.lithium.reports.menu;

import lombok.AllArgsConstructor;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.reports.objects.ReportCategory;
import net.leonemc.lithium.utils.bukkit.CC;
import net.leonemc.lithium.utils.bukkit.ItemBuilder;
import net.leonemc.lithium.utils.menu.Button;
import net.leonemc.lithium.utils.menu.Menu;
import net.leonemc.lithium.utils.menu.menus.ConfirmMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@AllArgsConstructor
public class ReportMenu extends Menu {

    private Player sender;
    private Player target;
    private String reason;

    @Override
    public String getTitle(Player player) {
        return "Reporting " + target.getName();
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        Arrays.stream(ReportCategory.values()).forEach(it -> buttons.put(buttons.size(), new ReportReasonButton(sender, target, it, reason)));
        return buttons;
    }

    @Override
    public int size(Player player) {
        return size(getButtons(player));
    }

    @AllArgsConstructor
    private static class ReportReasonButton extends Button {

        private Player sender;
        private Player target;
        private ReportCategory category;
        private String reason;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(category.getDisplayItem()).setName("§c" + category.getName()).setLore("§fClick to report §c" + target.getName() + " §ffor §b" + category.getName()).toItemStack();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            Button button = new Button() {
                @Override
                public ItemStack getButtonItem(Player player) {
                    return new ItemBuilder(Material.PAPER).setName(CC.WHITE + "Are you sure you want to report §c" + target.getName() + " §ffor §b" + category.getName()).toItemStack();
                }
            };

            Button[] middleButtons = {button, button, button};
            new ConfirmMenu("Confirm report?", data -> {
                if (data) {
                    Lithium.getInstance().getReportHandler().sendReport(sender, target, category, reason);
                } else {
                    player.sendMessage("§cReport cancelled!");
                }
            }, true, middleButtons).openMenu(player);
        }
    }
}