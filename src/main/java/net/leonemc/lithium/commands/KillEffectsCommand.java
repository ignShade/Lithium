package net.leonemc.lithium.commands;

import net.leonemc.api.command.Command;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.cosmetics.menu.CosmeticListMenu;
import net.leonemc.lithium.cosmetics.objects.CosmeticType;
import org.bukkit.entity.Player;

public class KillEffectsCommand {

    @Command(names = "killeffect gui")
    public static void execute(Player player) {
        new CosmeticListMenu(Lithium.getInstance().getCosmeticHandler().getCosmeticsByType(CosmeticType.KILL_EFFECT)).openMenu(player);
    }

    @Command(names = "killeffect test", permission = "*")
    public static void executeTest(Player player) {
        Lithium.getInstance().getCosmeticHandler().startEffect(player, player);
    }

}
