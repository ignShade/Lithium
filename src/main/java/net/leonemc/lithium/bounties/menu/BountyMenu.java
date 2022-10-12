package net.leonemc.lithium.bounties.menu;

import lombok.AllArgsConstructor;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.bounties.Bounty;
import net.leonemc.lithium.utils.bukkit.CC;
import net.leonemc.lithium.utils.bukkit.ItemBuilder;
import net.leonemc.lithium.utils.menu.Button;
import net.leonemc.lithium.utils.menu.pagination.PaginatedMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class BountyMenu extends PaginatedMenu {

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Bounties";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        HashMap<Integer, Button> button = new HashMap<>();

        Lithium.getInstance().getBountyHandler().getBounties().forEach(bounty -> button.put(button.size(), new BountyButton(bounty)));

        return button;
    }

    @AllArgsConstructor
    private static class BountyButton extends Button {

        private Bounty bounty;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.SKULL_ITEM, 1)
                    .setName("§c" + bounty.getTarget())
                    .setLore(
                            CC.MENU_BAR,
                            "§7The reward is §f$" + bounty.getBalance() + "§7.",
                            "§7Wanted by §e" + bounty.getAddedBy(),
                            CC.MENU_BAR

                    )
                    .setDurability((short) 3)
                    .setSkullOwner(bounty.getTarget())
                    .toItemStack();
        }
    }
}
