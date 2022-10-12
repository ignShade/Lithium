package net.leonemc.lithium.profiles;


import lombok.Data;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.cosmetics.objects.Cosmetic;
import net.leonemc.lithium.storage.Yaml;
import net.leonemc.lithium.tags.objects.Tag;
import org.bukkit.Location;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Data
public class Profile {

    private Lithium plugin = Lithium.getInstance();

    private String name;
    private UUID uuid;


    private int kills;
    private int deaths;
    private int killstreak;
    private int highestKillstreak;
    private int level;
    private int xp;

    private Cosmetic killEffect;
    private Tag tag;

    private boolean hasResetXP;

    private int disguisedKills;
    private int disguisedDeaths;
    private int disguisedKillstreak;
    private int disguisedLevel;

    private Location lastGroundLocation;

    public Profile(UUID uuid, String name) {
        this.name = name;
        this.uuid = uuid;
    }

    public void load() {
        Yaml yaml = plugin.getData().getPlayerYaml(uuid.toString());

        kills = yaml.get("kills") == null ? 0 : yaml.getInteger("kills");
        deaths = yaml.get("deaths") == null ? 0 : yaml.getInteger("deaths");
        killstreak = yaml.get("killstreak") == null ? 0 : yaml.getInteger("killstreak");
        highestKillstreak = yaml.get("highestKillstreak") == null ? 0 : yaml.getInteger("highestKillstreak");
        level = yaml.get("level") == null ? 0 : yaml.getInteger("level");
        xp = yaml.get("xp") == null ? 0 : yaml.getInteger("xp");
        hasResetXP = yaml.get("hasResetXP") != null && yaml.getBoolean("hasResetXP");
        killEffect = yaml.get("kill_effect") == null ? null : plugin.getCosmeticHandler().getCosmeticByName(yaml.getString("kill_effect"));
        tag = yaml.get("tag") == null ? null : plugin.getTagHandler().getTagByName(yaml.getString("tag"));

        yaml.save();
    }

    public void save() {
        Yaml yaml = plugin.getData().getPlayerYaml(uuid.toString());

        yaml.set("name", name);
        yaml.set("uuid", uuid.toString());

        yaml.set("kills", kills);
        yaml.set("deaths", deaths);
        yaml.set("killstreak", killstreak);
        yaml.set("highestKillstreak", highestKillstreak);
        yaml.set("level", level);
        yaml.set("xp", xp);
        yaml.set("hasResetXP", hasResetXP);

        yaml.set("kill_effect", killEffect == null ? null : killEffect.getName());
        yaml.set("tag", tag == null ? null : tag.getName());

        yaml.save();
    }

    public void saveAsync() {
        CompletableFuture.runAsync(this::save);
    }
}