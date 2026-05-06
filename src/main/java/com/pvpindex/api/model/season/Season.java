package com.pvpindex.api.model.season;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * An ELO season.
 */
public final class Season {

    @JsonProperty("id")
    private int id;

    @JsonProperty("number")
    private int number;

    @JsonProperty("name")
    private String name;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("starts_at")
    private Instant startsAt;

    @JsonProperty("ends_at")
    private Instant endsAt;

    @JsonProperty("is_active")
    private boolean active;

    public Season() {}

    public int getId() { return id; }
    public int getNumber() { return number; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public Instant getStartsAt() { return startsAt; }
    public Instant getEndsAt() { return endsAt; }
    public boolean isActive() { return active; }
}
