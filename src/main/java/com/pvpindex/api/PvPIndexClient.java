package com.pvpindex.api;

import com.pvpindex.api.endpoint.BattleClient;
import com.pvpindex.api.endpoint.FederationClient;
import com.pvpindex.api.endpoint.GameModeClient;
import com.pvpindex.api.endpoint.LeaderboardClient;
import com.pvpindex.api.endpoint.PlayerClient;
import com.pvpindex.api.endpoint.SeasonClient;
import com.pvpindex.api.endpoint.ServerClient;
import com.pvpindex.api.endpoint.VerificationClient;
import com.pvpindex.api.http.HttpTransport;

/**
 * Main entry point for the PvPIndex Java API client.
 *
 * <h2>Quick start</h2>
 * <pre>{@code
 * PvPIndexClient client = PvPIndexClient.of("https://api.pvpindex.com", "your-api-key");
 *
 * // Blocking
 * PlayerProfile profile = client.players().getPlayer("Notch");
 *
 * // Non-blocking
 * client.battles()
 *       .listBattlesAsync(BattleListParams.builder().perPage(10).build())
 *       .thenAccept(result -> {
 *           if (result.isOk()) result.getData().getData().forEach(b -> log(b.getUuid()));
 *       });
 * }</pre>
 *
 * <h2>Custom timeouts</h2>
 * <pre>{@code
 * PvPIndexClient client = PvPIndexClient.builder("https://api.pvpindex.com", "key")
 *     .connectTimeoutSeconds(3)
 *     .requestTimeoutSeconds(8)
 *     .build();
 * }</pre>
 */
public final class PvPIndexClient {

    private final BattleClient battles;
    private final PlayerClient players;
    private final LeaderboardClient leaderboards;
    private final ServerClient servers;
    private final GameModeClient gameModes;
    private final SeasonClient seasons;
    private final VerificationClient verification;
    private final FederationClient federation;

    private PvPIndexClient(PvPIndexClientConfig config) {
        HttpTransport transport = new HttpTransport(
            config.getBaseUrl(),
            config.getApiKey(),
            config.getConnectTimeout(),
            config.getRequestTimeout()
        );
        this.battles = new BattleClient(transport);
        this.players = new PlayerClient(transport);
        this.leaderboards = new LeaderboardClient(transport);
        this.servers = new ServerClient(transport);
        this.gameModes = new GameModeClient(transport);
        this.seasons = new SeasonClient(transport);
        this.verification = new VerificationClient(transport);
        this.federation = new FederationClient(transport);
    }

    /**
     * Creates a client with default timeouts (5s connect, 10s request).
     */
    public static PvPIndexClient of(String baseUrl, String apiKey) {
        return new PvPIndexClient(PvPIndexClientConfig.builder(baseUrl, apiKey).build());
    }

    /**
     * Creates a client from an explicit config object.
     */
    public static PvPIndexClient of(PvPIndexClientConfig config) {
        return new PvPIndexClient(config);
    }

    /**
     * Returns a builder for constructing a client with custom timeouts.
     */
    public static PvPIndexClientConfig.Builder builder(String baseUrl, String apiKey) {
        return PvPIndexClientConfig.builder(baseUrl, apiKey);
    }

    // --- Sub-client accessors ---

    public BattleClient battles() { return battles; }
    public PlayerClient players() { return players; }
    public LeaderboardClient leaderboards() { return leaderboards; }
    public ServerClient servers() { return servers; }
    public GameModeClient gameModes() { return gameModes; }
    public SeasonClient seasons() { return seasons; }
    public VerificationClient verification() { return verification; }
    public FederationClient federation() { return federation; }
}
