package net.leonemc.lithium.cosmetics.impl.killeffect.buyable;

import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.cosmetics.annotation.CosmeticInfo;
import net.leonemc.lithium.cosmetics.impl.CosmeticKillEffect;
import net.leonemc.lithium.cosmetics.objects.CosmeticGroup;
import net.leonemc.lithium.cosmetics.objects.CosmeticRarity;
import net.leonemc.lithium.cosmetics.objects.CosmeticType;
import net.minecraft.server.v1_8_R3.EntityLightning;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityWeather;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

@CosmeticInfo(
        name = "Lightning",
        description = "Strikes lightning on the player you killed.",
        displayItem = XMaterial.WHITE_WOOL,
        cosmeticType = CosmeticType.KILL_EFFECT,
        permission = "lithium.killeffect.lightning",
        rarity = CosmeticRarity.UNCOMMON,
        group = CosmeticGroup.PURCHASE,
        price = 1500
)
public class LightningKillEffect extends CosmeticKillEffect {

    @Override
    public void onPlayerDeath(Player player) {
        player.getWorld().playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 100, 1);

        //play lightning packet
        EntityLightning is = new EntityLightning(((CraftWorld) player.getWorld()).getHandle(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
        PacketPlayOutSpawnEntityWeather sp = new PacketPlayOutSpawnEntityWeather(is);

        for (Player p : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(sp);
        }
    }
}
