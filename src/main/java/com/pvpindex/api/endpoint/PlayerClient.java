package com.pvpindex.api.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pvpindex.api.http.ApiResult;
import com.pvpindex.api.http.HttpTransport;
import com.pvpindex.api.model.player.PlayerProfile;
import com.pvpindex.api.model.player.PlayerRanking;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Players endpoint: profile, rankings, ELO history.
 */
public class PlayerClient {

    private final HttpTransport transport;

    public PlayerClient(HttpTransport transport) {
        this.transport = transport;
    }

    // --- Get player profile ---

    public CompletableFuture<ApiResult<PlayerProfile>> getPlayerAsync(String identifier) {
        return transport.getAsync("/api/players/" + identifier, PlayerProfile.class);
    }

    public PlayerProfile getPlayer(String identifier) {
        return getPlayerAsync(identifier).join().orElseThrow();
    }

    // --- Get player rankings (all game modes) ---

    public CompletableFuture<ApiResult<List<PlayerRanking>>> getPlayerRankingsAsync(String identifier) {
        return transport.getAsync("/api/players/" + identifier + "/rankings", new TypeReference<>() {});
    }

    public List<PlayerRanking> getPlayerRankings(String identifier) {
        return getPlayerRankingsAsync(identifier).join().orElseThrow();
    }

    // --- Get player ELO history ---

    public CompletableFuture<ApiResult<List<PlayerRanking>>> getPlayerHistoryAsync(String identifier) {
        return transport.getAsync("/api/players/" + identifier + "/history", new TypeReference<>() {});
    }

    public List<PlayerRanking> getPlayerHistory(String identifier) {
        return getPlayerHistoryAsync(identifier).join().orElseThrow();
    }
}
