package net.leonemc.lithium.customitems.menu;

import lombok.AllArgsConstructor;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.customitems.CustomItem;
import net.leonemc.lithium.utils.bukkit.CC;
import net.leonemc.lithium.utils.bukkit.ItemBuilder;
import net.leonemc.lithium.utils.menu.Button;
import net.leonemc.lithium.utils.menu.pagination.PaginatedMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ItemListMenu extends PaginatedMenu {

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "List of custom items";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();

        for (CustomItem item : Lithium.getInstance().getCustomItemHandler().getItems()) {
            buttons.put(buttons.size(), new CustomItemButton(item));
        }

        return buttons;
    }

    @AllArgsConstructor
    private static class CustomItemButton extends Button {

        private CustomItem item;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(item.getItem().parseItem())
                    .setName(CC.translate(item.getDisplayName()))
                    .setLore(Arrays.asList(
                            CC.MENU_BAR,
                            "§eName: §c" + item.getName(),
                            "§eDescription: §c" + item.getDescription(),
                            "§eMax Uses: §c" + item.getUses(),
                            CC.MENU_BAR,
                            "§eLeft click to give yourself this item",
                            CC.MENU_BAR
                    ))
                    .toItemStack();
        }


        @Override
        public void clicked(Player player, ClickType clickType) {
            player.getInventory().addItem(item.getItemStack());
        }
    }
}
