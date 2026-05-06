package com.pvpindex.api.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pvpindex.api.http.ApiResult;
import com.pvpindex.api.http.HttpTransport;
import com.pvpindex.api.model.common.Page;
import com.pvpindex.api.model.leaderboard.LeaderboardEntry;
import com.pvpindex.api.model.season.Season;
import com.pvpindex.api.request.LeaderboardParams;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Seasons endpoint: list, get, and fetch season leaderboard.
 */
public class SeasonClient {

    private final HttpTransport transport;

    public SeasonClient(HttpTransport transport) {
        this.transport = transport;
    }

    public CompletableFuture<ApiResult<List<Season>>> listSeasonsAsync() {
        return transport.getAsync("/api/seasons", new TypeReference<>() {});
    }

    public List<Season> listSeasons() {
        return listSeasonsAsync().join().orElseThrow();
    }

    public CompletableFuture<ApiResult<Season>> getSeasonAsync(String slug) {
        return transport.getAsync("/api/seasons/" + slug, Season.class);
    }

    public Season getSeason(String slug) {
        return getSeasonAsync(slug).join().orElseThrow();
    }

    public CompletableFuture<ApiResult<Page<LeaderboardEntry>>> getSeasonLeaderboardAsync(
            String slug, LeaderboardParams params) {
        String qs = params != null ? params.toQueryString() : "";
        return transport.getAsync("/api/seasons/" + slug + "/leaderboard" + qs, new TypeReference<>() {});
    }

    public Page<LeaderboardEntry> getSeasonLeaderboard(String slug, LeaderboardParams params) {
        return getSeasonLeaderboardAsync(slug, params).join().orElseThrow();
    }

    public Page<LeaderboardEntry> getSeasonLeaderboard(String slug) {
        return getSeasonLeaderboard(slug, null);
    }
}
