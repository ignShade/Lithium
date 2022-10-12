package net.leonemc.lithium.tags.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.storage.Yaml;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Tag {

    private UUID uuid;
    private String name;
    private String prefix;

    public void save() {
        Yaml yaml = Lithium.getInstance().getData().getTagYaml(this.getUuid().toString());
        yaml.set("uuid", this.getUuid().toString());
        yaml.set("name", this.getName());
        yaml.set("prefix", this.getPrefix());
        yaml.save();
    }

}
