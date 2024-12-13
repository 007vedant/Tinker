package com.tinker.rateLimiter.algorithms;

import java.time.Instant;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Rate Limiter implementation using leaky bucket algorithm.
 */
public class LeakyBuckerRateLimiter implements RateLimiter {
    private final int bucketSize;
    private final int leakRate;
    private final Deque<Instant> bucket;
    private Instant lastLeakTime;

    public LeakyBuckerRateLimiter(int bucketSize, int leakRate) {
        this.bucketSize = bucketSize;
        this.leakRate = leakRate;
        this.bucket = new LinkedList<>();
        this.lastLeakTime = Instant.now();
    }

    @Override
    public boolean allowRequest() {
        leak();
        if (bucket.size() < bucketSize) {
            bucket.offer(Instant.now());
            return true;
        }

        return false;
    }

    private void leak() {
        Instant now = Instant.now();
        long timeElapsed = now.toEpochMilli() - lastLeakTime.toEpochMilli();
        int leaked = (int) ((timeElapsed * leakRate) / 1000.0);

        for (int i = 0; i < leaked && !bucket.isEmpty(); i++) {
            bucket.poll();
        }
        lastLeakTime = now;
    }
}
