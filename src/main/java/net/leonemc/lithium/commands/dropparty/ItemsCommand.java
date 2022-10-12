package net.leonemc.lithium.commands.dropparty;

import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.duels.utils.LocationUtil;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.dropparty.DropPartyHandler;
import net.leonemc.lithium.utils.menu.Button;
import net.leonemc.lithium.utils.menu.pagination.PaginatedMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemsCommand {

    @Command(names = {"dropparty additem", "dp additem"}, permission = "*")
    public static void executeAdd(Player player) {
        DropPartyHandler handler = Lithium.getInstance().getDropPartyHandler();

        if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
            player.sendMessage("§cYou must be holding an item in your hand.");
            return;
        }

        handler.addItem(player.getItemInHand());
        player.setItemInHand(null);

        player.sendMessage("§aItem added to drop party.");
    }


    @Command(names = {"dropparty removeitem", "dp removeitem"}, permission = "*")
    public static void executeRemove(Player player, @Param(name = "index") int index) {
        DropPartyHandler handler = Lithium.getInstance().getDropPartyHandler();

        handler.removeItem(index);

        player.sendMessage("§cItem removed.");
    }


    @Command(names = {"dropparty list", "dp list"}, permission = "*")
    public static void executeList(Player player) {
        DropPartyHandler handler = Lithium.getInstance().getDropPartyHandler();

        new PaginatedMenu() {
            @Override
            public String getPrePaginatedTitle(Player player) {
                return "List of items";
            }

            @Override
            public Map<Integer, Button> getAllPagesButtons(Player player) {
                HashMap<Integer, Button> buttons = new HashMap<>();

                for (ItemStack item : handler.getItems()) {
                    buttons.put(buttons.size(), new Button() {
                        @Override
                        public ItemStack getButtonItem(Player player) {
                            return item;
                        }

                        @Override
                        public void clicked(Player player, ClickType clickType) {
                            if (clickType == ClickType.LEFT) {
                                player.getInventory().addItem(item);
                                handler.removeItem(item);

                                player.sendMessage("§cItem removed from drop party.");
                            }
                        }
                    });
                }

                return buttons;
            }
        }.openMenu(player);
    }


    @Command(names = {"dropparty clearitems", "dp clearitems"}, permission = "*")
    public static void executeClear(Player player) {
        DropPartyHandler handler = Lithium.getInstance().getDropPartyHandler();

        handler.clearItems();

        player.sendMessage("§cItems cleared.");
    }
}
