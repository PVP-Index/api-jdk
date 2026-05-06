package com.pvpindex.api.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Low-level HTTP transport layer. Wraps {@link java.net.http.HttpClient} and handles:
 * <ul>
 *   <li>Bearer token injection on every request</li>
 *   <li>JSON serialization of request bodies (Jackson)</li>
 *   <li>JSON deserialization of response bodies into {@link ApiResult}</li>
 *   <li>Network-level failures wrapped into {@link ApiResult#networkFailure}</li>
 * </ul>
 *
 * <p>All methods are non-blocking and return a {@link CompletableFuture}.</p>
 */
public class HttpTransport {

    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String ACCEPT_JSON = "application/json";

    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private final String baseUrl;
    private final String bearerToken;
    private final Duration requestTimeout;

    public HttpTransport(String baseUrl, String bearerToken,
                         Duration connectTimeout, Duration requestTimeout) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.bearerToken = bearerToken;
        this.requestTimeout = requestTimeout;

        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(connectTimeout)
            .build();

        this.mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // --- GET ---

    public <T> CompletableFuture<ApiResult<T>> getAsync(String path, Class<T> responseType) {
        HttpRequest request = buildRequest(path)
            .GET()
            .build();
        return send(request, responseType);
    }

    public <T> CompletableFuture<ApiResult<T>> getAsync(String path, TypeReference<T> responseType) {
        HttpRequest request = buildRequest(path)
            .GET()
            .build();
        return send(request, responseType);
    }

    // --- POST ---

    public <T> CompletableFuture<ApiResult<T>> postAsync(String path, Object body, Class<T> responseType) {
        HttpRequest request = buildRequest(path)
            .header("Content-Type", CONTENT_TYPE_JSON)
            .POST(toBodyPublisher(body))
            .build();
        return send(request, responseType);
    }

    public <T> CompletableFuture<ApiResult<T>> postAsync(String path, Object body, TypeReference<T> responseType) {
        HttpRequest request = buildRequest(path)
            .header("Content-Type", CONTENT_TYPE_JSON)
            .POST(toBodyPublisher(body))
            .build();
        return send(request, responseType);
    }

    /** POST with no request body. */
    public <T> CompletableFuture<ApiResult<T>> postAsync(String path, Class<T> responseType) {
        return postAsync(path, Map.of(), responseType);
    }

    // --- PATCH ---

    public <T> CompletableFuture<ApiResult<T>> patchAsync(String path, Object body, Class<T> responseType) {
        HttpRequest request = buildRequest(path)
            .header("Content-Type", CONTENT_TYPE_JSON)
            .method("PATCH", toBodyPublisher(body))
            .build();
        return send(request, responseType);
    }

    // --- DELETE ---

    public <T> CompletableFuture<ApiResult<T>> deleteAsync(String path, Class<T> responseType) {
        HttpRequest request = buildRequest(path)
            .DELETE()
            .build();
        return send(request, responseType);
    }

    // --- Internal helpers ---

    private HttpRequest.Builder buildRequest(String path) {
        String url = baseUrl + (path.startsWith("/") ? path : "/" + path);
        return HttpRequest.newBuilder()
            .uri(URI.create(url))
            .timeout(requestTimeout)
            .header("Authorization", "Bearer " + bearerToken)
            .header("Accept", ACCEPT_JSON)
            .header("User-Agent", "pvpindex-api-client/1.0");
    }

    private HttpRequest.BodyPublisher toBodyPublisher(Object body) {
        try {
            byte[] bytes = mapper.writeValueAsBytes(body);
            return HttpRequest.BodyPublishers.ofByteArray(bytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize request body", e);
        }
    }

    private <T> CompletableFuture<ApiResult<T>> send(HttpRequest request, Class<T> responseType) {
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(response -> parseResponse(response, responseType))
            .exceptionally(ex -> ApiResult.networkFailure(ex.getMessage()));
    }

    private <T> CompletableFuture<ApiResult<T>> send(HttpRequest request, TypeReference<T> responseType) {
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(response -> parseResponse(response, responseType))
            .exceptionally(ex -> ApiResult.networkFailure(ex.getMessage()));
    }

    private <T> ApiResult<T> parseResponse(HttpResponse<String> response, Class<T> responseType) {
        int status = response.statusCode();
        String body = response.body();
        if (status >= 200 && status < 300) {
            try {
                T data = mapper.readValue(body, responseType);
                return ApiResult.success(status, data);
            } catch (Exception e) {
                return ApiResult.failure(status, "Deserialization error: " + e.getMessage() + " | body: " + truncate(body, 300));
            }
        }
        return ApiResult.failure(status, body);
    }

    private <T> ApiResult<T> parseResponse(HttpResponse<String> response, TypeReference<T> responseType) {
        int status = response.statusCode();
        String body = response.body();
        if (status >= 200 && status < 300) {
            try {
                T data = mapper.readValue(body, responseType);
                return ApiResult.success(status, data);
            } catch (Exception e) {
                return ApiResult.failure(status, "Deserialization error: " + e.getMessage() + " | body: " + truncate(body, 300));
            }
        }
        return ApiResult.failure(status, body);
    }

    private static String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max) + "…";
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
