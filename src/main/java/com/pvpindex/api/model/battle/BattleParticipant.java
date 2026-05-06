package com.pvpindex.api.model.battle;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A single participant in a battle, as returned by the API.
 */
public final class BattleParticipant {

    @JsonProperty("player_id")
    private int playerId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("elo_before")
    private int eloBefore;

    @JsonProperty("elo_after")
    private int eloAfter;

    @JsonProperty("elo_change")
    private int eloChange;

    @JsonProperty("result")
    private ParticipantResult result;

    @JsonProperty("start_setup")
    private Object startSetup;

    public BattleParticipant() {}

    public int getPlayerId() { return playerId; }
    public String getUsername() { return username; }
    public String getAvatarUrl() { return avatarUrl; }
    public int getEloBefore() { return eloBefore; }
    public int getEloAfter() { return eloAfter; }
    public int getEloChange() { return eloChange; }
    public ParticipantResult getResult() { return result; }
    public Object getStartSetup() { return startSetup; }
}
