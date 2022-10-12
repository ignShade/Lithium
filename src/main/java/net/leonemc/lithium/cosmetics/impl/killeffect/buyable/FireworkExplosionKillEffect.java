package net.leonemc.lithium.cosmetics.impl.killeffect.buyable;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.cosmetics.annotation.CosmeticInfo;
import net.leonemc.lithium.cosmetics.impl.CosmeticKillEffect;
import net.leonemc.lithium.cosmetics.objects.CosmeticGroup;
import net.leonemc.lithium.cosmetics.objects.CosmeticRarity;
import net.leonemc.lithium.cosmetics.objects.CosmeticType;
import net.leonemc.lithium.utils.bukkit.InstantFirework;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

@CosmeticInfo(
        name = "Firework (Explosion)",
        description = "Launches a instantly exploding firework when you kill someone.",
        displayItem = XMaterial.FIREWORK_ROCKET,
        cosmeticType = CosmeticType.KILL_EFFECT,
        permission = "lithium.killeffect.fireworkexplode",
        rarity = CosmeticRarity.COMMON,
        group = CosmeticGroup.PURCHASE,
        price = 1000
)
public class FireworkExplosionKillEffect extends CosmeticKillEffect {

    @Override
    public void onPlayerDeath(Player player) {
        Location location = player.getLocation();

        new InstantFirework(FireworkEffect.builder()
                .withColor(Color.RED)
                .with(FireworkEffect.Type.BALL_LARGE)
                .build(), location);
    }
}
