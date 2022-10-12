package net.leonemc.lithium.shop.menu;

import lombok.AllArgsConstructor;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.shop.ShopHandler;
import net.leonemc.lithium.shop.objects.ShopCategory;
import net.leonemc.lithium.shop.objects.ShopItem;
import net.leonemc.lithium.shop.objects.ShopType;
import net.leonemc.lithium.utils.bukkit.CC;
import net.leonemc.lithium.utils.bukkit.ItemBuilder;
import net.leonemc.lithium.utils.menu.Button;
import net.leonemc.lithium.utils.menu.button.BackButton;
import net.leonemc.lithium.utils.menu.menus.ConfirmMenu;
import net.leonemc.lithium.utils.menu.pagination.PaginatedMenu;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.leonemc.lithium.shop.objects.ShopCategory.ALL;

@AllArgsConstructor
public class ShopMenu extends PaginatedMenu {

    private ShopCategory category;

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Shop - " + category.getName();
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(4, new BackButton(new ShopCategorySelection()));
        return buttons;
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        final Map<Integer, Button> buttons = new HashMap<>();
        final List<ShopItem> items = Lithium.getInstance().getShopHandler().getShopItems().stream().filter(item -> category == ALL || item.getCategory() == category).collect(java.util.stream.Collectors.toList());

        items.forEach(item -> buttons.put(buttons.size(), new ShopItemButton(item)));

        return buttons;
    }

    @AllArgsConstructor
    private static class ShopItemButton extends Button {

        private final ShopItem item;

        final Economy economy = Lithium.getInstance().getEcon();

        @Override
        public ItemStack getButtonItem(Player player) {
            ItemStack itemStack =  new ItemBuilder(item.getItem().getType(), item.getItem().getAmount()).setNBTTag("antidupe", "true").setDurability((short) item.getItem().getData().getData()).setName("§b" + item.getName()).setLore(CC.MENU_BAR, "§7Price: §a" + item.getPrice(), usageLore().get(0), usageLore().get(1)).toItemStack();

            if (item.getItem().getEnchantments().isEmpty()) {
                return itemStack;
            }

            itemStack.addUnsafeEnchantments(item.getItem().getEnchantments());

            return itemStack;
        }

        public List<String> usageLore() {
            switch (item.getShopType()) {
                case BUY_ONLY:
                    return Arrays.asList(
                            "§aLeft click §7to buy",
                            "§c§lThis item is not sellable.");
                case SELL_ONLY:
                    return Arrays.asList(
                            "§cRight click §7to sell",
                            "§c§lThis item is not purchasable.");
                default: {
                    return Arrays.asList(
                            "§aLeft click §7to buy",
                            "§cRight click §7to sell");
                }
            }
        }

        public boolean canBuy() {
            return item.getShopType() == ShopType.BUY_ONLY || item.getShopType() == ShopType.BOTH;
        }

        public boolean canSell() {
            return item.getShopType() == ShopType.SELL_ONLY || item.getShopType() == ShopType.BOTH;
        }

        public boolean hasMoney(Player player) {
            return economy.has(player, item.getPrice());
        }

        public boolean hasSpace(Player player) {
            return player.getInventory().firstEmpty() != -1;
        }

        public boolean hasItem(Player player) {
            return player.getInventory().containsAtLeast(item.getItem(), item.getItem().getAmount());
        }

        public void buyItem(Player player) {
            economy.withdrawPlayer(player, item.getPrice());
            player.getInventory().addItem(item.getItem());
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            switch (clickType) {
                case LEFT: {
                    if (!canBuy()) {
                        player.sendMessage("§cThis item is not buyable.");
                        return;
                    }

                    if (!hasMoney(player)) {
                        player.sendMessage("§cYou do not have enough money.");
                        return;
                    }

                    if (!hasSpace(player)) {
                        player.sendMessage("§cYou do not have enough space in your inventory.");
                        return;
                    }

                    Button button = new Button() {
                        @Override
                        public ItemStack getButtonItem(Player player) {
                            return new ItemBuilder(Material.PAPER).setName("§aConfirm").setLore("§7Are you sure you want to buy this item?").toItemStack();
                        }
                    };

                    if (item.getPrice() > 1000) {
                        Button[] middleButtons = {button, button, button};
                        new ConfirmMenu("Confirm purchase?", data -> {
                            if (data) {
                                buyItem(player);
                                player.sendMessage("§aYou have successfully bought this item.");
                            }
                            new ShopMenu(item.getCategory()).openMenu(player);
                        }, true, middleButtons).openMenu(player);
                    } else {
                        buyItem(player);
                        player.sendMessage("§aYou have successfully bought this item.");
                    }

                    break;
                }

                case RIGHT: {
                    if (!canSell()) {
                        player.sendMessage("§cThis item is not sellable.");
                        return;
                    }

                    if (!hasItem(player)) {
                        player.sendMessage("§cYou do not have this item or enough of it.");
                        return;
                    }

                    Button button = new Button() {
                        @Override
                        public ItemStack getButtonItem(Player player) {
                            return new ItemBuilder(Material.PAPER).setName("§aConfirm").setLore("§7Are you sure you want to sell this item?").toItemStack();
                        }
                    };

                    Button[] middleButtons = {button, button, button};
                    new ConfirmMenu("Confirm sale?", data -> {
                        if (data) {
                            economy.depositPlayer(player, item.getPrice());
                            player.getInventory().removeItem(item.getItem());
                            player.sendMessage("§aYou have successfully sold this item.");
                        }
                        new ShopMenu(item.getCategory()).openMenu(player);
                    }, true, middleButtons).openMenu(player);

                    break;
                }
            }
        }
    }
}
