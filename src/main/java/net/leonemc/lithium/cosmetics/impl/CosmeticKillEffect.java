package net.leonemc.lithium.cosmetics.impl;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.cosmetics.objects.Cosmetic;
import net.leonemc.lithium.cosmetics.objects.CosmeticGroup;
import net.leonemc.lithium.cosmetics.objects.CosmeticRarity;
import net.leonemc.lithium.cosmetics.objects.CosmeticType;
import org.bukkit.entity.Player;

public abstract class CosmeticKillEffect extends Cosmetic {

    public CosmeticKillEffect(String name, String description, XMaterial displayItem, CosmeticType cosmeticType, String permission, int price, CosmeticRarity rarity, CosmeticGroup group) {
        super(name, description, displayItem, cosmeticType, permission, price, rarity, group);
    }

    public CosmeticKillEffect(String name, String description, XMaterial displayItem, CosmeticType cosmeticType, int price, CosmeticRarity rarity, CosmeticGroup group) {
        super(name, description, displayItem, cosmeticType, price, rarity, group);
    }

    public CosmeticKillEffect() {
        super();
    }

    public void onPlayerDeath(Player player) {

    }

    public void onPlayerDeath(Player player, Player killer) {

    }

}
