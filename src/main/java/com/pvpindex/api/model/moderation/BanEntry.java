package com.pvpindex.api.model.moderation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.UUID;

/**
 * A federated ban entry, as returned by {@code GET /moderation/federated-bans}.
 * Aligns with the {@code BanEntry} record used in pvpindex-battles.
 */
public final class BanEntry {

    public enum Scope {
        LOCAL,
        FEDERATED
    }

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("player_uuid")
    private UUID playerUuid;

    @JsonProperty("player_name")
    private String playerName;

    @JsonProperty("issued_by")
    private UUID issuedBy;

    @JsonProperty("issued_by_name")
    private String issuedByName;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("issued_at")
    private Instant issuedAt;

    @JsonProperty("expires_at")
    private Instant expiresAt;

    @JsonProperty("scope")
    private Scope scope;

    @JsonProperty("battle_uuid")
    private UUID battleUuid;

    @JsonProperty("source_server_id")
    private String sourceServerId;

    public BanEntry() {}

    public UUID getId() { return id; }
    public UUID getPlayerUuid() { return playerUuid; }
    public String getPlayerName() { return playerName; }
    public UUID getIssuedBy() { return issuedBy; }
    public String getIssuedByName() { return issuedByName; }
    public String getReason() { return reason; }
    public Instant getIssuedAt() { return issuedAt; }
    public Instant getExpiresAt() { return expiresAt; }
    public Scope getScope() { return scope; }
    public UUID getBattleUuid() { return battleUuid; }
    public String getSourceServerId() { return sourceServerId; }

    public boolean isPermanent() { return expiresAt == null; }

    public boolean isActiveAt(Instant moment) {
        return expiresAt == null || moment.isBefore(expiresAt);
    }
}
