package com.pvpindex.api.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pvpindex.api.http.ApiResult;
import com.pvpindex.api.http.HttpTransport;
import com.pvpindex.api.model.server.Server;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Servers endpoint: list and get individual server.
 */
public class ServerClient {

    private final HttpTransport transport;

    public ServerClient(HttpTransport transport) {
        this.transport = transport;
    }

    public CompletableFuture<ApiResult<List<Server>>> listServersAsync() {
        return transport.getAsync("/api/servers", new TypeReference<>() {});
    }

    public List<Server> listServers() {
        return listServersAsync().join().orElseThrow();
    }

    public CompletableFuture<ApiResult<Server>> getServerAsync(String slug) {
        return transport.getAsync("/api/servers/" + slug, Server.class);
    }

    public Server getServer(String slug) {
        return getServerAsync(slug).join().orElseThrow();
    }
}
