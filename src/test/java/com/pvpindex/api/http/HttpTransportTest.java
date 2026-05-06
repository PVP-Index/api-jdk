package com.pvpindex.api.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpTransportTest {

    private HttpServer server;
    private HttpTransport transport;
    private int port;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        server = HttpServer.create(new InetSocketAddress(0), 0);
        port = server.getAddress().getPort();
        server.start();
        transport = new HttpTransport(
            "http://localhost:" + port,
            "test-token",
            Duration.ofSeconds(3),
            Duration.ofSeconds(5)
        );
    }

    @AfterEach
    void tearDown() {
        server.stop(0);
    }

    @Test
    void get_success_deserializesBody() throws Exception {
        Map<String, String> payload = Map.of("key", "value");
        byte[] body = mapper.writeValueAsBytes(payload);
        server.createContext("/api/test", exchange -> {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body);
            }
        });

        ApiResult<Map> result = transport.getAsync("/api/test", Map.class).join();

        assertTrue(result.isOk());
        assertEquals(200, result.getStatusCode());
        assertEquals("value", result.<Map<String, String>>getData().get("key"));
    }

    @Test
    void get_notFound_returnsFailure() throws Exception {
        server.createContext("/api/missing", exchange -> {
            byte[] body = "{\"message\":\"not found\"}".getBytes();
            exchange.sendResponseHeaders(404, body.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body);
            }
        });

        ApiResult<Map> result = transport.getAsync("/api/missing", Map.class).join();

        assertFalse(result.isOk());
        assertEquals(404, result.getStatusCode());
        assertNotNull(result.getErrorBody());
    }

    @Test
    void get_sends_authorizationHeader() throws Exception {
        String[] capturedAuth = new String[1];
        server.createContext("/api/auth-check", exchange -> {
            capturedAuth[0] = exchange.getRequestHeaders().getFirst("Authorization");
            byte[] body = "{}".getBytes();
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body);
            }
        });

        transport.getAsync("/api/auth-check", Map.class).join();

        assertEquals("Bearer test-token", capturedAuth[0]);
    }

    @Test
    void post_serializesBodyAsJson() throws Exception {
        byte[] captured = new byte[1024];
        int[] capturedLen = {0};
        server.createContext("/api/post-test", exchange -> {
            capturedLen[0] = exchange.getRequestBody().read(captured);
            byte[] body = "{\"ok\":true}".getBytes();
            exchange.sendResponseHeaders(201, body.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body);
            }
        });

        Map<String, String> requestBody = Map.of("foo", "bar");
        ApiResult<Map> result = transport.postAsync("/api/post-test", requestBody, Map.class).join();

        assertTrue(result.isOk());
        assertEquals(201, result.getStatusCode());
        String sentBody = new String(captured, 0, capturedLen[0]);
        assertTrue(sentBody.contains("foo"));
        assertTrue(sentBody.contains("bar"));
    }

    @Test
    void apiResult_retryable_onServerError() {
        ApiResult<String> result = ApiResult.failure(503, "Service Unavailable");
        assertTrue(result.isRetryable());
    }

    @Test
    void apiResult_retryable_onRateLimit() {
        ApiResult<String> result = ApiResult.failure(429, "Too Many Requests");
        assertTrue(result.isRetryable());
    }

    @Test
    void apiResult_notRetryable_onClientError() {
        ApiResult<String> result = ApiResult.failure(422, "Unprocessable Entity");
        assertFalse(result.isRetryable());
    }

    @Test
    void apiResult_retryable_onNetworkFailure() {
        ApiResult<String> result = ApiResult.networkFailure("Connection refused");
        assertTrue(result.isRetryable());
        assertEquals(0, result.getStatusCode());
    }

    @Test
    void apiResult_orElseThrow_throwsOnFailure() {
        ApiResult<String> result = ApiResult.failure(401, "Unauthorized");
        ApiException ex = assertThrows(ApiException.class, result::orElseThrow);
        assertEquals(401, ex.getStatusCode());
        assertTrue(ex.isUnauthorized());
    }

    @Test
    void apiResult_orElseThrow_returnsDataOnSuccess() {
        ApiResult<String> result = ApiResult.success(200, "hello");
        assertEquals("hello", result.orElseThrow());
    }
}
