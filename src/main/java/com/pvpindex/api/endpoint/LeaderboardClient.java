package com.pvpindex.api.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pvpindex.api.http.ApiResult;
import com.pvpindex.api.http.HttpTransport;
import com.pvpindex.api.model.common.Page;
import com.pvpindex.api.model.leaderboard.LeaderboardEntry;
import com.pvpindex.api.request.LeaderboardParams;

import java.util.concurrent.CompletableFuture;

/**
 * Leaderboard endpoint: global and per-game-mode rankings.
 */
public class LeaderboardClient {

    private final HttpTransport transport;

    public LeaderboardClient(HttpTransport transport) {
        this.transport = transport;
    }

    // --- Global leaderboard ---

    public CompletableFuture<ApiResult<Page<LeaderboardEntry>>> getLeaderboardAsync(LeaderboardParams params) {
        String qs = params != null ? params.toQueryString() : "";
        return transport.getAsync("/api/leaderboards" + qs, new TypeReference<>() {});
    }

    public Page<LeaderboardEntry> getLeaderboard(LeaderboardParams params) {
        return getLeaderboardAsync(params).join().orElseThrow();
    }

    public Page<LeaderboardEntry> getLeaderboard() {
        return getLeaderboard(null);
    }

    // --- Per-game-mode leaderboard ---

    public CompletableFuture<ApiResult<Page<LeaderboardEntry>>> getLeaderboardForModeAsync(
            String gameMode, LeaderboardParams params) {
        String qs = params != null ? params.toQueryString() : "";
        return transport.getAsync("/api/leaderboards/" + gameMode + qs, new TypeReference<>() {});
    }

    public Page<LeaderboardEntry> getLeaderboardForMode(String gameMode, LeaderboardParams params) {
        return getLeaderboardForModeAsync(gameMode, params).join().orElseThrow();
    }

    public Page<LeaderboardEntry> getLeaderboardForMode(String gameMode) {
        return getLeaderboardForMode(gameMode, null);
    }
}
