package net.leonemc.lithium.cosmetics.menu;

import lombok.AllArgsConstructor;
import net.leonemc.api.menu.Button;
import net.leonemc.api.menu.pagination.PaginatedMenu;
import net.leonemc.api.util.bukkit.ItemBuilder;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.cosmetics.CosmeticHandler;
import net.leonemc.lithium.cosmetics.objects.Cosmetic;
import net.leonemc.lithium.cosmetics.objects.CosmeticGroup;
import net.leonemc.lithium.profiles.Profile;
import net.leonemc.lithium.shop.menu.ShopMenu;
import net.leonemc.lithium.utils.menu.menus.ConfirmMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CosmeticListMenu extends PaginatedMenu {

    private final List<Cosmetic> cosmetics;

    public CosmeticListMenu(List<Cosmetic> cosmetics) {
        this.cosmetics = cosmetics;
        setUpdateAfterClick(true);
    }

    @NotNull
    @Override
    public String getPrePaginatedTitle(@NotNull Player player) {
        return "Select a cosmetic";
    }

    @NotNull
    @Override
    public Map<Integer, Button> getAllPagesButtons(@NotNull Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();

        cosmetics.forEach(cosmetic -> buttons.put(buttons.size(), new CosmeticButton(cosmetic)));

        return buttons;
    }

    @Override
    public int getMaxItemsPerPage(@NotNull Player player) {
        return 20;
    }

    @AllArgsConstructor
    private static class CosmeticButton extends Button {
        private final Cosmetic cosmetic;

        @NotNull
        @Override
        public ItemStack getButtonItem(@NotNull Player player) {
            CosmeticHandler cosmeticHandler = Lithium.getInstance().getCosmeticHandler();
            Profile profile = Lithium.getInstance().getProfileManager().getProfile(player);
            ItemBuilder base = new ItemBuilder(Objects.requireNonNull(cosmetic.getDisplayItem().parseItem()))
                    .name((cosmeticHandler.ownsCosmetic(player, cosmetic) ? "§a" + cosmetic.getName() : "§c" + cosmetic.getName()))
                    .addToLore(
                            "§7" + cosmetic.getDescription(),
                            "",
                            cosmetic.getGroup() == CosmeticGroup.PURCHASE ? "§ePrice: §a$" + cosmetic.getPrice() : "§eRequires " + cosmetic.getGroup().getName() + " §erank.",
                            "§eRarity: §a" + cosmetic.getRarity().getColor() + cosmetic.getRarity().getName(),
                            "",
                            (cosmeticHandler.ownsCosmetic(player, cosmetic) ? "§eClick to equip cosmetic" : "§eClick to purchase cosmetic.")
                    );

            return base.build();
        }

        @Override
        public void clicked(@NotNull Player player, int slot, @NotNull ClickType clickType, @NotNull InventoryView view) {
            CosmeticHandler cosmeticHandler = Lithium.getInstance().getCosmeticHandler();
            Profile profile = Lithium.getInstance().getProfileManager().getProfile(player);

            if (cosmeticHandler.ownsCosmetic(player, cosmetic)) {
                profile.setKillEffect(cosmetic);
                player.sendMessage("§aYou have selected §e" + cosmetic.getName() + "§a as your kill effect.");
            } else {
                if (cosmetic.getGroup() == CosmeticGroup.PURCHASE) {
                    net.leonemc.lithium.utils.menu.Button button = new net.leonemc.lithium.utils.menu.Button() {
                        @Override
                        public ItemStack getButtonItem(Player player) {
                            return new net.leonemc.lithium.utils.bukkit.ItemBuilder(Material.PAPER).setName("§aConfirm").setLore("§7Are you sure you want to buy this item?").toItemStack();
                        }
                    };
                    net.leonemc.lithium.utils.menu.Button[] middleButtons = {button, button, button};
                    new ConfirmMenu("Confirm purchase?", data -> {
                        if (data) {
                            if (cosmeticHandler.buyCosmetic(player, cosmetic)) {
                                profile.setKillEffect(cosmetic);
                                player.sendMessage("§aSuccess! You've bought the §e" + cosmetic.getName() + "§a kill effect.");
                            } else {
                                player.sendMessage("§cYou don't have enough money to buy this item.");
                            }
                        }
                    }, true, middleButtons).openMenu(player);
                } else {
                    player.sendMessage("§cYou don't have permission to use this cosmetic.");
                }
            }
        }
    }
}
