package net.leonemc.lithium.utils;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class LevelColorUtil {

    public static String getLevelColored(int level, int prestige) {
        if (level < 10)
            return ChatColor.GRAY + "[" + level + "\u272B]";
        else if (level < 20)
            return ChatColor.WHITE + "[" + level + "\u272B]";
        else if (level < 30)
            return ChatColor.YELLOW + "[" + level + "\u272B]";
        else if (level < 40)
            return ChatColor.GOLD + "[" + level + "\u272B]";
        else if (level < 50)
            return ChatColor.AQUA + "[" + level + "\u272B]";
        else if (level < 60)
            return ChatColor.DARK_AQUA + "[" + level + "\u272B]";
        else if (level < 70)
            return ChatColor.GREEN + "[" + level + "\u272B]";
        else if (level < 80)
            return ChatColor.DARK_GREEN + "[" + level + "\u272B]";
        else if (level < 90)
            return ChatColor.RED + "[" + level + "\u272B]";
        else if (level < 100)
            return ChatColor.DARK_RED + "[" + level + "\u272B]";
        else if (level < 110)
            return ChatColor.LIGHT_PURPLE + "[" + level + "\u272B]";
        else if (level < 120)
            return ChatColor.BLUE + "[" + level + "\u272B]";
        else if (level < 130)
            return ChatColor.DARK_PURPLE + "[" + level + "\u272B]";
        else if (level < 140)
            return ChatColor.DARK_BLUE + "[" + level + "\u272B]";
        else if (level < 150)
            return ChatColor.DARK_GRAY + "[" + level + "\u272B]";
        else if (level < 160)
            return generateLightRainbow("[" + level + "\u272B]");
        else if (level < 170)
            return createRepeatingText("[" + level + "\u272B]", "§8", "§7", "§f", "§f", "§7", "§8");
        else if (level < 180)
            return createRepeatingText("[" + level + "\u272B]", "§5", "§d", "§d", "§6", "§e", "§e");
        else if (level < 190)
            return createRepeatingText("[" + level + "\u272B]", "§b", "§f", "§f", "§7", "§7", "§8");
        else if (level < 200)
            return createRepeatingText("[" + level + "\u272B]", "§4", "§4", "§c", "§d", "§d", "§5");
        else if (level < 210)
            return createRepeatingText("[" + level + "\u272B]", "§a", "§a", "§a", "§a", "§a", "§a");
        else if (level < 220)
            return createRepeatingText("[" + level + "\u272B]", "§f", "§f", "§a", "§a", "§2", "§2");
        else if (level < 230)
            return createRepeatingText("[" + level + "\u272B]", "§e", "§e", "§f", "§f", "§7", "§8");
        else if (level < 240)
            return createRepeatingText("[" + level + "\u272B]", "§b", "§3", "§3", "§9", "§9", "§9");
        else if (level < 250)
            return createRepeatingText("[" + level + "\u272B]", "§f", "§e", "§e", "§e", "§6", "§6");

        else if (level > 250)
            return createRepeatingText("[" + level + "\u272B]", "§e", "§6", "§6", "§c", "§c", "§4");
        return ChatColor.GRAY + "[" + level + "\u272B]";
    }

    public static String generateLightRainbow(String text) {
        String[] colors = new String[] {
                ChatColor.RED.toString(),
                ChatColor.GOLD.toString(),
                ChatColor.YELLOW.toString(),
                ChatColor.GREEN.toString(),
                ChatColor.AQUA.toString(),
                ChatColor.LIGHT_PURPLE.toString(),
                ChatColor.BLUE.toString(),
        };
        String[] letter = text.split("");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < letter.length; i++) {
            sb.append(colors[i % colors.length]);
            sb.append(letter[i]);
        }
        return sb.toString();
    }

    public static String createText(String text, ChatColor... colors) {
        char[] s = text.toCharArray();

        for (int i = 0; i < colors.length; i++) {
            s[i] = colors[i].toString().charAt(i);
        }

        return String.valueOf(s);
    }

    public static String createText(String text, String... colors) {
        char[] s = text.toCharArray();

        for (int i = 0; i < colors.length; i++) {
            s[i] = colors[i].toString().charAt(i);
        }

        return String.valueOf(s);
    }

    public static String createRepeatingText(String text, ChatColor... colors) {
        String[] s = text.split("");

        for (int i = 0; i < s.length; i++) {
            s[i] = colors[i % colors.length].toString() + s[i];
        }

        return Arrays.toString(s);
    }

    public static String createRepeatingText(String text, String... colors) {
        String[] s = text.split("");

        for (int i = 0; i < s.length; i++) {
            s[i] = colors[i % colors.length].toString() + s[i];
        }

        return Strings.join(s, "");
    }

}