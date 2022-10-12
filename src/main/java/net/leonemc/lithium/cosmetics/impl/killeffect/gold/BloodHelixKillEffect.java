package net.leonemc.lithium.cosmetics.impl.killeffect.gold;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.cosmetics.annotation.CosmeticInfo;
import net.leonemc.lithium.cosmetics.impl.CosmeticKillEffect;
import net.leonemc.lithium.cosmetics.objects.CosmeticGroup;
import net.leonemc.lithium.cosmetics.objects.CosmeticRarity;
import net.leonemc.lithium.cosmetics.objects.CosmeticType;
import net.leonemc.lithium.utils.bukkit.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@CosmeticInfo(
        name = "Blood Helix",
        description = "Blood magic is very dangerous… but also very cool",
        displayItem = XMaterial.REDSTONE,
        cosmeticType = CosmeticType.KILL_EFFECT,
        permission = "lithium.killeffect.bloodhelix",
        rarity = CosmeticRarity.EPIC,
        group = CosmeticGroup.GOLD
)
public class BloodHelixKillEffect extends CosmeticKillEffect {


    @Override
    public void onPlayerDeath(Player player, Player killer) {
        new BukkitRunnable(){
            double phi = 0.0;

            public void run() {
                Location location1 = killer.getLocation();
                this.phi += 0.39269908169872414;
                for (double d = 0.0; d <= Math.PI * 2; d += 0.19634954084936207) {
                    for (double d2 = 0.0; d2 <= 1.0; d2 += 1.0) {
                        double d3 = 0.4 * (Math.PI * 2 - d) * 0.5 * Math.cos(d + this.phi + d2 * Math.PI);
                        double d4 = 0.5 * d;
                        double d5 = 0.4 * (Math.PI * 2 - d) * 0.5 * Math.sin(d + this.phi + d2 * Math.PI);
                        location1.add(d3, d4, d5);
                        ParticleEffect.REDSTONE.display(0.0f, 0.0f, 0.0f, 0.0f, 1, location1, 100.0);
                        location1.subtract(d3, d4, d5);
                    }
                }
                if (this.phi > Math.PI * 3) {
                    this.cancel();
                }
            }
        }.runTaskTimer(Lithium.getInstance(), 0L, 3L);
    }
}
