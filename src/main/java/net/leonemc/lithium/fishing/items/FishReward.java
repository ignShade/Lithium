package net.leonemc.lithium.fishing.items;

import net.leonemc.lithium.fishing.FishingItem;
import net.leonemc.lithium.fishing.Rarity;
import org.bukkit.Material;

public class FishReward extends FishingItem {

    @Override
    public String name() {
        return "Fish";
    }

    @Override
    public Rarity rarity() {
        return Rarity.UNCOMMON;
    }

    @Override
    public Material item() {
        return Material.RAW_FISH;
    }
}
