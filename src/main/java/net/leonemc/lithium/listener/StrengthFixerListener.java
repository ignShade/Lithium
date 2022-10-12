package net.leonemc.lithium.listener;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StrengthFixerListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getDamager();
        for (PotionEffect effect : p.getActivePotionEffects()) {
            if (!effect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
                continue;
            }

            ItemStack weapon = p.getItemInHand() != null ? p.getItemInHand() : new ItemStack(Material.AIR);
            int sharpnessLevel = weapon.containsEnchantment(Enchantment.DAMAGE_ALL) ? weapon.getEnchantmentLevel(Enchantment.DAMAGE_ALL) : 0;
            int strengthLevel = effect.getAmplifier() + 1;
            int damagePerLevel = 2;
            double totalDamage = e.getDamage();
            double weaponDamage = (totalDamage - 1.25 * (double) sharpnessLevel) / (1.0 + 1.3 * (double) strengthLevel) - 1.0;
            double finalDamage = 1.0 + weaponDamage + 1.25 * (double) sharpnessLevel + (double) (damagePerLevel * strengthLevel);
            e.setDamage(finalDamage);
            break;
        }
    }
}
