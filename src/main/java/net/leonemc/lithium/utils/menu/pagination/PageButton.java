package net.leonemc.lithium.utils.menu.pagination;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import net.leonemc.lithium.utils.menu.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@AllArgsConstructor
public class PageButton extends Button {

    private int mod;
    private PaginatedMenu menu;

    @Override
    public ItemStack getButtonItem(Player player) {
        ItemStack itemStack = new ItemStack(Material.ARROW);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (this.hasNext(player)) {
            itemMeta.setDisplayName(this.mod > 0 ? ChatColor.GREEN + "Next page" : ChatColor.RED + "Previous page");
        } else {
            itemMeta.setDisplayName(ChatColor.YELLOW + (this.mod > 0 ? "Last page" : "First page"));
        }

        itemMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Click here to",
                ChatColor.GRAY + "jump to a page"
        ));

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (clickType == ClickType.RIGHT) {
            new ViewAllPagesMenu(this.menu).openMenu(player);
            playNeutral(player);
        } else {
            if (hasNext(player)) {
                this.menu.modPage(player, this.mod);
                Button.playNeutral(player);
            } else {
                Button.playFail(player);
            }
        }
    }

    private boolean hasNext(Player player) {
        int pg = this.menu.getPage() + this.mod;
        return pg > 0 && this.menu.getPages(player) >= pg;
    }

}