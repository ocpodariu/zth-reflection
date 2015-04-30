package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.database.DBManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
                    id_column = column.getDbName();
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
            queryBuilder.setTableName(tableName)
                        .setQueryType(QueryType.SELECT)
                        .addQueryColumns(columns)
                        .addCondition(condition);

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
        List<T> records = new ArrayList<T>();

        // Create a connection to the Database
        try (Connection conn = DBManager.getConnection()) {
            // Get table name
            String tableName = EntityUtils.getTableName(entityClass);

            // Get table columns
            List<ColumnInfo> columns = EntityUtils.getColumns(entityClass);

            // Create the actual query
            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.setTableName(tableName)
                        .setQueryType(QueryType.SELECT)
                        .addQueryColumns(columns);

            String sqlQuery = queryBuilder.createQuery();

            // Execute the query and store results in a list
            try (Statement stmt = DBManager.getConnection().createStatement();
                 ResultSet results = stmt.executeQuery(sqlQuery)) {

                // Navigate result set
                while(results.next()) {
                    // Create an instance and set its values
                    T instance = entityClass.newInstance();

                    Field field;
                    for (ColumnInfo column : columns) {
                        if (column.getColumnType() == String.class) {
                            field = entityClass.getDeclaredField(column.getColumnName());
                            field.set(instance, results.getString(column.getDbName()));
                        } else if (column.getColumnType() == Long.class) {
                            field = entityClass.getDeclaredField(column.getColumnName());
                            field.set(instance, results.getLong(column.getDbName()));
                        } else if (column.getColumnType() == Double.class) {
                            field = entityClass.getDeclaredField(column.getColumnName());
                            field.set(instance, results.getDouble(column.getDbName()));
                        } else if (column.getColumnType() == Date.class) {
                            field = entityClass.getDeclaredField(column.getColumnName());
                            field.set(instance, results.getDate(column.getDbName()));
                        }
                    }

                    // Store it in the records list
                    records.add(instance);
                }

                return records;
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
    public <T> Object insert(T entity) {
        // Create a connection to the Database
        try (Connection conn = DBManager.getConnection()) {
            // Get table name
            String tableName = EntityUtils.getTableName(entity.getClass());

            // Get table columns
            List<ColumnInfo> columns = EntityUtils.getColumns(entity.getClass());

            // Set column values to the current values of the entity
            try {
                Field field;
                for (ColumnInfo column : columns) {
                    field = entity.getClass().getDeclaredField(column.getColumnName());
                    column.setValue(field.get(entity));
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }

            // Create the actual query
            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.setTableName(tableName)
                        .setQueryType(QueryType.INSERT)
                        .addQueryColumns(columns);

            String sqlQuery = queryBuilder.createQuery();

            // Execute the query and store the results in a new instance of class
            try (Statement stmt = conn.createStatement()) {
                // Execute insert operation
                stmt.executeQuery(sqlQuery);

                // Commit changes to database
                conn.commit();

                // Get last inserted id from database
                ResultSet resultId = stmt.executeQuery("SELECT last_insert_id();");

                if (resultId.next()) {
                    Long id = resultId.getLong(1);

                    return findById(entity.getClass(), id);
                } else {
                    return null;
                }
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
    public <T> T update(T entity) {
        // Create a connection to the Database
        try (Connection conn = DBManager.getConnection()) {
            // Get table name
            String tableName = EntityUtils.getTableName(entity.getClass());

            // Get table columns
            List<ColumnInfo> columns = EntityUtils.getColumns(entity.getClass());

            // Set column values to the current values of the entity
            try {
                Field field;
                for (ColumnInfo column : columns) {
                    field = entity.getClass().getDeclaredField(column.getColumnName());
                    column.setValue(field.get(entity));
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }

            // Get ID column name and value of record to be updated
            String idColumn = null;
            Object idValue = null;

            for (ColumnInfo column : columns)
                if (column.isId()) {
                    idColumn = column.getDbName();
                    idValue = column.getValue();
                    break;
                }

            // Define query condition (find by ID and update)
            Condition condition = new Condition();
            condition.setColumnName(idColumn);
            condition.setValue(idValue);

            // Create the actual query
            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.setTableName(tableName)
                        .setQueryType(QueryType.UPDATE)
                        .addQueryColumns(columns)
                        .addCondition(condition);

            String sqlQuery = queryBuilder.createQuery();

            // Execute the query and store the results in a new instance of class
            try (Statement stmt = conn.createStatement()) {
                // Execute query
                stmt.executeQuery(sqlQuery);

                // Commit changes to database
                conn.commit();

                return entity;
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
    public void delete(Object entity) {

    }
}
