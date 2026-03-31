package com.app.quantitymeasurement.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis-backed token blacklist for implementing logout / token invalidation.
 *
 * <p>When a user logs out, the JTI (JWT ID) of their current access token
 * is added to this blacklist with a TTL matching its original expiration time. 
 * The {@link com.app.quantitymeasurement.security.JwtAuthenticationFilter}
 * checks this list before accepting any token.</p>
 */
@Service
@Slf4j
public class TokenBlacklistService {

    private static final String BLACKLIST_PREFIX = "jwt:blacklist:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Adds a JTI to the blacklist (called on logout) and sets its expiration
     * time so that Redis automatically evicts it when the JWT naturally expires.
     *
     * @param jti the JWT ID to blacklist
     * @param ttlMs the remaining time to live in milliseconds
     */
    public void blacklist(String jti, long ttlMs) {
        if (ttlMs > 0) {
            redisTemplate.opsForValue().set(BLACKLIST_PREFIX + jti, "true", ttlMs, TimeUnit.MILLISECONDS);
            log.info("Token blacklisted in Redis. JTI: {} with TTL {} ms", jti, ttlMs);
        }
    }

    /**
     * Checks whether a JTI has been blacklisted.
     *
     * @param jti the JWT ID to check
     * @return true if blacklisted (token is invalid), false if still valid
     */
    public boolean isBlacklisted(String jti) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_PREFIX + jti));
    }
}
