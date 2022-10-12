package net.leonemc.lithium.fishing.items;

import net.leonemc.lithium.fishing.FishingItem;
import net.leonemc.lithium.fishing.Rarity;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClownfishReward extends FishingItem {

    @Override
    public String name() {
        return "Clownfish";
    }

    @Override
    public Rarity rarity() {
        return Rarity.RARE;
    }

    @Override
    public Material item() {
        return Material.RAW_FISH;
    }

    public short id() {
        return 2;
    }
}
