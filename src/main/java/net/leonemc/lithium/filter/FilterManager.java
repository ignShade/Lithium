package net.leonemc.lithium.filter;

import lombok.Getter;
import net.leonemc.lithium.Lithium;

import java.util.List;

public class FilterManager {

    @Getter
    private List<String> otherFiltered = Lithium.getInstance().getConfig().getStringList("filtered-words.normal");
    @Getter
    private List<String> mutableWords = Lithium.getInstance().getConfig().getStringList("filtered-words.mutable");

    public enum FilterCategory {
        OTHER,
        MUTABLE,
        REPLACE,
        NONE
    }

    public String filterMessage(String msg) {
        String message = msg;

        for (String other : otherFiltered) {
            if (message.toLowerCase().contains(other.toLowerCase())) {
                char[] characters = other.toCharArray(); // Do not put it in the loop below
                StringBuilder repeatedBadword = new StringBuilder();

                for (char character : characters)
                    repeatedBadword.append("*");
                message = message.replaceAll("(?i)" + other, repeatedBadword.toString());
            }
        }
        for (String mutableWord : mutableWords) {
            if (message.toLowerCase().contains(mutableWord.toLowerCase())) {
                char[] characters = mutableWord.toCharArray(); // Do not put it in the loop below
                StringBuilder repeatedBadword = new StringBuilder();

                for (char character : characters)
                    repeatedBadword.append("*");
                message = message.replaceAll("(?i)" + mutableWord, repeatedBadword.toString());
            }
        }

        return message;
    }

    public String getOffendingWord(String msg) {
        for (String mutableWord : mutableWords) {
            if (msg.toLowerCase().contains(mutableWord.toLowerCase())) {
                return mutableWord;
            }
        }

        return null;
    }

    public FilterCategory checkMessage(String message) {
        for (String other : otherFiltered) {
            if (message.toLowerCase().contains(other.toLowerCase())) {
                return FilterCategory.OTHER;
            }
        }
        for (String mutableWord : mutableWords) {
            if (message.toLowerCase().contains(mutableWord.toLowerCase())) {
                return FilterCategory.MUTABLE;
            }
        }
        return FilterCategory.NONE;
    }

}