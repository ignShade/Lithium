package net.leonemc.lithium.fishing.items;

import net.leonemc.lithium.fishing.FishingItem;
import net.leonemc.lithium.fishing.Rarity;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DolphinReward extends FishingItem {

    @Override
    public String name() {
        return "Dolphin";
    }

    @Override
    public Rarity rarity() {
        return Rarity.LEGENDARY;
    }

    @Override
    public Material item() {
        return Material.SKULL_ITEM;
    }

    @Override
    public short id() {
        return 3;
    }

    @Override
    public String setCustomSkull() {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGU5Njg4Yjk1MGQ4ODBiNTViN2FhMmNmY2Q3NmU1YTBmYTk0YWFjNmQxNmY3OGU4MzNmNzQ0M2VhMjlmZWQzIn19fQ==";
    }
}
