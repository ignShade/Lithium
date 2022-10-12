package net.leonemc.lithium.reports.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@AllArgsConstructor
@Getter
public enum ReportCategory {

    COMBAT_HACKS("Combat Hacks", Material.IRON_SWORD),
    MOVEMENT_HACKS("Movement Hacks", Material.IRON_BOOTS),
    BUG_ABUSE("Bug Abuse", Material.IRON_HOE),
    CHAT_ABUSE("Chat Abuse", Material.BOOK_AND_QUILL),
    INAPPROPRIATE_SKIN("Inappropriate Skin", Material.SKULL_ITEM),
    BEGINNER_ARMOR("Using Diamond Armor in Beginner Arena", Material.DIAMOND_HELMET),
    OP_ARMOR("Using Iron Armor in OP Arena", Material.IRON_HELMET),
    TEAMING("Teaming (3 or more)", Material.LEASH),
    OTHER("Other", Material.PAPER);


    private final String name;
    private final Material displayItem;
}