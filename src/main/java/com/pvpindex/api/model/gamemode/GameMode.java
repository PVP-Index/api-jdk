package com.pvpindex.api.model.gamemode;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An available game mode (e.g. "Vanilla 1.21", "Crystal PvP").
 */
public final class GameMode {

    @JsonProperty("id")
    private int id;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("battle_count")
    private int battleCount;

    public GameMode() {}

    public int getId() { return id; }
    public String getSlug() { return slug; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getBattleCount() { return battleCount; }
}
