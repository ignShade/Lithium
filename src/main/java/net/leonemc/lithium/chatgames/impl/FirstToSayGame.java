package net.leonemc.lithium.chatgames.impl;

import lombok.Getter;
import lombok.Setter;
import net.leonemc.lithium.chatgames.object.ChatGame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

@Getter @Setter
public class FirstToSayGame extends ChatGame {

    private List<String> words;

    private String word;

    public FirstToSayGame(String name, String description, int duration, List<String> words) {
        super(name, description, duration);

        this.words = words;
    }

    @Override
    public void start() {
        word = words.get((int) (Math.random() * words.size()));

        Bukkit.broadcastMessage("§6§lChat Games §8» §fFirst person to say the word §e" + word + " §fwins.");
    }

    @Override
    public void stop(Player winner) {
        if (winner == null) {
            Bukkit.broadcastMessage("§6§lChat Games §8» §fNobody said the word in time. "  + "The word was §b" + word + "§f.");
        } else {
            Bukkit.broadcastMessage("§6§lChat Games §8» §e" + winner.getName() + " §fhas said the word. The word was §b" + word + "§f.");
        }
    }

    @Override
    public boolean handleMessage(Player sender, String message) {
        return message.equalsIgnoreCase(word);
    }
}
