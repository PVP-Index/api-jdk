package com.pvpindex.api.model.server;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * A registered Minecraft server.
 */
public final class Server {

    @JsonProperty("id")
    private int id;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("verified")
    private boolean verified;

    @JsonProperty("trust_score")
    private double trustScore;

    @JsonProperty("rating")
    private double rating;

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("website_url")
    private String websiteUrl;

    @JsonProperty("region")
    private String region;

    @JsonProperty("joined_at")
    private Instant joinedAt;

    @JsonProperty("battle_count")
    private int battleCount;

    @JsonProperty("unique_players")
    private int uniquePlayers;

    @JsonProperty("status")
    private String status;

    @JsonProperty("player_count")
    private Integer playerCount;

    @JsonProperty("max_player_count")
    private Integer maxPlayerCount;

    @JsonProperty("last_seen_at")
    private Instant lastSeenAt;

    @JsonProperty("uptime_percentage")
    private Double uptimePercentage;

    public Server() {}

    public int getId() { return id; }
    public String getSlug() { return slug; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isVerified() { return verified; }
    public double getTrustScore() { return trustScore; }
    public double getRating() { return rating; }
    public String getIpAddress() { return ipAddress; }
    public String getWebsiteUrl() { return websiteUrl; }
    public String getRegion() { return region; }
    public Instant getJoinedAt() { return joinedAt; }
    public int getBattleCount() { return battleCount; }
    public int getUniquePlayers() { return uniquePlayers; }
    public String getStatus() { return status; }
    public Integer getPlayerCount() { return playerCount; }
    public Integer getMaxPlayerCount() { return maxPlayerCount; }
    public Instant getLastSeenAt() { return lastSeenAt; }
    public Double getUptimePercentage() { return uptimePercentage; }
}
