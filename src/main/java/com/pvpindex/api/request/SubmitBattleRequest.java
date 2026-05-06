package com.pvpindex.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Request body for {@code POST /battles}.
 *
 * <p>Use {@link Builder} to construct. This replaces the
 * {@code Map<String, Object>}-based payload produced by
 * {@code BattlePayloadFactory} in pvpindex-battles.</p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class SubmitBattleRequest {

    @JsonProperty("uuid")
    private final String uuid;

    @JsonProperty("server_id")
    private final String serverId;

    @JsonProperty("game_mode_slug")
    private final String gameModeSlug;

    @JsonProperty("battle_type")
    private final String battleType;

    @JsonProperty("status")
    private final String status;

    @JsonProperty("participants")
    private final List<Participant> participants;

    @JsonProperty("winners")
    private final List<String> winners;

    @JsonProperty("losers")
    private final List<String> losers;

    @JsonProperty("started_at")
    private final Instant startedAt;

    @JsonProperty("ended_at")
    private final Instant endedAt;

    @JsonProperty("replay_data")
    private final Map<String, Object> replayData;

    @JsonProperty("replay_url")
    private final String replayUrl;

    @JsonProperty("metadata")
    private final Map<String, Object> metadata;

    private SubmitBattleRequest(Builder builder) {
        this.uuid = builder.uuid;
        this.serverId = builder.serverId;
        this.gameModeSlug = builder.gameModeSlug;
        this.battleType = builder.battleType;
        this.status = builder.status;
        this.participants = builder.participants;
        this.winners = builder.winners;
        this.losers = builder.losers;
        this.startedAt = builder.startedAt;
        this.endedAt = builder.endedAt;
        this.replayData = builder.replayData;
        this.replayUrl = builder.replayUrl;
        this.metadata = builder.metadata;
    }

    // --- Getters ---

    public String getUuid() { return uuid; }
    public String getServerId() { return serverId; }
    public String getGameModeSlug() { return gameModeSlug; }
    public String getBattleType() { return battleType; }
    public String getStatus() { return status; }
    public List<Participant> getParticipants() { return participants; }
    public List<String> getWinners() { return winners; }
    public List<String> getLosers() { return losers; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getEndedAt() { return endedAt; }
    public Map<String, Object> getReplayData() { return replayData; }
    public String getReplayUrl() { return replayUrl; }
    public Map<String, Object> getMetadata() { return metadata; }

    // --- Nested Participant ---

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static final class Participant {

        @JsonProperty("minecraft_uuid")
        private final String minecraftUuid;

        @JsonProperty("minecraft_username")
        private final String minecraftUsername;

        @JsonProperty("team")
        private final String team;

        @JsonProperty("result")
        private final String result;

        @JsonProperty("kills")
        private final int kills;

        @JsonProperty("deaths")
        private final int deaths;

        @JsonProperty("damage_dealt")
        private final double damageDealt;

        @JsonProperty("damage_taken")
        private final double damageTaken;

        @JsonProperty("healing_done")
        private final double healingDone;

        @JsonProperty("elo_before")
        private final Integer eloBefore;

        @JsonProperty("elo_after")
        private final Integer eloAfter;

        @JsonProperty("start_setup")
        private final Map<String, Object> startSetup;

        private Participant(Builder builder) {
            this.minecraftUuid = builder.minecraftUuid;
            this.minecraftUsername = builder.minecraftUsername;
            this.team = builder.team;
            this.result = builder.result;
            this.kills = builder.kills;
            this.deaths = builder.deaths;
            this.damageDealt = builder.damageDealt;
            this.damageTaken = builder.damageTaken;
            this.healingDone = builder.healingDone;
            this.eloBefore = builder.eloBefore;
            this.eloAfter = builder.eloAfter;
            this.startSetup = builder.startSetup;
        }

        public String getMinecraftUuid() { return minecraftUuid; }
        public String getMinecraftUsername() { return minecraftUsername; }
        public String getTeam() { return team; }
        public String getResult() { return result; }
        public int getKills() { return kills; }
        public int getDeaths() { return deaths; }
        public double getDamageDealt() { return damageDealt; }
        public double getDamageTaken() { return damageTaken; }
        public double getHealingDone() { return healingDone; }
        public Integer getEloBefore() { return eloBefore; }
        public Integer getEloAfter() { return eloAfter; }
        public Map<String, Object> getStartSetup() { return startSetup; }

        public static Builder builder(UUID minecraftUuid, String minecraftUsername) {
            return new Builder(minecraftUuid.toString(), minecraftUsername);
        }

        public static Builder builder(String minecraftUuid, String minecraftUsername) {
            return new Builder(minecraftUuid, minecraftUsername);
        }

        public static final class Builder {
            private final String minecraftUuid;
            private final String minecraftUsername;
            private String team;
            private String result;
            private int kills;
            private int deaths;
            private double damageDealt;
            private double damageTaken;
            private double healingDone;
            private Integer eloBefore;
            private Integer eloAfter;
            private Map<String, Object> startSetup;

            private Builder(String minecraftUuid, String minecraftUsername) {
                this.minecraftUuid = minecraftUuid;
                this.minecraftUsername = minecraftUsername;
            }

            public Builder team(String team) { this.team = team; return this; }
            public Builder result(String result) { this.result = result; return this; }
            public Builder kills(int kills) { this.kills = kills; return this; }
            public Builder deaths(int deaths) { this.deaths = deaths; return this; }
            public Builder damageDealt(double d) { this.damageDealt = d; return this; }
            public Builder damageTaken(double d) { this.damageTaken = d; return this; }
            public Builder healingDone(double h) { this.healingDone = h; return this; }
            public Builder eloBefore(Integer elo) { this.eloBefore = elo; return this; }
            public Builder eloAfter(Integer elo) { this.eloAfter = elo; return this; }
            public Builder startSetup(Map<String, Object> setup) { this.startSetup = setup; return this; }

            public Participant build() {
                return new Participant(this);
            }
        }
    }

    // --- SubmitBattleRequest.Builder ---

    public static Builder builder(String uuid, String serverId, String gameModeSlug) {
        return new Builder(uuid, serverId, gameModeSlug);
    }

    public static final class Builder {
        private final String uuid;
        private final String serverId;
        private final String gameModeSlug;
        private String battleType;
        private String status;
        private List<Participant> participants;
        private List<String> winners;
        private List<String> losers;
        private Instant startedAt;
        private Instant endedAt;
        private Map<String, Object> replayData;
        private String replayUrl;
        private Map<String, Object> metadata;

        private Builder(String uuid, String serverId, String gameModeSlug) {
            this.uuid = uuid;
            this.serverId = serverId;
            this.gameModeSlug = gameModeSlug;
        }

        public Builder battleType(String battleType) { this.battleType = battleType; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder participants(List<Participant> participants) { this.participants = participants; return this; }
        public Builder winners(List<String> winners) { this.winners = winners; return this; }
        public Builder losers(List<String> losers) { this.losers = losers; return this; }
        public Builder startedAt(Instant startedAt) { this.startedAt = startedAt; return this; }
        public Builder endedAt(Instant endedAt) { this.endedAt = endedAt; return this; }
        public Builder replayData(Map<String, Object> replayData) { this.replayData = replayData; return this; }
        public Builder replayUrl(String replayUrl) { this.replayUrl = replayUrl; return this; }
        public Builder metadata(Map<String, Object> metadata) { this.metadata = metadata; return this; }

        public SubmitBattleRequest build() {
            return new SubmitBattleRequest(this);
        }
    }
}
