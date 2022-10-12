package net.leonemc.lithium.utils.menu.pagination;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import net.leonemc.lithium.utils.bukkit.CC;
import net.leonemc.lithium.utils.menu.Button;
import net.leonemc.lithium.utils.menu.Menu;
import org.bukkit.entity.Player;

public abstract class PaginatedMenu extends Menu {

    @Getter
    private int page = 1;
    {
        setUpdateAfterClick(false);
    }

    @Override
    public String getTitle(Player player) {
        if (showPages(player)) {
            return CC.translate(getPrePaginatedTitle(player) + " &7(Page " + page + "/" + getPages(player) + ")");
        } else {
            return CC.translate(getPrePaginatedTitle(player));
        }
    }

    /**
     * Changes the page number
     *
     * @param player player viewing the inventory
     * @param mod    delta to modify the page number by
     */
    public final void modPage(Player player, int mod) {
        page += mod;
        getButtons().clear();
        openMenu(player);
    }

    @Override
    public int size(Player player) {
        return 36;
    }

    /**
     * @param player player viewing the inventory
     */
    public final int getPages(Player player) {
        int buttonAmount = getAllPagesButtons(player).size();

        if (buttonAmount == 0) {
            return 1;
        }

        return (int) Math.ceil(buttonAmount / (double) getMaxItemsPerPage(player));
    }

    @Override
    public final Map<Integer, Button> getButtons(Player player) {
        int minIndex = (int) ((double) (page - 1) * getMaxItemsPerPage(player));
        int maxIndex = (int) ((double) (page) * getMaxItemsPerPage(player));

        HashMap<Integer, Button> buttons = new HashMap<>();

        buttons.put(0, new PageButton(-1, this));
        buttons.put(8, new PageButton(1, this));

        try {
            for (Map.Entry<Integer, Button> entry : getAllPagesButtons(player).entrySet()) {
                int ind = entry.getKey();

                if (ind >= minIndex && ind < maxIndex) {
                    ind -= (int) ((double) (getMaxItemsPerPage(player)) * (page - 1)) - 9;
                    buttons.put(ind, entry.getValue());
                }
            }
        } catch (Exception ignored) {
            player.closeInventory();
            openMenu(player);
        }

        Map<Integer, Button> global = getGlobalButtons(player);

        if (global != null) {
            for (Map.Entry<Integer, Button> gent : global.entrySet()) {
                buttons.put(gent.getKey(), gent.getValue());
            }
        }

        return buttons;
    }

    public int getMaxItemsPerPage(Player player) {
        return 27;
    }

    /**
     * @param player player viewing the inventory
     * @return a Map of button that returns items which will be present on all pages
     */
    public Map<Integer, Button> getGlobalButtons(Player player) {
        return null;
    }

    /**
     * @param player player viewing the inventory
     * @return title of the inventory before the page number is added
     */
    public abstract String getPrePaginatedTitle(Player player);


    public boolean showPages(Player player) {
        return false;
    }

    /**
     * @param player player viewing the inventory
     * @return a map of button that will be paginated and spread across pages
     */
    public abstract Map<Integer, Button> getAllPagesButtons(Player player);

    @Override
    public boolean isAutoUpdate() {
        return true;
    }
}