package net.leonemc.lithium.rename;

import net.leonemc.api.player.APIPlayer;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.utils.Cooldown;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class RenameTask extends BukkitRunnable {

    int stage = 0;
    Player player;

    public RenameTask(Player player) {
        this.player = player;
    }


    @Override
    public void run() {
        RenameHandler renameHandler = Lithium.getInstance().getRenameHandler();
        ItemStack itemStack = player.getItemInHand();
        APIPlayer apiPlayer = new APIPlayer(player);

        if (itemStack == null) {
            apiPlayer.sendActionbar("§cYou must be holding an item!");
            cancel();
            renameHandler.refund(player);

            Cooldown.removeCooldown("renamer", player);
            return;
        }

        if (!(itemStack.getType().name().contains("AXE") || itemStack.getType().name().contains("SWORD"))) {
            apiPlayer.sendActionbar("§cYou must be holding an axe or sword!");
            cancel();
            renameHandler.refund(player);

            Cooldown.removeCooldown("renamer", player);
            return;
        }

        stage++;

        if (stage == 5) {
            renameHandler.rename(player);
            cancel();
        } else {
            renameHandler.sendDislike(player);
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
        }
    }
}
