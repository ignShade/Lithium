package net.leonemc.lithium.cosmetics.impl.killeffect.silver;

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
        name = "Head Rocket",
        description = "Launches your enemies head into the sky",
        displayItem = XMaterial.SKELETON_SKULL,
        cosmeticType = CosmeticType.KILL_EFFECT,
        permission = "lithium.killeffect.headrocket",
        rarity = CosmeticRarity.RARE,
        group = CosmeticGroup.SILVER
)
public class HeadRocketKillEffect extends CosmeticKillEffect {


    @Override
    public void onPlayerDeath(Player player, Player killer) {
        Location loc = player.getLocation().clone();

        ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class);
        stand.setHelmet(new ItemBuilder(Material.SKULL_ITEM, 1).setDurability((byte) 3).setSkullOwner(player.getName()).setName("§aHead Rocket").toItemStack());
        stand.setGravity(false);
        stand.setVisible(false);

        player.getWorld().playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 5, 1);

        new BukkitRunnable() {
            int x = -360;
            int run = 0;

            @Override
            public void run() {
                Location l = stand.getLocation();
                l.setPitch(x);
                l.setY(l.getY() + 0.40);
                stand.setHeadPose(stand.getHeadPose().add(0, 0.23, 0));
                stand.teleport(l);

                ParticleEffect.FIREWORKS_SPARK.display(0, 0, 0, 1, 1, l.clone(), 25.0);

                x++;
                run++;

                if (run == 35) {
                    Location particle = stand.getLocation().clone().add(random(-0.5, 0.5), random(0.3, 0.6), random(-0.5, 0.5));
                    for (int i = 0; i < 10; i++) {
                        ParticleEffect.ITEM_CRACK.display(new ParticleEffect.ItemData(Material.REDSTONE, (byte) 0), 0.2f, 0.2f, 0.2f, 0.1f, 5, particle, 20.0);
                        ParticleEffect.BLOCK_DUST.display(new ParticleEffect.BlockData(Material.REDSTONE_BLOCK, (byte) 0), 0.2f, 0.2f, 0.2f, 0.1f, 5, particle, 20.0);
                    }
                    stand.remove();
                    this.cancel();
                }

            }
        }.runTaskTimer(Lithium.getInstance(), 0, 1);
    }

    protected double random(double n, double n2) {
        return n + ThreadLocalRandom.current().nextDouble() * (n2 - n);
    }
}
