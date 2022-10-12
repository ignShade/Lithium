package net.leonemc.lithium.cosmetics.impl.killeffect.buyable;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.cosmetics.annotation.CosmeticInfo;
import net.leonemc.lithium.cosmetics.impl.CosmeticKillEffect;
import net.leonemc.lithium.cosmetics.objects.CosmeticGroup;
import net.leonemc.lithium.cosmetics.objects.CosmeticRarity;
import net.leonemc.lithium.cosmetics.objects.CosmeticType;
import net.leonemc.lithium.utils.entity.Hologram;
import net.minecraft.server.v1_8_R3.EntityLightning;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityWeather;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

@CosmeticInfo(
        name = "Destroyed (Hologram)",
        description = "Spawns a hologram above the player you killed.",
        displayItem = XMaterial.OAK_SIGN,
        cosmeticType = CosmeticType.KILL_EFFECT,
        permission = "lithium.killeffect.destroyed",
        rarity = CosmeticRarity.RARE,
        group = CosmeticGroup.PURCHASE,
        price = 3000
)
public class DestroyedKillEffect extends CosmeticKillEffect {

    @Override
    public void onPlayerDeath(Player player, Player killer) {
        Hologram hologram = new Hologram("§f" + killer.getName() + " §edestroyed §f" + player.getName() + "§e here!", player.getLocation());
        hologram.showAllTemp(5 * 20);
    }
}
