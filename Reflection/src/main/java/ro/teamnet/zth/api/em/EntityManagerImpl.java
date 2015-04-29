package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.database.DBManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

/**
 * Author: Ovidiu
 * Date:   4/29/2015
 */
public class EntityManagerImpl implements EntityManager {

    @Override
    public <T> T findById(Class<T> entityClass, Long id) {
        // Create a connection to the Database
        try (Connection conn = DBManager.getConnection()) {
            // Get table name
            String tableName = EntityUtils.getTableName(entityClass);

            // Get table columns
            List<ColumnInfo> columns = EntityUtils.getColumns(entityClass);

            // Set column values to the current values of the entity
            // for (ColumnInfo column : columns) {}

            // Find primary key column i.e. ID column
            String id_column = null;
            for (ColumnInfo column : columns) {
                if (column.isId()) {
                    id_column = column.getColumnName();
                    break;
                }
            }

            // Check if entity has a primary key; otherwise, early exit
            if (id_column == null) {
                System.out.println("ERROR! Entity doesn't have a primary key.");
                return null;
            }

            // Define query condition (find by ID)
            Condition condition = new Condition();
            condition.setColumnName(id_column);
            condition.setValue(id);

            // Create the actual query
            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.setTableName(tableName);
            queryBuilder.setQueryType(QueryType.SELECT);
            queryBuilder.addQueryColumns(columns);
            queryBuilder.addCondition(condition);

            String sqlQuery = queryBuilder.createQuery();

            // Execute the query and store the results in a new instance of class
            try (Statement stmt = DBManager.getConnection().createStatement();
                 ResultSet result = stmt.executeQuery(sqlQuery)) {
                result.first();

                // Create an instance and set its values
                T instance = entityClass.newInstance();

                Field field;
                for (ColumnInfo column : columns) {
                    if (column.getColumnType() == String.class) {
                        field = entityClass.getDeclaredField(column.getColumnName());
                        field.set(instance, result.getString(column.getDbName()));
                    } else if (column.getColumnType() == Long.class) {
                        field = entityClass.getDeclaredField(column.getColumnName());
                        field.set(instance, result.getLong(column.getDbName()));
                    } else if (column.getColumnType() == Double.class) {
                        field = entityClass.getDeclaredField(column.getColumnName());
                        field.set(instance, result.getDouble(column.getDbName()));
                    } else if (column.getColumnType() == Date.class) {
                        field = entityClass.getDeclaredField(column.getColumnName());
                        field.set(instance, result.getDate(column.getDbName()));
                    }
                }

                return instance;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            } catch (InstantiationException e) {
                e.printStackTrace();
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass) {
        return null;
    }

    @Override
    public <T> Object insert(T entity) {
        return null;
    }

    @Override
    public <T> T update(T entity) {
        return null;
    }

    @Override
    public void delete(Object entity) {

    }
}
