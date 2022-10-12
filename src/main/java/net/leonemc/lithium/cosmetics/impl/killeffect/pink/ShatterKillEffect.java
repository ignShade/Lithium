package net.leonemc.lithium.cosmetics.impl.killeffect.pink;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.cosmetics.annotation.CosmeticInfo;
import net.leonemc.lithium.cosmetics.impl.CosmeticKillEffect;
import net.leonemc.lithium.cosmetics.objects.CosmeticGroup;
import net.leonemc.lithium.cosmetics.objects.CosmeticRarity;
import net.leonemc.lithium.cosmetics.objects.CosmeticType;
import net.leonemc.lithium.utils.bukkit.ItemBuilder;
import net.leonemc.lithium.utils.bukkit.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

@CosmeticInfo(
        name = "Shatter",
        description = "Shatters your enemies into pieces",
        displayItem = XMaterial.WHITE_STAINED_GLASS_PANE,
        cosmeticType = CosmeticType.KILL_EFFECT,
        permission = "lithium.killeffect.shatter",
        rarity = CosmeticRarity.RARE,
        group = CosmeticGroup.PINK
)
public class ShatterKillEffect extends CosmeticKillEffect {


    @Override
    public void onPlayerDeath(Player player, Player killer) {
        Location loc = player.getLocation().clone();
        Location particle = loc.add(random(-0.5, 0.5), 1 + random(0.3, 0.6), random(-0.5, 0.5));

        player.getWorld().playSound(loc, Sound.GLASS, 10, 1);

        for (int i = 0; i < 10; i++) {
            ParticleEffect.ITEM_CRACK.display(new ParticleEffect.ItemData(Material.STAINED_GLASS, (byte) 0), 0.2f, 0.2f, 0.2f, 0.1f, 5, particle, 20.0);
            ParticleEffect.BLOCK_DUST.display(new ParticleEffect.BlockData(Material.STAINED_GLASS, (byte) 0), 0.2f, 0.2f, 0.2f, 0.1f, 5, particle, 20.0);
        }
    }

    protected double random(double n, double n2) {
        return n + ThreadLocalRandom.current().nextDouble() * (n2 - n);
    }
}
