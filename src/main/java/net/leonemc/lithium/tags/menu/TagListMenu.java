package net.leonemc.lithium.tags.menu;

import lombok.AllArgsConstructor;
import net.leonemc.api.items.XMaterial;
import net.leonemc.api.util.bukkit.ItemBuilder;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.profiles.Profile;
import net.leonemc.lithium.tags.TagHandler;
import net.leonemc.lithium.tags.objects.Tag;
import net.leonemc.lithium.utils.menu.Button;
import net.leonemc.lithium.utils.menu.pagination.PaginatedMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TagListMenu extends PaginatedMenu {


    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Showing all tags";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();
        TagHandler tagHandler = Lithium.getInstance().getTagHandler();

        tagHandler.getTags().forEach(tag -> buttons.put(buttons.size(), new TagButton(tag)));

        return buttons;
    }

    @AllArgsConstructor
    private static class TagButton extends Button {

        Tag tag;

        @Override
        public ItemStack getButtonItem(Player player) {
            TagHandler tagHandler = Lithium.getInstance().getTagHandler();
            Profile profile = Lithium.getInstance().getProfileManager().getProfile(player.getUniqueId());

            return new ItemBuilder(Objects.requireNonNull(profile.getTag() != null && profile.getTag() == tag ? XMaterial.LIME_WOOL.parseItem() : XMaterial.RED_WOOL.parseItem()))
                    .name((tagHandler.ownsTag(tag, player) ? "§a" : "§c") + tag.getName())
                    .setLore(Arrays.asList(
                            "§eDisplays as: §f" + tag.getPrefix(),
                            " ",
                            "§eClick to select this tag."
                    ))
                    .build();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            TagHandler tagHandler = Lithium.getInstance().getTagHandler();
            Profile profile = Lithium.getInstance().getProfileManager().getProfile(player.getUniqueId());

            if (tagHandler.ownsTag(tag, player)) {
                profile.setTag(tag);
                player.sendMessage("§aYou have selected the tag §e" + tag.getName() + "§a.");
            } else {
                player.sendMessage("§cYou do not own this tag.");
            }
        }
    }
}
