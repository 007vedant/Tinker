package com.tinker.rateLimiter.algorithms;

/**
 * Interface to define behaviour of rate limiters.
 */
public interface RateLimiter {
    boolean allowRequest();
}
