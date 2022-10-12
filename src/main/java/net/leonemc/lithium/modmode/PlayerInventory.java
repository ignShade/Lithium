package net.leonemc.lithium.modmode;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@AllArgsConstructor
@Data
public class PlayerInventory {

    private ItemStack[] inventory;
    private ItemStack[] armor;

}
