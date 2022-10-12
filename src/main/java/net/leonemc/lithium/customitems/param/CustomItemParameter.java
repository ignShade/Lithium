package net.leonemc.lithium.customitems.param;

import net.leonemc.api.command.data.parameter.ParameterType;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.customitems.CustomItem;
import net.leonemc.lithium.customitems.CustomItemHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CustomItemParameter implements ParameterType<CustomItem> {

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull Player player, @NotNull Set<String> set, @NotNull String s) {
        for (CustomItem item : Lithium.getInstance().getCustomItemHandler().getItems()) {
            set.add(item.getName());
        }

        return Collections.emptyList();
    }

    @Nullable
    @Override
    public CustomItem transform(@NotNull CommandSender commandSender, @NotNull String s) {
        CustomItem item = Lithium.getInstance().getCustomItemHandler().getCustomItem(s);

        if (item == null) {
            commandSender.sendMessage("§cItem not found.");
            return null;
        }

        return item;
    }
}

