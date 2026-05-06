package com.pvpindex.api.model.battle;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

/**
 * A battle record as returned by the API.
 */
public final class Battle {

    @JsonProperty("id")
    private int id;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("game_mode")
    private String gameMode;

    @JsonProperty("server")
    private String server;

    @JsonProperty("server_slug")
    private String serverSlug;

    @JsonProperty("server_region")
    private String serverRegion;

    @JsonProperty("status")
    private BattleStatus status;

    @JsonProperty("dispute_reason")
    private String disputeReason;

    @JsonProperty("dispute_reported_by")
    private String disputeReportedBy;

    @JsonProperty("replay_url")
    private String replayUrl;

    @JsonProperty("started_at")
    private Instant startedAt;

    @JsonProperty("ended_at")
    private Instant endedAt;

    @JsonProperty("participants")
    private List<BattleParticipant> participants;

    public Battle() {}

    public int getId() { return id; }
    public String getUuid() { return uuid; }
    public String getGameMode() { return gameMode; }
    public String getServer() { return server; }
    public String getServerSlug() { return serverSlug; }
    public String getServerRegion() { return serverRegion; }
    public BattleStatus getStatus() { return status; }
    public String getDisputeReason() { return disputeReason; }
    public String getDisputeReportedBy() { return disputeReportedBy; }
    public String getReplayUrl() { return replayUrl; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getEndedAt() { return endedAt; }
    public List<BattleParticipant> getParticipants() { return participants; }
}
