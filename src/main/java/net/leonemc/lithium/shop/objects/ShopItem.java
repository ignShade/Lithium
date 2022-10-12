package net.leonemc.lithium.shop.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class ShopItem {

    private String name;
    private ItemStack item;
    private int price;
    private ShopType shopType; //used for checking if it's buy, sell or both
    private ShopCategory category;

}
