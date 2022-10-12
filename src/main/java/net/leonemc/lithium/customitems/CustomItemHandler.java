package net.leonemc.lithium.customitems;

import com.google.common.reflect.ClassPath;
import lombok.Getter;
import net.leonemc.lithium.utils.bukkit.ItemUtil;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

@Getter
public class CustomItemHandler {

    private final ArrayList<CustomItem> items = new ArrayList<>();

    public CustomItemHandler() {
        try {
            ClassPath.from(this.getClass().getClassLoader()).getAllClasses().stream()
                    .filter(info -> info.getPackageName().startsWith("net.leonemc.lithium.customitems.impl"))
                    .forEach(info -> {
                        if (!info.getName().contains("$")) {
                            try {
                                registerItem((CustomItem) info.load().newInstance());
                            } catch (InstantiationException | IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerItem(CustomItem item) {
        items.add(item);
    }

    public boolean isCustomItem(ItemStack is) {
        return ItemUtil.hasNBTTag(is, "ability");
    }

    public CustomItem getCustomItem(ItemStack is) {
        return items.stream().filter(item -> item.getName().equals(ItemUtil.getNBTTag(is, "ability"))).findFirst().orElse(null);
    }

    public CustomItem getCustomItem(String name) {
        return items.stream().filter(item -> item.getName().equals(name)).findFirst().orElse(null);
    }

}
