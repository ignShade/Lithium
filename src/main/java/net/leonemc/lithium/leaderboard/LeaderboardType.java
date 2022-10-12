package net.leonemc.lithium.leaderboard;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LeaderboardType {

    KILLS("Kills"),
    DEATHS("Deaths"),
    KILLSTREAK("Killstreak");


    private final String name;

}
