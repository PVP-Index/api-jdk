package com.pvpindex.api.model.battle;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Battle lifecycle status as returned by the API.
 */
public enum BattleStatus {
    @JsonProperty("pending")
    PENDING,
    @JsonProperty("confirmed")
    CONFIRMED,
    @JsonProperty("disputed")
    DISPUTED
}
