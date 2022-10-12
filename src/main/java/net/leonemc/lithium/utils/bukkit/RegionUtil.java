package net.leonemc.lithium.utils.bukkit;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import static com.sk89q.worldguard.bukkit.BukkitUtil.toVector;

public class RegionUtil {

    public static boolean isWithinRegion(Player player, String region) {
        return isWithinRegion(player.getLocation(), region);
    }

    public static boolean isWithinRegionContains(Player player, String region) {
        return isWithinRegionContains(player.getLocation(), region);
    }

    public static boolean isWithinRegion(Block block, String region) {
        return isWithinRegion(block.getLocation(), region);
    }

    public static boolean isWithinRegion(Location loc, String region) {
        WorldGuardPlugin guard = WorldGuardPlugin.inst();
        Vector v = toVector(loc);
        RegionManager manager = guard.getRegionManager(loc.getWorld());
        ApplicableRegionSet set = manager.getApplicableRegions(v);
        for (ProtectedRegion each : set)
            if (each.getId().equalsIgnoreCase(region))
                return true;
        return false;
    }


    public static boolean isWithinRegionContains(Location loc, String region) {
        WorldGuardPlugin guard = WorldGuardPlugin.inst();
        Vector v = toVector(loc);
        RegionManager manager = guard.getRegionManager(loc.getWorld());
        ApplicableRegionSet set = manager.getApplicableRegions(v);
        for (ProtectedRegion each : set)
            if (each.getId().toLowerCase().contains(region.toLowerCase()))
                return true;
        return false;
    }

}
