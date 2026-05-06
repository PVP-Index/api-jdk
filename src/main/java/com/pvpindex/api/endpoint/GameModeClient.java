package com.pvpindex.api.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pvpindex.api.http.ApiResult;
import com.pvpindex.api.http.HttpTransport;
import com.pvpindex.api.model.gamemode.GameMode;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Game modes endpoint.
 */
public class GameModeClient {

    private final HttpTransport transport;

    public GameModeClient(HttpTransport transport) {
        this.transport = transport;
    }

    public CompletableFuture<ApiResult<List<GameMode>>> listGameModesAsync() {
        return transport.getAsync("/api/game-modes", new TypeReference<>() {});
    }

    public List<GameMode> listGameModes() {
        return listGameModesAsync().join().orElseThrow();
    }

    public CompletableFuture<ApiResult<GameMode>> getGameModeAsync(String slug) {
        return transport.getAsync("/api/game-modes/" + slug, GameMode.class);
    }

    public GameMode getGameMode(String slug) {
        return getGameModeAsync(slug).join().orElseThrow();
    }
}
