package net.leonemc.lithium.fishing;

import net.leonemc.lithium.Lithium;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class FishingListener implements Listener {

    /*/@EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        PlayerFishEvent.State state = event.getState();

        if (state != PlayerFishEvent.State.CAUGHT_FISH) {
            return;
        }

        FishingItem randomItem = Lithium.getInstance().getFishingHandler().getRandomReward();
        Item fishedItem = (Item) event.getCaught();

        if (fishedItem != null) {
            fishedItem.setItemStack(randomItem.asItemStack(player));
        }
    }/*/
}
