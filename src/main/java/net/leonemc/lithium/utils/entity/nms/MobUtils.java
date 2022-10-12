package net.leonemc.lithium.utils.entity.nms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MobUtils {

    private static Class<?> nmsEntityClass;
    private static Class<?> nbtTagCompoundClass;
    private static Class<?> craftEntityClass;
    private static Method getNBTTag;

    public MobUtils() {
        loadClasses();
    }

    private void loadClasses() {
        try {
            nmsEntityClass = getNMSClass("Entity");
            nbtTagCompoundClass = getNMSClass("NBTTagCompound");
            craftEntityClass = getCraftEntity();
            getNBTTag = nmsEntityClass.getMethod("getNBTTag", new Class[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Entity setSilent(Entity entity) {
        setTags(entity, "Silent");
        return entity;
    }


    public Entity setInvulnerable(Entity entity) {
        setTags(entity, "Invulnerable");
        return entity;
    }


    public static Entity setDefaultAttributes(Entity entity) {
        setTags(entity, "Silent", "Invulnerable");
        return entity;
    }


    public static Entity setTags(Entity entity, String ... tags) {
        try {
            Class<?> entityClass = craftEntityClass.cast(entity).getClass();
            Object nmsEntity = entityClass.getMethod("getHandle", new Class[0]).invoke(entity, new Object[0]);
            Object nbtTagCompound = getNBTTag.invoke(nmsEntity, new Object[0]);
            if (nbtTagCompound == null) {
                nbtTagCompound = nbtTagCompoundClass.newInstance();
            }
            Class<?> nbtTagCompoundClass = nbtTagCompound.getClass();
            nmsEntityClass.getMethod("c", nbtTagCompoundClass).invoke(nmsEntity, nbtTagCompound);
            Method setInt = nbtTagCompoundClass.getMethod("setInt", String.class, Integer.TYPE);
            for (String tag : tags) {
                setInt.invoke(nbtTagCompound, tag, 1);
            }
            nmsEntityClass.getMethod("f", nbtTagCompoundClass).invoke(nmsEntity, nbtTagCompound);
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return entity;
    }

    private String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(name.lastIndexOf(46) + 1) + ".";
    }

    private Class<?> getCraftEntity() throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        return Class.forName("org.bukkit.craftbukkit." + version + "entity.CraftEntity");
    }

    private Class<?> getNMSClass(String className) {
        String nmsClassLocation = "net.minecraft.server." + getVersion() + className;
        Class<?> nms = null;
        try {
            nms = Class.forName(nmsClassLocation);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return nms;
    }
}
