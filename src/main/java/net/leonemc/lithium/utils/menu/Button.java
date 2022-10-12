package net.leonemc.lithium.utils.menu;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class Button {

    public abstract ItemStack getButtonItem(Player player);

    public void clicked(Player player, ClickType clickType) {

    }

    public static Button placeholder(ItemStack stack) {
        return new Button() {

            @Override
            public ItemStack getButtonItem(Player player) {
                return stack;
            }
        };
    }

    public static void playFail(Player player) {
        player.playSound(player.getLocation(), Sound.DIG_GRASS, 20F, 0.1F);

    }

    public static void playSuccess(Player player) {
        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 20F, 15F);
    }

    public static void playNeutral(Player player) {
        player.playSound(player.getLocation(), Sound.CLICK, 20F, 1F);
    }
}