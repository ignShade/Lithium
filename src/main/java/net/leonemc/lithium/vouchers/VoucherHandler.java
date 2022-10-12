package net.leonemc.lithium.vouchers;

import net.leonemc.lithium.utils.bukkit.ItemUtil;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class VoucherHandler {

    public boolean isVoucher(ItemStack is) {
        return ItemUtil.hasNBTTag(is, "voucher");
    }

    public String getCommand(ItemStack is) {
        net.minecraft.server.v1_8_R3.ItemStack nmsNote = CraftItemStack.asNMSCopy(is);
        NBTTagCompound compound = (nmsNote.hasTag()) ? nmsNote.getTag() : new NBTTagCompound();

        return compound.getString("command");
    }

    public ItemStack createVoucher(Player player, String command) {
        UUID uuid = UUID.randomUUID();
        ItemStack note = player.getItemInHand();

        net.minecraft.server.v1_8_R3.ItemStack nmsNote = CraftItemStack.asNMSCopy(note);
        NBTTagCompound compound = (nmsNote.hasTag()) ? nmsNote.getTag() : new NBTTagCompound();

        compound.set("voucher", new NBTTagInt(1));
        compound.set("command", new NBTTagString(command));
        compound.set("creator", new NBTTagString(player.getName()));
        compound.set("uuid", new NBTTagString(uuid.toString()));

        nmsNote.setTag(compound);

        return CraftItemStack.asBukkitCopy(nmsNote);
    }

}
