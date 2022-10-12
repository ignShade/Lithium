package net.leonemc.lithium.showcase;

import lombok.Getter;
import net.leonemc.api.items.XMaterial;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.utils.bukkit.ItemBuilder;
import net.leonemc.lithium.utils.menu.Button;
import net.leonemc.lithium.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter
public class ShowcaseHandler {

    private final HashMap<UUID, PreviewableInventory> inventories = new HashMap<>();

    public ShowcaseHandler() {
        new ShowcaseRunnable().runTaskTimerAsynchronously(Lithium.getInstance(), 0, 20);
    }

    public void addInventory(UUID uuid, PreviewableInventory inventory) {
        inventories.put(uuid, inventory);
    }

    public void open(Player player, UUID uuid) {
        PreviewableInventory inventory = inventories.get(uuid);

        if (inventory == null) {
            player.sendMessage("§cThis showcase has expired.");
            return;
        }

        new Menu() {

            @Override
            public String getTitle(Player player) {
                return "§e" + inventory.getOwner() + "'s Inventory";
            }

            @Override
            public Map<Integer, Button> getButtons(Player player) {
                HashMap<Integer, Button> buttons = new HashMap<>();
                ArrayList<ItemStack> items = new ArrayList<>(Arrays.asList(inventory.getContents()));

                items.forEach(item -> {
                    if (item == null || item.getType() == Material.AIR) {
                        buttons.put(buttons.size(), new Button() {
                            @Override
                            public ItemStack getButtonItem(Player player) {
                                return new ItemStack(Material.AIR, 1);
                            }
                        });
                    } else {
                        buttons.put(buttons.size(), new Button() {
                            @Override
                            public ItemStack getButtonItem(Player player) {
                                return new ItemStack(item);
                            }
                        });
                    }
                });

                // bar to seperate armor
                for (int i = 36; i < 45; i++) {
                    buttons.put(i, new Button() {
                        @Override
                        public ItemStack getButtonItem(Player player) {
                            return new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setName(" ").toItemStack();
                        }
                    });
                }

                // armor
                for (int i = 0; i < 4; i++) {
                    ItemStack item = inventory.getArmorContents()[i];
                    if (item == null || item.getType() == Material.AIR) {
                        buttons.put(45 + i, new Button() {
                            @Override
                            public ItemStack getButtonItem(Player player) {
                                return new ItemStack(Material.AIR, 1);
                            }
                        });
                    } else {
                        buttons.put(45 + i, new Button() {
                            @Override
                            public ItemStack getButtonItem(Player player) {
                                return new ItemStack(item);
                            }
                        });
                    }
                }

                return buttons;
            }

            @Override
            public int size(Player player) {
                return 54;
            }

        }.openMenu(player);
    }
}
