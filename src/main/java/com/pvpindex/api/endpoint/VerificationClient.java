package com.pvpindex.api.endpoint;

import com.pvpindex.api.http.ApiResult;
import com.pvpindex.api.http.HttpTransport;
import com.pvpindex.api.request.VerifyMinecraftRequest;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Minecraft account verification endpoint ({@code POST /verify-minecraft}).
 */
public class VerificationClient {

    private final HttpTransport transport;

    public VerificationClient(HttpTransport transport) {
        this.transport = transport;
    }

    /**
     * Verifies a player's claim code submitted in-game.
     * Returns the raw API response as a Map.
     */
    @SuppressWarnings("unchecked")
    public CompletableFuture<ApiResult<Map<String, Object>>> verifyMinecraftAsync(VerifyMinecraftRequest request) {
        return transport.postAsync("/api/verify-minecraft", request, (Class<Map<String, Object>>) (Class<?>) Map.class);
    }

    public Map<String, Object> verifyMinecraft(VerifyMinecraftRequest request) {
        return verifyMinecraftAsync(request).join().orElseThrow();
    }
}
