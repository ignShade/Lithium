package net.leonemc.lithium.listener;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.commands.WarpNotFoundException;
import net.ess3.api.InvalidWorldException;
import net.leonemc.api.player.APIPlayer;
import net.leonemc.lithium.utils.bukkit.PlayerUtil;
import net.leonemc.lithium.worldguard.events.RegionEnterEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PortalListener implements Listener {

    private final Essentials essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");

    @EventHandler
    public void onRegionEnter(RegionEnterEvent event) {
        Player player = event.getPlayer();
        APIPlayer aPlayer = new APIPlayer(player);
        String regionName = event.getRegion().getId();


        switch (regionName) {
            case "portal_beginner":
                if (!aPlayer.hasOnlyIronArmor() && !player.hasMetadata("modmode")) {
                    PlayerUtil.sendTitle(player, "§c§lAccess Denied!", "Using diamond armor is not allowed!");
                    return;
                }

                try {
                    Location loc = essentials.getWarps().getWarp("beginnerarena");
                    player.teleport(loc);
                } catch (WarpNotFoundException | InvalidWorldException ignored) {}
                break;
            case "portal_op":
                if (!aPlayer.hasDiamondArmor() && !player.hasMetadata("modmode")) {
                    PlayerUtil.sendTitle(player, "§c§lAccess Denied!", "Using iron armor is not allowed!");
                    return;
                }

                try {
                    Location loc = essentials.getWarps().getWarp("oparena");
                    player.teleport(loc);
                } catch (WarpNotFoundException | InvalidWorldException ignored) {}
                break;
        }
    }

}
