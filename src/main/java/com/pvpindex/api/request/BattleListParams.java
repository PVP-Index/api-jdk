package com.pvpindex.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Query parameters for {@code GET /battles}.
 * Pass to {@link com.pvpindex.api.endpoint.BattleClient#listBattles}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class BattleListParams {

    private final String status;
    private final String gameMode;
    private final String server;
    private final String region;
    private final String playerUsername;
    private final String opponentUsername;
    private final Integer perPage;

    private BattleListParams(Builder builder) {
        this.status = builder.status;
        this.gameMode = builder.gameMode;
        this.server = builder.server;
        this.region = builder.region;
        this.playerUsername = builder.playerUsername;
        this.opponentUsername = builder.opponentUsername;
        this.perPage = builder.perPage;
    }

    public String getStatus() { return status; }
    public String getGameMode() { return gameMode; }
    public String getServer() { return server; }
    public String getRegion() { return region; }
    public String getPlayerUsername() { return playerUsername; }
    public String getOpponentUsername() { return opponentUsername; }
    public Integer getPerPage() { return perPage; }

    /**
     * Serializes this object as a URL query string (e.g. {@code ?status=confirmed&per_page=20}).
     * Returns empty string if all fields are null.
     */
    public String toQueryString() {
        StringBuilder sb = new StringBuilder();
        append(sb, "status", status);
        append(sb, "game_mode", gameMode);
        append(sb, "server", server);
        append(sb, "region", region);
        append(sb, "player_username", playerUsername);
        append(sb, "opponent_username", opponentUsername);
        append(sb, "per_page", perPage != null ? perPage.toString() : null);
        return sb.length() > 0 ? "?" + sb.substring(1) : "";
    }

    private static void append(StringBuilder sb, String key, String value) {
        if (value != null) {
            sb.append('&').append(key).append('=').append(value);
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String status;
        private String gameMode;
        private String server;
        private String region;
        private String playerUsername;
        private String opponentUsername;
        private Integer perPage;

        public Builder status(String status) { this.status = status; return this; }
        public Builder gameMode(String gameMode) { this.gameMode = gameMode; return this; }
        public Builder server(String server) { this.server = server; return this; }
        public Builder region(String region) { this.region = region; return this; }
        public Builder playerUsername(String playerUsername) { this.playerUsername = playerUsername; return this; }
        public Builder opponentUsername(String opponentUsername) { this.opponentUsername = opponentUsername; return this; }
        public Builder perPage(int perPage) { this.perPage = perPage; return this; }

        public BattleListParams build() { return new BattleListParams(this); }
    }
}
