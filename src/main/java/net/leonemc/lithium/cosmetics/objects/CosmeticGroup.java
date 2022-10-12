package net.leonemc.lithium.cosmetics.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CosmeticGroup {

    PURCHASE(null, null),

    BLUE("§9Blue", "rank.blue"),
    PINK("§dPink", "rank.pink"),
    SILVER("§7Silver", "rank.silver"),
    GOLD("§6Gold", "rank.gold"),
    PLATINUM("§3Platinum", "rank.platinum");

    private final String name;
    private final String permission;
}
