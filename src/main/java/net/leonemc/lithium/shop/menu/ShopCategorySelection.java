package net.leonemc.lithium.shop.menu;

import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.shop.objects.ShopCategory;
import net.leonemc.lithium.utils.bukkit.ItemBuilder;
import net.leonemc.lithium.utils.menu.Button;
import net.leonemc.lithium.utils.menu.Menu;
import net.leonemc.lithium.utils.menu.pagination.PaginatedMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ShopCategorySelection extends PaginatedMenu {

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Shop";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (ShopCategory value : ShopCategory.values()) {
            buttons.put(buttons.size(), new Button() {
                @Override
                public ItemStack getButtonItem(Player player) {
                    return new ItemBuilder(value.getIcon()).setName("§b" + value.getName()).toItemStack();
                }

                @Override
                public void clicked(Player player, ClickType clickType) {
                    new ShopMenu(value).openMenu(player);
                }
            });
        }

        return buttons;
    }
}
