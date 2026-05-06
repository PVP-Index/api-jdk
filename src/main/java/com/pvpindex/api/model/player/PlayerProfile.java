package com.pvpindex.api.model.player;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Minecraft player profile.
 */
public final class PlayerProfile {

    @JsonProperty("id")
    private int id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("verified")
    private boolean verified;

    @JsonProperty("is_banned")
    private boolean banned;

    @JsonProperty("country")
    private String country;

    @JsonProperty("region")
    private String region;

    @JsonProperty("global_rank")
    private int globalRank;

    @JsonProperty("peak_elo")
    private int peakElo;

    @JsonProperty("wins")
    private int wins;

    @JsonProperty("losses")
    private int losses;

    @JsonProperty("draws")
    private int draws;

    public PlayerProfile() {}

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getDisplayName() { return displayName; }
    public String getAvatarUrl() { return avatarUrl; }
    public boolean isVerified() { return verified; }
    public boolean isBanned() { return banned; }
    public String getCountry() { return country; }
    public String getRegion() { return region; }
    public int getGlobalRank() { return globalRank; }
    public int getPeakElo() { return peakElo; }
    public int getWins() { return wins; }
    public int getLosses() { return losses; }
    public int getDraws() { return draws; }
}
