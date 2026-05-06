package com.pvpindex.api.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pvpindex.api.http.ApiResult;
import com.pvpindex.api.http.HttpTransport;
import com.pvpindex.api.model.battle.Battle;
import com.pvpindex.api.model.battle.BattleReplay;
import com.pvpindex.api.model.common.Page;
import com.pvpindex.api.request.BattleListParams;
import com.pvpindex.api.request.DisputeBattleRequest;
import com.pvpindex.api.request.SubmitBattleRequest;

import java.util.concurrent.CompletableFuture;

/**
 * Battles endpoint: list, get, submit, confirm, dispute.
 *
 * <p>Every method has an {@code Async} variant returning
 * {@code CompletableFuture<ApiResult<T>>} and a blocking variant that
 * returns {@code T} directly and throws
 * {@link com.pvpindex.api.http.ApiException} on non-2xx.</p>
 */
public class BattleClient {

    private final HttpTransport transport;

    public BattleClient(HttpTransport transport) {
        this.transport = transport;
    }

    // --- List battles ---

    public CompletableFuture<ApiResult<Page<Battle>>> listBattlesAsync(BattleListParams params) {
        String qs = params != null ? params.toQueryString() : "";
        return transport.getAsync("/api/battles" + qs, new TypeReference<>() {});
    }

    public Page<Battle> listBattles(BattleListParams params) {
        return listBattlesAsync(params).join().orElseThrow();
    }

    public Page<Battle> listBattles() {
        return listBattles(null);
    }

    // --- Get battle ---

    public CompletableFuture<ApiResult<Battle>> getBattleAsync(String uuid) {
        return transport.getAsync("/api/battles/" + uuid, Battle.class);
    }

    public Battle getBattle(String uuid) {
        return getBattleAsync(uuid).join().orElseThrow();
    }

    // --- Get replay ---

    public CompletableFuture<ApiResult<BattleReplay>> getBattleReplayAsync(String uuid) {
        return transport.getAsync("/api/battles/" + uuid + "/replay", BattleReplay.class);
    }

    public BattleReplay getBattleReplay(String uuid) {
        return getBattleReplayAsync(uuid).join().orElseThrow();
    }

    // --- Submit battle ---

    public CompletableFuture<ApiResult<Battle>> submitBattleAsync(SubmitBattleRequest request) {
        return transport.postAsync("/api/battles", request, Battle.class);
    }

    public Battle submitBattle(SubmitBattleRequest request) {
        return submitBattleAsync(request).join().orElseThrow();
    }

    // --- Confirm battle ---

    public CompletableFuture<ApiResult<Battle>> confirmBattleAsync(String uuid) {
        return transport.postAsync("/api/battles/" + uuid + "/confirm", Battle.class);
    }

    public Battle confirmBattle(String uuid) {
        return confirmBattleAsync(uuid).join().orElseThrow();
    }

    // --- Dispute battle ---

    public CompletableFuture<ApiResult<Battle>> disputeBattleAsync(String uuid, DisputeBattleRequest request) {
        return transport.postAsync("/api/battles/" + uuid + "/dispute", request, Battle.class);
    }

    public Battle disputeBattle(String uuid, DisputeBattleRequest request) {
        return disputeBattleAsync(uuid, request).join().orElseThrow();
    }
}
