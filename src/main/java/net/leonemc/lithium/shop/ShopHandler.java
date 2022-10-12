package net.leonemc.lithium.shop;

import lombok.Getter;
import net.leonemc.lithium.shop.objects.ShopCategory;
import net.leonemc.lithium.shop.objects.ShopItem;
import net.leonemc.lithium.shop.objects.ShopType;
import net.leonemc.lithium.utils.bukkit.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

@Getter
public class ShopHandler {

    private final ArrayList<ShopItem> shopItems = new ArrayList<>();

    public ShopHandler() {
        shopItems.add(new ShopItem("Iron Helmet", new ItemBuilder(Material.IRON_HELMET).toItemStack(), 5, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Iron Chestplate", new ItemBuilder(Material.IRON_CHESTPLATE).toItemStack(), 10, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Iron Leggings", new ItemBuilder(Material.IRON_LEGGINGS).toItemStack(), 10, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Iron Boots", new ItemBuilder(Material.IRON_BOOTS).toItemStack(), 5, ShopType.BUY_ONLY, ShopCategory.GEAR));

        shopItems.add(new ShopItem("Diamond Helmet", new ItemBuilder(Material.DIAMOND_HELMET).toItemStack(), 1500, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Diamond Chestplate", new ItemBuilder(Material.DIAMOND_CHESTPLATE).toItemStack(), 2400, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Diamond Leggings", new ItemBuilder(Material.DIAMOND_LEGGINGS).toItemStack(), 2100, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Diamond Boots", new ItemBuilder(Material.DIAMOND_BOOTS).toItemStack(), 1200, ShopType.BUY_ONLY, ShopCategory.GEAR));

        shopItems.add(new ShopItem("Diamond Sword", new ItemBuilder(Material.DIAMOND_SWORD).toItemStack(), 1000, ShopType.BUY_ONLY, ShopCategory.GEAR));

        shopItems.add(new ShopItem("Bow", new ItemBuilder(Material.BOW).toItemStack(), 5, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Arrows", new ItemBuilder(Material.ARROW, 16).toItemStack(), 5, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Fishing Rod", new ItemBuilder(Material.FISHING_ROD).toItemStack(), 5, ShopType.BUY_ONLY, ShopCategory.GEAR));

        shopItems.add(new ShopItem("Potion of Regeneration", new ItemBuilder(Material.POTION).setDurability((short) 8225).toItemStack(), 250, ShopType.BUY_ONLY, ShopCategory.POTIONS));
        shopItems.add(new ShopItem("Potion of Regeneration", new ItemBuilder(Material.POTION).setDurability((short) 16417).toItemStack(), 500, ShopType.BUY_ONLY, ShopCategory.POTIONS));

        shopItems.add(new ShopItem("Potion of Leaping", new ItemBuilder(Material.POTION).setDurability((short) 8267).toItemStack(), 50, ShopType.BUY_ONLY, ShopCategory.POTIONS));
        shopItems.add(new ShopItem("Potion of Leaping", new ItemBuilder(Material.POTION).setDurability((short) 8235).toItemStack(), 100, ShopType.BUY_ONLY, ShopCategory.POTIONS));

        shopItems.add(new ShopItem("Potion of Strength", new ItemBuilder(Material.POTION).setDurability((short) 8201).toItemStack(), 500, ShopType.BUY_ONLY, ShopCategory.POTIONS));
        shopItems.add(new ShopItem("Potion of Strength", new ItemBuilder(Material.POTION).setDurability((short) 8265).toItemStack(), 1000, ShopType.BUY_ONLY, ShopCategory.POTIONS));

        shopItems.add(new ShopItem("Potion of Swiftness", new ItemBuilder(Material.POTION).setDurability((short) 8226).toItemStack(), 5, ShopType.BUY_ONLY, ShopCategory.POTIONS));
        shopItems.add(new ShopItem("Potion of Swiftness", new ItemBuilder(Material.POTION).setDurability((short) 8194).toItemStack(), 5, ShopType.BUY_ONLY, ShopCategory.POTIONS));
        shopItems.add(new ShopItem("Potion of Swiftness", new ItemBuilder(Material.POTION).setDurability((short) 8258).toItemStack(), 5, ShopType.BUY_ONLY, ShopCategory.POTIONS));

        shopItems.add(new ShopItem("Potion of Healing", new ItemBuilder(Material.POTION).setDurability((short) 16453).toItemStack(), 100, ShopType.BUY_ONLY, ShopCategory.POTIONS));
        shopItems.add(new ShopItem("Potion of Healing", new ItemBuilder(Material.POTION).setDurability((short) 16421).toItemStack(), 250, ShopType.BUY_ONLY, ShopCategory.POTIONS));

        shopItems.add(new ShopItem("Enderpearl", new ItemBuilder(Material.ENDER_PEARL).toItemStack(), 500, ShopType.BUY_ONLY, ShopCategory.OTHER));
        shopItems.add(new ShopItem("Golden Apple", new ItemBuilder(Material.GOLDEN_APPLE).toItemStack(), 10, ShopType.BUY_ONLY, ShopCategory.OTHER));
        shopItems.add(new ShopItem("Golden Apple", new ItemBuilder(Material.GOLDEN_APPLE).setDurability((short) 1).toItemStack(), 3000, ShopType.BUY_ONLY, ShopCategory.OTHER));

        shopItems.add(new ShopItem("Steak", new ItemBuilder(Material.COOKED_BEEF, 16).toItemStack(), 5, ShopType.BUY_ONLY, ShopCategory.FOOD));
        shopItems.add(new ShopItem("Steak", new ItemBuilder(Material.GOLDEN_CARROT, 8).toItemStack(), 1, ShopType.BUY_ONLY, ShopCategory.FOOD));

        shopItems.add(new ShopItem("XP Bottle", new ItemBuilder(Material.EXP_BOTTLE, 4).toItemStack(), 200, ShopType.BUY_ONLY, ShopCategory.ENCHANTING));
        shopItems.add(new ShopItem("XP Bottle", new ItemBuilder(Material.EXP_BOTTLE, 16).toItemStack(), 800, ShopType.BUY_ONLY, ShopCategory.ENCHANTING));
    }

    /*/
    Keeping this here, so we don't have to remake it
     */
    /*/public void oldShop() {
        //others
        shopItems.add(new ShopItem("Bow", new ItemStack(Material.BOW, 1), 100, ShopType.BUY_ONLY, ShopCategory.OTHER));
        shopItems.add(new ShopItem("Arrow", new ItemStack(Material.ARROW, 16), 32, ShopType.BUY_ONLY, ShopCategory.OTHER));
        shopItems.add(new ShopItem("Golden Apple", new ItemStack(Material.GOLDEN_APPLE, 1), 500, ShopType.BUY_ONLY, ShopCategory.OTHER));
        shopItems.add(new ShopItem("Golden Apple", new ItemBuilder(Material.GOLDEN_APPLE, 1).setDurability((short) 1).toItemStack(), 25000, ShopType.BUY_ONLY, ShopCategory.OTHER));
        shopItems.add(new ShopItem("Fishing Rod", new ItemBuilder(Material.FISHING_ROD, 1).toItemStack(), 50, ShopType.BUY_ONLY, ShopCategory.OTHER));
        shopItems.add(new ShopItem("Bottle o' Enchanting", new ItemBuilder(Material.EXP_BOTTLE, 16).toItemStack(), 5000, ShopType.BUY_ONLY, ShopCategory.OTHER));
        shopItems.add(new ShopItem("Enderpearl", new ItemBuilder(Material.ENDER_PEARL, 1).toItemStack(), 2000, ShopType.BUY_ONLY, ShopCategory.OTHER));

        //ores
        //shopItems.add(new ShopItem("Emerald", new ItemStack(Material.EMERALD, 8), 10, ShopType.SELL_ONLY, ShopCategory.ORES));
        //shopItems.add(new ShopItem("Diamond", new ItemStack(Material.DIAMOND, 8), 50, ShopType.BUY_ONLY, ShopCategory.ORES));

        shopItems.add(new ShopItem("Gold Ore", new ItemStack(Material.GOLD_ORE, 8), 2, ShopType.SELL_ONLY, ShopCategory.ORES));
        shopItems.add(new ShopItem("Iron Ore", new ItemStack(Material.IRON_ORE, 8), 3, ShopType.SELL_ONLY, ShopCategory.ORES));
        shopItems.add(new ShopItem("Stone", new ItemStack(Material.STONE, 32), 20, ShopType.SELL_ONLY, ShopCategory.ORES));
        shopItems.add(new ShopItem("Cobblestone", new ItemStack(Material.COBBLESTONE, 32), 15, ShopType.SELL_ONLY, ShopCategory.ORES));
        shopItems.add(new ShopItem("Acacia Wood", new ItemBuilder(Material.LOG_2, 32).setDurability((short) 0).toItemStack(), 320, ShopType.SELL_ONLY, ShopCategory.ORES));
        shopItems.add(new ShopItem("Lapis", new ItemStack(Material.LAPIS_ORE, 32), 4, ShopType.SELL_ONLY, ShopCategory.ORES));
        shopItems.add(new ShopItem("Redstone", new ItemStack(Material.REDSTONE, 32), 4, ShopType.SELL_ONLY, ShopCategory.ORES));
        shopItems.add(new ShopItem("Coal", new ItemStack(Material.COAL, 32), 10, ShopType.SELL_ONLY, ShopCategory.ORES));/*/

        //pickaxes
        /*/shopItems.add(new ShopItem("Iron Pickaxe", new ItemBuilder(Material.IRON_PICKAXE, 1)
                .addEnchant(Enchantment.DIG_SPEED, 1)
                .addEnchant(Enchantment.DURABILITY, 10).toItemStack()
                , 250, ShopType.BUY_ONLY, ShopCategory.MINING));
        shopItems.add(new ShopItem("Iron Pickaxe", new ItemBuilder(Material.IRON_PICKAXE, 1)
                .addEnchant(Enchantment.DIG_SPEED, 2)
                .addEnchant(Enchantment.DURABILITY, 10).toItemStack()
                , 1000, ShopType.BUY_ONLY, ShopCategory.MINING));
        shopItems.add(new ShopItem("Iron Pickaxe", new ItemBuilder(Material.IRON_PICKAXE, 1)
                .addEnchant(Enchantment.DIG_SPEED, 3)
                .addEnchant(Enchantment.DURABILITY, 10).toItemStack()
                , 2000, ShopType.BUY_ONLY, ShopCategory.MINING));
        shopItems.add(new ShopItem("Iron Pickaxe", new ItemBuilder(Material.IRON_PICKAXE, 1)
                .addEnchant(Enchantment.DIG_SPEED, 4)
                .addEnchant(Enchantment.DURABILITY, 10).toItemStack()
                , 4000, ShopType.BUY_ONLY, ShopCategory.MINING));
        shopItems.add(new ShopItem("Iron Pickaxe", new ItemBuilder(Material.IRON_PICKAXE, 1)
                .addEnchant(Enchantment.DIG_SPEED, 5)
                .addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1)
                .addEnchant(Enchantment.DURABILITY, 10).toItemStack()
                , 2500, ShopType.BUY_ONLY, ShopCategory.MINING));

        shopItems.add(new ShopItem("Diamond Pickaxe", new ItemBuilder(Material.DIAMOND_PICKAXE, 1)
                .addEnchant(Enchantment.DIG_SPEED, 1)
                .addEnchant(Enchantment.DURABILITY, 10).toItemStack()
                , 5000, ShopType.BUY_ONLY, ShopCategory.MINING));
        shopItems.add(new ShopItem("Diamond Pickaxe", new ItemBuilder(Material.DIAMOND_PICKAXE, 1)
                .addEnchant(Enchantment.DIG_SPEED, 2)
                .addEnchant(Enchantment.DURABILITY, 10).toItemStack()
                , 10000, ShopType.BUY_ONLY, ShopCategory.MINING));
        shopItems.add(new ShopItem("Diamond Pickaxe", new ItemBuilder(Material.DIAMOND_PICKAXE, 1)
                .addEnchant(Enchantment.DIG_SPEED, 3)
                .addEnchant(Enchantment.DURABILITY, 10).toItemStack()
                , 20000, ShopType.BUY_ONLY, ShopCategory.MINING));
        shopItems.add(new ShopItem("Diamond Pickaxe", new ItemBuilder(Material.DIAMOND_PICKAXE, 1)
                .addEnchant(Enchantment.DIG_SPEED, 4)
                .addEnchant(Enchantment.DURABILITY, 10).toItemStack()
                , 30000, ShopType.BUY_ONLY, ShopCategory.MINING));
        shopItems.add(new ShopItem("Diamond Pickaxe", new ItemBuilder(Material.DIAMOND_PICKAXE, 1)
                .addEnchant(Enchantment.DIG_SPEED, 5)
                .addEnchant(Enchantment.DURABILITY, 10).toItemStack()
                , 40000, ShopType.BUY_ONLY, ShopCategory.MINING));
        shopItems.add(new ShopItem("Diamond Pickaxe", new ItemBuilder(Material.DIAMOND_PICKAXE, 1)
                .addEnchant(Enchantment.DIG_SPEED, 6)
                .addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1)
                .addEnchant(Enchantment.DURABILITY, 10).toItemStack()
                , 50000, ShopType.BUY_ONLY, ShopCategory.MINING));
        shopItems.add(new ShopItem("Diamond Pickaxe", new ItemBuilder(Material.DIAMOND_PICKAXE, 1)
                .addEnchant(Enchantment.DIG_SPEED, 7)
                .addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1)
                .addEnchant(Enchantment.DURABILITY, 10).toItemStack()
                , 60000, ShopType.BUY_ONLY, ShopCategory.MINING));
        shopItems.add(new ShopItem("Diamond Pickaxe", new ItemBuilder(Material.DIAMOND_PICKAXE, 1)
                .addEnchant(Enchantment.DIG_SPEED, 8)
                .addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1)
                .addEnchant(Enchantment.DURABILITY, 10).toItemStack()
                , 750000, ShopType.BUY_ONLY, ShopCategory.MINING));
        shopItems.add(new ShopItem("Diamond Pickaxe", new ItemBuilder(Material.DIAMOND_PICKAXE, 1)
                .addEnchant(Enchantment.DIG_SPEED, 9)
                .addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1)
                .addEnchant(Enchantment.DURABILITY, 10).toItemStack()
                , 850000, ShopType.BUY_ONLY, ShopCategory.MINING));
        shopItems.add(new ShopItem("Diamond Pickaxe", new ItemBuilder(Material.DIAMOND_PICKAXE, 1)
                .addEnchant(Enchantment.DIG_SPEED, 10)
                .addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1)
                .addEnchant(Enchantment.DURABILITY, 10).toItemStack()
                , 150000, ShopType.BUY_ONLY, ShopCategory.MINING));/*/

        //gear
        /*/
        shopItems.add(new ShopItem("Iron Helmet", new ItemBuilder(Material.IRON_HELMET, 1)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack()
                , 250, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Iron Chestplate", new ItemBuilder(Material.IRON_CHESTPLATE, 1)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack()
                , 250, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Iron Leggings", new ItemBuilder(Material.IRON_LEGGINGS, 1)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack()
                , 250, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Iron Boots", new ItemBuilder(Material.IRON_BOOTS, 1)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack()
                , 250, ShopType.BUY_ONLY, ShopCategory.GEAR));

        shopItems.add(new ShopItem("Iron Helmet", new ItemBuilder(Material.IRON_HELMET, 1)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack()
                , 750, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Iron Chestplate", new ItemBuilder(Material.IRON_CHESTPLATE, 1)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack()
                , 750, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Iron Leggings", new ItemBuilder(Material.IRON_LEGGINGS, 1)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack()
                , 750, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Iron Boots", new ItemBuilder(Material.IRON_BOOTS, 1)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack()
                , 750, ShopType.BUY_ONLY, ShopCategory.GEAR));

        shopItems.add(new ShopItem("Diamond Helmet", new ItemBuilder(Material.DIAMOND_HELMET, 1).toItemStack()
                , 1000, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Diamond Chestplate", new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1).toItemStack()
                , 1000, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Diamond Leggings", new ItemBuilder(Material.DIAMOND_LEGGINGS, 1).toItemStack()
                , 1000, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Diamond Boots", new ItemBuilder(Material.DIAMOND_BOOTS, 1).toItemStack()
                , 1000, ShopType.BUY_ONLY, ShopCategory.GEAR));

        shopItems.add(new ShopItem("Iron Sword", new ItemBuilder(Material.IRON_SWORD, 1).addEnchant(Enchantment.DAMAGE_ALL, 1).toItemStack()
                , 250, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Iron Sword", new ItemBuilder(Material.IRON_SWORD, 1).addEnchant(Enchantment.DAMAGE_ALL, 2).toItemStack()
                , 750, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Iron Sword", new ItemBuilder(Material.DIAMOND_SWORD, 1).toItemStack()
                , 750, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Iron Sword", new ItemBuilder(Material.IRON_SWORD, 1).addEnchant(Enchantment.DAMAGE_ALL, 3).toItemStack()
                , 1500, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Diamond Sword", new ItemBuilder(Material.DIAMOND_SWORD).toItemStack(), 750, ShopType.BUY_ONLY, ShopCategory.GEAR));
        shopItems.add(new ShopItem("Diamond Sword", new ItemBuilder(Material.DIAMOND_SWORD, 1).addEnchant(Enchantment.DAMAGE_ALL, 1).toItemStack()
                , 1000, ShopType.BUY_ONLY, ShopCategory.GEAR));

        //potions
        shopItems.add(new ShopItem("Potion of Regeneration", new ItemBuilder(Material.POTION, 1).setDurability((short) 8225).toItemStack()
                , 1000, ShopType.BUY_ONLY, ShopCategory.POTIONS));
        shopItems.add(new ShopItem("Potion of Swiftness", new ItemBuilder(Material.POTION, 1).setDurability((short) 8226).toItemStack()
                , 150, ShopType.BUY_ONLY, ShopCategory.POTIONS));
        shopItems.add(new ShopItem("Potion of Regeneration", new ItemBuilder(Material.POTION, 1).setDurability((short) 8257).toItemStack()
                , 1000, ShopType.BUY_ONLY, ShopCategory.POTIONS));
        shopItems.add(new ShopItem("Potion of Strength", new ItemBuilder(Material.POTION, 1).setDurability((short) 8265).toItemStack()
                , 5000, ShopType.BUY_ONLY, ShopCategory.POTIONS));
        shopItems.add(new ShopItem("Potion of Swiftness", new ItemBuilder(Material.POTION, 1).setDurability((short) 8258).toItemStack()
                , 150, ShopType.BUY_ONLY, ShopCategory.POTIONS));
    }/*/
}
