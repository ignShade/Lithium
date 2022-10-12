package net.leonemc.lithium.utils.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {

    public static boolean shouldDelete(ItemStack is) {
        //delete all arrows
        if (is.getType() == Material.ARROW) {
            return true;
        }

        //only delete these items if there isn't an enchant on them.
        if (is.getType() == Material.IRON_HELMET ||
                is.getType() == Material.IRON_CHESTPLATE ||
                is.getType() == Material.IRON_LEGGINGS ||
                is.getType() == Material.IRON_BOOTS ||
                is.getType() == Material.IRON_SWORD ||
                is.getType() == Material.BOW) {

            if (!is.hasItemMeta()) {
                return true;
            }
            if (!is.getItemMeta().hasEnchants()) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasNBTTag(ItemStack is, String tag) {
        net.minecraft.server.v1_8_R3.ItemStack craft = CraftItemStack.asNMSCopy(is);
        return craft.getTag() != null && craft.getTag().hasKey(tag);
    }

    public static String getItemName(ItemStack itemInHand) {
        return itemInHand.getItemMeta().getDisplayName();
    }

    public static String getEnchantmentName(Enchantment arg) {
        switch (arg.getName()) {
            case "ARROW_DAMAGE":
                return "Power";
            case "ARROW_FIRE":
                return "Flame";
            case "ARROW_INFINITE":
                return "Infinity";
            case "ARROW_KNOCKBACK":
                return "Punch";
            case "BINDING_CURSE":
                return "Curse of Binding";
            case "DAMAGE_ALL":
                return "Sharpness";
            case "DAMAGE_ARTHROPODS":
                return "Bane of Arthropods";
            case "DAMAGE_UNDEAD":
                return "Smite";
            case "DEPTH_STRIDER":
                return "Depth Strider";
            case "DIG_SPEED":
                return "Efficiency";
            case "DURABILITY":
                return "Unbreaking";
            case "FIRE_ASPECT":
                return "Fire Aspect";
            case "FROST_WALKER":
                return "Frost Walker";
            case "KNOCKBACK":
                return "Knockback";
            case "LOOT_BONUS_BLOCKS":
                return "Fortune";
            case "LOOT_BONUS_MOBS":
                return "Looting";
            case "LUCK":
                return "Luck of the Sea";
            case "LURE":
                return "Lure";
            case "MENDING":
                return "Mending";
            case "OXYGEN":
                return "Respiration";
            case "PROTECTION_ENVIRONMENTAL":
                return "Protection";
            case "PROTECTION_EXPLOSIONS":
                return "Blast Protection";
            case "PROTECTION_FALL":
                return "Feather Falling";
            case "PROTECTION_FIRE":
                return "Fire Protection";
            case "PROTECTION_PROJECTILE":
                return "Projectile Protection";
            case "SILK_TOUCH":
                return "Silk Touch";
            case "SWEEPING_EDGE":
                return "Sweeping Edge";
            case "THORNS":
                return "Thorns";
            case "VANISHING_CURSE":
                return "Cure of Vanishing";
            case "WATER_WORKER":
                return "Aqua Affinity";
            default:
                return "Unknown";
        }
    }

    public static String getNBTTag(ItemStack is, String ability) {
        net.minecraft.server.v1_8_R3.ItemStack craft = CraftItemStack.asNMSCopy(is);
        return craft.getTag().getString(ability);
    }

    public static ItemStack addNBTTag(ItemStack item, String s, String aTrue) {
        net.minecraft.server.v1_8_R3.ItemStack craft = CraftItemStack.asNMSCopy(item);
        craft.getTag().setString(s, aTrue);
        return CraftItemStack.asBukkitCopy(craft);
    }

    public static ItemStack removeNBTTag(ItemStack item, String s) {
        net.minecraft.server.v1_8_R3.ItemStack craft = CraftItemStack.asNMSCopy(item);
        craft.getTag().remove(s);

        return CraftItemStack.asBukkitCopy(craft);
    }
}
