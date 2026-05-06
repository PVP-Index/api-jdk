package com.pvpindex.api.http;

/**
 * Result of an API call. Wraps either a successfully-deserialized response body
 * or an error (HTTP status + raw response body).
 *
 * <p>Every async method on endpoint sub-clients returns
 * {@code CompletableFuture<ApiResult<T>>}. Every blocking wrapper returns
 * {@code T} directly and throws {@link ApiException} on non-2xx.</p>
 *
 * @param <T> the deserialized response type
 */
public final class ApiResult<T> {

    private final boolean ok;
    private final int statusCode;
    private final T data;
    private final String errorBody;

    private ApiResult(boolean ok, int statusCode, T data, String errorBody) {
        this.ok = ok;
        this.statusCode = statusCode;
        this.data = data;
        this.errorBody = errorBody;
    }

    // --- Factory methods ---

    public static <T> ApiResult<T> success(int statusCode, T data) {
        return new ApiResult<>(true, statusCode, data, null);
    }

    public static <T> ApiResult<T> failure(int statusCode, String errorBody) {
        return new ApiResult<>(false, statusCode, null, errorBody);
    }

    /** Network-level failure (connect timeout, DNS, etc.) — statusCode is 0. */
    public static <T> ApiResult<T> networkFailure(String message) {
        return new ApiResult<>(false, 0, null, message);
    }

    // --- Accessors ---

    public boolean isOk() {
        return ok;
    }

    public int getStatusCode() {
        return statusCode;
    }

    /** Non-null when {@link #isOk()} is true. */
    public T getData() {
        return data;
    }

    /** Non-null when {@link #isOk()} is false. */
    public String getErrorBody() {
        return errorBody;
    }

    /**
     * Whether this failure is safe to retry.
     * 5xx, 408 (Request Timeout), 429 (Rate Limited), and network errors (statusCode == 0)
     * are considered retryable. 4xx client errors are not.
     */
    public boolean isRetryable() {
        if (ok) return false;
        return statusCode == 0
            || statusCode == 408
            || statusCode == 429
            || statusCode >= 500;
    }

    /**
     * Returns the data if successful, or throws {@link ApiException} otherwise.
     */
    public T orElseThrow() {
        if (!ok) {
            throw new ApiException(statusCode, errorBody);
        }
        return data;
    }

    @Override
    public String toString() {
        if (ok) {
            return "ApiResult{ok=true, statusCode=" + statusCode + "}";
        }
        String body = errorBody != null && errorBody.length() > 200
            ? errorBody.substring(0, 200) + "…"
            : errorBody;
        return "ApiResult{ok=false, statusCode=" + statusCode + ", errorBody=" + body + "}";
    }
}
