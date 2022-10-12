package net.leonemc.lithium.shop.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@AllArgsConstructor
@Getter
public enum ShopCategory {

    GEAR("Gear", Material.DIAMOND_HELMET),
    POTIONS("Potions", Material.POTION),
    FOOD("Food", Material.COOKED_BEEF),
    ENCHANTING("Enchanting", Material.EXP_BOTTLE),
    OTHER("Other", Material.GOLDEN_APPLE),
    ALL("All", Material.REDSTONE);


    private String name;
    private Material icon;
}
