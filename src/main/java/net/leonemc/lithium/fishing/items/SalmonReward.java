package net.leonemc.lithium.fishing.items;

import net.leonemc.lithium.fishing.FishingItem;
import net.leonemc.lithium.fishing.Rarity;
import org.bukkit.Material;

public class SalmonReward extends FishingItem {

    @Override
    public String name() {
        return "Salmon";
    }

    @Override
    public Rarity rarity() {
        return Rarity.COMMON;
    }

    @Override
    public Material item() {
        return Material.RAW_FISH;
    }

    public short id() {
        return 1;
    }
}
