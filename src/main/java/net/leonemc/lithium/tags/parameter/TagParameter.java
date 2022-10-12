package net.leonemc.lithium.tags.parameter;

import net.leonemc.api.command.data.parameter.ParameterType;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.tags.objects.Tag;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TagParameter implements ParameterType<Tag> {

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull Player player, @NotNull Set<String> set, @NotNull String s) {
        for (Tag item : Lithium.getInstance().getTagHandler().getTags()) {
            set.add(item.getName());
        }

        return Collections.emptyList();
    }

    @Nullable
    @Override
    public Tag transform(@NotNull CommandSender commandSender, @NotNull String s) {
        Tag tag = Lithium.getInstance().getTagHandler().getTagByName(s);

        if (tag == null) {
            commandSender.sendMessage("§cTag not found.");
            return null;
        }

        return tag;
    }
}

