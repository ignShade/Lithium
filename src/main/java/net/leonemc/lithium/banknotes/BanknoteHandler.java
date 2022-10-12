package net.leonemc.lithium.banknotes;

import net.leonemc.api.util.bukkit.ItemBuilder;
import net.leonemc.lithium.utils.bukkit.CC;
import net.leonemc.lithium.utils.bukkit.ItemUtil;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

public class BanknoteHandler {

    public boolean isBankNote(ItemStack is) {
        return ItemUtil.hasNBTTag(is, "banknote");
    }

    public int getAmount(ItemStack is) {
        net.minecraft.server.v1_8_R3.ItemStack nmsNote = CraftItemStack.asNMSCopy(is);
        NBTTagCompound compound = (nmsNote.hasTag()) ? nmsNote.getTag() : new NBTTagCompound();

        return compound.getInt("amount");
    }

    public ItemStack createBanknote(Player player, int amount) {
        UUID uuid = UUID.randomUUID();
        ItemStack note = new ItemBuilder(Material.PAPER).name("§6§lBank Note").setLore(Arrays.asList(
                CC.MENU_BAR,
                "§eAmount: §a" + amount,
                "§eCreated by: §f" + player.getName(),
                "§eUUID: §f" + uuid,
                CC.MENU_BAR
        )).build();

        net.minecraft.server.v1_8_R3.ItemStack nmsNote = CraftItemStack.asNMSCopy(note);
        NBTTagCompound compound = (nmsNote.hasTag()) ? nmsNote.getTag() : new NBTTagCompound();

        compound.set("banknote", new NBTTagInt(1));
        compound.set("amount", new NBTTagInt(amount));
        compound.set("creator", new NBTTagString(player.getName()));
        compound.set("uuid", new NBTTagString(uuid.toString()));

        nmsNote.setTag(compound);

        return CraftItemStack.asBukkitCopy(nmsNote);
    }

}
