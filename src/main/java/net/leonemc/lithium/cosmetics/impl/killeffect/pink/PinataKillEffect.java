package net.leonemc.lithium.cosmetics.impl.killeffect.pink;

import net.leonemc.api.items.ItemBuilder;
import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.cosmetics.annotation.CosmeticInfo;
import net.leonemc.lithium.cosmetics.impl.CosmeticKillEffect;
import net.leonemc.lithium.cosmetics.objects.CosmeticGroup;
import net.leonemc.lithium.cosmetics.objects.CosmeticRarity;
import net.leonemc.lithium.cosmetics.objects.CosmeticType;
import net.leonemc.lithium.utils.bukkit.InstantFirework;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@CosmeticInfo(
        name = "Pinata",
        description = "Turns your enemy into a pinata",
        displayItem = XMaterial.PINK_DYE,
        cosmeticType = CosmeticType.KILL_EFFECT,
        permission = "lithium.killeffect.pinata",
        rarity = CosmeticRarity.COMMON,
        group = CosmeticGroup.PINK
)
public class PinataKillEffect extends CosmeticKillEffect {

    private final ArrayList<Entity> entities = new ArrayList<>();

    @Override
    public void onPlayerDeath(Player player) {
        Location loc = player.getLocation().clone().add(0.0, 1.5, 0.0);
        ArrayList<ItemStack> items = new ArrayList<>();

        for (short i = 1; i < 14; ++i) {
            if (i == 3)
                continue;

            items.add(new ItemBuilder(Material.INK_SACK, 1).setDurability(i).toItemStack());
        }

        for (ItemStack itemStack : items) {
            Item item = loc.getWorld().dropItem(loc.clone().add(this.random(-0.5, 0.5), this.random(0.3, 0.6), this.random(-0.5, 0.5)), itemStack);
            item.setPickupDelay(Integer.MAX_VALUE);
            player.getWorld().playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
            entities.add(item);
            Bukkit.getScheduler().runTaskLater(Lithium.getInstance(), () -> {
                item.remove();
                entities.remove(item);
            }, 60L);
        }

    }

    protected double random(double n, double n2) {
        return n + ThreadLocalRandom.current().nextDouble() * (n2 - n);
    }
}
