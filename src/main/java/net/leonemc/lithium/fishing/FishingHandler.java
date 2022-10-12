package net.leonemc.lithium.fishing;

import com.google.common.reflect.ClassPath;
import lombok.Getter;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.fishing.items.ClownfishReward;
import net.leonemc.lithium.fishing.items.FishReward;
import net.leonemc.lithium.fishing.items.SalmonReward;
import net.leonemc.lithium.utils.ChanceUtil;
import org.bukkit.entity.Fish;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Getter
public class FishingHandler {

    private List<FishingItem> loot = new ArrayList<>();

    public FishingHandler() {
        try {
            ClassPath.from(this.getClass().getClassLoader()).getAllClasses().stream()
                    .filter(info -> info.getPackageName().startsWith("net.leonemc.lithium.fishing.items"))
                    .forEach(info -> {
                        try {
                            loot.add((FishingItem) info.load().newInstance());
                        } catch (InstantiationException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Lithium.getInstance().getLogger().log(Level.INFO, "Loading fishing items...");
        loot.forEach(item -> Lithium.getInstance().getLogger().log(Level.INFO, "Loaded fishing item: " + item.name()));
        Lithium.getInstance().getLogger().log(Level.INFO, "Loaded all fishing items...");
    }

    public double getPrice(Rarity rarity) {
        switch (rarity) {
            case COMMON:
                return 20;
            case UNCOMMON:
                return 50;
            case RARE:
                return 115;
            case EPIC:
                return 225;
            case LEGENDARY:
                return 332;
            default:
                return 0;
        }
    }

    public FishingItem getRandomReward() {
        if (ChanceUtil.getChance(5)) {
            return getRandomReward(Rarity.LEGENDARY);
        } else if (ChanceUtil.getChance(15)) {
            return getRandomReward(Rarity.EPIC);
        } else if (ChanceUtil.getChance(25)) {
            return getRandomReward(Rarity.RARE);
        } else if (ChanceUtil.getChance(40)) {
            return getRandomReward(Rarity.UNCOMMON);
        } else {
            return getRandomReward(Rarity.COMMON);
        }
    }

    public FishingItem getRandomReward(Rarity category) {
        List<FishingItem> categoryLoot = new ArrayList<>();
        for (FishingItem item : loot) {
            if (item.rarity() == category) {
                categoryLoot.add(item);
            }
        }
        return categoryLoot.get((int) (Math.random() * categoryLoot.size()));
    }
}
