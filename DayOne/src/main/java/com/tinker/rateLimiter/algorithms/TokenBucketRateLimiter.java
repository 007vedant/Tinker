package com.tinker.rateLimiter.algorithms;

import java.time.Instant;

/**
 * Rate Limiter implementation using token bucket algorithm.
 */
public class TokenBucketRateLimiter implements RateLimiter {
    private final int maxTokens;
    private final int fillRate;
    private int remainingTokens;
    private Instant lastReFillTime;

    public TokenBucketRateLimiter(int maxTokens, int fillRate) {
        this.maxTokens = maxTokens;
        this.fillRate = fillRate;
        this.remainingTokens = maxTokens;
        this.lastReFillTime = Instant.now();
    }

    @Override
    public synchronized boolean allowRequest() {
        refillTokens();
        if (remainingTokens > 0) {
            remainingTokens -= 1; // assuming 1 request consumes 1 token
            return true;
        }
        return false;
    }

    private void refillTokens() {
        Instant now = Instant.now();
        long timeElapsed = now.toEpochMilli() - lastReFillTime.toEpochMilli();
        if (timeElapsed > 0) {
            int tokensToFill = (int) ((timeElapsed * fillRate) / 1000.0);
            remainingTokens = Math.min(maxTokens, remainingTokens + tokensToFill);
            lastReFillTime = now;
        }
    }
}
