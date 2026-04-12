package com.app.quantitymeasurement.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.LengthUnit;
import com.app.quantitymeasurement.unit.TemperatureUnit;
import com.app.quantitymeasurement.unit.VolumeUnit;
import com.app.quantitymeasurement.unit.WeightUnit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of IQuantityMeasurementService.
 * Handles the logic for unit conversions, comparisons, and arithmetic operations
 * while persisting each operation to the database via QuantityMeasurementRepository.
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final double COMPARISON_TOLERANCE = 0.0001;

    // Cache key prefixes
    private static final String CACHE_KEY_HISTORY_OPERATION = "history:operation:%s:%s";
    private static final String CACHE_KEY_HISTORY_TYPE = "history:type:%s:%s";
    private static final String CACHE_KEY_COUNT_OPERATION = "count:operation:%s:%s";
    private static final String CACHE_KEY_ERRORS = "history:errors:%s";

    private final QuantityMeasurementRepository repository;


    private Long getCurrentUserIdRaw() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug("getCurrentUserIdRaw - Authentication: {}", auth);
        if (auth != null && auth.isAuthenticated() && 
            !(auth.getPrincipal() instanceof String && "anonymousUser".equals(auth.getPrincipal()))) {
            // Try to get user ID from JWT token or custom principal
            Object principal = auth.getPrincipal();
            log.debug("getCurrentUserIdRaw - Principal: {} (type: {})", principal, principal != null ? principal.getClass().getName() : "null");
            
            if (principal instanceof Long) {
                log.debug("getCurrentUserIdRaw - Found Long principal: {}", principal);
                return (Long) principal;
            } else if (principal instanceof String) {
                // If principal is a string (username), we need to get the user ID
                // For now, return null to prevent saving history for string-based auth
                log.debug("getCurrentUserIdRaw - String principal, returning null");
                return null;
            } else {
                // Custom principal object - try to extract ID
                try {
                    // Try to get ID from custom principal
                    java.lang.reflect.Method getIdMethod = principal.getClass().getMethod("getId");
                    Object id = getIdMethod.invoke(principal);
                    if (id instanceof Long) {
                        log.debug("getCurrentUserIdRaw - Found ID via reflection: {}", id);
                        return (Long) id;
                    }
                } catch (Exception e) {
                    log.debug("getCurrentUserIdRaw - Reflection error: {}", e.getMessage());
                }
            }
        }
        log.debug("getCurrentUserIdRaw - No valid user found, returning null");
        return null;
    }

    private boolean isUserAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() && 
               !(auth.getPrincipal() instanceof String && "anonymousUser".equals(auth.getPrincipal()));
    }


    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }
        return false;
    }

    // Get current user ID for cache keys
    private String getCurrentUserId() {
        Long userId = getCurrentUserIdRaw();
        return userId != null ? String.valueOf(userId) : "anonymous";
    }


    // Caching disabled (Redis not available)
    private void evictCache(String key) {
        log.debug("Cache eviction skipped (Redis not available) for key: {}", key);
    }

    private void evictCachesForType(String measurementType) {
        String userId = getCurrentUserId();
        evictCache(String.format(CACHE_KEY_HISTORY_TYPE, userId, measurementType));
        evictCache(String.format(CACHE_KEY_ERRORS, userId));

        if (isAdmin()) {
            evictCache(String.format(CACHE_KEY_HISTORY_TYPE, "all", measurementType));
            evictCache(String.format(CACHE_KEY_ERRORS, "all"));
        }
    }

    private void evictCachesForOperation(String operation) {
        String userId = getCurrentUserId();
        evictCache(String.format(CACHE_KEY_HISTORY_OPERATION, userId, operation));
        evictCache(String.format(CACHE_KEY_COUNT_OPERATION, userId, operation));
        evictCache(String.format(CACHE_KEY_ERRORS, userId));

        if (isAdmin()) {
            evictCache(String.format(CACHE_KEY_HISTORY_OPERATION, "all", operation));
            evictCache(String.format(CACHE_KEY_COUNT_OPERATION, "all", operation));
            evictCache(String.format(CACHE_KEY_ERRORS, "all"));
        }
    }

    // ================== COMPARE ==================
    @Override
    public QuantityMeasurementDTO compare(QuantityDTO q1, QuantityDTO q2) {

        log.debug("compare() called with q1: {} {}, q2: {} {}", 
                 q1.getValue(), q1.getUnit(), q2.getValue(), q2.getUnit());

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        boolean result = performComparison(q1, q2);

        entity.setThisValue(q1.getValue());
        entity.setThisUnit(q1.getUnit());
        entity.setThisMeasurementType(q1.getMeasurementType());

        entity.setThatValue(q2.getValue());
        entity.setThatUnit(q2.getUnit());
        entity.setThatMeasurementType(q2.getMeasurementType());

        entity.setOperation("COMPARE");
        entity.setResultString(result ? "true" : "false");
        entity.setError(false);
        
        // Only save history if user is authenticated
        boolean isAuthenticated = isUserAuthenticated();
        log.debug("compare() - isUserAuthenticated: {}", isAuthenticated);
        
        if (isAuthenticated) {
            Long userId = getCurrentUserIdRaw();
            log.debug("compare() - userId: {}", userId);
            entity.setUserId(userId);
            repository.save(entity);

            // Invalidate relevant caches
            evictCachesForOperation("COMPARE");
            evictCachesForType(q1.getMeasurementType());
        } else {
            log.debug("compare() - Skipping history save - user not authenticated");
        }

        
        return QuantityMeasurementDTO.from(entity);
    }

    // ================== CONVERT ==================
    @Override
    public QuantityMeasurementDTO convert(QuantityDTO quantity, String targetUnit) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        double result = convertValue(quantity, targetUnit);

        entity.setThisValue(quantity.getValue());
        entity.setThisUnit(quantity.getUnit());
        entity.setThisMeasurementType(quantity.getMeasurementType());

        entity.setThatValue(0.0);
        entity.setThatUnit("N/A");
        entity.setThatMeasurementType("N/A");

        entity.setOperation("CONVERT");
        entity.setResultValue(result);
        entity.setResultUnit(targetUnit);
        entity.setResultMeasurementType(quantity.getMeasurementType());
        entity.setError(false);
        
        // Only save history if user is authenticated
        if (isUserAuthenticated()) {
            Long userId = getCurrentUserIdRaw();
            entity.setUserId(userId);
            repository.save(entity);

            // Invalidate relevant caches
            evictCachesForOperation("CONVERT");
            evictCachesForType(quantity.getMeasurementType());
        }

        
        return QuantityMeasurementDTO.from(entity);
    }

    // ================== ADD ==================
    @Override
    public QuantityMeasurementDTO add(QuantityDTO q1, QuantityDTO q2) {
        QuantityMeasurementDTO result = performArithmetic(q1, q2, null, "ADD", Double::sum);
        // Invalidate relevant caches
        evictCachesForOperation("ADD");
        evictCachesForType(q1.getMeasurementType());
        return result;
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO q1, QuantityDTO q2, QuantityDTO target) {
        QuantityMeasurementDTO result = performArithmetic(q1, q2, target, "ADD", Double::sum);
        // Invalidate relevant caches
        evictCachesForOperation("ADD");
        evictCachesForType(q1.getMeasurementType());
        return result;
    }

    // ================== SUBTRACT ==================
    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO q1, QuantityDTO q2) {
        QuantityMeasurementDTO result = performArithmetic(q1, q2, null, "SUBTRACT", (a, b) -> a - b);
        // Invalidate relevant caches
        evictCachesForOperation("SUBTRACT");
        evictCachesForType(q1.getMeasurementType());
        return result;
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO q1, QuantityDTO q2, QuantityDTO target) {
        QuantityMeasurementDTO result = performArithmetic(q1, q2, target, "SUBTRACT", (a, b) -> a - b);
        // Invalidate relevant caches
        evictCachesForOperation("SUBTRACT");
        evictCachesForType(q1.getMeasurementType());
        return result;
    }

    // ================== MULTIPLY ==================
    @Override
    public QuantityMeasurementDTO multiply(QuantityDTO q, double factor) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        IMeasurable u1 = getUnitEnum(q.getMeasurementType(), q.getUnit());
        u1.validateOperationSupport("MULTIPLY");
        double result = q.getValue() * factor;

        entity.setThisValue(q.getValue());
        entity.setThisUnit(q.getUnit());
        entity.setThisMeasurementType(q.getMeasurementType());

        entity.setThatValue(factor);
        entity.setThatUnit("FACTOR");
        entity.setThatMeasurementType("Scalar");

        entity.setOperation("MULTIPLY");
        entity.setResultValue(result);
        entity.setResultUnit(q.getUnit());
        entity.setResultMeasurementType(q.getMeasurementType());
        entity.setError(false);
        
        // Only save history if user is authenticated
        if (isUserAuthenticated()) {
            Long userId = getCurrentUserIdRaw();
            entity.setUserId(userId);
            repository.save(entity);

            // Invalidate relevant caches
            evictCachesForOperation("MULTIPLY");
            evictCachesForType(q.getMeasurementType());
        }
        
        return QuantityMeasurementDTO.from(entity);
    }

    // ================== DIVIDE ==================
    @Override
    public QuantityMeasurementDTO divide(QuantityDTO q, double divisor) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        if (divisor == 0) {
            throw new ArithmeticException("Divide by zero");
        }
        IMeasurable u1 = getUnitEnum(q.getMeasurementType(), q.getUnit());
        u1.validateOperationSupport("DIVIDE");
        double result = q.getValue() / divisor;

        entity.setThisValue(q.getValue());
        entity.setThisUnit(q.getUnit());
        entity.setThisMeasurementType(q.getMeasurementType());

        entity.setThatValue(divisor);
        entity.setThatUnit("FACTOR");
        entity.setThatMeasurementType("Scalar");

        entity.setOperation("DIVIDE");
        entity.setResultValue(result);
        entity.setResultUnit(q.getUnit());
        entity.setResultMeasurementType(q.getMeasurementType());
        entity.setError(false);
        
        // Only save history if user is authenticated
        if (isUserAuthenticated()) {
            Long userId = getCurrentUserIdRaw();
            entity.setUserId(userId);
            repository.save(entity);

            // Invalidate relevant caches
            evictCachesForOperation("DIVIDE");
            evictCachesForType(q.getMeasurementType());
        }
        
        return QuantityMeasurementDTO.from(entity);
    }

    // ================== DIVIDE (RATIO) ==================
    @Override
    public QuantityMeasurementDTO divide(QuantityDTO q1, QuantityDTO q2) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        if (!q1.getMeasurementType().equals(q2.getMeasurementType())) {
            throw new IllegalArgumentException("Cannot divide different measurement types");
        }

        IMeasurable u1 = getUnitEnum(q1.getMeasurementType(), q1.getUnit());
        IMeasurable u2 = getUnitEnum(q2.getMeasurementType(), q2.getUnit());

        double base1 = u1.convertToBaseUnit(q1.getValue());
        double base2 = u2.convertToBaseUnit(q2.getValue());

        if (base2 == 0) {
            throw new ArithmeticException("Divide by zero");
        }

        double ratio = base1 / base2;

        entity.setThisValue(q1.getValue());
        entity.setThisUnit(q1.getUnit());
        entity.setThisMeasurementType(q1.getMeasurementType());

        entity.setThatValue(q2.getValue());
        entity.setThatUnit(q2.getUnit());
        entity.setThatMeasurementType(q2.getMeasurementType());

        entity.setOperation("DIVIDE");
        entity.setResultValue(ratio);
        entity.setResultUnit("RATIO");
        entity.setResultMeasurementType("Scalar");
        entity.setError(false);
        
        // Only save history if user is authenticated
        if (isUserAuthenticated()) {
            Long userId = getCurrentUserIdRaw();
            entity.setUserId(userId);
            repository.save(entity);

            // Invalidate relevant caches
            evictCachesForOperation("DIVIDE");
            evictCachesForType(q1.getMeasurementType());
        }
        
        return QuantityMeasurementDTO.from(entity);
    }

    // ================== HISTORY ==================
    @Override
    public List<QuantityMeasurementDTO> getOperationHistory(String operation) {
        // Cache miss - fetch from database
        List<QuantityMeasurementDTO> result;
        if (isAdmin()) {
            result = QuantityMeasurementDTO.fromList(repository.findByOperation(operation));
        } else {
            result = QuantityMeasurementDTO.fromList(
                    repository.findByUserIdAndOperation(getCurrentUserIdRaw(), operation)
            );
        }

        return result;
    }

    @Override
    public List<QuantityMeasurementDTO> getMeasurementsByType(String type) {
        // Cache miss - fetch from database
        List<QuantityMeasurementDTO> result;
        if (isAdmin()) {
            result = QuantityMeasurementDTO.fromList(repository.findByThisMeasurementType(type));
        } else {
            result = QuantityMeasurementDTO.fromList(
                    repository.findByUserIdAndThisMeasurementType(getCurrentUserIdRaw(), type)
            );
        }

        return result;
    }

    @Override
    public long getOperationCount(String operation) {
        // Cache miss - fetch from database
        long count;
        if (isAdmin()) {
            count = repository.countByOperationAndIsErrorFalse(operation);
        } else {
            count = repository.countByUserIdAndOperationAndIsErrorFalse(getCurrentUserIdRaw(), operation);
        }

        return count;
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        // Cache miss - fetch from database
        List<QuantityMeasurementDTO> result;
        if (isAdmin()) {
            result = QuantityMeasurementDTO.fromList(repository.findByIsErrorTrue());
        } else {
            result = QuantityMeasurementDTO.fromList(
                    repository.findByUserIdAndIsErrorTrue(getCurrentUserIdRaw())
            );
        }

        return result;
    }

    @Override
    @Transactional
    public void clearHistoryByType(String measurementType) {
        log.info("clearHistoryByType() called for type: {}", measurementType);
        
        if (isAdmin()) {
            log.info("Admin clearing all history for type: {}", measurementType);
            repository.deleteByThisMeasurementType(measurementType);
        } else {
            Long userId = getCurrentUserIdRaw();
            log.info("User {} clearing history for type: {}", userId, measurementType);
            if (userId != null) {
                repository.deleteByUserIdAndThisMeasurementType(userId, measurementType);
            }
        }
        
        // Invalidate relevant caches
        evictCachesForType(measurementType);
        // Also evict general operation caches as we don't know which operations were deleted
        evictCachesForOperation("CONVERT");
        evictCachesForOperation("COMPARE");
        evictCachesForOperation("ADD");
        evictCachesForOperation("SUBTRACT");
        evictCachesForOperation("MULTIPLY");
        evictCachesForOperation("DIVIDE");
    }

    // ================== HELPER METHODS ==================

    private IMeasurable getUnitEnum(String measurementType, String unitStr) {
        switch (measurementType) {
            case "LengthUnit": return LengthUnit.valueOf(unitStr);
            case "VolumeUnit": return VolumeUnit.valueOf(unitStr);
            case "WeightUnit": return WeightUnit.valueOf(unitStr);
            case "TemperatureUnit": return TemperatureUnit.valueOf(unitStr);
            default: throw new IllegalArgumentException("Unknown measurement type: " + measurementType);
        }
    }

    private boolean performComparison(QuantityDTO q1, QuantityDTO q2) {
        if (!q1.getMeasurementType().equals(q2.getMeasurementType())) {
            throw new IllegalArgumentException("Cannot compare different measurement types");
        }
        IMeasurable u1 = getUnitEnum(q1.getMeasurementType(), q1.getUnit());
        IMeasurable u2 = getUnitEnum(q2.getMeasurementType(), q2.getUnit());
        
        double val1 = u1.convertToBaseUnit(q1.getValue());
        double val2 = u2.convertToBaseUnit(q2.getValue());
        
        return Math.abs(val1 - val2) < COMPARISON_TOLERANCE;
    }

    private double convertValue(QuantityDTO q, String targetUnitStr) {
        IMeasurable fromUnit = getUnitEnum(q.getMeasurementType(), q.getUnit());
        IMeasurable toUnit = getUnitEnum(q.getMeasurementType(), targetUnitStr);
        
        double baseValue = fromUnit.convertToBaseUnit(q.getValue());
        return toUnit.convertFromBaseUnit(baseValue);
    }

    private QuantityMeasurementDTO performArithmetic(
            QuantityDTO q1,
            QuantityDTO q2,
            QuantityDTO target,
            String operation,
            java.util.function.DoubleBinaryOperator operator) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        if (!q1.getMeasurementType().equals(q2.getMeasurementType())) {
            throw new IllegalArgumentException("Cannot perform arithmetic on different measurement types");
        }

        IMeasurable u1 = getUnitEnum(q1.getMeasurementType(), q1.getUnit());
        u1.validateOperationSupport(operation);
        IMeasurable u2 = getUnitEnum(q2.getMeasurementType(), q2.getUnit());

        double base1 = u1.convertToBaseUnit(q1.getValue());
        double base2 = u2.convertToBaseUnit(q2.getValue());

        double resultInBase = operator.applyAsDouble(base1, base2);

        String resultUnitStr = target != null ? target.getUnit() : q1.getUnit();
        IMeasurable resultUnitEnum = getUnitEnum(q1.getMeasurementType(), resultUnitStr);

        double finalResult = resultUnitEnum.convertFromBaseUnit(resultInBase);

        entity.setThisValue(q1.getValue());
        entity.setThisUnit(q1.getUnit());
        entity.setThisMeasurementType(q1.getMeasurementType());

        entity.setThatValue(q2.getValue());
        entity.setThatUnit(q2.getUnit());
        entity.setThatMeasurementType(q2.getMeasurementType());

        entity.setOperation(operation);
        entity.setResultValue(finalResult);
        entity.setResultUnit(resultUnitStr);
        entity.setResultMeasurementType(q1.getMeasurementType());
        entity.setError(false);
        
        // Only save history if user is authenticated
        if (isUserAuthenticated()) {
            Long userId = getCurrentUserIdRaw();
            entity.setUserId(userId);
            repository.save(entity);
        }

        return QuantityMeasurementDTO.from(entity);
    }
}