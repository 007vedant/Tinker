package com.tinker.rateLimiter.algorithms;

import java.util.concurrent.TimeUnit;

/**
 * Rate Limiter implementation using token bucket algorithm.
 */
public class TokenBucketRateLimiter implements RateLimiter {
    private final int maxTokens;
    private final int fillRate;
    private int remainingTokens;
    private long lastReFillTime;

    public TokenBucketRateLimiter(int maxTokens, int fillRate) {
        this.maxTokens = maxTokens;
        this.fillRate = fillRate;
        this.remainingTokens = maxTokens;
        this.lastReFillTime = System.currentTimeMillis();
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
        long now = System.currentTimeMillis();
        long timeElapsed = TimeUnit.MILLISECONDS.toSeconds(now - lastReFillTime);
        if (timeElapsed > 0) {
            int tokensToFill = (int) timeElapsed * fillRate;
            remainingTokens = Math.min(maxTokens, remainingTokens + tokensToFill);
            lastReFillTime = now;
        }
    }
}
