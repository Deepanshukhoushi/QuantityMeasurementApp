/**
 * QuantityMeasurementDatabaseRepository.java
 *
 * This class implements the IQuantityMeasurementRepository interface and provides methods
 * to interact with a relational database for storing and retrieving quantity measurement
 * data. It uses JDBC for database operations and a connection pool for efficient resource
 * management. The repository handles CRUD operations for QuantityMeasurementEntity objects
 * and includes methods to query measurements by operation type and measurement type. It
 * also includes error handling using a custom DatabaseException to encapsulate any
 * database-related issues. The repository is designed to be used by the service layer of
 * the application to persist the results of quantity measurement operations and to
 * retrieve historical data for analysis and reporting. It ensures that database connections
 * are properly managed and that resources are released after use to prevent leaks and ensure
 * optimal performance.
 *
 * @author Developer
 * @version 16.0
 * @since 16.0
 */
package com.app.quantitymeasurement.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.util.ConnectionPool;

public class QuantityMeasurementDatabaseRepository
        implements IQuantityMeasurementRepository {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementDatabaseRepository.class.getName());

    private static final String INSERT_QUERY =
            "INSERT INTO quantity_measurement_entity " +
            "(this_value,this_unit,this_measurement_type,that_value,that_unit,that_measurement_type,operation,result_value,result_unit,result_measurement_type,result_string,is_error,error_message) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SELECT_ALL_QUERY =
            "SELECT * FROM quantity_measurement_entity";

    private static final String SELECT_BY_OPERATION_QUERY =
            "SELECT * FROM quantity_measurement_entity WHERE operation = ?";

    private static final String SELECT_BY_TYPE_QUERY =
            "SELECT * FROM quantity_measurement_entity WHERE this_measurement_type = ?";

    @Override
    public void save(QuantityMeasurementEntity entity) {

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_QUERY)) {

            statement.setDouble(1, entity.getThisValue());
            statement.setString(2, entity.getThisUnit());
            statement.setString(3, entity.getThisMeasurementType());
            statement.setDouble(4, entity.getThatValue());
            statement.setString(5, entity.getThatUnit());
            statement.setString(6, entity.getThatMeasurementType());
            statement.setString(7, entity.getOperation());
            statement.setDouble(8, entity.getResultValue());
            statement.setString(9, entity.getResultUnit());
            statement.setString(10, entity.getResultMeasurementType());
            statement.setString(11, entity.getResultString());
            statement.setBoolean(12, entity.isError());
            statement.setString(13, entity.getErrorMessage());

            statement.executeUpdate();

            logger.info("Measurement saved to database");

        } catch (SQLException e) {
            throw DatabaseException.queryFailed("Insert measurement", e);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {

        List<QuantityMeasurementEntity> list = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SELECT_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                QuantityMeasurementEntity entity =
                        new QuantityMeasurementEntity();

                entity.setId(resultSet.getLong("id"));
                entity.setThisValue(resultSet.getDouble("this_value"));
                entity.setThisUnit(resultSet.getString("this_unit"));
                entity.setThisMeasurementType(resultSet.getString("this_measurement_type"));
                entity.setOperation(resultSet.getString("operation"));
                entity.setResultValue(resultSet.getDouble("result_value"));
                entity.setResultUnit(resultSet.getString("result_unit"));
                entity.setResultMeasurementType(resultSet.getString("result_measurement_type"));
                entity.setResultString(resultSet.getString("result_string"));
                entity.setError(resultSet.getBoolean("is_error"));
                entity.setErrorMessage(resultSet.getString("error_message"));

                list.add(entity);
            }

        } catch (SQLException e) {
            throw DatabaseException.queryFailed("Fetch all measurements", e);
        }

        return list;
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        List<QuantityMeasurementEntity> list = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_OPERATION_QUERY)) {
            statement.setString(1, operation);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(mapResultSetToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            throw DatabaseException.queryFailed("Fetch measurements by operation: " + operation, e);
        }
        return list;
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
        List<QuantityMeasurementEntity> list = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_TYPE_QUERY)) {
            statement.setString(1, measurementType);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(mapResultSetToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            throw DatabaseException.queryFailed("Fetch measurements by type: " + measurementType, e);
        }
        return list;
    }

    private QuantityMeasurementEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setId(resultSet.getLong("id"));
        entity.setThisValue(resultSet.getDouble("this_value"));
        entity.setThisUnit(resultSet.getString("this_unit"));
        entity.setThisMeasurementType(resultSet.getString("this_measurement_type"));
        entity.setThatValue(resultSet.getDouble("that_value"));
        entity.setThatUnit(resultSet.getString("that_unit"));
        entity.setThatMeasurementType(resultSet.getString("that_measurement_type"));
        entity.setOperation(resultSet.getString("operation"));
        entity.setResultValue(resultSet.getDouble("result_value"));
        entity.setResultUnit(resultSet.getString("result_unit"));
        entity.setResultMeasurementType(resultSet.getString("result_measurement_type"));
        entity.setResultString(resultSet.getString("result_string"));
        entity.setError(resultSet.getBoolean("is_error"));
        entity.setErrorMessage(resultSet.getString("error_message"));
        return entity;
    }

    @Override
    public void deleteAll() {

        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM quantity_measurement_entity");

            logger.info("All measurements deleted");

        } catch (SQLException e) {
            throw DatabaseException.queryFailed("Delete all measurements", e);
        }
    }

    @Override
    public long getTotalCount() {

        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery("SELECT COUNT(*) FROM quantity_measurement_entity")) {

            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

        } catch (SQLException e) {
            throw DatabaseException.queryFailed("Count measurements", e);
        }

        return 0;
    }

    @Override
    public void releaseResources() {
        ConnectionPool.closePool();
        logger.info("Repository resources released");
    }
}