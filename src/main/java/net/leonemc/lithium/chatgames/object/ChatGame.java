package net.leonemc.lithium.chatgames.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Data
public abstract class ChatGame {

    private final String name;
    private final String description;
    private final int duration;

    public abstract void start();
    public abstract void stop(Player winner);
    public abstract boolean handleMessage(Player sender, String message);

}
