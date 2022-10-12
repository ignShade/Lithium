package net.leonemc.lithium.utils.menu.button;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import net.leonemc.lithium.utils.bukkit.CC;
import net.leonemc.lithium.utils.bukkit.ItemBuilder;
import net.leonemc.lithium.utils.menu.Button;
import net.leonemc.lithium.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class BackButton extends Button {

    private Menu back;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.FEATHER)
                .setName(CC.GOLD + CC.BOLD + "Go Back")
                .setLore(Arrays.asList(
                        CC.WHITE + "Click here to return to",
                        CC.WHITE + "the previous menu.")
                )
                .toItemStack();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        Button.playNeutral(player);
        back.openMenu(player);
    }

}