package net.leonemc.lithium.cosmetics.impl.killeffect.blue;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.cosmetics.annotation.CosmeticInfo;
import net.leonemc.lithium.cosmetics.impl.CosmeticKillEffect;
import net.leonemc.lithium.cosmetics.objects.CosmeticGroup;
import net.leonemc.lithium.cosmetics.objects.CosmeticRarity;
import net.leonemc.lithium.cosmetics.objects.CosmeticType;
import net.leonemc.lithium.utils.bukkit.InstantFirework;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

@CosmeticInfo(
        name = "Corpse",
        description = "Launches a instantly exploding firework when you kill someone.",
        displayItem = XMaterial.RED_BED,
        cosmeticType = CosmeticType.KILL_EFFECT,
        permission = "lithium.killeffect.corpse",
        rarity = CosmeticRarity.LEGENDARY,
        group = CosmeticGroup.BLUE
)
public class CorpseKillEffect extends CosmeticKillEffect {

    @Override
    public void onPlayerDeath(Player killed) {
        Location location = killed.getLocation();

        Lithium.getInstance().getCorpses().spawnCorpse(killed, location, null);
    }
}
