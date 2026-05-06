package com.pvpindex.api.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Wraps a paginated Laravel response.
 * Maps to: { data: T[], current_page, last_page, per_page, total }
 */
public final class Page<T> {

    @JsonProperty("data")
    private List<T> data;

    @JsonProperty("current_page")
    private int currentPage;

    @JsonProperty("last_page")
    private int lastPage;

    @JsonProperty("per_page")
    private int perPage;

    @JsonProperty("total")
    private int total;

    public Page() {}

    public List<T> getData() { return data; }
    public int getCurrentPage() { return currentPage; }
    public int getLastPage() { return lastPage; }
    public int getPerPage() { return perPage; }
    public int getTotal() { return total; }

    public boolean hasNextPage() {
        return currentPage < lastPage;
    }
}
