package net.leonemc.lithium.customitems;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.customitems.annotation.ItemInfo;
import net.leonemc.lithium.utils.bukkit.ItemBuilder;
import net.leonemc.lithium.utils.java.StringUtils;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public abstract class CustomItem {

    private String name;
    private String displayName;
    private String description;
    private XMaterial item;
    private int uses;

    public CustomItem() {
        ItemInfo info = getClass().getAnnotation(ItemInfo.class);

        this.name = info.name();
        this.displayName = info.displayName();
        this.description = info.description();
        this.item = info.displayItem();
        this.uses = info.uses();
    }

    private ItemStack getBaseItem(int uses) {
        return new ItemBuilder(item.parseItem())
                .setName(displayName)
                .setLore(Arrays.asList(
                        "§7" + description,
                        " ",
                        "§eUses: §a" + uses
                )).toItemStack();
    }

    public ItemStack getItemStack() {
        net.minecraft.server.v1_8_R3.ItemStack nmsNote = CraftItemStack.asNMSCopy(getBaseItem(uses));
        NBTTagCompound compound = (nmsNote.hasTag()) ? nmsNote.getTag() : new NBTTagCompound();

        compound.set("ability", new NBTTagString(name));
        compound.set("uses", new NBTTagInt(uses));

        nmsNote.setTag(compound);

        ItemStack item = CraftItemStack.asBukkitCopy(nmsNote);
        List<String> lore = item.getItemMeta().getLore();

        lore.add(StringUtils.generateInvisible() + " ");

        return new ItemBuilder(item)
                .setLore(lore)
                .toItemStack();
    }

    public boolean isCustomItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return false;
        }

        net.minecraft.server.v1_8_R3.ItemStack nmsNote = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsNote.hasTag()) ? nmsNote.getTag() : new NBTTagCompound();

        if (!compound.hasKey("ability")) {
            return false;
        }

        String ability = compound.getString("ability");
        return ability.equals(name);
    }

    public void use(Player player) {
        net.minecraft.server.v1_8_R3.ItemStack heldItem = CraftItemStack.asNMSCopy(player.getItemInHand());
        NBTTagCompound heldItemComp = (heldItem.hasTag()) ? heldItem.getTag() : new NBTTagCompound();

        int uses = heldItemComp.getInt("uses");
        String ability = heldItemComp.getString("ability");

        net.minecraft.server.v1_8_R3.ItemStack nmsNote = CraftItemStack.asNMSCopy(getBaseItem(uses - 1));
        NBTTagCompound compound = (nmsNote.hasTag()) ? nmsNote.getTag() : new NBTTagCompound();

        if (uses == 1) {
            player.setItemInHand(null);
            return;
        }

        compound.set("uses", new NBTTagInt(uses - 1));
        compound.set("ability", new NBTTagString(ability));
        nmsNote.setTag(compound);

        ItemStack item = CraftItemStack.asBukkitCopy(nmsNote);

        // make the durability show the uses left
        double percentage = (double) (uses - 1) / (double) this.uses; // convert available uses to percentage
        int durability = (int) (percentage * item.getType().getMaxDurability()); // convert percentage to durability
        short fixed = (short) (item.getType().getMaxDurability() - durability); // set the durability

        item.setDurability(fixed);

        // For some reason it has to be done a tick later...
        Bukkit.getScheduler().runTaskLater(Lithium.getInstance(), () -> {
            player.setItemInHand(item);
            player.updateInventory();
        }, 1L);
    }
}
