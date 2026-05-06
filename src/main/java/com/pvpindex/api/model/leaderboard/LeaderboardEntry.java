package com.pvpindex.api.model.leaderboard;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A single entry on a leaderboard.
 */
public final class LeaderboardEntry {

    @JsonProperty("id")
    private int id;

    @JsonProperty("rank")
    private int rank;

    @JsonProperty("player_id")
    private int playerId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("game_mode")
    private String gameMode;

    @JsonProperty("current_elo")
    private double currentElo;

    @JsonProperty("derived_rank")
    private String derivedRank;

    @JsonProperty("wins")
    private int wins;

    @JsonProperty("losses")
    private int losses;

    @JsonProperty("draws")
    private int draws;

    @JsonProperty("matches_played")
    private int matchesPlayed;

    @JsonProperty("win_rate")
    private double winRate;

    @JsonProperty("region")
    private String region;

    public LeaderboardEntry() {}

    public int getId() { return id; }
    public int getRank() { return rank; }
    public int getPlayerId() { return playerId; }
    public String getUsername() { return username; }
    public String getDisplayName() { return displayName; }
    public String getAvatarUrl() { return avatarUrl; }
    public String getGameMode() { return gameMode; }
    public double getCurrentElo() { return currentElo; }
    public String getDerivedRank() { return derivedRank; }
    public int getWins() { return wins; }
    public int getLosses() { return losses; }
    public int getDraws() { return draws; }
    public int getMatchesPlayed() { return matchesPlayed; }
    public double getWinRate() { return winRate; }
    public String getRegion() { return region; }
}
