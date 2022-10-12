package net.leonemc.lithium.utils;

import java.util.Random;

public class ChanceUtil {

    public static boolean getChance(int minimalChance) {
        Random random = new Random();
        return random.nextInt(99) + 1 >= minimalChance;
    }

}
