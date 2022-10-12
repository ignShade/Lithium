package net.leonemc.lithium.chatgames;

import lombok.Getter;
import net.leonemc.lithium.Lithium;
import net.leonemc.lithium.chatgames.impl.FirstToSayGame;
import net.leonemc.lithium.chatgames.impl.ScrambleGame;
import net.leonemc.lithium.chatgames.object.ChatGame;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@Getter
public class ChatGameHandler {

    private final ArrayList<ChatGame> chatGames = new ArrayList<>();
    private ChatGame currentGame = null;

    public ChatGameHandler() {
        setup();

        Bukkit.getScheduler().runTaskTimer(Lithium.getInstance(), this::startGame, 600 * 20, 600 * 20);
    }

    public void setup() {
        chatGames.add(new ScrambleGame("Scramble", "Unscramble the word.", 30, Lithium.getInstance().getConfig().getStringList("chat-games.scramble.words")));
        chatGames.add(new FirstToSayGame("FirstToSay", "Say the word.", 30, Lithium.getInstance().getConfig().getStringList("chat-games.first-to-say.words")));
    }

    public void reload() {
        chatGames.clear();
        setup();
    }

    public ChatGame getByName(String name) {
        return chatGames.stream().filter(chatGame -> chatGame.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void startGame() {
        if (currentGame != null) {
            return;
        }

        ChatGame game = chatGames.get((int) (Math.random() * chatGames.size()));
        currentGame = game;

        startChatGame(game);
    }

    public void startChatGame(ChatGame chatGame) {
        currentGame = chatGame;
        chatGame.start();

        Bukkit.getScheduler().runTaskLater(Lithium.getInstance(), () -> {
            if (currentGame == chatGame) {
                currentGame.stop(null);
            }
        }, chatGame.getDuration() * 20L);
    }

    public boolean isActive() {
        return currentGame != null;
    }

    public void end(Player winner) {
        currentGame.stop(winner);
        currentGame = null;

        Economy economy = Lithium.getInstance().getEcon();
        economy.depositPlayer(winner, 25);
    }
}
