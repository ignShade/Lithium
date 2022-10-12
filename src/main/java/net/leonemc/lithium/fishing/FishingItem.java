package net.leonemc.lithium.fishing;

import net.leonemc.lithium.utils.bukkit.CC;
import net.leonemc.lithium.utils.bukkit.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class FishingItem {

    public abstract String name();
    public abstract Rarity rarity();
    public abstract Material item();

    public short id() {
        return 0;
    }
    public String setSkullOwner() {
        return null;
    }

    public String setCustomSkull() {
        return null;
    }

    public ItemStack asItemStack(Player caughtBy) {
        ItemStack base = new ItemBuilder(item()).setName(rarity().getColor() + name()).setDurability(id()).setLore(CC.MENU_BAR, "§fCaught by: §e" + caughtBy.getName(), "§fRarity: " + rarity().getColor() + rarity().name(), CC.MENU_BAR).toItemStack();

        if (setSkullOwner() != null) {
            base = new ItemBuilder(base).setSkullOwner(setSkullOwner()).toItemStack();
        }

        if (setCustomSkull() != null) {
            base = new ItemBuilder(base).setCustomHeadTexture(setCustomSkull()).toItemStack();
        }

        return base;
    }

}
