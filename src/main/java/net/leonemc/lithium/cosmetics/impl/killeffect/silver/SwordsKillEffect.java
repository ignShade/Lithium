package net.leonemc.lithium.cosmetics.impl.killeffect.silver;

import net.leonemc.api.items.ItemBuilder;
import net.leonemc.api.items.XMaterial;
import net.leonemc.api.util.bukkit.Tasks;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.cosmetics.annotation.CosmeticInfo;
import net.leonemc.lithium.cosmetics.impl.CosmeticKillEffect;
import net.leonemc.lithium.cosmetics.objects.CosmeticGroup;
import net.leonemc.lithium.cosmetics.objects.CosmeticRarity;
import net.leonemc.lithium.cosmetics.objects.CosmeticType;
import net.leonemc.lithium.utils.Task;
import net.leonemc.lithium.utils.bukkit.RepeatingTaskHelper;
import net.leonemc.lithium.utils.entity.ArmorStandBuilder;
import net.leonemc.lithium.utils.entity.nms.MobUtils;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@CosmeticInfo(
        name = "Swords",
        description = "Drops swords on the player you killed",
        displayItem = XMaterial.IRON_SWORD,
        cosmeticType = CosmeticType.KILL_EFFECT,
        permission = "lithium.killeffect.swords",
        rarity = CosmeticRarity.LEGENDARY,
        group = CosmeticGroup.SILVER
)
public class SwordsKillEffect extends CosmeticKillEffect {

    private ArmorStandBuilder ab;
    Random random = new Random();

    @Override
    public void onPlayerDeath(Player player) {
    }


    private List<ArmorStand> spawnStands(Location[] locations, Location targetLocation) {
        ArrayList<ArmorStand> stands = new ArrayList<>();
        World world = targetLocation.getWorld();
        double x = targetLocation.getX();
        double y = targetLocation.getY() + 1.0;
        double z = targetLocation.getZ();
        RepeatingTaskHelper taskHelper = new RepeatingTaskHelper();
        taskHelper.setTaskID(Task.scheduleSyncRepeatingTask(() -> {
            if (taskHelper.getCounter() >= 19) {
                taskHelper.cancel();
            } else {
                ArmorStand stand = this.ab.withLocation(locations[taskHelper.getCounter()]).spawn();
                stand.setRightArmPose(new EulerAngle(1.38, random.nextDouble() * 2.0, 0.0));
                stand.setVelocity(new Vector(0, -3, 0));

                stands.add(stand);
            }
            taskHelper.increment();
            if (stands.size() > 4) {
                stands.get(0).remove();
                stands.remove(0);
                if (taskHelper.getCounter() % 4 == 0) {
                    world.playEffect(new Location(world, x + random.nextDouble() - 0.5, y, z + random.nextDouble() - 0.5), Effect.STEP_SOUND, 152);
                }
            }
        }, 0L, 3L));
        return stands;
    }

    private Location[] getRandomLocations(Location targetLocation) {
        Location[] locations = new Location[20];
        Random random = new Random();
        World world = targetLocation.getWorld();
        double x = targetLocation.getX();
        double y = targetLocation.getY() + 10.0;
        double z = targetLocation.getZ();
        for (int i = 0; i < 20; ++i) {
            locations[i] = new Location(world, x + (random.nextDouble() * 2.0 - 1.0), y, z + (random.nextDouble() * 2.0 - 1.0));
        }
        return locations;
    }
}
