package net.leonemc.lithium.leaderboard;


import java.util.HashMap;
import java.util.Map;

public class CachedLeaderboard {

    public Map<String, Integer> killLeaderboard = new HashMap<>();
    public Map<String, Integer> coinLeaderboard = new HashMap<>();
    public Map<String, Integer> deathLeaderboard = new HashMap<>();
    public Map<String, Integer> killstreakLeaderboard = new HashMap<>();
    public Map<String, Integer> levelLeaderboard = new HashMap<>();

    public Map<String, Integer> getCoinLeaderboard() {
        return coinLeaderboard;
    }

    public Map<String, Integer> getDeathLeaderboard() {
        return deathLeaderboard;
    }

    public Map<String, Integer> getKillLeaderboard() {
        return killLeaderboard;
    }

    public Map<String, Integer> getKillstreakLeaderboard() {
        return killstreakLeaderboard;
    }

    public Map<String, Integer> getLevelLeaderboard() {
        return levelLeaderboard;
    }

    public void setCoinLeaderboard(Map<String, Integer> coinLeaderboard) {
        this.coinLeaderboard.clear();
        this.coinLeaderboard = coinLeaderboard;
    }

    public void setDeathLeaderboard(Map<String, Integer> deathLeaderboard) {
        this.deathLeaderboard.clear();
        this.deathLeaderboard = deathLeaderboard;
    }

    public void setKillLeaderboard(Map<String, Integer> killLeaderboard) {
        this.killLeaderboard.clear();
        this.killLeaderboard = killLeaderboard;
    }

    public void setKillstreakLeaderboard(Map<String, Integer> killstreakLeaderboard) {
        this.killstreakLeaderboard.clear();
        this.killstreakLeaderboard = killstreakLeaderboard;
    }

    public void setLevelLeaderboard(Map<String, Integer> levelLeaderboard) {
        this.levelLeaderboard.clear();
        this.levelLeaderboard = levelLeaderboard;
    }
}