package ro.teamnet.zth.api.em;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Ovidiu
 * Date:   4/28/2015
 */
public class QueryBuilder {

    private Object tableName;
    private List<ColumnInfo> queryColumns;
    private QueryType queryType;
    private List<Condition> conditions;

    public QueryBuilder() {
        queryColumns = new ArrayList<ColumnInfo>();
        conditions   = new ArrayList<Condition>();
    }

    /**
     * Adds the new condition to the query
     * @param condition
     * @return reference to this query to allow chaining
     */
    public QueryBuilder addCondition(Condition condition) {
        conditions.add(condition);
        return this;
    }

    /**
     * Sets the table name
     * @param tableName
     * @return reference to this query to allow chaining
     */
    public QueryBuilder setTableName(Object tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * Adds the specified columns to the query
     * @param queryColumns
     * @return reference to this query to allow chaining
     */
    public QueryBuilder addQueryColumns(List<ColumnInfo> queryColumns) {
        this.queryColumns.addAll(queryColumns);
        return this;
    }

    /**
     * Sets the type of the query
     * @param queryType
     * @return reference to this query to allow chaining
     */
    public QueryBuilder setQueryType(QueryType queryType) {
        this.queryType = queryType;
        return this;
    }

    /**
     * Constructs and executes a SELECT query using the current parameters
     */
    private String createSelectQuery() {
        StringBuilder sql = new StringBuilder("SELECT ");

        // Add columns
        for (int i = 0; i < queryColumns.size() - 1; i++) {
            sql.append(queryColumns.get(i).getDbName());
            sql.append(",");
        }
        if (queryColumns.size() > 0)
            sql.append(queryColumns.get(queryColumns.size() - 1).getDbName());

        // Add table name
        sql.append("\nFROM ");
        sql.append(tableName.toString());

        // Add conditions
        if (conditions.size() > 0) {
            sql.append("\nWHERE ");
            for (int i = 0; i < conditions.size() - 1; i++) {
                sql.append(conditions.get(i).getColumnName());
                sql.append(" = ");
                if (conditions.get(i).getValue() instanceof String)
                    sql.append("'").append(conditions.get(i).getValue().toString()).append("'");
                else
                    sql.append(conditions.get(i).getValue().toString());
                sql.append(" AND ");
            }

            sql.append(conditions.get(conditions.size() - 1).getColumnName());
            sql.append(" = ");
            sql.append(conditions.get(conditions.size() - 1).getValue().toString());
        }
        sql.append(";");

        return sql.toString();
    }

    /**
     * Constructs and executes an UPDATE query using the current parameters
     */
    private String createUpdateQuery() {
        StringBuilder sql = new StringBuilder("UPDATE ");

        // Add table name
        sql.append(tableName.toString());

        // Add columns to be updated
        sql.append("\nSET ");
        for (int i = 0; i < queryColumns.size() - 1; i++) {
            sql.append(queryColumns.get(i).getDbName());
            sql.append(" = ");
            if (queryColumns.get(i).getValue() instanceof String)
                sql.append("'").append(queryColumns.get(i).getValue()).append("'");
            else
                sql.append(queryColumns.get(i).getValue());
            sql.append(", ");
        }
        if (queryColumns.size() > 0) {
            sql.append(queryColumns.get(queryColumns.size() - 1).getDbName());
            sql.append(" = ");
            if (queryColumns.get(queryColumns.size() - 1).getValue() instanceof String)
                sql.append("'").append(queryColumns.get(queryColumns.size() - 1).getValue()).append("'");
            else
                sql.append(queryColumns.get(queryColumns.size() - 1).getValue());
        }

        // Add conditions
        if (conditions.size() > 0) {
            sql.append("\nWHERE ");
            for (int i = 0; i < conditions.size() - 1; i++) {
                sql.append(conditions.get(i).getColumnName());
                sql.append(" = ");
                if (conditions.get(i).getValue() instanceof String)
                    sql.append("'").append(conditions.get(i).getValue().toString()).append("'");
                else
                    sql.append(conditions.get(i).getValue().toString());
                sql.append(" AND ");
            }

            sql.append(conditions.get(conditions.size() - 1).getColumnName());
            sql.append(" = ");
            sql.append(conditions.get(conditions.size() - 1).getValue().toString());
        }
        sql.append(";");

        return sql.toString();
    }

    /**
     * Constructs and executes an INSERT query using the current parameters
     */
    private String createInsertQuery() {
        StringBuilder sql = new StringBuilder("INSERT INTO ");

        // Add table name
        sql.append(tableName.toString()).append("\n");

        // Add columns
        if (queryColumns.size() > 0) {
            sql.append(" (");
            for (int i = 0; i < queryColumns.size() - 1; i++) {
                sql.append(queryColumns.get(i).getDbName());
                sql.append(", ");
            }
            sql.append(queryColumns.get(queryColumns.size() - 1).getDbName());
            sql.append(")");
        }

        // Add values corresponding to each column
        sql.append("\nVALUES (");
        for (int i = 0; i < queryColumns.size() - 1; i++) {
            if (queryColumns.get(i).getValue() instanceof String)
                sql.append("'").append(queryColumns.get(i).getValue()).append("'");
            else
                sql.append(queryColumns.get(i).getValue());
            sql.append(", ");
        }
        if (queryColumns.get(queryColumns.size() - 1).getValue() instanceof String)
            sql.append("'").append(queryColumns.get(queryColumns.size() - 1).getValue()).append("'");
        else
            sql.append(queryColumns.get(queryColumns.size() - 1).getValue());

        sql.append(");");

        return sql.toString();
    }

    /**
     * Constructs and executes a DELETE query using the current parameters
     */
    private String createDeleteQuery() {
        StringBuilder sql = new StringBuilder("DELETE FROM ");

        // Add table name
        sql.append(tableName.toString());

        // Add conditions
        sql.append("\nWHERE ");
        for (int i = 0; i < conditions.size() - 1; i++) {
            sql.append(conditions.get(i).getColumnName());
            sql.append(" = ");
            if (conditions.get(i).getValue() instanceof String)
                sql.append("'").append(conditions.get(i).getValue().toString()).append("'");
            else
                sql.append(conditions.get(i).getValue().toString());
            sql.append(" AND ");
        }
        sql.append(conditions.get(conditions.size() - 1).getColumnName());
        sql.append(" = ");
        sql.append(conditions.get(conditions.size() - 1).getValue().toString());
        sql.append(";");

        return sql.toString();
    }

    /**
     * Constructs the appropriate query and returns it as a String
     * @return query
     */
    public String createQuery() {
        if (queryType == QueryType.SELECT)
            return createSelectQuery();
        else if (queryType == QueryType.UPDATE)
            return createUpdateQuery();
        else if (queryType == QueryType.INSERT)
            return createInsertQuery();
        else if (queryType == QueryType.DELETE)
            return createDeleteQuery();
        else
            return null;
    }

}
