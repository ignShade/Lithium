package net.leonemc.lithium.commands;

import net.leonemc.lithium.utils.bukkit.CC;
import net.leonemc.lithium.utils.command.Command;
import net.leonemc.lithium.utils.command.CommandArgs;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class AdvertiseCommand {

    private final List<String> adMessages = Arrays.asList(
            "/ad LeonePvP &b1.8 PvP &r| &dAmazing Anticheat &r| &bKitPvP and More &r| &dJoin Now!"
    );

    @Command(name = "advertise", aliases = {"ad"})
    public void advertise(CommandArgs cmd) {
        Player p = cmd.getPlayer();

        p.sendMessage(CC.CHAT_BAR);
        p.sendMessage("§e§lAdvertise §7- §fList of ad commands");
        p.sendMessage("§7(Click to copy to clipboard)");
        p.sendMessage(" ");

        adMessages.forEach(msg -> {
            TextComponent chatMessage = new TextComponent();
            chatMessage.setText(ChatColor.translateAlternateColorCodes('&', msg));
            chatMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§eClick to copy message")));
            chatMessage.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, msg));

            p.spigot().sendMessage(chatMessage);
        });

        p.sendMessage(CC.CHAT_BAR);
    }

}
