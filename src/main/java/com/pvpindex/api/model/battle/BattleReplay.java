package com.pvpindex.api.model.battle;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Replay data for a battle.
 */
public final class BattleReplay {

    @JsonProperty("replay_url")
    private String replayUrl;

    @JsonProperty("replay_data")
    private Object replayData;

    public BattleReplay() {}

    public String getReplayUrl() { return replayUrl; }
    public Object getReplayData() { return replayData; }
}
