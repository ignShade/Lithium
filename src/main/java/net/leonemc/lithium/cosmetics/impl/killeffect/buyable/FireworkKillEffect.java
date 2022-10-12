package net.leonemc.lithium.cosmetics.impl.killeffect.buyable;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.cosmetics.annotation.CosmeticInfo;
import net.leonemc.lithium.cosmetics.objects.CosmeticGroup;
import net.leonemc.lithium.cosmetics.objects.CosmeticRarity;
import net.leonemc.lithium.cosmetics.objects.CosmeticType;
import net.leonemc.lithium.cosmetics.impl.CosmeticKillEffect;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.block.data.type.Fire;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

@CosmeticInfo(
        name = "Firework",
        description = "Launches a firework when you kill someone.",
        displayItem = XMaterial.FIREWORK_ROCKET,
        cosmeticType = CosmeticType.KILL_EFFECT,
        permission = "lithium.killeffect.firework",
        rarity = CosmeticRarity.COMMON,
        group = CosmeticGroup.PURCHASE,
        price = 2000
)
public class FireworkKillEffect extends CosmeticKillEffect {

    @Override
    public void onPlayerDeath(Player player) {
        Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
        FireworkMeta fwMeta = firework.getFireworkMeta();
        fwMeta.addEffect(FireworkEffect.builder()
                .withColor(Color.RED)
                .with(FireworkEffect.Type.BALL_LARGE)
                .build()
        );
    }
}
