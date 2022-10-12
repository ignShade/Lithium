package net.leonemc.lithium.listener;

import net.leonemc.lithium.Lithium;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;

public class PotionListener implements Listener {

    /**
     * This removes the water bottle from their inventory after drinking a potion
     */
    @EventHandler
    public void onPotionDrink(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // automatically remove the empty bottle from their inventory
        // we have to loop through all items because whenever you drink a potion in a stack, the glass bottle gets put in another slot.
        if (item.getType().equals(Material.POTION)) {
            Bukkit.getServer().getScheduler().runTaskLater(Lithium.getInstance(), () -> {
                for (int i = 0; i < player.getInventory().getSize(); i++) {
                    ItemStack invItem = player.getInventory().getItem(i);
                    if (invItem != null && invItem.getType().equals(Material.GLASS_BOTTLE)) {
                        player.getInventory().setItem(i, null);
                        break;
                    }
                }
            }, 1L);
        }
    }

    /**
     * Used for picking up potions and making them stackable
     */
    @EventHandler
    public void onPotionPickup(PlayerPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        Player player = event.getPlayer();

        if (item.getType() != Material.POTION) {
            return;
        }

        int amount = item.getAmount();

        // Clones the potion and sets the amount to 1, so we can "split" the 1 itemstack into multiple items
        ItemStack modifiedStack = item.clone();
        modifiedStack.setAmount(1);

        // "Splits" the itemstack into multiple items
        // Probably the best way to do this
        for (int i = 0; i < amount; i++) {
            handlePotionPickup(event, modifiedStack, player);
        }
    }

    /**
     * Handles the potion pickup event (should be called after the itemstack has been "split" [only should be 1 item])
     *
     * @param event The event
     * @param item The itemstack
     * @param player The player
     */
    private void handlePotionPickup(PlayerPickupItemEvent event, ItemStack item, Player player) {
        // Ignore splash potions
        if (isSplash(item)) {
            return;
        }

        int slot = getNextPossibleStack(player, item);

        // Their inventory is full
        if (slot == -1)
            return;

        ItemStack stack = player.getInventory().getItem(slot);

        if (stack != null) { // found a stack that isn't full
            stack.setAmount(stack.getAmount() + item.getAmount());
        } else { // no stack found, add a new one
            player.getInventory().setItem(slot, item);
        }

        event.getItem().remove();
        event.setCancelled(true);
    }

    /**
     * Checks if the potion is a splash potion
     *
     * @param item the item to check
     * @return true if the potion is a splash potion
     */
    private boolean isSplash(ItemStack item) {
        if (item.getType() != Material.POTION) {
            return false;
        }

        Potion potion = Potion.fromItemStack(item);
        return potion.isSplash();
    }

    /**
     * Gets the next available slot in the player's inventory that can hold the item
     * If there's no slot found that can hold the item, then it finds the next empty slot
     * If there's no empty slot, then it returns -1
     *
     * @param player The player to check
     * @param item The item to find a slot for
     * @return the slot number or -1 if no slot found
     */
    private int getNextPossibleStack(Player player, ItemStack item) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack != null && stack.isSimilar(item) && stack.getAmount() < 64) {
                return i;
            }
        }
        return player.getInventory().firstEmpty();
    }
}
