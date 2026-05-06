package com.pvpindex.api.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pvpindex.api.http.ApiResult;
import com.pvpindex.api.http.HttpTransport;
import com.pvpindex.api.model.moderation.BanEntry;
import com.pvpindex.api.request.PublishFederatedBanRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Federated ban sync endpoints:
 * <ul>
 *   <li>{@code GET /moderation/federated-bans} — fetch bans from the network</li>
 *   <li>{@code POST /moderation/federated-bans} — publish a new ban</li>
 * </ul>
 */
public class FederationClient {

    private final HttpTransport transport;

    public FederationClient(HttpTransport transport) {
        this.transport = transport;
    }

    public CompletableFuture<ApiResult<List<BanEntry>>> fetchFederatedBansAsync() {
        return transport.getAsync("/api/moderation/federated-bans", new TypeReference<>() {});
    }

    public List<BanEntry> fetchFederatedBans() {
        return fetchFederatedBansAsync().join().orElseThrow();
    }

    public CompletableFuture<ApiResult<BanEntry>> publishFederatedBanAsync(PublishFederatedBanRequest request) {
        return transport.postAsync("/api/moderation/federated-bans", request, BanEntry.class);
    }

    public BanEntry publishFederatedBan(PublishFederatedBanRequest request) {
        return publishFederatedBanAsync(request).join().orElseThrow();
    }
}
