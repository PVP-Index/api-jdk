package com.pvpindex.api.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pvpindex.api.http.ApiResult;
import com.pvpindex.api.http.HttpTransport;
import com.pvpindex.api.model.battle.Battle;
import com.pvpindex.api.model.battle.BattleStatus;
import com.pvpindex.api.model.common.Page;
import com.pvpindex.api.request.BattleListParams;
import com.pvpindex.api.request.DisputeBattleRequest;
import com.pvpindex.api.request.SubmitBattleRequest;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BattleClientTest {

    private HttpServer server;
    private BattleClient client;
    private int port;
    private final ObjectMapper mapper = new ObjectMapper()
        .registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() throws IOException {
        server = HttpServer.create(new InetSocketAddress(0), 0);
        port = server.getAddress().getPort();
        server.start();
        HttpTransport transport = new HttpTransport(
            "http://localhost:" + port,
            "test-token",
            Duration.ofSeconds(3),
            Duration.ofSeconds(5)
        );
        client = new BattleClient(transport);
    }

    @AfterEach
    void tearDown() {
        server.stop(0);
    }

    private void serve(String path, int status, Object payload) throws Exception {
        byte[] body = mapper.writeValueAsBytes(payload);
        server.createContext(path, exchange -> {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(status, body.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body);
            }
        });
    }

    @Test
    void getBattle_deserializesBattle() throws Exception {
        Map<String, Object> battleMap = Map.of(
            "id", 1,
            "uuid", "test-uuid-1234",
            "status", "confirmed",
            "started_at", "2026-01-01T00:00:00Z",
            "ended_at", "2026-01-01T00:05:00Z"
        );
        serve("/api/battles/test-uuid-1234", 200, battleMap);

        Battle battle = client.getBattle("test-uuid-1234");

        assertEquals("test-uuid-1234", battle.getUuid());
        assertEquals(BattleStatus.CONFIRMED, battle.getStatus());
    }

    @Test
    void listBattles_deserializesPage() throws Exception {
        Map<String, Object> pageMap = Map.of(
            "data", List.of(Map.of("uuid", "b1", "status", "pending")),
            "current_page", 1,
            "last_page", 1,
            "per_page", 20,
            "total", 1
        );
        serve("/api/battles", 200, pageMap);

        Page<Battle> page = client.listBattles();

        assertEquals(1, page.getTotal());
        assertEquals("b1", page.getData().get(0).getUuid());
    }

    @Test
    void submitBattle_postsAndDeserializesResponse() throws Exception {
        Map<String, Object> battleMap = Map.of(
            "uuid", "new-battle-uuid",
            "status", "pending"
        );
        serve("/api/battles", 201, battleMap);

        SubmitBattleRequest request = SubmitBattleRequest
            .builder("new-battle-uuid", "server-1", "vanilla")
            .startedAt(Instant.now())
            .endedAt(Instant.now())
            .build();

        ApiResult<Battle> result = client.submitBattleAsync(request).join();
        assertTrue(result.isOk());
        assertEquals("new-battle-uuid", result.getData().getUuid());
    }

    @Test
    void confirmBattle_postsToConfirmPath() throws Exception {
        String[] capturedPath = new String[1];
        server.createContext("/api/battles/", exchange -> {
            capturedPath[0] = exchange.getRequestURI().getPath();
            byte[] body = mapper.writeValueAsBytes(Map.of("uuid", "uuid1", "status", "confirmed"));
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream os = exchange.getResponseBody()) { os.write(body); }
        });

        client.confirmBattle("uuid1");
        assertEquals("/api/battles/uuid1/confirm", capturedPath[0]);
    }

    @Test
    void disputeBattle_includesReason() throws Exception {
        byte[] capturedBody = new byte[1024];
        int[] capturedLen = {0};
        server.createContext("/api/battles/", exchange -> {
            capturedLen[0] = exchange.getRequestBody().read(capturedBody);
            byte[] body = mapper.writeValueAsBytes(Map.of("uuid", "uuid1", "status", "disputed"));
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream os = exchange.getResponseBody()) { os.write(body); }
        });

        client.disputeBattle("uuid1", new DisputeBattleRequest("hacking"));

        String sentBody = new String(capturedBody, 0, capturedLen[0]);
        assertTrue(sentBody.contains("hacking"));
    }

    @Test
    void listBattles_withParams_appendsQueryString() throws Exception {
        String[] capturedQuery = {null};
        byte[] body = mapper.writeValueAsBytes(Map.of(
            "data", List.of(),
            "current_page", 1, "last_page", 1, "per_page", 5, "total", 0
        ));
        server.createContext("/api/battles", exchange -> {
            capturedQuery[0] = exchange.getRequestURI().getQuery();
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream os = exchange.getResponseBody()) { os.write(body); }
        });

        client.listBattles(BattleListParams.builder().status("confirmed").perPage(5).build());

        assertNotNull(capturedQuery[0]);
        assertTrue(capturedQuery[0].contains("status=confirmed"));
        assertTrue(capturedQuery[0].contains("per_page=5"));
    }
}
