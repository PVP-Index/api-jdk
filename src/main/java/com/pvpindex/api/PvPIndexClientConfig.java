package com.pvpindex.api;

import java.time.Duration;

/**
 * Immutable configuration for {@link PvPIndexClient}.
 */
public final class PvPIndexClientConfig {

    public static final int DEFAULT_CONNECT_TIMEOUT_SECONDS = 5;
    public static final int DEFAULT_REQUEST_TIMEOUT_SECONDS = 10;

    private final String baseUrl;
    private final String apiKey;
    private final Duration connectTimeout;
    private final Duration requestTimeout;

    private PvPIndexClientConfig(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.apiKey = builder.apiKey;
        this.connectTimeout = builder.connectTimeout;
        this.requestTimeout = builder.requestTimeout;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    public Duration getRequestTimeout() {
        return requestTimeout;
    }

    public static Builder builder(String baseUrl, String apiKey) {
        return new Builder(baseUrl, apiKey);
    }

    public static final class Builder {
        private final String baseUrl;
        private final String apiKey;
        private Duration connectTimeout = Duration.ofSeconds(DEFAULT_CONNECT_TIMEOUT_SECONDS);
        private Duration requestTimeout = Duration.ofSeconds(DEFAULT_REQUEST_TIMEOUT_SECONDS);

        private Builder(String baseUrl, String apiKey) {
            if (baseUrl == null || baseUrl.isBlank()) throw new IllegalArgumentException("baseUrl must not be blank");
            if (apiKey == null || apiKey.isBlank()) throw new IllegalArgumentException("apiKey must not be blank");
            this.baseUrl = baseUrl;
            this.apiKey = apiKey;
        }

        public Builder connectTimeout(Duration timeout) {
            this.connectTimeout = timeout;
            return this;
        }

        public Builder connectTimeoutSeconds(int seconds) {
            return connectTimeout(Duration.ofSeconds(seconds));
        }

        public Builder requestTimeout(Duration timeout) {
            this.requestTimeout = timeout;
            return this;
        }

        public Builder requestTimeoutSeconds(int seconds) {
            return requestTimeout(Duration.ofSeconds(seconds));
        }

        public PvPIndexClientConfig build() {
            return new PvPIndexClientConfig(this);
        }
    }
}
