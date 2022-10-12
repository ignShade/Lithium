package net.leonemc.lithium.cosmetics;

import lombok.Getter;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.cosmetics.impl.CosmeticKillEffect;
import net.leonemc.lithium.cosmetics.impl.killeffect.blue.CorpseKillEffect;
import net.leonemc.lithium.cosmetics.impl.killeffect.buyable.*;
import net.leonemc.lithium.cosmetics.impl.killeffect.gold.BloodHelixKillEffect;
import net.leonemc.lithium.cosmetics.impl.killeffect.pink.PinataKillEffect;
import net.leonemc.lithium.cosmetics.impl.killeffect.pink.ShatterKillEffect;
import net.leonemc.lithium.cosmetics.impl.killeffect.silver.HeadRocketKillEffect;
import net.leonemc.lithium.cosmetics.objects.Cosmetic;
import net.leonemc.lithium.cosmetics.objects.CosmeticGroup;
import net.leonemc.lithium.cosmetics.objects.CosmeticType;
import net.leonemc.lithium.profiles.Profile;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Getter
public class CosmeticHandler {

    private ArrayList<Cosmetic> cosmetics = new ArrayList<>();

    public CosmeticHandler() {

        cosmetics.add(new FireworkKillEffect());
        cosmetics.add(new FireworkExplosionKillEffect());
        cosmetics.add(new DestroyedKillEffect());
        cosmetics.add(new CorpseKillEffect());
        cosmetics.add(new LightningKillEffect());
        cosmetics.add(new HeadRocketKillEffect());
        cosmetics.add(new ShatterKillEffect());
        cosmetics.add(new CookiesKillEffect());
        cosmetics.add(new PinataKillEffect());
        cosmetics.add(new BloodHelixKillEffect());

        cosmetics = sort(cosmetics);

        Lithium.getInstance().getLogger().log(Level.INFO, "Loaded " + cosmetics.size() + " cosmetics");
    }

    private ArrayList<Cosmetic> sort(ArrayList<Cosmetic> cosmetics) {
        HashMap<CosmeticGroup, List<Cosmetic>> groupedCosmetics = new HashMap<>();

        for (CosmeticGroup group : CosmeticGroup.values()) {
            groupedCosmetics.put(group, cosmetics.stream().filter(cosmetic -> cosmetic.getGroup() == group).collect(Collectors.toList()));
        }

        //sorts the groups based on rarity, then price
        for (CosmeticGroup group : CosmeticGroup.values()) {
            groupedCosmetics.get(group).sort(Comparator.comparing(Cosmetic::getRarity).thenComparing(Cosmetic::getPrice));
        }

        ArrayList<Cosmetic> finalSortedCosmetics = new ArrayList<>();

        for (CosmeticGroup group : CosmeticGroup.values()) {
            List<Cosmetic> groupCosmetics = groupedCosmetics.get(group);
            finalSortedCosmetics.addAll(groupCosmetics);
        }

        return finalSortedCosmetics;
    }

    public boolean ownsCosmetic(Player player, Cosmetic cosmetic) {
        if (cosmetic.getPermission().equals(""))
            return true;

        if (cosmetic.getGroup() != CosmeticGroup.PURCHASE && player.hasPermission(cosmetic.getGroup().getPermission()))
            return true;

        return player.hasPermission(cosmetic.getPermission());
    }

    public boolean buyCosmetic(Player player, Cosmetic cosmetic) {
        if (ownsCosmetic(player, cosmetic))
            return true;

        Economy economy = Lithium.getInstance().getEcon();

        if (!economy.has(player, cosmetic.getPrice())) {
            return false;
        }

        economy.withdrawPlayer(player, cosmetic.getPrice());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set " + cosmetic.getPermission() + " true");

        return true;
    }

    public void startEffect(Player player, Player killed) {
        Profile playerData = Lithium.getInstance().getProfileManager().getProfile(player);

        if (playerData.getKillEffect() == null)
            return;

        CosmeticKillEffect killEffect = (CosmeticKillEffect) playerData.getKillEffect();

        try {
            CosmeticKillEffect effect = killEffect.getClass().newInstance();

            effect.onPlayerDeath(killed);
            effect.onPlayerDeath(killed, player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Cosmetic> getCosmeticsByType(CosmeticType type) {
        return cosmetics.stream().filter(cosmetic -> cosmetic.getCosmeticType() == type).collect(Collectors.toList());
    }

    public Cosmetic getCosmeticByName(String name) {
       return cosmetics.stream().filter(cosmetic -> cosmetic.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
