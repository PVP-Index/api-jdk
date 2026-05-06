package com.pvpindex.api.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pvpindex.api.http.HttpTransport;
import com.pvpindex.api.model.player.PlayerProfile;
import com.pvpindex.api.model.player.PlayerRanking;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerClientTest {

    private HttpServer server;
    private PlayerClient client;
    private int port;
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() throws IOException {
        server = HttpServer.create(new InetSocketAddress(0), 0);
        port = server.getAddress().getPort();
        server.start();
        HttpTransport transport = new HttpTransport(
            "http://localhost:" + port, "token",
            Duration.ofSeconds(3), Duration.ofSeconds(5)
        );
        client = new PlayerClient(transport);
    }

    @AfterEach
    void tearDown() { server.stop(0); }

    private void serve(String path, int status, Object payload) throws Exception {
        byte[] body = mapper.writeValueAsBytes(payload);
        server.createContext(path, exchange -> {
            exchange.sendResponseHeaders(status, body.length);
            try (OutputStream os = exchange.getResponseBody()) { os.write(body); }
        });
    }

    @Test
    void getPlayer_deserializesProfile() throws Exception {
        serve("/api/players/Notch", 200, Map.of(
            "id", 42, "username", "Notch", "verified", true,
            "is_banned", false, "global_rank", 1, "peak_elo", 1800,
            "wins", 100, "losses", 10, "draws", 5
        ));

        PlayerProfile p = client.getPlayer("Notch");

        assertEquals(42, p.getId());
        assertEquals("Notch", p.getUsername());
        assertTrue(p.isVerified());
        assertFalse(p.isBanned());
        assertEquals(1800, p.getPeakElo());
    }

    @Test
    void getPlayerRankings_deserializesList() throws Exception {
        serve("/api/players/Notch/rankings", 200, List.of(
            Map.of("game_mode", "vanilla", "elo", 1600, "rank", 3,
                   "wins", 50, "losses", 5, "draws", 2, "matches_played", 57,
                   "win_rate", 87.7, "peak_elo", 1650, "derived_rank", "Diamond")
        ));

        List<PlayerRanking> rankings = client.getPlayerRankings("Notch");

        assertEquals(1, rankings.size());
        assertEquals("vanilla", rankings.get(0).getGameMode());
        assertEquals(1600, rankings.get(0).getElo());
        assertEquals("Diamond", rankings.get(0).getDerivedRank());
    }
}
