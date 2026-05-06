# pvpindex-api-client

Java 21 client library for the [PvPIndex](https://pvpindex.com) REST API.  
Published via **Jitpack** as `com.github.PVP-Index:api-jdk`.

---

## Installation

### Maven (recommended for plugins / server-side code)

Add the Jitpack repository and the dependency to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.PVP-Index</groupId>
        <artifactId>api-jdk</artifactId>
        <version>main-SNAPSHOT</version> <!-- or a specific tag -->
    </dependency>
</dependencies>
```

### Gradle

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.PVP-Index:api-jdk:main-SNAPSHOT'
}
```

---

## Quick start

```java
// One-line construction with default timeouts (5 s connect, 10 s request)
PvPIndexClient client = PvPIndexClient.of("https://api.pvpindex.com", "your-api-key");

// Blocking call — throws ApiException on non-2xx
PlayerProfile profile = client.players().getPlayer("Notch");
System.out.println(profile.getElo());   // current ELO across all modes

// Async call — CompletableFuture<ApiResult<T>>
client.battles()
      .listBattlesAsync(BattleListParams.builder().status("confirmed").perPage(10).build())
      .thenAccept(result -> {
          if (result.isOk()) {
              result.getData().getData().forEach(b -> System.out.println(b.getUuid()));
          } else {
              System.err.println("API error " + result.getStatusCode() + ": " + result.getErrorBody());
          }
      });
```

### Custom timeouts

```java
PvPIndexClient client = PvPIndexClient.builder("https://api.pvpindex.com", "key")
    .connectTimeoutSeconds(3)
    .requestTimeoutSeconds(8)
    .build();
```

---

## Submitting a battle (plugin use)

```java
SubmitBattleRequest request = SubmitBattleRequest
    .builder(battleUuid.toString(), serverId, "vanilla")
    .battleType("DUEL")
    .startedAt(startedAt)
    .endedAt(endedAt)
    .winners(List.of(winnerUuid.toString()))
    .losers(List.of(loserUuid.toString()))
    .participant(SubmitBattleRequest.Participant.builder(winnerUuid.toString(), "Steve")
        .result("winner").kills(3).deaths(0).build())
    .participant(SubmitBattleRequest.Participant.builder(loserUuid.toString(), "Alex")
        .result("loser").kills(0).deaths(3).build())
    .build();

Battle battle = client.battles().submitBattle(request);   // blocking
client.battles().confirmBattle(battle.getUuid());
```

---

## API surface

| Sub-client | Access | Key methods |
|---|---|---|
| `BattleClient` | `client.battles()` | `listBattles`, `getBattle`, `submitBattle`, `confirmBattle`, `disputeBattle`, `getBattleReplay` |
| `PlayerClient` | `client.players()` | `getPlayer`, `getPlayerRankings`, `getPlayerHistory` |
| `LeaderboardClient` | `client.leaderboards()` | `getLeaderboard`, `getLeaderboardForMode` |
| `ServerClient` | `client.servers()` | `listServers`, `getServer` |
| `GameModeClient` | `client.gameModes()` | `listGameModes`, `getGameMode` |
| `SeasonClient` | `client.seasons()` | `listSeasons`, `getSeason`, `getSeasonLeaderboard` |
| `VerificationClient` | `client.verification()` | `verifyMinecraft` |
| `FederationClient` | `client.federation()` | `fetchFederatedBans`, `publishFederatedBan` |

Every method has both a **blocking** variant (`T method(...)` — throws `ApiException` on non-2xx) and an **async** variant (`CompletableFuture<ApiResult<T>> methodAsync(...)`).

---

## Error handling

### Blocking style

```java
try {
    PlayerProfile p = client.players().getPlayer("unknown-player");
} catch (ApiException e) {
    if (e.isNotFound()) {
        // 404
    } else if (e.isRateLimited()) {
        // 429 — back off and retry
    }
}
```

### Async style

```java
client.players().getPlayerAsync("Notch").thenAccept(result -> {
    if (!result.isOk()) {
        if (result.getStatusCode() == 404) { /* not found */ }
        if (result.isRetryable())          { /* 5xx / network / 408 / 429 */ }
        return;
    }
    use(result.getData());
});
```

`ApiResult.isRetryable()` returns `true` for 5xx, 408, 429, and network failures (status code 0).

---

## Model classes

| Package | Types |
|---|---|
| `model.battle` | `Battle`, `BattleParticipant`, `BattleReplay`, `BattleStatus`, `ParticipantResult` |
| `model.player` | `PlayerProfile`, `PlayerRanking` |
| `model.leaderboard` | `LeaderboardEntry` |
| `model.server` | `Server` |
| `model.gamemode` | `GameMode` |
| `model.season` | `Season` |
| `model.moderation` | `BanEntry` |
| `model.common` | `Page<T>` |

All date/time fields are `java.time.Instant`. Unknown JSON properties are silently ignored.

---

## Requirements

- **Java 21+**
- No transitive runtime dependencies beyond **Jackson 2.18** (bundled by Jitpack; already present in most plugin environments)

---

## Building locally

```bash
cd apps/api-jdk
mvn clean package       # compile + test
mvn install -DskipTests # install to local ~/.m2 for use by other local modules
```

Tests use the JDK built-in `com.sun.net.httpserver.HttpServer` — no additional test infrastructure required.

---

## License

MIT — see [LICENSE](LICENSE).

