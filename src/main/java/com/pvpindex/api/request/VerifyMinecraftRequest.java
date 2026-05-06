package com.pvpindex.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request body for {@code POST /verify-minecraft}.
 */
public final class VerifyMinecraftRequest {

    @JsonProperty("minecraft_uuid")
    private final String minecraftUuid;

    @JsonProperty("claim_code")
    private final String claimCode;

    public VerifyMinecraftRequest(String minecraftUuid, String claimCode) {
        this.minecraftUuid = minecraftUuid;
        this.claimCode = claimCode;
    }

    public String getMinecraftUuid() { return minecraftUuid; }
    public String getClaimCode() { return claimCode; }
}
