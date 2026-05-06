package com.pvpindex.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request body for {@code POST /battles/{uuid}/dispute}.
 */
public final class DisputeBattleRequest {

    @JsonProperty("reason")
    private final String reason;

    public DisputeBattleRequest(String reason) {
        this.reason = reason;
    }

    public String getReason() { return reason; }
}
