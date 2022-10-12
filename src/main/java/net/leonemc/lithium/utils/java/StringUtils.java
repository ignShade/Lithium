package net.leonemc.lithium.utils.java;

public class StringUtils {

    public static String capitalizeWord(String str) {
        String[] words = str.split("\\s");
        StringBuilder capitalizeWord = new StringBuilder();
        for (String w : words) {
            String first = w.substring(0, 1);
            String afterfirst = w.substring(1);
            capitalizeWord.append(first.toUpperCase()).append(afterfirst).append(" ");
        }
        return capitalizeWord.toString().trim();
    }

    public static String enumToName(String name) {
        return capitalizeWord(name.replaceAll("_", " "));
    }

    public static String generateInvisible() {
        StringBuilder sb = new StringBuilder();
        int random = (int) (Math.random() * 30);
        for (int i = 0; i < random; i++) {
            String[] colors = new String[]{"§0", "§1", "§2", "§3", "§4", "§5", "§6", "§7", "§8", "§9", "§a", "§b", "§c", "§d", "§e", "§f"};
            sb.append(colors[(int) (Math.random() * colors.length)]);
        }

        return sb.toString();
    }

    public static String intToRoman(int number) {
        if (number<=0) return "";
        if (number-1000>=0) return "M" + intToRoman(number-1000);
        if (number-900>=0) return "CM" + intToRoman(number-900);
        if (number-500>=0) return "D" + intToRoman(number-500);
        if (number-400>=0) return "CD" + intToRoman(number-400);
        if (number-100>=0) return "C" + intToRoman(number-100);
        if (number-90>=0) return "XC" + intToRoman(number-90);
        if (number-50>=0) return "L" + intToRoman(number-50);
        if (number-40>=0) return "XL" + intToRoman(number-40);
        if (number-10>=0) return "X" + intToRoman(number-10);
        if (number-9>=0) return "IX" + intToRoman(number-9);
        if (number-5>=0) return "V" + intToRoman(number-5);
        if (number-4>=0) return "IV" + intToRoman(number-4);
        if (number-1>=0) return "I" + intToRoman(number-1);
        return null;
    }
}
