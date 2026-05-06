package com.pvpindex.api.model.player;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A player's ranking in a specific game mode.
 */
public final class PlayerRanking {

    @JsonProperty("game_mode")
    private String gameMode;

    @JsonProperty("rank")
    private int rank;

    @JsonProperty("elo")
    private int elo;

    @JsonProperty("peak_elo")
    private int peakElo;

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

    public PlayerRanking() {}

    public String getGameMode() { return gameMode; }
    public int getRank() { return rank; }
    public int getElo() { return elo; }
    public int getPeakElo() { return peakElo; }
    public String getDerivedRank() { return derivedRank; }
    public int getWins() { return wins; }
    public int getLosses() { return losses; }
    public int getDraws() { return draws; }
    public int getMatchesPlayed() { return matchesPlayed; }
    public double getWinRate() { return winRate; }
}
