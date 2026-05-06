package com.pvpindex.api.model.battle;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A participant's outcome in a battle.
 */
public enum ParticipantResult {
    @JsonProperty("winner")
    WINNER,
    @JsonProperty("loser")
    LOSER,
    @JsonProperty("draw")
    DRAW,
    @JsonProperty("surrender")
    SURRENDER
}
