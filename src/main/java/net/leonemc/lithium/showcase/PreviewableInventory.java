package net.leonemc.lithium.showcase;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class PreviewableInventory {

    private long createdAt;
    private String owner;
    private ItemStack[] contents;
    private ItemStack[] armorContents;

    public boolean isExpired() {
        return System.currentTimeMillis() - createdAt > 1000 * 60 * 5; // 5 minutes
    }
}
