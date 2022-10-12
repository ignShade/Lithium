package net.leonemc.lithium.chatgames.impl;

import lombok.Getter;
import lombok.Setter;
import net.leonemc.lithium.chatgames.object.ChatGame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
@Setter
public class ScrambleGame extends ChatGame {

    private List<String> words;

    private String word;

    public ScrambleGame(String name, String description, int duration, List<String> words) {
        super(name, description, duration);

        this.words = words;
    }

    @Override
    public void start() {
        String chosenWord = words.get((int) (Math.random() * words.size()));
        char[] wordArray = chosenWord.toCharArray();

        for (int i = 0; i < wordArray.length; i++) {
            int randomIndex = (int) (Math.random() * wordArray.length);
            char temp = wordArray[i];
            wordArray[i] = wordArray[randomIndex];
            wordArray[randomIndex] = temp;
        }

        word = chosenWord;
        Bukkit.broadcastMessage("§6§lChat Games §8» §fFirst person to unscramble the word §e" + new String(wordArray) + " §fwins.");
    }

    @Override
    public void stop(Player winner) {
        if (winner == null) {
            Bukkit.broadcastMessage("§6§lChat Games §8» §fNobody guessed the word in time. " + "The word was §b" + word + "§f.");
        } else {
            Bukkit.broadcastMessage("§6§lChat Games §8» §e" + winner.getName() + " §fguessed the word in time. The word was §b" + word + "§f.");
        }
    }

    @Override
    public boolean handleMessage(Player sender, String message) {
        return message.equalsIgnoreCase(word);
    }
}
