package net.leonemc.lithium.commands;

import net.leonemc.lithium.utils.bukkit.ItemBuilder;
import net.leonemc.lithium.utils.command.Command;
import net.leonemc.lithium.utils.command.CommandArgs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DupeProtectionCommand {

    @Command(name = "dupeprotectdebug", permission = "lithium.dupeprotect")
    public void dupeprotect(CommandArgs args) {
        Player player = args.getPlayer();
        ItemStack heldItem = player.getItemInHand();

        if (heldItem == null) {
            player.sendMessage("§cYou must be holding an item to dupe protect it.");
            return;
        }

        player.sendMessage("§aYou have dupe protected your item.");
        ItemStack is = new ItemBuilder(heldItem).setNBTTag("antidupe", "true").toItemStack();
        player.setItemInHand(is);
    }

}
