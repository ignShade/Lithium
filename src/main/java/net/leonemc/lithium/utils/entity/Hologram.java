package net.leonemc.lithium.utils.entity;

import net.leonemc.lithium.Lithium;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Hologram {

    private List<EntityArmorStand> entitylist = new ArrayList<EntityArmorStand>();
    private String text;
    private Location location;
    private double DISTANCE = 0.25D;
    int count;

    public Hologram(String text, Location location) {
        this.text = text;
        this.location = location;
        create();
    }

    public void showPlayerTemp(final Player p, int Time) {
        showPlayer(p);
        Bukkit.getScheduler().runTaskLater(Lithium.getInstance(), () -> hidePlayer(p), Time);
    }

    public void showAllTemp(int Time) {
        showAll();
        Bukkit.getScheduler().runTaskLater(Lithium.getInstance(), () -> hideAll(), Time);
    }

    public void showPlayer(Player p) {
        int i = 0;
        for (EntityArmorStand armor : entitylist) {
            armor.setCustomName(this.text);
            i++;
            PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(armor);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void hidePlayer(Player p) {
        for (EntityArmorStand armor : entitylist) {
            armor.setCustomName(armor.getCustomName());
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(armor.getId());
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void showAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            int i = 0;
            for (EntityArmorStand armor : entitylist) {
                armor.setCustomName(this.text);
                i++;
                PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(armor);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    public void hideAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (EntityArmorStand armor : entitylist) {
                armor.setCustomName(armor.getCustomName());
                PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(armor.getId());
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    private void create() {
        EntityArmorStand entity = new EntityArmorStand(((CraftWorld) this.location.getWorld()).getHandle(),
                this.location.getX(), this.location.getY(), this.location.getZ());
        entity.setCustomName(text);
        entity.setCustomNameVisible(true);
        entity.setInvisible(true);
        entity.setGravity(false);
        entitylist.add(entity);
        this.location.subtract(0, this.DISTANCE, 0);
        count++;

        for (int i = 0; i < count; i++) {
            this.location.add(0, this.DISTANCE, 0);
        }
        this.count = 0;
    }

    public void update() {
        showAll();
    }

    public String randomColor() {
        List<String> colors;
        colors = Arrays.asList("§1", "§2", "§3", "§4", "§5", "§6", "§7", "§8", "§9", "§a", "§b", "§c", "§d", "§e", "§f");
        String selected = colors.get(new Random().nextInt(colors.size() - 1));
        return selected;
    }

}