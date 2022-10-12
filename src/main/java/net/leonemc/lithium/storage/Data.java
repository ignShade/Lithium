package net.leonemc.lithium.storage;

import net.leonemc.lithium.Lithium;
import org.bukkit.entity.Player;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Data {

    public Yaml getPlayerYaml(String uuid) {
        Lithium plugin = Lithium.getInstance();
        return new Yaml(plugin.getDataFolder().getAbsolutePath() + File.separator + "data" + File.separator + "players" + File.separator + uuid + ".yml");
    }

    public Yaml getPlayerYaml(Player player) {
        Lithium plugin = Lithium.getInstance();
        return new Yaml(plugin.getDataFolder().getAbsolutePath() + File.separator + "data" + File.separator + "players" + File.separator + player.getUniqueId() + ".yml");
    }

    public Yaml getTagYaml(String uuid) {
        Lithium plugin = Lithium.getInstance();
        return new Yaml(plugin.getDataFolder().getAbsolutePath() + File.separator + "data" + File.separator + "tags" + File.separator + uuid + ".yml");
    }

    public Yaml getOfflinePlayerYaml(String uuid) {
        Lithium plugin = Lithium.getInstance();
        return new Yaml(plugin.getDataFolder().getAbsolutePath() + File.separator + "data" + File.separator + "players" + File.separator + uuid + ".yml");
    }

    public List<Yaml> getAllPlayersYaml() {
        Lithium plugin = Lithium.getInstance();
        ArrayList<Yaml> results = new ArrayList<>();
        File[] files = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "data" + File.separator + "players" + File.separator).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                if (file.getName().equalsIgnoreCase("leaderboards.yml"))
                    continue;

                results.add(new Yaml(plugin.getDataFolder().getAbsolutePath() + File.separator + "data" + File.separator + "players" + File.separator + file.getName()));
            }
        }
        return results;
    }


    public List<Yaml> getAllTags() {
        Lithium plugin = Lithium.getInstance();
        ArrayList<Yaml> results = new ArrayList<>();
        File[] files = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "data" + File.separator + "tags" + File.separator).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                results.add(new Yaml(plugin.getDataFolder().getAbsolutePath() + File.separator + "data" + File.separator + "tags" + File.separator + file.getName()));
            }
        }
        return results;
    }

}