package net.leonemc.lithium.dropparty;

import lombok.Getter;
import lombok.Setter;
import net.leonemc.duels.utils.LocationUtil;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.utils.bukkit.ItemUtil;
import net.leonemc.lithium.utils.java.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

@Getter
@Setter
public class DropPartyHandler {

    private Location locA;
    private Location locB;

    private ArrayList<ItemStack> items = new ArrayList<>();

    private double delaySeconds;


    public DropPartyHandler() {
        String a = Lithium.getInstance().getConfig().getString("drop-party.locA");
        String b = Lithium.getInstance().getConfig().getString("drop-party.locB");

        delaySeconds = Lithium.getInstance().getConfig().getDouble("drop-party.delay-seconds");

        if (!a.equalsIgnoreCase("none") && !b.equalsIgnoreCase("none")) {
            this.locA = LocationUtil.INSTANCE.deserialize(a);
            this.locB = LocationUtil.INSTANCE.deserialize(b);
        }
    }

    public void addItem(ItemStack item) {
        items.add(item);
    }

    public void removeItem(ItemStack item) {
        items.remove(item);
    }

    public void removeItem(int item) {
        items.remove(item);
    }

    public void clearItems() {
        items.clear();
    }

    public void start() {
        if (locA == null || locB == null) {
            Lithium.getInstance().getLogger().warning("Drop party locations are not set.");
            return;
        }

        if (items.isEmpty()) {
            Lithium.getInstance().getLogger().warning("Drop party items are not set.");
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (items.size() == 0) {
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("§6§lDrop Party §8» §cThe drop party has ended.");
                    Bukkit.broadcastMessage("");
                    cancel();
                    return;
                }

                ItemStack item = items.get((int) (Math.random() * items.size()));
                Location loc = new Location(locA.getWorld(), Math.random() * (locB.getX() - locA.getX()) + locA.getX(), locA.getY(), Math.random() * (locB.getZ() - locA.getZ()) + locA.getZ());

                removeItem(item);

                // gives the item a item meta thing because nbt tags will error if it doesn't have one
                String name = StringUtils.enumToName(item.getType().name().toLowerCase());
                ItemMeta meta = item.getItemMeta();

                if (!meta.hasDisplayName())
                    meta.setDisplayName("§f" + name);

                item.setItemMeta(meta);

                try {
                    item = ItemUtil.addNBTTag(item, "drop-party", "true");
                } catch (Exception e) {

                }
                loc.getWorld().dropItemNaturally(loc, item);

                String finalName = item.getItemMeta().getDisplayName().equals(name) ?
                        name :
                        item.getItemMeta().getDisplayName() + " §7(" + name + ")";

                Bukkit.broadcastMessage("§6§lDrop Party §8» §fA §e" + finalName + " §fhas been dropped at §e" + loc.getBlockX() + "§f, §e" + loc.getBlockZ() + "§f.");
            }
        }.runTaskTimer(Lithium.getInstance(), 0, (long) (delaySeconds * 20));
    }
}
