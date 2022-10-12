package net.leonemc.lithium.cosmetics.impl.killeffect.buyable;

import net.leonemc.api.items.ItemBuilder;
import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.cosmetics.annotation.CosmeticInfo;
import net.leonemc.lithium.cosmetics.impl.CosmeticKillEffect;
import net.leonemc.lithium.cosmetics.objects.CosmeticGroup;
import net.leonemc.lithium.cosmetics.objects.CosmeticRarity;
import net.leonemc.lithium.cosmetics.objects.CosmeticType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@CosmeticInfo(
        name = "Cookies",
        description = "Turns your enemy into a bunch of cookies",
        displayItem = XMaterial.COOKIE,
        cosmeticType = CosmeticType.KILL_EFFECT,
        permission = "lithium.killeffect.cookies",
        rarity = CosmeticRarity.COMMON,
        group = CosmeticGroup.PURCHASE,
        price = 1000
)
public class CookiesKillEffect extends CosmeticKillEffect {

    private final ArrayList<Entity> entities = new ArrayList<>();

    @Override
    public void onPlayerDeath(Player player) {
        Location loc = player.getLocation().clone().add(0.0, 1.5, 0.0);
        ArrayList<ItemStack> items = new ArrayList<>();

        for (short i = 1; i < 14; ++i) {
            if (i == 3)
                continue;

            items.add(new ItemBuilder(Material.COOKIE, 1).toItemStack());
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
