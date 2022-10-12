package net.leonemc.lithium.commands;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Kit;
import com.earth2me.essentials.Kits;
import net.leonemc.api.command.Command;
import net.leonemc.api.command.data.parameter.Param;
import net.leonemc.lithium.kitpreview.KitPreviewMenu;
import org.bukkit.entity.Player;

public class PreviewKitCommand {

    @Command(names = {"previewkit", "previewkit", "viewkit"}, async = true)
    public static void execute(Player player, @Param(name = "kit") String name) {
        Kit kit;
        Essentials ess = (Essentials) org.bukkit.Bukkit.getPluginManager().getPlugin("Essentials");

        try {
            kit = new Kit(name.toLowerCase(), ess);
        } catch (Exception e) {
            player.sendMessage("§cKit not found.");
            return;
        }

        new KitPreviewMenu(kit).openMenu(player);
    }

}
