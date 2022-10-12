package net.leonemc.lithium.tags;

import lombok.Getter;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.storage.Yaml;
import net.leonemc.lithium.tags.objects.Tag;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class TagHandler {

    private final ArrayList<Tag> tags = new ArrayList<>();

    public TagHandler() {
        List<Yaml> tags = Lithium.getInstance().getData().getAllTags();

        for (Yaml tag : tags) {
            this.tags.add(new Tag(UUID.fromString(tag.getString("uuid")), tag.getString("name"), tag.getString("prefix")));
        }
    }

    public void saveTags() {
        for (Tag tag : tags) {
            tag.save();
        }
    }

    public Tag getTagByName(String name) {
        return tags.stream().filter(tag -> tag.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Tag getTagByPrefix(String prefix) {
        return tags.stream().filter(tag -> tag.getPrefix().equalsIgnoreCase(prefix)).findFirst().orElse(null);
    }

    public Tag getTagByUUID(UUID uuid) {
        return tags.stream().filter(tag -> tag.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public void createTag(String name) {
        tags.add(new Tag(UUID.randomUUID(), name, ""));
    }

    public boolean ownsTag(Tag tag, Player player) {
        return player.hasPermission("tag." + tag.getName().toLowerCase());
    }

    public void deleteTag(Tag tag) {
        tags.remove(tag);

        Yaml yaml = Lithium.getInstance().getData().getTagYaml(tag.getUuid().toString());

        if (yaml != null) {
            yaml.delete();
        }
    }
}
