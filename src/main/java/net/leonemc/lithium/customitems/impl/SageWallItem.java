package net.leonemc.lithium.customitems.impl;

import lombok.Getter;
import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.customitems.CustomItem;
import net.leonemc.lithium.customitems.annotation.ItemInfo;
import net.leonemc.lithium.utils.bukkit.RegionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

@ItemInfo(
        name = "ice_wall",
        displayName = "§aIce Wall",
        description = "Place this to create an ice wall.",
        displayItem = XMaterial.PACKED_ICE
)
public class SageWallItem extends CustomItem implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemInHand();

        if (!isCustomItem(item)) {
            return;
        }

        if (RegionUtil.isWithinRegionContains(event.getBlock().getLocation(), "spawn")) {
            player.sendMessage("§cYou cannot place this item in spawn.");
            event.setCancelled(true);
            return;
        }

        Location location = event.getBlock().getLocation();
        String direction = getDirection(player.getLocation().getYaw());
        List<Location> layerList = new ArrayList<>();

        // Creates the first layer of the wall
        if (direction.equals("S") || direction.equals("N")) {
            // 2 blocks east
            for (int i = 0; i < 3; i++) {
                layerList.add(location.clone().add(-i, 0, 0));
            }
            // 2 blocks west
            for (int i = 0; i < 3; i++) {
                layerList.add(location.clone().add(i, 0, 0));
            }
        } else {
            // 2 blocks south
            for (int i = 0; i < 3; i++) {
                layerList.add(location.clone().add(0, 0, -i));
            }
            // 2 blocks north
            for (int i = 0; i < 3; i++) {
                layerList.add(location.clone().add(0, 0, i));
            }
        }

        List<Layer> wall = new ArrayList<>();

        // Creates the other layers of the wall
        for (int i = 0; i < 3; i++) {
            Layer layer = new Layer();
            for (Location loc : layerList) {
                layer.getLocations().add(loc.clone().add(0, i, 0));
            }

            wall.add(layer);
        }

        List<Location> placedBlocks = new ArrayList<>();

        // Handles the building part of the wall
        for (int i = 0; i < (wall.size()); i++) {
            Layer layer = wall.get(i);
            Bukkit.getScheduler().runTaskLater(Lithium.getInstance(), () -> {
                for (Location loc : layer.getLocations()) {
                    // Make sure there isn't a block already there
                    Block blockAt = loc.getWorld().getBlockAt(loc);
                    if (blockAt.getType() != Material.AIR) {
                        continue;
                    }

                    loc.getBlock().setType(XMaterial.PACKED_ICE.parseMaterial());
                    placedBlocks.add(loc);
                }
            }, (i * 5L));
        }

        // Handles the removal of the wall
        Bukkit.getScheduler().runTaskLater(Lithium.getInstance(), () -> {
            for (Location loc : placedBlocks) {
                loc.getBlock().setType(Material.AIR);
            }
        }, 8 * 20L);

        // Cancel the event so the item doesn't get placed
        use(player);
        event.setCancelled(true);
    }

    public String getDirection(float yaw) {
        yaw -= 180;
        yaw = (yaw % 360);
        if (yaw < 0) yaw += 360;

        if (0 <= yaw && yaw < 22.5) {
            return "N";
        } else if (22.5 <= yaw && yaw < 67.5) {
            return "NE";
        } else if (67.5 <= yaw && yaw < 112.5) {
            return "E";
        } else if (112.5 <= yaw && yaw < 157.5) {
            return "SE";
        } else if (157.5 <= yaw && yaw < 202.5) {
            return "S";
        } else if (202.5 <= yaw && yaw < 247.5) {
            return "SW";
        } else if (247.5 <= yaw && yaw < 292.5) {
            return "W";
        } else if (292.5 <= yaw && yaw < 337.5) {
            return "NW";
        } else if (337.5 <= yaw && yaw < 360.0) {
            return "N";
        } else {
            return "?";
        }
    }

    @Getter
    private static class Layer {
        private final List<Location> locations = new ArrayList<>();
    }
}
