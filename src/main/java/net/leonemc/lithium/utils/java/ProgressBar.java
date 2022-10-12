package net.leonemc.lithium.utils.java;

import com.google.common.base.Strings;
import org.bukkit.ChatColor;

/**
 * @author Kansio
 */
public class ProgressBar {

    public static String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
                                        ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        String bar;
        try {
            bar = Strings.repeat("" + completedColor + symbol, progressBars)
                    + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
        } catch (Exception e) {
            bar = Strings.repeat("" + completedColor + symbol, totalBars);
        }

        return bar;
    }

    public static int getPercentage(int amount) {
        int percent = amount * 100 / 100;
        double percentage = Math.round(percent * 10.0) / 10.0;

        return percentage > 100 ? 100 : (int) Math.round(percentage);
    }

    public static int getPercentageOfTotal(int amount, int max) {
        return amount * 100 / max;
    }

}