package net.leonemc.lithium.cosmetics.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;

@AllArgsConstructor
@Getter
public enum CosmeticRarity {

    COMMON(ChatColor.GRAY, "Common"),
    UNCOMMON(ChatColor.GREEN, "Uncommon"),
    RARE(ChatColor.BLUE, "Rare"),
    EPIC(ChatColor.DARK_PURPLE, "Epic"),
    LEGENDARY(ChatColor.GOLD, "Legendary");

    private final ChatColor color;
    private final String name;
}
