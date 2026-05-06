package com.pvpindex.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.UUID;

/**
 * Request body for {@code POST /moderation/federated-bans}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class PublishFederatedBanRequest {

    @JsonProperty("player_uuid")
    private final String playerUuid;

    @JsonProperty("player_name")
    private final String playerName;

    @JsonProperty("reason")
    private final String reason;

    @JsonProperty("expires_at")
    private final Instant expiresAt;

    @JsonProperty("battle_uuid")
    private final String battleUuid;

    private PublishFederatedBanRequest(Builder builder) {
        this.playerUuid = builder.playerUuid;
        this.playerName = builder.playerName;
        this.reason = builder.reason;
        this.expiresAt = builder.expiresAt;
        this.battleUuid = builder.battleUuid;
    }

    public String getPlayerUuid() { return playerUuid; }
    public String getPlayerName() { return playerName; }
    public String getReason() { return reason; }
    public Instant getExpiresAt() { return expiresAt; }
    public String getBattleUuid() { return battleUuid; }

    public static Builder builder(UUID playerUuid, String playerName, String reason) {
        return new Builder(playerUuid.toString(), playerName, reason);
    }

    public static final class Builder {
        private final String playerUuid;
        private final String playerName;
        private final String reason;
        private Instant expiresAt;
        private String battleUuid;

        private Builder(String playerUuid, String playerName, String reason) {
            this.playerUuid = playerUuid;
            this.playerName = playerName;
            this.reason = reason;
        }

        public Builder expiresAt(Instant expiresAt) { this.expiresAt = expiresAt; return this; }
        public Builder battleUuid(UUID battleUuid) { this.battleUuid = battleUuid.toString(); return this; }
        public Builder battleUuid(String battleUuid) { this.battleUuid = battleUuid; return this; }

        public PublishFederatedBanRequest build() {
            return new PublishFederatedBanRequest(this);
        }
    }
}
