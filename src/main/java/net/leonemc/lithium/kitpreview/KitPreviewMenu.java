package net.leonemc.lithium.kitpreview;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Kit;
import com.earth2me.essentials.MetaItemStack;
import com.earth2me.essentials.libs.snakeyaml.external.biz.base64Coder.Base64Coder;
import lombok.AllArgsConstructor;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.utils.menu.Button;
import net.leonemc.lithium.utils.menu.pagination.PaginatedMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@AllArgsConstructor
public class KitPreviewMenu extends PaginatedMenu {

    private Kit kit;

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Kit " + kit.getName();
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();

        List<ItemStack> items = new ArrayList<>();
        Essentials ess = (Essentials) Lithium.getInstance().getServer().getPluginManager().getPlugin("Essentials");

        // parses the kit items
        // why is this so complicated
        try {
            for (String kitItem : kit.getItems()) {
                ItemStack stack;
                if (kitItem.startsWith("@")) {
                    if (ess.getSerializationProvider() == null) {
                        continue;
                    }
                    stack = ess.getSerializationProvider().deserializeItem(Base64Coder.decodeLines(kitItem.substring(1)));
                } else {
                    final String[] parts = kitItem.split(" +");
                    final ItemStack parseStack = ess.getItemDb().get(parts[0], parts.length > 1 ? Integer.parseInt(parts[1]) : 1);

                    if (parseStack.getType() == Material.AIR) {
                        continue;
                    }

                    final MetaItemStack metaStack = new MetaItemStack(parseStack);

                    if (parts.length > 2) {
                        // We pass a null sender here because kits should not do perm checks
                        metaStack.parseStringMeta(null, true, parts, 2, ess);
                    }

                    stack = metaStack.getItemStack();
                }

                items.add(stack);
            }
        } catch (Exception e) {
            return buttons;
        }

        try {
            items.forEach(itemStack -> buttons.put(buttons.size(), new KitPreviewButton(itemStack)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buttons;
    }

    @AllArgsConstructor
    private static class KitPreviewButton extends Button {

        private ItemStack itemStack;

        @Override
        public ItemStack getButtonItem(Player player) {
            return itemStack;
        }
    }
}
