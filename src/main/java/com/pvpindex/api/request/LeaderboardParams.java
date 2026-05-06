package com.pvpindex.api.request;

/**
 * Query parameters for leaderboard endpoints.
 * Pass to {@link com.pvpindex.api.endpoint.LeaderboardClient}.
 */
public final class LeaderboardParams {

    private final String search;
    private final String region;
    private final String sort;
    private final Integer perPage;
    private final Integer page;

    private LeaderboardParams(Builder builder) {
        this.search = builder.search;
        this.region = builder.region;
        this.sort = builder.sort;
        this.perPage = builder.perPage;
        this.page = builder.page;
    }

    public String getSearch() { return search; }
    public String getRegion() { return region; }
    public String getSort() { return sort; }
    public Integer getPerPage() { return perPage; }
    public Integer getPage() { return page; }

    public String toQueryString() {
        StringBuilder sb = new StringBuilder();
        append(sb, "search", search);
        append(sb, "region", region);
        append(sb, "sort", sort);
        append(sb, "per_page", perPage != null ? perPage.toString() : null);
        append(sb, "page", page != null ? page.toString() : null);
        return sb.length() > 0 ? "?" + sb.substring(1) : "";
    }

    private static void append(StringBuilder sb, String key, String value) {
        if (value != null) {
            sb.append('&').append(key).append('=').append(value);
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String search;
        private String region;
        private String sort;
        private Integer perPage;
        private Integer page;

        public Builder search(String search) { this.search = search; return this; }
        public Builder region(String region) { this.region = region; return this; }
        /** Sort field: elo | wins | matches_played */
        public Builder sort(String sort) { this.sort = sort; return this; }
        public Builder perPage(int perPage) { this.perPage = perPage; return this; }
        public Builder page(int page) { this.page = page; return this; }

        public LeaderboardParams build() { return new LeaderboardParams(this); }
    }
}
